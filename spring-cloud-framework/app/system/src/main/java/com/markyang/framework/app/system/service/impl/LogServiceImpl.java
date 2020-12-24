package com.markyang.framework.app.system.service.impl;

import com.markyang.framework.app.system.form.search.LogSearchForm;
import com.markyang.framework.app.system.service.LogService;
import com.markyang.framework.app.system.repository.LogRepository;
import com.markyang.framework.pojo.entity.system.Log;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统日志(Log)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
public class LogServiceImpl extends AbstractSearchableServiceImpl<Log, LogRepository, LogSearchForm> implements LogService {

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public Log create() {
        return Log.builder().build();
    }
}