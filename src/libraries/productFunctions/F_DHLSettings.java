package libraries.productFunctions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import interfaces.I_DHL_Settings;
import interfaces.I_CP_Common;
import interfaces.I_CP_Settings;
import interfaces.I_Common;
import interfaces.I_DHL_Home;
import libraries.SeleniumBrowser;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Log;
import libraries.generalFunctions.Mouse;
import libraries.verificationFunctions.F_DHLVerification;

public class F_DHLSettings {

	public static class goToPage {
		public static void Setting(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_DHL_Home.tab_Settings);
		}

		public static void System(SeleniumBrowser Browser) {
			Setting(Browser);
			F_Navigation.goToPage(Browser, I_DHL_Settings.tab_System);
		}

		public static void FormTemplate(SeleniumBrowser Browser) {
			Setting(Browser);
			F_Navigation.goToPage(Browser, I_DHL_Settings.tab_FormTemplate);
			Browser.waitForElementVisible(I_Common.rowEle);
		}

		public static void Validation(SeleniumBrowser Browser) {
			Setting(Browser);
			F_Navigation.goToPage(Browser, I_DHL_Settings.tab_ValidationRule);
			Browser.waitForElementVisible(I_Common.rowEle);
		}

		public static void CustomFields(SeleniumBrowser Browser) {
			Setting(Browser);
			F_Navigation.goToPage(Browser, I_DHL_Settings.tab_customFields);
			Browser.waitForElementVisible(I_Common.rowEle);
		}

		public static void IncidentType(SeleniumBrowser Browser) {
			Setting(Browser);
			F_Navigation.goToPage(Browser, I_DHL_Settings.tab_IncidentTypes);
			Browser.waitForElementVisible(I_Common.rowEle);
		}

		public static void IncidentTypeAssignment(SeleniumBrowser Browser) {
			Setting(Browser);
			F_Navigation.goToPage(Browser, I_DHL_Settings.tab_IncidentTypeAssignment);
		}

		public static void States(SeleniumBrowser Browser) {
			Setting(Browser);
			F_Navigation.goToPage(Browser, I_DHL_Settings.tab_States);
			Browser.waitForElementVisible(I_Common.rowEle);
		}

		public static void Roles(SeleniumBrowser Browser) {
			Setting(Browser);
			F_Navigation.goToPage(Browser, I_DHL_Settings.tab_Roles);
			Browser.waitForElementVisible(I_Common.rowEle);
		}

		public static void RoleAssignment(SeleniumBrowser Browser) {
			Setting(Browser);
			F_Navigation.goToPage(Browser, I_DHL_Settings.tab_RoleAssignment);
			Browser.waitForElementVisible(I_Common.rowEle);
		}

