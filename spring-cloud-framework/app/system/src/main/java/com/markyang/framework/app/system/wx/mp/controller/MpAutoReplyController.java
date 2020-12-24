package com.markyang.framework.app.system.wx.mp.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.wx.mp.form.search.MpAutoReplySearchForm;
import com.markyang.framework.app.system.wx.mp.form.update.MpAutoReplyUpdateForm;
import com.markyang.framework.app.system.wx.mp.service.MpAutoReplyService;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.entity.system.wx.MpAutoReply;
import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.service.core.controller.AbstractFrameworkController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信自动回复(MpAutoReply)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:11:19
 */
@Api(tags = "微信自动回复控制器")
@ApiSort(1)
@CacheName("mpAutoReply")
@RestController
@RequestMapping("/api/mpAutoReply")
@Slf4j
@AllArgsConstructor
public class MpAutoReplyController extends AbstractFrameworkController<MpAutoReply, MpAutoReplyService, MpAutoReplySearchForm, MpAutoReplyUpdateForm> {

    private final WxMpService wxService;

    /**
     * 获取access_token接口
     * @return 结果对象
     */
    @ApiOperationSupport(order = 10, author = "yangchangliang")
    @ApiOperation(value = "获取access_token接口", notes = "获取access_token接口")
    @GetMapping("/getAccessToken")
    public ResultVo<String> getAccessToken() throws WxErrorException {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP,wxService.getAccessToken());
    }
}