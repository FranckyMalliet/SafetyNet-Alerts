package com.safetynet.application.repository;

import com.safetynet.application.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataReadingDAO {


    public List<String> getEmailData(String city);
    //public List<Person> readPersonData(String firstName, String lastName);
    public List<List<String>> readPersonMedicalData(String firstName, String lastName);
    public List<String> fireStationResidentData(String fireStationNumber);
}
