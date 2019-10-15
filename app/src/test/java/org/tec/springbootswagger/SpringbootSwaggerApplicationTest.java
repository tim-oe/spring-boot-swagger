package org.tec.springbootswagger;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=SpringbootSwaggerApplication.class)
public class SpringbootSwaggerApplicationTest {

    @Autowired
    private transient DataSource dataSource;

    @Test
    @DisplayName("simple test to verify data source")
    public void hikariConnectionPoolIsConfigured() {
        Assertions.assertNotNull(dataSource, "datasource not available");
        Assertions.assertEquals(HikariDataSource.class.getName(), dataSource.getClass().getName(), "datasource is not HikariCP " + dataSource.getClass().getName());
    }

    @Test
    public void contextLoads() {
        //TBD
    }
}
