package com.markyang.framework.app.base.upload.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.markyang.framework.app.base.upload.service.UploadService;
import com.markyang.framework.pojo.web.ResultVo;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 上传控制器
 *
 * @author yangchangliang
 * @version 1
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/uploader")
public class UploadController {

    /**
     * 注入Upload上传服务类
     */
    private final List<UploadService> uploadServices;

    /**
     * 文件上传接口
     * @param type 文件上传类型
     * @param file 文件对象
     * @return 结果对象
     */
    @ApiOperationSupport(order = 1, author = "yangchangliang")
    @ApiOperation(value = "文件上传", notes = "上传文件接口，支持各种文件上传")
    @PostMapping(value = "{type}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVo<?> upload(@PathVariable String type, @RequestParam("file") MultipartFile file) {
        for (UploadService uploadService : this.uploadServices) {
            if (uploadService.support(type)) {
                Object result = uploadService.upload(file);
                return ResultVo.success("上传成功", result);
            }
        }
        return ResultVo.error("不支持的文件上传类型：" + type);
    }
}
