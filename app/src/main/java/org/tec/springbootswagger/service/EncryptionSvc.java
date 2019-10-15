package org.tec.springbootswagger.service;

public interface EncryptionSvc {

    /**
     * hash password
     * @param password he unencrypted password
     * @return the hash
     */
    String hashPassword(String password);

    /**
     * test if password matches hashed value
     * @param rawPassword the unencrypted password
     * @param encodedPassword
     * @return
     */
    boolean matches(String rawPassword, String encodedPassword);
}
