package com.safetynet.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.repository.DataReadingDAO;
import com.safetynet.application.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

// if we want specific data in a jsonNode field : JsonNode nameNode = jsonNode.at("/identification/name");

@Service
public class JsonReaderService implements DataReadingDAO {

    private final String path = "E:/Users/Francky Malliet/git/SafetyNet Alerts/data.json";
    private final static Logger logger = LoggerFactory.getLogger(JsonReaderService.class);
    private final ObjectMapper mapper = new ObjectMapper();

    private List<Person> personList = new ArrayList<Person>();
    private JsonNode rootArray = getRootArray();

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private final static LocalDate today = LocalDate.now();
    private final int adultLegalTime = 18 * 60 * 60 * 24 * 365;

    /*@Override
    public List<Person> readPersonData(String firstName, String lastName) {

            for(JsonNode root : rootArray.path("persons")){
                    if(root.path("lastName").asText().equals(lastName)){
                        Person personData = new Person();
                        personData.setFirstName(root.path("firstName").asText());
                        personData.setLastName(root.path("lastName").asText());
                        personData.setAddress(root.path("address").asText());
                        personData.setCity(root.path("city").asText());
                        personData.setZip(root.path("zip").asText());
                        personData.setPhone(root.path("phone").asText());
                        personData.setEmail(root.path("email").asText());

                        logger.info(firstName + " " + lastName + " retrieving data");
                        personList.add(personData);
                    }
                }

        return personList;
    }*/

    @Override
    public List<String> fireStationResidentData(String fireStationNumber) {

        logger.info("number recovered : " + fireStationNumber);

        List<String> personInformation = new ArrayList<>();
        int children = 0;
        int adult = 0;

        for (JsonNode fireStationRoot : rootArray.path("firestations")) {
            FireStation fireStation = new FireStation();
            fireStation.setStationNumber(fireStationRoot.path("station").asText());
            fireStation.setFireStationAddress(fireStationRoot.path("address").asText());

            logger.info("Station number " + fireStation.getStationNumber());
            logger.info("Station address " + fireStation.getFireStationAddress());

            if (fireStation.getStationNumber().equals(fireStationNumber)) {
                for (JsonNode personRoot : rootArray.path("persons")) {
                    if (personRoot.path("address").asText().equals(fireStation.getFireStationAddress())) {
                        //Instantiation of Person object
                        Person personData = new Person();

                        personData.setFirstName(personRoot.path("firstName").asText());
                        personData.setLastName(personRoot.path("lastName").asText());
                        personData.setAddress(personRoot.path("address").asText());
                        personData.setPhone(personRoot.path("phone").asText());

                        String personFirstName = personData.getFirstName();
                        String personLastName = personData.getLastName();
                        String personAddress = personData.getAddress();
                        String personPhone = personData.getPhone();

                        personInformation.add(personFirstName);
                        personInformation.add(personLastName);
                        personInformation.add(personAddress);
                        personInformation.add(personPhone);

                        logger.info(personFirstName + "" + personLastName + " retrieving data");

                        for (JsonNode rootMedicalRecord : rootArray.path("medicalrecords")) {
                            if (rootMedicalRecord.path("firstName").asText().equals((personFirstName)) & rootMedicalRecord.path("lastName").asText().equals(personLastName)) {
                                // Instantiation of MedicalRecord object
                                MedicalRecord medicalRecordData = new MedicalRecord();

                                medicalRecordData.setBirthDate(rootMedicalRecord.path("birthdate").asText());

                                String birthdate = medicalRecordData.getBirthDate();
                                int birthDateYear = getPersonAge(birthdate);

                                if(birthDateYear > 18){
                                    adult += 1;
                                } else {
                                    children += 1;
                                }
                            }
                        }
                    }
                }
            }
            logger.info("retrieving person data covered by station number : " + fireStationNumber);
        }

        personInformation.add("Number of Adults : " + String.valueOf(adult));
        personInformation.add("Number of Children : " + String.valueOf(children));
        return personInformation;
    }

