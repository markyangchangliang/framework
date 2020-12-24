package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.ExportFieldSearchForm;
import com.markyang.framework.app.system.form.update.ExportFieldUpdateForm;
import com.markyang.framework.app.system.service.ExportFieldService;
import com.markyang.framework.pojo.entity.system.ExportField;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 导出数据字段(ExportField)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "导出数据字段控制器")
@ApiSort(1)
@CacheName("exportField")
@RestController
@RequestMapping("/exportField")
@AllArgsConstructor
@Slf4j
public class ExportFieldController extends AbstractSystemController<ExportField, ExportFieldService, ExportFieldSearchForm, ExportFieldUpdateForm> {

}