package com.markyang.framework.app.system.message.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.base.exception.UpdateDeniedException;
import com.markyang.framework.app.system.controller.AbstractSystemController;
import com.markyang.framework.pojo.common.support.ItemEntry;
import com.markyang.framework.pojo.common.support.TemplateMessageArgument;
import com.markyang.framework.pojo.common.support.TemplateMessageArgumentsData;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.entity.message.MessageBusinessKey;
import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.service.message.form.search.MessageBusinessKeySearchForm;
import com.markyang.framework.service.message.form.update.MessageBusinessKeyUpdateForm;
import com.markyang.framework.service.message.service.MessageBusinessKeyService;
import com.markyang.framework.util.ClassOperationUtils;
import com.markyang.framework.util.ReflectionOperationUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 业务key信息表(MessageBusinessKey)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-12 09:45:19
 */
@Api(tags = "业务key信息表控制器")
@ApiSort(1)
@CacheName("messageBusinessKey")
@RestController
@RequestMapping("/messageBusinessKey")
@Slf4j
public class MessageBusinessKeyController extends AbstractSystemController<MessageBusinessKey, MessageBusinessKeyService, MessageBusinessKeySearchForm, MessageBusinessKeyUpdateForm> {

    /**
     * 获取所有的参数对象类
     * @return 结果对象
     */
    @GetMapping("/dataClasses")
    public ResultVo<List<ItemEntry>> getAllArgumentsDataClasses() {
        Set<Class<?>> classes = ClassOperationUtils.scanPackageClasses("com.markyang.framework.pojo.dto.message", metadataReader -> metadataReader.getAnnotationMetadata().hasAnnotation(TemplateMessageArgumentsData.class.getName()));
        List<ItemEntry> entries = classes.parallelStream().map(clazz -> {
            String value = clazz.getAnnotation(TemplateMessageArgumentsData.class).value();
            if (StringUtils.isBlank(value)) {
                value = clazz.getSimpleName();
            }
            return ItemEntry.of(clazz.getName(), value);
        }).collect(Collectors.toList());
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, entries);
    }

    /**
     * 获取参数类中的字段
     * @param className 类名
     * @return 结果对象
     */
    @GetMapping("/dataClassFields/{className}")
    public ResultVo<List<ItemEntry>> getArgumentDataClassFields(@PathVariable String className) {
        return ClassOperationUtils.loadClass(className).map(clazz -> {
            List<Field> fields = ReflectionOperationUtils.getAnnotatedFields(clazz, TemplateMessageArgument.class);
            return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, fields.parallelStream().map(field -> {
                String value = field.getAnnotation(TemplateMessageArgument.class).value();
                if (StringUtils.isBlank(value)) {
                    value = field.getName();
                }
                return ItemEntry.of(field.getName(), value);
            }).collect(Collectors.toList()));
        }).orElse(ResultVo.error("加载Class失败：" + className));
    }

    /** 在实体创建之前处理实体属性数据
     *
     * @param entity 实体对象
     * @param form   表单对象
     */
    @Override
    public void beforeAdd(MessageBusinessKey entity, MessageBusinessKeyUpdateForm form) {
        // 验证DTO是否存在
        if (!ClassOperationUtils.loadClass(form.getParameterDto()).isPresent()) {
            throw new UpdateDeniedException("DTO类[" + form.getParameterDto() + "]不存在");
        }
    }

    /**
     * 在实体修改之前处理实体属性数据
     *
     * @param entity 实体对象
     * @param form   表单对象
     * @return bool 是否继续拷贝属性
     */
    @Override
    public boolean beforeUpdate(MessageBusinessKey entity, MessageBusinessKeyUpdateForm form) {
        this.beforeAdd(entity, form);
        return true;
    }

    /**
     * 是否是机构所属数据
     *
     * @return bool
     */
    @Override
    public boolean isOrgBelonged() {
        return true;
    }
}