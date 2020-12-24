package com.markyang.framework.app.system.repository;

import com.markyang.framework.pojo.dto.system.ColumnInfoDto;
import com.markyang.framework.pojo.dto.system.TableInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据库仓库类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/29 4:28 下午 星期五
 */
public interface DatabaseRepository {

    /**
     * 获取表信息
     * @param databaseName 数据库名称
     * @param tableName 表名
     * @return 表信息
     */
    List<ColumnInfoDto> getTableColumnInfo(@Param("databaseName") String databaseName, @Param("tableName") String tableName);

    /**
     * 获取数据库中所有的表
     * @return 所有的表
     */
    List<TableInfoDto> getDatabaseTableInfos();
}
