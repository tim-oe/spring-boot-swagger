package org.tec.springbootswagger.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tec.springbootswagger.SpringbootSwaggerApplication;
import org.tec.springbootswagger.model.entity.PersonEntity;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= SpringbootSwaggerApplication.class)
public class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Test
    @Transactional
    public void insertUpdate() {
        PersonEntity expected = new PersonEntity();
        expected.setEmail("test@example.net");
        expected.setFirstName("test");
        expected.setLastName("me");

        PersonEntity actual = personRepository.save(expected);

        Assert.assertEquals(expected, actual);

        expected.setId(actual.getId());
        expected.setFirstName("retest");
        expected.setLastName("metoo");

        actual = personRepository.save(expected);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void insertFieldUpdate() {
        PersonEntity expected = new PersonEntity();
        expected.setEmail("test@example.net");
        expected.setFirstName("test");
        expected.setLastName("me");

        PersonEntity actual = personRepository.save(expected);

        Assert.assertEquals(expected, actual);

        expected.setId(actual.getId());
        expected.setFirstName("retest");

        personRepository.updateFirstName(expected.getFirstName(), expected.getId());

        actual = personRepository.findByEmail(expected.getEmail()).get();

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);

        expected.setLastName("metoo");

        personRepository.updateLastName(expected.getLastName(), expected.getId());

        actual = personRepository.findByEmail(expected.getEmail()).get();

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void deleteId() {
        PersonEntity expected = new PersonEntity();
        expected.setEmail("test@example.net");
        expected.setFirstName("test");
        expected.setLastName("me");

        PersonEntity actual = personRepository.save(expected);

        Assert.assertEquals(expected, actual);

        expected.setId(actual.getId());

        personRepository.delete(expected);

        actual = personRepository.findByEmail(expected.getEmail()).orElse(null);

        Assert.assertNull(actual);
    }

    @Test
    @Transactional
    public void deleteEmail() {
        PersonEntity expected = new PersonEntity();
        expected.setEmail("test@example.net");
        expected.setFirstName("test");
        expected.setLastName("me");

        PersonEntity actual = personRepository.save(expected);

        Assert.assertEquals(expected, actual);

        expected.setId(actual.getId());

        personRepository.deleteByEmail(expected.getEmail());

        actual = personRepository.findByEmail(expected.getEmail()).orElse(null);

        Assert.assertNull(actual);
    }
}
