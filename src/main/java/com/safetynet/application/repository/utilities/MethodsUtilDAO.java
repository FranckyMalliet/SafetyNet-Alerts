package com.safetynet.application.repository.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MethodsUtilDAO {

    public JsonNode getRootArray();
    public int getPersonAge(String birthDate);
    public List<String> getMedicalData(JsonNode root, String medicalData);
    public List<String> getListOfStringFromJson(JsonNode root, String rootPointer);
}
