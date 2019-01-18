package org.tec.springbootswagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@PropertySources({
        @PropertySource(value="file:${user.home}/.application.properties", ignoreResourceNotFound = true)
})
public class SpringbootSwaggerApplication {
    /**
     * spring boot application entry point
     * @param args command line args if any
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringbootSwaggerApplication.class, args);
    }
}