package com.markyang.framework.app.base.client;

import com.markyang.framework.util.TypeCastUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * Client扫描器注册类
 *
 * @author yangchangliang
 * @version 1
 */
public class FrameworkClientScannerConfigurerRegister implements ImportBeanDefinitionRegistrar {

    /**
     * Register bean definitions as necessary based on the given annotation metadata of
     * the importing {@code @Configuration} class.
     * <p>Note that {@link BeanDefinitionRegistryPostProcessor} types may <em>not</em> be
     * registered here, due to lifecycle constraints related to {@code @Configuration}
     * class processing.
     * <p>The default implementation is empty.
     *
     * @param importingClassMetadata annotation metadata of the importing class
     * @param registry               current bean definition registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(FrameworkClientScan.class.getName());
        if (MapUtils.isEmpty(attributes)) {
            return;
        }
        //转换下类型
        String[] packages = TypeCastUtils.cast(attributes.get("value"));
        if (ArrayUtils.isEmpty(packages)) {
            return;
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(FrameworkClientScannerConfigurer.class);
        builder.addConstructorArgValue(packages);
        registry.registerBeanDefinition("frameworkClientScannerConfigurerRegister", builder.getBeanDefinition());
    }
}
