package org.tec.noaa.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.noaa.NoaaApplication;
import org.tec.noaa.model.request.DataParams;
import org.tec.noaa.model.response.Data;
import org.tec.noaa.model.response.Response;
import org.tec.noaa.service.NoaaDataSvc;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= NoaaApplication.class)
public class NoaaDataSvcTest {

    @Autowired
    protected transient NoaaDataSvc noaaDataSvc;

    @Test
    public void getData() {

        DataParams params = new DataParams();
        params.setDataSetId("GHCND");
        params.setLocationId("ZIP:28801");
        params.setStartDate(LocalDate.parse("2010-05-01"));
        params.setEndDate(LocalDate.parse("2010-05-01"));

        Response<Data> response = noaaDataSvc.getData(params);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getResults().isEmpty());
    }
}