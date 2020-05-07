package org.tec.auth.svc;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.tec.auth.entity.OAuthUser;

import javax.servlet.http.HttpServletRequest;

/**
 * this is needed to allow for transactional processing
 */
public interface OEAuthenticationSvc extends AuthenticationProvider, AuthenticationManager {

    //request attribute key
    String LOGGED_BAD_KEY = "login_attempt_processed";

    /**
     * expose processing for testing
     * @param oAuthUser the user information
     * @param password the password
     * @param request the current request object
     * @return the auth record
     * @throws BadCredentialsException
     */
    Authentication getAuthentication(final OAuthUser oAuthUser, final String password, HttpServletRequest request) throws BadCredentialsException;

    /**
     * sets the new hash for the given user, to help isolate the transaction
     * @param oAuthUser the user details data to us for authentication
     * @param hash the hashed password
     */
    void setEnanchedPwd(OAuthUser oAuthUser, String hash);

}
