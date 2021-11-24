package com.safetynet.application.test;

import com.safetynet.application.model.FireStation;
import com.safetynet.application.repository.IFireStationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationServiceUnitTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private IFireStationService IFireStationServiceTest;

    @Autowired
    private static MockMvc mockMvc;

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // Mock fireStation data
    private FireStation fireStationData = new FireStation();
    private final String station = "2";
    private final String address = "29 15th St";

    @Test
    public void getFireStationAddressWithNumberTest(){
        //GIVEN
        List<String> fireStationListOfString = new ArrayList<>();

        //WHEN
        fireStationListOfString = IFireStationServiceTest.getFireStationAddressWithNumber(station);

        //THEN
        Assertions.assertNotNull(fireStationListOfString);
    }

    @Test
    public void getFireStationNumberWithAddressTest(){
        //GIVEN
        List<String> fireStationListOfString = new ArrayList<>();
        FireStation fireStationDataRecovered = new FireStation();

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
        //GIVEN
        List<FireStation> fireStationList = new ArrayList<>();

        //WHEN
        fireStationList = IFireStationServiceTest.getFireStationInformationWithNumber(station);

        //THEN
        Assertions.assertNotNull(fireStationList);
    }

    @Test
    public void getAllFireStationTest(){
        //GIVEN
        List<FireStation> fireStationList = new ArrayList<>();

        //WHEN
        fireStationList = IFireStationServiceTest.getAllFireStation();

        //THEN
        Assertions.assertNotNull(fireStationList);
    }
}
