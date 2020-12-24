package com.markyang.framework.app.base.upload.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件服务类
 *
 * @author yangchangliang
 * @version 1
 */
public interface UploadService {

    /**
     * 获取签名和文件唯一标识
     * @return 文件唯一标识
     */
    Object getSigAndKey();

    /**
     * 根据文件唯一标识判断文件是否存在
     * @param key 文件唯一标识
     * @return bool
     */
    boolean exists(String key);

    /**
     * 执行上传回调
     * @param result 结果
     * @return 返回结果
     */
    Object execCallback(Object result);

    /**
     * 上传文件
     * @param file 文件对象
     * @return 结果
     */
    Object upload(MultipartFile file);

    /**
     * 删除文件
     * @param key 文件标识
     * @return 删除结果
     */
    boolean delete(String key);

    /**
     * 是否支持指定类型的文件上传
     * @param type 上传类型
     * @return bool
     */
    default boolean support(String type) {
        return StringUtils.equals(this.getType(), type);
    }

    /**
     * 获取文件上传类型名称
     * @return 类型
     */
    String getType();
}
