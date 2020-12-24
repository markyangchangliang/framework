package com.markyang.framework.app.base.client;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 远程调用Client扫描注解
 *
 * @author yangchangliang
 * @version 1
 */
@Import(FrameworkClientScannerConfigurerRegister.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FrameworkClientScan {

    /**
     * 扫描路径
     * @return 路径
     */
    String[] value();
}
