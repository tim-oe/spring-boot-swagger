package org.tec.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.tec.auth.svc.OEAuthenticationSvc;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private transient OEAuthenticationSvc oeAuthenticationSvc;

    /**
     * this is to be be able to hook up auth for password grant_type in web security config
     * @return the auth manager.
     * @throws Exception something went wrong
     */
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return oeAuthenticationSvc;
    }

    /**
     * https://www.baeldung.com/spring-security-authentication-provider
     * provide custom auth linking to the oe data
     * DO NOT ADD UserDetailsSvc
     * https://stackoverflow.com/questions/30835674/spring-security-boot-replace-default-daoauthenticationprovider
     * @param auth the authtentication object
     * @throws Exception if something goes wrong
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(oeAuthenticationSvc);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authenticationProvider(oeAuthenticationSvc)
        .csrf().disable()
        .authorizeRequests().anyRequest().authenticated();
    }
}