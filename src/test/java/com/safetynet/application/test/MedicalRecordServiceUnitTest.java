package com.safetynet.application.test;

import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.repository.IMedicalRecordServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordServiceUnitTest {

    @Autowired
    private IMedicalRecordServiceTest IMedicalRecordServiceTest;

    @BeforeEach
    public void setup(){

        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");

        allergies.add("nillacilan");
    }

    // File path for testing
    private final String path = "E:/Users/Francky Malliet/git/SafetyNet Alerts/dataTest.json";

    // Mock medicalRecord data
    private final String firstName = "John";
    private final String lastName = "Boyd";
    private final String birthDate = "03/06/1984";
    private List<String> medications = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();

    // For Medical Record testing
    private List<MedicalRecord> medicalRecordList = new ArrayList<>();

    @Test
    public void getMedicalRecordDataWithFirstNameAndLastNameTest(){
        //WHEN
        MedicalRecord medicalRecordData = IMedicalRecordServiceTest.getMedicalRecordDataWithFirstNameAndLastName(firstName, lastName);

        //THEN
        Assertions.assertNotNull(medicalRecordData);
        Assertions.assertEquals(firstName, medicalRecordData.getFirstName());
        Assertions.assertEquals(lastName, medicalRecordData.getLastName());
        Assertions.assertEquals(birthDate, medicalRecordData.getBirthdate());
        Assertions.assertEquals(medications, medicalRecordData.getMedications());
        Assertions.assertEquals(allergies, medicalRecordData.getAllergies());
    }

    @Test
    public void getAllMedicalRecordTest(){
        //WHEN
        medicalRecordList = IMedicalRecordServiceTest.getAllMedicalRecord();

        //THEN
        Assertions.assertNotNull(medicalRecordList);
    }
}
