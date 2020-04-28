package org.tec.noaa.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tec.noaa.model.response.Location;
import org.tec.noaa.model.response.Response;
import org.tec.noaa.service.NoaaLocationSvc;

@Service
public class NoaaLocationSvcImpl extends BaseNoaaSvcImpl implements NoaaLocationSvc {
    private static final String END_POINT = "locations";

    @Override
    public String getEndPoint() {
        return END_POINT;
    }

    @Override
    public Response<Location> getLocations() {
        final ResponseEntity<Response<Location>> response = get(new ParameterizedTypeReference<Response<Location>>() {});

        return response.getBody();
    }
}
