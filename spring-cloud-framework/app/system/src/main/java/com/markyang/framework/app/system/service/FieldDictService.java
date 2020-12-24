package com.markyang.framework.app.system.service;

import com.markyang.framework.app.system.form.search.FieldDictSearchForm;
import com.markyang.framework.service.core.service.SearchableService;
import com.markyang.framework.pojo.common.support.ItemEntry;
import com.markyang.framework.pojo.dto.system.DictDto;
import com.markyang.framework.pojo.entity.system.FieldDict;

import java.util.List;
import java.util.Map;

/**
 * 数据字典(FieldDict)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
public interface FieldDictService extends SearchableService<FieldDict, FieldDictSearchForm> {

    /**
     * 获取表列表
     *
     * @param appId     应用ID
     * @return 表名列表
     */
    List<ItemEntry> getAllTables(String appId);

    /**
     * 获取表字段列表
     *
     * @param appId     应用ID
     * @param tableName 表名
     * @return 表名列表
     */
    List<ItemEntry> getAllTableFields(String appId, String tableName);

    /**
     * 获取表中所有的数据字典字段
     *
     * @param appId     应用ID
     * @param tableName 表名
     * @return 字典
     */
    Map<String, List<DictDto>> getAllFieldDict(String appId, String tableName);

    /**
     * 获取表中某一字段的字典
     *
     * @param appId     应用ID
     * @param tableName 表名
     * @param fieldName 字段名
     * @return 字典
     */
    List<DictDto> getFieldDict(String appId, String tableName, String fieldName);


    /**
     * 获取表列表
     *
     * @param appId      应用ID
     * @param tableName  表名
     * @param fieldName  字段名称
     * @param fieldValue 字典值
     * @param fieldMean  字典值含义
     * @return 表名列表
     */
    List<FieldDict> getGeneratedInsertSql(String appId, String tableName, String fieldName, String fieldValue, String fieldMean);
}