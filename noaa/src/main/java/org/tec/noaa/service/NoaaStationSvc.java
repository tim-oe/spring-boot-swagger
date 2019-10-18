package org.tec.noaa.service;

import org.tec.noaa.model.response.Response;
import org.tec.noaa.model.response.Station;

/**
 * https://www.ncdc.noaa.gov/cdo-web/webservices/v2#stations
 */
public interface NoaaStationSvc {

    /**
     * get locations
     * @return the locations
     */
    Response<Station> getStations();
}
