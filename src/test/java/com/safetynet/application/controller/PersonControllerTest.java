package com.safetynet.application.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.application.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonControllerTest {

    private final static Logger logger = LoggerFactory.getLogger(PersonControllerTest.class);
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private com.safetynet.application.repository.IPersonService IPersonService;

    /**
     * This URL must be written /fireStation?stationNumber=stationNumber
     * @param stationNumber
     * @return a list of persons covered by a fireStation.
     * The information are : firstName, lastName, address, phoneNumber and
     * the number of adults and children in the area
     * @throws JsonProcessingException
     */

    @GetMapping(value = "/fireStationTest")
    public String fireStationResidentsInfo(@RequestParam String stationNumber) throws JsonProcessingException {
        List<List<String>> data = IPersonService.getFireStationResidentData(stationNumber);
        logger.info("Data from residents coming from the fireStation number : " + stationNumber + " successfully recovered");
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

    @GetMapping(value ="/childAlertTest")
    public String getChildrenList(@RequestParam String address) throws JsonProcessingException {
        List<Person> data = IPersonService.getChildAlert(address);
        logger.info("Children list coming from : " + address + " successfully recovered");
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    /**
     * This URL must be written /phoneAlert?fireStationNumber=fireStationNumber
     * @param stationNumber
     * @return a list of phoneNumber of all residents deserved by a
     * specific fireStation
     * @throws JsonProcessingException
     */

    @GetMapping(value = "/phoneAlertTest")
    public String getResidentsPhone(@RequestParam String stationNumber) throws JsonProcessingException {
        List<String> data = IPersonService.getResidentPhone(stationNumber);
        logger.info("Phone list of residents coming from the fireStation number : " + stationNumber + " successfully recovered");
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

    @GetMapping(value ="/fireTest")
    public String getMedicalInformationFromAddress(@RequestParam String address) throws JsonProcessingException {
        List<List<String>> data = IPersonService.getMedicalInformationFromAddress(address);
        logger.info("List of residents deserved by a fireStation at : " + address + " successfully recovered");
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    /**
     * This URL must be written /flood/stations?stationNumber=stationNumber
     * @param stationNumber
     * @return a list of people deserved by a specific fireStation grouped by address
     * The information are firstName, lastName, phoneNumber, age, medications, allergies
     * @throws JsonProcessingException
     */

    @GetMapping(value ="/flood/stationsTest")
    public String getAllPersonsDataFromAListOfFireStation(@RequestParam String stationNumber) throws JsonProcessingException {
        List<List<String>> data = IPersonService.getAllPersonsDataFromAListOfFireStation(stationNumber);
        logger.info("List of residents deserved by the fireStation number : " + stationNumber + " successfully recovered");
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

    @GetMapping(value = "/personInfoTest")
    public String getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) throws JsonProcessingException {
        List<List<String>> data = IPersonService.getPersonMedicalData(firstName, lastName);
        logger.info("Data from " + firstName + " " + lastName + " successfully recovered");
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    /**
     * This URL must be written /communityEmail?city=cityName
     * @param city
     * @return a list of emailAddress from a specific city
     * The information are emailAddress
     * @throws JsonProcessingException
     */

    @GetMapping(value = "/communityEmailTest")
    public String getAllPeopleEmailFromACity(@RequestParam String city) throws JsonProcessingException {
        List<String> recoveringEmailData = IPersonService.getEmailData(city);
        logger.info("List of email coming from " + city + " successfully recovered");
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(recoveringEmailData);
    }

    // ENDPOINTS

    /**
     * This URL must be written in PostMan or equivalent
     * @param person
     * Add a new person in the json file
     */

    @PostMapping(value = "/personTest")
    public void addNewPerson(@RequestBody Person person){
        logger.info("Adding a new person in the json file");
        IPersonService.writeNewPersonData(person);
    }

    /**
     * This URL must be written in PostMan or equivalent
     * @param person
     * Update a person in the json file
     */

    @PutMapping(value = "/personTest")
    public void updatePerson(@RequestBody Person person){
        logger.info("Updating a person in the json file");
        IPersonService.updatePersonData(person);
    }

    /**
     * This URL must be written /person?firstName=firstName&lastName=lastName
     * @param firstName
     * @param lastName
     * Delete a person in the json file
     */

    @DeleteMapping(value = "/personTest")
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName){
        logger.info("Deleting a person in the json file");
        IPersonService.deletePersonData(firstName, lastName);
    }
}

