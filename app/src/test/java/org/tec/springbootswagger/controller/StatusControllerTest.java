package org.tec.springbootswagger.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.springbootswagger.SpringbootSwaggerApplication;
import org.tec.springbootswagger.model.Response;
import org.tec.springbootswagger.model.Status;
import org.tec.springbootswagger.test.config.TestConfig;
import org.tec.springbootswagger.test.controller.ControllerTestUtils;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={SpringbootSwaggerApplication.class, TestConfig.class})
public class StatusControllerTest {
    @Autowired
    private transient ControllerTestUtils controllerTestUtils;

    @BeforeEach
    public void setUp() {
        controllerTestUtils.init(this);
    }

    @Test
    public void get() {
        Response<Status> response = controllerTestUtils.get(StatusController.PATH, new TypeReference<Response<Status>>() {});

        Assertions.assertNotNull(response.getData());
    }
}
