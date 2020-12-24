package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.TaskSearchForm;
import com.markyang.framework.app.system.form.update.TaskUpdateForm;
import com.markyang.framework.app.system.service.TaskService;
import com.markyang.framework.pojo.entity.system.Task;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务(Task)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "定时任务控制器")
@ApiSort(1)
@CacheName("task")
@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController extends AbstractSystemController<Task, TaskService, TaskSearchForm, TaskUpdateForm> {
}