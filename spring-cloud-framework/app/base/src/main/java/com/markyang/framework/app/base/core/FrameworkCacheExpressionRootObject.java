package com.markyang.framework.app.base.core;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * 框架表达式解析使用到的根对象
 *
 * @author yangchangliang
 * @version 1
 */
@Data
public class FrameworkCacheExpressionRootObject {

    private final Method method;

    private final Object[] args;

    private final Object target;

    private final Class<?> targetClass;
}
