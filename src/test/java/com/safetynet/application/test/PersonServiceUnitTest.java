package com.safetynet.application.test;

import com.safetynet.application.model.Person;
import com.safetynet.application.repository.IPersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonServiceUnitTest {

    @Autowired
    private IPersonService IPersonServiceTest;

    // File path for testing
    private final String path = "E:/Users/Francky Malliet/git/SafetyNet Alerts/dataTest.json";

    // Mock file data for testing
    // Mock person data
    private final String firstName = "John";
    private final String lastName = "Boyd";
    private final String address = "1509 Culver St";
    private final String city = "Culver";
    private final String zip = "97451";
    private final String phone = "841-874-6501";
    private final String email = "JohnsonSmith@email.com";

    // For Person testing
    private List<Person> personList = new ArrayList<>();
    private Person personDataRecovered = new Person();

    @Test
    public void getPersonDataWithAddressTest(){
        //WHEN
        personList = IPersonServiceTest.getPersonDataWithAddress(address);
        for(Person personElement : personList){
            personDataRecovered.setFirstName(personElement.getFirstName());
            personDataRecovered.setLastName(personElement.getLastName());
            personDataRecovered.setAddress(personElement.getAddress());
        }

        //THEN
        Assertions.assertNotNull(personDataRecovered.getFirstName(), personDataRecovered.getLastName());
        Assertions.assertEquals(address, personDataRecovered.getAddress());
    }

    @Test
    public void getPersonDataWithLastNameTest(){
        //WHEN
        personList = IPersonServiceTest.getPersonDataWithLastName(lastName);
        for(Person personElement : personList){
            personDataRecovered.setLastName(personElement.getLastName());
        }

        //THEN
        Assertions.assertNotNull(personList);
        Assertions.assertEquals(lastName, personDataRecovered.getLastName());
    }

    @Test
    public void getPersonDataWithFirstNameAndLastNameTest(){
        //WHEN
        personList = IPersonServiceTest.getPersonDataWithFirstNameAndLastName(firstName, lastName);
        for(Person personElement : personList){
            personDataRecovered.setFirstName(personElement.getFirstName());
            personDataRecovered.setLastName(personElement.getLastName());
        }

        //THEN
        Assertions.assertNotNull(personList);
        Assertions.assertEquals(firstName, personDataRecovered.getFirstName());
        Assertions.assertEquals(lastName, personDataRecovered.getLastName());
    }

    @Test
    public void getAllPersonTest(){
        //WHEN
        personList = IPersonServiceTest.getAllPerson();

        //THEN
        Assertions.assertNotNull(personList);
    }
}
