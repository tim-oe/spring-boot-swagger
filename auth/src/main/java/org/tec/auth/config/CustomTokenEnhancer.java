package org.tec.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.tec.auth.entity.OAuthUser;
import org.tec.auth.repository.OAuthUserRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// https://stackoverflow.com/questions/28492116/can-i-include-user-information-while-issuing-an-access-token
// This token enhancer will give back some useful information for APIs to use in order to know who exactly
// they're dealing with.
@Slf4j
public class CustomTokenEnhancer implements TokenEnhancer {

    public static final String OE_USER_INFO_KEY = "oe.user.data";

    @Autowired
    private transient OAuthUserRepository oAuthUserRepository;

    @Override
    public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken, final OAuth2Authentication authentication) {
        //grant_type client will be null.
        if(authentication.getUserAuthentication() != null) {
            final Map<String, Object> additionalInfo = new ConcurrentHashMap<>();
            final Object principal = authentication.getUserAuthentication().getPrincipal();
            if (principal instanceof OAuthUser) {
                additionalInfo.put(OE_USER_INFO_KEY, new OAuthUser((OAuthUser)principal));
            } else if (principal instanceof String) {
                final Optional<OAuthUser> opt = oAuthUserRepository.findByUsername((String) principal);

                if(opt.isPresent()) {
                    additionalInfo.put(OE_USER_INFO_KEY, new OAuthUser(opt.get()));
                } else {
                    log.warn("no enhanced data for " + principal);
                }
            }
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        }
        return accessToken;
    }
}