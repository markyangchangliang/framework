package com.markyang.framework.app.base.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.MapUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 框架EL表达式解析器
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@RequiredArgsConstructor
public class FrameworkExpressionEvaluator extends CachedExpressionEvaluator {

    private static final Map<CachedExpressionEvaluator.ExpressionKey, Expression> EXPRESSION_CACHE = new ConcurrentHashMap<>(64);

    @NonNull
    private final ApplicationContext applicationContext;

    /**
     * 解析表达式
     * @param expression 表达式
     * @param method 方法对象
     * @param args 方法参数
     * @param target 目标对象
     * @param targetClass 目标类
     * @param desiredType 需要的类型
     * @return 结果
     */
    public <T> T evaluate(String expression, Method method, Object[] args, Object target, Class<?> targetClass, Class<? extends T> desiredType) {
        return this.evaluate(expression, method, args, target, targetClass, desiredType, null);
    }

    /**
     * 解析表达式
     * @param expression 表达式
     * @param method 方法对象
     * @param args 方法参数
     * @param target 目标对象
     * @param targetClass 目标类
     * @param desiredType 需要的类型
     * @param otherVariables 其他变量
     * @return 结果
     */
    public <T> T evaluate(String expression, Method method, Object[] args, Object target, Class<?> targetClass, Class<? extends T> desiredType, Map<String, Object> otherVariables) {
        // 设置根对象
        FrameworkCacheExpressionRootObject rootObject = new FrameworkCacheExpressionRootObject(method, args, target, targetClass);
        MethodBasedEvaluationContext evaluationContext = new MethodBasedEvaluationContext(rootObject, method, args, this.getParameterNameDiscoverer());
        if (MapUtils.isNotEmpty(otherVariables)) {
            for (Map.Entry<String, Object> entry : otherVariables.entrySet()) {
                evaluationContext.setVariable(entry.getKey(), entry.getValue());
            }
        }
        // 设置Bean解析工厂
        evaluationContext.setBeanResolver(new BeanFactoryResolver(this.applicationContext));
        return this.getExpression(EXPRESSION_CACHE,
            new AnnotatedElementKey(method, targetClass), expression)
            .getValue(evaluationContext, desiredType);
    }

    /**
     * 解析表达式
     * @param expression 表达式
     * @param root 根对象
     * @param targetClass 目标类
     * @param desiredType 需要的类型
     * @param annotatedElement 标注实现对象
     * @return 结果
     */
    public <T> T evaluate(String expression, Object root, Class<?> targetClass, Class<? extends T> desiredType, AnnotatedElement annotatedElement) {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext(root);
        // 设置Bean解析工厂
        evaluationContext.setBeanResolver(new BeanFactoryResolver(this.applicationContext));
        return getExpression(EXPRESSION_CACHE, new AnnotatedElementKey(annotatedElement, targetClass), expression).getValue(evaluationContext, desiredType);
    }
}
