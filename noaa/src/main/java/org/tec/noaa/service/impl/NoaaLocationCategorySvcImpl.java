package org.tec.noaa.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tec.noaa.model.response.LocationCategory;
import org.tec.noaa.model.response.Response;
import org.tec.noaa.service.NoaaLocationCategorySvc;

@Service
public class NoaaLocationCategorySvcImpl extends BaseNoaaSvcImpl implements NoaaLocationCategorySvc {
    private static final String END_POINT = "locationcategories";

    @Override
    public String getEndPoint() {
        return END_POINT;
    }

    @Override
    public Response<LocationCategory> getLocationCategories() {
        final ResponseEntity<Response<LocationCategory>> response = get(new ParameterizedTypeReference<Response<LocationCategory>>() {});

//        System.out.println(response.getBody());
        return response.getBody();
    }
}
