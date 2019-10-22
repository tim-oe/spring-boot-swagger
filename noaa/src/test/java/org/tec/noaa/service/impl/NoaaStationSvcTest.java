package org.tec.noaa.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.noaa.NoaaApplication;
import org.tec.noaa.model.response.Response;
import org.tec.noaa.model.response.Station;
import org.tec.noaa.service.NoaaStationSvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= NoaaApplication.class)
public class NoaaStationSvcTest {
    @Autowired
    protected transient NoaaStationSvc noaaStationSvc;

    @Test
    public void test() {
        Response<Station> response = noaaStationSvc.getStations();

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getResults().isEmpty());
    }
}
