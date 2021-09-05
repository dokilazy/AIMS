package tests.SBP;

import org.testng.annotations.Test;

import java.text.MessageFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import interfaces.I_Common;
import interfaces.I_DHL_Home;
import interfaces.I_DHL_Settings;
import libraries.CPValueList;
import libraries.Constants;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.DHLValueList.Message;
import libraries.DHLValueList.dataType;
import libraries.DHLValueList.Form;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Mouse;
import libraries.productFunctions.F_DHLInbox;
import libraries.productFunctions.F_DHLSettings;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_DHLVerification;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;

public class DHL_Setting_1 extends DefaultAnnotations {

	@Test(groups = { TestSuite.BVT })
	public void verify_add_new_Incident_Type() {
		String typename = Functions.randomString("IncidentType_", "");
		String code = Functions.randomNumberString(6);
		String workflow = "";
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.Setting(Browser);
		workflow = F_DHLSettings.addNewIncidentType(Browser, typename, code);
		F_DHLVerification.verifySuccessCreateMsg(Browser, "Incident type", Result);
		F_DHLVerification.verifyIncidentType(Browser, typename, code, workflow, Result);
		F_DHLVerification.verifyNewIncidentTypeatAssignmentpage(Browser, typename, Result);

		Assert.assertTrue(Result.Result, Result.Message);
		//--- post condition ---
		F_Navigation.goToPage(Browser, I_DHL_Settings.tab_IncidentTypes);
		F_DHLSettings.deleteRow(Browser, typename);
	}

	@Test(groups = { TestSuite.BVT })
	public void verify_edit_IncidentType() {

		String name = Functions.randomString("TestType_", "");
		String code = Functions.randomNumberString(8);
		String wf = "";
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.IncidentType(Browser);
		wf = F_DHLSettings.addNewIncidentType(Browser, name, code);
		F_DHLSettings.select(Browser, name);
		F_DHLSettings.clickEditIncidentType(Browser);
		String newname = name + "_up";
		F_DHLSettings.fillIncidentType(Browser, newname);
		Browser.click(I_DHL_Settings.btn_SaveEdit);

		F_DHLVerification.verifySuccessUpdateMsg(Browser, "Incident type", Result);
		F_DHLSettings.closeCurrentTab(Browser);
		// F_Navigation.RefreshPage(Browser);
		// -- refresh
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLSettings.goToPage.IncidentType(Browser);
		

		F_DHLVerification.verifyIncidentType(Browser, newname, code, wf, Result);
		F_DHLVerification.verifyNewIncidentTypeatAssignmentpage(Browser, newname, Result);

		Assert.assertTrue(Result.Result, Result.Message);
		
		//--- post condition ---
		F_Navigation.goToPage(Browser, I_DHL_Settings.tab_IncidentTypes);
		F_DHLSettings.deleteRow(Browser, newname);
	}
	
	
	@Test(groups = { TestSuite.BVT })
	public void verify_delete_incident_type() {
		
		String name = Functions.randomString("TestType_", "");
		String code = Functions.randomNumberString(8);
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.IncidentType(Browser);
		String wf = F_DHLSettings.addNewIncidentType(Browser, name, code);
		F_DHLVerification.verifyIncidentType(Browser, name, code, wf, Result);
		
		F_DHLSettings.select(Browser, name);
		F_DHLSettings.clickDelete(Browser);
		F_DHLVerification.verifyDeleteConfirm(Browser, name, Result);
		F_DHLSettings.clickConfirmDelete(Browser);
		F_DHLVerification.verifySuccessDeleteMsg(Browser, name, Result);
		F_DHLVerification.verifyRowNotExist(Browser, name, Result);
		
		Assert.assertTrue(Result.Result, Result.Message);
	}
	

	@Test
	public void Verify_add_and_delete_Category() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.IncidentTypeAssignment(Browser);
		String catalog = Functions.randomText();
		F_DHLSettings.clickAddCatalog(Browser, "INCIDENT CATALOG");
		F_DHLSettings.fillCatalogName(Browser, catalog);
		F_DHLSettings.clickSaveCatalogue(Browser);

