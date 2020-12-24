package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.AppSearchForm;
import com.markyang.framework.app.system.form.update.AppUpdateForm;
import com.markyang.framework.app.system.service.AppService;
import com.markyang.framework.pojo.common.support.ItemEntry;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.entity.system.App;
import com.markyang.framework.pojo.web.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 应用管理(App)控制器
 *
 * @author ytangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "应用管理控制器")
@ApiSort(1)
@CacheName("app")
@RestController
@RequestMapping("/app")
@AllArgsConstructor
@Slf4j
public class AppController extends AbstractSystemController<App, AppService, AppSearchForm, AppUpdateForm> {

    private final AppService appService;

    /**
     * 在实体创建之前处理实体属性数据
     *
     * @param entity 实体对象
     * @param form   表单对象
     */
    @Override
    public void beforeAdd(App entity, AppUpdateForm form) {
        entity.setId(form.getId());
    }

    /**
     * 在实体修改之前处理实体属性数据
     *
     * @param entity 实体对象
     * @param form   表单对象
     * @return bool 是否继续拷贝属性
     */
    @Override
    public boolean beforeUpdate(App entity, AppUpdateForm form) {
        this.beforeAdd(entity, form);
        return true;
    }


    /**
     * 获取全部应用
     *
     * @return 结果对象
     */
    @ApiOperation(value = "获取全部应用", notes = "获取全部应用")
    @GetMapping(value = "/all", produces = "application/json;charset=UTF-8")
    public ResultVo<List<ItemEntry>> all() {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.appService.getAll());
    }
}