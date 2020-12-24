package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.FileSearchForm;
import com.markyang.framework.app.system.form.update.FileUpdateForm;
import com.markyang.framework.app.system.service.FileService;
import com.markyang.framework.pojo.entity.system.File;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件上传(File)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "文件上传控制器")
@ApiSort(1)
@CacheName("file")
@RestController
@RequestMapping("/file")
@AllArgsConstructor
@Slf4j
public class FileController extends AbstractSystemController<File, FileService, FileSearchForm, FileUpdateForm> {


}