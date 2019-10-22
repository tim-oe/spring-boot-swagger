package org.tec.noaa.model.request;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseParameters {
    protected transient Map<String,String> params = new HashMap();

    /**
     * get parameters
     */
    public Map<String,String> getParams() {
        return params;
    }
}
