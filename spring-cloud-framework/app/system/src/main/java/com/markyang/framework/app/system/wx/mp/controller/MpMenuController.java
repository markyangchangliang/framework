package com.markyang.framework.app.system.wx.mp.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.wx.mp.form.search.MpMenuSearchForm;
import com.markyang.framework.app.system.wx.mp.form.update.MpMenuUpdateForm;
import com.markyang.framework.app.system.wx.mp.service.MpMenuService;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.wx.MenuDto;
import com.markyang.framework.pojo.entity.system.wx.MpMenu;
import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.service.core.controller.AbstractFrameworkController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.web.bind.annotation.*;

/**
 * 微信自定义菜单(MpMenu)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:11:19
 */
@Api(tags = "微信自定义菜单控制器")
@ApiSort(1)
@CacheName("mpMenu")
@RestController
@RequestMapping("/api/mpMenu")
@Slf4j
@AllArgsConstructor
public class MpMenuController extends AbstractFrameworkController<MpMenu, MpMenuService, MpMenuSearchForm, MpMenuUpdateForm> {

    private final MpMenuService mpMenuService;

    /**
     * 微信获取菜单
     *
     * @return 结果对象
     */
    @ApiOperationSupport(order = 3, author = "yangchangliang")
    @ApiOperation(value = "微信获取菜单", notes = "微信获取菜单")
    @GetMapping("/getWxMenuValue")
    public ResultVo<WxMpMenu> getWxMenuValue() throws WxErrorException {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, mpMenuService.getWxMenuValue());
    }

    /**
     * 微信保存并发布菜单
     *
     * @param menu 菜单
     * @return 菜单实体
     */
    @ApiOperationSupport(order = 2, author = "yangchangliang")
    @ApiOperation(value = "微信保存并发布菜单", notes = "微信保存并发布菜单")
    @PostMapping("/saveAndRelease")
    public ResultVo<Boolean> saveAndRelease(@RequestBody @ApiParam(name = "menu",required = true,value = "自定义菜单实体类") MenuDto menu) throws WxErrorException {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP,mpMenuService.saveAndRelease(menu));

    }
    /**
     * 微信删除菜单
     *
     * @return 结果对象
     */
    @ApiOperationSupport(order = 4, author = "yangchangliang")
    @ApiOperation(value = "微信删除菜单", notes = "微信删除菜单")
    @PutMapping("/delWxMenuValue")
    public ResultVo<Boolean> delWxMenuValue() {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, mpMenuService.delWxMenuValue());
    }
}