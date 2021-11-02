package com.safetynet.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.application.dao.DataDAOTest;
import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import com.safetynet.application.repository.IFireStationServiceTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationServiceTest implements IFireStationServiceTest {

    private final static Logger logger = LoggerFactory.getLogger(FireStationServiceTest.class);

    @Lazy
    @Autowired
    private com.safetynet.application.repository.IPersonServiceTest IPersonServiceTest;

    @Lazy
    @Autowired
    private com.safetynet.application.repository.IMedicalRecordServiceTest IMedicalRecordServiceTest;

    @Lazy
    @Autowired
    private com.safetynet.application.repository.IUtilitiesServiceTest IUtilitiesServiceTest;

    @Lazy
    @Autowired
    private DataDAOTest dataDAOTest;

    /**
     * Method used for recovering information from fireStation using the stationNumber
     * @param element
     * @return a list of fireStation that include their address and stationNumber
     */

    @Override
    public List<String> getFireStationAddressWithNumber(String element) {

        List<String> fireStationAddressList = new ArrayList<>();

        for(JsonNode root : IUtilitiesServiceTest.getRootArray().path("firestations")){
            if(root.path("station").asText().equals(element)){
                FireStation fireStationData = new FireStation();
                fireStationData.setAddress(root.path("address").asText());
                fireStationData.setStation(root.path("station").asText());

                logger.debug(fireStationData.getAddress() + " retrieving data");
                fireStationAddressList.add(fireStationData.getAddress());
            }
        }

        return fireStationAddressList;
    }

    /**
     * Method used for recovering information from fireStation using the address
     * @param element
     * @return a list of fireStation that include their stationNumber
     */

    @Override
    public List<String> getFireStationNumberWithAddress(String element) {

        List<String> fireStationStationList = new ArrayList<>();

        for(JsonNode root : IUtilitiesServiceTest.getRootArray().path("firestations")){
            if(root.path("address").asText().equals(element)){
                FireStation fireStationData = new FireStation();
                fireStationData.setAddress(root.path("address").asText());
                fireStationData.setStation(root.path("station").asText());

                logger.debug(fireStationData.getStation() + " retrieving data");
                fireStationStationList.add(fireStationData.getStation());
            }
        }

        return fireStationStationList;
    }

    /**
     * Method used for recovering information from fireStation using the address
     * @param element
     * @return a list of fireStation that include their address and stationNumber
     */

    @Override
    public List<FireStation> getFireStationInformationWithNumber(String element) {

        List<FireStation> fireStationList = new ArrayList<>();

        for (JsonNode root : IUtilitiesServiceTest.getRootArray().path("firestations")) {
            if (root.path("station").asText().equals(element)) {
                FireStation fireStationData = new FireStation();
                fireStationData.setAddress(root.path("address").asText());
                fireStationData.setStation(root.path("station").asText());

                logger.debug("Station number " + fireStationData.getStation() + " at " + fireStationData.getAddress() + " retrieving data");
                fireStationList.add(fireStationData);
            }
        }

        return fireStationList;
    }

    /**
     * Method used to add a new fireStation in the json file
     * Used by DataController class as /fireStation, request mapping POST
     * @param fireStation
     * Add a new fireStation in the json file
     */

    @Override
    public void writeNewFireStationData(FireStation fireStation) {

        List<Person> personList = IPersonServiceTest.getAllPerson();
        List<FireStation> fireStationList = getAllFireStation();
        List<MedicalRecord> medicalRecordsList = IMedicalRecordServiceTest.getAllMedicalRecord();

        FireStation fireStationData = new FireStation();
        fireStationData.setAddress(fireStation.getAddress());
        fireStationData.setStation(fireStation.getStation());

        fireStationList.add(fireStationData);

        logger.debug("Adding new fireStation data in json file "
                + fireStationData.getAddress() + " "
                + fireStationData.getStation() + " ");

        dataDAOTest.writeAllDataTest(personList, fireStationList, medicalRecordsList);
    }

    /**
     * Method used to update a fireStation
     * Used by DataController class as /fireStation, request mapping PUT
     * @param fireStation
     * Update a fireStation in the json file
     */

    @Override
    public void updateFireStationData(FireStation fireStation) {

        List<Person> personList = IPersonServiceTest.getAllPerson();
        List<FireStation> fireStationList = getAllFireStation();
        List<MedicalRecord> medicalRecordsList = IMedicalRecordServiceTest.getAllMedicalRecord();

        FireStation updateFireStation = new FireStation();
        updateFireStation.setAddress(fireStation.getAddress());
        updateFireStation.setStation(fireStation.getStation());

        logger.debug("Updating fireStation : " + updateFireStation.getAddress() + " " + updateFireStation.getStation());

        for (FireStation fireStationData : fireStationList) {
            if (updateFireStation.getAddress().equals(fireStationData.getAddress())) {
                fireStationData.setStation(updateFireStation.getStation());

                logger.debug("Modifying fireStation data in json file "
                        + fireStationData.getAddress() + " "
                        + fireStationData.getStation());
            }

            dataDAOTest.writeAllDataTest(personList, fireStationList, medicalRecordsList);
        }
    }

    /**
     * Method used to delete a fireStation using address and stationNumber
     * Used by DataController class as /firestation, request mapping DEL
     * @param address
     * @param stationNumber
     * Delete a fireStation
     */

    @Override
    public void deleteFireStationData(String address, String stationNumber){

        List<Person> personList = IPersonServiceTest.getAllPerson();
        List<FireStation> fireStationList = getAllFireStation();
        List<MedicalRecord> medicalRecordsList = IMedicalRecordServiceTest.getAllMedicalRecord();

        List<FireStation> newFireStationList = new ArrayList<>();

        for(FireStation fireStationData : fireStationList){
            String fireStationAddress = fireStationData.getAddress();
            String fireStationNumber = fireStationData.getStation();
            if(!fireStationAddress.equals(address)&!fireStationNumber.equals(stationNumber)){
                newFireStationList.add(fireStationData);
            }
        }

        logger.debug("Deleting station number :" + stationNumber + " at " + address);
        dataDAOTest.writeAllDataTest(personList, fireStationList, medicalRecordsList);
    }

    /**
     * Method used for recovering all the fireStations in the json file
     * @return a list of all fireStation include in the json file
     */

    @Override
    public List<FireStation> getAllFireStation() {

        List<FireStation> fireStationList = new ArrayList<>();

        for (JsonNode root : IUtilitiesServiceTest.getRootArray().get("firestations")) {
            FireStation fireStationData = new FireStation();

            fireStationData.setAddress(root.path("address").asText());
            fireStationData.setStation(root.path("station").asText());

            fireStationList.add(fireStationData);
        }

        logger.debug("Retrieving all the fireStations data");
        return fireStationList;
    }
}
