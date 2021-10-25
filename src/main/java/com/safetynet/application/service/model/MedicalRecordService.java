package com.safetynet.application.service.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.repository.model.MedicalRecordDAO;
import com.safetynet.application.repository.utilities.MethodsUtilDAO;
import com.safetynet.application.service.JsonReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MedicalRecordService implements MedicalRecordDAO {

    private final static Logger logger = LoggerFactory.getLogger(JsonReaderService.class);

    @Autowired
    private MethodsUtilDAO methodsUtilDAO;

    @Override
    public MedicalRecord getMedicalRecordDataWithFirstNameAndLastName(String firstName, String lastName) {

        MedicalRecord medicalRecordData = new MedicalRecord();

        for (JsonNode medicalRecordRoot : methodsUtilDAO.getRootArray().path("medicalrecords")) {
            if (medicalRecordRoot.path("firstName").asText().equals(firstName) & medicalRecordRoot.path("lastName").asText().equals(lastName)) {

                medicalRecordData.setFirstName(medicalRecordRoot.path("firstName").asText());
                medicalRecordData.setLastName(medicalRecordRoot.path("lastName").asText());
                medicalRecordData.setBirthdate(medicalRecordRoot.path("birthdate").asText());
                medicalRecordData.setMedications(Collections.singletonList(medicalRecordRoot.path("medications").asText()));
                medicalRecordData.setAllergies(Collections.singletonList(medicalRecordRoot.path("allergies").asText()));
            }
        }
        return medicalRecordData;
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecord(){

        List<MedicalRecord> medicalRecordList = new ArrayList<>();

        for (JsonNode medicalRecordRoot : methodsUtilDAO.getRootArray().path("medicalrecords")) {
            MedicalRecord medicalRecordData = new MedicalRecord();

            medicalRecordData.setFirstName(medicalRecordRoot.path("firstName").asText());
            medicalRecordData.setLastName(medicalRecordRoot.path("lastName").asText());
            medicalRecordData.setBirthdate(medicalRecordRoot.path("birthdate").asText());
            List<String> medicationData = methodsUtilDAO.getListOfStringFromJson(medicalRecordRoot, "medications");
            medicalRecordData.setMedications(medicationData);
            List<String> allergiesData = methodsUtilDAO.getListOfStringFromJson(medicalRecordRoot, "allergies");
            medicalRecordData.setAllergies(allergiesData);

            medicalRecordList.add(medicalRecordData);
        }
        return medicalRecordList;
    }
}


    /*@Override
    public List<List<String>> getMedicalRecordDataWithFirstNameAndLastName(String firstName, String lastName){

        List<List<String>> personMedicalData = new ArrayList<>();
        List<String> personMedicalRecord = new ArrayList<>();

        for(JsonNode medicalRecordRoot : rootArray.path("medicalrecords")){
            if(medicalRecordRoot.path("firstName").asText().equals(firstName)&medicalRecordRoot.path("lastName").asText().equals(lastName)){
                MedicalRecord medicalRecordData = new MedicalRecord();

                medicalRecordData.setBirthDate(medicalRecordRoot.path("birthdate").asText());

                String birthDate = medicalRecordData.getBirthDate();
                int birthDateYear = methodsUtilDAO.getPersonAge(birthDate);
                personMedicalRecord.add(String.valueOf(birthDateYear));

                // Create a list of Strings coming from a json list using a method called getMedicalData
                List<String> medicationList = new ArrayList<>();
                List<String> medicationData = methodsUtilDAO.getMedicalData(medicalRecordRoot, "medications");
                medicationData.forEach(elements -> medicationList.add(elements));
                medicalRecordData.setMedications(medicationList);

                // Create a list of Strings coming from a json list using a method called getMedicalData
                List<String> allergiesList = new ArrayList<>();
                List<String> allergiesData = methodsUtilDAO.getMedicalData(medicalRecordRoot, "allergies");
                allergiesData.forEach(elements -> allergiesList.add(elements));
                medicalRecordData.setAllergies(allergiesData);

                // Adding this data directly to the final list
                personMedicalData.add(personMedicalRecord);
                personMedicalData.add(medicalRecordData.getMedications());
                personMedicalData.add(medicalRecordData.getAllergies());
            }
        }
        return personMedicalData;
    }*/