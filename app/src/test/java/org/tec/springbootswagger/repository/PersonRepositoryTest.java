package org.tec.springbootswagger.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.springbootswagger.SpringbootSwaggerApplication;
import org.tec.springbootswagger.entity.PersonEntity;

import javax.transaction.Transactional;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= SpringbootSwaggerApplication.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class PersonRepositoryTest {

    @Autowired
    private transient PersonRepository personRepository;

    @Test
    @Transactional
    public void insertUpdate() {
        PersonEntity expected = new PersonEntity();
        expected.setEmail("test@example.net");
        expected.setFirstName("test");
        expected.setLastName("me");
        expected.setHash("hash");

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
        expected.setHash("hash");

        PersonEntity actual = personRepository.save(expected);

        Assert.assertEquals(expected, actual);

        expected.setId(actual.getId());
        expected.setFirstName("retest");

        personRepository.updateFirstName(expected.getFirstName(), expected.getId());

        Optional<PersonEntity> opt = personRepository.findByEmail(expected.getEmail());

        Assert.assertTrue(opt.isPresent());
        Assert.assertEquals(opt.get(), actual);

        expected.setLastName("metoo");

        personRepository.updateLastName(expected.getLastName(), expected.getId());

        opt = personRepository.findByEmail(expected.getEmail());

        Assert.assertTrue(opt.isPresent());
        Assert.assertEquals(opt.get(), actual);
    }

    @Test
    @Transactional
    public void deleteId() {
        PersonEntity expected = new PersonEntity();
        expected.setEmail("test@example.net");
        expected.setFirstName("test");
        expected.setLastName("me");
        expected.setHash("hash");

        PersonEntity actual = personRepository.save(expected);

        Assert.assertEquals(expected, actual);

        expected.setId(actual.getId());

        personRepository.delete(expected);

        Optional<PersonEntity> opt = personRepository.findByEmail(expected.getEmail());

        Assert.assertTrue(opt.isPresent());
    }

    @Test
    @Transactional
    public void deleteEmail() {
        PersonEntity expected = new PersonEntity();
        expected.setEmail("test@example.net");
        expected.setFirstName("test");
        expected.setLastName("me");
        expected.setHash("hash");

        PersonEntity actual = personRepository.save(expected);

        Assert.assertEquals(expected, actual);

        expected.setId(actual.getId());

        personRepository.deleteByEmail(expected.getEmail());

        Optional<PersonEntity> opt = personRepository.findByEmail(expected.getEmail());

        Assert.assertTrue(opt.isPresent());
    }
}
