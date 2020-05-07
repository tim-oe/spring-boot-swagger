package org.tec.auth.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.tec.auth.config.CustomTokenEnhancer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class ClientTest {
    public static final String OAUTH_URL = "http://localhost:8080/oauth/token";

    public static final String OAUTH_CLIENT_ID = "first-client";
    public static final String OAUTH_CLIENT_SECRET = "noonewilleverguess";

    public static final String GRANT_TYPE_CLT_CRED = "client_credentials";
    public static final String GRANT_TYPE_REFRESH = "refresh_token";
    public static final String GRANT_TYPE_PWD = "password";

    public static final String OAUTH_SCOPE_ALL = "all";

    public static final String OAUTH_GRANT_TYPE_PARAM = "grant_type";
    public static final String OAUTH_CLIENT_ID_PARAM = "client_id";
    public static final String OAUTH_CLIENT_SECRET_PARAM = "client_secret";
    public static final String OAUTH_SCOPE_PARAM = "scope";
    public static final String OAUTH_REFRESH_PARAM = "refresh_token";
    public static final String OAUTH_USERNAME_PARAM = "username";
    public static final String OAUTH_PASSWORD_PARAM = "password";

    public static final String USERNAME_VALUE = "demo@outboundengine.com";
    public static final String PASSWORD_VALUE = "demo";

//    @Autowired
//    private transient DataSource dataSource;
//
//    private transient JdbcTemplate jdbcTemplate = new JdbcTemplate();


    /** dump request/response data */
    protected ClientHttpRequestInterceptor debugInterceptor = new DebugRequestInterceptor();

    /** used to trap error data */
    protected DebugResponseErrorHandler responseErroHandler = new DebugResponseErrorHandler();

    /** spring reset client */
    protected RestTemplate restTemplate = new RestTemplate();

//    @AfterEach
//    public void tearDown() {
//        jdbcTemplate.execute("delete from login_attempt");
//    }

    @BeforeEach
    public void setup() {

//        jdbcTemplate.setDataSource(dataSource);

        //allow response to be read multiple times
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setPrettyPrint(false);
        messageConverter.setObjectMapper(getObjectMapper());

        restTemplate.getMessageConverters().removeIf(m -> m.getClass().getName().equals(MappingJackson2HttpMessageConverter.class.getName()));
        restTemplate.getMessageConverters().add(messageConverter);

        //be able to get dump for request and response but not as good as jersey
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(debugInterceptor);
        interceptors.add(new BasicAuthenticationInterceptor(OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET));

        restTemplate.setInterceptors(interceptors);

        restTemplate.setErrorHandler(responseErroHandler);
    }

    protected ObjectMapper getObjectMapper() {
        final ObjectMapper om = new ObjectMapper();

        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCaseStrategy.SNAKE_CASE);

        //https://github.com/FasterXML/jackson-modules-java8
        om.registerModule(new Jdk8Module());
        om.registerModule(new ParameterNamesModule());
        om.registerModule(new JavaTimeModule());

        return om;
    }

    protected OAuth2AccessToken getAuthToken(String grantType, String userName, String pwd, String refreshToken) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(OAUTH_GRANT_TYPE_PARAM, grantType);

        if(grantType.equals(GRANT_TYPE_REFRESH)) {
            params.add(OAUTH_CLIENT_ID_PARAM, OAUTH_CLIENT_ID);
            params.add(OAUTH_CLIENT_SECRET_PARAM, OAUTH_CLIENT_SECRET);
            params.add(OAUTH_REFRESH_PARAM, refreshToken);
        } else {
            if (userName != null) {
                params.add(OAUTH_SCOPE_PARAM, OAUTH_SCOPE_ALL);
                params.add(OAUTH_USERNAME_PARAM, userName);
            }
            if (pwd != null) {
                params.add(OAUTH_PASSWORD_PARAM, pwd);
            }
        }
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(OAUTH_URL);

        builder.queryParams(params);

        HttpEntity<String> entity = new HttpEntity<>(getDefaultSpringHeaders());

        HttpEntity<OAuth2AccessToken> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                OAuth2AccessToken.class);

        return response.getBody();
    }

    /**
     * Gets default headers for spring client.
     * @return the default headers
     */
    public static HttpHeaders getDefaultSpringHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<MediaType> types = new ArrayList<>();
        types.add(MediaType.APPLICATION_JSON);
        headers.setAccept(types);

        List<Charset> charsets = new ArrayList<>();
        charsets.add(StandardCharsets.UTF_8);
        headers.setAcceptCharset(charsets);

        return headers;
    }

    @Test
    @Tag("server")
    public void testClientAuth() {
        OAuth2AccessToken token = getAuthToken(GRANT_TYPE_CLT_CRED, null, null, null);
        assertNotNull(token);
        assertNotNull(token.getValue());
        assertNull(token.getRefreshToken());
    }

    @Test
    @Tag("server")
    public void testPwdAuth() {
        OAuth2AccessToken token = getAuthToken(GRANT_TYPE_PWD, USERNAME_VALUE, PASSWORD_VALUE, null);
        assertNotNull(token);
        assertNotNull(token.getValue());
        assertNotNull(token.getRefreshToken());
        assertNotNull(token.getAdditionalInformation().get(CustomTokenEnhancer.OE_USER_INFO_KEY));
        System.out.println(token.getAdditionalInformation().get(CustomTokenEnhancer.OE_USER_INFO_KEY));
    }

    @Test
    @Tag("server")
    public void testRefesh() {
        OAuth2AccessToken token = getAuthToken(GRANT_TYPE_PWD, USERNAME_VALUE, PASSWORD_VALUE, null);
        assertNotNull(token);

        OAuth2AccessToken refToken = getAuthToken(GRANT_TYPE_REFRESH, null, null, token.getRefreshToken().getValue());
        assertNotNull(refToken);
        assertNotNull(refToken.getValue());
        assertNotNull(refToken.getRefreshToken());
        assertNotNull(refToken.getAdditionalInformation().get(CustomTokenEnhancer.OE_USER_INFO_KEY));
        System.out.println(refToken.getAdditionalInformation().get(CustomTokenEnhancer.OE_USER_INFO_KEY));
    }

    @Test
    @Tag("server")
    public void testAdminPwdAuth() {
        OAuth2AccessToken token = getAuthToken(GRANT_TYPE_PWD, "admin@outboundengine.com", "admin", null);
        assertNotNull(token);
        assertNotNull(token.getValue());
        assertNotNull(token.getRefreshToken());
        assertNotNull(token.getAdditionalInformation().get(CustomTokenEnhancer.OE_USER_INFO_KEY));
        System.out.println(token.getAdditionalInformation().get(CustomTokenEnhancer.OE_USER_INFO_KEY));
    }

    @Test
    @Tag("server")
    public void testCustomerMgrPwdAuth() {
        OAuth2AccessToken token = getAuthToken(GRANT_TYPE_PWD, "agency@outboundengine.com", "agency", null);
        assertNotNull(token);
        assertNotNull(token.getValue());
        assertNotNull(token.getRefreshToken());
        assertNotNull(token.getAdditionalInformation().get(CustomTokenEnhancer.OE_USER_INFO_KEY));
        System.out.println(token.getAdditionalInformation().get(CustomTokenEnhancer.OE_USER_INFO_KEY));
    }

    @Test
    @Tag("server")
    public void testBadPwdAuth() {
        for(int cnt = 0; cnt < 3; cnt++) {
            try {
                OAuth2AccessToken token = getAuthToken(GRANT_TYPE_PWD, USERNAME_VALUE, "blah", null);
                fail("test should fail");
            } catch (HttpClientErrorException e) {
                assertEquals(HttpStatus.BAD_REQUEST.value(), e.getRawStatusCode());
                System.out.println(e.getMessage());
            }
        }

        try {
            OAuth2AccessToken token = getAuthToken(GRANT_TYPE_PWD, USERNAME_VALUE, "blah", null);
            fail("test should fail");
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.BAD_REQUEST.value(), e.getRawStatusCode());
            System.out.println(e.getMessage());
        }
    }
}
