package com.safetynet.application.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safetynet.application.model.FireStation;
import com.safetynet.application.repository.IFireStationServiceTest;
import com.safetynet.application.repository.IMedicalRecordServiceTest;
import com.safetynet.application.repository.IPersonServiceTest;
import org.junit.jupiter.api.Assertions;
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
public class FireStationServiceUnitTest {

    // Mock fireStation data
    private final String fireStationNumber = "2";
    private final String fireStationAddress = "25 12th St";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private IFireStationServiceTest IFireStationServiceTest;

    @Autowired
    private IMedicalRecordServiceTest IMedicalRecordServiceTest;

    @Autowired
    private IPersonServiceTest IPersonServiceTest;

    @Autowired
    private static MockMvc mockMvc;

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // File path for testing
    private final String path = "E:/Users/Francky Malliet/git/SafetyNet Alerts/dataTest.json";

    // Mock fireStation data
    private FireStation fireStationData = new FireStation();
    private final String station = "2";
    private final String address = "951 LoneTree Rd";

    // For FireStation testing
    private List<FireStation> fireStationList = new ArrayList<>();
    private List<String> fireStationListOfString = new ArrayList<>();
    private FireStation fireStationDataRecovered = new FireStation();

    @Test
    public void getFireStationAddressWithNumberTest(){
        //WHEN
        fireStationListOfString = IFireStationServiceTest.getFireStationAddressWithNumber(station);
        for(String fireStationElement : fireStationListOfString){
            fireStationDataRecovered.setAddress(fireStationElement);
        }

        //THEN
        Assertions.assertNotNull(fireStationListOfString);
        Assertions.assertEquals(address, fireStationDataRecovered.getAddress());
    }

    @Test
    public void getFireStationNumberWithAddressTest(){
        //WHEN
        fireStationListOfString = IFireStationServiceTest.getFireStationNumberWithAddress(address);
        for(String fireStationElement : fireStationListOfString){
            fireStationDataRecovered.setStation(fireStationElement);
        }

        //THEN
        Assertions.assertNotNull(fireStationListOfString);
        Assertions.assertEquals(station, fireStationDataRecovered.getStation());
    }

    @Test
    public void getFireStationInformationWithNumberTest(){
        //WHEN
        fireStationList = IFireStationServiceTest.getFireStationInformationWithNumber(station);
        for(FireStation fireStationElement : fireStationList){
            fireStationDataRecovered.setStation(fireStationElement.getStation());
            fireStationDataRecovered.setAddress(fireStationElement.getAddress());
        }

        //THEN
        Assertions.assertNotNull(fireStationList);
        Assertions.assertEquals(station, fireStationDataRecovered.getStation());
        Assertions.assertEquals(address, fireStationDataRecovered.getAddress());
    }

    @Test
    public void getAllFireStationTest(){
        //WHEN
        fireStationList = IFireStationServiceTest.getAllFireStation();

        //THEN
        Assertions.assertNotNull(fireStationList);
    }

    //ENDPOINT FIRESTATION
    @Test
    public void givenAFireStationInformation_AddANewFireStationOrAddress() throws Exception {
        //GIVEN
        FireStation fireStation = new FireStation();
        fireStation.setAddress(fireStationAddress);
        fireStation.setStation(fireStationNumber);

        //WHEN
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String jsonRequest = objectWriter.writeValueAsString(fireStation);

        //THEN
        mockMvc.perform(post("/fireStationTest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAFireStationNumber_UpdateTheFireStationAddress() throws Exception {
        //GIVEN
        FireStation fireStation = new FireStation();
        fireStation.setAddress(fireStationAddress);
        fireStation.setStation(fireStationNumber);

        //WHEN
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String jsonRequest = objectWriter.writeValueAsString(fireStation);

        //THEN
        mockMvc.perform(put("/fireStationTest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAFireStationNumber_DeleteTheFireStationOrAddress() throws Exception {
        mockMvc.perform(delete("/fireStationTest")
                        .param("address", address)
                        .param("stationNumber", fireStationNumber))
                .andDo(print()).andExpect(status().isOk());
    }
}
