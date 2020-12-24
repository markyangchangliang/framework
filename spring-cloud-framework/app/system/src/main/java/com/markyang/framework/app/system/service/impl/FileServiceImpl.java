package com.markyang.framework.app.system.service.impl;

import com.markyang.framework.app.system.form.search.FileSearchForm;
import com.markyang.framework.app.system.service.FileService;
import com.markyang.framework.app.system.repository.FileRepository;
import com.markyang.framework.pojo.entity.system.File;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 文件上传(File)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
public class FileServiceImpl extends AbstractSearchableServiceImpl<File, FileRepository, FileSearchForm> implements FileService {

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public File create() {
        return File.builder().build();
    }
}