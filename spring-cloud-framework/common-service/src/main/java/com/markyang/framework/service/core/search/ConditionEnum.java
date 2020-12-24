package com.markyang.framework.service.core.search;

import com.markyang.framework.service.core.search.concrete.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 搜索条件枚举
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/23 2:46 下午 星期一
 */
@Getter
@AllArgsConstructor
public enum ConditionEnum {

    /**
     * 等于
     */
    EQUAL(EqualConditionStrategy.class),
    /**
     * 不等于
     */
    NOT_EQUAL(NotEqualConditionStrategy.class),
    /**
     * 大于
     */
    GREATER_THAN(GreaterThanConditionStrategy.class),
    /**
     * 大于等于
     */
    GREATER_THAN_OR_EQUAL(GreaterThanOrEqualConditionStrategy.class),
    /**
     * 小于
     */
    LESS_THAN(LessThanConditionStrategy.class),
    /**
     * 小于等于
     */
    LESS_THAN_OR_EQUAL(LessThanOrEqualConditionStrategy.class),
    /**
     * 模糊匹配
     */
    LIKE(LikeConditionStrategy.class),
    /**
     * IN匹配
     */
    IN(InConditionStrategy.class);
    /**
     * 搜索策略类
     */
    private final Class<? extends ConditionStrategy> strategy;
}
