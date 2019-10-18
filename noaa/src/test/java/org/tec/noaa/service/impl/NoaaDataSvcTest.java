package org.tec.noaa.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.noaa.NoaaApplication;
import org.tec.noaa.model.response.Data;
import org.tec.noaa.model.response.DataSet;
import org.tec.noaa.model.response.Response;
import org.tec.noaa.service.NoaaDataSetSvc;
import org.tec.noaa.service.NoaaDataSvc;

//@Disabled
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= NoaaApplication.class)
public class NoaaDataSvcTest {

    @Autowired
    NoaaDataSvc noaaDataSvc;

    @Autowired
    NoaaDataSetSvc noaaDataSetSvc;

    @Test
    public void getData() {
        Response<DataSet> dataSetResponse = noaaDataSetSvc.getDataSets();

        Assertions.assertNotNull(dataSetResponse);
        Assertions.assertFalse(dataSetResponse.getResults().isEmpty());

        DataSet ds = dataSetResponse.getResults().get(0);

        Response<Data> response = noaaDataSvc.getData(ds.getId(), ds.getMaxdate().minusDays(7), ds.getMaxdate());

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getResults().isEmpty());
    }
}