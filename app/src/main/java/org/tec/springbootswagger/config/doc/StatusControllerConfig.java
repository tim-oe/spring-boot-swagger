package org.tec.springbootswagger.config.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tec.springbootswagger.controller.StatusController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class StatusControllerConfig {
    @Bean
    public Docket swaggerStatusApi10() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("status-api-1.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage(StatusController.class.getName()))
                .paths(regex("/status"))
                .build()
                .apiInfo(new ApiInfoBuilder().version("1.0").title("Status API").description("Documentation Status API v1.0").build());
    }
}
