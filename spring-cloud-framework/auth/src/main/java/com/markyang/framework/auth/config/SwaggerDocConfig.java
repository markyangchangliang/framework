package com.markyang.framework.auth.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
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
@EnableKnife4j
@EnableSwagger2
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerDocConfig {

    @Bean("authApi")
    @Order(value = 1)
    public Docket serviceRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(serviceApiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("org.springframework.security.oauth2.provider.endpoint"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo serviceApiInfo(){
        return new ApiInfoBuilder()
            .title("认证服务Api文档")
            .description("<div style='font-size:14px;color:red;'>认证授权相关的Api文档</div>")
            .termsOfServiceUrl("http://www.markyang.com/")
            .contact(new Contact("yangchangliang", "http://www.markyang.com", "markyangt088@163.com"))
            .version("1.0")
            .build();
    }
}
