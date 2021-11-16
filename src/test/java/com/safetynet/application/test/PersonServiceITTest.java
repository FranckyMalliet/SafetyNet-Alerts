package com.safetynet.application.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safetynet.application.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonServiceITTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private static MockMvc mockMvc;

	@BeforeEach
	public void setupMockMvc(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	// Mock person data
	private final String firstName = "Joe";
	private final String lastName = "Williams";
	private final String address = "1509 Culver St";
	private final String city = "Culver";
	private final String zip = "97451";
	private final String phone = "841-874-6501";
	private final String email = "JoeWilliams@email.com";

	// Mock fireStation data
	private final String fireStationNumber = "2";

	//FIRESTATION STATION NUMBER
	@Test
	public void  givenAFireStationNumber_ReturnAListOfPeopleCoveredByThisFireStation() throws Exception {
		mockMvc.perform(get("/fireStation")
						.param("stationNumber", fireStationNumber))
				.andDo(print()).andExpect(status().isOk());
	}

	//CHILD ALERT
	@Test
	public void givenAnAddress_ReturnTwoList_OneWithAllChildrenAndTheSecondWithAdultsLivingInThisAddress() throws Exception {
		mockMvc.perform(get("/childAlert")
						.param("address", address))
				.andDo(print()).andExpect(status().isOk());
	}

	//PHONEALERT
	@Test
	public void givenAFireStationNumber_ReturnAllPhoneFromTheArea() throws Exception {
		mockMvc.perform(get("/phoneAlert")
						.param("stationNumber", fireStationNumber))
				.andDo(print()).andExpect(status().isOk());
	}

	//FIRE
	@Test
	public void givenAnAddress_ReturnPeopleInformationAndFireStationNumber() throws Exception {
		mockMvc.perform(get("/fire")
						.param("address", address))
				.andDo(print()).andExpect(status().isOk());
	}

	//FLOOD/STATIONS
	@Test
	public void givenAFireStation_ReturnAllAddressesDeservedByAFireStation() throws Exception {
		mockMvc.perform(get("/flood/stations")
						.param("stationNumber", fireStationNumber))
				.andDo(print()).andExpect(status().isOk());
	}

	//PERSONINFO
	@Test
	public void givenANameAndALastName_ReturnAllPersonnalInformation() throws Exception {
		mockMvc.perform(get("/personInfo")
				.param("firstName", firstName)
				.param("lastName", lastName))
				.andDo(print()).andExpect(status().isOk());
	}

	//COMMUNITY EMAIL
	@Test
	public void givenACity_ReturnAllEmailAddressOfEveryPersonInThisCity() throws Exception {
		mockMvc.perform(get("/communityEmail")
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
		mockMvc.perform(post("/person")
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
		mockMvc.perform(put("/person")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isOk());
	}

	@Test
	public void givenAPersonNameAndLastName_DeleteThisPersonInformation() throws Exception {
		mockMvc.perform(delete("/person")
						.param("firstName", firstName)
						.param("lastName", lastName))
				.andExpect(status().isOk());
	}
}
