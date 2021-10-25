package com.safetynet.application.service;

import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import com.safetynet.application.repository.DataUpdatingDAO;
import com.safetynet.application.repository.DataWritingDAO;
import com.safetynet.application.repository.model.FireStationDAO;
import com.safetynet.application.repository.model.MedicalRecordDAO;
import com.safetynet.application.repository.model.PersonDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonUpdateService implements DataUpdatingDAO {

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
    public void updatePersonData(Person person) {

        List<Person> personList = personDAO.getAllPerson();
        List<FireStation> fireStationList = fireStationDAO.getAllFireStation();
        List<MedicalRecord> medicalRecordsList = medicalRecordDAO.getAllMedicalRecord();

        Person updatePerson = new Person();
        updatePerson.setFirstName(person.getFirstName());
        updatePerson.setLastName(person.getLastName());
        updatePerson.setAddress(person.getAddress());
        updatePerson.setCity(person.getCity());
        updatePerson.setZip(person.getZip());
        updatePerson.setPhone(person.getPhone());
        updatePerson.setEmail(person.getEmail());

        logger.info("Updating person : " + updatePerson.getFirstName() + " " + updatePerson.getLastName());

        for(Person personData : personList){
            if(updatePerson.getFirstName().equals(personData.getFirstName())&updatePerson.getLastName().equals(personData.getLastName())){
                personData.setAddress(updatePerson.getAddress());
                personData.setCity(updatePerson.getCity());
                personData.setZip(updatePerson.getZip());
                personData.setPhone(updatePerson.getPhone());
                personData.setEmail(updatePerson.getEmail());

                logger.info("Modifying person data in json file "
                        + personData.getFirstName() + " "
                        + personData.getLastName() + " "
                        + personData.getAddress() + " "
                        + personData.getCity() + " "
                        + personData.getZip() + " "
                        + personData.getPhone() + " "
                        + personData.getEmail());
            }
        }

        dataWritingDAO.writeAllData(personList, fireStationList, medicalRecordsList);
    }

    @Override
    public void updateFireStationData(FireStation fireStation) {

        List<Person> personList = personDAO.getAllPerson();
        List<FireStation> fireStationList = fireStationDAO.getAllFireStation();
        List<MedicalRecord> medicalRecordsList = medicalRecordDAO.getAllMedicalRecord();

        FireStation updateFireStation = new FireStation();
        updateFireStation.setAddress(fireStation.getAddress());
        updateFireStation.setStation(fireStation.getStation());

        logger.info("Updating fireStation : " + updateFireStation.getAddress() + " " + updateFireStation.getStation());

        for (FireStation fireStationData : fireStationList) {
            if (updateFireStation.getAddress().equals(fireStationData.getAddress())) {
                fireStationData.setStation(updateFireStation.getStation());

                logger.info("Modifying fireStation data in json file "
                        + fireStationData.getAddress() + " "
                        + fireStationData.getStation());
            }

            dataWritingDAO.writeAllData(personList, fireStationList, medicalRecordsList);
        }
    }

    @Override
    public void updateMedicalRecordsData(MedicalRecord medicalRecord) {
        List<Person> personList = personDAO.getAllPerson();
        List<FireStation> fireStationList = fireStationDAO.getAllFireStation();
        List<MedicalRecord> medicalRecordsList = medicalRecordDAO.getAllMedicalRecord();

        MedicalRecord updateMedicalRecord = new MedicalRecord();
        updateMedicalRecord.setFirstName(medicalRecord.getFirstName());
        updateMedicalRecord.setLastName(medicalRecord.getLastName());
        updateMedicalRecord.setBirthdate(medicalRecord.getBirthdate());
        updateMedicalRecord.setMedications(medicalRecord.getMedications());
        updateMedicalRecord.setAllergies(medicalRecord.getAllergies());

        logger.info("Updating medicalRecord : " + updateMedicalRecord.getFirstName() + " " + updateMedicalRecord.getLastName());

        for(MedicalRecord medicalRecordData : medicalRecordsList){
            if(updateMedicalRecord.getFirstName().equals(medicalRecordData.getFirstName())&updateMedicalRecord.getLastName().equals(medicalRecordData.getLastName())){
                medicalRecordData.setBirthdate(updateMedicalRecord.getBirthdate());
                medicalRecordData.setMedications(updateMedicalRecord.getMedications());
                medicalRecordData.setAllergies(updateMedicalRecord.getAllergies());

                logger.info("Modifying person data in json file "
                        + medicalRecordData.getFirstName() + " "
                        + medicalRecordData.getLastName() + " "
                        + medicalRecordData.getBirthdate() + " "
                        + medicalRecordData.getMedications() + " "
                        + medicalRecordData.getAllergies());
            }
        }

        dataWritingDAO.writeAllData(personList, fireStationList, medicalRecordsList);
    }
}

