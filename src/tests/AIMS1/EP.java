package tests.AIMS1;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import interfaces.I_AIMS_Incident_Details;
import interfaces.I_EP;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.TestConfig.TestSuite;
import libraries.ValueList.EvidenceType;
import libraries.ValueList.UploadType;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Mouse;
import libraries.objects.O_Evidence;
import libraries.objects.O_Incident;
import libraries.productFunctions.F_AIMS;
import libraries.productFunctions.F_EP;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_AIMSVerification;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;


public class EP extends DefaultAnnotations {

	@Test(priority = 0, groups = { TestSuite.BVT })
	public void Verify_Search_Evidence() {

		// --- Create a new Incident with uploaded evidences --
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		O_Incident newIncident = new O_Incident();
		newIncident.createIncidentWithoutCustomfields(Browser);

		Browser.clickJavascript(I_AIMS_Incident_Details.btn_DeclineWwarning);
		F_AIMS.closeIncident(Browser);
		List<String> evidences = newIncident.uploadEvidence(Browser, UploadType.image, 2);
		F_AIMS.closeIncident(Browser);

		Browser.openNewWebDriver();
		// F_EP.OpenEP(Browser, Constants.GlobalUsername, Constants.Password);
		F_EP.OpenEPbyURL(Browser, Constants.GlobalUsername, Constants.Password);

		String AIMS_title = "ASA Evidence Presenter";
		Browser.switchToWindowHasTitle(AIMS_title);

		F_EP.openSearch(Browser);
		F_EP.fillSearch(newIncident.RefNo);

		Functions.waitForSeconds(3);
		F_EP.VerifyIncidentList(Browser,   Result);
		Assert.assertTrue(Result.Result, Result.Message);

		Browser.click(I_EP.div_Incidents);
		F_EP.VerifyEvidenceList(Browser, Result, evidences);

		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test(priority = 1, dependsOnMethods = { "Verify_export_all_evidences_more_than_6_evidences" }, groups = { TestSuite.BVT })
	public void Verify_Auto_Export() {

		// --- Create a new Incident with uploaded evidences --
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		O_Incident newIncident = new O_Incident();
		newIncident.createIncidentWithoutCustomfields(Browser);

		Browser.clickJavascript(I_AIMS_Incident_Details.btn_DeclineWwarning);
		F_AIMS.closeIncident(Browser);
		List<String> evidences = newIncident.uploadEvidence(Browser, UploadType.image, 2);
		F_AIMS.closeIncident(Browser);

		WebDriver originalDriver = Browser.Driver;  
		Browser.openNewWebDriver();
		// F_EP.OpenEP(Browser, Constants.GlobalUsername, Constants.Password);
		F_EP.OpenEPbyURL(Browser, Constants.GlobalUsername, Constants.Password);

		String EP_title = "ASA Evidence Presenter";
		Browser.switchToWindowHasTitle(EP_title);
		F_EP.openSearch(Browser);
		//F_EP.fillSearch("R-181127-0002");
		
		F_EP.fillSearch(newIncident.RefNo);

		Functions.waitForSeconds(3);
		F_EP.VerifyIncidentList(Browser, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		Browser.click(I_EP.div_Incidents);
		Functions.waitForSeconds(3);
		
		Assert.assertTrue(Result.Result, Result.Message);
		
		F_GeneralVerification.verifyElementEnabled(Browser, "Auto Export button", I_EP.btn_AutoExport, Result);
		
		//----Create new project ----
		String projectName = "Automation test";
		String desc = "Verify_Auto_Export";
		
		F_EP.createNewProject(Browser, projectName, desc);
		Functions.waitForSeconds(2);
		Browser.click(I_EP.btn_AutoExport);
		
		//F_GeneralVerification.verifyElementVisible(Browser, I_EP.div_Exportfeedback, "Export Progress dialog", Result);
		Functions.waitForSeconds(25);
		F_GeneralVerification.verifyElementEnabled(Browser, "Export Complete", I_EP.lbl_ExportComplete, Result);
		F_GeneralVerification.verifyElementVisible(Browser, I_EP.lbl_ExportComplete, "Export Progress Complete", Result);
		Assert.assertTrue(Result.Result, Result.Message);
		
		//--- Verify Export File in AIMS ----
		Browser.Driver.close();
		Browser.Driver = originalDriver;
		Browser.SwitchToMainWindow();
		
		F_AIMS.openIncident(Browser, newIncident.RefNo);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);
		
		String filename = "[EP_BURNDOWN] " + desc;
		O_Evidence evidence = new O_Evidence(EvidenceType.mp4, filename, null);
		F_AIMSVerification.verifyEvidence(evidence, Browser, Result);
		
		Assert.assertTrue(Result.Result, Result.Message);
		
		//---- Verify mp4 file -----
		String data = "Imgs/Evidence.png";
		F_AIMS.viewEvidence(Browser, filename, Result);

		// Browser.click(I_AIMS_Incident_Details.Evidence.btn_PlayPause);
		Functions.waitForSeconds(3);
		Mouse.MoveMouseToElement(Browser, I_AIMS_Incident_Details.Evidence.frm_Video);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_PlayPause);
		
		F_GeneralVerification.verifyImageOnpage(data, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

}
