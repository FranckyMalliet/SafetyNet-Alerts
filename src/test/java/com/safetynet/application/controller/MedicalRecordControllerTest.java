package com.safetynet.application.controller;


import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.repository.IMedicalRecordServiceTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordControllerTest {

    private final static Logger logger = LoggerFactory.getLogger(MedicalRecordControllerTest.class);

    @Autowired
    private IMedicalRecordServiceTest IMedicalRecordServiceTest;

    // ENDPOINTS

    /**
     * This URL must be written in PostMan or equivalent
     * @param medicalRecord
     * add a new medicalRecord in the json file
     */

    @PostMapping(value = "/medicalRecordTest")
    public void addNewMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        logger.info("Adding a new medicalRecord in the json file");
        IMedicalRecordServiceTest.writeNewMedicalRecordsData(medicalRecord);
    }

    /**
     * This URL must be written in PostMan or equivalent
     * @param medicalRecord
     * Update a medicalRecord in the json file
     */

    @PutMapping(value = "/medicalRecordTest")
    public void updateMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        logger.info("Updating a medicalRecord in the json file");
        IMedicalRecordServiceTest.updateMedicalRecordsData(medicalRecord);
    }

    /**
     * This URL must be written /medicalRecord?firstName=firstName&lastName=lastName
     * @param //firstName
     * @param //lastName
     * Delete a medical record in the json file
     */

    @DeleteMapping(value = "/medicalRecordTest")
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName){
        logger.info("Deleting a medicalRecord in the json file");
        IMedicalRecordServiceTest.deleteMedicalRecordData(firstName, lastName);
    }
}
