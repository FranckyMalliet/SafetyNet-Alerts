package com.safetynet.application.repository;

import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DataWritingDAO {

    public void writeNewPersonData(Person person);
    public void writeNewFireStationData(FireStation fireStation);
    public void writeNewMedicalRecordsData(MedicalRecord medicalRecord);
    public Map<String, List> writeAllData(List<Person> personList, List<FireStation> fireStationList, List<MedicalRecord> medicalRecordsList);

}
