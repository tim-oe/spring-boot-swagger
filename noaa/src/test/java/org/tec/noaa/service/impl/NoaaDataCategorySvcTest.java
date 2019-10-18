package org.tec.noaa.service.impl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.noaa.NoaaApplication;
import org.tec.noaa.service.NoaaDataCategorySvc;

@Disabled
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= NoaaApplication.class)
class NoaaDataCategorySvcTest {

    @Autowired
    NoaaDataCategorySvc noaaDataCategorySvc;

    @Test
    void getDataCategories() {
        noaaDataCategorySvc.getDataCategories();
    }
}