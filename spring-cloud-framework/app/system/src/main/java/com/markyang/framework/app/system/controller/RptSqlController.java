package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.RptSqlSearchForm;
import com.markyang.framework.app.system.form.update.RptSqlUpdateForm;
import com.markyang.framework.app.system.service.RptSqlService;
import com.markyang.framework.pojo.entity.system.RptSql;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报表管理(RptSql)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "报表管理控制器")
@ApiSort(1)
@CacheName("rptSql")
@RestController
@RequestMapping("/rptSql")
@Slf4j
public class RptSqlController extends AbstractSystemController<RptSql, RptSqlService, RptSqlSearchForm, RptSqlUpdateForm> {
}