		public static void Workflow(SeleniumBrowser Browser) {
			Setting(Browser);
			F_Navigation.goToPage(Browser, I_DHL_Settings.tab_Workflow);
			Browser.waitForElementVisible(I_Common.rowEle);
		}
	}

	// ------------ Common -----
	public static void commonSearch(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.txt_commonSearch, pValue);
		// Browser.switchParentFrame();
		Mouse.FreeMouse(Browser);
		Functions.waitForSeconds(2);
	}

	public static void select(SeleniumBrowser Browser, String pValue) {
		commonSearch(Browser, pValue);
		F_CP_common.selectRow(Browser, 1);
	}

	public static void selectWithoutSearch(SeleniumBrowser Browser, String pValue) {
		F_CP_common.selectRow(Browser, 1);
	}

	public static void deleteRow(SeleniumBrowser Browser, String name) {
		F_DHLSettings.select(Browser, name);
		F_DHLSettings.clickDelete(Browser);
		F_DHLSettings.clickConfirmDelete(Browser);
	}

	public static void closeCurrentTab(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Home.btn_CloseCurrentTab);
		// Browser.clickJavascript(I_AIMS_Home.btn_CloseCurrentTab);
		Functions.waitForSeconds(1);
	}

	public static void clickSaveAtToolbar(SeleniumBrowser Browser) {
		Browser.scrollToEle(I_DHL_Settings.btn_Save);
		Browser.click(I_DHL_Settings.btn_Save);
	}

	public static void clickSaveAtForm(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Settings.btn_SaveAtForm);
		// Browser.waitForElementDisabled(I_DHL_Settings.btn_SaveAtForm);
		Functions.waitForSeconds(0.5);
	}

	public static void clickSaveInline(SeleniumBrowser Browser) {
		Browser.scrollToEle(I_DHL_Settings.btn_SaveInline);
		Browser.click(I_DHL_Settings.btn_SaveInline);
	}

	public static void clickAddNew(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Settings.btn_AddNew);
		Functions.waitForSeconds(1);
	}

	public static void clickEdit(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Settings.btn_Edit);
		Functions.waitForSeconds(1.5);
	}

	public static void clickEditAtPanel(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Settings.btn_Edit);
		Functions.waitForSeconds(0.5);
	}

	public static void clickDelete(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Settings.btn_Delete);
		Functions.waitForSeconds(1);
	}

	// --------------- System page -------

	public static void updateDeletedIncidents(SeleniumBrowser Browser, String dayNumber) {
		Browser.scrollToEle(I_DHL_Settings.txt_deletedMarkedIncident);
		Browser.enter(I_DHL_Settings.txt_deletedMarkedIncident, dayNumber);
		clickSaveAtToolbar(Browser);
	}

	public static void setGenerateIncidentCode(SeleniumBrowser Browser, boolean isAutoGenarate, boolean isOverWrite) {
		goToPage.Setting(Browser);

		if (isAutoGenarate == false) {
			// Browser.selectCheckbox(I_DHL_Settings.rd_manual);

			Browser.scrollToEle(I_DHL_Settings.rd_manual);
			Browser.click(I_DHL_Settings.rd_manual);
			if (isOverWrite == false) {
				Browser.scrollToEle(I_DHL_Settings.chk_overwrite);
				Browser.deselectCheckbox(I_DHL_Settings.chk_overwrite);
			} else {
				Browser.scrollToEle(I_DHL_Settings.chk_overwrite);
				Browser.selectCheckbox(I_DHL_Settings.chk_overwrite);
			}
			;
		} else {
			Browser.scrollToEle(I_DHL_Settings.rd_automatic);
			Browser.click(I_DHL_Settings.rd_automatic);
		}

		clickSaveAtToolbar(Browser);
		Functions.waitForSeconds(2);
	}

	// ------------------ Form Template page----
	public static void clickOKUpload(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Settings.btn_OkTemplateForm);
		Functions.waitForSeconds(1.5);
	}

	public static void clickCancelUpload(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Settings.btn_CancelTemplateForm);
	//	Functions.waitForSeconds(1);
	//	Browser.clickJavascript(I_DHL_Settings.btn_CancelTemplateForm);
		Browser.waitForElementNotVisible(By.xpath("//app-form-template-create"));
	}

	public static void selectTemplate(SeleniumBrowser Browser, String templateName) {
		By rowItem = By.xpath("//tbody/tr/td[contains(., '" + templateName + "')]");
		Browser.click(rowItem);
		Functions.waitForSeconds(1);
	}

	public static void deleteTemplate(SeleniumBrowser Browser, String templateName) {
		selectTemplate(Browser, templateName);
		F_DHLSettings.clickDelete(Browser);
		F_DHLSettings.clickConfirmDelete(Browser);
	}

	public static boolean checkTemplateExist(SeleniumBrowser Browser, String templateName) {
		Browser.setTimeOut(Browser, 5);
		By template = By.xpath("//tbody/tr/td[contains(., '" + templateName + "')]");

		if (Browser.captureInterface(template) != null) {
			Browser.resetTimeOut(Browser);
			return true;
		} else {
			Browser.resetTimeOut(Browser);
			return false;
		}

	}

	// --------------- validation rule page ------
	public static void addNewRule(SeleniumBrowser Browser, String ruleName, String type, String regex, String desc) {
		clickAddNew(Browser);
		F_DHLSettings.fillRuleName(Browser, ruleName);
		F_DHLSettings.selectDataType(Browser, type);
		F_DHLSettings.fillRegex(Browser, regex);
		F_DHLSettings.fillDesc(Browser, desc);
		F_DHLSettings.clickSaveInline(Browser);
	}

	public static void fillRuleName(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.txt_ruleName, pValue);
	}

	public static void selectDataType(SeleniumBrowser Browser, String pValue) {
		// Browser.enter(I_DHL_Settings.txt_ruleName, pValue);
		Browser.click(I_DHL_Settings.dd_dataType);
		Browser.selectDivDropdownByText(Browser, I_DHL_Settings.div_dataTypedropdown, pValue);
	}

	public static void fillRegex(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.txt_regex, pValue);
	}

	public static void fillDesc(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.txt_ruleDesc, pValue);
	}

	public static void deleteRule(SeleniumBrowser Browser, String name) {
		F_DHLSettings.select(Browser, name);
		F_DHLSettings.clickDelete(Browser);
		F_DHLSettings.clickConfirmDelete(Browser);
	}

	// --------------- Custom fields page ------

	public static void fillFieldName(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.txt_fieldName, pValue);
	}

	public static void fillLableForUK(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.txt_UKLabel, pValue);
	}

	public static void fillLableForUS(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.txt_USLabel, pValue);
	}

	public static void fillLableForDE(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.txt_DeuLabel, pValue);
	}

	public static void selectDataTypeForCustomField(SeleniumBrowser Browser, String pValue) {
		Browser.click(I_DHL_Settings.dd_dataTypeOfField);
		Browser.selectDivDropdownByText(Browser, I_Common.div_generalDropdown, pValue);
	}

	public static String selectRandomDataTypeForCustomField(SeleniumBrowser Browser, String pValue) {
		Browser.click(I_DHL_Settings.dd_dataTypeOfField);
		return Browser.selectRandomDropdown(Browser, I_Common.div_generalDropdown, pValue);
	}

	public static void selectRuleForCustomField(SeleniumBrowser Browser, String pValue) {
		Browser.click(I_DHL_Settings.dd_validation);
		Browser.selectDivDropdownByText(Browser, I_Common.div_generalDropdown, pValue);
	}

	public static void selectCustomField(SeleniumBrowser Browser, String pValue) {
		By rowItem = By.xpath("//tbody/tr/td[contains(., '" + pValue + "')]");
		Browser.click(rowItem);
		Functions.waitForSeconds(0.5);
	}

	public static void deleteCustomField(SeleniumBrowser Browser, String name) {
		F_DHLSettings.selectCustomField(Browser, name);
		F_DHLSettings.clickDelete(Browser);
		F_DHLSettings.clickConfirmDelete(Browser);
	}

	public static void addNewCustomField(SeleniumBrowser Browser, String name, String type, String UKlabel,
			String USlabel, String DElabel) {
		F_DHLSettings.clickAddNew(Browser);
		F_DHLSettings.fillFieldName(Browser, name);
		F_DHLSettings.selectDataTypeForCustomField(Browser, type);
		F_DHLSettings.fillLableForUK(Browser, UKlabel);
		F_DHLSettings.fillLableForUS(Browser, USlabel);
		F_DHLSettings.fillLableForDE(Browser, DElabel);
		F_DHLSettings.clickSaveAtForm(Browser);
	}

	public static void updateCustomField(SeleniumBrowser Browser, String name, String UKlabel, String USlabel,
			String DElabel) {
		Browser.enter(I_DHL_Settings.txt_UKLabelEdittab, UKlabel);
		Browser.enter(I_DHL_Settings.txt_USLabelEdittab, USlabel);
		Browser.enter(I_DHL_Settings.txt_DeuLabelEdittab, DElabel);
	}

	// --------------- Incident type --------

	public static String addNewIncidentType(SeleniumBrowser Browser, String name, String code) {
		F_Navigation.goToPage(Browser, I_DHL_Settings.tab_IncidentTypes);
		Browser.click(I_DHL_Settings.btn_addNewType);
		Browser.enter(I_DHL_Settings.txt_InputName, name);
		Browser.enter(I_DHL_Settings.txt_incidentCode, code);
		String type = selectRandomWorkFlow(Browser);
		Browser.click(I_DHL_Settings.btn_SaveAtForm);
		return type;
	}

	public static void fillIncidentType(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.txt_InputName, pValue);
	}

	public static void fillCode(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.txt_incidentCode, pValue);
	}

	public static String selectRandomWorkFlow(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Settings.txt_workflowDD);
		String workflow = Browser.selectRandomDropdown(Browser, I_DHL_Settings.div_workflowDropdown);
		return workflow;
	}

	public static void clickEditIncidentType(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Settings.btn_EditIncidentType);
		Functions.waitForSeconds(1);
	}

	public static void importIncidentType(SeleniumBrowser Browser) {
//		String path = "..\\Test Data\\IncidentTypes.csv";

		String path = "D:\\QC\\Automation\\Framework\\myFW\\Test Data\\IncidentTypes.csv";
		List<String> list = Functions.ReadCSV_Scanner(path);

		String incidentTypeName = "";
		goToPage.IncidentType(Browser);

		for (int i = 0; i < list.size(); i++) {

			incidentTypeName = list.get(i);
			if (incidentTypeName != "") {
				Browser.click(I_DHL_Settings.btn_addNewType);
				Browser.enter(I_DHL_Settings.txt_InputName, incidentTypeName);
				Browser.enter(I_DHL_Settings.txt_incidentCode, Functions.randomNumberString(5));
				Browser.click(I_DHL_Settings.txt_workflowDD);
				Browser.selectRandomDropdown(Browser, I_DHL_Settings.div_workflowDropdown);

				Browser.click(I_DHL_Settings.btn_Save);
				Functions.waitForSeconds(0.5);
				Log.info("Incident type addded success = " + incidentTypeName);
			}
		}
	}

	// --------------- Incident type assigment -----

	public static void clickAddCatalog(SeleniumBrowser Browser, String name) {
		WebElement editEle = Browser.captureInterface(
				By.xpath("//tree-node-wrapper/div/div/tree-node-content[contains(.,'" + name + "')]/button[1]/i"));
		editEle.click();
		Functions.waitForSeconds(1);
	}

	public static void addNewCatalog(SeleniumBrowser Browser, String catalog) {
		F_AIMS.clickAddCatalog(Browser, "INCIDENT CATALOG");
		F_AIMS.fillCatalogName(Browser, catalog);
		F_AIMS.clickSaveCatalogue(Browser);
	}

	// public static void clickAddSubCatalog(SeleniumBrowser Browser, String name) {
	// WebElement editEle = Browser.captureInterface(By
	// .xpath("//tree-node-content[contains(.,'" + name + "')]/button[1]/i"));
	// editEle.click();
	// }

	public static void clickDeleteCatalog(SeleniumBrowser Browser, String name) {
		By deleteCatEle = By
				.xpath("//tree-node/div/tree-node-wrapper//tree-node-content[contains(.,'" + name + "')]/button[2]/i");
		WebElement editEle = Browser.captureInterface(deleteCatEle);
		Browser.scrollToEle(editEle);
		// editEle.click();
		Browser.clickJavascript(deleteCatEle);
	}

	public static void fillCatalogName(SeleniumBrowser Browser, String pValue) {
		Browser.scrollToEle(I_CP_Settings.OffenceTypeAssign.txt_Name);
		Browser.enter(I_CP_Settings.OffenceTypeAssign.txt_Name, pValue);
	}

	public static void clickSaveCatalogue(SeleniumBrowser Browser) {
		Browser.scrollToEle(I_CP_Settings.OffenceTypeAssign.btn_Save);
		Browser.click(I_CP_Settings.OffenceTypeAssign.btn_Save);

	}

	public static void clickSaveEditCatalogue(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.OffenceTypeAssign.btn_SaveEdit);
	}

	public static void expandCatalog(SeleniumBrowser Browser, String name) {
		By expandBy = By.xpath("//tree-node-content[contains(.,'" + name + "')]/../../tree-node-expander/span");
		WebElement catalogEle = Browser.captureInterface(expandBy);
		String classValue = "toggle-children-wrapper-collapsed";
		String a = catalogEle.getAttribute("class");
		System.out.println(a);
		if (catalogEle.getAttribute("class").contains(classValue)) {
			catalogEle.click();
			Browser.waitForAttributeValue(expandBy, "class", "toggle-children-wrapper-expanded");
		}
	}

	public static void collapseCatalog(SeleniumBrowser Browser, String name) {
		By expandBy = By.xpath("//tree-node-content[contains(.,'" + name + "')]/../../tree-node-expander/span");
		WebElement catalogEle = Browser.captureInterface(expandBy);
		String classValue = "toggle-children-wrapper-expanded";
		String a = catalogEle.getAttribute("class");
		System.out.println(a);
		if (catalogEle.getAttribute("class").contains(classValue)) {
			catalogEle.click();
			Browser.waitForAttributeValue(expandBy, "class", "toggle-children-wrapper-collapsed");
		}
	}

	public static void clickExtendUncategorized(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.OffenceTypeAssign.btn_extendUncategorized);
	}

	public static void createCatalog(SeleniumBrowser Browser, String name) {
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.Assignment(Browser);
		F_CPSettings.clickAddCatalog(Browser, "OFFENCE CATALOG");
		F_CPSettings.fillCatalogName(Browser, name);
		F_CPSettings.clickSaveCatalogue(Browser);
	}

	public static void editCatalog(SeleniumBrowser Browser, String originalName, String newName) {
		WebElement catalogEle = Browser
				.captureInterface(By.xpath("//img/following-sibling::span[contains(.,'" + originalName + "')]"));
		Mouse.RightclickOnElement(Browser, catalogEle);
		fillCatalogName(Browser, newName);
		clickSaveEditCatalogue(Browser);
	}

	public static void clickConfirmDelete(SeleniumBrowser Browser) {
		// Browser.waitForElementVisible(I_CP_Common.btn_OkConfirm);
		// Browser.click(I_CP_Common.btn_OkConfirm);
		Browser.clickJavascript(I_CP_Common.btn_OkConfirm);
		Functions.waitForSeconds(0.5);
	}

	public static void clickConfirmDeleteCatalog(SeleniumBrowser Browser) {
		Browser.waitForElementEnabled(I_CP_Common.btn_OkConfirm);
		// Browser.click(I_CP_Common.btn_OkDeleteConfirm);
		Browser.clickJavascript(I_CP_Common.btn_OkDeleteConfirm);
	}

	public static void dragdropType(SeleniumBrowser Browser, String typeName, String catelogName) {

		By typeBy = By.xpath(
				"//tree-node-wrapper/div/div/tree-node-content/span[text() = \"Vehicle Type Prohibition (motor vehicles)\"]");
		WebElement typeEle = Browser.captureInterface(typeBy);
		System.out.println(typeEle.getSize());
		System.out.println(typeEle.getText());
		// WebElement catalogEle = Browser
		// .captureInterface(By.xpath("//img/following-sibling::span[contains(.,'" +
		// catelogName + "')]"));
		WebElement catalogEle = Browser
				.captureInterface(By.xpath("//img/following-sibling::span[contains(.,'ZEOKIQ_TEST')]"));

		System.out.println(catalogEle.getSize());
		System.out.println(catalogEle.getText());
		Mouse.DragDropElement(Browser, typeEle, catalogEle);

	}

	/**
	 * @param Browser
	 * @param categoryName
	 */
	public static String addnewSubcategory(SeleniumBrowser Browser, String categoryName) {
		WebElement newCategory = Browser.Driver
				.findElement(By.xpath("//div[@id='tree-root-categorized']/ol/li/div/span[contains(text(),'"
						+ categoryName + "')]/../span[last()]/button[1]"));
		newCategory.click();

		By ol_Subcategory = By.xpath("//div[@id='tree-root-categorized']/ol/li/div/span[contains(text(),'"
				+ categoryName + "')]/../following-sibling::ol");

		WebElement Category = Browser.Driver.findElement(ol_Subcategory);

		WebElement newdefaultCategory = Category.findElement(By.xpath("//li[last()]/div/input"));
		String randomName = Functions.randomText();
		newdefaultCategory.clear();
		newdefaultCategory.sendKeys(randomName);
		Browser.click(I_DHL_Settings.btn_SaveCategory);
		return randomName;
	}

	public static void extendCatItems(SeleniumBrowser Browser, String categoryName) {
		By pbtnExtend = By.xpath("//span[contains(text(),'" + categoryName + "')]/../span[1]/i");
		WebElement btnExtend = Browser.Driver.findElement(pbtnExtend);
		if (btnExtend.getAttribute("class").contains("right"))
			Browser.scrollToEle(btnExtend);
		btnExtend.click();
	}

	// ----------------- States -------------
	public static void fillStateName(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.txt_stateName, pValue);
	}

	public static void selectFinalState(SeleniumBrowser Browser, boolean pValue) {
		if (pValue == true)
			Browser.selectCheckbox(I_DHL_Settings.chk_finalState);

		if (pValue == false)
			Browser.deselectCheckbox(I_DHL_Settings.chk_finalState);
	}

	public static void selectRequirefield(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.dd_requiredFields, pValue);
	}

	public static void fillStateDesc(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.txt_stateDesc, pValue);
	}

	public static void addNewState(SeleniumBrowser Browser, String status, String desc, boolean isFinalState) {
		F_DHLSettings.clickAddNew(Browser);
		F_DHLSettings.fillStateName(Browser, status);
		F_DHLSettings.selectFinalState(Browser, isFinalState);
		F_DHLSettings.fillStateDesc(Browser, desc);
		F_DHLSettings.clickSaveInline(Browser);
		Functions.waitForSeconds(1);
	}

	
	public static void UncheckAllRequiredComment(SeleniumBrowser Browser) {
		
		
		
	}
	
	public static void UncheckAllRequiredInput(SeleniumBrowser Browser) {
		
	}
	
	
	
	// -------------- Roles ----------------

	public static void fillRoleName(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.txt_roleName, pValue);
	}

	public static void addNewrole(SeleniumBrowser Browser, String role) {
		F_DHLSettings.clickAddNew(Browser);
		F_DHLSettings.fillRoleName(Browser, role);
		F_DHLSettings.clickSaveInline(Browser);
		Functions.waitForSeconds(1);
	}

	// ----------------- Assign Role ----------
	public static void updateRoleForUser(SeleniumBrowser Browser, String username, String role) {
		F_DHLSettings.commonSearch(Browser, username);
		F_DHLSettings.select(Browser, username);
		F_DHLSettings.clickEdit(Browser);
		if (Browser.captureInterface(I_DHL_Settings.btn_clearAll) != null)
			Browser.click(I_DHL_Settings.btn_clearAll);

		Browser.click(I_DHL_Settings.dd_roles);
		Browser.selectDivDropdownByText(Browser, I_Common.div_generalDropdown, role);
		F_DHLSettings.clickSaveInline(Browser);
	}

	// -------------- Workflow ----------------

	public static void fillWorkflowName(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.txt_workflowName, pValue);
	}

	public static void selectFirstState(SeleniumBrowser Browser, String pValue) {
		Browser.click(I_DHL_Settings.dd_firstState);
		Browser.selectDivDropdownByText(Browser, I_Common.div_generalDropdown, pValue);
	}

	public static String selectRandomFirstState(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Settings.dd_firstState);
		return Browser.selectRandomDropdown(Browser, I_Common.div_generalDropdown);

	}

	public static void selectDefaultWorkflow(SeleniumBrowser Browser, boolean pValue) {
		if (pValue == true)
			Browser.selectCheckbox(I_DHL_Settings.chk_defaultWorkflowAtForm);

		if (pValue == false)
			Browser.deselectCheckbox(I_DHL_Settings.chk_defaultWorkflowAtForm);
	}

	public static void addNewWorkflow(SeleniumBrowser Browser, String name, String firststate) {
		F_DHLSettings.clickAddNew(Browser);
		F_DHLSettings.fillWorkflowName(Browser, name);
		if (firststate == null || firststate == "") {
			F_DHLSettings.selectRandomFirstState(Browser);
		} else
			selectFirstState(Browser, firststate);

		F_DHLSettings.clickSaveAtForm(Browser);
	}

	public static void renameWF(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Settings.txt_workflowNameAtEditTab, pValue);
	}

	public static void selectDefaultWorkflowAtEditTab(SeleniumBrowser Browser, boolean pValue) {
		if (pValue == true)
			Browser.selectCheckbox(I_DHL_Settings.chk_defaultWorkflowAtEditTab);

		if (pValue == false)
			Browser.deselectCheckbox(I_DHL_Settings.chk_defaultWorkflowAtEditTab);
	}

	public static void selectFirstStateAtEditTab(SeleniumBrowser Browser, String pValue) {
		Browser.click(I_DHL_Settings.dd_firstStateAtEditTab);
		Browser.selectDivDropdownByText(Browser, I_Common.div_generalDropdown, pValue);
	}

	public static String selectRandomFirstStateAtEditTab(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Settings.dd_firstStateAtEditTab);
		return Browser.selectRandomDropdown(Browser, I_Common.div_generalDropdown);

	}

	public static void deleteWF(SeleniumBrowser Browser, String wf) {
		F_DHLSettings.select(Browser, wf);
		F_DHLSettings.clickDelete(Browser);
		F_DHLSettings.clickConfirmDelete(Browser);
	}

	public static void selectTemplateValue(SeleniumBrowser Browser, String label, String value) {

		WebElement FieldLabel = Browser.captureInterface(By.xpath(
				"//app-form-template-create/div[@class='customBoxScroll']//div[@class='form-horizontal']/div/label[contains(., '"
						+ label + "')]/following-sibling::div"));

		FieldLabel.click();

		By dropdown = By.xpath(
				"//app-form-template-create/div[@class='customBoxScroll']//div[@class='form-horizontal']/div/label[contains(., '"
						+ label + "')]/following-sibling::div/ng-select/ng-dropdown-panel/div/div[2]");

		Browser.selectDivDropdownByText(Browser, dropdown, value);
	//	Browser.waitForElementNotVisible(dropdown);
	
		
	}

}
