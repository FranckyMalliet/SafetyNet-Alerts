package com.safetynet.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.application.repository.JsonFileCurrentDataDAO;
import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class JsonFileCurrentDataService implements JsonFileCurrentDataDAO {

    private final String path = "E:/Users/Francky Malliet/git/SafetyNet Alerts/data.json";
    private final Logger logger = LoggerFactory.getLogger(JsonFileCurrentDataService.class);
    private final ObjectMapper mapper = new ObjectMapper();

    private Map<Integer, FireStation> fireStationMap;
    private Map<Integer, MedicalRecord> medicalRecordsMap;
    private List<Object> allDataMap = new ArrayList<>();

    public List<Person> JsonFileWriterPerson() {
        List<Person> personList = new ArrayList<>();

        JsonNode rootArray = getRootArray();

        for (JsonNode root : rootArray.get("persons")) {
            Person personData = new Person();

            String firstName = root.path("firstName").asText();
            String lastName = root.path("lastName").asText();
            String address = root.path("address").asText();
            String city = root.path("city").asText();
            String zip = root.path("zip").asText();
            String phone = root.path("phone").asText();
            String email = root.path("email").asText();

            personData.setFirstName(firstName);
            personData.setLastName(lastName);
            personData.setAddress(address);
            personData.setCity(city);
            personData.setZip(zip);
            personData.setPhone(phone);
            personData.setEmail(email);

            personList.add(personData);
        }

        return personList;
        //return allDataMap.add(personMap);
    }

    public List<FireStation> JsonFileWriterFireStation() {
        List<FireStation> fireStationList = new ArrayList<>();

        JsonNode rootArray = getRootArray();

        for (JsonNode root : rootArray.get("firestations")) {
            FireStation fireStationData = new FireStation();

            String fireStationAddress = root.path("address").asText();
            String stationNumber = root.path("station").asText();

            fireStationData.setFireStationAddress(fireStationAddress);
            fireStationData.setStationNumber(stationNumber);

            fireStationList.add(fireStationData);
        }

        return fireStationList;
    }

    public List<MedicalRecord> JsonFileWriterMedicalRecord() {
        List<MedicalRecord> medicalRecordsList = new ArrayList<>();

        JsonNode rootArray = getRootArray();

        for (JsonNode root : rootArray.get("medicalrecords")) {
            MedicalRecord medicalRecordData = new MedicalRecord();

            String firstName = root.path("firstName").asText();
            String lastName = root.path("lastName").asText();
            String birthDate = root.path("birthdate").asText();

            medicalRecordData.setFirstName(firstName);
            medicalRecordData.setLastName(lastName);
            medicalRecordData.setBirthDate(birthDate);

            List<String> medicationList = new ArrayList<>();
            List<String> medicationData = getMedicalData(root, "medications");
            medicationData.forEach(elements -> medicationList.add(elements));
            medicalRecordData.setMedications(medicationList);

            List<String> allergiesList = new ArrayList<>();
            List<String> allergiesData = getMedicalData(root, "allergies");
            allergiesData.forEach(elements -> allergiesList.add(elements));
            medicalRecordData.setAllergies(allergiesData);

            medicalRecordsList.add(medicalRecordData);
        }

        return medicalRecordsList;
    }

    public List<Object> JsonFileWriterAllData(List<Person> personList, List<FireStation> fireStationList, List<MedicalRecord> medicalRecordsList){

        allDataMap.add(personList);
        allDataMap.add(fireStationList);
        allDataMap.add(medicalRecordsList);

        try {
            mapper.writeValue(new File("E:/Users/Francky Malliet/git/SafetyNet Alerts/dataTest.json"),allDataMap);
        } catch(IOException error){
            logger.error("Json file not found");
            throw new IllegalStateException(error);
        }
        return allDataMap;
    }

    private JsonNode getRootArray(){
        try{
            return mapper.readTree(new File(path));
        } catch(IOException error){
            logger.error("Json file not found");
            throw new IllegalStateException(error);
        }
    }

    private List<String> getMedicalData(JsonNode root, String allergies) {
        try{
            return mapper.readValue(String.valueOf(root.path(allergies)), new TypeReference<>() {});
        } catch(IOException error){
            logger.error("Json file not found");
            throw new IllegalStateException(error);
        }
    }

}
