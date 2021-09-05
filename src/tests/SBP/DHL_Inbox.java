package tests.SBP;

import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import interfaces.I_DHL_Details;
import interfaces.I_DHL_Home;
import libraries.Constants;
import libraries.DHLValueList;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Mouse;
import libraries.objects.DetailField;
import libraries.objects.O_DHLIncident;
import libraries.productFunctions.F_API;
import libraries.productFunctions.F_DHLInbox;
import libraries.productFunctions.F_DHLSettings;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_DHLVerification;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;
import tests.SBP.DHL_APITest;

public class DHL_Inbox extends DefaultAnnotations {

	//@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void create_INCIDENT_TestData() {
		int n = 10;
		for (int i = 0; i < n; i++) {
			Date observedTime = new Date();
			Long timeStamp = Functions.getCurrentTimeinLong(observedTime);
			F_DHLInbox.createIncident(timeStamp, false);
		}
	}

	@Test(groups = { TestSuite.BVT })
	public void verify_add_new_incident_ManualGenerateCode() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.setGenerateIncidentCode(Browser, false, true);
		F_DHLInbox.gotoInboxpage(Browser);
		O_DHLIncident incident = F_API.createIncident(true);
		
//		F_DHLVerification.verifyNewlyCreatedincidentpopup(Browser, Result);  // 
//		F_DHLInbox.declineWarning(Browser);
		F_DHLVerification.verifyIncidentAtInbox(Browser, false, incident, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_DHLInbox.searchByCode(Browser, incident.Code);
		F_DHLVerification.verifyIncidentResultOfSearch(Browser, incident.Code, Result);
		Assert.assertTrue(Result.Result, Result.Message);
		
		F_DHLInbox.openIncident(Browser, incident.Code, Result);
		F_DHLVerification.verifyIncidentAtDetail(Browser, incident, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.BVT }, dependsOnMethods = { "verify_add_new_incident_ManualGenerateCode" })
	public void verify_add_new_incident_AutoGenerateCode() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.setGenerateIncidentCode(Browser, true, true);
		

		// String incidentID = F_DHLInbox.createIncident(timeStamp, true);

		O_DHLIncident incident = F_API.createIncident(true);

		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLVerification.verifyIncidentAtInbox(Browser, true, incident, Result);

		F_DHLInbox.openLatestIncident(Browser);
		F_DHLVerification.verifyIncidentAtDetail(Browser, incident, Result);

		
		// ---- Post condition ----
		F_DHLSettings.setGenerateIncidentCode(Browser, false, true);
		
		Assert.assertTrue(Result.Result, Result.Message);
	}

	public void verify_update_coordinate_for_incident() {
		
		// String incidentID = F_DHLInbox.createIncident(timeStamp, false);

		O_DHLIncident incident = F_API.createIncident(true);
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLInbox.searchByCode(Browser, incident.Code);
		F_DHLVerification.verifyIncidentResultOfSearch(Browser, incident.Code, Result);
		F_DHLInbox.openIncident(Browser, incident.Code, Result);

		String reporter = F_DHLInbox.selectRandomAssignee(Browser);
		F_DHLInbox.selectMap(Browser);
		F_DHLInbox.saveIncident(Browser);
		F_DHLVerification.verifySuccessUpdateMsg(Browser, "Incident", Result);
		F_DHLVerification.verifyCoordinateFormatOfIncident(Browser, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	/*
	 * Update Reporting, Status, Location
	 * 
	 */
	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void verify_update_incident_state_without_comment() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);

		F_DHLInbox.gotoInboxpage(Browser);
		String incidentID = F_DHLInbox.openLatestIncident(Browser);
		F_DHLInbox.enableEditIncident(Browser);

//		String assignee = F_DHLInbox.selectRandomAssignee(Browser);
		String status = F_DHLInbox.selectRandomStatus(Browser);
		F_DHLInbox.saveIncident(Browser);

		F_DHLVerification.verifySuccessUpdateMsg(Browser, "Incident", Result);
		Assert.assertTrue(Result.Result, Result.Message);

		O_DHLIncident expectedDetails = F_DHLInbox.getDetails(Browser);
//		expectedDetails.Assignee = assignee;
		expectedDetails.Status = status;

		F_DHLInbox.closeIncident(Browser);

		F_DHLInbox.openIncident(Browser, incidentID, Result);
		F_DHLVerification.verifyIncidentAtDetail(Browser, expectedDetails, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	/*
	 * Change workflow
	 * 
	 */
	@Test(groups = { TestSuite.BVT })
	public void verify_update_incident_type_of_incident() {
	
		// String incidentID = F_DHLInbox.createIncident(timeStamp, false);
		
		
		O_DHLIncident incident = F_API.createIncident(true);
		String incidentCode = incident.Code;
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLInbox.searchByCode(Browser, incidentCode);
		F_DHLVerification.verifyIncidentResultOfSearch(Browser, incidentCode, Result);
		F_DHLInbox.openIncident(Browser, incidentCode, Result);
		F_DHLInbox.enableEditIncident(Browser);

		String incidentType = F_DHLInbox.selectRandomIncidentType(Browser);
		F_DHLInbox.saveIncident(Browser);
		O_DHLIncident expectedDetails = new O_DHLIncident();
		expectedDetails.Status = DHLValueList.firstState;
		expectedDetails.Type = incidentType;
		expectedDetails.Workflow = DHLValueList.defaultWF;

		F_DHLVerification.verifyConfirmChangeIncidentType(Browser, Result);
		F_DHLInbox.clickConfirmChangeIncidentType(Browser);
		F_DHLVerification.verifySuccessUpdateMsg(Browser, "Incident", Result);
		F_DHLVerification.verifyIncidentAtDetail(Browser, expectedDetails, Result);

		Assert.assertTrue(Result.Result, Result.Message);

	}

	// @Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void verify_update_incident_date() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLInbox.gotoInboxpage(Browser);

		// -- Open a new incident ---
		F_DHLInbox.searchByStatus(Browser, DHLValueList.firstState);
		F_DHLInbox.openLatestIncident(Browser);
		F_DHLInbox.enableEditIncident(Browser);

	}

	@Test(groups = { TestSuite.BVT })
	public void verify_counter_when_creating_new_incidents() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLInbox.gotoInboxpage(Browser);
		int originalincidentNo = F_DHLInbox.getTotalIncidentNo(Browser);
		int originalnewIncidentNo = F_DHLInbox.getTotalNewIncidentNo(Browser);

		// String incidentID = F_DHLInbox.createIncident(timeStamp, false);

		O_DHLIncident incident = F_API.createIncident(true);
		String incidentID = incident.Code;

		F_DHLInbox.gotoInboxpage(Browser);

		int incidentNo = F_DHLInbox.getTotalIncidentNo(Browser);
		int newIncidentNo = F_DHLInbox.getTotalNewIncidentNo(Browser);

		F_DHLVerification.verifyIncidentCounter(Browser, originalincidentNo + 1, originalnewIncidentNo + 1, incidentNo,
				newIncidentNo, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.BVT })
	public void verify_counter_when_updating_incidents() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLInbox.gotoInboxpage(Browser);
		int originalincidentNo = F_DHLInbox.getTotalIncidentNo(Browser);
		int originalnewIncidentNo = F_DHLInbox.getTotalNewIncidentNo(Browser);

		// -- Open a new incident ---
		F_DHLInbox.searchByStatus(Browser, DHLValueList.firstState);
		F_DHLInbox.openLatestIncident(Browser);
		F_DHLInbox.enableEditIncident(Browser);
		F_DHLInbox.changeToAnotherState(Browser);

		F_DHLInbox.saveIncident(Browser);
		F_DHLInbox.closeIncident(Browser);

		F_DHLInbox.gotoInboxpage(Browser);
		int incidentNo = F_DHLInbox.getTotalIncidentNo(Browser);
		int newIncidentNo = F_DHLInbox.getTotalNewIncidentNo(Browser);

		F_DHLVerification.verifyIncidentCounter(Browser, originalincidentNo, originalnewIncidentNo - 1, incidentNo,
				newIncidentNo, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.BVT })
	public void verify_incident_state_history() {
		String initialStatus = DHLValueList.firstState;
		String newstatus2 = "";
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLInbox.gotoInboxpage(Browser);

		O_DHLIncident incident = F_API.createIncident(true);
		String incidentID = incident.Code;
		// String incidentID = F_DHLInbox.createIncident(timeStamp, false);

		F_DHLInbox.searchByCode(Browser, incidentID);
		F_DHLInbox.openIncident(Browser, incidentID, Result);
		F_DHLInbox.enableEditIncident(Browser);
		String newstatus1 = F_DHLInbox.changeToAnotherState(Browser);
		
		F_DHLInbox.saveIncident(Browser);
		F_DHLVerification.verifySuccessUpdateMsg(Browser, "Incident", Result);
		F_DHLVerification.verifyHighlightIncidentStateHistory(Browser, initialStatus, Result);
		F_DHLVerification.verifyHighlightIncidentStateHistory(Browser, newstatus1, Result);

		do {
			newstatus2 = F_DHLInbox.changeToAnotherState(Browser);
		} while (newstatus2 == initialStatus || newstatus2 == newstatus1);
		F_DHLInbox.saveIncident(Browser);
		F_DHLVerification.verifySuccessUpdateMsg(Browser, "Incident", Result);

		F_DHLVerification.verifyHighlightIncidentStateHistory(Browser, initialStatus, Result);
		F_DHLVerification.verifyHighlightIncidentStateHistory(Browser, newstatus1, Result);
		F_DHLVerification.verifyHighlightIncidentStateHistory(Browser, newstatus2, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.BVT })
	public void verify_incident_state_history_reset_after_changing_incident_type() {
		String initialStatus = DHLValueList.firstState;
		String newstatus2 = "";
			
		O_DHLIncident incident = F_API.createIncident(true);
		String incidentID = incident.Code;
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLInbox.gotoInboxpage(Browser);

		F_DHLInbox.searchByCode(Browser, incidentID);
		F_DHLInbox.openIncident(Browser, incidentID, Result);
		F_DHLInbox.enableEditIncident(Browser);
		String newstatus1 = F_DHLInbox.changeToAnotherState(Browser);

		F_DHLInbox.saveIncident(Browser);
		F_DHLVerification.verifyHighlightIncidentStateHistory(Browser, initialStatus, Result);
		F_DHLVerification.verifyHighlightIncidentStateHistory(Browser, newstatus1, Result);

		do {
			newstatus2 = F_DHLInbox.changeToAnotherState(Browser);
		} while (newstatus2.contains(initialStatus) || newstatus2 == newstatus1);

		F_DHLInbox.saveIncident(Browser);

		F_DHLVerification.verifyHighlightIncidentStateHistory(Browser, initialStatus, Result);
		F_DHLVerification.verifyHighlightIncidentStateHistory(Browser, newstatus1, Result);
		F_DHLVerification.verifyHighlightIncidentStateHistory(Browser, newstatus2, Result);

		F_DHLInbox.selectRandomIncidentType(Browser);
		F_DHLInbox.saveIncident(Browser);
		F_DHLInbox.clickConfirmChangeIncidentType(Browser);
		F_DHLVerification.verifySuccessUpdateMsg(Browser, "Incident", Result);

		F_DHLVerification.verifyHighlightIncidentStateHistory(Browser, initialStatus, Result);
		F_DHLVerification.verifyNonHighlightIncidentStateHistory(Browser, newstatus1, Result);
		F_DHLVerification.verifyNonHighlightIncidentStateHistory(Browser, newstatus2, Result);
		Assert.assertTrue(Result.Result, Result.Message);

	}

	// ----------------- SEARCH -------------
	@Test(groups = { TestSuite.BVT })
	public void verify_search_incident_by_code() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);

		// String incidentID = F_DHLInbox.createIncident(timeStamp, false);

		O_DHLIncident incident = F_API.createIncident(true);
		String incidentID = incident.Code;
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLInbox.searchByCode(Browser, incidentID);
		F_DHLVerification.verifyIncidentResultOfSearch(Browser, incidentID, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	 @Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void verify_search_incident_by_status() {
	}

	// @Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void verify_search_incident_by_date() {
	}

	@Test(groups = { TestSuite.BVT }, retryAnalyzer = tests.RetryAnalyzer.class)
	public void verify_search_incident_by_custom_fields() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.CustomFields(Browser);
		String UKlabel = Functions.randomText(6);
		F_DHLSettings.addNewCustomField(Browser, UKlabel, DHLValueList.dataType.string, UKlabel, "", "");

		F_DHLInbox.gotoInboxpage(Browser);
		String incidentID = F_DHLInbox.openLatestIncident(Browser);
		String value = Functions.randomText(15);

		F_DHLInbox.enableEditIncident(Browser);
		F_DHLInbox.fillCustomField(Browser, UKlabel, value);
		Mouse.FreeMouse(Browser);
		F_DHLInbox.saveIncident(Browser);
		F_DHLVerification.verifySuccessUpdateMsg(Browser, "Incident", Result);
		F_DHLInbox.closeIncident(Browser);
		F_DHLSettings.goToPage.Setting(Browser);
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLInbox.searchByCustomField(Browser, UKlabel, value);
		F_DHLVerification.verifyIncidentResultOfSearch(Browser, incidentID, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// ------- Clear data ---
		F_DHLInbox.openIncident(Browser, incidentID, Result);
		F_DHLInbox.clearCustomField(Browser, UKlabel);
		F_DHLInbox.saveIncident(Browser);
		F_DHLSettings.goToPage.CustomFields(Browser);
		F_DHLSettings.deleteCustomField(Browser, UKlabel);
	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void verify_search_incident_by_last_event_location_and_last_station() {

		Date now = new Date();
		Long timeStamp = Functions.getCurrentTimeinLong(now);
		String lastEventLocation = Functions.randomText(10);
		int lastStation = Functions.randomInterger(10, 9999);
	//	String incidentID = F_DHLInbox.createIncidentWithRandomAddInfo(timeStamp, lastEventLocation, false);
		String incidentID = F_API.createIncident(timeStamp, false, lastEventLocation, lastStation);
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLInbox.searchByLastEventLocation(Browser, lastEventLocation);

		F_DHLVerification.verifyIncidentResultOfSearch(Browser, incidentID, Result);
		
		F_Navigation.RefreshPage(Browser);
		F_DHLInbox.searchByLastStation(Browser, String.valueOf(lastStation));
		F_DHLVerification.verifyIncidentResultOfSearch(Browser, incidentID, Result);
		
		Assert.assertTrue(Result.Result, Result.Message);
	}
	
	

	@Test(groups = { TestSuite.BVT })
	public void verify_fields_disable_untill_fill_assignee() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);

		
		O_DHLIncident incident = F_API.createIncident(true);
		String incidentID = incident.Code;
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLInbox.searchByCode(Browser, incidentID);
		F_DHLVerification.verifyIncidentResultOfSearch(Browser, incidentID, Result);

		F_DHLInbox.openIncident(Browser, incidentID, Result);

		F_GeneralVerification.verifyElementEnabled(Browser, "Assignee", I_DHL_Details.txt_Assignee, Result);
		F_GeneralVerification.verifyElementNotEnabled(Browser, "Coworker", I_DHL_Details.txt_Coworker, Result);
		F_GeneralVerification.verifyElementNotEnabled(Browser, "Date field", I_DHL_Details.txt_DateTime, Result);
		F_GeneralVerification.verifyElementNotEnabled(Browser, "Incident type", I_DHL_Details.txt_IncidentType, Result);
		F_GeneralVerification.verifyElementNotEnabled(Browser, "State", I_DHL_Details.txt_Status, Result);
	//	F_GeneralVerification.verifyElementNotEnabled(Browser, "Customfiled", I_DHL_Details.txt_customFields, Result);

		
		Assert.assertTrue(Result.Result, Result.Message);
		
		F_DHLInbox.enableEditIncident(Browser);
		F_GeneralVerification.verifyElementEnabled(Browser, "Assignee", I_DHL_Details.txt_Assignee, Result);
		F_GeneralVerification.verifyElementEnabled(Browser, "Coworker", I_DHL_Details.txt_Coworker, Result);
		F_GeneralVerification.verifyElementEnabled(Browser, "Date field", I_DHL_Details.txt_DateTime, Result);
		F_GeneralVerification.verifyElementEnabled(Browser, "Incident type", I_DHL_Details.txt_IncidentType, Result);
		F_GeneralVerification.verifyElementEnabled(Browser, "State", I_DHL_Details.txt_Status, Result);
	//	F_GeneralVerification.verifyElementEnabled(Browser, "Customfiled", I_DHL_Details.txt_customFields, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.BVT })
	public void verify_mark_delete_for_an_incident() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);

		// select ramdom a row
		String incidentCode = F_DHLInbox.selectRandomIncident(Browser, true);
		System.out.print(incidentCode + " is selected for deleting." + "\t\n");

		// click on Delete
		F_DHLInbox.clickDeleteIncident_intoolBar(Browser);
		F_DHLVerification.verifyConfirmDeleteIncident(Browser, Result);
		F_DHLInbox.clickOkDeleletConfirm(Browser);

		F_DHLVerification.verifySuccessDeleteMsg(Browser, "Incident", Result);

		// check delete mark
		F_DHLVerification.verifyIncidentInDeletingState(Browser, incidentCode, Result);

		// check Delete button
		F_DHLInbox.selectIncident(Browser, incidentCode);

		F_GeneralVerification.verifyElementExist(Browser, "Undelete button at toolbar",
				I_DHL_Home.btn_undeleteIncidentOnToolbar, Result);
		F_GeneralVerification.verifyElementExist(Browser, "Undelete button at detail",
				I_DHL_Home.btn_undeleteIncidentIndetails, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// ---check Undelete --
		F_DHLInbox.clickUndeleteIncident_intoolBar(Browser);
		F_DHLInbox.clickOkDeleletConfirm(Browser);

		F_DHLVerification.verifySuccessUndeleteMsg(Browser, "Incident", Result);
		F_DHLVerification.verifyIncidentNotInDeletingState(Browser, incidentCode, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// Todo: check DB
	}

	@Test
	public void verify_assigneeIsMe_and_coworker() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);

		// -- create a new incident--
		O_DHLIncident incident = F_API.createIncident(true);
		String incidentCode = incident.Code;

		// -- search and open it
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLInbox.searchByCode(Browser, incidentCode);
		F_DHLVerification.verifyIncidentResultOfSearch(Browser, incidentCode, Result);

		F_DHLInbox.openIncident(Browser, incidentCode, Result);
		F_DHLInbox.enableEditIncident(Browser); // select me as assignee

		// --- add random coworker ---
		List<String> cokr = F_DHLInbox.selectRandomMultiCoworkers(Browser);

		F_DHLInbox.saveIncident(Browser);
		F_DHLVerification.verifySuccessUpdateMsg(Browser, "Incident", Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// --- verify at Home page --
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLInbox.searchByCode(Browser, incidentCode);
		F_DHLInbox.selectIncident(Browser, incidentCode);

		F_DHLVerification.verifyAssigneeAtInboxDetails(Browser, "me", Result);

		F_DHLVerification.verifyCoworkerAtInboxDetails(Browser, cokr, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}
	
	
	@Test
	public void verify_assignee_and_coworkerIsMe() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);

		// -- create a new incident--
		O_DHLIncident incident = F_API.createIncident(true);
		String incidentCode = incident.Code;

		// -- search and open it
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLInbox.searchByCode(Browser, incidentCode);
		F_DHLVerification.verifyIncidentResultOfSearch(Browser, incidentCode, Result);
		Assert.assertTrue(Result.Result, Result.Message);
		

		F_DHLInbox.openIncident(Browser, incidentCode, Result);
		F_DHLInbox.enableEditIncident(Browser); // select me as assignee

		
		// --- add random assign ---
		String assignee = F_DHLInbox.selectRandomAssigneeNotMe(Browser);
		
		
		// --- add random coworker ---
		F_DHLInbox.selectCoworkerAsMe(Browser);
		List<String> cokr = F_DHLInbox.selectRandomMultiCoworkers(Browser);
		cokr.add("me");
		
		F_DHLInbox.saveIncident(Browser);
		F_DHLVerification.verifySuccessUpdateMsg(Browser, "Incident", Result);

		//--- check assignee field not editable
		F_GeneralVerification.verifyElementNotEnabled(Browser, "Assignee", I_DHL_Details.txt_Assignee, Result);
		F_GeneralVerification.verifyElementNotEnabled(Browser, "Coworker", I_DHL_Details.txt_Coworker, Result);
	
		F_GeneralVerification.verifyElementEnabled(Browser, "Date field", I_DHL_Details.txt_DateTime, Result);
		F_GeneralVerification.verifyElementEnabled(Browser, "Incident type", I_DHL_Details.txt_IncidentType, Result);
		F_GeneralVerification.verifyElementEnabled(Browser, "State", I_DHL_Details.txt_Status, Result);
	//	F_GeneralVerification.verifyElementEnabled(Browser, "Customfiled", I_DHL_Details.txt_customFields, Result);

		// --- verify at Home page --
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLInbox.searchByCode(Browser, incidentCode);
		F_DHLInbox.selectIncident(Browser, incidentCode);

		F_DHLVerification.verifyAssigneeAtInboxDetails(Browser, assignee, Result);

		F_DHLVerification.verifyCoworkerAtInboxDetails(Browser, cokr, Result);
		
		Assert.assertTrue(Result.Result, Result.Message);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
