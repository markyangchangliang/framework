package com.markyang.framework.app.system.wx.mp.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.wx.mp.form.search.MpUserSearchForm;
import com.markyang.framework.app.system.wx.mp.form.update.MpUserUpdateForm;
import com.markyang.framework.app.system.wx.mp.service.MpUserService;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.entity.system.wx.MpUser;
import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.service.core.controller.AbstractFrameworkController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信用户(MpUser)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:11:19
 */
@Api(tags = "微信用户控制器")
@ApiSort(1)
@CacheName("mpUser")
@RestController
@RequestMapping("/api/mpUser")
@Slf4j
@AllArgsConstructor
public class MpUserController extends AbstractFrameworkController<MpUser, MpUserService, MpUserSearchForm, MpUserUpdateForm> {

    private final MpUserService mpUserService;

    /**
     * 微信同步用户信息
     * @return 结果对象
     */
    @ApiOperationSupport(order = 10, author = "yangchangliang")
    @ApiOperation(value = "微信同步用户信息", notes = "微信同步用户信息")
    @GetMapping("/synchronize")
    public ResultVo<Boolean> synchronize() throws WxErrorException {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, mpUserService.synchron());
    }
}