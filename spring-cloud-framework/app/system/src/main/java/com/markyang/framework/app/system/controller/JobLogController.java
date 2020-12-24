package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.JobLogSearchForm;
import com.markyang.framework.app.system.form.update.JobLogUpdateForm;
import com.markyang.framework.app.system.service.JobLogService;
import com.markyang.framework.pojo.entity.system.JobLog;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务日志(JobLog)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "定时任务日志控制器")
@ApiSort(1)
@CacheName("jobLog")
@RestController
@RequestMapping("/jobLog")
@AllArgsConstructor
@Slf4j
public class JobLogController extends AbstractSystemController<JobLog, JobLogService, JobLogSearchForm, JobLogUpdateForm> {

}