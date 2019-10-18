package org.tec.springbootswagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.tec.springbootswagger.config"})
public class SpringbootSwaggerApplication {
    /**
     * spring boot application entry point
     * @param args command line args if any
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringbootSwaggerApplication.class, args);
    }
}