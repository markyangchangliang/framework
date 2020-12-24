package com.markyang.framework.pojo.common.jdbc;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 条件字段值描述符
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/10 4:46 下午 星期日
 */
@Data
public class ConditionSqlFieldValueDescriptor {

    /**
     * 值占位符
     */
    private String valuePlaceholder;

    /**
     * 值
     */
    private List<Object> values = Lists.newArrayList();

    /**
     * 添加值
     * @param value 值
     */
    public void addValue(Object value) {
        this.values.add(value);
    }
}
