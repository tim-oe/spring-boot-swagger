package org.tec.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={AuthApplication.class})
public class AuthApplicationTest {

    @Autowired
    AuthApplication authApplication;

    @Test
    public void testInit() {
        assertNotNull(authApplication);
    }
}