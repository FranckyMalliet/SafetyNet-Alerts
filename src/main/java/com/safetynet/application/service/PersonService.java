package com.safetynet.application.service;

import com.fasterxml.jackson.databind.JsonNode;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService implements IPersonService {

    @Autowired
    IFireStationService IFireStationService;

    @Autowired
    IMedicalRecordService IMedicalRecordService;

    @Autowired
    IUtilitiesService IUtilitiesService;

    @Autowired
    DataDAO dataDAO;

    private final static Logger logger = LoggerFactory.getLogger(PersonService.class);

    /**
     * Method used for recovering information in a person list using address
     * @param address
     * @return a Person object
     */

    @Override
    public List<Person> getPersonDataWithAddress(String address) {

        List<Person> personInformation = new ArrayList<>();

        for (JsonNode personRoot : IUtilitiesService.getRootArray().path("persons")) {
            if (personRoot.path("address").asText().equals(address)) {
                Person personData = new Person();

                personData.setFirstName(personRoot.path("firstName").asText());
                personData.setLastName(personRoot.path("lastName").asText());
                personData.setAddress(personRoot.path("address").asText());
                personData.setCity(personRoot.path("city").asText());
                personData.setZip(personRoot.path("zip").asText());
                personData.setPhone(personRoot.path("phone").asText());
                personData.setEmail(personRoot.path("email").asText());

                personInformation.add(personData);
            }
        }

        logger.debug("Retrieving a list of persons data from " + address);
        return personInformation;
    }

    /**
     * Method used for recovering information in a person list using lastName
     * @param lastName
     * @return a list of person information
     */

    @Override
    public List<Person> getPersonDataWithLastName(String lastName) {

        List<Person> personInformation = new ArrayList<>();

        for (JsonNode personRoot : IUtilitiesService.getRootArray().path("persons")) {
            if (personRoot.path("lastName").asText().equals(lastName)) {
                Person personData = new Person();

                personData.setFirstName(personRoot.path("firstName").asText());
                personData.setLastName(personRoot.path("lastName").asText());
                personData.setAddress(personRoot.path("address").asText());
                personData.setCity(personRoot.path("city").asText());
                personData.setZip(personRoot.path("zip").asText());
                personData.setPhone(personRoot.path("phone").asText());
                personData.setEmail(personRoot.path("email").asText());

                personInformation.add(personData);
            }
        }

        logger.debug("Retrieving a list of persons data with " + lastName + " as last name");
        return personInformation;
    }

    /**
     * Method used to add a new person in the json file
     * Used by DataController class as /person, request mapping POST
     * @param person
     * Add a new person in the json file
     */

    @Override
    public void writeNewPersonData(Person person){

        List<Person> personList = getAllPerson();
        List<FireStation> fireStationList = IFireStationService.getAllFireStation();
        List<MedicalRecord> medicalRecordsList = IMedicalRecordService.getAllMedicalRecord();

        Person personData = new Person();
        personData.setFirstName(person.getFirstName());
        personData.setLastName(person.getLastName());
        personData.setAddress(person.getAddress());
        personData.setCity(person.getCity());
        personData.setZip(person.getZip());
        personData.setPhone(person.getPhone());
        personData.setEmail(person.getEmail());

        personList.add(personData);

        logger.debug("Adding new person data in json file "
                + personData.getFirstName() + " "
                + personData.getLastName() + " "
                + personData.getAddress() + " "
                + personData.getCity() + " "
                + personData.getZip() + " "
                + personData.getPhone() + " "
                + personData.getEmail());

        dataDAO.writeAllData(personList, fireStationList, medicalRecordsList);
    }

    /**
     * Method used to update a person
     * Used by DataController class as /person, request mapping PUT
     * @param person
     * Update a person in the json file
     */

    @Override
    public void updatePersonData(Person person) {

        List<Person> personList = getAllPerson();
        List<FireStation> fireStationList = IFireStationService.getAllFireStation();
        List<MedicalRecord> medicalRecordsList = IMedicalRecordService.getAllMedicalRecord();

        Person updatePerson = new Person();
        updatePerson.setFirstName(person.getFirstName());
        updatePerson.setLastName(person.getLastName());
        updatePerson.setAddress(person.getAddress());
        updatePerson.setCity(person.getCity());
        updatePerson.setZip(person.getZip());
        updatePerson.setPhone(person.getPhone());
        updatePerson.setEmail(person.getEmail());

        logger.debug("Updating person : " + updatePerson.getFirstName() + " " + updatePerson.getLastName());

        for(Person personData : personList){
            if(updatePerson.getFirstName().equals(personData.getFirstName())&updatePerson.getLastName().equals(personData.getLastName())){
                personData.setAddress(updatePerson.getAddress());
                personData.setCity(updatePerson.getCity());
                personData.setZip(updatePerson.getZip());
                personData.setPhone(updatePerson.getPhone());
                personData.setEmail(updatePerson.getEmail());

                logger.debug("Modifying person data in json file "
                        + personData.getFirstName() + " "
                        + personData.getLastName() + " "
                        + personData.getAddress() + " "
                        + personData.getCity() + " "
                        + personData.getZip() + " "
                        + personData.getPhone() + " "
                        + personData.getEmail());
            }
        }

        dataDAO.writeAllData(personList, fireStationList, medicalRecordsList);
    }

    /**
     * Method used to delete a person using firstName and lastName
     * Used by DataController class as /person, request mapping DEL
     * @param firstName
     * @param lastName
     * Delete a person
     */

    @Override
    public void deletePersonData(String firstName, String lastName){

        List<Person> personList = getAllPerson();
        List<FireStation> fireStationList = IFireStationService.getAllFireStation();
        List<MedicalRecord> medicalRecordsList = IMedicalRecordService.getAllMedicalRecord();

        List<Person> newPersonList = new ArrayList<>();

        for(Person personData : personList){
            String personFirstName = personData.getFirstName();
            String personLastName = personData.getLastName();
            if(!personFirstName.equals(firstName)&!personLastName.equals(lastName)){
                newPersonList.add(personData);
            }
        }

        logger.debug("Deleting " + firstName + " " + lastName);
        dataDAO.writeAllData(newPersonList, fireStationList, medicalRecordsList);
    }

    /**
     * Method used for recovering all people in the json file
     * @return a list of all persons include in the json file
     */

    @Override
    public List<Person> getAllPerson(){

        List<Person> personList = new ArrayList<>();

        for (JsonNode root : IUtilitiesService.getRootArray().get("persons")) {
            Person personData = new Person();

            personData.setFirstName(root.path("firstName").asText());
            personData.setLastName(root.path("lastName").asText());
            personData.setAddress(root.path("address").asText());
            personData.setCity(root.path("city").asText());
            personData.setZip(root.path("zip").asText());
            personData.setPhone(root.path("phone").asText());
            personData.setEmail(root.path("email").asText());

            personList.add(personData);
        }

        logger.debug("Retrieving all the person data");
        return personList;
    }

    /**
     * Method used to get all people deserved by a specific fireStation using the fireStationNumber
     * Used by DataController class as /firestation, request mapping GET
     * @param fireStationNumber
     * @return a list of persons and the number of children and adults in the area
     */

    @Override
    public List<List<String>> getFireStationResidentData(String fireStationNumber) {

        List<List<String>> personAllData = new ArrayList<>();
        List<String> personInformation = new ArrayList<>();

        int children = 0;
        int adult = 0;

        List<FireStation> fireStationList = IFireStationService.getFireStationInformationWithNumber(fireStationNumber);
        for(FireStation fireStationData : fireStationList){
            personInformation.add("Residents covered by station number : " + fireStationData.getStation() + " at " + fireStationData.getAddress());

            logger.debug("Station number " + fireStationData.getStation() + " , station address : " + fireStationData.getAddress());

            List<Person> personInformationList = getPersonDataWithAddress(fireStationData.getAddress());
            for(Person personData : personInformationList){
                MedicalRecord personMedicalRecord = IMedicalRecordService.getMedicalRecordDataWithFirstNameAndLastName(personData.getFirstName(), personData.getLastName());
                String personAge = IUtilitiesService.getPersonAge(personMedicalRecord.getBirthdate());

                personInformation.add(personData.getFirstName());
                personInformation.add(personData.getLastName());
                personInformation.add(personAge);
                personInformation.add(personData.getAddress());
                personInformation.add(personData.getPhone());
                personInformation.add(" --------------- ");

                if (Integer.parseInt(personAge) > 18) {
                    adult += 1;
                } else {
                    children += 1;
                }

                logger.debug(personData.getFirstName() + " " + personData.getLastName() + " retrieving data");
            }
            personInformation.add("The number of adults in the area is : " + String.valueOf(adult));
            personInformation.add("The number of children in the area is : " + String.valueOf(children));
            personInformation.add(" --------------- ");

            children = 0;
            adult = 0;
        }

        personAllData.add(personInformation);

        return personAllData;
    }

    /**
     * Method used to get all children in a specific address using address
     * Used by DataController class as /childAlert, request mapping GET
     * @param address
     * @return a list of children information from the address
     */

    @Override
    public List<List<String>> getAListOfChildFromAnAddressAndAListOfTheOtherResidents(String address) {

        List<List<String>> personAllData = new ArrayList<>();
        List<String> childrenList = new ArrayList<>();
        List<String> adultList = new ArrayList<>();

        childrenList.add("List of children");
        adultList.add("List of adults");

        List<Person> personList = getPersonDataWithAddress(address);
        for(Person personData : personList){
            MedicalRecord personMedicalRecord = IMedicalRecordService.getMedicalRecordDataWithFirstNameAndLastName(personData.getFirstName(), personData.getLastName());
            if(Integer.parseInt(IUtilitiesService.getPersonAge(personMedicalRecord.getBirthdate())) <= 18){
                childrenList.add(personData.getFirstName());
                childrenList.add(personData.getLastName());
                childrenList.add(IUtilitiesService.getPersonAge(personMedicalRecord.getBirthdate()));
                childrenList.add(" --------------- ");
            } else {
                adultList.add(personData.getFirstName());
                adultList.add(personData.getLastName());
                adultList.add(IUtilitiesService.getPersonAge(personMedicalRecord.getBirthdate()));
                adultList.add(" --------------- ");
            }
        }

        personAllData.add(childrenList);
        personAllData.add(adultList);
        return personAllData;
    }

    /**
     * Method used to get all residents phoneNumber in a specific area using fireStationNumber
     * Used by DataController class as /phoneAlert, request mapping GET
     * @param fireStationNumber
     * @return a list of person information : firstName, lastName and Phone
     */

    @Override
    public List<List<String>> getResidentPhoneDeservedByAFireStation(String fireStationNumber) {

        List<List<String>> personAllData = new ArrayList<>();

        List<FireStation> fireStationList = IFireStationService.getFireStationInformationWithNumber(fireStationNumber);

        for(FireStation fireStationData : fireStationList){
            List<Person> personList = getPersonDataWithAddress(fireStationData.getAddress());
            for(Person personData : personList){
                List<String> personInformation = new ArrayList<>();
                personInformation.add(personData.getFirstName());
                personInformation.add(personData.getLastName());
                personInformation.add(personData.getPhone());

                logger.debug(personData.getFirstName() + " " + personData.getLastName() + " retrieving data");
                personAllData.add(personInformation);
            }
        }

        return personAllData;
    }

    /**
     * Method used to get all residents living in a specific area using fireStationNumber
     * Used by DataController class as /fire, request mapping GET
     * @param address
     * @return a list of people with their firstName, lastName, phoneNumber and medications
     */

    @Override
    public List<List<String>> getMedicalInformationFromAddress(String address){

        List<List<String>> personAllData = new ArrayList<>();

        List<Person> personList = getPersonDataWithAddress(address);
        for(Person personData : personList){
            List<String> personInformation = new ArrayList<>();
            personInformation.add(personData.getFirstName());
            personInformation.add(personData.getLastName());
            personInformation.add(personData.getPhone());

            MedicalRecord medicalRecordData = IMedicalRecordService.getMedicalRecordDataWithFirstNameAndLastName(personData.getFirstName(), personData.getLastName());
            personInformation.add(IUtilitiesService.getPersonAge(medicalRecordData.getBirthdate()));
            personAllData.add(personInformation);

            List<String> medicationData = medicalRecordData.getMedications();
            List<String> allergiesData = medicalRecordData.getAllergies();

            personAllData.add(medicationData);
            personAllData.add(allergiesData);

            logger.debug(personData.getFirstName() + " " + personData.getLastName() + " retrieving data");
        }

        List<String> fireStationList = IFireStationService.getFireStationNumberWithAddress(address);
        personAllData.add(fireStationList);
        return personAllData;
    }

    /**
     * Method used to get all residents deserved by a specific fireStation using fireStationNumber
     * Used by DataController class as /flood/stations, request mapping GET
     * @param stationNumber
     * @return a list of people with their firstName, lastName, phoneNumber, age and medications
     * grouped by a common address
     */

    @Override
    public List<List<String>> getPersonsDataFromAListOfFireStation(String stationNumber){

        List<List<String>> personAllData = new ArrayList<>();

        List<String> fireStationAddressList = IFireStationService.getFireStationAddressWithNumber(stationNumber);
        for(String elements : fireStationAddressList){
            List<String> fireStationInformation = new ArrayList<>();
            fireStationInformation.add("FireStation number : " + stationNumber + " at " + elements);
            personAllData.add(fireStationInformation);

            List<Person> personList = getPersonDataWithAddress(elements);
            for(Person personData : personList){
                List<String> personInformation = new ArrayList<>();
                personInformation.add(personData.getFirstName());
                personInformation.add(personData.getLastName());
                personInformation.add(personData.getPhone());

                MedicalRecord medicalRecordData = IMedicalRecordService.getMedicalRecordDataWithFirstNameAndLastName(personData.getFirstName(), personData.getLastName());
                personInformation.add(IUtilitiesService.getPersonAge(medicalRecordData.getBirthdate()));
                personAllData.add(personInformation);

                List<String> medicationData = medicalRecordData.getMedications();
                List<String> allergiesData = medicalRecordData.getAllergies();

                personAllData.add(medicationData);
                personAllData.add(allergiesData);

                logger.debug(personData.getFirstName() + " " + personData.getLastName() + " retrieving data");
            }
        }
        return personAllData;
    }

    /**
     * Method used to get a person data using firstName and lastName
     * Used by DataController class as /personInfo, request mapping GET
     * @param firstName
     * @param lastName
     * @return a list with a person information and all people who have the same
     * name
     */

    @Override
    public List<List<String>> getPersonMedicalData(String firstName, String lastName){

        List<List<String>> personAllData = new ArrayList<>();

        List<Person> personList = getPersonDataWithLastName(lastName);
        for(Person personData : personList){
            List<String> personInformation = new ArrayList<>();
            personInformation.add(personData.getFirstName());
            personInformation.add(personData.getLastName());
            personInformation.add(personData.getAddress());
            personInformation.add(personData.getEmail());

            MedicalRecord medicalRecordData = IMedicalRecordService.getMedicalRecordDataWithFirstNameAndLastName(personData.getFirstName(), personData.getLastName());
            personInformation.add(IUtilitiesService.getPersonAge(medicalRecordData.getBirthdate()));

            personAllData.add(personInformation);

            List<String> medicationData = medicalRecordData.getMedications();
            List<String> allergiesData = medicalRecordData.getAllergies();

            personAllData.add(medicationData);
            personAllData.add(allergiesData);

            logger.debug(personData.getFirstName() + " " + personData.getLastName() + " retrieving data");
        }

        return personAllData;
    }

    /**
     * Method used to get all email in a specific city using city
     * Used by DataController class as /communityEmail, request mapping GET
     * @param city
     * @return a list of email
     */

    @Override
    public List<String> getEmailData(String city) {

        List<String> emailData = new ArrayList<String>();

        for (JsonNode root : IUtilitiesService.getRootArray().path("persons")) {
            if (root.path("city").asText().equals(city)) {
                emailData.add(root.path("email").asText());

                logger.debug("retrieving email data : " + root.path("email").asText());
            }
        }

        return emailData;
    }

    /**
     * Method used for recovering information in a person list using firstName and lastName
     * @param firstName
     * @param lastName
     * @return a list of person information
     */

    @Override
    public List<Person> getPersonDataWithFirstNameAndLastName(String firstName, String lastName) {

        List<Person> personInformation = new ArrayList<>();

        for (JsonNode personRoot : IUtilitiesService.getRootArray().path("persons")) {
            if (personRoot.path("firstName").asText().equals(firstName)&personRoot.path("lastName").asText().equals(lastName)) {
                Person personData = new Person();

                personData.setFirstName(personRoot.path("firstName").asText());
                personData.setLastName(personRoot.path("lastName").asText());
                personData.setAddress(personRoot.path("address").asText());
                personData.setCity(personRoot.path("city").asText());
                personData.setZip(personRoot.path("zip").asText());
                personData.setPhone(personRoot.path("phone").asText());
                personData.setEmail(personRoot.path("email").asText());

                personInformation.add(personData);
            }
        }

        logger.debug("Retrieving person data from " + firstName + " " + lastName);
        return personInformation;
    }
}

