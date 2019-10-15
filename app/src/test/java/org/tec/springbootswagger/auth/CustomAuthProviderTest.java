package org.tec.springbootswagger.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.springbootswagger.SpringbootSwaggerApplication;
import org.tec.springbootswagger.entity.PersonEntity;
import org.tec.springbootswagger.repository.PersonRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= SpringbootSwaggerApplication.class)
public class CustomAuthProviderTest {

    @Autowired
    protected transient CustomAuthProvider customAuthProvider;

    @Autowired
    protected transient PersonRepository personRepository;

    @Test
    public void test() {
        for(int cnt = 1; cnt < 4; cnt++) {
            PersonEntity pe = personRepository.findByEmail("user" + cnt + "@example.net");
            Assertions.assertNotNull(pe);

            Authentication expected = Mockito.mock(Authentication.class);

            BDDMockito.given(expected.getName()).willReturn(pe.getEmail());
            BDDMockito.given(expected.getCredentials()).willReturn("user" + cnt);

            Authentication actual = customAuthProvider.authenticate(expected);

            Assertions.assertNotNull(actual);
        }
    }
}
