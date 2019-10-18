package org.tec.noaa.service;

import org.tec.noaa.model.response.DataCategory;
import org.tec.noaa.model.response.Response;

/**
 * https://www.ncdc.noaa.gov/cdo-web/webservices/v2#dataCategories
 */
public interface NoaaDataCategorySvc {

    /**
     * get data categories
     */
    Response<DataCategory> getDataCategories();
}
