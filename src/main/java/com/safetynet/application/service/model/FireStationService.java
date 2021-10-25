package com.safetynet.application.service.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.application.model.FireStation;
import com.safetynet.application.repository.model.FireStationDAO;
import com.safetynet.application.repository.utilities.MethodsUtilDAO;
import com.safetynet.application.service.JsonReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService implements FireStationDAO {

    private final static Logger logger = LoggerFactory.getLogger(JsonReaderService.class);

    @Autowired
    private MethodsUtilDAO methodsUtilDAO;

    @Override
    public List<FireStation> getFireStationInformationWithNumber(String element) {

        List<FireStation> fireStationInformationList = new ArrayList<>();

        for(JsonNode root : methodsUtilDAO.getRootArray().path("firestations")){
            if(root.path("station").asText().equals(element)){
                FireStation fireStationData = new FireStation();
                fireStationData.setAddress(root.path("address").asText());
                fireStationData.setStation(root.path("station").asText());

                fireStationInformationList.add(fireStationData);
            }
        }
        return fireStationInformationList;
    }

    @Override
    public List<String> getFireStationInformationWithAddress(String element) {

        List<String> fireStationInformationList = new ArrayList<>();

        for(JsonNode root : methodsUtilDAO.getRootArray().path("firestations")){
            if(root.path("address").asText().equals(element)){
                FireStation fireStationData = new FireStation();
                fireStationData.setAddress(root.path("address").asText());
                fireStationData.setStation(root.path("station").asText());

                String fireStationNumber = fireStationData.getStation();

                logger.info(element + " retrieving data");
                fireStationInformationList.add("FireStation number is " + fireStationNumber);
            }
        }

        return fireStationInformationList;
    }

    @Override
    public List<String> getFireStationAddressWithNumber(String element) {

        List<String> fireStationAddressList = new ArrayList<>();

        for(JsonNode root : methodsUtilDAO.getRootArray().path("firestations")){
            if(root.path("station").asText().equals(element)){
                FireStation fireStationData = new FireStation();
                fireStationData.setAddress(root.path("address").asText());
                fireStationData.setStation(root.path("station").asText());

                String fireStationAddress = fireStationData.getAddress();

                logger.info(element + " retrieving data");
                fireStationAddressList.add("FireStation number is " + fireStationAddress);
            }
        }

        return fireStationAddressList;
    }

    @Override
    public List<String> getFireStationStationNumberWithAddress(String element) {

        List<String> fireStationStationList = new ArrayList<>();

        for(JsonNode root : methodsUtilDAO.getRootArray().path("firestations")){
            if(root.path("address").asText().equals(element)){
                FireStation fireStationData = new FireStation();
                fireStationData.setAddress(root.path("address").asText());
                fireStationData.setStation(root.path("station").asText());

                String fireStationNumber = fireStationData.getStation();

                logger.info(element + " retrieving data");
                fireStationStationList.add("FireStation number is " + fireStationNumber);
            }
        }

        return fireStationStationList;
    }

    @Override
    public List<FireStation> getAllFireStation() {

        List<FireStation> fireStationList = new ArrayList<>();

        for (JsonNode root : methodsUtilDAO.getRootArray().get("firestations")) {
            FireStation fireStationData = new FireStation();

            fireStationData.setAddress(root.path("address").asText());
            fireStationData.setStation(root.path("station").asText());

            fireStationList.add(fireStationData);
        }

        return fireStationList;
    }
}
