package com.safetynet.application.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface DataDeletingDAO {
    public void deletePersonData(String firstName, String lastName);
    public void deleteFireStationData(String address, String stationNumber);
    public void deleteMedicalRecordData(String firstName, String lastName);
}
