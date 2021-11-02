package com.safetynet.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safetynet.application.model.FireStation;
import com.safetynet.application.model.MedicalRecord;
import com.safetynet.application.model.Person;
import com.safetynet.application.repository.IFireStationServiceTest;
import com.safetynet.application.repository.IMedicalRecordServiceTest;
import com.safetynet.application.repository.IPersonServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationITTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private static MockMvc mockMvc;

	@Autowired
	private IPersonServiceTest IPersonServiceTest;

	@Autowired
	private IFireStationServiceTest IFireStationServiceTest;

	@Autowired
	private IMedicalRecordServiceTest IMedicalRecordServiceTest;

	@BeforeEach
	public void setupMockMvc(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		medications.add("pharmacol:5000mg");
		medications.add("terazine:10mg");

		allergies.add("peanut");
	}

	private static ObjectMapper objectMapper;

	// File path for testing
	private final String path = "E:/Users/Francky Malliet/git/SafetyNet Alerts/dataTest.json";

	// Mock file data for testing
	// Mock person data
	private final String firstName = "John";
	private final String lastName = "Boyd";
	private final String address = "1509 Culver St";
	private final String city = "Culver";
	private final String zip = "97451";
	private final String phone = "841-874-6501";
	private final String email = "JohnsonSmith@email.com";

	// Mock fireStation data
	private final String fireStationNumber = "2";
	private final String fireStationAddress = "25 12th St";

	// Mock medicalRecord data
	private final String medicalFirstName = "Joe";
	private final String medicalLastName = "Williams";
	private final String birthDate = "03/10/1995";
	private List<String> medications = new ArrayList<>();
	private List<String> allergies = new ArrayList<>();

	//FIRESTATION STATION NUMBER
	@Test
	public void  givenAFireStationNumber_ReturnAListOfPeopleCoveredByThisFireStation() throws Exception {
		mockMvc.perform(get("/fireStationTest")
						.param("stationNumber", fireStationNumber))
				.andDo(print()).andExpect(status().isOk());
	}

	//CHILD ALERT
	@Test
	public void givenAnAddress_ReturnTwoList_OneWithAllChildrenAndTheSecondWithAdultsLivingInThisAddress() throws Exception {
		mockMvc.perform(get("/childAlertTest")
						.param("address", address))
				.andDo(print()).andExpect(status().isOk());
	}

	//PHONEALERT
	@Test
	public void givenAFireStationNumber_ReturnAllPhoneFromTheArea() throws Exception {
		mockMvc.perform(get("/phoneAlertTest")
						.param("stationNumber", fireStationNumber))
				.andDo(print()).andExpect(status().isOk());
	}

	//FIRE
	@Test
	public void givenAnAddress_ReturnPeopleInformationAndFireStationNumber() throws Exception {
		mockMvc.perform(get("/fireTest")
						.param("address", address))
				.andDo(print()).andExpect(status().isOk());
	}

	//FLOOD/STATIONS
	@Test
	public void givenAFireStation_ReturnAllAddressesDeservedByAFireStation() throws Exception {
		mockMvc.perform(get("/flood/stationsTest")
						.param("stationNumber", fireStationNumber))
				.andDo(print()).andExpect(status().isOk());
	}

	//PERSONINFO
	@Test
	public void givenANameAndALastName_ReturnAllPersonnalInformation() throws Exception {
		mockMvc.perform(get("/personInfoTest")
				.param("firstName", firstName)
				.param("lastName", lastName))
				.andDo(print()).andExpect(status().isOk());
	}

	//COMMUNITY EMAIL
	@Test
	public void givenACity_ReturnAllEmailAddressOfEveryPersonInThisCity() throws Exception {
		mockMvc.perform(get("/communityEmailTest")
				.param("city", city))
				.andDo(print()).andExpect(status().isOk());
	}

	//ENDPOINT PERSON
	@Test
	public void givenAPersonInformation_AddANewPersonInTheDataFiles() throws Exception {
		//GIVEN
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setAddress(address);
		person.setCity(city);
		person.setZip(zip);
		person.setPhone(phone);
		person.setEmail(email);

		//WHEN
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
		String jsonRequest = objectWriter.writeValueAsString(person);

		//THEN
		mockMvc.perform(post("/personTest")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isOk());
	}

	@Test
	public void givenAPersonNameAndLastName_UpdateHisPersonnalInformation() throws Exception {
		//GIVEN
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setAddress(address);
		person.setCity(city);
		person.setZip(zip);
		person.setPhone(phone);
		person.setEmail(email);

		//WHEN
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
		String jsonRequest = objectWriter.writeValueAsString(person);

		//THEN
		mockMvc.perform(put("/personTest")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isOk());
	}

	@Test
	public void givenAPersonNameAndLastName_DeleteThisPersonInformation() throws Exception {
		mockMvc.perform(delete("/personTest")
						.param("firstName", firstName)
						.param("lastName", lastName))
				.andDo(print()).andExpect(status().isOk());
	}





	//ENDPOINT MEDICAL RECORD
	@Test
	public void givenAMedicalRecordInformation_AddANewMedicalRecord() throws Exception {
		//GIVEN
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName(medicalFirstName);
		medicalRecord.setLastName(medicalLastName);
		medicalRecord.setBirthdate(birthDate);
		medicalRecord.setMedications(medications);
		medicalRecord.setAllergies(allergies);

		//WHEN
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
		String jsonRequest = objectWriter.writeValueAsString(medicalRecord);

		//THEN
		mockMvc.perform(post("/personTest")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isOk());
	}

	@Test
	public void givenANameAndLastName_UpdateTheMedicalRecordOfThisPerson() throws Exception {
		//GIVEN
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName(medicalFirstName);
		medicalRecord.setLastName(medicalLastName);
		medicalRecord.setBirthdate(birthDate);
		medicalRecord.setMedications(medications);
		medicalRecord.setAllergies(allergies);

		//WHEN
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
		String jsonRequest = objectWriter.writeValueAsString(medicalRecord);

		//THEN
		mockMvc.perform(put("/personTest")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isOk());
	}

	@Test
	public void givenANameAndLastName_DeleteTheMedicalRecordOfThisPerson() throws Exception {
		mockMvc.perform(delete("/medicalRecordTest")
						.param("firstName", firstName)
						.param("lastName", lastName))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
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
