package com.safetynet.application.repository;

import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JsonFileCurrentDataDAO {
    List<Person> JsonFileWriterPerson();
    List<FireStation> JsonFileWriterFireStation();
    List<MedicalRecord> JsonFileWriterMedicalRecord();
    List JsonFileWriterAllData(List<Person> personList, List<FireStation> fireStationList, List<MedicalRecord> medicalRecordsList);
}