    // GetMapping personInfo
    @Override
    public List<List<String>> readPersonMedicalData(String firstName, String lastName){

        logger.info("firstName : " + firstName + " lastName : " + lastName + " recovered");

        List<List<String>> personMedicalData = new ArrayList<>();
        List<String> personMedicalRecord = new ArrayList<>();

        for(JsonNode rootMedicalRecord : rootArray.path("medicalrecords")){
            if(rootMedicalRecord.path("firstName").asText().equals((firstName))&rootMedicalRecord.path("lastName").asText().equals(lastName)){
                // Instantiation of MedicalRecord object
                MedicalRecord medicalRecordData = new MedicalRecord();

                medicalRecordData.setLastName(rootMedicalRecord.path("lastName").asText());
                medicalRecordData.setBirthDate(rootMedicalRecord.path("birthdate").asText());

                // Create a list of Strings coming from a json list using a method called getMedicalData
                List<String> medicationList = new ArrayList<>();
                List<String> medicationData = getMedicalData(rootMedicalRecord, "medications");
                medicationData.forEach(elements -> medicationList.add(elements));
                medicalRecordData.setMedications(medicationList);

                // Create a list of Strings coming from a json list using a method called getMedicalData
                List<String> allergiesList = new ArrayList<>();
                List<String> allergiesData = getMedicalData(rootMedicalRecord, "allergies");
                allergiesData.forEach(elements -> allergiesList.add(elements));
                medicalRecordData.setAllergies(allergiesData);

                // Getting all the required data coming from the object
                String personLastName = medicalRecordData.getLastName();
                String personAge = String.valueOf(getPersonAge(medicalRecordData.getBirthDate()));
                List<String> medicationDataList = medicalRecordData.getMedications();
                List<String> allergiesDataList = medicalRecordData.getAllergies();

                // Adding this data directly to the final list
                personMedicalData.add(medicationDataList);
                personMedicalData.add(allergiesDataList);

                // Adding the data to a list that need to get more information before sent to the final list
                personMedicalRecord.add(personAge);
                personMedicalRecord.add(personLastName);

                for(JsonNode rootPerson : rootArray.path("persons")){
                    if(rootPerson.path("firstName").asText().equals((firstName))&rootPerson.path("lastName").asText().equals(lastName)){

                        String personAddress = rootPerson.path("address").asText();
                        String personEmail = rootPerson.path("email").asText();

                        // Adding the last data required for the list personMedicalRecord before being sent to personMedicalData
                        personMedicalRecord.add(personAddress);
                        personMedicalRecord.add(personEmail);

                        logger.info("retrieving data : " + personLastName + "" + personAge + "" + medicationData + "" + allergiesData + "" + personAddress + "" + personEmail);
                    }
                }
                personMedicalData.add(personMedicalRecord);
            }
        }
        return personMedicalData;
    }

    //GetMapping communityEmail
    @Override
    public List<String> getEmailData(String city) {

        List<String> emailData = new ArrayList<String>();

        for (JsonNode root : rootArray.path("persons")) {
            if (root.path("city").asText().equals(city)) {
                emailData.add(root.path("email").asText());

                logger.info("retrieving email data : " + root.path("email").asText());
            }
        }

        return emailData;
    }


    /* List of methods used as utilities */
    private List<String> getMedicalData(JsonNode root, String medicalData) {
        try{
            return mapper.readValue(String.valueOf(root.path(medicalData)), new TypeReference<>() {});
        } catch(IOException error){
            logger.error("Json file not found");
            throw new IllegalStateException(error);
        }
    }

    public int getPersonAge(String birthDate){
        LocalDate birthDay = LocalDate.parse(birthDate, dateTimeFormatter);

        Period actualAge = Period.between(birthDay, today);
        return actualAge.getYears();
    }

//http://localhost:9090/fireStation?station=%221%22
    private JsonNode getRootArray(){
        try{
            return mapper.readTree(new File(path));
        } catch(IOException error){
            logger.error("Json file not found");
            throw new IllegalStateException(error);
        }
    }
}
