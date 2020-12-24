package com.markyang.framework.service.common.repository;

import com.markyang.framework.pojo.dto.system.DictDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据字典翻译仓库类
 *
 * @author yangchangliang
 * @version 1
 */
public interface FieldDictTranslatorRepository {

    /**
     * 获取表中所有数据字典
     * @param appId 应用ID
     * @param tableName 表名
     * @return 字典结果
     */
    List<DictDto> getAllFieldDict(@Param("appId") String appId, @Param("tableName") String tableName, @Param("filterDisplay") boolean filterDisplay);

    /**
     * 获取表中单字段所有字典
     * @param appId 应用ID
     * @param tableName 表名
     * @param fieldName 字段名
     * @return 字典结果
     */
    List<DictDto> getFieldDict(@Param("appId") String appId, @Param("tableName") String tableName, @Param("fieldName") String fieldName, @Param("filterDisplay") boolean filterDisplay);
}
