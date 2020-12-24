package com.markyang.framework.app.system.service.impl;

import com.markyang.framework.app.system.form.search.RptSqlSearchForm;
import com.markyang.framework.app.system.service.RptSqlService;
import com.markyang.framework.app.system.repository.RptSqlRepository;
import com.markyang.framework.pojo.entity.system.RptSql;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 报表管理(RptSql)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
public class RptSqlServiceImpl extends AbstractSearchableServiceImpl<RptSql, RptSqlRepository, RptSqlSearchForm> implements RptSqlService {

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public RptSql create() {
        return RptSql.builder().build();
    }
}