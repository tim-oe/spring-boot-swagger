package org.tec.noaa.service;

import org.tec.noaa.model.request.DataParams;
import org.tec.noaa.model.response.Data;
import org.tec.noaa.model.response.Response;

/**
 * https://www.ncdc.noaa.gov/cdo-web/webservices/v2#data
 */
public interface NoaaDataSvc {
    /**
     * get Noaa data
     * @param params the request params
     */
    Response<Data> getData(DataParams params);
}
