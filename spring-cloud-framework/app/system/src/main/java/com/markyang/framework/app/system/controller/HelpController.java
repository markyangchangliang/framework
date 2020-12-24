package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.HelpSearchForm;
import com.markyang.framework.app.system.form.update.HelpUpdateForm;
import com.markyang.framework.app.system.service.HelpService;
import com.markyang.framework.pojo.entity.system.Help;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 帮助文件(Help)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "帮助文件控制器")
@ApiSort(1)
@CacheName("help")
@RestController
@RequestMapping("/help")
@AllArgsConstructor
@Slf4j
public class HelpController extends AbstractSystemController<Help, HelpService, HelpSearchForm, HelpUpdateForm> {

}