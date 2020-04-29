package org.tec.noaa.rest;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * spring rest client debug interceptor that you get for free /w jersey...
 * for this to work in line you need to have the following set for the template
 * restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
 * TODO move to common lib
 */
@Slf4j
//not sure what the npe dereferance error is...
@SuppressWarnings({"NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE","PMD.SystemPrintln"})
public class TraceRequestInterceptor implements ClientHttpRequestInterceptor {
    
    @Override
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        final ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(final HttpRequest request, final byte[] body) throws IOException {
        System.out.println("============================request begin==========================================");
        System.out.println("URI          : {}" + request.getURI());
        System.out.println("Method       : {}" + request.getMethod());
        System.out.println("content type : {}" + request.getHeaders().getContentType());
        System.out.println("Headers      : {}" + request.getHeaders());
        if(body != null && request.getHeaders() != null && request.getHeaders().getContentType() != null && request.getHeaders().getContentType().includes(MediaType.APPLICATION_JSON)) {
            System.out.println("Request body : {}" + new String(body, "UTF-8"));
        }
        System.out.println("============================request end============================================");
    }

    @SuppressFBWarnings("NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
    private void traceResponse(final ClientHttpResponse response) throws IOException {
        System.out.println("============================response begin=========================================");
        System.out.println("Status code  : {}" + response.getStatusCode());
        System.out.println("Status text  : {}" + response.getStatusText());
        System.out.println("Headers      : {}" + response.getHeaders());

        if(response.getHeaders() != null && response.getHeaders().getContentType() != null && response.getHeaders().getContentType().includes(MediaType.APPLICATION_JSON)) {
            final StringBuilder inputStringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"))) {
                String line = bufferedReader.readLine();
                while (line != null) {
                    inputStringBuilder.append(line);
                    inputStringBuilder.append('\n');
                    line = bufferedReader.readLine();
                }
                System.out.println("Response body: {}" + inputStringBuilder.toString());
            }
        }
        System.out.println("=======================response end================================================");
    }
}