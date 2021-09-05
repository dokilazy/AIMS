package tests.SBP;

//import static io.restassured.RestAssured.given;

//import static org.hamcrest.Matchers.equalTo;

import io.restassured.matcher.RestAssuredMatchers.*;

import java.util.List;

import org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import libraries.Constants;
import libraries.DHLValueList;
import libraries.SeleniumBrowser;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.generalFunctions.Functions;
import libraries.productFunctions.F_API;
import libraries.productFunctions.F_DHLInbox;
import libraries.productFunctions.F_DHLSettings;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;

public class DHL_APITest extends DefaultAnnotations {

	void checkAuthorizationTest() {
		RestAssured.baseURI = "https://sbp-dev.dallmeier.vn";

		RequestSpecification httpRequest = RestAssured.given();

		Response response = httpRequest.request(Method.GET, "/");

		// Expecting unauthorized status 401
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);

		// Passing the Basic Auth username and password
		httpRequest.auth().preemptive().basic("initUser", "initUser");

		// Expecting our Auth works and verifying status code
		Response responseWithAuth = httpRequest.request(Method.GET, "/");
		statusCode = responseWithAuth.getStatusCode();
		Assert.assertEquals(statusCode, 200);

		// Verifying the response body of this API
//        JsonPath jsonValidator = responseWithAuth.jsonPath();
//        Boolean authentication = jsonValidator.get("authenticated");
//        Assert.assertTrue(authentication);
	}

	public void GetWeatherDetails() {
		// Specify the base URL to the RESTful web service
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

		// Get the RequestSpecification of the request that you want to sent
		// to the server. The server is specified by the BaseURI that we have
		// specified in the above step.
		RequestSpecification httpRequest = RestAssured.given();

		// Make a request to the server by specifying the method Type and the method
		// URL.
		// This will return the Response from the server. Store the response in a
		// variable.
		Response response = httpRequest.request(Method.GET, "/Hyderabad");

		// Now let us print the body of the message to see what response
		// we have recieved from the server
		String responseBody = response.getBody().asString();
		int responseCode = response.getStatusCode();
		System.out.println("Response Body is =>  " + responseBody);

		System.out.println(response.getStatusCode());

		Assert.assertEquals(responseCode /* actual value */, 200 /* expected value */, "Correct status code returned");
	}

	// ------------------- START ------------------

	@Test(groups = { TestSuite.API })
	public void CREATE_incident_by_API() {
		int n = 5;
		String incidentCode = "";
//		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
//		F_DHLSettings.setGenerateIncidentCode(Browser, false, false);
		F_API.loginToDHL();
		for (int i = 0; i < n; i++) {
			incidentCode = F_DHLInbox.getRandomCode();
			F_API.createIncident(incidentCode);
			System.out.println(incidentCode);
		}
	}

	@Test(groups = { TestSuite.API })
	public void verify_nonExisting_IncidentNbo() {
		F_API.loginToDHL();
		String incidentNbo = "R-200420-12345";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/aims2/rest/incident/incidentNbo/" + incidentNbo);

		int responseCode = response.getStatusCode();

		System.out.println(response.getStatusCode());
		Assert.assertEquals(responseCode, 404);

		String responseBody = response.getBody().asString();
		String errorMsg = "Entity does not exist";
		String message = response.jsonPath().getString("message");
		System.out.println(message);
		// List<String> jsonResponse = response.jsonPath().getList("$");

		System.out.println("Response Body is =>  " + responseBody);

		Assert.assertEquals(message, errorMsg);
	}

