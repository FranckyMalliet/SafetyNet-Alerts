package com.safetynet.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import com.safetynet.application.repository.DataReadingDAO;
import com.safetynet.application.repository.model.FireStationDAO;
import com.safetynet.application.repository.model.MedicalRecordDAO;
import com.safetynet.application.repository.model.PersonDAO;
import com.safetynet.application.repository.utilities.MethodsUtilDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JsonReaderService implements DataReadingDAO {

    private final static Logger logger = LoggerFactory.getLogger(JsonReaderService.class);

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private FireStationDAO fireStationDAO;

    @Autowired
    private MedicalRecordDAO medicalRecordDAO;

    @Autowired
    private MethodsUtilDAO methodsUtilDAO;

    private List<Person> personList = new ArrayList<Person>();

    @Override
    public List<List<String>> getFireStationResidentData(String fireStationNumber) {

        logger.info("number recovered : " + fireStationNumber);

        List<List<String>> personAllData = new ArrayList<>();
        List<String> personInformation = new ArrayList<>();
        List<String> personLastInformation = new ArrayList<>();

        int children = 0;
        int adult = 0;

        List<FireStation> fireStationList = fireStationDAO.getFireStationInformationWithNumber(fireStationNumber);
        for(FireStation fireStationData : fireStationList){
            personInformation.add("Residents covered by station number : " + fireStationData.getStation() + " at " + fireStationData.getAddress());

            logger.info("Station number " + fireStationData.getStation());
            logger.info("Station address " + fireStationData.getAddress());

            List<Person> personInformationList = personDAO.getPersonDataWithAddress(fireStationData.getAddress());
            for(Person personData : personInformationList){
                MedicalRecord personMedicalRecord = medicalRecordDAO.getMedicalRecordDataWithFirstNameAndLastName(personData.getFirstName(), personData.getLastName());
                int personAge = methodsUtilDAO.getPersonAge(personMedicalRecord.getBirthdate());

                personInformation.add(personData.getFirstName());
                personInformation.add(personData.getLastName());
                personInformation.add(String.valueOf(personAge));
                personInformation.add(personData.getAddress());
                personInformation.add(personData.getPhone());
                personInformation.add(" --------------- ");

                if (personAge > 18) {
                    adult += 1;
                } else {
                    children += 1;
                }

                logger.info(personData.getFirstName() + "" + personData.getLastName() + " retrieving data");
            }
        }

        personLastInformation.add("The number of adults is : " + String.valueOf(adult));
        personLastInformation.add("The number of children is : " + String.valueOf(children));
        personAllData.add(personInformation);
        personAllData.add(personLastInformation);

        return personAllData;
    }

    //GetMapping childAlert
    @Override
    public List<Person> getChildAlert(String address) {

        List<String> personInformation = new ArrayList<>();

        logger.info("Children from " + address);

        for (JsonNode root : methodsUtilDAO.getRootArray().path("persons")) {
            if (root.path("address").asText().equals(address)) {
                List<Person> personList = new ArrayList<Person>();
                Person personData = new Person();

                personData.setFirstName(root.path("firstName").asText());
                personData.setLastName(root.path("lastName").asText());
                personData.setAddress(root.path("address").asText());
                personData.setCity(root.path("city").asText());
                personData.setZip(root.path("zip").asText());
                personData.setPhone(root.path("phone").asText());
                personData.setEmail(root.path("email").asText());

                String personFirstName = personData.getFirstName();
                String personLastName = personData.getLastName();

                for (JsonNode medicalRecordRoot : methodsUtilDAO.getRootArray().path("medicalrecord")) {
                    if (medicalRecordRoot.path("firstName").asText().equals(personFirstName) & medicalRecordRoot.path("lastName").asText().equals(personLastName)) {
                        MedicalRecord medicalRecordData = new MedicalRecord();

                        medicalRecordData.setBirthdate(medicalRecordRoot.path("birthdate").asText());

                        String birthdate = medicalRecordData.getBirthdate();
                        int birthDateYear = methodsUtilDAO.getPersonAge(birthdate);

                        if (birthDateYear <= 18) {
                            personList.add(personData);
                        }
                    }
                }
            }
        }
        return personList;
    }


    //GetMapping phoneAlert
    @Override
    public List<String> getResidentPhone(String fireStationNumber) {

        List<String> personInformation = new ArrayList<>();

        for (JsonNode fireStationRoot : methodsUtilDAO.getRootArray().path("firestations")) {
            FireStation fireStation = new FireStation();
            fireStation.setStation(fireStationRoot.path("station").asText());
            fireStation.setAddress(fireStationRoot.path("address").asText());

            logger.info("Station number " + fireStation.getStation());
            logger.info("Station address " + fireStation.getAddress());

            if (fireStation.getStation().equals(fireStationNumber)) {
                for (JsonNode personRoot : methodsUtilDAO.getRootArray().path("persons")) {
                    if (personRoot.path("address").asText().equals(fireStation.getAddress())) {
                        //Instantiation of Person object
                        Person personData = new Person();

                        personData.setFirstName(personRoot.path("firstName").asText());
                        personData.setLastName(personRoot.path("lastName").asText());
                        personData.setPhone(personRoot.path("phone").asText());

                        String personFirstName = personData.getFirstName();
                        String personLastName = personData.getLastName();
                        String personPhone = personData.getPhone();

                        personInformation.add(personFirstName);
                        personInformation.add(personLastName);;
                        personInformation.add(personPhone);
                        personInformation.add("");

                        logger.info(personFirstName + "" + personLastName + " retrieving data");
                    }
                }
            }
        }
        return personInformation;
    }


    //GetMapping fire
    @Override
    public List<List<String>> getMedicalInformationFromAddress(String address){

        List<List<String>> personAllData = new ArrayList<>();
        List<String> personInformation = new ArrayList<>();

        for(JsonNode personRoot : methodsUtilDAO.getRootArray().path("persons")){
            if (personRoot.path("address").asText().equals(address)){
                Person personData = new Person();

                personData.setFirstName(personRoot.path("firstName").asText());
                personData.setLastName(personRoot.path("lastName").asText());
                personData.setPhone(personRoot.path("phone").asText());

                String personFirstName = personData.getFirstName();
                String personLastName = personData.getLastName();

                personInformation.add(personData.getFirstName());
                personInformation.add(personData.getLastName());
                personInformation.add(personData.getPhone());

                for(JsonNode medicalRecordRoot : methodsUtilDAO.getRootArray().path("medicalrecords")){
                    if(medicalRecordRoot.path("firstName").asText().equals(personFirstName)&medicalRecordRoot.path("lastName").asText().equals(personLastName)){
                        MedicalRecord medicalRecordData = new MedicalRecord();

                        medicalRecordData.setBirthdate(medicalRecordRoot.path("birthdate").asText());

                        String birthDate = medicalRecordData.getBirthdate();
                        int birthDateYear = methodsUtilDAO.getPersonAge(birthDate);
                        personInformation.add(String.valueOf(birthDateYear));

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

                        List<String> medicationDataList = medicalRecordData.getMedications();
                        List<String> allergiesDataList = medicalRecordData.getAllergies();

                        // Adding this data directly to the final list
                        personAllData.add(personInformation);
                        personAllData.add(medicationDataList);
                        personAllData.add(allergiesDataList);
                    }
                }
            }
        }

        List<String> fireStationList = fireStationDAO.getFireStationStationNumberWithAddress(address);
        personAllData.add(fireStationList);
        return personAllData;
    }

    //GetMapping flood/stations
    @Override
    public List<List<String>> getAllPersonsDataFromAListOfFireStation(String stationNumber){

        List<List<String>> personAllData = new ArrayList<>();
        List<String> personInformation = new ArrayList<>();

        JsonNode medicalRecordRoot = methodsUtilDAO.getRootArray().path("medicalrecords");

        List<String> fireStationAddressList = fireStationDAO.getFireStationAddressWithNumber(stationNumber);
        for(String elements : fireStationAddressList){
            List<Person> personList = personDAO.getPersonDataWithAddress(elements);
            for(Person personData : personList){
                personInformation.add(personData.getFirstName());
                personInformation.add(personData.getLastName());
                personInformation.add(personData.getPhone());

                MedicalRecord personMedicalRecord = medicalRecordDAO.getMedicalRecordDataWithFirstNameAndLastName(personData.getFirstName(), personData.getLastName());

                int personAge = methodsUtilDAO.getPersonAge(personMedicalRecord.getBirthdate());
                personInformation.add(String.valueOf(personAge));

                List<String> medicationList = methodsUtilDAO.getListOfStringFromJson(medicalRecordRoot, "medications");
                List<String> allergiesList = methodsUtilDAO.getListOfStringFromJson(medicalRecordRoot, "allergies");

                personAllData.add(personInformation);
                personAllData.add(medicationList);
                personAllData.add(allergiesList);
            }
        }
        return personAllData;
    }

    // GetMapping personInfo
    @Override
    public List<List<String>> getPersonMedicalData(String firstName, String lastName){

        logger.info("firstName : " + firstName + " lastName : " + lastName + " recovered");

        List<List<String>> personAllData = new ArrayList<>();
        List<String> personInformation = new ArrayList<>();

        JsonNode medicalRecordRoot = methodsUtilDAO.getRootArray().path("medicalrecords");

        List<Person> personList = personDAO.getPersonDataWithLastName(lastName);
        for(Person personData : personList){
                personInformation.add(personData.getFirstName());
                personInformation.add(personData.getLastName());
                personInformation.add(personData.getAddress());
                personInformation.add(personData.getEmail());

                MedicalRecord personMedicalRecord = medicalRecordDAO.getMedicalRecordDataWithFirstNameAndLastName(personData.getFirstName(), personData.getLastName());

                int personAge = methodsUtilDAO.getPersonAge(personMedicalRecord.getBirthdate());
                personInformation.add(String.valueOf(personAge));

                List<String> medicationList = methodsUtilDAO.getListOfStringFromJson(medicalRecordRoot, medicalRecordRoot.path("medications").asText());
                List<String> allergiesList = methodsUtilDAO.getListOfStringFromJson(medicalRecordRoot, medicalRecordRoot.path("allergies").asText());

                personAllData.add(personInformation);
                personAllData.add(medicationList);
                personAllData.add(allergiesList);
            }
        return personAllData;
    }

    //GetMapping communityEmail
    @Override
    public List<String> getEmailData(String city) {

        List<String> emailData = new ArrayList<String>();

        for (JsonNode root : methodsUtilDAO.getRootArray().path("persons")) {
            if (root.path("city").asText().equals(city)) {
                emailData.add(root.path("email").asText());

                logger.info("retrieving email data : " + root.path("email").asText());
            }
        }

        return emailData;
    }
}
