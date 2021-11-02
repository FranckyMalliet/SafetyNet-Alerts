package com.safetynet.application.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import com.safetynet.application.repository.IUtilitiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DataDAO {

    private final String path = "E:/Users/Francky Malliet/git/SafetyNet-Alerts/data.json";
    private final static Logger logger = LoggerFactory.getLogger(DataDAO.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private com.safetynet.application.repository.IPersonService IPersonService;

    @Autowired
    private com.safetynet.application.repository.IFireStationService IFireStationService;

    @Autowired
    private com.safetynet.application.repository.IMedicalRecordService IMedicalRecordService;

    @Autowired
    private IUtilitiesService IUtilitiesService;

    /**
     * Method used to write all the data on the json file
     * @param personList
     * @param fireStationList
     * @param medicalRecordsList
     * @return a Map with list corresponding of each node from
     * the json file
     */

    public void writeAllData(List<Person> personList, List<FireStation> fireStationList, List<MedicalRecord> medicalRecordsList){

        Map<String, List> allDataMap = new HashMap<>();

        allDataMap.put("persons", personList);
        allDataMap.put("firestations", fireStationList);
        allDataMap.put("medicalrecords", medicalRecordsList);

        try {
            mapper.writeValue(new File(path),allDataMap);
        } catch(IOException error){
            logger.error("writeAllData Method, Json file not found");
            throw new IllegalStateException(error);
        }
    }
}
