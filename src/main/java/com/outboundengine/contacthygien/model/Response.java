package com.outboundengine.contacthygien.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * the encapsulation of the standard rest response

 */
@XmlRootElement
public class Response<T>{

    /** whether this request was successful */
    protected boolean success = true;

    /** whether the user is */
    protected boolean authorized = true;

    /** the response message if any */
    protected String message = null;

    /**
     * data if any
     * Needs to be able to be marshalled to json
     */
    protected T data = null;

    /**
     * get success
     *
     * @return true if success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * get authorized
     *
     * @return true if authorized
     */
    public boolean isAuthorized() {
        return authorized;
    }

    /**
     * get the response message
     *
     * @return the response message
     */
    public String getMessage() {
        return message;
    }

    /**
     * get the response data
     *
     * @return the response data
     */
    public T getData() {
        return data;
    }

    /**
     * set success
     *
     * @param success true if success else false
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * set authorized
     *
     * @param authorized true if authorized else false
     */
    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    /**
     * set the message
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * set data can be object or collection of objects
     *
     * @param data set response data Needs to be able to be marshalled to json
     */
    public void setData(T data) {
        this.data = data;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
