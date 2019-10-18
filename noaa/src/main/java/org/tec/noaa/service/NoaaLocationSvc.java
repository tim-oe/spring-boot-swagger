package org.tec.noaa.service;

import org.tec.noaa.model.response.Location;
import org.tec.noaa.model.response.Response;

/**
 * https://www.ncdc.noaa.gov/cdo-web/webservices/v2#locations
 */
public interface NoaaLocationSvc {

    /**
     * get locations
     * @return locations
     */
    Response<Location> getLocations();
}
