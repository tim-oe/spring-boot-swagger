package org.tec.noaa.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.tec.noaa.rest.TraceRequestInterceptor;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * https://www.ncdc.noaa.gov/cdo-web/webservices/v2
 * http://zetcode.com/springboot/environment/
 */
public abstract class BaseNoaaSvcImpl {

    protected static final String TOKEN_ENV_KEY="NOAA_TOKEN";

    protected static final String BASE_URL="https://www.ncdc.noaa.gov/cdo-web/api/v2/";

    protected static final String TOKEN_HEADER_KEY="token";

    protected static final String LIMIT_PARAM_KEY="limit";
    protected static final String OFFSET_PARAM_KEY="offset";

    @Autowired
    private transient Environment env;

    @Autowired
    @Qualifier("objectMapper")
    protected transient ObjectMapper objectMapper;

    private transient String token;

    /** spring reset client */
    protected transient RestTemplate restTemplate = new RestTemplate();

    /** get the api end point */
    abstract String getEndPoint();

    @PostConstruct
    public void init() {
        token = env.getProperty(TOKEN_ENV_KEY);

        //allow response to be read multiple times
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setPrettyPrint(false);
        messageConverter.setObjectMapper(objectMapper);

        restTemplate.getMessageConverters().removeIf(m -> m.getClass().getName().equals(MappingJackson2HttpMessageConverter.class.getName()));
        restTemplate.getMessageConverters().add(messageConverter);

        //be able to get dump for request and response but not as good as jersey
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new TraceRequestInterceptor());
        restTemplate.setInterceptors(interceptors);
    }

    /**
     * Gets headers for spring client.
     *
     * @return the default headers
     */
    protected HttpHeaders getHeaders() {
        return  getHeaders(MediaType.APPLICATION_JSON, new HashMap<>());
    }

    /**
     * Gets headers for spring client.
     *
     * @param additionalHeaderParams  custom header s
     * @return the default headers
     */
    protected HttpHeaders getHeaders(Map<String, String> additionalHeaderParams) {
        return  getHeaders(MediaType.APPLICATION_JSON, additionalHeaderParams);
    }

    /**
     * Gets headers for spring client.
     *
     * @param  mediaType MediaType.APPLICATION_JSON
     * @param additionalHeaderParams  custom header s
     * @return the default headers
     */
    protected HttpHeaders getHeaders(MediaType mediaType, Map<String, String> additionalHeaderParams) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        //not setting for multipart. Throws 406 if it gets set
        if(mediaType != MediaType.MULTIPART_FORM_DATA) {
            List<MediaType> types = new ArrayList<>();
            types.add(mediaType);
            headers.setAccept(types);
        }

        List<Charset> charsets = new ArrayList<>();
        charsets.add(StandardCharsets.UTF_8);
        headers.setAcceptCharset(charsets);

        if(MapUtils.isNotEmpty(additionalHeaderParams)) {
            for(Iterator<Map.Entry<String, String>> it = additionalHeaderParams.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, String> entry = it.next();
                headers.set(entry.getKey(), entry.getValue());
            }
        }

        headers.set(TOKEN_HEADER_KEY, token);

        return headers;
    }

    /**
     * generic client call.
     * @param method  the http method for the call
     * @param params additional querstring parameters
     * @param headers  http headers
     * @param body the request body
     * @param responseType the response type the server controller response object dev
     *          See: https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate
     *          Example: new ParameterizedTypeReference<ResponseMobile<Long>>() {}
     *
     * @return the actual response object from the controller call
     */
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "false positives"})
    protected <T> ResponseEntity<T> method(HttpMethod method,
                                         @Nullable Map<String, String> params,
                                         @Nullable HttpHeaders headers,
                                         @Nullable String body,
                                         ParameterizedTypeReference<T> responseType) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(BASE_URL + getEndPoint());

        if(params != null && !params.isEmpty()) {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        HttpEntity httpEntity = (body != null)
                ? new HttpEntity(body, headers)
                : new HttpEntity(headers);

        ResponseEntity<T> response = null;

        try {
            response = restTemplate.exchange(
                    builder.build().encode().toUri(),
                    method,
                    httpEntity,
                    responseType);
        } catch (Exception e) {
            throw new RuntimeException("failed to process request " + builder.build().encode().toUri(), e);
        }

        if(response.getStatusCode() == HttpStatus.OK) {
            throw new RuntimeException(response.getStatusCode() + " failed to process request " + builder.build().encode().toUri());
        }

        return response;
    }
}