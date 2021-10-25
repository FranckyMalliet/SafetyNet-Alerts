package com.safetynet.application.utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.application.repository.utilities.MethodsUtilDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MethodsUtil implements MethodsUtilDAO {

    private final String path = "E:/Users/Francky Malliet/git/SafetyNet-Alerts/data.json";
    private final static Logger logger = LoggerFactory.getLogger(MethodsUtil.class);
    private final ObjectMapper mapper = new ObjectMapper();

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final static LocalDate today = LocalDate.now();

    @Override
    public JsonNode getRootArray(){
        try{
            return mapper.readTree(new File(path));
        } catch(IOException error){
            logger.error("Json file not found");
            throw new IllegalStateException(error);
        }
    }

    @Override
    public int getPersonAge(String birthDate){
        LocalDate birthDay = LocalDate.parse(birthDate, dateTimeFormatter);

        Period actualAge = Period.between(birthDay, today);
        return actualAge.getYears();
    }

    @Override
    public List<String> getMedicalData(JsonNode root, String medicalData) {
        try{
            return mapper.readValue(String.valueOf(root.path(medicalData)), new TypeReference<>() {});
        } catch(IOException error){
            logger.error("Json file not found");
            throw new IllegalStateException(error);
        }
    }

    @Override
    public List<String> getListOfStringFromJson(JsonNode root, String rootPointer){
        List<String> listOfStrings = new ArrayList<>();
        List<String> stringExtraction = getMedicalData(root, rootPointer);
        stringExtraction.forEach(elements -> listOfStrings.add(elements));
        return listOfStrings;
    }
}
