package com.markyang.framework.app.base.upload.service.support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上传的文件
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@Builder
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UploadedFile {

    /**
     * 文件类型（指上传类型）
     */
    private String type;
    /**
     * 文件标识
     */
    private String key;
    /**
     * 文件访问域名
     */
    private String domain;

    public static UploadedFile of(String type, String key) {
        return of(type, key, null);
    }
}
