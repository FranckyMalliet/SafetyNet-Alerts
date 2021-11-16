package com.safetynet.application.controller;

import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.repository.IMedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordController {

    private final static Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @Autowired
    private IMedicalRecordService IMedicalRecordService;

    // ENDPOINTS

    /**
     * This URL must be written in PostMan or equivalent
     * @param medicalRecord
     * add a new medicalRecord in the json file
     */

    @PostMapping(value = "/medicalRecord")
    public void addNewMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        logger.info("Adding a new medicalRecord in the json file");
        IMedicalRecordService.writeNewMedicalRecordData(medicalRecord);
    }

    /**
     * This URL must be written in PostMan or equivalent
     * @param medicalRecord
     * Update a medicalRecord in the json file
     */

    @PutMapping(value = "/medicalRecord")
    public void updateMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        logger.info("Updating a medicalRecord in the json file");
        IMedicalRecordService.updateMedicalRecordData(medicalRecord);
    }

    /**
     * This URL must be written /medicalRecord?firstName=firstName&lastName=lastName
     * @param //firstName
     * @param //lastName
     * Delete a medical record in the json file
     */

    @DeleteMapping(value = "/medicalRecord")
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName){
        logger.info("Deleting a medicalRecord in the json file");
        IMedicalRecordService.deleteMedicalRecordData(firstName, lastName);
    }
}
