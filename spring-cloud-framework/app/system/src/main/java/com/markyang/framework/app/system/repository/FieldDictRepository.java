package com.markyang.framework.app.system.repository;

import com.markyang.framework.pojo.common.support.ItemEntry;
import com.markyang.framework.pojo.entity.system.FieldDict;
import com.markyang.framework.service.core.repository.FrameworkRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据字典(FieldDict)仓库类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:16:18
 */
public interface FieldDictRepository extends FrameworkRepository<FieldDict> {

    /**
     * 获取所有的表
     * @param appId 应用ID
     * @return 表
     */
    List<ItemEntry> getAllTables(@Param("appId") String appId);

    /**
     * 获取所有的字段
     * @param appId 应用ID
     * @param tableName 表名
     * @return 字段
     */
    List<ItemEntry> getAllTableFields(@Param("appId") String appId, @Param("tableName") String tableName);
}