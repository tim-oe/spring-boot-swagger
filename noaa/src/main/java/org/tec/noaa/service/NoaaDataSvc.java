package org.tec.noaa.service;

import org.tec.noaa.model.response.Data;
import org.tec.noaa.model.response.Response;

import java.time.LocalDate;

/**
 * https://www.ncdc.noaa.gov/cdo-web/webservices/v2#data
 */
public interface NoaaDataSvc {
    /**
     * get Noaa data
     * @param dataSetId the data set id for the data
     * @param startDate the data start date
     * @param enddDate  the data end date
     */
    Response<Data> getData(String dataSetId, LocalDate startDate, LocalDate enddDate);
}
