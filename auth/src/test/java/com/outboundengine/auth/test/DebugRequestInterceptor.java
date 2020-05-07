package org.tec.auth.test;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
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
 */
public class DebugRequestInterceptor implements ClientHttpRequestInterceptor {


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        System.out.println("============================request begin==========================================");
        System.out.println("URI          : {}" + request.getURI());
        System.out.println("Method       : {}" + request.getMethod());
        System.out.println("content type : {}" + request.getHeaders().getContentType());
        System.out.println("Headers      : {}" + request.getHeaders());
        if(request.getHeaders().getContentType().includes(MediaType.APPLICATION_JSON)) {
            System.out.println("Request body : {}" + new String(body, "UTF-8"));
        }
        System.out.println("============================request end============================================");
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        System.out.println("============================response begin=========================================");
        System.out.println("Status code  : {}" + response.getStatusCode());
        System.out.println("Status text  : {}" + response.getStatusText());

        //don't dump of status code not ok
        if(response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Headers      : {}" + response.getHeaders());
            if(response.getHeaders().getContentType().includes(MediaType.APPLICATION_JSON)) {
                StringBuilder inputStringBuilder = new StringBuilder();
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
        }
        System.out.println("=======================response end================================================");
    }
}