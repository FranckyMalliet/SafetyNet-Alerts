package com.safetynet.application.service;

import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import com.safetynet.application.repository.DataDeletingDAO;
import com.safetynet.application.repository.DataWritingDAO;
import com.safetynet.application.repository.model.FireStationDAO;
import com.safetynet.application.repository.model.MedicalRecordDAO;
import com.safetynet.application.repository.model.PersonDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JsonDeleteService implements DataDeletingDAO {

    private final static Logger logger = LoggerFactory.getLogger(JsonWriterService.class);

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private FireStationDAO fireStationDAO;

    @Autowired
    private MedicalRecordDAO medicalRecordDAO;

    @Autowired
    private DataWritingDAO dataWritingDAO;

    @Override
    public void deletePersonData(String firstName, String lastName){

        List<Person> personList = personDAO.getAllPerson();
        List<FireStation> fireStationList = fireStationDAO.getAllFireStation();
        List<MedicalRecord> medicalRecordsList = medicalRecordDAO.getAllMedicalRecord();

        List<Person> newPersonList = new ArrayList<>();

        for(Person personData : personList){
            String personFirstName = personData.getFirstName();
            String personLastName = personData.getLastName();
            if(!personFirstName.equals(firstName)&!personLastName.equals(lastName)){
                newPersonList.add(personData);

            }
        }

        logger.info("Deleting " + firstName + " " + lastName);

        dataWritingDAO.writeAllData(newPersonList, fireStationList, medicalRecordsList);
    }

    @Override
    public void deleteFireStationData(String address, String stationNumber){

        List<Person> personList = personDAO.getAllPerson();
        List<FireStation> fireStationList = fireStationDAO.getAllFireStation();
        List<MedicalRecord> medicalRecordsList = medicalRecordDAO.getAllMedicalRecord();

        List<FireStation> newFireStationList = new ArrayList<>();

        for(FireStation fireStationData : fireStationList){
            String fireStationAddress = fireStationData.getAddress();
            String fireStationNumber = fireStationData.getStation();
            if(!fireStationAddress.equals(address)&!fireStationNumber.equals(stationNumber)){
                newFireStationList.add(fireStationData);
            }
        }

        logger.info("Deleting station number :" + stationNumber + " at " + address);

        dataWritingDAO.writeAllData(personList, newFireStationList, medicalRecordsList);
    }

    @Override
    public void deleteMedicalRecordData(String firstName, String lastName){

        List<Person> personList = personDAO.getAllPerson();
        List<FireStation> fireStationList = fireStationDAO.getAllFireStation();
        List<MedicalRecord> medicalRecordsList = medicalRecordDAO.getAllMedicalRecord();

        List<MedicalRecord> newMedicalRecordList = new ArrayList<>();

        for(MedicalRecord medicalRecordData : medicalRecordsList){
            String personFirstName = medicalRecordData.getFirstName();
            String personLastName = medicalRecordData.getLastName();
            if(!personFirstName.equals(firstName)&!personLastName.equals(lastName)){
                newMedicalRecordList.add(medicalRecordData);

            }
        }

        logger.info("Deleting " + firstName + " " + lastName);

        dataWritingDAO.writeAllData(personList, fireStationList, newMedicalRecordList);
    }
}

