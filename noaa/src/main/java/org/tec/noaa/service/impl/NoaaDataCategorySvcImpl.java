package org.tec.noaa.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tec.noaa.model.response.DataCategory;
import org.tec.noaa.model.response.Response;
import org.tec.noaa.service.NoaaDataCategorySvc;

@Service
public class NoaaDataCategorySvcImpl extends BaseNoaaSvcImpl implements NoaaDataCategorySvc {
    private static final String END_POINT = "datacategories";

    @Override
    String getEndPoint() {
        return END_POINT;
    }

    @Override
    public Response<DataCategory> getDataCategories() {
        ResponseEntity<Response<DataCategory>> response = get(new ParameterizedTypeReference<Response<DataCategory>>() {});

        return response.getBody();
    }
}
