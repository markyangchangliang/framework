package com.markyang.framework.app.system.message.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.controller.AbstractSystemController;
import com.markyang.framework.pojo.common.support.ItemEntry;
import com.markyang.framework.pojo.common.support.TemplateMessageArgument;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.entity.message.MessageBusinessKey;
import com.markyang.framework.pojo.entity.message.MessageTemplate;
import com.markyang.framework.pojo.message.MessageTemplateDetails;
import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.service.message.MessageChannelTemplateService;
import com.markyang.framework.service.message.form.search.MessageTemplateSearchForm;
import com.markyang.framework.service.message.form.update.MessageTemplateUpdateForm;
import com.markyang.framework.service.message.service.MessageBusinessKeyService;
import com.markyang.framework.service.message.service.MessageTemplateService;
import com.markyang.framework.util.AuthUtils;
import com.markyang.framework.util.ClassOperationUtils;
import com.markyang.framework.util.ReflectionOperationUtils;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息模板(MessageTemplate)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-12 09:45:19
 */
@Api(tags = "消息模板控制器")
@ApiSort(1)
@CacheName("messageTemplate")
@RestController
@RequestMapping("/messageTemplate")
@Slf4j
@AllArgsConstructor
public class MessageTemplateController extends AbstractSystemController<MessageTemplate, MessageTemplateService, MessageTemplateSearchForm, MessageTemplateUpdateForm> {

    private final MessageBusinessKeyService messageBusinessKeyService;
    private final List<MessageChannelTemplateService> messageChannelTemplateServices;

    /**
     * 是否是机构所属数据
     *
     * @return bool
     */
    @Override
    public boolean isOrgBelonged() {
        return true;
    }

    /**
     * 获取消息业务Key
     * @return 结果对象
     */
    @GetMapping("/messageBusinessKeys")
    public ResultVo<List<ItemEntry>> getMessageBusinessKeys() {
        List<MessageBusinessKey> messageBusinessKeys = this.messageBusinessKeyService.list(
            Wrappers.<MessageBusinessKey>lambdaQuery()
                .select(MessageBusinessKey::getKeyName, MessageBusinessKey::getRemark)
                .eq(MessageBusinessKey::getOrgId, AuthUtils.getLoggedUserOrgId())
        );
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, messageBusinessKeys.parallelStream().map(messageBusinessKey -> ItemEntry.of(messageBusinessKey.getKeyName(), messageBusinessKey.getRemark())).collect(Collectors.toList()));
    }

    /**
     * 获取指定通道下的所有模板
     * @param channelName 通道名称
     * @return 结果对象
     */
    @GetMapping("/messageTemplates/{channelName}")
    public ResultVo<List<MessageTemplateDetails>> getMessageTemplates(@PathVariable String channelName) {
        for (MessageChannelTemplateService messageChannelTemplateService : this.messageChannelTemplateServices) {
            if (messageChannelTemplateService.supports(channelName)) {
                return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, messageChannelTemplateService.getTemplates(AuthUtils.getLoggedUserOrgId()));
            }
        }
        return ResultVo.error("没有找到对应通道[" + channelName + "]的消息模板服务对象");
    }

    /**
     * 获取参数类中的字段
     * @param businessKey 业务Key
     * @return 结果对象
     */
    @GetMapping("/businessKeyParameters/{businessKey}")
    public ResultVo<List<ItemEntry>> getArgumentDataClassFields(@PathVariable String businessKey) {
        return this.messageBusinessKeyService.getBusinessKey(AuthUtils.getLoggedUserOrgId(), businessKey)
            .map(messageBusinessKey -> ClassOperationUtils.loadClass(messageBusinessKey.getParameterDto()).map(clazz -> {
                List<Field> fields = ReflectionOperationUtils.getAnnotatedFields(clazz, TemplateMessageArgument.class);
                return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, fields.parallelStream().map(field -> {
                    String value = field.getAnnotation(TemplateMessageArgument.class).value();
                    if (StringUtils.isBlank(value)) {
                        value = field.getName();
                    }
                    return ItemEntry.of(field.getName(), value);
                }).collect(Collectors.toList()));
            }).orElse(ResultVo.error("加载Class失败：" + messageBusinessKey.getParameterDto()))).orElse(ResultVo.error("找不到业务key[" + businessKey + "]相关信息"));
    }
}