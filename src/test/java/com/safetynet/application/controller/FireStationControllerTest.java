package com.safetynet.application.controller;

import com.safetynet.application.model.FireStation;
import com.safetynet.application.repository.IFireStationServiceTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FireStationControllerTest {

    private final static Logger logger = LoggerFactory.getLogger(FireStationControllerTest.class);

    @Autowired
    private IFireStationServiceTest IFireStationServiceTest;

    // ENDPOINTS
    /**
     * This URL must be written in PostMan or equivalent
     * @param fireStation
     * Add a new fireStation in the json file
     */

    @PostMapping(value = "/fireStationTest")
    public void addNewFireStation(@RequestBody FireStation fireStation){
        logger.info("Adding a new fireStation in the json file");
        IFireStationServiceTest.writeNewFireStationData(fireStation);
    }

    /**
     * This URL must be written in PostMan or equivalent
     * @param fireStation
     * Update a fireStation in the json file
     */

    @PutMapping(value = "/fireStationTest")
    public void updateFireStationNumber(@RequestBody FireStation fireStation){
        logger.info("Updating a fireStation in the json file");
        IFireStationServiceTest.updateFireStationData(fireStation);
    }

    /**
     * This URL must be written /fireStation?address=address&stationNumber=stationNumber
     * @param address
     * @param stationNumber
     * Delete a fireStation in the json file
     */

    @DeleteMapping(value = "/fireStationTest")
    public void deleteFireStation(@RequestParam String address, @RequestParam String stationNumber){
        logger.info("Deleting a fireStation in the json file");
        IFireStationServiceTest.deleteFireStationData(address, stationNumber);
    }
}
