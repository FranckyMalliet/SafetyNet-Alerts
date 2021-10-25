package com.safetynet.application.service.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.application.model.Person;
import com.safetynet.application.repository.model.PersonDAO;
import com.safetynet.application.repository.utilities.MethodsUtilDAO;
import com.safetynet.application.service.JsonReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService implements PersonDAO {

    private final static Logger logger = LoggerFactory.getLogger(JsonReaderService.class);

    @Autowired
    private MethodsUtilDAO methodsUtilDAO;

    @Override
    public List<Person> getPersonDataWithAddress(String address) {

        List<Person> personInformation = new ArrayList<>();

        for (JsonNode personRoot : methodsUtilDAO.getRootArray().path("persons")) {
            if (personRoot.path("address").asText().equals(address)) {
                Person personData = new Person();

                personData.setFirstName(personRoot.path("firstName").asText());
                personData.setLastName(personRoot.path("lastName").asText());
                personData.setAddress(personRoot.path("address").asText());
                personData.setCity(personRoot.path("city").asText());
                personData.setZip(personRoot.path("zip").asText());
                personData.setPhone(personRoot.path("phone").asText());
                personData.setEmail(personRoot.path("email").asText());

                personInformation.add(personData);
            }
        }
        return personInformation;
    }

    @Override
    public List<Person> getPersonDataWithLastName(String lastName) {

        List<Person> personInformation = new ArrayList<>();

        for (JsonNode personRoot : methodsUtilDAO.getRootArray().path("persons")) {
            if (personRoot.path("lastName").asText().equals(lastName)) {
                Person personData = new Person();

                personData.setFirstName(personRoot.path("firstName").asText());
                personData.setLastName(personRoot.path("lastName").asText());
                personData.setAddress(personRoot.path("address").asText());
                personData.setCity(personRoot.path("city").asText());
                personData.setZip(personRoot.path("zip").asText());
                personData.setPhone(personRoot.path("phone").asText());
                personData.setEmail(personRoot.path("email").asText());

                personInformation.add(personData);
            }
        }
        return personInformation;
    }

    @Override
    public List<Person> getPersonDataWithFirstNameAndLastName(String firstName, String lastName) {

        List<Person> personInformation = new ArrayList<>();

        for (JsonNode personRoot : methodsUtilDAO.getRootArray().path("persons")) {
            if (personRoot.path("firstName").asText().equals(firstName)&personRoot.path("lastName").asText().equals(lastName)) {
                Person personData = new Person();

                personData.setFirstName(personRoot.path("firstName").asText());
                personData.setLastName(personRoot.path("lastName").asText());
                personData.setAddress(personRoot.path("address").asText());
                personData.setCity(personRoot.path("city").asText());
                personData.setZip(personRoot.path("zip").asText());
                personData.setPhone(personRoot.path("phone").asText());
                personData.setEmail(personRoot.path("email").asText());

                personInformation.add(personData);
            }
        }
        return personInformation;
    }

    @Override
    public List<Person> getAllPerson(){

        List<Person> personList = new ArrayList<>();

        for (JsonNode root : methodsUtilDAO.getRootArray().get("persons")) {
            Person personData = new Person();

            personData.setFirstName(root.path("firstName").asText());
            personData.setLastName(root.path("lastName").asText());
            personData.setAddress(root.path("address").asText());
            personData.setCity(root.path("city").asText());
            personData.setZip(root.path("zip").asText());
            personData.setPhone(root.path("phone").asText());
            personData.setEmail(root.path("email").asText());

            personList.add(personData);
        }

        return personList;
    }
}

