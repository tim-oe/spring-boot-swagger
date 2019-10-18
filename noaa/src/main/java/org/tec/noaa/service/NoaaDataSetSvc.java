package org.tec.noaa.service;

import org.tec.noaa.model.response.DataSet;
import org.tec.noaa.model.response.Response;

/**
 * https://www.ncdc.noaa.gov/cdo-web/webservices/v2#datasets
 */
public interface NoaaDataSetSvc {

    /**
     * get data sets
     */
    Response<DataSet> getDataSets();
}
