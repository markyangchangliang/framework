package com.markyang.framework.app.base.upload.service.support;

import com.markyang.framework.app.base.exception.FileSystemUploadException;
import com.markyang.framework.app.base.upload.config.FileSystemUploadConfigProperties;
import com.markyang.framework.util.DateTimeUtils;
import com.markyang.framework.util.TypeCastUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * 文件系统上传服务类
 *
 * @author yangchangliang
 * @version 1
 */
@Service
@AllArgsConstructor
@Slf4j
public class FileSystemUploadService extends UploadServiceAdapter {

    private final FileSystemUploadConfigProperties configProperties;

    /**
     * 文件删除逻辑
     *
     * @param key 文件标识
     * @return 删除结果
     */
    @Override
    public boolean doDelete(String key) {
        return FileUtils.deleteQuietly(FileUtils.getFile(this.configProperties.getPath(), key));
    }

    /**
     * 根据文件唯一标识判断文件是否存在
     *
     * @param key 文件唯一标识
     * @return bool
     */
    @Override
    public boolean exists(String key) {
        return FileUtils.getFile(this.configProperties.getPath(), key).exists();
    }

    /**
     * 真正执行上传的逻辑
     *
     * @param file 文件对象
     * @return 上传结果
     */
    @Override
    public Object doUpload(MultipartFile file) {
        if (Objects.isNull(file) || file.isEmpty()) {
            throw new FileSystemUploadException("文件上传异常，需上传的文件不能为空");
        }
        log.info("开始上传文件：{}，原始文件名称：{}，文件大小：{}", file.getName(), file.getOriginalFilename(), file.getSize());
        if (file.getSize() > this.configProperties.getMaxSize()) {
            // 超出限制
            throw new FileSystemUploadException("文件上传失败，需上传的文件[" + file.getSize() + "]超过最大限制[" + this.configProperties.getMaxSize() + "]");
        }
        String filename = DateTimeUtils.toStringFormat(LocalDate.now()) + "/" + UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        File dest = FileUtils.getFile(this.configProperties.getPath(), filename);
        try {
            FileUtils.forceMkdirParent(dest);
        } catch (IOException e) {
            throw new FileSystemUploadException("即将上传的文件[" + dest.getAbsolutePath() + "]父级目录创建失败：" + e.getMessage());
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new FileSystemUploadException("文件[" + dest.getAbsolutePath() + "]传输失败：" + e.getMessage());
        }
        return UploadedFile.of("fs", "/" + filename, this.configProperties.getDomain());
    }

    /**
     * 从上传结果中获取文件Key
     *
     * @param result 上传结果
     * @return 文件key
     */
    @Override
    public String getKey(Object result) {
        UploadedFile uploadedFile = TypeCastUtils.cast(result);
        return uploadedFile.getKey();
    }

    /**
     * 获取文件上传类型名称
     *
     * @return 类型
     */
    @Override
    public String getType() {
        return "fs";
    }
}
