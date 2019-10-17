package org.tec.springbootswagger.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tec.springbootswagger.model.Response;
import org.tec.springbootswagger.model.Status;
import org.tec.springbootswagger.service.VersionScv;

import javax.sql.DataSource;

@Slf4j
@RestController
@RequestMapping(value = StatusController.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class StatusController {

    public static final String PATH = "/status";

    @Autowired
    protected transient DataSource dataSource;

    @Autowired
    protected transient VersionScv versionScv;

    @GetMapping
    public Response<Status> statusv1() {
        Response<Status> resp = new Response();

        Status stat = new Status();
        stat.setVersionInformation(versionScv.getVersionInformation());

        resp.setData(stat);

        log.debug(resp.toString());

        return  resp;
    }
}
