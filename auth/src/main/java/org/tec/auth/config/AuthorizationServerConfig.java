package org.tec.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.tec.auth.svc.OEUserDetailsSvc;

import javax.sql.DataSource;

/**
 * https://docs.spring.io/spring-security-oauth2-boot/docs/current/reference/html/boot-features-security-oauth2-authorization-server.html
 * https://javadeveloperzone.com/spring-boot/spring-boot-oauth2-jdbc-token-store-example/
 * https://stackoverflow.com/questions/36904178/how-to-persist-oauth-access-tokens-in-spring-security-jdbc
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private transient AuthenticationManager authenticationManager;

    @Autowired
    private transient DataSource dataSource;

    @Autowired
    private transient OEUserDetailsSvc userDetailsService;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * https://stackoverflow.com/questions/39756748/spring-oauth-authorization-server-requires-scope
     * https://github.com/spring-projects/spring-framework/issues/20771
     * https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.jdbc(dataSource);

        clients
                .inMemory()
                .withClient("first-client")
                .secret(passwordEncoder().encode("noonewilleverguess"))
                .scopes("all")
                .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh_token")
                .redirectUris("http://localhost:8081/oauth/login/client-app");
    }

    /**
     * this is needed for handling password grant type
     * https://stackoverflow.com/questions/47392372/spring-security-oauth2-token-error-missing-grant-type
     * @param endpoints
     */
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancer())
                .approvalStore(approvalStore())
                .authorizationCodeServices(authorizationCodeServices())
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);
    }
}