package tests.AIMS1;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.Test;
import interfaces.I_AIMS_Home;
import interfaces.I_AIMS_Incident_Details;
import interfaces.I_CP_Common;
import interfaces.I_Global_Common;
import interfaces.I_Global_Settings;
import libraries.CPValueList;
import libraries.Constants;
import libraries.Message;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.ValueList;
import libraries.ValueList.AIMSMessage;
import libraries.ValueList.EvidenceType;
import libraries.ValueList.PopupTitle;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Log;
import libraries.generalFunctions.Mouse;
import libraries.objects.O_Evidence;
import libraries.objects.O_ExportLog;
import libraries.objects.O_Incident;
import libraries.productFunctions.CommonMethods;
import libraries.productFunctions.F_AIMS;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_AIMSVerification;
import libraries.verificationFunctions.F_CPVerification;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;

public class Homepage extends DefaultAnnotations {

	@Test(groups = { TestSuite.Smoke })
	public void Verify_Incident_Coordinates_Value() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		F_AIMS.clickAddIncident(Browser);
		F_AIMS.selectMap(Browser);

		F_AIMSVerification.verifyCoordinateFormatOfIncident(Browser, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_Creation_new_Incidents() {

		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		O_Incident new_o = new O_Incident();
		new_o.createIncident(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.CreateIncidentSuccess, Result);

		Date now = new Date();

		F_GeneralVerification.verifyElementVisible(Browser, I_AIMS_Incident_Details.btn_DeclineWwarning,
				PopupTitle.DeclineWarning, Result);

		F_AIMS.declineWarning(Browser);
		F_AIMS.closeIncident(Browser);

		// Check newly created Incident
		O_Incident result_o = new O_Incident();
		result_o.getIncidentData(Browser);
		F_AIMSVerification.verifyIncidentDetails(Browser, result_o, new_o, now, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_Creation_new_Incidents_have_multi_incidentTypes() {

		int noOftype = 5;
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		List<String> types = F_AIMS.createIncidentWithMultiType(Browser, noOftype);

		F_GeneralVerification.verifyElementVisible(Browser, I_AIMS_Incident_Details.btn_DeclineWwarning,
				PopupTitle.DeclineWarning, Result);

		F_AIMS.declineWarning(Browser);
		F_AIMS.closeIncident(Browser);
		// Check newly created Incident
		F_AIMSVerification.verifyMultiIncidentType(Browser, types, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}


	@Test(groups = { TestSuite.Smoke })
	public void Verify_Upload_an_image() {

		String filename = "image 001.jpg";
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.openLatestIncident(Browser);
		F_AIMS.openEvidenceTab(Browser);
		F_AIMS.uploadAEvidence(Browser, filename);
		// F_AIMS.selectLocationForEvidence(Browser);
		F_AIMS.selectRandomCritical(Browser);
		F_AIMS.clickSaveEvidence(Browser);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.UploadEvidenceSuccess, Result);
		F_Navigation.RefreshPage(Browser);
		F_AIMS.openEvidenceTab(Browser);

		// -- Check Observed date default as Last modified date ----
		long l = Functions.getLastModified(filename);
		Date observedOn = Functions.convertLongToDate(l);
		O_Evidence e = new O_Evidence(EvidenceType.jpeg, filename, observedOn);
		F_AIMSVerification.verifyEvidence(e, Browser, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// --- Post condition --
		F_AIMS.deleteEvidence(Browser, filename, "test", Result);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_Export_evidence() {

		String filename = "";
		String destDir = Constants.UpzipFilePath;
		String folderPath = Constants.DownloadFilePath;
		Functions.deleteFiles(destDir);
		Functions.deleteFiles(folderPath);

		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.openLatestIncident(Browser);
		F_AIMS.openEvidenceTab(Browser);

		filename = F_AIMS.uploadIfEmptyAndSelect(Browser, Result);

		F_AIMS.clickExport(Browser);
		System.out.println("Start export file: " + filename);

		// Functions.saveFile(folderPath + "//");
		Date now = new Date();

		Functions.waitForSeconds(4);
		// Verify downloaded file

		String zipFilePath = Functions.getDownloadFile(Constants.DownloadFilePath);
		CommonMethods.unzip(zipFilePath, destDir);
		System.out.println("Export is finished");

		String zipName = Functions.getFilenameFromFilePath(zipFilePath);
		System.out.println(zipName);
		F_AIMSVerification.verifyExportFile(Browser, filename, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_Navigation.RefreshPage(Browser);
		F_AIMS.openEvidenceTab(Browser);
		F_AIMS.viewExportLog(Browser);
		F_AIMSVerification.verifyExportLog(Browser, Constants.GlobalUsername, now, zipName, filename, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_Delete_an_evidence() {

		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.openLatestIncident(Browser);
		F_AIMS.openEvidenceTab(Browser);

		// Upload file if empty
		String filename = F_AIMS.uploadIfEmptyAndSelect(Browser, Result);
		Assert.assertTrue(Result.Result, Result.Message);
		int noOfEvidence = F_AIMS.getNumberOfEvidence(Browser);

		F_AIMS.clickDeleteEvidence(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.DeleteEvidenceSuccess, Result);

		F_Navigation.RefreshPage(Browser);
		F_AIMS.openEvidenceTab(Browser);

		F_AIMSVerification.verifyEvidenceDeleted(Browser, filename, noOfEvidence, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_Upload_a_video_then_capture_an_image() {
		String filename = "VideoFile1.mp4";
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		F_AIMS.openLatestIncident(Browser);
		F_AIMS.openEvidenceTab(Browser);
		F_AIMS.uploadAEvidence(Browser, filename);
		// F_AIMS.selectLocationForEvidence(Browser);
		F_AIMS.selectRandomCritical(Browser);
		F_AIMS.clickSaveEvidence(Browser);

		F_Navigation.RefreshPage(Browser);
		F_AIMS.openEvidenceTab(Browser);

		long l = Functions.getLastModified(filename);
		System.out.println(l);
		Date observedOn = Functions.convertLongToDate(l);
		O_Evidence e = new O_Evidence(EvidenceType.mp4, filename, observedOn);
		F_AIMSVerification.verifyEvidence(e, Browser, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// Open video and capture image
		F_AIMS.viewEvidence(Browser, filename, Result);
		F_AIMS.playAndCaptureVideo(Browser);
		Date now = new Date();

		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.UploadEvidenceSuccess, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_AIMS.openEvidenceTab(Browser);
		DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm");
		O_Evidence o_CapturedImage = new O_Evidence(EvidenceType.png, mDateFormat.format(now).substring(0, 13),
				observedOn);
		F_AIMSVerification.verifyEvidence(o_CapturedImage, Browser, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// -----After Test
		F_AIMS.deleteEvidence(Browser, filename, "test", Result);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_Export_All_Evidences_more_than_6_evidences() {

		List<String> uploadList = new ArrayList<String>();
		String destDir = Constants.UpzipFilePath;
		String folderPath = Constants.DownloadFilePath;
		Functions.deleteFiles(destDir);
		Functions.deleteFiles(folderPath);
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.openLatestIncident(Browser);
		F_AIMS.openEvidenceTab(Browser);

		// precondition -- up till reach 6 evidences
		int r = F_AIMS.evidenceCount(Browser);
		if (r <= 6) {
			uploadList = F_AIMS.uploadRandomEvidence(Browser, 6 - r);
		}

		List<String> evidenceList = F_AIMS.getEvidenceList(Browser);
		F_AIMS.selectAllEvidence(Browser);
		F_AIMS.clickExport(Browser);
		System.out.println("Start export All files");
		Date now = new Date();
		Functions.waitForSeconds(8);

		System.out.println("Start Unzip files");
		String zipFilePath = Functions.getDownloadFile(Constants.DownloadFilePath);
		CommonMethods.unzip(zipFilePath, destDir);
		

		String zipName = Functions.getFilenameFromFilePath(zipFilePath);
		System.out.println(zipName);
		F_AIMSVerification.verifyExportFileList(Browser, uploadList, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_Navigation.RefreshPage(Browser);
		F_AIMS.openEvidenceTab(Browser);
		F_AIMS.viewExportLog(Browser);
		F_AIMSVerification.verifyExportLog(Browser, Constants.GlobalUsername, now, zipName, evidenceList, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_Mandatary_fields() {

		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.clickAddIncident(Browser);
		Browser.enter(I_AIMS_Incident_Details.txt_callPhone, Functions.randomNumberString(8));
		F_AIMS.saveIncident(Browser);
		F_AIMSVerification.verifyMandataryFieldsError(Browser, "Incident Location",
				I_AIMS_Incident_Details.div_incidentCoorError, ValueList.AIMSWarning.IncidentCoordinateWarnning,
				Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_Update_description_and_discarding_changes() {
		String originalValue = "";
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.openLatestIncident(Browser);

		// --update description field ---
		originalValue = Functions.getText(Browser, I_AIMS_Incident_Details.txt_desc);
		F_AIMS.updateDescription(Browser);
		Mouse.FreeMouse(Browser);

		F_AIMS.closeCurrentTab(Browser);
		Browser.click(I_AIMS_Incident_Details.btn_DiscardChanges);

		// Verify value after discarding
		F_Navigation.RefreshPage(Browser);
		F_AIMS.openLatestIncident(Browser);
		String actualResult = Functions.getText(Browser, I_AIMS_Incident_Details.txt_desc);
		F_GeneralVerification.verifyElementContent(Browser, "Description Value", actualResult, originalValue, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_Update_a_custom_fields_and_discarding_changes() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.openLatestIncident(Browser);
		F_AIMS.scrollToEndOfDetailsPanel(Browser);

		// Get original value of text custom field
		WebElement input = Browser.Driver.findElement(I_AIMS_Incident_Details.txt_customFields);
		String originalValue = input.getText();
		// update to new value
		String randomText = "";
		if (input.getAttribute("type").contains("number")) {
			randomText = Functions.randomNumberString(10);
		} else
			randomText = Functions.randomText(15);

		input.clear();
		input.sendKeys(randomText);

		// Close Incident
		F_AIMS.closeCurrentTab(Browser);
		Browser.click(I_AIMS_Incident_Details.btn_DiscardChanges);

		// Verify value after discarding
		F_Navigation.RefreshPage(Browser);
		F_AIMS.openLatestIncident(Browser);
		String actualResult = Browser.Driver.findElement(I_AIMS_Incident_Details.txt_customFields).getAttribute("value");
		F_GeneralVerification.verifyElementContent(Browser, "CAD custom field value", actualResult, originalValue,
				Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_Update_a_custom_fields_and_saving_chages() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.openLatestIncident(Browser);
		F_AIMS.scrollToEndOfDetailsPanel(Browser);

		// Get original value of text custom field
		WebElement input = Browser.Driver.findElement(I_AIMS_Incident_Details.txt_customFields);

		// update to new value
		String randomText = "";
		if (input.getAttribute("type").contains("number")) {
			randomText = Functions.randomNumberString(10);
		} else
			randomText = Functions.randomText(15);

		input.clear();
		input.sendKeys(randomText);

		// Close Incident
		F_AIMS.closeCurrentTab(Browser);
		Browser.click(I_AIMS_Incident_Details.btn_SaveChanges);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.UpdateIncidentSuccess, Result);

		// Verify value after Saving changes
		F_AIMS.openLatestIncident(Browser);
		String actualResult = Browser.Driver.findElement(I_AIMS_Incident_Details.txt_customFields)
				.getAttribute("value");
		F_GeneralVerification.verifyElementContent(Browser, "CAD custom field value", actualResult, randomText, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_LookUp_in_Evidence_dialog() {
		String filename = "image 002.jpg";
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		F_AIMS.openLatestIncident(Browser);
		F_AIMS.openEvidenceTab(Browser);
		F_AIMS.uploadAEvidence(Browser, filename);

		F_GeneralVerification.verifyElementNotEnabled(Browser, "Look Up button",
				I_AIMS_Incident_Details.Evidence.btn_lookup, Result);
		F_AIMS.searchAndSelectRandomAddress(Browser, Result);
		F_AIMSVerification.verifyEvidenceMetadataFilled(Browser, Result);

		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Save);
		Browser.waitForElementNotVisible(I_AIMS_Incident_Details.Evidence.frm_AddEvidence);

		Functions.waitForSeconds(1);
		long l = Functions.getLastModified(filename);
		Date observedOn = Functions.convertLongToDate(l);
		O_Evidence e = new O_Evidence(EvidenceType.jpeg, filename, observedOn);
		F_AIMSVerification.verifyEvidence(e, Browser, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_LookUp_in_Incident_Details() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		F_AIMS.openLatestIncident(Browser);
		Browser.enter(I_AIMS_Incident_Details.txt_callAddress, ValueList.MapSearchKeyword);
		Browser.click(I_AIMS_Incident_Details.btn_callLookup);
		Browser.waitForElementVisible(I_AIMS_Incident_Details.Evidence.dlg_Addresslist);
		F_GeneralVerification.verifyElementExist(Browser, "Address list dialog",
				I_AIMS_Incident_Details.Evidence.dlg_Addresslist, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_GeneralVerification.verifyElementNotEnabled(Browser, "OK button on Address stress dialog",
				I_AIMS_Incident_Details.Evidence.btn_AddressSubmit, Result);
		Browser.selectRandomDropdown(Browser, I_AIMS_Incident_Details.Evidence.ul_Addresslist, "li");
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_AddressSubmit);
		Functions.waitForSeconds(1);
		F_GeneralVerification.verifyElementNotExist(Browser, "Addresses list",
				I_AIMS_Incident_Details.Evidence.dlg_Addresslist, Result);

		F_AIMSVerification.verifyCallLocationFilled(Browser, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// ---verify Incident location--
		Browser.enter(I_AIMS_Incident_Details.txt_incidentAddr, ValueList.MapSearchKeyword);
		Browser.click(I_AIMS_Incident_Details.btn_inLoLookup);
		Browser.waitForElementVisible(I_AIMS_Incident_Details.Evidence.dlg_Addresslist);
		F_GeneralVerification.verifyElementNotEnabled(Browser, "OK button on Address stress dialog",
				I_AIMS_Incident_Details.Evidence.btn_AddressSubmit, Result);

		Browser.selectRandomDropdown(Browser, I_AIMS_Incident_Details.Evidence.ul_Addresslist, "li");
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_AddressSubmit);
		F_AIMSVerification.verifyIncidentLocationFilled(Browser, Result);

		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test(groups = { TestSuite.Smoke })
	public void verify_Create_two_Incident_continuously() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		O_Incident incident1_o = new O_Incident();
		incident1_o.createIncident(Browser);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.CreateIncidentSuccess, Result);

		F_GeneralVerification.verifyElementVisible(Browser, I_AIMS_Incident_Details.btn_DeclineWwarning,
				PopupTitle.DeclineWarning, Result);

		F_AIMS.declineWarning(Browser);
		Assert.assertTrue(Result.Result, Result.Message);

		F_AIMS.closeIncident(Browser);

		O_Incident incident2_o = new O_Incident();
		incident2_o.createIncident(Browser);
		Date now = new Date();

		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.CreateIncidentSuccess, Result);
		F_GeneralVerification.verifyElementVisible(Browser, I_AIMS_Incident_Details.btn_DeclineWwarning,
				PopupTitle.DeclineWarning, Result);

		F_AIMS.closeIncident(Browser);

		// Check newly created Incident
		O_Incident result_o = new O_Incident();
		result_o.getIncidentData(Browser);
		F_AIMSVerification.verifyIncidentDetails(Browser, result_o, incident1_o, now, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

}
