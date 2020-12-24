package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.SystemOptionSearchForm;
import com.markyang.framework.app.system.form.update.SystemOptionUpdateForm;
import com.markyang.framework.app.system.service.SystemOptionService;
import com.markyang.framework.pojo.entity.system.SystemOption;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统选项(SystemOption)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "系统选项控制器")
@ApiSort(1)
@CacheName("systemOption")
@RestController
@RequestMapping("/systemOption")
@Slf4j
public class SystemOptionController extends AbstractSystemController<SystemOption, SystemOptionService, SystemOptionSearchForm, SystemOptionUpdateForm> {
}