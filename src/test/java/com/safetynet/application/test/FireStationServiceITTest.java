package com.safetynet.application.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safetynet.application.model.FireStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationServiceITTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private static MockMvc mockMvc;

    // Mock fireStation data
    private final String fireStationNumber = "2";
    private final String fireStationAddress = "25 12th St";

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    //ENDPOINT FIRESTATION
    @Test
    public void givenAFireStationInformation_AddANewFireStation() throws Exception {
        //GIVEN
        FireStation fireStation = new FireStation();
        fireStation.setStation(fireStationNumber);
        fireStation.setAddress(fireStationAddress);

        //WHEN
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String jsonRequest = objectWriter.writeValueAsString(fireStation);

        //THEN
        mockMvc.perform(post("/fireStation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAFireStationNumber_UpdateTheFireStation() throws Exception {
        //GIVEN
        FireStation fireStation = new FireStation();
        fireStation.setStation(fireStationNumber);
        fireStation.setAddress(fireStationAddress);

        //WHEN
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String jsonRequest = objectWriter.writeValueAsString(fireStation);

        //THEN
        mockMvc.perform(put("/fireStation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void givenTheNumberAndAddressOfAFireStation_DeleteTheFireStation() throws Exception {
        mockMvc.perform(delete("/fireStation")
                        .param("stationNumber", fireStationNumber)
                        .param("address", fireStationAddress))
                .andDo(print()).andExpect(status().isOk());
    }
}
