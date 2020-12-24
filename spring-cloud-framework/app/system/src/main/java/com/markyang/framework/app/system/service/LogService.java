package com.markyang.framework.app.system.service;

import com.markyang.framework.app.system.form.search.LogSearchForm;
import com.markyang.framework.service.core.service.SearchableService;
import com.markyang.framework.pojo.entity.system.Log;

/**
 * 系统日志(Log)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
public interface LogService extends SearchableService<Log, LogSearchForm> {
}