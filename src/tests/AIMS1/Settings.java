package tests.AIMS1;

import java.text.MessageFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import interfaces.I_AIMS_Home;
import interfaces.I_AIMS_Settings;
import interfaces.I_CP_Common;
import libraries.CPValueList;
import libraries.Constants;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.ValueList.AIMSMessage;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Mouse;
import libraries.productFunctions.F_AIMS;
import libraries.productFunctions.F_CPSettings;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_AIMSVerification;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;

public class Settings extends DefaultAnnotations {

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void verify_add_new_Incident_type() {

		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_Navigation.goToPage(Browser, I_AIMS_Home.tab_Settings);
		String name = Functions.randomString("TestType_", "");
		String code = Functions.randomNumberString(8);
		F_AIMS.addNewIncidentType(Browser, name, code);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "Incident type"), Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_AIMSVerification.verify_IncidentType(Browser, name, code, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_AIMSVerification.verify_new_incidentType_at_Assignment_page(Browser, name, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test
	public void verify_update_IncidentType() {

		String name = Functions.randomString("TestType_", "");
		String code = Functions.randomNumberString(8);

		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.gotoIncidentTypepage(Browser);
		F_AIMS.addNewIncidentType(Browser, name, code);
		F_AIMS.selectToEdit(Browser, name);

		Browser.waitAndClick(I_AIMS_Settings.btn_EditIncidentType);
		name = name + "_up";
		Browser.enter(I_AIMS_Settings.txt_InputName, name);
		Browser.click(I_AIMS_Settings.btn_SaveEdit);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.UpdateIncidentTypeSuccess, Result);
		F_AIMS.closeCurrentTab(Browser);
		F_Navigation.RefreshPage(Browser);
		F_AIMS.gotoIncidentTypepage(Browser);

		F_AIMSVerification.verify_IncidentType(Browser, name, code, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_AIMSVerification.verify_new_incidentType_at_Assignment_page(Browser, name, Result);
		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test
	public void Verify_Add_new_Category_than_delete() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.gotoAssignmentpage(Browser);

		String catalog = Functions.randomText();

		F_AIMS.clickAddCatalog(Browser, "INCIDENT CATALOG");
		F_AIMS.fillCatalogName(Browser, catalog);
		F_AIMS.clickSaveCatalogue(Browser);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "Incident Catalog"), Result);

		By pcategory = By.xpath("//span[contains(text(),'" + catalog + "')]");
		F_AIMSVerification.verifyCatalogExist(Browser, catalog, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_AIMS.clickDeleteCatalog(Browser, catalog);
		F_AIMS.clickConfirmDeleteCatalog(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.DeleteCategorySuccess, Result);

		F_GeneralVerification.verifyElementNotExist(Browser, catalog, pcategory, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test
	public void Verify_Rename_a_category() {
		String originalName = Functions.randomText();
		String updateName = Functions.randomText();
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.gotoAssignmentpage(Browser);
		String catalog = Functions.randomText();
		F_AIMS.addNewCatalog(Browser, originalName);
		F_Navigation.RefreshPage(Browser);
		
		// ---- Rename ---
		F_AIMS.editCatalog(Browser, originalName, updateName);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "Incident Catalog"), Result);
		
		F_AIMSVerification.verifyCatalogExist(Browser, updateName, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// ---- Delete Category-----
		F_AIMS.clickDeleteCatalog(Browser, updateName);
		F_AIMS.clickConfirmDeleteCatalog(Browser);
	}

	@Test
	public void Verify_Dragdrop_IncidentType_to_Catalog() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.gotoAssignmentpage(Browser);
		String catalog = Functions.randomText();
		F_AIMS.addNewCatalog(Browser, catalog);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.CreateCategorySuccess, Result);
		Browser.waitForElementNotVisible(I_AIMS_Home.div_SuccessMessage);

		F_AIMSVerification.verifyCatalogExist(Browser, catalog, Result);

		WebElement targetCat = Browser.Driver.findElement(By.xpath("//span[contains(text(),'" + catalog + "')]"));
		String lableName = targetCat.getText();

		List<WebElement> incidentTypeElements = Browser.Driver.findElements(I_AIMS_Settings.treenode_UncategorizedList);

		// Get random 20 in top 30 in list
		int index = Functions.randomInterger(19, 29);
		WebElement source = incidentTypeElements.get(index);
		String IncidentTypeName = source.getText();

		// --- Drag and drop to Category
		Mouse.DragDropElement(Browser, source, targetCat);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.AssignIncidentTypeSuccess, Result);
	//	Browser.waitForElementNotVisible(I_AIMS_Home.div_SuccessMessage);

		// ----- Check Incident type moved success---
		targetCat = Browser.Driver.findElement(By.xpath("//span[contains(text(),'" + catalog + "')]"));
		lableName = targetCat.getText();
		F_GeneralVerification.verifyElementContent(Browser, "Incident Name", lableName, "(1)", Result);
		
		By assigment = By.xpath("//span[contains(text(),'" + catalog + "')]");
		F_GeneralVerification.verifyElementExist(Browser, "assigment Incident Type", assigment, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// --- Check in Incident Type list
		
		List<WebElement> Elements = Browser.Driver.findElements(I_AIMS_Settings.treenode_UncategorizedList);
		for (WebElement s : Elements) {
			if (s.getText().contains(IncidentTypeName) == true) {
				Result.SetResult(false);
				Result.SetMessage("Incident type list contains " + IncidentTypeName);
			}
		}
		Assert.assertTrue(Result.Result, Result.Message);


		// Move back to Uncategorized then delete category
		F_AIMS.extendCatItems(Browser, catalog);
		WebElement sourceEle = Browser.Driver.findElement(assigment);

		WebElement UncategorizedEle = Browser
				.captureInterface(By.xpath("//span[contains(text(), 'UNCATEGORIZED')]"));
		Mouse.DragDropElement(Browser, sourceEle, UncategorizedEle);
		
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.AssignIncidentTypeSuccess, Result);
		
		Assert.assertTrue(Result.Result, Result.Message);
		
		//--- Post condition ---
		F_AIMS.clickDeleteCatalog(Browser, catalog);
		F_AIMS.clickConfirmDeleteCatalog(Browser);
	}

	@Test
	public void Verify_Create_Subcategory() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.gotoAssignmentpage(Browser);
		String catalog = Functions.randomText();
		String subCatalog = Functions.randomText();
		// --- Create new category ---
		F_AIMS.addNewCatalog(Browser, catalog);

		// ---- Add new child category ----
		F_AIMS.clickAddCatalog(Browser, catalog);
		F_AIMS.expandCatalog(Browser, catalog);
		F_AIMS.fillCatalogName(Browser, subCatalog);
		F_AIMS.clickSaveCatalogue(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "Incident Catalog"), Result);

		F_AIMSVerification.verifyCatalogExist(Browser, subCatalog, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// --- Delete all --
		F_AIMS.clickDeleteCatalog(Browser, subCatalog);
		F_AIMS.clickConfirmDeleteCatalog(Browser);
		Functions.waitForSeconds(2);
		F_AIMS.clickDeleteCatalog(Browser, catalog);
		F_AIMS.clickConfirmDeleteCatalog(Browser);
	}
	

	@Test
	public void Verify_Add_new_role() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		Assert.assertTrue(Result.Result, Result.Message);
	
	}
	@Test
	public void Verify_update_role() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		Assert.assertTrue(Result.Result, Result.Message);
	
	}@Test
	public void Verify_delete_role() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		Assert.assertTrue(Result.Result, Result.Message);
	
	}
}
