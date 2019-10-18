package org.tec.noaa.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tec.noaa.model.response.DataSet;
import org.tec.noaa.model.response.Response;
import org.tec.noaa.service.NoaaDataSetSvc;

@Service
public class NoaaDataSetSvcImpl extends BaseNoaaSvcImpl implements NoaaDataSetSvc {
    private static final String END_POINT = "datasets";

    @Override
    String getEndPoint() {
        return END_POINT;
    }

    @Override
    public Response<DataSet>  getDataSets() {
        ResponseEntity<Response<DataSet>> response = get(new ParameterizedTypeReference<Response<DataSet>>() {});

        return response.getBody();
    }
}
