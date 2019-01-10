package com.outboundengine.contacthygien.contoller;

import com.outboundengine.contacthygien.entity.DatasourceInformationDba;
import com.outboundengine.contacthygien.model.Response;
import com.outboundengine.contacthygien.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;


@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    DatasourceInformationDba datasourceInformationDba;

    @GetMapping("/v1.0")
    public Response<Status> statusv1() {
        Response<Status> resp = new Response();

        Status stat = new Status();
        resp.setData(stat);

        stat.setApiVersion("1.0");
        stat.setDatasourceClass(dataSource.getClass().getName());
        stat.setDatabaseVersion(datasourceInformationDba.getVersion());

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

        return  resp;
    }
}
