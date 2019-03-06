package org.tec.springbootswagger.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tec.springbootswagger.SpringbootSwaggerApplication;
import org.tec.springbootswagger.model.dto.PersonDto;
import org.tec.springbootswagger.service.PersonSvc;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
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

        Assert.assertEquals(expected, actual);

        expected.setId(actual.getId());
        expected.setFirstName("retest");
        expected.setLastName("metoo");

        actual = personSvc.update(expected);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void insertFieldUpdate() {
        PersonDto expected = new PersonDto();
        expected.setEmail("test@example.net");
        expected.setFirstName("test");
        expected.setLastName("me");

        PersonDto actual = personSvc.create(expected);

        Assert.assertEquals(expected, actual);

        expected.setId(actual.getId());
        expected.setFirstName("retest");

        personSvc.setFirstname(expected.getId(), expected.getFirstName());

        actual = personSvc.getPerson(expected.getEmail());

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);

        expected.setLastName("metoo");

        personSvc.setLastname(expected.getId(), expected.getLastName());

        actual = personSvc.getPerson(expected.getEmail());

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void deleteEmail() {
        PersonDto expected = new PersonDto();
        expected.setEmail("test@example.net");
        expected.setFirstName("test");
        expected.setLastName("me");

        PersonDto actual = personSvc.create(expected);

        Assert.assertEquals(expected, actual);

        Assert.assertEquals(expected, actual);

        expected.setId(actual.getId());

        personSvc.deletePerson(expected.getEmail());

        actual = personSvc.getPerson(expected.getEmail());

        Assert.assertNull(actual);
    }
}
