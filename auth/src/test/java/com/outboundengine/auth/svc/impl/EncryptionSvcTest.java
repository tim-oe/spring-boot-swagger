package org.tec.auth.svc.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.auth.AuthApplication;
import org.tec.auth.svc.EncryptionSvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={AuthApplication.class})
public class EncryptionSvcTest {

    @Autowired
    EncryptionSvc encryptionSvc;

    @Test
    public void test() {
        final String pwd ="this is my password";

        String hash = encryptionSvc.hashPassword(pwd);

        assertNotNull(hash);

        assertTrue(encryptionSvc.matches(pwd, hash));
    }
}
