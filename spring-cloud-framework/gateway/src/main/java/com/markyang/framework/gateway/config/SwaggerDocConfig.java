package com.markyang.framework.gateway.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger文档配置
 *
 * @author yangchangliang
 * @version 1
 */
@EnableSwagger2
@EnableKnife4j
@Configuration
public class SwaggerDocConfig {

    @Bean
    @Order(value = 1)
    public Docket serviceRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(serviceApiInfo());
    }

    private ApiInfo serviceApiInfo(){
        return new ApiInfoBuilder()
            .title("SpringCloud项目Api文档")
            .description("<div style='font-size:14px;color:red;'>Api文档</div>")
            .termsOfServiceUrl("http://www.markyang.com/")
            .contact(new Contact("yangchangliang", "http://www.markyang.com", "markyang088@163.com"))
            .version("1.0")
            .build();
    }
}
