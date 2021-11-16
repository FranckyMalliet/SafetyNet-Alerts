package com.safetynet.application.repository;

import com.safetynet.application.model.MedicalRecord;

import java.util.List;

public interface IMedicalRecordService {

    public MedicalRecord getMedicalRecordDataWithFirstNameAndLastName(String firstName, String lastName);
    public void writeNewMedicalRecordData(MedicalRecord medicalRecord);
    public void updateMedicalRecordData(MedicalRecord medicalRecord);
    public void deleteMedicalRecordData(String firstName, String lastName);
    public List<MedicalRecord> getAllMedicalRecord();

}
