package org.tec.springbootswagger.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.springbootswagger.SpringbootSwaggerApplication;
import org.tec.springbootswagger.model.dto.PersonDto;
import org.tec.springbootswagger.service.PersonSvc;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= SpringbootSwaggerApplication.class)
public class PersonSvcTest {

    @Autowired
    PersonSvc personSvc;

    @Test
    @Transactional
    public void insertUpdate() {
        PersonDto expected = new PersonDto();
        expected.setEmail("test@example.net");
        expected.setFirstName("test");
        expected.setLastName("me");

        PersonDto actual = personSvc.create(expected);

        Assertions.assertEquals(expected, actual);

        expected.setId(actual.getId());
        expected.setFirstName("retest");
        expected.setLastName("metoo");

        actual = personSvc.update(expected);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void insertFieldUpdate() {
        PersonDto expected = new PersonDto();
        expected.setEmail("test@example.net");
        expected.setFirstName("test");
        expected.setLastName("me");

        PersonDto actual = personSvc.create(expected);

        Assertions.assertEquals(expected, actual);

        expected.setId(actual.getId());
        expected.setFirstName("retest");

        personSvc.setFirstname(expected.getId(), expected.getFirstName());

        actual = personSvc.getPerson(expected.getEmail());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);

        expected.setLastName("metoo");

        personSvc.setLastname(expected.getId(), expected.getLastName());

        actual = personSvc.getPerson(expected.getEmail());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void deleteEmail() {
        PersonDto expected = new PersonDto();
        expected.setEmail("test@example.net");
        expected.setFirstName("test");
        expected.setLastName("me");

        PersonDto actual = personSvc.create(expected);

        Assertions.assertEquals(expected, actual);

        Assertions.assertEquals(expected, actual);

        expected.setId(actual.getId());

        personSvc.deletePerson(expected.getEmail());

        actual = personSvc.getPerson(expected.getEmail());

        Assertions.assertNull(actual);
    }
}