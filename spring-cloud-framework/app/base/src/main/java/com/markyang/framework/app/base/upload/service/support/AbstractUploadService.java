package com.markyang.framework.app.base.upload.service.support;

import com.markyang.framework.app.base.upload.service.UploadService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 抽象的上传文件服务类
 *
 * @author yangchangliang
 * @version 1
 */
public abstract class AbstractUploadService implements UploadService {

    /**
     * 删除文件
     *
     * @param key 文件标识
     * @return 删除结果
     */
    @Override
    public boolean delete(String key) {
        if (!this.exists(key)) {
            return false;
        }
        return this.doDelete(key);
    }

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 结果
     */
    @Override
    public Object upload(MultipartFile file) {
        Object result = this.doUpload(file);
        String key = this.getKey(result);
        return result;
    }

    /**
     * 真正执行上传的逻辑
     * @param file 文件对象
     * @return 上传结果
     */
    public abstract Object doUpload(MultipartFile file);

    /**
     * 从上传结果中获取文件Key
     * @param result 上传结果
     * @return 文件key
     */
    public abstract String getKey(Object result);

    /**
     * 文件删除逻辑
     * @param key 文件标识
     * @return 删除结果
     */
    public abstract boolean doDelete(String key);
}
