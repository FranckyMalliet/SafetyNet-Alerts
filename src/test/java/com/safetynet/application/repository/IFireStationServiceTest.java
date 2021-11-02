package com.safetynet.application.repository;

import com.safetynet.application.model.FireStation;

import java.util.List;

public interface IFireStationServiceTest {

    public List<String> getFireStationAddressWithNumber(String stationNumber);
    public List<String> getFireStationNumberWithAddress(String address);
    public List<FireStation> getFireStationInformationWithNumber(String element);
    public void writeNewFireStationData(FireStation fireStation);
    public void updateFireStationData(FireStation fireStation);
    public void deleteFireStationData(String address, String stationNumber);
    public List<FireStation> getAllFireStation();
}
