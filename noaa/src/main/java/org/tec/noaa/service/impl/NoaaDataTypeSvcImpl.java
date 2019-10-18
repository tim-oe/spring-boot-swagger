package org.tec.noaa.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tec.noaa.model.response.DataType;
import org.tec.noaa.model.response.Response;
import org.tec.noaa.service.NoaaDataTypeSvc;

@Service
public class NoaaDataTypeSvcImpl extends BaseNoaaSvcImpl implements NoaaDataTypeSvc {
    private static final String END_POINT = "datatypes";

    @Override
    String getEndPoint() {
        return END_POINT;
    }

    @Override
    public Response<DataType> getDataTypes() {
        ResponseEntity<Response<DataType>> response = get(new ParameterizedTypeReference<Response<DataType>>() {});

        return response.getBody();

    }
}
