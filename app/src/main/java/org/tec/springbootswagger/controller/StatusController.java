package org.tec.springbootswagger.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tec.springbootswagger.dba.DatasourceInformationDba;
import org.tec.springbootswagger.model.Response;
import org.tec.springbootswagger.model.Status;

import javax.sql.DataSource;

@Slf4j
@RestController
// WARNING make sure to set produces json or it will try
@RequestMapping(value = StatusController.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class StatusController {

    public static final String PATH = "/status";

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

        log.debug(resp.toString());

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

        log.debug(resp.toString());

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

        log.debug(resp.toString());

        return  resp;
    }
}