//	@Test(groups = { TestSuite.API })
//	public void verify_IncidentNbo_is_exist() {
//		F_API.loginToDHL();
//		String incidentNbo = "";
//		String expected_aggregatedEventID = "";
//	
//		if( Constants.BaseUrl.contains("sbp-dev")) {
//			incidentNbo = "840398002328603";
//			expected_aggregatedEventID = "1239328";
//		}
//		
//		
//		if( Constants.BaseUrl.contains("bbs")) {
//			incidentNbo = "R-201215-0018";
//			expected_aggregatedEventID = "1184580";
//		}
//		
//		RequestSpecification httpRequest = RestAssured.given();
//		Response response = httpRequest.request(Method.GET, "/aims2/rest/incident/incidentNbo/" + incidentNbo);
//
//		int responseCode = response.getStatusCode();
//
//		Assert.assertEquals(responseCode, 200, "The incident does not exist !");
//
//		String responseBody = response.getBody().asString();
//
//		String actual_aggregatedEventID = response.jsonPath().getString("aggregatedEventID");
//		System.out.println(actual_aggregatedEventID);
//		System.out.println("Response Body is =>  " + responseBody);
//
//		Assert.assertEquals(actual_aggregatedEventID, expected_aggregatedEventID);
//	}

	@Test(groups = { TestSuite.API })
	public void verify_create_new_incident() {

		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.setGenerateIncidentCode(Browser, false, false);

		F_API.loginToDHL();
		String incidentCode = F_DHLInbox.getRandomCode();
		Response response = F_API.createIncident(incidentCode);
//		  RestAssured.given().urlEncodingEnabled(true)
//		  .body(payload)
//          .post("/aims2/rest/incident/remote")
//          .then().assertThat().statusCode(200);

		int responseCode = response.getStatusCode();
		String incidentNbo = response.jsonPath().getJsonObject("content.incidentNbo").toString();
		System.out.println(incidentNbo);
		F_GeneralVerification.verifyValue("Response Code", responseCode, 200, Result);
		F_GeneralVerification.verifyElementValue("Incident Code", incidentNbo, incidentCode, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		RequestSpecification httpRequest = RestAssured.given();
		Response GETresponse = httpRequest.request(Method.GET, "/aims2/rest/incident/incidentNbo/" + incidentNbo);

		Assert.assertEquals(GETresponse.getStatusCode(), 200, "The incident does not exist !");

	}

	@Test(groups = { TestSuite.API })
	public void verify_create_new_incident_allow_overwrite() {
		// ---pre-setup: Allow Overwrite on UI ---
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.setGenerateIncidentCode(Browser, false, true);
		Browser.Driver.quit();

		F_API.loginToDHL();
		String incidentCode = F_DHLInbox.getRandomCode();
		Response response = F_API.createIncident(incidentCode);

		System.out.println("Incident: " + incidentCode);

		String incidentNbo = response.jsonPath().getJsonObject("content.incidentNbo").toString();
		System.out.println(incidentNbo);

		int responseCode = response.getStatusCode();

		F_GeneralVerification.verifyValue("Response Code", responseCode, 200, Result);
		F_GeneralVerification.verifyElementValue("Incident Code", incidentNbo, incidentCode, Result);

		Response response2 = F_API.createIncident(incidentCode);
		String responseBody = response2.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);

		int responseCode2 = response2.getStatusCode();
		F_GeneralVerification.verifyValue("Response Code", responseCode2, 200, Result);

		String incidentNbo2 = response2.jsonPath().getJsonObject("content.incidentNbo").toString();
		System.out.println("Incident: " + incidentNbo2);

		F_GeneralVerification.verifyElementValue("Incident Code", incidentNbo2, incidentCode, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.API })
	public void verify_create_new_incident_not_allow_overwrite() {
		// ---pre-setup: Allow Overwrite on UI ---
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.setGenerateIncidentCode(Browser, false, false);
		Browser.Driver.quit();

		F_API.loginToDHL();
		String incidentCode = Functions.randomNumberString(15);
		Response response = F_API.createIncident(incidentCode);

		System.out.println(incidentCode);
		String expected_message = "Key (incident_nbo)=(" + incidentCode + ") already exists.";

		Functions.waitForSeconds(1);
		String incidentNbo = response.jsonPath().getJsonObject("content.incidentNbo").toString();

		int responseCode = response.getStatusCode();

		Assert.assertEquals(responseCode, 200);
		Assert.assertEquals(incidentNbo, incidentCode);

		Response response2 = F_API.createIncident2(incidentCode);
		String responseBody = response2.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);

		int responseCode2 = response2.getStatusCode();
		F_GeneralVerification.verifyValue("Response Code", responseCode2, 400, Result);

		String message = response2.jsonPath().getString("message");

		System.out.println(message);
		F_GeneralVerification.verifyElementValue("Response Message", message, expected_message, Result);

		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test(groups = { TestSuite.API })
	public void verify_cancel_incident() {

		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.setGenerateIncidentCode(Browser, false, false);
		Browser.Driver.quit();

		F_API.loginToDHL();
		String incidentCode = F_DHLInbox.getRandomCode();
		Response createResponse = F_API.createIncident(incidentCode);

		System.out.println("Incident: " + incidentCode);

		String state = createResponse.jsonPath().getJsonObject("content.state.name").toString();
		String finalState = createResponse.jsonPath().getJsonObject("content.state.finalState").toString();

		F_GeneralVerification.verifyElementValue("State", state, DHLValueList.firstState, Result);
		F_GeneralVerification.verifyElementValue("Is Final State", finalState, "false", Result);

		Response cancelResponse = F_API.cancelIncident(incidentCode);

		String incidentNbo = cancelResponse.jsonPath().getJsonObject("incidentNbo").toString();

		F_GeneralVerification.verifyValue("Response Code", cancelResponse.getStatusCode(), 200, Result);
		F_GeneralVerification.verifyElementValue("Incident Code", incidentNbo, incidentCode, Result);

		String stateAfterCancel = cancelResponse.jsonPath().getJsonObject("state.name").toString();
		finalState = cancelResponse.jsonPath().getJsonObject("state.finalState").toString();

		F_GeneralVerification.verifyElementValue("State", stateAfterCancel, DHLValueList.finalState, Result);
		F_GeneralVerification.verifyElementValue("Is Final State", finalState, "true", Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.API })
	public void verify_cancel_incident_which_not_in_first_state() {

		F_API.loginToDHL();

		String incidentCode = DHLValueList.incidentNbo_notFirstState;

		Response cancelResponse = F_API.cancelIncident(incidentCode);

		String responseBody = cancelResponse.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);

		F_GeneralVerification.verifyValue("Response Code", cancelResponse.getStatusCode(), 400, Result);
		String responseMsg = cancelResponse.jsonPath().getJsonObject("message").toString();
		F_GeneralVerification.verifyElementValue("response Message", responseMsg, "Can not cancel this incident!",
				Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

}
