package com.markyang.framework.app.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.system.form.search.ConvenienceUserMenuSearchForm;
import com.markyang.framework.app.system.form.update.ConvenienceUserMenuUpdateForm;
import com.markyang.framework.app.system.service.ConvenienceUserMenuService;
import com.markyang.framework.app.system.repository.ConvenienceUserMenuRepository;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.system.ConvenienceUserMenuDto;
import com.markyang.framework.pojo.entity.system.ConvenienceUserMenu;
import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.service.core.service.support.CustomizationSearchCondition;
import com.markyang.framework.util.AuthUtils;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户便捷菜单(ConvenienceUserMenu)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-08 17:37:01
 */
@Api(tags = "用户便捷菜单控制器")
@ApiSort(1)
@RestController
@RequestMapping("/convenienceUserMenu")
@AllArgsConstructor
@Slf4j
public class ConvenienceUserMenuController extends AbstractSystemController<ConvenienceUserMenu, ConvenienceUserMenuService, ConvenienceUserMenuSearchForm, ConvenienceUserMenuUpdateForm> {

    private final ConvenienceUserMenuService convenienceUserMenuService;
    private final ConvenienceUserMenuRepository convenienceUserMenuRepository;

  /**
      * 额外的搜索条件
      *
      * @return 条件谓词
      */
     @Override
     public CustomizationSearchCondition<ConvenienceUserMenu> addAdditionalSearchCondition() {
       return queryWrapper -> queryWrapper.lambda().eq(ConvenienceUserMenu::getUserId, AuthUtils.getLoggedUserOrgId());
     }

    /**
     * 保存便捷菜单
     * @param convenienceUserMenus 便捷菜单
     * @return 结果对象
     */
     @PutMapping("/save")
     @Transactional(rollbackFor = Exception.class)
     public ResultVo<?> saveConvenienceMenus(@RequestBody List<ConvenienceUserMenuUpdateForm> convenienceUserMenus) {
         // 保存
         final String userId = AuthUtils.getLoggedUserId();
         // 删除已存在的菜单
         this.convenienceUserMenuService.remove(Wrappers.<ConvenienceUserMenu>lambdaQuery().eq(ConvenienceUserMenu::getUserId, userId));
         // 添加新的菜单
         List<ConvenienceUserMenu> userMenuList = convenienceUserMenus.parallelStream().map(convenienceUserMenu -> {
             ConvenienceUserMenu newMenu = this.convenienceUserMenuService.create();
             BeanUtils.copyProperties(convenienceUserMenu, newMenu);
             newMenu.setUserId(userId);
             return newMenu;
         }).collect(Collectors.toList());
         this.convenienceUserMenuRepository.insertBatchSomeColumn(userMenuList);
         return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP);
     }

    /**
     * 获取用户所有的便捷菜单
     * @return 结果对象
     */
    @GetMapping("/menus")
    public ResultVo<List<ConvenienceUserMenuDto>> getConvenienceUserMenus() {
         return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.convenienceUserMenuRepository.getConvenienceUserMenus(AuthUtils.getLoggedUserId()));
    }
 }