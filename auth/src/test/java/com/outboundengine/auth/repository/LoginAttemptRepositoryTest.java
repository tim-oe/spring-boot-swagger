package org.tec.auth.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.auth.AuthApplication;
import org.tec.auth.entity.LoginAttempt;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={AuthApplication.class})
public class LoginAttemptRepositoryTest {

    @Autowired
    LoginAttemptRepository loginAttemptRepository;

    public void test(){
        String userName = "test.me" +System.currentTimeMillis();

        final LoginAttempt la = new LoginAttempt();
        la.setUserName(userName);
        la.setIpAddress("127.0.0.1");
        loginAttemptRepository.save(la);

        assertEquals(1, loginAttemptRepository.countAttempts(userName, 1));
    }
}
