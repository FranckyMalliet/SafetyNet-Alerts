package com.safetynet.application.repository.model;

import com.safetynet.application.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordDAO {

    public MedicalRecord getMedicalRecordDataWithFirstNameAndLastName(String firstName, String lastName);
    public List<MedicalRecord> getAllMedicalRecord();
}
