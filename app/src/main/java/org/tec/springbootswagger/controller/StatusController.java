package org.tec.springbootswagger.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.tec.springbootswagger.entity.DatasourceInformationDba;
import org.tec.springbootswagger.model.Response;
import org.tec.springbootswagger.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@Log4j2
@RestController
// WARNING make sure to set produces json or it will try
@RequestMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatusController {

    @Autowired
    protected transient DataSource dataSource;

    @Autowired
    protected transient DatasourceInformationDba datasourceInformationDba;

    @GetMapping("/v1.0")
    public Response<Status> statusv1() {
        Response<Status> resp = new Response();

        Status stat = new Status();
        resp.setData(stat);

        stat.setApiVersion("1.0");
        stat.setDatasourceClass(dataSource.getClass().getName());
        stat.setDatabaseVersion(datasourceInformationDba.getVersion());

        log.debug(resp);

        return  resp;
    }

    @GetMapping("/v2.0")
    public Response<Status> statusv2() {
        Response<Status> resp = new Response();

        Status stat = new Status();
        resp.setData(stat);

        stat.setApiVersion("2.0");
        stat.setDatasourceClass(dataSource.getClass().getName());
        stat.setDatabaseVersion(datasourceInformationDba.getVersion());

        log.debug(resp);

        return  resp;
    }

    @GetMapping("/v3.0")
    public Response<Status> statusv3() {
        Response<Status> resp = new Response();

        Status stat = new Status();
        resp.setData(stat);

        stat.setApiVersion("3.0");
        stat.setDatasourceClass(dataSource.getClass().getName());
        stat.setDatabaseVersion(datasourceInformationDba.getVersion());

        log.debug(resp);

        return  resp;
    }
}
