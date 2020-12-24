package com.markyang.framework.app.system.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * Swagger文档配置
 *
 * @author yangchangliang
 * @version 1
 */
@EnableKnife4j
@EnableSwagger2
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerDocConfig {

    @Bean("systemApi")
    @Order(value = 1)
    public Docket serviceRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(serviceApiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.markyang.framework.app.system"))
            .paths(PathSelectors.any())
            .build()
            .securityContexts(Lists.newArrayList(securityContext()))
            .securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey()));
    }

    private ApiInfo serviceApiInfo() {
        return new ApiInfoBuilder()
            .title("系统服务Api文档")
            .description("<div style='font-size:14px;color:red;'>系统管理相关的Api文档</div>")
            .termsOfServiceUrl("http://www.markyang.com/")
            .contact(new Contact("yangchangliang", "http://www.markyang.com", "markyang088@163.com"))
            .version("1.0")
            .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "Header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("/.*"))
            .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }
}
