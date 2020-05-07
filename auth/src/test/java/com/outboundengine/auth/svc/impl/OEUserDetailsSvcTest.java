package org.tec.auth.svc.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.auth.AuthApplication;
import org.tec.auth.svc.OEUserDetailsSvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={AuthApplication.class})
public class OEUserDetailsSvcTest {

    @Autowired
    OEUserDetailsSvc oeUserDetailsSvc;

    @Test
    public void testGood() {
        UserDetails actual = oeUserDetailsSvc.loadUserByUsername("demo@outboundengine.com");

        assertNotNull(actual);
    }

    @Test
    public void testDisabled() {
        try {
            UserDetails actual = oeUserDetailsSvc.loadUserByUsername("elizabeth_mitchell@featurechat.com");
            fail("should have thrown UsernameNotFoundException");
        } catch (UsernameNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testBad() {
        try {
            UserDetails actual = oeUserDetailsSvc.loadUserByUsername("nobody@newhere.net");
            fail("should have thrown UsernameNotFoundException");
        } catch (UsernameNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
