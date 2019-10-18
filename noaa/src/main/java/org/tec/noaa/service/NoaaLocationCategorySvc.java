package org.tec.noaa.service;

import org.tec.noaa.model.response.LocationCategory;
import org.tec.noaa.model.response.Response;

/**
 * https://www.ncdc.noaa.gov/cdo-web/webservices/v2#locationCategories
 */
public interface NoaaLocationCategorySvc {

    /**
     * get Location categories
     */
    Response<LocationCategory> getLocationCategories();
}
