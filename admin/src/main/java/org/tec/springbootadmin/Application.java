package org.tec.springbootadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public abstract class Application {

    /**
     * spring boot application entry point
     *
     * @param args command line args if any
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}