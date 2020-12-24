package com.markyang.framework.app.base.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Map;
import java.util.Objects;

/**
 * 远程调用接口加载
 *
 * @author yangchangliang
 * @version 1
 */
public class FrameworkClientLoader implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(FrameworkClientScan.class);
        beans.keySet().forEach(beanName -> {
            FrameworkClientScan annotation = applicationContext.findAnnotationOnBean(beanName, FrameworkClientScan.class);
            if (Objects.nonNull(annotation)) {
                this.processClientClassScan(applicationContext, annotation);
            }
        });
    }

    public void processClientClassScan(ApplicationContext applicationContext, FrameworkClientScan annotation) {
        String[] packages = annotation.value();
        for (String p : packages) {

        }
    }
}
