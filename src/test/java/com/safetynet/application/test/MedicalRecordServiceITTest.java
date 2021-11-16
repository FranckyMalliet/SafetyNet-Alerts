package com.safetynet.application.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safetynet.application.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordServiceITTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private static MockMvc mockMvc;

    // Mock medicalRecord data
    private final String firstName = "Joe";
    private final String lastName = "Williams";
    private final String birthDate = "03/10/1995";
    private List<String> medications = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        medications.add("pharmacol:5000mg");
        medications.add("terazine:10mg");

        allergies.add("peanut");
    }

    //ENDPOINT MEDICAL RECORD
    @Test
    public void givenAMedicalRecordInformation_AddANewMedicalRecord() throws Exception {
        //GIVEN
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName(firstName);
        medicalRecord.setLastName(lastName);
        medicalRecord.setBirthdate(birthDate);
        medicalRecord.setMedications(medications);
        medicalRecord.setAllergies(allergies);

        //WHEN
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String jsonRequest = objectWriter.writeValueAsString(medicalRecord);

        //THEN
        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void givenANameAndLastName_UpdateTheMedicalRecordOfThisPerson() throws Exception {
        //GIVEN
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName(firstName);
        medicalRecord.setLastName(lastName);
        medicalRecord.setBirthdate(birthDate);
        medicalRecord.setMedications(medications);
        medicalRecord.setAllergies(allergies);

        //WHEN
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String jsonRequest = objectWriter.writeValueAsString(medicalRecord);

        //THEN
        mockMvc.perform(put("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void givenANameAndLastName_DeleteTheMedicalRecordOfThisPerson() throws Exception {
        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", firstName)
                        .param("lastName", lastName))
                .andDo(print()).andExpect(status().isOk());
    }
}
