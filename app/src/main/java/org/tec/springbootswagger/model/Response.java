package org.tec.springbootswagger.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * the encapsulation of the standard rest response
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Response<T>{

    /** whether this request was successful */
    protected boolean success = true;

    /** whether the user is */
    protected boolean authorized = true;

    /** the response message if any */
    protected String message;

    /**
     * data if any
     * Needs to be able to be marshalled to json
     */
    protected T data;

    /**
     * get the response data
     *
     * @return the response data
     */
    public T getData() {
        return data;
    }

    /**
     * set data can be object or collection of objects
     *
     * @param data set response data Needs to be able to be marshalled to json
     */
    public void setData(final T data) {
        this.data = data;
    }
}