package com.safetynet.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import com.safetynet.application.repository.DataDeletingDAO;
import com.safetynet.application.repository.DataReadingDAO;
import com.safetynet.application.repository.DataUpdatingDAO;
import com.safetynet.application.repository.DataWritingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DataController class is using Url and Uri to get information from a Json file
 *
 */

@RestController
public class DataController {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private DataReadingDAO dataReadingDAO;

    @Autowired
    private DataWritingDAO dataWritingDAO;

    @Autowired
    private DataUpdatingDAO dataUpdatingDAO;

    @Autowired
    private DataDeletingDAO dataDeletingDAO;

    /**
     * This URL must be written /fireStation?stationNumber=stationNumber
     * @param stationNumber
     * @return a list of persons covered by a fireStation.
     * The information are : firstName, lastName, address, phoneNumber and
     * the number of adults and children in the area
     * @throws JsonProcessingException
     */

    @GetMapping(value = "/fireStation")
    public String fireStationResidentsInfo(@RequestParam String stationNumber) throws JsonProcessingException {
        List<List<String>> data = dataReadingDAO.getFireStationResidentData(stationNumber);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    /**
     * This URL must be written /childAlert?address=address
     * @param address
     * @return a list of children under or equal of 18 years old living in a
     * specific address
     * The information are firstName, lastName, age and a list of the other members
     * of the family
     * @throws JsonProcessingException
     */

    @GetMapping(value ="/childAlert")
    public String getChildrenList(@RequestParam String address) throws JsonProcessingException {
        List<Person> data = dataReadingDAO.getChildAlert(address);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    /**
     * This URL must be written /phoneAlert?fireStation=fireStationNumber
     * @param stationNumber
     * @return a list of phoneNumber of all residents deserved by a
     * specific fireStation
     * @throws JsonProcessingException
     */

    @GetMapping(value = "phoneAlert")
    public String getResidentsPhone(@RequestParam String stationNumber) throws JsonProcessingException {
        List<String> data = dataReadingDAO.getResidentPhone(stationNumber);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    /**
     * This URL must be written /fire?address=address
     * @param address
     * @return a list of residents living in a specific address
     * The information are firstName, lastName, phoneNumber, age, medications and allergies
     * from each person
     * @throws JsonProcessingException
     */

    @GetMapping(value ="fire")
    public String getMedicalInformationFromAddress(@RequestParam String address) throws JsonProcessingException {
        List<List<String>> data = dataReadingDAO.getMedicalInformationFromAddress(address);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    /**
     * This URL must be written /flood/stations?stationNumber=stationNumber
     * @param stationNumber
     * @return a list of people deserved by a specific fireStation grouped by address
     * The information are firstName, lastName, phoneNumber, age, medications, allergies
     * @throws JsonProcessingException
     */

    @GetMapping(value ="flood/stations")
    public String getAllPersonsDataFromAListOfFireStation(@RequestParam String stationNumber) throws JsonProcessingException {
        List<List<String>> data = dataReadingDAO.getAllPersonsDataFromAListOfFireStation(stationNumber);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    /**
     * This URL must be written /personInfo?firstName=firstName&lastName=lastName
     * @param firstName
     * @param lastName
     * @return a list of resident by their firstName and lastName
     * The information are firstName, lastName, address, age, emailAddress, medications and allergies
     * @throws JsonProcessingException
     */

    @GetMapping(value = "/personInfo")
    public String getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) throws JsonProcessingException {
        List<List<String>> data = dataReadingDAO.getPersonMedicalData(firstName, lastName);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    /**
     * This URL must be written /communityEmail?city=cityName
     * @param city
     * @return a list of emailAddress from a specific city
     * The information are emailAddress
     * @throws JsonProcessingException
     */

    @GetMapping(value = "/communityEmail")
    public String getAllPeopleEmailFromACity(@RequestParam String city) throws JsonProcessingException {
        List<String> recoveringEmailData = dataReadingDAO.getEmailData(city);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(recoveringEmailData);
    }


    // ENDPOINTS

    /**
     * This URL must be written in PostMan or equivalent
     * @param person
     * Add a new person in the json file
     */

    @PostMapping(value = "/person")
    public void addNewPerson(@RequestBody Person person){
        dataWritingDAO.writeNewPersonData(person);
    }

    /**
     * This URL must be written in PostMan or equivalent
     * @param person
     * Update a person in the json file
     */

    @PutMapping(value = "/person")
    public void updatePerson(@RequestBody Person person){
        dataUpdatingDAO.updatePersonData(person);
    }

    /**
     * This URL must be written /person?firstName=firstName&lastName=lastName
     * @param firstName
     * @param lastName
     * Delete a person in the json file
     */

    @DeleteMapping(value = "/person")
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName){
        dataDeletingDAO.deletePersonData(firstName, lastName);
    }

    /**
     * This URL must be written in PostMan or equivalent
     * @param fireStation
     * Add a new fireStation in the json file
     */

    @PostMapping(value = "/firestation")
    public void addNewFireStation(@RequestBody FireStation fireStation){
        dataWritingDAO.writeNewFireStationData(fireStation);
    }

    /**
     * This URL must be written in PostMan or equivalent
     * @param fireStation
     * Update a fireStation in the json file
     */

    @PutMapping(value = "/firestation")
    public void updateFirestationNumber(@RequestBody FireStation fireStation){
        dataUpdatingDAO.updateFireStationData(fireStation);
    }

    /**
     * This URL must be written /firestation?address=address&stationNumber=stationNumber
     * @param address
     * @param stationNumber
     * Delete a fireStation in the json file
     */

    @DeleteMapping(value = "/firestation")
    public void deleteFirestation(@RequestParam String address, @RequestParam String stationNumber){
        dataDeletingDAO.deleteFireStationData(address, stationNumber);
    }

    /**
     * This URL must be written in PostMan or equivalent
     * @param medicalRecord
     * add a new medicalRecord in the json file
     */

    @PostMapping(value = "/medicalRecord")
    public void addNewMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        dataWritingDAO.writeNewMedicalRecordsData(medicalRecord);
    }

    /**
     * This URL must be written in PostMan or equivalent
     * @param medicalRecord
     * Update a medicalRecord in the json file
     */

    @PutMapping(value = "/medicalRecord")
    public void updateMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        dataUpdatingDAO.updateMedicalRecordsData(medicalRecord);
    }

    /**
     * This URL must be written /medicalRecord?firstName=firstName&lastName=lastName
     * @param firstName
     * @param lastName
     * Delete a medical record in the json file
     */

    @DeleteMapping(value = "/medicalRecord")
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName){
        dataDeletingDAO.deleteMedicalRecordData(firstName, lastName);
    }

    //URL
    /*
    logger.info(" Message pour les réponses réussis {}", nomVariable)

    logger.error("Message pour les erreurs ou exceptions)

    logger.debug("Message pour les étapes ou calculs informatifs)
    }*/

    @GetMapping(value="/")
    public @ResponseBody String greeting(){
        return "Hello, World";
    }

    @GetMapping(value="/bordello")
    public @ResponseBody String bonjour(){
        return "Bonjour, Tintin";
    }
}
