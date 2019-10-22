package org.tec.springbootswagger.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.springbootswagger.SpringbootSwaggerApplication;
import org.tec.springbootswagger.entity.PersonEntity;
import org.tec.springbootswagger.model.Response;
import org.tec.springbootswagger.model.dto.PersonDto;
import org.tec.springbootswagger.repository.PersonRepository;
import org.tec.springbootswagger.test.config.TestConfig;
import org.tec.springbootswagger.test.controller.ControllerTestUtils;

import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={SpringbootSwaggerApplication.class, TestConfig.class})
public class PersonControllerTest {

    @Autowired
    private transient PersonRepository personRepository;

    @Autowired
    private transient ControllerTestUtils controllerTestUtils;

    @BeforeEach
    public void setUp() {
        controllerTestUtils.init(this);
    }

    @Test
    public void get() {
        PersonEntity pe = personRepository.findByEmail("user1@example.net");
        Assertions.assertNotNull(pe);

        Map<String, String> headers = controllerTestUtils.getHeadersWithAuth(pe.getEmail(),
                pe.getEmail().replace("@example.net", StringUtils.EMPTY));

        Response<PersonDto> response = controllerTestUtils.get(PersonController.PATH, headers, new TypeReference<Response<PersonDto>>() {});

        Assertions.assertNotNull(response.getData());
    }

    @Test
    public void unauth() {
        Response<PersonDto> response = controllerTestUtils.get(PersonController.PATH, HttpStatus.UNAUTHORIZED, new TypeReference<Response<PersonDto>>() {});

        Assertions.assertNull(response);
    }
}