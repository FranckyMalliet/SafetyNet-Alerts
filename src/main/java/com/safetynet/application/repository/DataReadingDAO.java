package com.safetynet.application.repository;

import com.safetynet.application.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataReadingDAO {

    public List<List<String>> getFireStationResidentData(String fireStationNumber);
    public List<Person> getChildAlert(String address);
    public List<String> getResidentPhone(String fireStationNumber);
    public List<List<String>> getMedicalInformationFromAddress(String address);
    public List<List<String>> getAllPersonsDataFromAListOfFireStation(String stationNumber);
    public List<List<String>> getPersonMedicalData(String firstName, String lastName);
    public List<String> getEmailData(String city);
}
