package org.tec.auth.crypto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.auth.AuthApplication;
import org.tec.auth.config.CustomTokenEnhancer;
import org.tec.auth.entity.OAuthUser;
import org.tec.auth.repository.OAuthUserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={AuthApplication.class})
public class CustomTokenEnhancerTest {

    @Autowired
    TokenEnhancer tokenEnhancer;

    @Autowired
    OAuthUserRepository oAuthUserRepository;

    @Test
    public void testUserName() {
        Optional<OAuthUser> opt = oAuthUserRepository.findByUsername("demo@outboundengine.com");

        assertTrue(opt.isPresent());

        OAuthUser oAuthUser = opt.get();

        Authentication auth = Mockito.mock(Authentication.class);
        BDDMockito.given(auth.getPrincipal()).willReturn(oAuthUser.getUsername());

        OAuth2Authentication oAuthentication = Mockito.mock(OAuth2Authentication.class);
        BDDMockito.given(oAuthentication.getUserAuthentication()).willReturn(auth);

        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken("blah");

        OAuth2AccessToken actual = tokenEnhancer.enhance(token, oAuthentication);

        assertNotNull(actual);

        assertNotNull(actual.getAdditionalInformation().get(CustomTokenEnhancer.OE_USER_INFO_KEY));
    }

    @Test
    public void testOAuthUser() {
        Optional<OAuthUser> opt = oAuthUserRepository.findByUsername("demo@outboundengine.com");

        assertTrue(opt.isPresent());

        OAuthUser oAuthUser = opt.get();

        Authentication auth = Mockito.mock(Authentication.class);
        BDDMockito.given(auth.getPrincipal()).willReturn(oAuthUser);

        OAuth2Authentication oAuthentication = Mockito.mock(OAuth2Authentication.class);
        BDDMockito.given(oAuthentication.getUserAuthentication()).willReturn(auth);

        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken("blah");

        OAuth2AccessToken actual = tokenEnhancer.enhance(token, oAuthentication);

        assertNotNull(actual);

        assertNotNull(actual.getAdditionalInformation().get(CustomTokenEnhancer.OE_USER_INFO_KEY));
    }

    @Test
    public void testUnknown() {
        Authentication auth = Mockito.mock(Authentication.class);
        BDDMockito.given(auth.getPrincipal()).willReturn(new Object());

        OAuth2Authentication oAuthentication = Mockito.mock(OAuth2Authentication.class);
        BDDMockito.given(oAuthentication.getUserAuthentication()).willReturn(auth);

        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken("blah");

        OAuth2AccessToken actual = tokenEnhancer.enhance(token, oAuthentication);

        assertNotNull(actual);

        assertNull(actual.getAdditionalInformation().get(CustomTokenEnhancer.OE_USER_INFO_KEY));
    }

    @Test
    public void testBad() {
        String userName = "blah@outboundengine.com";
        Optional<OAuthUser> oAuthUser = oAuthUserRepository.findByUsername(userName);

        assertFalse(oAuthUser.isPresent());

        Authentication auth = Mockito.mock(Authentication.class);
        BDDMockito.given(auth.getPrincipal()).willReturn(userName);

        OAuth2Authentication oAuthentication = Mockito.mock(OAuth2Authentication.class);
        BDDMockito.given(oAuthentication.getUserAuthentication()).willReturn(auth);

        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken("blah");

        OAuth2AccessToken actual = tokenEnhancer.enhance(token, oAuthentication);

        assertNotNull(actual);

        assertTrue(actual.getAdditionalInformation().isEmpty());
    }

    @Test
    public void testClient() {
        OAuth2Authentication oAuthentication = Mockito.mock(OAuth2Authentication.class);

        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken("blah");

        OAuth2AccessToken actual = tokenEnhancer.enhance(token, oAuthentication);

        assertNotNull(actual);

        assertTrue(actual.getAdditionalInformation().isEmpty());
    }
}
