package com.markyang.framework.config.mybatis;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;

/**
 * 根据ID判断是否存在
 *
 * @author yangchangliang
 * @version 1
 */
public class ExistsById extends AbstractMethod {
    /**
     * 注入自定义 MappedStatement
     *
     * @param mapperClass mapper 接口
     * @param modelClass  mapper 泛型
     * @param tableInfo   数据库表反射信息
     * @return MappedStatement
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = String.format("SELECT 1 FROM %s WHERE %s=#{%s} %s LIMIT 1\n",
            tableInfo.getTableName(),
            tableInfo.getKeyColumn(),
            tableInfo.getKeyProperty(),
            tableInfo.getLogicDeleteSql(true, true));
        SqlSource sqlSource = new RawSqlSource(configuration, sql, Object.class);
        return this.addSelectMappedStatementForOther(mapperClass, this.getMethod(null), sqlSource, Boolean.class);
    }

    /**
     * 获取自定义方法名，未设置采用默认方法名
     * https://gitee.com/baomidou/mybatis-plus/pulls/88
     *
     * @param sqlMethod sqlMethod
     * @return method
     * @author 义陆无忧
     */
    @Override
    public String getMethod(SqlMethod sqlMethod) {
        return "existsById";
    }
}
