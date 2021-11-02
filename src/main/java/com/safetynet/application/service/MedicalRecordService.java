package com.safetynet.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.application.dao.DataDAO;
import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import com.safetynet.application.repository.IFireStationService;
import com.safetynet.application.repository.IMedicalRecordService;
import com.safetynet.application.repository.IPersonService;
import com.safetynet.application.repository.IUtilitiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecordService implements IMedicalRecordService {

    private final static Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private IPersonService IPersonService;

    @Autowired
    private IFireStationService IFireStationService;

    @Autowired
    private IUtilitiesService IUtilitiesService;

    @Autowired
    private DataDAO dataDAO;

    /**
     * Method used for recovering information in a medical record using
     * the firstName and lastName of a person
     * @param firstName
     * @param lastName
     * @return a MedicalRecord object
     */

    @Override
    public MedicalRecord getMedicalRecordDataWithFirstNameAndLastName(String firstName, String lastName) {

        MedicalRecord medicalRecordData = new MedicalRecord();

        for (JsonNode medicalRecordRoot : IUtilitiesService.getRootArray().path("medicalrecords")) {
            if (medicalRecordRoot.path("firstName").asText().equals(firstName) & medicalRecordRoot.path("lastName").asText().equals(lastName)) {

                medicalRecordData.setFirstName(medicalRecordRoot.path("firstName").asText());
                medicalRecordData.setLastName(medicalRecordRoot.path("lastName").asText());
                medicalRecordData.setBirthdate(medicalRecordRoot.path("birthdate").asText());
                List<String> medicationData = getListOfStringFromJson(medicalRecordRoot, "medications");
                medicalRecordData.setMedications(medicationData);
                List<String> allergiesData = getListOfStringFromJson(medicalRecordRoot, "allergies");
                medicalRecordData.setAllergies(allergiesData);
            }
        }

        logger.debug("Retrieving medicalRecord data from " + firstName + " " + lastName);
        return medicalRecordData;
    }

    /**
     * Method used to add a new medicalRecord in the json file
     * Used by DataController class as /medicalRecord, request mapping POST
     * @param medicalRecord
     * Add a new medicalRecord in the json file
     */

    @Override
    public void writeNewMedicalRecordsData(MedicalRecord medicalRecord) {

        List<Person> personList = IPersonService.getAllPerson();
        List<FireStation> fireStationList = IFireStationService.getAllFireStation();
        List<MedicalRecord> medicalRecordsList = getAllMedicalRecord();

        MedicalRecord medicalRecordData = new MedicalRecord();
        medicalRecordData.setFirstName(medicalRecord.getFirstName());
        medicalRecordData.setLastName(medicalRecord.getLastName());
        medicalRecordData.setBirthdate(medicalRecord.getBirthdate());
        medicalRecordData.setMedications(medicalRecord.getMedications());
        medicalRecordData.setAllergies(medicalRecord.getAllergies());

        medicalRecordsList.add(medicalRecordData);

        logger.debug("Adding new medicalrecord data in json file "
                + medicalRecordData.getFirstName() + " "
                + medicalRecordData.getLastName() + " "
                + medicalRecordData.getBirthdate() + " "
                + medicalRecordData.getMedications() + " "
                + medicalRecordData.getAllergies());

        dataDAO.writeAllData(personList, fireStationList, medicalRecordsList);
    }

    /**
     * Method used to update a medicalRecord
     * Used by DataController class as /medicalRecord, request mapping PUT
     * @param medicalRecord
     * Update a medicalRecord in the json file
     */

    @Override
    public void updateMedicalRecordsData(MedicalRecord medicalRecord) {

        List<Person> personList = IPersonService.getAllPerson();
        List<FireStation> fireStationList = IFireStationService.getAllFireStation();
        List<MedicalRecord> medicalRecordsList = getAllMedicalRecord();

        MedicalRecord updateMedicalRecord = new MedicalRecord();
        updateMedicalRecord.setFirstName(medicalRecord.getFirstName());
        updateMedicalRecord.setLastName(medicalRecord.getLastName());
        updateMedicalRecord.setBirthdate(medicalRecord.getBirthdate());
        updateMedicalRecord.setMedications(medicalRecord.getMedications());
        updateMedicalRecord.setAllergies(medicalRecord.getAllergies());

        logger.debug("Updating medicalRecord : " + updateMedicalRecord.getFirstName() + " " + updateMedicalRecord.getLastName());

        for(MedicalRecord medicalRecordData : medicalRecordsList){
            if(updateMedicalRecord.getFirstName().equals(medicalRecordData.getFirstName())&updateMedicalRecord.getLastName().equals(medicalRecordData.getLastName())){
                medicalRecordData.setBirthdate(updateMedicalRecord.getBirthdate());
                medicalRecordData.setMedications(updateMedicalRecord.getMedications());
                medicalRecordData.setAllergies(updateMedicalRecord.getAllergies());

                logger.debug("Modifying person data in json file "
                        + medicalRecordData.getFirstName() + " "
                        + medicalRecordData.getLastName() + " "
                        + medicalRecordData.getBirthdate() + " "
                        + medicalRecordData.getMedications() + " "
                        + medicalRecordData.getAllergies());
            }
        }

        dataDAO.writeAllData(personList, fireStationList, medicalRecordsList);
    }

    /**
     * Method used to delete a medicalRecord using firstName and lastName
     * Used by DataController class as /medicalRecord, request mapping DEL
     * @param firstName
     * @param lastName
     * Delete a medicalRecord
     */

    @Override
    public void deleteMedicalRecordData(String firstName, String lastName){

        List<Person> personList = IPersonService.getAllPerson();
        List<FireStation> fireStationList = IFireStationService.getAllFireStation();
        List<MedicalRecord> medicalRecordsList = getAllMedicalRecord();

        List<MedicalRecord> newMedicalRecordList = new ArrayList<>();

        for(MedicalRecord medicalRecordData : medicalRecordsList){
            String personFirstName = medicalRecordData.getFirstName();
            String personLastName = medicalRecordData.getLastName();
            if(!personFirstName.equals(firstName)&!personLastName.equals(lastName)){
                newMedicalRecordList.add(medicalRecordData);

            }
        }

        logger.debug("Deleting " + firstName + " " + lastName);
        dataDAO.writeAllData(personList, fireStationList, newMedicalRecordList);
    }

    /**
     * Method used for recovering all the medicalRecords in the json file
     * @return a list of all medicalRecord include in the json file
     */

    @Override
    public List<MedicalRecord> getAllMedicalRecord(){

        List<MedicalRecord> medicalRecordList = new ArrayList<>();

        for (JsonNode medicalRecordRoot : IUtilitiesService.getRootArray().path("medicalrecords")) {
            MedicalRecord medicalRecordData = new MedicalRecord();

            medicalRecordData.setFirstName(medicalRecordRoot.path("firstName").asText());
            medicalRecordData.setLastName(medicalRecordRoot.path("lastName").asText());
            medicalRecordData.setBirthdate(medicalRecordRoot.path("birthdate").asText());
            List<String> medicationData = getListOfStringFromJson(medicalRecordRoot, "medications");
            medicalRecordData.setMedications(medicationData);
            List<String> allergiesData = getListOfStringFromJson(medicalRecordRoot, "allergies");
            medicalRecordData.setAllergies(allergiesData);

            medicalRecordList.add(medicalRecordData);
        }

        logger.debug("Retrieving all the medicalRecord data");
        return medicalRecordList;
    }

    /**
     * Method used to wrap the IOException encountered with
     * the objectmapper when he is reading values from a json file
     * @param root
     * @param medicalData
     * @return a List of Strings of values coming from a json file
     */

    public List<String> getMedicalData(JsonNode root, String medicalData) {
        try{
            return mapper.readValue(String.valueOf(root.path(medicalData)), new TypeReference<List<String>>() {});
        } catch(IOException error){
            logger.error("getMedicalData Method, Json file not found");
            throw new IllegalStateException(error);
        }
    }

    /**
     * Method used to extract the json data array into a list of Strings
     * @param root
     * @param rootPointer
     * @return a list of Strings
     */

    public List<String> getListOfStringFromJson(JsonNode root, String rootPointer){
        List<String> listOfStrings = new ArrayList<>();
        List<String> stringExtraction = getMedicalData(root, rootPointer);
        listOfStrings.addAll(stringExtraction);
        return listOfStrings;
    }


}