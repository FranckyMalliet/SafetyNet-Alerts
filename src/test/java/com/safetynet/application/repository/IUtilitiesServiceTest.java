package com.safetynet.application.repository;

import com.fasterxml.jackson.databind.JsonNode;

public interface IUtilitiesServiceTest {

    public JsonNode getRootArray();
    public String getPersonAge(String birthDate);
}
