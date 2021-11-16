package com.safetynet.application.test;

import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import com.safetynet.application.repository.IFireStationService;
import com.safetynet.application.repository.IMedicalRecordService;
import com.safetynet.application.repository.IPersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class DataDAOTestIT {

    @Autowired
    private IPersonService IPersonServiceTest;

    @Autowired
    private IFireStationService IFireStationServiceTest;

    @Autowired
    private IMedicalRecordService IMedicalRecordServiceTest;

    @Test
    @Rollback
    public void givenThreeListInAMap_WriteAllData(){
        //GIVEN
        List<Person> personList = IPersonServiceTest.getAllPerson();
        List<FireStation> fireStationList = IFireStationServiceTest.getAllFireStation();
        List<MedicalRecord> medicalRecordList = IMedicalRecordServiceTest.getAllMedicalRecord();

        //THEN
        Assertions.assertNotNull(personList);
        Assertions.assertNotNull(fireStationList);
        Assertions.assertNotNull(medicalRecordList);
    }
}
