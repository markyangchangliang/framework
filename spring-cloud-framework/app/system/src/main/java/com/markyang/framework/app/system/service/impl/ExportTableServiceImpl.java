package com.markyang.framework.app.system.service.impl;

import com.markyang.framework.app.system.form.search.ExportTableSearchForm;
import com.markyang.framework.app.system.service.ExportTableService;
import com.markyang.framework.app.system.repository.ExportTableRepository;
import com.markyang.framework.pojo.entity.system.ExportTable;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 导出数据表名(ExportTable)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
public class ExportTableServiceImpl extends AbstractSearchableServiceImpl<ExportTable, ExportTableRepository, ExportTableSearchForm> implements ExportTableService {

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public ExportTable create() {
        return ExportTable.builder().build();
    }
}