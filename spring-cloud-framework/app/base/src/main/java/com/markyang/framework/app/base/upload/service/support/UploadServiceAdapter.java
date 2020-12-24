package com.markyang.framework.app.base.upload.service.support;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传服务类适配器
 *
 * @author yangchangliang
 * @version 1
 */
public class UploadServiceAdapter extends AbstractUploadService {
    /**
     * 文件删除逻辑
     *
     * @param key 文件标识
     * @return 删除结果
     */
    @Override
    public boolean doDelete(String key) {
        return false;
    }

    /**
     * 获取签名和文件唯一标识
     *
     * @return 文件唯一标识
     */
    @Override
    public Object getSigAndKey() {
        return null;
    }

    /**
     * 根据文件唯一标识判断文件是否存在
     *
     * @param key 文件唯一标识
     * @return bool
     */
    @Override
    public boolean exists(String key) {
        return false;
    }

    /**
     * 执行上传回调
     *
     * @param result 结果
     * @return 返回结果
     */
    @Override
    public Object execCallback(Object result) {
        return null;
    }

    /**
     * 真正执行上传的逻辑
     *
     * @param file 文件对象
     * @return 上传结果
     */
    @Override
    public Object doUpload(MultipartFile file) {
        return null;
    }

    /**
     * 从上传结果中获取文件Key
     *
     * @param result 上传结果
     * @return 文件key
     */
    @Override
    public String getKey(Object result) {
        return null;
    }

    /**
     * 获取文件上传类型名称
     *
     * @return 类型
     */
    @Override
    public String getType() {
        return null;
    }
}
