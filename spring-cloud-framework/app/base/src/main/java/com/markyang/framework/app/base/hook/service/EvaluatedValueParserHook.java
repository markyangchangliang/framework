package com.markyang.framework.app.base.hook.service;

import com.markyang.framework.app.base.core.FrameworkExpressionEvaluator;
import com.markyang.framework.pojo.entity.support.EvaluatedValue;
import com.markyang.framework.service.core.service.support.HookContext;
import com.markyang.framework.service.core.service.support.ServiceHookAdapter;
import com.markyang.framework.util.ReflectionOperationUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * 解析值注解解析钩子
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@AllArgsConstructor
public class EvaluatedValueParserHook extends ServiceHookAdapter {

    private final FrameworkExpressionEvaluator expressionEvaluator;

    /**
     * 查询之后
     *
     * @param object  实体类
     * @param context 钩子上下文对象
     */
    @Override
    public void postGet(Object object, HookContext context) {
        Class<?> clazz = object.getClass();
        List<Field> fields = ReflectionOperationUtils.getAnnotatedFields(clazz, EvaluatedValue.class);
        for (Field field : fields) {
            String value = field.getAnnotation(EvaluatedValue.class).value();
            if (StringUtils.isNotBlank(value)) {
                // 解析表达式
                Object evaluatedValue = this.expressionEvaluator.evaluate(value, object, clazz, field.getType(), field);
                if (Objects.nonNull(evaluatedValue)) {
                    // 设置解析后的值
                    ReflectionOperationUtils.setFieldValue(field, object, evaluatedValue);
                }
            }
        }
    }

    /**
     * 添加之前
     *
     * @param object  实体类
     * @param context 钩子上下文对象
     */
    @Override
    public void preAdd(Object object, HookContext context) {
        this.postGet(object, context);
    }

    /**
     * 更新之前
     *
     * @param object  实体类
     * @param context 钩子上下文对象
     */
    @Override
    public void preUpdate(Object object, HookContext context) {
        this.postGet(object, context);
    }
}
