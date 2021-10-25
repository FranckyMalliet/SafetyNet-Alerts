package com.safetynet.application.repository.model;

import com.safetynet.application.model.FireStation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FireStationDAO {

    public List<FireStation> getFireStationInformationWithNumber(String stationNumber);
    public List<String> getFireStationInformationWithAddress(String address);
    public List<String> getFireStationAddressWithNumber(String stationNumber);
    public List<String> getFireStationStationNumberWithAddress(String address);
    public List<FireStation> getAllFireStation();
}
