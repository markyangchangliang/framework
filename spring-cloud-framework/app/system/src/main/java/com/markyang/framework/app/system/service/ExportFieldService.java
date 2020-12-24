package com.markyang.framework.app.system.service;

import com.markyang.framework.app.system.form.search.ExportFieldSearchForm;
import com.markyang.framework.service.core.service.SearchableService;
import com.markyang.framework.pojo.entity.system.ExportField;

/**
 * 导出数据字段(ExportField)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
public interface ExportFieldService extends SearchableService<ExportField, ExportFieldSearchForm> {
}