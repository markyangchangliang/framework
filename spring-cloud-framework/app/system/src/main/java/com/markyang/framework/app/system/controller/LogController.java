package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.LogSearchForm;
import com.markyang.framework.app.system.form.update.LogUpdateForm;
import com.markyang.framework.app.system.service.LogService;
import com.markyang.framework.pojo.entity.system.Log;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统日志(Log)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "系统日志控制器")
@ApiSort(1)
@CacheName("log")
@RestController
@RequestMapping("/log")
@AllArgsConstructor
@Slf4j
public class LogController extends AbstractSystemController<Log, LogService, LogSearchForm, LogUpdateForm> {


}