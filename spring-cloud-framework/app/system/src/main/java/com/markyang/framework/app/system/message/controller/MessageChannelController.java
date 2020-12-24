package com.markyang.framework.app.system.message.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.pojo.common.support.ItemEntry;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.entity.message.MessageChannel;
import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.service.core.controller.AbstractFrameworkController;
import com.markyang.framework.service.message.form.search.MessageChannelSearchForm;
import com.markyang.framework.service.message.form.update.MessageChannelUpdateForm;
import com.markyang.framework.service.message.service.MessageChannelService;
import com.markyang.framework.util.AuthUtils;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息配置(MessageChannel)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-12 09:45:19
 */
@Api(tags = "消息配置控制器")
@ApiSort(1)
@CacheName("messageChannel")
@RestController
@RequestMapping("/messageChannel")
@AllArgsConstructor
@Slf4j
public class MessageChannelController extends AbstractFrameworkController<MessageChannel, MessageChannelService, MessageChannelSearchForm, MessageChannelUpdateForm> {

    private final MessageChannelService messageChannelService;

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
     * 获取所有的messageChannel
     * @return 结果对象
     */
    @GetMapping("/messageChannels")
    public ResultVo<List<ItemEntry>> getAllMessageChannel() {
        List<MessageChannel> messageChannels = this.messageChannelService.list(Wrappers.<MessageChannel>lambdaQuery().select(MessageChannel::getName, MessageChannel::getRemark).eq(MessageChannel::getOrgId, AuthUtils.getLoggedUserOrgId()));
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, messageChannels.parallelStream().map(messageChannel -> ItemEntry.of(messageChannel.getName(), messageChannel.getRemark())).collect(Collectors.toList()));
    }
}