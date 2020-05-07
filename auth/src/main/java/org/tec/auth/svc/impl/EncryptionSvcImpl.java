package org.tec.auth.svc.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.tec.auth.svc.EncryptionSvc;

/**
 * https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html
 */
@Slf4j
@Service
public class EncryptionSvcImpl implements EncryptionSvc {

    protected final transient BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String hashPassword(final String password) {
        try {
            return passwordEncoder.encode(password);
        } catch (Exception e) {
            throw new RuntimeException("Exception encountered in hashPassword()", e);
        }
    }

    @Override
    public boolean matches(final String rawPassword, final String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}