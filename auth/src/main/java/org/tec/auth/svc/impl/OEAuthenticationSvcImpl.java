package org.tec.auth.svc.impl;

import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.tec.auth.entity.LoginAttempt;
import org.tec.auth.entity.OAuthUser;
import org.tec.auth.repository.LoginAttemptRepository;
import org.tec.auth.repository.OAuthUserRepository;
import org.tec.auth.svc.EncryptionSvc;
import org.tec.auth.svc.OEAuthenticationSvc;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * https://medium.com/takeaway-tech/wiring-your-own-authentication-with-springbootoauth2-a636b7420714
 */
@Slf4j
@Service
public class OEAuthenticationSvcImpl implements OEAuthenticationSvc {

    /** remove when sha-1 is removed */
    private static final String[] HEX_MAP = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    @Autowired
    private transient EncryptionSvc encryptionSvc;

    @Autowired
    private transient OAuthUserRepository oAuthUserRepository;

    @Autowired
    private transient LoginAttemptRepository loginAttemptRepository;

    @Autowired
    private transient HttpServletRequest request;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();

        if(loginAttemptRepository.countAttempts(name, LoginAttemptRepository.MAX_MINUTES) >= LoginAttemptRepository.MAX_ATTEMPTS){
            throw new LockedException(name + " is locked to due to too many failed attempts");
        }

        final Optional<OAuthUser> opt = oAuthUserRepository.findByUsername(name);
        if(!opt.isPresent() || !opt.get().isEnabled()){
            insertLoginAttempt(name, request);
            throw new BadCredentialsException("user not found " + name);
        }

        final String password = authentication.getCredentials() == null ? "" : authentication.getCredentials().toString();
        return getAuthentication(opt.get(), password, request);
    }

    /**
     * save the login attempt
     * @param userName the user name attempting to login
     */
    @Transactional
    protected void insertLoginAttempt(final String userName, final HttpServletRequest request) {
        //TODO why is this getting double tapped???
        if(request.getAttribute(LOGGED_BAD_KEY) == null) {
            final LoginAttempt la = new LoginAttempt();
            la.setUserName(userName);

            final String xfHeader = request.getHeader(HttpHeaders.X_FORWARDED_FOR);
            if (xfHeader == null) {
                la.setIpAddress(request.getRemoteAddr());
            } else {
                la.setIpAddress(xfHeader.split(",")[0]);
            }
            loginAttemptRepository.save(la);
            request.setAttribute(LOGGED_BAD_KEY, Boolean.TRUE);
        }
    }

    @Override
    public Authentication getAuthentication(final OAuthUser oAuthUser, final String password, final HttpServletRequest request) throws BadCredentialsException {
        Authentication auth = null;
        //use strong encryption
        if (StringUtils.isNoneBlank(oAuthUser.getEnhancedPwd()) && encryptionSvc.matches(password, oAuthUser.getEnhancedPwd())) {
            auth = new UsernamePasswordAuthenticationToken(oAuthUser.getUsername(), oAuthUser.getEnhancedPwd(), oAuthUser.getAuthorities());
        } else if (oAuthUser.getPassword().equals(getSha1Hex(oAuthUser.getUsername(), password))) {
            final String hashed = encryptionSvc.hashPassword(password);
            setEnanchedPwd(oAuthUser, hashed);
            auth = new UsernamePasswordAuthenticationToken(oAuthUser.getUsername(), hashed, oAuthUser.getAuthorities());
        } else {
            insertLoginAttempt(oAuthUser.getUsername(), request);
            throw new BadCredentialsException("user not found " + oAuthUser.getUsername());
        }
        loginAttemptRepository.clearUserName(oAuthUser.getUsername());
        return auth;
    }

    /**
     *
     * @param oAuthUser the user details data to us for authentication
     * @param hash the hashed password
     */
    @Override
    public void setEnanchedPwd(final OAuthUser oAuthUser, final String hash) {
        oAuthUser.setEnhancedPwd(hash);
        oAuthUserRepository.save(oAuthUser);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * Gets sha 1 hex.
     * @param userName
     * @param password
     * @return the sha 1 hex
     * @deprecated this is unsecure should be using bCrypt kill when sha-1 is removed
     */
    @SuppressWarnings("PMD.ForLoopCanBeForeach")
    private static String getSha1Hex(final String userName, final String password) {
        final byte[] data = DigestUtils.sha1(userName + ":" + password);

        final StringBuffer result = new StringBuffer();

        for (int cnt = 0; cnt < data.length; cnt++)
        {
            final int left = ((data[cnt]) & 0xF0) >> 4;
            final int right = (data[cnt]) & 0x0F;
            result.append(HEX_MAP[left] + HEX_MAP[right]);
        }

        return result.toString();
    }
}
