package com.outboundengine.contacthygien;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactHygienApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    public void hikariConnectionPoolIsConfigured() {
        Assert.assertNotNull("datasource not available", dataSource);
        Assert.assertEquals("datasource is not HikariCP " + dataSource.getClass().getName(), HikariDataSource.class.getName(), dataSource.getClass().getName());
    }

    @Test
    public void contextLoads() {
        //TBD
    }
}

