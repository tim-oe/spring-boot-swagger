package org.tec.noaa.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
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
public class TraceRequestInterceptor implements ClientHttpRequestInterceptor {
    
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        //if(log.isTraceEnabled()) {
            StringBuilder buff = new StringBuilder();
            buff.append("============================request begin==========================================\n");
            buff.append("URI          : {}" + request.getURI() + "\n");
            buff.append("Method       : {}" + request.getMethod() + "\n");
            buff.append("content type : {}" + request.getHeaders().getContentType() + "\n");
            buff.append("Headers      : {}" + request.getHeaders() + "\n");
            buff.append("Request body : {}" + new String(body, 0, (body.length > 10000 ? 10000 : body.length), "UTF-8") + "\n");
            buff.append("============================request end============================================" + "\n");
            log.warn(buff.toString());
        //}
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        //if(log.isTraceEnabled()) {
            StringBuilder buff = new StringBuilder();
            buff.append("============================response begin=========================================" + "\n");
            buff.append("Status code  : {}" + response.getStatusCode() + "\n");
            buff.append("Status text  : {}" + response.getStatusText() + "\n");

            //don't dump of status code not ok
            if (response.getStatusCode() == HttpStatus.OK) {
                StringBuilder inputStringBuilder = new StringBuilder();
                try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"))) {
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        inputStringBuilder.append(line);
                        inputStringBuilder.append('\n');
                        line = bufferedReader.readLine();
                    }
                }
                buff.append("Headers      : {}" + response.getHeaders() + "\n");
                buff.append("Response body: {}" + inputStringBuilder.substring(0, (inputStringBuilder.length() > 10000 ? 10000 : inputStringBuilder.length())) + "\n");
            }
            buff.append("=======================response end================================================" + "\n");
            log.warn(buff.toString());
        //}
    }
}