		F_DHLVerification.verifySuccessCreateMsg(Browser, "Incident Catalog", Result);
		F_DHLVerification.verifyCatalogExist(Browser, catalog, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// --- Delete Catalog ---
		F_DHLSettings.clickDeleteCatalog(Browser, catalog);
		F_DHLSettings.clickConfirmDeleteCatalog(Browser);
		F_DHLVerification.verifySuccessDeleteMsg(Browser, "Incident Catalog", Result);

		F_DHLVerification.verifyCatalogNotExist(Browser, catalog, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test
	public void Verify_rename_a_category() {
		String originalName = Functions.randomText();
		String updateName = Functions.randomText();
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.IncidentTypeAssignment(Browser);
		String catalog = Functions.randomText();
		F_DHLSettings.addNewCatalog(Browser, originalName);
		// F_Navigation.RefreshPage(Browser);

		// -- refresh
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLSettings.goToPage.IncidentTypeAssignment(Browser);

		// ---- Rename ---
		F_DHLSettings.editCatalog(Browser, originalName, updateName);
		F_DHLVerification.verifySuccessUpdateMsg(Browser, "Incident Catalog", Result);
		F_DHLVerification.verifyCatalogExist(Browser, updateName, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// ---- Delete Category-----
		F_DHLSettings.clickDeleteCatalog(Browser, updateName);
		F_DHLSettings.clickConfirmDeleteCatalog(Browser);
	}

	// @Test
	public void Verify_dragdrop_IncidentType_to_Catalog() {

		By UNCATEGORIZED_folder = By.xpath("//span[contains(text(), 'UNCATEGORIZED')]");
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.IncidentTypeAssignment(Browser);
		String catalog = Functions.randomText();
		F_DHLSettings.addNewCatalog(Browser, catalog);
		F_DHLVerification.verifySuccessCreateMsg(Browser, "Incident Catalog", Result);
		Browser.waitForElementNotVisible(I_DHL_Home.div_SuccessMessage);

		// -- refresh
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLSettings.goToPage.IncidentTypeAssignment(Browser);
		F_DHLVerification.verifyCatalogExist(Browser, catalog, Result);

		WebElement targetCat = Browser.Driver.findElement(By.xpath("//span[contains(text(),'" + catalog + "')]"));
		String lableName = targetCat.getText();

		List<WebElement> incidentTypeElements = Browser.Driver.findElements(I_DHL_Settings.treenode_UncategorizedList);

		// Get random 15 in top 20 in list
		Browser.scrollToEle(UNCATEGORIZED_folder);
		int index = Functions.randomInterger(15, 20);
		WebElement source = incidentTypeElements.get(index);
		String IncidentTypeName = source.getText();
		System.out.println(IncidentTypeName);

		// --- Drag and drop to Category
		Mouse.DragDropElement(Browser, source, targetCat);
		F_DHLVerification.verifySuccessAssignMsg(Browser, "Incident Type", Result);
		// Browser.waitForElementNotVisible(I_AIMS_Home.div_SuccessMessage);

		// ----- Check Incident type moved success---
		targetCat = Browser.Driver.findElement(By.xpath("//span[contains(text(),'" + catalog + "')]"));
		lableName = targetCat.getText();
		F_GeneralVerification.verifyElementContent(Browser, "Incident Name", lableName, "content (1)", Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// --- Check in Incident Type list
		List<WebElement> Elements = Browser.Driver.findElements(I_DHL_Settings.treenode_UncategorizedList);
		for (WebElement s : Elements) {
			if (s.getText().contains(IncidentTypeName) == true) {
				Result.SetResult(false);
				Result.SetMessage("Incident type list contains " + IncidentTypeName);
			}
		}
		Assert.assertTrue(Result.Result, Result.Message);

		// Move back to Uncategorized then delete category
		F_DHLSettings.extendCatItems(Browser, catalog);
		WebElement sourceEle = Browser.Driver.findElement(By.xpath("//span[contains(text(),'" + catalog + "')]"));

		WebElement UncategorizedEle = Browser.captureInterface(UNCATEGORIZED_folder);
		Mouse.DragDropElement(Browser, sourceEle, UncategorizedEle);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_DHL_Home.div_SuccessMessage, Message.AssignSuccess,
				Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// --- Post condition ---
		F_DHLSettings.clickDeleteCatalog(Browser, catalog);
		F_DHLSettings.clickConfirmDeleteCatalog(Browser);
	}

	@Test
	public void Verify_create_Subcategory() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.IncidentTypeAssignment(Browser);
		String catalog = Functions.randomText();
		String subCatalog = Functions.randomText();
		// --- Create new category ---
		F_DHLSettings.addNewCatalog(Browser, catalog);

		// ---- Add new child category ----
		F_DHLSettings.clickAddCatalog(Browser, catalog);
		F_DHLSettings.expandCatalog(Browser, catalog);
		F_DHLSettings.fillCatalogName(Browser, subCatalog);
		F_DHLSettings.clickSaveCatalogue(Browser);
		F_DHLVerification.verifySuccessCreateMsg(Browser, "Incident Catalog", Result);
		F_DHLVerification.verifyCatalogExist(Browser, subCatalog, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// --- Delete all --
		F_DHLSettings.clickDeleteCatalog(Browser, subCatalog);
		F_DHLSettings.clickConfirmDeleteCatalog(Browser);
		Functions.waitForSeconds(2);
		F_DHLSettings.clickDeleteCatalog(Browser, catalog);
		F_DHLSettings.clickConfirmDeleteCatalog(Browser);
	}

	@Test
	public void Verify_update_system_page() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.System(Browser);
		String originalDays = "30";
		String randomDays = Functions.randomNumber(false);
		F_DHLSettings.updateDeletedIncidents(Browser, randomDays);
		F_DHLVerification.verifySuccessUpdateMsg(Browser, "System", Result);
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLSettings.goToPage.System(Browser);
		F_DHLVerification.verifyDeletedIncidentsSetting(Browser, randomDays, Result);

		Assert.assertTrue(Result.Result, Result.Message);
		F_DHLSettings.updateDeletedIncidents(Browser, originalDays);
	}

	@Test
	public void Verify_add_validation_rule() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.Validation(Browser);
		F_DHLSettings.clickAddNew(Browser);

		String ruleName = Functions.randomText(6);
		String regex = Functions.randomText(20);
		String desc = Functions.randomText(30);
		String type = dataType.string;
		F_DHLSettings.fillRuleName(Browser, ruleName);
		F_DHLSettings.selectDataType(Browser, type);
		F_DHLSettings.fillRegex(Browser, regex);
		F_DHLSettings.fillDesc(Browser, desc);
		F_DHLSettings.clickSaveInline(Browser);
		F_DHLVerification.verifySuccessCreateMsg(Browser, ruleName, Result);

		F_DHLVerification.verifyValidationRule(Browser, ruleName, type, regex, desc, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// -------- delete rule --
		F_DHLSettings.deleteRule(Browser, ruleName);
	}

	@Test
	public void Verify_edit_validation_rule() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.Validation(Browser);
		String ruleName = Functions.randomText(6);
		String regex = Functions.randomText(20);
		String desc = Functions.randomText(30);
		String type = dataType.string;
		F_DHLSettings.addNewRule(Browser, ruleName, type, regex, desc);
		F_DHLVerification.verifyValidationRule(Browser, ruleName, type, regex, desc, Result);

		F_DHLSettings.select(Browser, ruleName);
		F_DHLSettings.clickEdit(Browser);
		String newName = Functions.randomText(6);
		String newRegex = Functions.randomText(20);
		String newDesc = Functions.randomText(30);

		F_DHLSettings.fillRuleName(Browser, newName);
		F_DHLSettings.fillRegex(Browser, newRegex);
		F_DHLSettings.fillDesc(Browser, newDesc);

		F_DHLSettings.clickSaveInline(Browser);
		F_DHLVerification.verifySuccessUpdateMsg(Browser, newName, Result);
		F_DHLVerification.verifyValidationRule(Browser, newName, type, newRegex, newDesc, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// -------- delete rule --
		F_DHLSettings.deleteRule(Browser, newName);

	}

	@Test
	public void Verify_delete_validation_rule() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.Validation(Browser);
		String ruleName = Functions.randomText(6);
		String regex = Functions.randomText(20);
		String desc = Functions.randomText(30);
		String type = dataType.string;

		F_DHLSettings.addNewRule(Browser, ruleName, type, regex, desc);
		F_DHLSettings.select(Browser, ruleName);
		F_DHLSettings.clickDelete(Browser);
		F_DHLVerification.verifyDeleteConfirm(Browser, ruleName, Result);
		F_DHLSettings.clickConfirmDelete(Browser);
		F_DHLVerification.verifySuccessDeleteMsg(Browser, ruleName, Result);

		F_DHLVerification.verifyRowNotExist(Browser, ruleName, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}


	
	
}
