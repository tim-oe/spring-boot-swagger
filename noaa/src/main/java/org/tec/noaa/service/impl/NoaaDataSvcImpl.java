package org.tec.noaa.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tec.noaa.model.request.DataParams;
import org.tec.noaa.model.response.Data;
import org.tec.noaa.model.response.Response;
import org.tec.noaa.service.NoaaDataSvc;

@Service
public class NoaaDataSvcImpl extends BaseNoaaSvcImpl implements NoaaDataSvc {
    private static final String END_POINT = "data";

    @Override
    String getEndPoint() {
        return END_POINT;
    }

    @Override
    public Response<Data> getData(DataParams params) {
        ResponseEntity<Response<Data>> response = get(params.getParams(), new ParameterizedTypeReference<Response<Data>>() {});

        return response.getBody();
    }
}
