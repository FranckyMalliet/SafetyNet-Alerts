package com.safetynet.application.repository.model;

import com.safetynet.application.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonDAO {

    public List<Person> getPersonDataWithAddress(String address);
    public List<Person> getPersonDataWithLastName(String lastName);
    public List<Person> getPersonDataWithFirstNameAndLastName(String firstName, String lastName);
    public List<Person> getAllPerson();
}
