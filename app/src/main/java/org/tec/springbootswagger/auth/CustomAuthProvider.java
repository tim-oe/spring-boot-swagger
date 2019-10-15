package org.tec.springbootswagger.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.tec.springbootswagger.entity.PersonEntity;
import org.tec.springbootswagger.repository.PersonRepository;
import org.tec.springbootswagger.service.EncryptionSvc;

import java.util.ArrayList;

/**
 * TODO add roles
 */
@Slf4j
@Component
public class CustomAuthProvider implements AuthenticationProvider {

    @Autowired
    protected transient PersonRepository personRepository;

    @Autowired
    protected transient EncryptionSvc encryptionSvc;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = (authentication.getCredentials() != null) ? authentication.getCredentials().toString() : null;

        PersonEntity pe = personRepository.findByEmail(name);
        if(pe != null && encryptionSvc.matches(password, pe.getHash())) {
            return new UsernamePasswordAuthenticationToken(name, encryptionSvc.hashPassword(password), new ArrayList<>());
        } else{
            log.warn("failed to Auth " + name + "@" + password);
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
