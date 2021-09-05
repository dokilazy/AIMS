package tests.SBP;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.DHLValueList.Form;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.generalFunctions.Functions;
import libraries.productFunctions.F_Common;
import libraries.productFunctions.F_DHLInbox;
import libraries.productFunctions.F_DHLSettings;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_DHLVerification;
import tests.DefaultAnnotations;

public class DHL_Event extends DefaultAnnotations {

	// ------------- EVIDENCE -------

	// @Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void verify_upload_evidence() {

	}

	// @Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void verify_export_evidence() {

	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void verify_create_form() {
		String templateName = Form.templateName;
		String file_path =   Functions.getCurrDirectory() + "\\" + Form.validForm;
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.FormTemplate(Browser);

		if (F_DHLSettings.checkTemplateExist(Browser, templateName) == true) {
			F_DHLSettings.deleteTemplate(Browser, templateName);
			
			Functions.waitForSeconds(1);
			F_DHLInbox.gotoInboxpage(Browser);
			F_DHLSettings.goToPage.FormTemplate(Browser);
		}

		// --- upload a valid teamplate at Settings
		F_DHLSettings.clickAddNew(Browser);

		List<String> placeHoders = new ArrayList<String>();
		placeHoders.add("assignee");
		placeHoders.add("dateTime");
		placeHoders.add("shipmentId");

		Functions.uploadfile(file_path, templateName);
		Browser.waitForElementVisible(By.xpath("//app-form-template-create"));
		
		// --- Check the dialog form displayed
		F_DHLVerification.verifyTemplateForm(placeHoders, Browser, Result);
		F_DHLSettings.clickOKUpload(Browser);
		F_DHLVerification.verifySuccessCreateMsg(Browser, "Document", Result);

		F_DHLVerification.verifyTemplateExist(templateName, Browser, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		
		// check Edit form ---
		F_Common.selectRow(Browser, templateName);
		F_DHLSettings.clickEdit(Browser);
		
		F_DHLSettings.selectTemplateValue(Browser, placeHoders.get(0), "Assignee");
		F_DHLSettings.selectTemplateValue(Browser, placeHoders.get(1), "Incident Date");
		F_DHLSettings.selectTemplateValue(Browser, placeHoders.get(2), "Incident Code");
		F_DHLSettings.clickOKUpload(Browser);
		F_DHLVerification.verifySuccessUpdateMsg(Browser, "Document", Result);
		//F_Common.waitForPopupDisappear( Browser);
		Assert.assertTrue(Result.Result, Result.Message);
		
		F_Common.selectRow(Browser, templateName);
		F_DHLSettings.clickEdit(Browser);
//		F_DHLVerification.verifyTemplateValueExist(Browser, placeHoders.get(0), "Assignee", Result);
//		F_DHLVerification.verifyTemplateValueExist(Browser, placeHoders.get(1), "Incident Date", Result);
		F_DHLSettings.clickCancelUpload(Browser);
		Assert.assertTrue(Result.Result, Result.Message);
				
		// --- Go to Evidence --
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLInbox.openLatestIncident(Browser);
		F_DHLInbox.enableEditIncident(Browser);
		F_DHLInbox.openEvidenceTab(Browser);
		F_DHLVerification.verifyTemplateExistInEvidenceTab(templateName, Browser, Result);
		
		// -- Select form and create form
		F_DHLInbox.selectATemplate(Browser, templateName);
		
		F_DHLInbox.clickOkbuttonInFormTemplate(Browser);
		F_DHLVerification.verifySuccessCreateFormMsg(Browser, Result);
		// -- Verify created form
		
		//Delete the template
		F_DHLSettings.goToPage.FormTemplate(Browser);
		F_DHLSettings.deleteTemplate(Browser, templateName);
		F_DHLVerification.verifySuccessDeleteMsg(Browser, "Document", Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

}
