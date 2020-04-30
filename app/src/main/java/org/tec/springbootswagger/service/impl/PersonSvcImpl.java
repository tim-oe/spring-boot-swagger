package org.tec.springbootswagger.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.tec.springbootswagger.entity.PersonEntity;
import org.tec.springbootswagger.model.dto.PersonDto;
import org.tec.springbootswagger.repository.PersonRepository;
import org.tec.springbootswagger.service.PersonSvc;

import java.util.Optional;

/**
 * https://www.baeldung.com/get-user-in-spring-security
 */
@Service
public class PersonSvcImpl implements PersonSvc {

    @Autowired
    protected transient PersonRepository personRepository;

    @Autowired
    protected transient ModelMapper modelMapper;

    @Override
    public PersonDto create(final PersonDto person) {
        PersonEntity pe = modelMapper.map(person, PersonEntity.class);
        pe = personRepository.save(pe);
        return modelMapper.map(pe, PersonDto.class);
    }

    @Override
    public PersonDto update(final PersonDto person) {
        if(person.getId() <= 0) {
            throw new IllegalArgumentException("person doesn't have id set " + person);
        }
        return create(person);
    }

    @Override
    public PersonDto getPerson(final String email) {
        final Optional<PersonEntity> pe = personRepository.findByEmail(email);
        return pe.isPresent() ? modelMapper.map(pe.get(), PersonDto.class) : null;
    }

    @Override
    public PersonDto getCurrentPerson() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDto pt = null;
        if(authentication != null) {
            final Optional<PersonEntity> pe = personRepository.findByEmail(authentication.getName());
            if(pe.isPresent()) {
                pt = modelMapper.map(pe.get(), PersonDto.class);
            }
        }
        return pt;
    }

    @Override
    public void setFirstname(final long id, final String firstName) {
        personRepository.updateFirstName(firstName, id);
    }

    @Override
    public void setLastname(final long id, final String lastName) {
        personRepository.updateLastName(lastName, id);
    }

    @Override
    public void deletePerson(final String email) {
        personRepository.deleteByEmail(email);
    }
}
