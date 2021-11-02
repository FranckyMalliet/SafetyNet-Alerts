package com.safetynet.application.repository;

import com.safetynet.application.model.Person;

import java.util.List;

public interface IPersonServiceTest {

    public List<Person> getPersonDataWithAddress(String address);
    public List<Person> getPersonDataWithLastName(String lastName);
    public List<Person> getPersonDataWithFirstNameAndLastName(String firstName, String lastName);
    public void writeNewPersonData(Person person);
    public void updatePersonData(Person person);
    public void deletePersonData(String firstName, String lastName);
    public List<Person> getAllPerson();
    public List<List<String>> getFireStationResidentData(String fireStationNumber);
    public List<Person> getChildAlert(String address);
    public List<String> getResidentPhone(String fireStationNumber);
    public List<List<String>> getMedicalInformationFromAddress(String address);
    public List<List<String>> getAllPersonsDataFromAListOfFireStation(String stationNumber);
    public List<List<String>> getPersonMedicalData(String firstName, String lastName);
    public List<String> getEmailData(String city);
}
