package org.tec.auth.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * response error handler used in test environment
 */
public class DebugResponseErrorHandler implements ResponseErrorHandler {

    HttpStatus status;
    String statusMsg;
    ResponseErrorHandler defaultHandler = new DefaultResponseErrorHandler();

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        this.status = response.getStatusCode();
        this.statusMsg = response.getStatusText();
        return defaultHandler.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        defaultHandler.handleError(response);
    }

    /**
     * Gets status.
     * @return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * Gets status msg.
     * @return the status msg
     */
    public String getStatusMsg() {
        return statusMsg;
    }
}
