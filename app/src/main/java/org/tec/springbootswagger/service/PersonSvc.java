package org.tec.springbootswagger.service;

import org.tec.springbootswagger.model.dto.PersonDto;

public interface PersonSvc {

    /**
     * create instance of a person
     * @param person the person to create
     * @return the complete dto instance
     */
    PersonDto create(PersonDto person);

    /**
     * update person
     * @param person the person to update
     * @return the updated person
     */
    PersonDto update(PersonDto person);

    /**
     * get a person by email
     * @param email the email of the person
     * @return the person object associated with the email
     */
    PersonDto getPerson(String email);

    /**
     * get the current logged in user
     * @return the current user
     */
    PersonDto getCurrentPerson();

    /**
     * set a person's first name
     * @param id the identity of the person
     * @param firstName the first name to set
     */
    void setFirstname(long id, String firstName);

    /**
     * set a person's last name
     * @param id the identity of the person
     * @param lastName the last name to set
     */
    void setLastname(long id, String lastName);

    /**
     * delete user by email
     * @param email the email for the user to delete
     */
    void deletePerson(String email);
}
