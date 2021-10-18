package com.safetynet.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.application.controller.DataController;
import com.safetynet.application.repository.DataReadingDAO;
import com.safetynet.application.repository.DataWritingDAO;
import com.safetynet.application.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DataTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private static MockMvc mockMvc;
	private static ObjectMapper objectMapper;

	private DataController dataController;

	@MockBean
	private static DataWritingDAO dataWritingDAO;
	private static DataReadingDAO dataReadingDAO;

	@BeforeEach
	public void setupMockMvc(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	// File path for testing
	private final String path = "E:/Users/Francky Malliet/git/SafetyNet Alerts/dataTest.json";

	// Mock file data for testing
	private final String firstName = "Johnson";
	private final String lastName = "Smith";
	private final String address = "17 25th St";
	private final String city = "Culver";
	private final String zip = "97451";
	private final String phone = "841-874-6501";
	private final String email = "JohnsonSmith@email.com";

	private final int serverPort = 8080;



	//FIRESTATION STATION NUMBER
	@Test
	public void  givenAFirestationNumber_ReturnAListOfPeopleCoveredByThisFirestation(){
		//GIVEN
		int firestationNumber = 1;

		//WHEN

		//THEN
	}

	//CHILD ALERT
	@Test
	public void givenAnAddress_ReturnTwoList_OneWithAllChildrenAndTheSecondWithAdultsLivingInThisAddress() {
		//GIVEN

		//WHEN

		//THEN
	}

	@Test
	public void givenAnAddress_WhenThereAreNoChildren_ReturnNull(){
		//GIVEN

		//WHEN

		//THEN
	}


	//PHONEALERT
	@Test
	public void givenAFirestationNumber_ReturnAllPhoneFromTheArea(){
		//GIVEN

		//WHEN

		//THEN
	}


	//FIRE
	@Test
	public void givenAnAddress_ReturnPeopleInformationsAndFirestationNumber(){
		//GIVEN

		//WHEN

		//THEN
	}


	//FLOOD/STATIONS
	@Test
	public void givenAFirestation_ReturnAllAddressesDeservedByAFirestation(){
		//GIVEN

		//WHEN

		//THEN
	}

	//PERSONINFO
	@Test
	public void givenANameAndALastName_ReturnAllPersonnalInformations() throws Exception {
		//GIVEN
		String firstName = "John";
		String lastName = "Boyd";

		//WHEN

		//THEN
		mockMvc.perform(get("/personInfo")
				.param("firstName", firstName)
				.param("lastName", lastName))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception{
		this.mockMvc.perform(get("/")).andDo(print()).andExpect((status().isOk())).andExpect(content().string(containsString("Hello, World")));
	}

	//COMMUNITY EMAIL
	@Test
	public void givenACity_ReturnAllEmailAddressOfEveryPersonInThisCity() throws Exception {
		//GIVEN
		String city = "Culver";

		//WHEN

		//THEN
		mockMvc.perform(get("/communityEmail")
				.param("city", city))
				.andDo(print()).andExpect(status().isOk());
	}

	//ENDPOINT PERSON
	/*@Test
	public void givenAPersonInformations_AddANewPersonInTheDataFiles() throws Exception {
		//GIVEN
		//final String baseUrl = "http://localhost:"+serverPort+"/person";
		//URI uri = new URI(baseUrl);

		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setAddress(address);
		person.setCity(city);
		person.setZip(zip);
		person.setPhone(phone);
		person.setEmail(email);

		//WHEN
		//mockMvc.perform(post("/person")).flashAttr("ADDED_OBJECT", person)
		//		.andExpect(status().isOk()).andDo(print());
				//.andExpect(status().isOk());
		//mockMvc.perform(post(uri)).andExpect(status().isOk());

		//mockMvc.perform(post("/person")).contentType("application/json")
		//		.andExpect(status().isOk());
		//dataWritingDAO.writePersonData(person);
		//dataReadingDAO.readPersonData(person.getFirstName(), person.getLastName());

		//THEN

	}*/

	@Test
	public void givenAPersonNameAndLastName_UpdateHisPersonnalInformations(){
		//GIVEN

		//WHEN

		//THEN
	}

	@Test
	public void givenAPersonNameAndLastName_DeleteThisPersonInformations(){
		//GIVEN

		//WHEN

		//THEN
	}


	//ENDPOINT FIRESTATION
	@Test
	public void givenAFirestationInformations_AddANewFirestationOrAddress(){
		//GIVEN

		//WHEN

		//THEN
	}

	@Test
	public void givenAFirestationNumber_UpdateTheFirestationAddress(){
		//GIVEN

		//WHEN

		//THEN
	}

	@Test
	public void givenAFirestationNumber_DeleteTheFirestationOrAddress(){
		//GIVEN

		//WHEN

		//THEN
	}


	//ENDPOINT MEDICAL RECORD
	@Test
	public void givenAMedicalRecordInformations_AddANewMedicalRecord(){
		//GIVEN

		//WHEN

		//THEN
	}

	@Test
	public void givenANameAndLastName_UpdateTheMedicalRecordOfThisPerson(){
		//GIVEN

		//WHEN

		//THEN
	}

	@Test
	public void givenANameAndLastName_DeleteTheMedicalRecordOfThisPerson(){
		//GIVEN

		//WHEN

		//THEN
	}
}
