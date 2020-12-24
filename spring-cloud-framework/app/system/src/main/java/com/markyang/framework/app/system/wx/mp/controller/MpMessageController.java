package com.markyang.framework.app.system.wx.mp.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.wx.mp.form.search.MpMessageSearchForm;
import com.markyang.framework.app.system.wx.mp.form.update.MpMessageUpdateForm;
import com.markyang.framework.app.system.wx.mp.service.MpMessageService;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.entity.system.wx.MpMessage;
import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.service.core.controller.AbstractFrameworkController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信消息(MpMessage)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:11:19
 */
@Api(tags = "微信消息控制器")
@ApiSort(1)
@CacheName("mpMessage")
@RestController
@RequestMapping("/api/mpMessage")
@Slf4j
@AllArgsConstructor
public class MpMessageController extends AbstractFrameworkController<MpMessage, MpMessageService, MpMessageSearchForm, MpMessageUpdateForm> {

    private final MpMessageService mpMessageService;

    /**
     * 微信群发消息.
     * @return 结果对象
     */
    @ApiOperationSupport(order = 10, author = "yangchangliang")
    @ApiOperation(value = "微信群发消息", notes = "微信群发消息")
    @PostMapping("/sendMessage")
    public ResultVo<Boolean> sendMessage(@RequestBody MpMessage message) throws Exception {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, mpMessageService.sendMessage(message));
    }
}