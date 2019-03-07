package org.tec.springbootadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@EnableAdminServer
@SpringBootApplication
@PropertySources({
        @PropertySource(value="file:${user.home}/.application.properties", ignoreResourceNotFound = true)
})
public class Application {
    /**
     * spring boot application entry point
     *
     * @param args command line args if any
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}