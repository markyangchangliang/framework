package com.markyang.framework.util;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.markyang.framework.pojo.common.jdbc.ConditionSqlFieldValueDescriptor;
import com.markyang.framework.pojo.common.jdbc.DynamicConditionSqlField;
import com.markyang.framework.pojo.common.jdbc.DynamicConditionSqlFragment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 数据库操作工具类
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@Slf4j
public final class JdbcOperationUtils {

    private static final Map<Class<?>, BeanPropertyRowMapper<?>> ROW_MAPPER_MAP = new ConcurrentReferenceHashMap<>(128);

    private static JdbcTemplate jdbcTemplate;
    private static NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcOperationUtils(JdbcTemplate jdbcTemplate) {
        JdbcOperationUtils.jdbcTemplate = jdbcTemplate;
        JdbcOperationUtils.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    /**
     * 查询 结果只有一个对象
     * @param sql 查询语句
     * @param clazz 类
     * @param args 参数
     * @param <T> 泛型
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T queryForObject(String sql, Class<T> clazz, Object... args) {
        return jdbcTemplate.queryForObject(sql, (BeanPropertyRowMapper<T>) ROW_MAPPER_MAP.computeIfAbsent(clazz, key -> {
            BeanPropertyRowMapper<?> rowMapper = BeanPropertyRowMapper.newInstance(key);
            ROW_MAPPER_MAP.put(key, rowMapper);
            return rowMapper;
        }), args);
    }

    /**
     * 查询 结果有多个对象
     * @param sql 查询语句
     * @param clazz 类
     * @param args 参数
     * @param <T> 泛型
     * @return 对象List
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> queryForList(String sql, Class<T> clazz, Object... args) {
        return jdbcTemplate.query(sql, (BeanPropertyRowMapper<T>) ROW_MAPPER_MAP.computeIfAbsent(clazz, key -> {
            BeanPropertyRowMapper<?> rowMapper = BeanPropertyRowMapper.newInstance(key);
            ROW_MAPPER_MAP.put(key, BeanPropertyRowMapper.newInstance(key));
            return rowMapper;
        }), args);
    }

    /**
     * 统计数量
     * @param sql 统计SQL
     * @param args 参数
     * @return 数量
     */
    public static Long count(String sql, Object... args) {
        return jdbcTemplate.queryForObject(sql, Long.class, args);
    }

    /**
     * 更新语句
     * @param sql SQL
     * @param args 绑定参数
     * @return 影响行数
     */
    public static int update(String sql, Object... args) {
        return jdbcTemplate.update(sql, args);
    }

    /**
     * 构建分页sql（包含排序）
     * @param page 分页对象
     * @return sql
     */
    public static String buildPaginationSql(Page<?> page) {
        StringBuilder sqlBuilder = new StringBuilder();
        List<OrderItem> orderItems = page.getOrders();
        // 判断是否有分页信息
        if (CollectionUtils.isNotEmpty(orderItems)) {
            sqlBuilder.append(" order by ");
            orderItems.forEach(order -> sqlBuilder.append(order.getColumn()).append(" ").append(order.isAsc() ? "asc" : "desc"));
        }
        // 进行分页sql拼装
        sqlBuilder.append(" limit ").append(page.getCurrent() * page.getSize()).append(",").append(page.getSize());
        return sqlBuilder.toString();
    }

    /**
     * 构建动态SQL
     * @param object 参数对象
     * @return 动态sql对象
     */
    public static DynamicConditionSqlFragment buildDynamicConditionSql(Object object) {
        List<Field> fields = ReflectionOperationUtils.getAnnotatedFields(object.getClass(), DynamicConditionSqlField.class);
        StringBuilder stringBuilder = new StringBuilder("1 = 1");
        List<Object> arguments = Lists.newArrayListWithExpectedSize(32);
        for (Field field : fields) {
            DynamicConditionSqlField annotation = field.getAnnotation(DynamicConditionSqlField.class);
            Optional<ConditionSqlFieldValueDescriptor> descriptorOptional = annotation.translator().getTranslator().translate(object.getClass(), field, annotation.operator(), ReflectionOperationUtils.getFieldValue(field, object).orElse(null));
            if (descriptorOptional.isPresent()) {
                ConditionSqlFieldValueDescriptor descriptor = descriptorOptional.get();
                // 处理字段名字
                stringBuilder.append(" and ").append(StringUtils.isNotBlank(annotation.name()) ? annotation.name() : ConversionUtils.camelToDash(field.getName())).append(" ").append(annotation.operator()).append(" ");
                // 处理字段值
                stringBuilder.append(descriptor.getValuePlaceholder());
                arguments.addAll(descriptor.getValues());
            }
        }
        return new DynamicConditionSqlFragment(stringBuilder.toString(), arguments);
    }
}
