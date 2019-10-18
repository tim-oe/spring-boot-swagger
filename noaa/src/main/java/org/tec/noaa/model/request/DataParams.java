package org.tec.noaa.model.request;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataParams extends BaseParameters {
    private static final String DATA_SET_ID_KEY = "datasetid";
    private static final String END_DATE_KEY = "enddate";
    private static final String START_DATE_KEY = "startdate";

    /**
     * set data set id
     * @param dataSetId the dataset id
     */
    public void setDataSetId(String dataSetId) {
        params.put(DATA_SET_ID_KEY, dataSetId);
    }

    /**
     * set end date
     * @param endDate end date
     */
    public void setEndDate(LocalDate endDate) {
        params.put(END_DATE_KEY, DateTimeFormatter.ISO_DATE.format(endDate));
    }

    /**
     * set start date
     * @param startDate start date
     */
    public void setStartDate(LocalDate startDate) {
        params.put(START_DATE_KEY, DateTimeFormatter.ISO_DATE.format(startDate));
    }
}
