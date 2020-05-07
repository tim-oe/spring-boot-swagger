package org.tec.auth.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.auth.AuthApplication;
import org.tec.auth.repository.OAuthUserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={AuthApplication.class})
public class OAuthUserTest {

    @Autowired
    OAuthUserRepository oAuthUserRepository;


    @Test
    public void testCopy() {
        Optional<OAuthUser> oAuthUser = oAuthUserRepository.findByUsername("demo@outboundengine.com");

        assertTrue(oAuthUser.isPresent());

        OAuthUser expected = oAuthUser.get();
        expected.setEnhancedPwd("blah");

        OAuthUser actual = new OAuthUser(expected);

        assertNull(actual.getPassword());
        assertNull(actual.getEnhancedPwd());

        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPermissionLevel(), actual.getPermissionLevel());
        assertEquals(expected.getAuthorities(), actual.getAuthorities());

        expected.getAuthorities().clear();

        assertFalse(actual.getAuthorities().isEmpty());
    }
}
