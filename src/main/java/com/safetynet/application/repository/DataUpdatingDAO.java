package com.safetynet.application.repository;

import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface DataUpdatingDAO {

    public void updatePersonData(Person person);
    public void updateFireStationData(FireStation fireStation);
    public void updateMedicalRecordsData(MedicalRecord medicalRecord);
}
