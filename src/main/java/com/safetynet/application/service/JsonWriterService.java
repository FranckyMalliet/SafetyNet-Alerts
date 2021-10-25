package com.safetynet.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import com.safetynet.application.repository.DataWritingDAO;
import com.safetynet.application.repository.model.FireStationDAO;
import com.safetynet.application.repository.model.MedicalRecordDAO;
import com.safetynet.application.repository.model.PersonDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JsonWriterService implements DataWritingDAO {

    private final String path = "E:/Users/Francky Malliet/git/SafetyNet-Alerts/data.json";
    private final static Logger logger = LoggerFactory.getLogger(JsonWriterService.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private FireStationDAO fireStationDAO;

    @Autowired
    private MedicalRecordDAO medicalRecordDAO;

    private Map<String, List> allDataMap = new HashMap<>();

    @Override
    public void writeNewPersonData(Person person){

        List<Person> personList = personDAO.getAllPerson();
        List<FireStation> fireStationList = fireStationDAO.getAllFireStation();
        List<MedicalRecord> medicalRecordsList = medicalRecordDAO.getAllMedicalRecord();

        Person personData = new Person();
        personData.setFirstName(person.getFirstName());
        personData.setLastName(person.getLastName());
        personData.setAddress(person.getAddress());
        personData.setCity(person.getCity());
        personData.setZip(person.getZip());
        personData.setPhone(person.getPhone());
        personData.setEmail(person.getEmail());

        personList.add(personData);

        logger.info("Adding new person data in json file "
                + personData.getFirstName() + " "
                + personData.getLastName() + " "
                + personData.getAddress() + " "
                + personData.getCity() + " "
                + personData.getZip() + " "
                + personData.getPhone() + " "
                + personData.getEmail());

        writeAllData(personList, fireStationList, medicalRecordsList);
    }

    @Override
    public void writeNewFireStationData(FireStation fireStation) {

        List<Person> personList = personDAO.getAllPerson();
        List<FireStation> fireStationList = fireStationDAO.getAllFireStation();
        List<MedicalRecord> medicalRecordsList = medicalRecordDAO.getAllMedicalRecord();

        FireStation fireStationData = new FireStation();
        fireStationData.setAddress(fireStation.getAddress());
        fireStationData.setStation(fireStation.getStation());

        fireStationList.add(fireStationData);

        logger.info("Adding new fireStation data in json file "
                + fireStationData.getAddress() + " "
                + fireStationData.getStation() + " ");

        writeAllData(personList, fireStationList, medicalRecordsList);
    }

    @Override
    public void writeNewMedicalRecordsData(MedicalRecord medicalRecord) {

        List<Person> personList = personDAO.getAllPerson();
        List<FireStation> fireStationList = fireStationDAO.getAllFireStation();
        List<MedicalRecord> medicalRecordsList = medicalRecordDAO.getAllMedicalRecord();

        MedicalRecord medicalRecordData = new MedicalRecord();
        medicalRecordData.setFirstName(medicalRecord.getFirstName());
        medicalRecordData.setLastName(medicalRecord.getLastName());
        medicalRecordData.setBirthdate(medicalRecord.getBirthdate());
        medicalRecordData.setMedications(medicalRecord.getMedications());
        medicalRecordData.setAllergies(medicalRecord.getAllergies());

        medicalRecordsList.add(medicalRecordData);

        logger.info("Adding new medicalrecord data in json file "
                + medicalRecordData.getFirstName() + " "
                + medicalRecordData.getLastName() + " "
                + medicalRecordData.getBirthdate() + " "
                + medicalRecordData.getMedications() + " "
                + medicalRecordData.getAllergies());

        writeAllData(personList, fireStationList, medicalRecordsList);
    }

    @Override
    public Map<String, List> writeAllData(List<Person> personList, List<FireStation> fireStationList, List<MedicalRecord> medicalRecordsList){

        allDataMap.put("persons", personList);
        allDataMap.put("firestations", fireStationList);
        allDataMap.put("medicalrecords", medicalRecordsList);

        try {
            mapper.writeValue(new File(path),allDataMap);
        } catch(IOException error){
            logger.error("Json file not found");
            throw new IllegalStateException(error);
        }
        return allDataMap;
    }
}