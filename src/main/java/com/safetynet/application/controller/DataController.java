package com.safetynet.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.application.repository.DataReadingDAO;
import com.safetynet.application.repository.DataWritingDAO;
import com.safetynet.application.repository.JsonFileCurrentDataDAO;
import com.safetynet.application.model.Person;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.xml.crypto.Data;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RestController
public class DataController {

    private static Logger logger = LoggerFactory.getLogger(DataController.class);
    private ObjectMapper mapper = new ObjectMapper();

    private Person person;

    @Autowired
    private DataWritingDAO dataWritingDAO;

    @Autowired
    private DataReadingDAO dataReadingDAO;
    //private JsonFileCurrentDataDAO jsonFileCurrentDataDAO;

    public DataController(){}

    public DataController(DataWritingDAO dataWritingDAO){
        this.dataWritingDAO = dataWritingDAO;
    }

    public DataController(DataReadingDAO dataReadingDAO){
        this.dataReadingDAO = dataReadingDAO;
    }

    /*//Must be written /personInfo?firstName=firstName&lastName=lastName
    @GetMapping(value = "/personInfo")
    public String personInfo(@RequestParam String firstName, @RequestParam String lastName){
        var v= dataReadingDAO.readPersonData(firstName, lastName);
        return v.stream().map(p -> p.toString()).collect(Collectors.joining(", "));
    }*/

    //Must be written /personInfo?firstName=firstName&lastName=lastName
    /*@GetMapping(value = "/personInfo")
    public String personInfo(@RequestParam String firstName, @RequestParam String lastName) throws JsonProcessingException {
        List<Person> data = dataReadingDAO.readPersonData(firstName, lastName);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }*/

    //Must be written /personInfo?firstName=firstName&lastName=lastName
    @GetMapping(value = "/personInfo")
    public String personInfo(@RequestParam String firstName, @RequestParam String lastName) throws JsonProcessingException {
        List<List<String>> data = dataReadingDAO.readPersonMedicalData(firstName, lastName);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    //Must be written /fireStation?station=stationNumber
    /*@GetMapping(value = "/fireStation")
    public String fireStationInfo(@RequestParam String fireStationNumber) throws JsonProcessingException{
        List<String> data = dataReadingDAO.fireStationResidentData(fireStationNumber);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }*/

    @GetMapping(value = "/fireStation")
    public String fireStationInfo(@RequestParam String stationNumber) throws JsonProcessingException {
        List<String> data = dataReadingDAO.fireStationResidentData(stationNumber);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        //Must return email address of every people from a city
    }

    //URL
    /*@GetMapping(value = "/firestation?stationNumber=<station_number>")
    public Data getFireStationNumber(@PathVariable) {

    String getFirestationNumber = baseApiUrl + "/firestation" + stationNumber;

    logger.info(" Message pour les réponses réussis {}", nomVariable)

    logger.error("Message pour les erreurs ou exceptions)

    logger.debug("Message pour les étapes ou calculs informatifs)

    Must return the list of people covered by a firestation with name, last name, adress,
    phone, number of adults, number of children in the area
    }*/

    /*@GetMapping(value ="/childAlert?address=<adress>")
    public List<Children> getChildrenList (){

    Must return a list of children aged of 18 or less living in this address. The list must have the name, last name,
    the age of every children and a list of the other members living here.
    If they are no children, this url can send a null object
    }

    @GetMapping(value ="/phoneAlert?firestation=<firestation>")
    public getEveryPhoneFromAFirestationArea()

    Must return a list of all phones coming from the firestation area.
     */


    /*@GetMapping(value ="/fire?address=<address>")
    public getPeopleFromASpecificAddressInAFirestationArea(){

       Must return a list of all living people in the given address and the number of the firestation taking care of this address.
       The list must include the name, phone, age et medical precedents.
    }*/


    /*@GetMapping(value ="/flood/stations?stations=<a list of stations_numbers>")
    public getAllPeopleAddressDeservedByAFirestation(){

    Must return a list of all addresses deserved by a firestation. The list include name, phone, age and medicals
    precedents near the name
    }*/

    /*@GetMapping(value = "/personInfo?firstName=<firstName>&lastName=<lastName>")
    public @ResponseBody Person getPeopleInformations(
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName)
    {
        Must return the name, address, age, mail address and medicals precedents from someone by
        Using his name and last name. If two persons has the same name, they must appear.
        Person person = new Person();

        person = dataReadingDAO.readPersonData(firstName, lastName);
        return person;
    }*/

    //Must be written /communityEmail?city=cityName
    @GetMapping(value = "/communityEmail")
    public String getAllPeopleEmailFromACity(@RequestParam String city) throws JsonProcessingException {
        List<String> recoveringEmailData = dataReadingDAO.getEmailData(city);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(recoveringEmailData);
      //Must return email address of every people from a city
    }

    // ENDPOINTS
    @PostMapping(value = "/person")
    public @ResponseBody Object addNewPerson(@RequestBody Person person){
        return dataWritingDAO.writePersonData(person);
    }

    /*
    @PutMapping(value = "/person")
    public updatePerson(){

        Update a person, except the name and last name, all the other data can be changed
    }

    @DeleteMapping(value = "/person")
    public deletePerson(){

        delete a person data using his name and last name
    }*/


    /*@PostMapping(value = "/firestation")
    public addNewFireStation(){

    Add a new firestation mapping or address

    }

    @PutMapping(value = "/firestation")
    public updateFirestationNumber(){

        Update the number of a firestation from a specific address
    }

    @DeleteMapping(value = "/firestation")
    public deleteFirestation(){

        delete a firestation mapping or address
    }*/


    /*@PostMapping(value = "/medicalRecord")
    public addNewMedicalRecord(){

    Add a new medical record

    }

    @PutMapping(value = "/medicalRecord")
    public updateMedicalRecord(){

        Update the medical record, the name and last name doesn t change
    }

    @DeleteMapping(value = "/medicalRecord")
    public deleteMedicalRecord(){

        delete a medical record using a combinaison of name and last name
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
