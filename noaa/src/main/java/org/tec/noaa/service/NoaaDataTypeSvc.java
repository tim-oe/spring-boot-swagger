package org.tec.noaa.service;

import org.tec.noaa.model.response.DataType;
import org.tec.noaa.model.response.Response;

/**
 * https://www.ncdc.noaa.gov/cdo-web/webservices/v2#dataTypes
 */
public interface NoaaDataTypeSvc {

    /**
     * get data types
     */
    Response<DataType> getDataTypes();
}
