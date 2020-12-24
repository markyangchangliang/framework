package com.markyang.framework.app.system.service;

import com.markyang.framework.app.system.form.search.FileSearchForm;
import com.markyang.framework.service.core.service.SearchableService;
import com.markyang.framework.pojo.entity.system.File;

/**
 * 文件上传(File)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
public interface FileService extends SearchableService<File, FileSearchForm> {
}