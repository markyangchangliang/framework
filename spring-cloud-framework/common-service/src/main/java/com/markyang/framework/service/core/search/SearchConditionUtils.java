package com.markyang.framework.service.core.search;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.markyang.framework.service.core.form.SearchBaseForm;
import com.markyang.framework.util.ConversionUtils;
import com.markyang.framework.util.ReflectionOperationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 搜索条件构建工具类
 * @author yangchangliang
 */
@Component
@Slf4j
public final class SearchConditionUtils {

    /**
     * 缓存搜索条件策略实例
     */
    private static Map<Class<? extends ConditionStrategy>, ConditionStrategy> strategyMap;

    public SearchConditionUtils(List<ConditionStrategy> conditionStrategies) {
        Assert.isTrue(CollectionUtils.isNotEmpty(conditionStrategies), "没有找到搜索条件策略实现类");
        // 初始化策略
        strategyMap = Maps.newHashMapWithExpectedSize(conditionStrategies.size());
        for (ConditionStrategy conditionStrategy : conditionStrategies) {
            strategyMap.put(conditionStrategy.getClass(), conditionStrategy);
        }
    }

    /**
     * 根据搜索表单，动态构件查询查询条件
     * @param searchForm 搜索表单对象
     * @param queryWrapper 条件构造器
     * @return 条件构造器
     */
    @NonNull
    public static QueryWrapper<?> applySearchCondition(SearchBaseForm searchForm, QueryWrapper<?> queryWrapper) {
        List<Field> fields = ReflectionOperationUtils.getAnnotatedFields(searchForm.getClass(), SearchCondition.class);
        for (Field field : fields) {
            SearchCondition searchCondition = field.getAnnotation(SearchCondition.class);
            // 获取字段的值
            Optional<Object> valueOptional = ReflectionOperationUtils.getFieldValue(field, searchForm);

            if (!valueOptional.isPresent()) {
                // 如果字段值为NULL，那就跳过
                continue;
            }

            ConditionStrategy conditionStrategy = strategyMap.get(searchCondition.strategy().getStrategy());
            // 获取相应的处理策略
            if (Objects.isNull(conditionStrategy)) {
                // 忽略本字段的处理
                continue;
            }
            // 使用策略
            conditionStrategy.apply("`" + ConversionUtils.camelToDash(field.getName()) + "`", valueOptional.get(), queryWrapper);
        }
        return queryWrapper;
    }

}
