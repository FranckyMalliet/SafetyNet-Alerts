package com.safetynet.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.application.repository.IUtilitiesServiceTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class UtilitiesServiceTest implements IUtilitiesServiceTest {

    private final String path = "E:/Users/Francky Malliet/git/SafetyNet-Alerts/data.json";
    private final static Logger logger = LoggerFactory.getLogger(UtilitiesServiceTest.class);
    private final ObjectMapper mapper = new ObjectMapper();

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final LocalDate today = LocalDate.now();

    /**
     * Method used to wrap the IOException encountered with
     * the objectmapper when he is reading the tree from a json file
     * @return a JsonNode
     */

    @Override
    public JsonNode getRootArray(){
        try{
            return mapper.readTree(new File(path));
        } catch(IOException error){
            logger.error("getRootArray Method, Json file not found");
            throw new IllegalStateException(error);
        }
    }

    /**
     * Method used to calculate the age from a birthdate
     * @param birthDate
     * @return an integer of a person age
     */

    @Override
    public String getPersonAge(String birthDate){
        LocalDate birthDay = LocalDate.parse(birthDate, dateTimeFormatter);

        Period actualAge = Period.between(birthDay, today);
        return String.valueOf(actualAge.getYears());
    }
}
