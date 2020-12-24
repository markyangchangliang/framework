package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.ItemFeeRatioSearchForm;
import com.markyang.framework.app.system.form.update.ItemFeeRatioUpdateForm;
import com.markyang.framework.app.system.service.ItemFeeRatioService;
import com.markyang.framework.pojo.entity.system.ItemFeeRatio;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目分成比例(ItemFeeRatio)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "项目分成比例控制器")
@ApiSort(1)
@CacheName("itemFeeRatio")
@RestController
@RequestMapping("/itemFeeRatio")
@AllArgsConstructor
@Slf4j
public class ItemFeeRatioController extends AbstractSystemController<ItemFeeRatio, ItemFeeRatioService, ItemFeeRatioSearchForm, ItemFeeRatioUpdateForm> {


}