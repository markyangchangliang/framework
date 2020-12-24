package com.markyang.framework.gateway.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Swagger文档资源配置
 *
 * @author yangchangliang
 * @version 1
 */
@Primary
@Component
@AllArgsConstructor
@Slf4j
public class SwaggerDocResourceProvider implements SwaggerResourcesProvider {

    private final RouteLocator routeLocator;

    @Override
    public List<SwaggerResource> get() {
        // 获取所有 router 即服务
        List<Route> routes = this.routeLocator.getRoutes();
        log.info("服务数量：{}", routes.size());
        return routes.parallelStream()
            .map(route -> this.getSwaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs")))
            .collect(Collectors.toList());
    }

    /**
     * 获取Swagger资源
     * @param name 服务名称
     * @param location 文档路径
     * @return 资源对象
     */
    private SwaggerResource getSwaggerResource(String name, String location) {
        log.info("服务：{}，Api文档路径：{}", name, location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
