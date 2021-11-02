package com.safetynet.application.controller;

import com.safetynet.application.model.FireStation;
import com.safetynet.application.repository.IFireStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FireStationController {

    private final static Logger logger = LoggerFactory.getLogger(FireStationController.class);

    @Autowired
    private IFireStationService IFireStationService;

    // ENDPOINTS
    /**
     * This URL must be written in PostMan or equivalent
     * @param fireStation
     * Add a new fireStation in the json file
     */

    @PostMapping(value = "/fireStation")
    public void addNewFireStation(@RequestBody FireStation fireStation){
        logger.info("Adding a new fireStation in the json file");
        IFireStationService.writeNewFireStationData(fireStation);
    }

    /**
     * This URL must be written in PostMan or equivalent
     * @param fireStation
     * Update a fireStation in the json file
     */

    @PutMapping(value = "/fireStation")
    public void updateFireStationNumber(@RequestBody FireStation fireStation){
        logger.info("Updating a fireStation in the json file");
        IFireStationService.updateFireStationData(fireStation);
    }

    /**
     * This URL must be written /fireStation?address=address&stationNumber=stationNumber
     * @param address
     * @param stationNumber
     * Delete a fireStation in the json file
     */

    @DeleteMapping(value = "/fireStation")
    public void deleteFireStation(@RequestParam String address, @RequestParam String stationNumber){
        logger.info("Deleting a fireStation in the json file");
        IFireStationService.deleteFireStationData(address, stationNumber);
    }
}
