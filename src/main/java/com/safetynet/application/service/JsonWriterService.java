package com.safetynet.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.application.repository.DataWritingDAO;
import com.safetynet.application.repository.JsonFileCurrentDataDAO;
import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// https://www.baeldung.com/java-check-url-exists

@Service
public class JsonWriterService implements DataWritingDAO {

    private final String path = "E:/Users/Francky Malliet/git/SafetyNet Alerts/dataTest.json";
    private final static Logger logger = LoggerFactory.getLogger(JsonWriterService.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private JsonFileCurrentDataDAO jsonFileCurrentDataDAO = new JsonFileCurrentDataService();

    List<Person> personList = jsonFileCurrentDataDAO.JsonFileWriterPerson();
    List<FireStation> fireStationList = jsonFileCurrentDataDAO.JsonFileWriterFireStation();
    List<MedicalRecord> medicalRecordsList = jsonFileCurrentDataDAO.JsonFileWriterMedicalRecord();

    @Override
    public List<Person> writePersonData(Person person){

        List<Person> persons = new ArrayList<>();
        Person personData = new Person();
        personData.setFirstName(person.getFirstName());
        personData.setLastName(person.getLastName());
        personData.setAddress(person.getAddress());
        personData.setCity(person.getCity());
        personData.setZip(person.getZip());
        personData.setPhone(person.getPhone());
        personData.setEmail(person.getEmail());

        personList.add(personData);
        persons = jsonFileCurrentDataDAO.JsonFileWriterAllData(personList, fireStationList, medicalRecordsList);

        logger.info("Adding a person data in json file");
        return persons;
    }

    @Override
    public boolean writeFireStationData(FireStation fireStation) {
        return false;
    }

    @Override
    public boolean writeMedicalRecordsData(MedicalRecord medicalRecord) {
        return false;
    }

    /* @Override
    public boolean writeFireStationData(FireStation fireStation) {

        try {

        } catch(IOException error){
            logger.error("JsonFile not found");
            error.printStackTrace();
        }
    }

    @Override
    public boolean writeMedicalRecordsData(MedicalRecord medicalRecord) {

        try{


        } catch(IOException error){
            logger.error("JsonFile not found");
            error.printStackTrace();
        }
    }*/
}
