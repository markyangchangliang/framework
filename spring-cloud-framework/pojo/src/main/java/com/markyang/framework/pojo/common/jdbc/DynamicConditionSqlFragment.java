package com.markyang.framework.pojo.common.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 动态sql语句
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/10 5:00 下午 星期日
 */
@Data
@AllArgsConstructor
public class DynamicConditionSqlFragment {

    /**
     * SQL片段
     */
    private String sqlFragment;

    /**
     * 参数列表
     */
    private List<Object> arguments;

    /**
     * 将参数转为数组
     * @return 参数数组
     */
    public Object[] getArgumentArray() {
        return this.arguments.toArray(new Object[0]);
    }
}
