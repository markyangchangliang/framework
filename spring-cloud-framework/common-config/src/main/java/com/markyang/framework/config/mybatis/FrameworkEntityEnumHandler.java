package com.markyang.framework.config.mybatis;

import com.markyang.framework.pojo.common.support.FrameworkEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 框架枚举类处理
 *
 * @author yangchangliang
 * @version 1
 */
public class FrameworkEntityEnumHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private final Class<E> type;

    public FrameworkEntityEnumHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        if (!FrameworkEnum.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException("Type argument must implement FrameworkEnum interface");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, ((FrameworkEnum) parameter).getValue());
    }

    /**
     * @param rs 结果集
     * @param columnName Colunm name, when configuration <code>useColumnLabel</code> is <code>false</code>
     */
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return FrameworkEnum.getEnum(this.type, rs.getString(columnName)).orElse(null);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return FrameworkEnum.getEnum(this.type, rs.getString(columnIndex)).orElse(null);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return FrameworkEnum.getEnum(this.type, cs.getString(columnIndex)).orElse(null);
    }
}
