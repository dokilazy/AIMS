package tests.Sandbox;

import java.io.File;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.google.common.base.Function;
import com.google.common.base.Verify;
import interfaces.I_AIMS_Home;
import interfaces.I_AIMS_Incident_Details;
import interfaces.I_Global_Common;
import interfaces.I_Global_Settings;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.ValueList;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Log;
import libraries.productFunctions.F_AIMS;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;

public class AIMS_test extends DefaultAnnotations {

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Create_Incidents() {

		int n = 10;
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		for (int i = 0; i < n; i++) {
			F_AIMS.clickAddIncident(Browser);
			F_AIMS.fillBasicInfo(Browser);
			F_AIMS.saveIncident(Browser);
			F_AIMS.declineWarning(Browser);
			F_AIMS.closeIncident(Browser);
			Log.info("Incident [" + i + "] has been added successfully");
			//F_Navigation.RefreshPage(Browser);
		}

		Assert.assertTrue(Result.Result, Result.Message);
	}

	// @Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Add_Custom_Fields() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		// Check Custom field missing
		// Check DataType if correct, if wrong update again

		// Add more
	}

	 @Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Add_Indident_Types() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		By aims_7_link = By.xpath("//a[@title='AIMS Angular 7']");
		Browser.click(aims_7_link);
		Browser.switchToWindowHasTitle("ASA - Alarm and Incident Management System");
		Functions.waitForSeconds(4);
		F_AIMS.importIncidentType(Browser);
	}
	 
	

	// @Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Update_Evidences() {

		String IncidentName = "R-180928-0024";

		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.openIncident(Browser, IncidentName);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);

		// Functions.SelectRandomRow (Browser, I_AIMS_Home.tbl_IncidentTable);
		// F_AIMS.UploadAEvidence(Browser, "dallmeier_camera.jpg");
		F_AIMS.uploadRandomEvidence(Browser, 0);
		// F_AIMS.InputEvidentMetadata(Browser);
	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Update_Folder() {

		String IncidentName = "R-181024-0005";
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.openIncident(Browser, IncidentName);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);
		F_AIMS.uploadRandomEvidence(Browser, 0);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	/**
	 * Create multi incidents with evidences
	 */
	// @Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void AddIncidentwithEvidence() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		int n = 1;
		for (int i = 0; i < n; i++) {
			F_AIMS.clickAddIncident(Browser);
			F_AIMS.fillBasicInfo(Browser);
			F_AIMS.saveIncident(Browser);
			F_AIMS.declineWarning(Browser);
			F_AIMS.closeIncident(Browser);
			F_AIMS.openLatestIncident(Browser);
			Browser.click(I_AIMS_Incident_Details.tab_Evidence);
			F_AIMS.uploadRandomEvidence(Browser, 20);
			F_AIMS.closeIncident(Browser);
			F_Navigation.RefreshPage(Browser);
			Log.info("Incident [" + i + "] has been added successfully");
		}

		Assert.assertTrue(Result.Result, Result.Message);

	}

	

}
