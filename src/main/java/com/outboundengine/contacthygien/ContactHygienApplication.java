package com.outboundengine.contacthygien;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@PropertySources({
        @PropertySource(value="file:${user.home}/.OutboundEngine.properties", ignoreResourceNotFound = true)
})
public class ContactHygienApplication {

    /**
     * spring boot application entry point
     * @param args command line args if any
     */
    public static void main(String[] args) {
        SpringApplication.run(ContactHygienApplication.class, args);
    }

}
