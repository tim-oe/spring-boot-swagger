package org.tec.springbootswagger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.tec.springbootswagger.auth.CustomAuthProvider;
import org.tec.springbootswagger.controller.StatusController;

/**
 * https://www.baeldung.com/spring-security-authentication-provider
 * https://spring.io/guides/gs/securing-web/
 * https://www.baeldung.com/spring-security-basic-authentication
 */
@Configuration
@EnableWebSecurity
@ComponentScan("org.tec.springbootswagger.auth")
public class AuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected transient CustomAuthProvider authProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authenticationProvider(authProvider)
                .authorizeRequests()
                .antMatchers(StatusController.PATH).permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
    }
}