package com.safetynet.application.test;

import com.safetynet.application.repository.IUtilitiesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class UtilitiesServiceUnitTest {

    @Autowired
    private IUtilitiesService IUtilitiesServiceTest;

    @Test
    public void getPersonAgeTest(){
        //GIVEN
        String birthDate = "09/11/2000";

        //WHEN
        String personAge = IUtilitiesServiceTest.getPersonAge(birthDate);

        //THEN
        Assertions.assertEquals("21", personAge);
    }
}
