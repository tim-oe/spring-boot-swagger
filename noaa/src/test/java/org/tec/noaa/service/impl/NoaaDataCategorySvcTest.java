package org.tec.noaa.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.noaa.NoaaApplication;
import org.tec.noaa.service.NoaaDataCategorySvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= NoaaApplication.class)
class NoaaDataCategorySvcTest {

    @Autowired
    protected transient NoaaDataCategorySvc noaaDataCategorySvc;

    @Test
    void getDataCategories() {
        noaaDataCategorySvc.getDataCategories();
    }
}