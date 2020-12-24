package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.ExportTableSearchForm;
import com.markyang.framework.app.system.form.update.ExportTableUpdateForm;
import com.markyang.framework.app.system.service.ExportTableService;
import com.markyang.framework.pojo.entity.system.ExportTable;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 导出数据表名(ExportTable)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "导出数据表名控制器")
@ApiSort(1)
@CacheName("exportTable")
@RestController
@RequestMapping("/exportTable")
@AllArgsConstructor
@Slf4j
public class ExportTableController extends AbstractSystemController<ExportTable, ExportTableService, ExportTableSearchForm, ExportTableUpdateForm> {

}