package org.tec.noaa.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tec.noaa.model.request.DataParams;
import org.tec.noaa.model.response.Data;
import org.tec.noaa.model.response.Response;
import org.tec.noaa.service.NoaaDataSvc;

import java.time.LocalDate;

@Service
public class NoaaDataSvcImpl extends BaseNoaaSvcImpl implements NoaaDataSvc {
    private static final String END_POINT = "data";

    @Override
    String getEndPoint() {
        return END_POINT;
    }

    @Override
    public Response<Data> getData(String dataSetId, LocalDate startDate, LocalDate enddDate) {
        DataParams params = new DataParams();
        params.setDataSetId(dataSetId);
        params.setStartDate(startDate);
        params.setEndDate(enddDate);

        ResponseEntity<Response<Data>> response = get(params.getParams(), new ParameterizedTypeReference<Response<Data>>() {});

        return response.getBody();
    }
}
