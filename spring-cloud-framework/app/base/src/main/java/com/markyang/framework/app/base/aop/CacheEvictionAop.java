package com.markyang.framework.app.base.aop;

import com.markyang.framework.app.base.annotation.CacheClean;
import com.markyang.framework.app.base.exception.CacheCleaningException;
import com.markyang.framework.app.base.core.FrameworkExpressionEvaluator;
import com.markyang.framework.util.BuilderUtils;
import com.markyang.framework.util.RedisUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 清除缓存AOP
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@Aspect
@Slf4j
@AllArgsConstructor
public class CacheEvictionAop {

    private final FrameworkExpressionEvaluator expressionEvaluator;

    @Pointcut("execution(* com.zxrj.framework.app..*.*(..)) " +
        "&& @annotation(com.markyang.framework.app.base.annotation.CacheClean)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object cleanCache(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!(joinPoint.getSignature() instanceof MethodSignature)) {
            // 不是方法代理，抛出错误
            throw new CacheCleaningException("只能并发控制代理的方法");
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        CacheClean annotation = method.getAnnotation(CacheClean.class);
        // 执行方法
        try {
            Object result = joinPoint.proceed();
            this.handleCleanCache(method, joinPoint.getArgs(), joinPoint.getTarget(), annotation, result, false);
            return result;
        } catch (Throwable throwable) {
            // 执行出现错误
            this.handleCleanCache(method, joinPoint.getArgs(), joinPoint.getTarget(), annotation, throwable, true);
            // 继续抛出异常
            throw throwable;
        }
    }

    /**
     * 清理缓存
     * @param method 方法对象
     * @param args 参数
     * @param target 目标对象
     * @param annotation 注解信息
     * @param result 结果对象
     * @param occurException 是否发生异常
     */
    private void handleCleanCache(Method method, Object[] args, Object target, CacheClean annotation, Object result, boolean occurException) {
        String[] cacheNames = annotation.cacheNames();
        if (StringUtils.isAnyBlank(cacheNames)) {
            log.warn("需清除的缓存命名空间必须都不能为空：{}", Arrays.toString(cacheNames));
            return;
        }
        // 可以清除缓存了
        Boolean condition = Boolean.FALSE;
        if (StringUtils.isNotBlank(annotation.cleanUnless())) {
            condition = expressionEvaluator.evaluate("", method, args, target, target.getClass(), Boolean.class, BuilderUtils.<String, Object>newHashMapBuilder().put("result", result).build());
        }
        if (occurException) {
            // 发生异常
            if (annotation.continueCleanIfThrowException() && !BooleanUtils.isTrue(condition)) {
                this.doClean(cacheNames);
            }
        } else {
            // 未发生异常
            if (!BooleanUtils.isTrue(condition)) {
                this.doClean(cacheNames);
            }
        }
    }

    /**
     * 清理缓存
     * @param cacheNames 缓存命名空间
     */
    private void doClean(String[] cacheNames) {
        Arrays.stream(cacheNames)
            .parallel()
            .forEach(RedisUtils::removeCache);
    }
}
