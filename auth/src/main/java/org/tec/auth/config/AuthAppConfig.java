package org.tec.auth.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthAppConfig {

    @Bean(name = "oeObjectMapper")
    public ObjectMapper objectMapper() {
        final ObjectMapper om = new ObjectMapper();

        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCaseStrategy.SNAKE_CASE);

        //https://github.com/FasterXML/jackson-modules-java8
        om.registerModule(new Jdk8Module());
        om.registerModule(new ParameterNamesModule());
        om.registerModule(new JavaTimeModule());

        return om;
    }
}