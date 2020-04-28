package org.tec.noaa.model.request;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseParameters {
    protected transient final Map<String,String> params = new ConcurrentHashMap<>();

    /**
     * get parameters
     */
    public Map<String,String> getParams() {
        return params;
    }
}
