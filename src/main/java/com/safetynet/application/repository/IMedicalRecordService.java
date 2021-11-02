package com.safetynet.application.repository;

import com.safetynet.application.model.MedicalRecord;

import java.util.List;

public interface IMedicalRecordService {

    public MedicalRecord getMedicalRecordDataWithFirstNameAndLastName(String firstName, String lastName);
    public void writeNewMedicalRecordsData(MedicalRecord medicalRecord);
    public void updateMedicalRecordsData(MedicalRecord medicalRecord);
    public void deleteMedicalRecordData(String firstName, String lastName);
    public List<MedicalRecord> getAllMedicalRecord();

}
