package org.tec.springbootswagger.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.tec.springbootswagger.service.EncryptionSvc;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class EncryptionSvcImpl implements EncryptionSvc {
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 512;

    protected transient BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        try {
            passwordEncoder = new BCryptPasswordEncoder(StringUtils.EMPTY, ITERATIONS, KEY_LENGTH);
        } catch (Exception e) {
            throw new RuntimeException("Exception encountered in hashPassword()", e);
        }
    }

    @Override
    public String hashPassword (String password) {
        try {
            return passwordEncoder.encode(password);
        } catch (Exception e) {
            throw new RuntimeException("Exception encountered in hashPassword()", e);
        }
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}