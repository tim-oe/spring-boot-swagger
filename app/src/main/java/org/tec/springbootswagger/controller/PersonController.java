package org.tec.springbootswagger.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tec.springbootswagger.model.Response;
import org.tec.springbootswagger.model.dto.PersonDto;
import org.tec.springbootswagger.service.PersonSvc;

@Slf4j
@RestController
@RequestMapping(value = PersonController.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    public static final String PATH = "/person";

    @Autowired
    private transient PersonSvc personSvc;

    @GetMapping
    public Response<PersonDto> get() {
        final Response<PersonDto> resp = new Response();

        resp.setData(personSvc.getCurrentPerson());

        log.debug(resp.toString());

        return  resp;
    }
}
