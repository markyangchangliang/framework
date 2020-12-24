package com.markyang.framework.eureka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 注册实例状态事件处理
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@Slf4j
public class EurekaInstanceStatusListener {

    @EventListener
    public void listenInstanceCanceledEvent(EurekaInstanceCanceledEvent eurekaInstanceCanceledEvent) {
        log.error("服务：{}（{}）下线了", eurekaInstanceCanceledEvent.getAppName(), eurekaInstanceCanceledEvent.getServerId());
    }

    @EventListener
    public void listenInstanceRegisteredEvent(EurekaInstanceRegisteredEvent eurekaInstanceRegisteredEvent) {
        log.info("服务：{}（{}）注册了", eurekaInstanceRegisteredEvent.getInstanceInfo().getAppName(), eurekaInstanceRegisteredEvent.getInstanceInfo().getInstanceId());
    }

    @EventListener
    public void listenInstanceRenewedEvent(EurekaInstanceRenewedEvent eurekaInstanceRenewedEvent) {
        log.info("服务：{}（{}）续约了", eurekaInstanceRenewedEvent.getAppName(), eurekaInstanceRenewedEvent.getServerId());
    }

    @EventListener
    public void listenRegistryAvailableEvent(EurekaRegistryAvailableEvent eurekaRegistryAvailableEvent) {
        log.info("服务注册中心启动");
    }

    @EventListener
    public void listenServerStartedEvent(EurekaServerStartedEvent eurekaServerStartedEvent) {
        log.info("Eureka 服务端启动");
    }
}
