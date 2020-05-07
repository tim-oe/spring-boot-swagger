package org.tec.auth.svc.impl;

import com.google.common.net.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.tec.auth.AuthApplication;
import org.tec.auth.entity.OAuthUser;
import org.tec.auth.repository.OAuthUserRepository;
import org.tec.auth.svc.OEAuthenticationSvc;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Transactional
@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes={AuthApplication.class})
@SpringBootTest(classes={AuthApplication.class})
public class OEAuthenticationSvcTest {

    public static final String OAUTH_PATH = "/oauth/token";

    @Autowired
    OEAuthenticationSvc oeAuthenticationSvc;

    @Autowired
    OAuthUserRepository oAuthUserRepository;

//    @Autowired
//    private WebApplicationContext webAppContext;
//
//    @Autowired
//    private Filter springSecurityFilterChain;

    @Autowired
    HttpServletRequest request;

    /** entry point for server-side Spring MVC test */
    protected MockMvc mockMvc;

    /**
     * need to mock the request here
     */
//    //@BeforeEach
//    public void setup() {
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(AuthApplicationTest.OAUTH_URL);
//
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(builder.build().toUri());
//
//        requestBuilder.header(HttpHeaders.X_FORWARDED_FOR, "10.1.1.18");
//
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webAppContext)
//                .defaultRequest(requestBuilder)
////                .dispatchOptions(true)
//                .addFilter(springSecurityFilterChain)
////                .apply(springSecurity())
//                .build();
//    }
//
//    protected MvcResult urlCall(MultiValueMap<String, String> params, Map<String, String> headers) throws Exception {
//        // build the request object
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(OAUTH_PATH);
//
//        request.header(HttpHeaders.AUTHORIZATION, org.springframework.http.HttpHeaders
//                .encodeBasicAuth(AuthApplicationTest.OAUTH_CLIENT_ID, AuthApplicationTest.OAUTH_CLIENT_SECRET, StandardCharsets.UTF_8));
//
//        request.headers(AuthApplicationTest.getDefaultSpringHeaders());
//
//        if(headers != null) {
//            for(Map.Entry<String,String> entry : headers.entrySet()) {
//                request.header(entry.getKey(), entry.getValue());
//            }
//        }
//
//        if(params != null) {
//            request.params(params);
//        }
//
//        return mockMvc.perform(request)
//                    .andDo(print())
//                    .andReturn();
//    }
//
//    @Disabled("not working with custum auth...")
//    public void testCall() throws Exception{
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add(AuthApplicationTest.OAUTH_GRANT_TYPE_PARAM, AuthApplicationTest.GRANT_TYPE_PWD);
//        params.add(AuthApplicationTest.OAUTH_SCOPE_PARAM, AuthApplicationTest.OAUTH_SCOPE_ALL);
//
//        params.add(AuthApplicationTest.OAUTH_USERNAME_PARAM, AuthApplicationTest.USERNAME_VALUE);
//        params.add(AuthApplicationTest.OAUTH_PASSWORD_PARAM, AuthApplicationTest.PASSWORD_VALUE);
//
//        MvcResult result = urlCall(params, null);
//
//        Assertions.assertNotNull(result);
//    }

    @Test
    public void testgetAuth() {
        Optional<OAuthUser> oAuthUser = oAuthUserRepository.findByUsername("demo@outboundengine.com");

        assertTrue(oAuthUser.isPresent());

        MockHttpServletRequest request = new MockHttpServletRequest();

        request.addHeader(HttpHeaders.X_FORWARDED_FOR, "10.1.1.18");

        OAuthUser actual = oAuthUser.get();

        assertNull(actual.getEnhancedPwd());

        Authentication auth = oeAuthenticationSvc.getAuthentication(actual, "demo", request);

        assertNotNull(auth);

        oAuthUser = oAuthUserRepository.findByUsername("demo@outboundengine.com");

        assertNotNull(oAuthUser.get().getEnhancedPwd());

        auth = oeAuthenticationSvc.getAuthentication(actual, "demo", request);

        assertNotNull(auth);
    }

    @Test
    public void testInvalidPwd() {
        Optional<OAuthUser> oAuthUser = oAuthUserRepository.findByUsername("demo@outboundengine.com");

        assertTrue(oAuthUser.isPresent());

        OAuthUser actual = oAuthUser.get();

        assertNull(actual.getEnhancedPwd());

        MockHttpServletRequest request = new MockHttpServletRequest();

        request.addHeader(HttpHeaders.X_FORWARDED_FOR, "10.1.1.18");

        for(int cnt = 0; cnt < 3; cnt++) {
            try {
                Authentication auth = oeAuthenticationSvc.getAuthentication(actual, "blah", request);
                fail("should've thrown BadCredentialsException");
            } catch (BadCredentialsException e) {
                System.out.println(e.getMessage());
                request.removeAttribute(OEAuthenticationSvc.LOGGED_BAD_KEY);
            }
        }

        request.removeHeader(HttpHeaders.X_FORWARDED_FOR);

        Authentication expected = Mockito.mock(Authentication.class);

        BDDMockito.given(expected.getName()).willReturn(actual.getUsername());
        BDDMockito.given(expected.getCredentials()).willReturn("blah");

        try {
            Authentication auth = oeAuthenticationSvc.authenticate(expected);
            fail("should've thrown LockedException");
        } catch (LockedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testValidUser() {
        Optional<OAuthUser> opt = oAuthUserRepository.findByUsername("demo@outboundengine.com");

        assertTrue(opt.isPresent());

        OAuthUser oAuthUser = opt.get();

        Authentication expected = Mockito.mock(Authentication.class);

        BDDMockito.given(expected.getName()).willReturn(oAuthUser.getUsername());
        BDDMockito.given(expected.getCredentials()).willReturn("demo");

        Authentication auth = oeAuthenticationSvc.authenticate(expected);

        assertNotNull(auth);
        assertEquals(oAuthUser.getUsername(), auth.getName());
    }

    @Test
    public void testInvalidUser() {
        String userName = "blah@outboundengine.com";
        Optional<OAuthUser> oAuthUser = oAuthUserRepository.findByUsername(userName);

        assertFalse(oAuthUser.isPresent());

        Authentication expected = Mockito.mock(Authentication.class);

        BDDMockito.given(expected.getName()).willReturn(userName);
        BDDMockito.given(expected.getCredentials()).willReturn("blah");

        try {
            Authentication auth = oeAuthenticationSvc.authenticate(expected);
            fail("should've thrown BadCredentialsException");
        } catch (BadCredentialsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testDisabledUser() {
        Optional<OAuthUser> opt = oAuthUserRepository.findByUsername("elizabeth_mitchell@featurechat.com");

        assertTrue(opt.isPresent());

        OAuthUser oAuthUser = opt.get();

        Authentication expected = Mockito.mock(Authentication.class);

        BDDMockito.given(expected.getName()).willReturn(oAuthUser.getUsername());
        BDDMockito.given(expected.getCredentials()).willReturn("blah");

        try {
            Authentication auth = oeAuthenticationSvc.authenticate(expected);
            fail("should've thrown BadCredentialsException");
        } catch (BadCredentialsException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void testSupport() {
        assertTrue(oeAuthenticationSvc.supports(UsernamePasswordAuthenticationToken.class));

        assertFalse(oeAuthenticationSvc.supports(AnonymousAuthenticationToken.class));
    }
}
