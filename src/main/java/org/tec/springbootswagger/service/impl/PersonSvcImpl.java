package org.tec.springbootswagger.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tec.springbootswagger.model.dto.PersonDto;
import org.tec.springbootswagger.model.entity.PersonEntity;
import org.tec.springbootswagger.repository.PersonRepository;
import org.tec.springbootswagger.service.PersonSvc;

@Service
public class PersonSvcImpl implements PersonSvc {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PersonDto create(PersonDto person) {
        PersonEntity pe = modelMapper.map(person, PersonEntity.class);
        pe = personRepository.save(pe);
        return modelMapper.map(pe, PersonDto.class);
    }

    @Override
    public PersonDto update(PersonDto person) {
        if(person.getId() <= 0) {
            throw new IllegalArgumentException("person doesn't have id set " + person);
        }
        return create(person);
    }

    @Override
    public PersonDto getPerson(String email) {
        PersonEntity pe = personRepository.findByEmail(email).orElse(null);
        if(pe != null) {
            return modelMapper.map(pe, PersonDto.class);
        } else {
            return null;
        }
    }

    @Override
    public void setFirstname(long id, String firstName) {
        personRepository.updateFirstName(firstName, id);
    }

    @Override
    public void setLastname(long id, String lastName) {
        personRepository.updateLastName(lastName, id);
    }

    @Override
    public void deletePerson(String email) {
        personRepository.deleteByEmail(email);
    }
}
