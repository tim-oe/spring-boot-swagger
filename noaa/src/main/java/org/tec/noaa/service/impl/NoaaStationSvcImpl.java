package org.tec.noaa.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tec.noaa.model.response.Response;
import org.tec.noaa.model.response.Station;
import org.tec.noaa.service.NoaaStationSvc;

@Service
public class NoaaStationSvcImpl extends BaseNoaaSvcImpl implements NoaaStationSvc {
    private static final String END_POINT = "stations";

    @Override
    public String getEndPoint() {
        return END_POINT;
    }

    @Override
    public Response<Station> getStations() {
       final ResponseEntity<Response<Station>> response = get(new ParameterizedTypeReference<Response<Station>>() {});

       return response.getBody();
    }
}
