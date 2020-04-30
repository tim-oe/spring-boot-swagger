package org.tec.springbootswagger.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.tec.springbootswagger.entity.PersonEntity;
import org.tec.springbootswagger.repository.PersonRepository;
import org.tec.springbootswagger.service.EncryptionSvc;

import java.util.ArrayList;
import java.util.Optional;

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
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String password = (authentication.getCredentials() == null) ? "" : authentication.getCredentials().toString();

        final Optional<PersonEntity> pe = personRepository.findByEmail(name);
        if(pe.isPresent() && encryptionSvc.matches(password, pe.get().getHash())) {
            return new UsernamePasswordAuthenticationToken(name, encryptionSvc.hashPassword(password), new ArrayList<>());
        } else{
            log.warn("failed to Auth " + name + "@" + password);
            throw new BadCredentialsException("failed to Auth " + name + "@" + password);
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
