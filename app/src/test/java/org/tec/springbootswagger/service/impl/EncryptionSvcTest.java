package org.tec.springbootswagger.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.springbootswagger.SpringbootSwaggerApplication;
import org.tec.springbootswagger.service.EncryptionSvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= SpringbootSwaggerApplication.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class EncryptionSvcTest {

    @Autowired
    protected transient EncryptionSvc encryptionSvc;

    @Test
    public void test() {
        System.out.println(encryptionSvc.hashPassword("user1"));
        System.out.println(encryptionSvc.hashPassword("user2"));
        System.out.println(encryptionSvc.hashPassword("user3"));
        System.out.println(encryptionSvc.hashPassword("owner"));
    }
}
