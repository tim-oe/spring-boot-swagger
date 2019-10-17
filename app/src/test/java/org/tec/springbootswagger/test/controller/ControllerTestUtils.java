package org.tec.springbootswagger.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * class for testing oeserver controllers
 * holds state so needs to not be singleton
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ControllerTestUtils {

    @Autowired
    @Qualifier("objectMapper")
    protected transient ObjectMapper objectMapper;

    @Autowired
    private transient WebApplicationContext webApplicationContext;

    @Autowired
    private transient FilterChainProxy springSecurityFilterChain;


    /**
     * entry point for server-side Spring MVC test
     */
    protected transient MockMvc mockMvc;

    /**
     * Creates the `mockMvc` instance for the controller along with support
     * this should be called in the function annotated /w before
     *
     * @param testClass   the test class instance should be "this"
     *                    <p>
     *                    for @ControllerAdvice classes.
     */
    public void init(Object testClass) {
        MockitoAnnotations.initMocks(testClass);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    /**
     * get header map /w basic auth
     * @param name the user name
     * @param password the password
     * @return header map /w basic auth
     */
    public Map<String, String> getHeadersWithAuth(String  name, String password) {
        Map<String, String> headers = new HashMap<>();

        String credentialsString = name + ":" + password;
        byte[] encodedBytes = Base64.getEncoder().encode(credentialsString.getBytes(StandardCharsets.UTF_8));
        String encodedCredentials = new String(encodedBytes, StandardCharsets.UTF_8);

        headers.put(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials);

        return headers;
    }

    /**
     * performn multi part file upload
     *
     * @param path         the controller path
     * @param in           the input stream of the file to upload
     * @param name         the file name
     * @param responseType the response type
     * @return the response
     */
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "false positives"})
    public <T> T imageUpload(String path, InputStream in, String fileName, String name, MediaType imageType, TypeReference<T> responseType) {

        MvcResult result = null;
        try {
            MockMultipartFile file = new MockMultipartFile(name, fileName, imageType.toString(), in);

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                    .multipart(path)
                    .file(file);

            if(request != null) {
                result = mockMvc.perform(request)
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andReturn();

                if (result != null) {
                    return objectMapper.readValue(result.getResponse().getContentAsString(), responseType);
                }
            }
        } catch (Exception t) {
            t.printStackTrace();
            Assertions.fail(t);
        } finally {
            try {
                if(in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * POST request.
     *
     * @param controllerPath The controller path to request.
     * @param params         The parameter map
     * @param responseType   The type response returned
     * @return the response object that is expected for the call
     */
    public <T> T post(String controllerPath, MultiValueMap<String, String> params, TypeReference<T> responseType) {
        return method(HttpMethod.POST, controllerPath, params, null, null, null, null, responseType);
    }

    /**
     * POST request.
     *
     * @param controllerPath The controller path to request.
     * @param params         The parameter map
     * @param headers        the set of headers
     * @param responseType   The type response returned
     * @return the response object that is expected for the call
     */
    public <T> T post(String controllerPath, MultiValueMap<String, String> params, Map<String, String> headers,  TypeReference<T> responseType) {
        return method(HttpMethod.POST, controllerPath, params, headers, null, null, null, responseType);
    }

    /**
     * POST request.
     *
     * @param controllerPath The controller path to request.
     * @param responseType   The type response returned
     * @param content        The Request Content
     * @return the response object that is expected for the call
     */
    public <T> T post(String controllerPath, TypeReference<T> responseType, String content, MediaType mediaType) {
        return method(HttpMethod.POST, controllerPath, null, null, content, mediaType, null, responseType);
    }

    /**
     * POST request.
     *
     * @param controllerPath The controller path to request.
     * @param headers        the set of headers
     * @param responseType   The type response returned
     * @param content        The Request Content
     * @return the response object that is expected for the call
     */
    public <T> T post(String controllerPath, Map<String, String> headers, String content, MediaType mediaType, TypeReference<T> responseType) {
        return method(HttpMethod.POST, controllerPath, null, headers, content, mediaType, null, responseType);
    }

    /**
     * GET request.
     *
     * @param controllerPath The controller path to request.
     * @param params         The parameter map
     * @param headers        the set of headers
     * @param responseType   The type response returned
     * @return the response object that is expected for the call
     * @if processing fails
     */
    public <T> T get(String controllerPath, MultiValueMap<String, String> params,Map<String, String> headers, TypeReference<T> responseType) {
        return method(HttpMethod.GET, controllerPath, params, headers, null, null, null, responseType);
    }

    /**
     * GET request.
     *
     * @param controllerPath The controller path to request.
     * @param headers        the set of headers
     * @param responseType   The type response returned
     * @return the response object that is expected for the call
     */
    public <T> T get(String controllerPath, Map<String, String> headers, TypeReference<T> responseType) {
        return method(HttpMethod.GET, controllerPath, null, headers, null, null, null, responseType);
    }

    /**
     * GET request.
     *
     * @param controllerPath The controller path to request.
     * @param params         The parameter map
     * @param status         The expected http status
     * @param responseType   The type response returned
     * @return the response object that is expected for the call
     */
    public <T> T get(String controllerPath, MultiValueMap<String, String> params, HttpStatus status, TypeReference<T> responseType) {
        return method(HttpMethod.GET, controllerPath, params, null, null, null, status, responseType);
    }

    /**
     * GET request.
     *
     * @param controllerPath The controller path to request.
     * @param params         The parameter map
     * @param responseType   The type response returned
     * @return the response object that is expected for the call
     */
    public <T> T get(String controllerPath, MultiValueMap<String, String> params, TypeReference<T> responseType) {
        return method(HttpMethod.GET, controllerPath, params, null, null, null, null, responseType);
    }

    /**
     * GET request.
     *
     * @param controllerPath The controller path to request.
     * @param responseType   The type response returned
     * @return the response object that is expected for the call
     */
    public <T> T get(String controllerPath, TypeReference<T> responseType) {
        return method(HttpMethod.GET, controllerPath, null, null, null, null, null, responseType);
    }

    /**
     * GET request.
     *
     * @param controllerPath The controller path to request.
     * @param status         the response status
     * @param responseType   The type response returned
     * @return the response object that is expected for the call
     */
    public <T> T get(String controllerPath, HttpStatus status, TypeReference<T> responseType) {
        return method(HttpMethod.GET, controllerPath, null, null, null, null, status, responseType);
    }

    /**
     * DELETE request.
     *
     * @param controllerPath The controller path to request.
     * @param params         The parameter map
     * @param responseType   The type response returned
     * @return the response object that is expected for the call
     * @if processing fails
     */
    public <T> T delete(String controllerPath, MultiValueMap<String, String> params, TypeReference<T> responseType) {
        return method(HttpMethod.DELETE, controllerPath, params, null, null, null, null, responseType);
    }

    /**
     * make a request to an mvc controller.
     *
     * @param method         the request method
     * @param controllerPath The controller path to request.
     * @param params         The parameter map
     * @param headers        the set of headers
     * @param content        The Request content
     * @param mediaType      The request type (application/json)
     * @param status         the response status
     * @param responseType   The type response returned
     * @return the response object that is expected for the call
     */
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "false positives"})
    protected <T> T method(HttpMethod method,
                           String controllerPath,
                           @Nullable MultiValueMap<String, String> params,
                           @Nullable Map<String, String> headers,
                           @Nullable String content,
                           @Nullable MediaType mediaType,
                           @Nullable HttpStatus status,
                           TypeReference<T> responseType) {
        MvcResult result = null;
        try {
            // build the request object
            MockHttpServletRequestBuilder request = null;

            switch (method) {
                case GET:
                    request = MockMvcRequestBuilders.get(controllerPath);
                    break;
                case POST:
                    request = MockMvcRequestBuilders.post(controllerPath);
                    break;
                case PUT:
                    request = MockMvcRequestBuilders.put(controllerPath);
                    break;
                case DELETE:
                    request = MockMvcRequestBuilders.delete(controllerPath);
                    break;
                default:
                    Assertions.fail("unhandled method: " + method);
                    break;
            }

            if (request != null) {
                if (headers != null) {
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        request.header(entry.getKey(), entry.getValue());
                    }
                }
                if (params != null) {
                    request.params(params);
                }

                request.contentType((mediaType != null ? mediaType : MediaType.TEXT_HTML));
                if (content != null) {
                    request.content(content);
                }

                if (status != null) {
                    result = mockMvc.perform(request)
                            .andDo(print())
                            .andExpect(status().is(status.value()))
                            .andReturn();
                     if (result != null && !StringUtils.isEmpty(result.getResponse().getContentAsString())) {
                         return objectMapper.readValue(result.getResponse().getContentAsString(), responseType);
                     }
                } else {
                    result = mockMvc.perform(request)
                            .andDo(print())
                            .andExpect(status().isOk())
                            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                            .andReturn();

                    if(result != null) {
                        return objectMapper.readValue(result.getResponse().getContentAsString(), responseType);
                    }
                }
            }
        } catch (Exception t) {
            Assertions.fail(t);
        }
        return null;
    }
}