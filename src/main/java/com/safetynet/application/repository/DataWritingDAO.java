package com.safetynet.application.repository;

import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface DataWritingDAO {

    Object writePersonData(Person person);
    boolean writeFireStationData(FireStation fireStation);
    boolean writeMedicalRecordsData(MedicalRecord medicalRecord);
}
