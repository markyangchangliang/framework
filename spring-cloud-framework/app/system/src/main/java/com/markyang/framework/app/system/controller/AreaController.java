package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.AreaSearchForm;
import com.markyang.framework.app.system.form.update.AreaUpdateForm;
import com.markyang.framework.app.system.service.AreaService;
import com.markyang.framework.pojo.entity.system.Area;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 区域信息(Area)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "区域信息控制器")
@ApiSort(1)
@CacheName("area")
@RestController
@RequestMapping("/area")
@AllArgsConstructor
@Slf4j
public class AreaController extends AbstractSystemController<Area, AreaService, AreaSearchForm, AreaUpdateForm> {

}