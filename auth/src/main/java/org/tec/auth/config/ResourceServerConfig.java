package org.tec.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private transient TokenStore tokenStore;

    @Autowired
    private transient AuthenticationManager authenticationManager;

    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore);
    }
}
