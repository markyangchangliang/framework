package com.markyang.framework.app.system.service.impl;

import com.markyang.framework.app.system.form.search.ExportFieldSearchForm;
import com.markyang.framework.app.system.service.ExportFieldService;
import com.markyang.framework.app.system.repository.ExportFieldRepository;
import com.markyang.framework.pojo.entity.system.ExportField;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 导出数据字段(ExportField)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
public class ExportFieldServiceImpl extends AbstractSearchableServiceImpl<ExportField, ExportFieldRepository, ExportFieldSearchForm> implements ExportFieldService {

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public ExportField create() {
        return ExportField.builder().build();
    }
}