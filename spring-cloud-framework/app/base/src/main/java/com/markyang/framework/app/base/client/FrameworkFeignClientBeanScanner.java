package com.markyang.framework.app.base.client;

import com.markyang.framework.pojo.common.client.FrameworkFeignClient;
import com.markyang.framework.util.TypeCastUtils;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Objects;
import java.util.Set;

/**
 * Client扫描器
 *
 * @author yangchangliang
 * @version 1
 */
public class FrameworkFeignClientBeanScanner extends ClassPathBeanDefinitionScanner {
    private final ApplicationContext applicationContext;
    /**
     * Create a new {@code ClassPathBeanDefinitionScanner} for the given bean factory.
     *
     * @param registry the {@code BeanFactory} to load bean definitions into, in the form
     *                 of a {@code BeanDefinitionRegistry}
     */
    public FrameworkFeignClientBeanScanner(BeanDefinitionRegistry registry, ApplicationContext applicationContext) {
        super(registry);
        this.applicationContext = applicationContext;
    }

    /**
     * Perform a scan within the specified base packages,
     * returning the registered bean definitions.
     * <p>This method does <i>not</i> register an annotation config processor
     * but rather leaves this up to the caller.
     *
     * @param basePackages the packages to check for annotated classes
     * @return set of beans registered if any for tooling registration purposes (never {@code null})
     */
    @NotNull
    @SneakyThrows
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> holders = super.doScan(basePackages);
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : holders) {
            definition = TypeCastUtils.cast(holder.getBeanDefinition());
            Class<?> clazz = definition.resolveBeanClass(null);
            definition.getConstructorArgumentValues().addGenericArgumentValue(Objects.requireNonNull(clazz));
            definition.getConstructorArgumentValues().addGenericArgumentValue(clazz.getAnnotation(FrameworkFeignClient.class));
            definition.getConstructorArgumentValues().addGenericArgumentValue(this.applicationContext);
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_NO);
            definition.setBeanClass(FrameworkFeignClientFactoryBean.class);
        }
        return holders;
    }

    /**
     * Determine whether the given bean definition qualifies as candidate.
     * <p>The default implementation checks whether the class is not an interface
     * and not dependent on an enclosing class.
     * <p>Can be overridden in subclasses.
     *
     * @param beanDefinition the bean definition to check
     * @return whether the bean definition qualifies as a candidate component
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent() && beanDefinition.getMetadata().hasAnnotation(FrameworkFeignClient.class.getName());
    }
}
