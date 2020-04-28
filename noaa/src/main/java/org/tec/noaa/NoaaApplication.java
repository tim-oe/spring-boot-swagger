package org.tec.noaa;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.tec.noaa.service"})
public class NoaaApplication {

	public static final String BEAN_TYPE_KEY = "@Type";

	public static void main(final String[] args) {
		SpringApplication.run(NoaaApplication.class, args);
	}

	@Bean(name = "objectMapper")
	public ObjectMapper objectMapper() {
		return getObjectMapper(false);
	}

	@Bean(name = "typedMapper")
	public ObjectMapper typedObjectMapper() {
		return getObjectMapper(true);
	}

	/**
	 * get an object mapper instance
	 * @param typed whether the instance will check/type field
	 * @return object mapper instance
	 */
	protected ObjectMapper getObjectMapper(final boolean typed) {
		final ObjectMapper om = new ObjectMapper();

		om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		//https://github.com/FasterXML/jackson-modules-java8
		om.registerModule(new Jdk8Module());
		om.registerModule(new ParameterNamesModule());
		om.registerModule(new JavaTimeModule());

		if(typed) {
			//https://fasterxml.github.io/jackson-databind/javadoc/2.8/com/fasterxml/jackson/databind/ObjectMapper.html#enableDefaultTypingAsProperty(com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping,%20java.lang.String)
			om.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_FINAL, BEAN_TYPE_KEY);
		}

		return om;
	}
}