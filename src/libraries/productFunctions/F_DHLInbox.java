package libraries.productFunctions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import interfaces.I_AIMS_Home;
import interfaces.I_DHL_Details;
import interfaces.I_DHL_Home;
import interfaces.I_CP_Contraventions;
import interfaces.I_Common;
import libraries.CPValueList;
import libraries.Constants;
import libraries.DHLValueList;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.ValueList;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Mouse;
import libraries.objects.DetailField;
import libraries.objects.O_DHLIncident;
import libraries.objects.O_ExportLog;
import libraries.verificationFunctions.F_GeneralVerification;

public class F_DHLInbox {

	public static void gotoInboxpage(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Home.tab_Inbox);
		//wait for incidentes loading
		Functions.waitForSeconds(6);
	}

	public static void clickAddIncident(SeleniumBrowser Browser) {
		// Browser.clickJavascript(I_AIMS_Home.btn_AddIncident);

		// Browser.click(I_AIMS_Home.btn_AddIncident);
		Browser.click(I_AIMS_Home.btn_addIncidentOnToolbar);
		Functions.waitForSeconds(1);
	}

	public static void saveIncident(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Details.btn_Save);
		Functions.waitForSeconds(1);
	}

	public static void closeIncident(SeleniumBrowser Browser) {

		F_AIMS.closeCurrentTab(Browser);
		Functions.waitForSeconds(2);

		Browser.setTimeOut(Browser, 2);
		WebElement a = Browser.captureInterface(I_DHL_Details.dlg_CloseTab);

		if (a != null && a.isDisplayed()) {
			Browser.click(I_DHL_Details.btn_SaveChanges);
			Functions.waitForSeconds(2);
		}

		if (Browser.captureInterface(I_DHL_Home.btn_CloseCurrentTab) != null)
			F_AIMS.closeCurrentTab(Browser);

		Browser.resetTimeOut(Browser);
	}

	public static void clickDeleteIncident_intoolBar(SeleniumBrowser Browser) {
		Browser.waitAndClick(I_DHL_Home.btn_deleteIncidentOnToolbar);
		Functions.waitForSeconds(0.5);
	}

	public static void clickDeleteIncident_inDetails(SeleniumBrowser Browser) {
		Browser.waitAndClick(I_DHL_Home.btn_deleteIncidentIndetails);
		Functions.waitForSeconds(0.5);
	}

	public static void clickUndeleteIncident_intoolBar(SeleniumBrowser Browser) {
		Browser.waitAndClick(I_DHL_Home.btn_undeleteIncidentOnToolbar);
		Functions.waitForSeconds(0.5);
	}

	public static void clickUndeleteIncident_inDetails(SeleniumBrowser Browser) {
		Browser.waitAndClick(I_DHL_Home.btn_undeleteIncidentIndetails);
		Functions.waitForSeconds(0.5);
	}

	public static void clickOkDeleletConfirm(SeleniumBrowser Browser) {
		Browser.waitAndClick(I_DHL_Home.btn_OkConfirmDialog);
		Functions.waitForSeconds(0.5);
	}

	public static String selectIncidentTypeDropdown(SeleniumBrowser Browser, By pBy) {
		String type = "";
		do {
			type = Browser.selectRandomDropdown(Browser, pBy);
		} while (type == "Crime");

		return type;
	}

	public static String selectRandomIncidentType(SeleniumBrowser Browser, By pBy_div) {

		WebElement ddList = Browser.captureInterface(pBy_div);
		int Groupcount = ddList.findElements(By.xpath("li")).size();

		int i = Functions.randomInterger(1, Groupcount);
		WebElement Group = ddList.findElement(By.xpath("li[" + i + "]"));

		int count = Group.findElements(By.xpath("div")).size();
		int s = Functions.randomInterger(3, count);

		WebElement item = Group.findElement(By.xpath("div[" + s + "]"));
		String name = item.getText();

		Browser.scrollToEle(item);
		// Functions.waitForSeconds(1);
		item.click();

		return name;
	}

	public static void scrollToEndOfDetailsPanel(SeleniumBrowser Browser) {

		Browser.scrollToEle(I_DHL_Details.pnl_CustomFields);
	}

	public static void declineWarning(SeleniumBrowser Browser) {

		WebElement wElement = Browser.captureInterface(I_DHL_Home.btn_DeclineAllWarning);
		if (wElement != null) {
			// Browser.waitForElementVisible(I_DHL_Details.btn_DeclineWwarning);
			// Browser.click(I_DHL_Details.btn_DeclineWwarning);

			Browser.clickJavascript(I_DHL_Home.btn_DeclineAllWarning);
		}
	}
	
	public static String getRandomCode () {
		return Functions.randomNumberString(15);
	}

	/**
	 * Open incident
	 * 
	 * @param Browser
	 * @param ReferId
	 */
	public static void openIncident(SeleniumBrowser Browser, String ReferId, TCResult pResult) {

		String temp_r = "//app-incident-list//tbody/tr/td/span[contains(text(),'" + ReferId + "')]";

		WebElement IncidentEle = Browser.Driver.findElement(By.xpath(temp_r));
		if (IncidentEle == null) {
			pResult.SetResult(false);
			pResult.SetMessage("Incident does not exist");
		} else {
			IncidentEle.click();
			Actions action = new Actions(Browser.Driver);
			action.moveToElement(IncidentEle).doubleClick().perform();
		}
		Functions.waitForSeconds(1);

	}

	public static String openLatestIncident(SeleniumBrowser Browser) {

		String temp_r = "//app-incident-list//table/tbody/tr[1]/td[2]";

		WebElement IncidentEle = Browser.Driver.findElement(By.xpath(temp_r));
		String incidentId = IncidentEle.getText();
		// IncidentEle.click();
		Actions action = new Actions(Browser.Driver);
		action.moveToElement(IncidentEle).doubleClick().perform();
		Functions.waitForSeconds(2);

		return incidentId;
	}

	public static void enableEditIncident(SeleniumBrowser Browser) {

		WebElement stateField = Browser.captureInterface(I_DHL_Details.txt_Status);
		if (stateField.isEnabled() == false) {
			addMeAssignee(Browser);
		}

	}

	public static void fillGeneralInfo(SeleniumBrowser Browser) {

		selectRandomAssignee(Browser);
		selectRandomIncidentType(Browser);
		// Browser.click(I_DHL_Details.btn_pickerDate);
		scrollToEndOfDetailsPanel(Browser);
		selectMap(Browser);
		// Browser.enter(I_DHL_Details.txt_incidentCity, Incident.city);
		// Browser.enter(I_DHL_Details.txt_incidentzipCode, Incident.zipCode);

	}

	public static void selectIncident(SeleniumBrowser Browser, String incidentCode) {
		WebElement delectedIncidentEle = Browser.Driver
				.findElement(By.xpath("//app-incident-list//table/tbody/tr/td/span[text()='" + incidentCode + "']/.."));
		if (delectedIncidentEle != null)
			delectedIncidentEle.click();
	}

	public static String selectRandomIncident(SeleniumBrowser Browser, boolean isNotDeteting) {
		String incidentCode = "";
		List<WebElement> l = Browser.Driver.findElements(By.xpath("//app-incident-list//table/tbody/tr"));
		int count = l.size();
		int selectIndex = 0;
		boolean isSelect = false;

		if (isNotDeteting == true) {
			do {
				selectIndex = Functions.randomInterger(0, count - 1);
				if (!l.get(selectIndex).getAttribute("class").contains("delete")) {
					l.get(selectIndex).click();
					Browser.Wait.until(ExpectedConditions.attributeContains(l.get(selectIndex), "class", "active"));
					isSelect = true;

				}
			} while (isSelect == false);
		} else {
			selectIndex = Functions.randomInterger(0, count - 1);
			l.get(selectIndex).click();
			Browser.Wait.until(ExpectedConditions.attributeContains(l.get(selectIndex), "class", "active"));

		}

		incidentCode = l.get(selectIndex).findElement(By.xpath("td[2]")).getText().toString();

		return incidentCode;
	}

	public static void addMeAssignee(SeleniumBrowser Browser) {
		String assignee = "-- me --";

		Browser.click(I_DHL_Details.txt_Assignee);
//		Browser.waitForElementVisible(I_Common.div_generalDropdown);
//		Browser.selectDropdownByText(I_Common.div_generalDropdown, assignee);

		Browser.waitForElementVisible(I_Common.div_generalDropdown);
		WebElement ddList = Browser.captureInterface(I_Common.div_generalDropdown);

		WebElement item = ddList.findElement(By.xpath("div[1]"));
		String name = item.getText();
		Browser.scrollToEle(item);
		item.click();
		Functions.waitForSeconds(0.5);

		saveIncident(Browser);

	}

	public static String selectRandomAssignee(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Details.txt_Assignee);
		Browser.waitForElementVisible(I_DHL_Details.ddl_Assignee);
		String assignee = Browser.selectRandomDropdown(Browser, I_DHL_Details.ddl_Assignee);
		// Browser.waitForElementNotVisible(I_DHL_Details.ddl_Officer);

		return assignee;
	}

	public static String selectRandomAssigneeNotMe(SeleniumBrowser Browser) {
		int n = 0;
		String assignee = "";
		String value = "";
		do {
			Browser.click(I_DHL_Details.txt_Assignee);
			Browser.waitForElementVisible(I_DHL_Details.ddl_Assignee);
			do {
				assignee = Browser.selectRandomDropdown(Browser, I_DHL_Details.ddl_Assignee);
				n++;
			} while ((n < 3 && Browser.captureInterface(I_DHL_Details.ddl_Assignee) != null));

			n = 0;
			value = Browser.captureInterface(I_DHL_Details.txt_Assignee).getAttribute("value").toString();
		} while (value.contains("me"));

		return assignee;
	}

	public static void selectCoworkerAsMe(SeleniumBrowser Browser) {
		String assignee = "-- me --";

		Browser.click(I_DHL_Details.txt_Coworker);
//		Browser.waitForElementVisible(I_Common.div_generalDropdown);
//		Browser.selectDropdownByText(I_Common.div_generalDropdown, assignee);

		Browser.waitForElementVisible(I_Common.div_generalDropdown);
		WebElement ddList = Browser.captureInterface(I_Common.div_generalDropdown);

		WebElement item = ddList.findElement(By.xpath("div[1]"));
		String name = item.getText();
		Browser.scrollToEle(item);
		item.click();
		Functions.waitForSeconds(0.5);

	}

	public static String selectRandomCoworker(SeleniumBrowser Browser) {
		boolean s = false;
		String coworker = "";
		int n = 0;

		Browser.setTimeOut(Browser, 4);
		do {
			Browser.click(I_DHL_Details.txt_Coworker);
			Browser.waitForElementVisible(I_DHL_Details.ddl_Coworker);
			coworker = Browser.selectRandomDropdown(Browser, I_DHL_Details.ddl_Coworker);
			Functions.waitForSeconds(1);
			n++;
		} while (n < 4 && Browser.captureInterface(I_DHL_Details.ddl_Coworker) != null);

		Browser.resetTimeOut(Browser);
		return coworker;
	}

	public static List<String> selectRandomMultiCoworkers(SeleniumBrowser Browser) {

		int n = Functions.randomInterger(1, 3);
		List<String> coworkerList = new ArrayList<String>();
		for (int i = 0; i < n; i++) {
			coworkerList.add(selectRandomCoworker(Browser));
		}

		return coworkerList;
	}

	public static void selectStatus(SeleniumBrowser Browser, String status) {
		Browser.click(I_DHL_Details.txt_Status);
		Browser.waitForElementVisible(I_Common.div_generalDropdown);
		Browser.selectDropdownByText(I_Common.div_generalDropdown, status);
		// Browser.waitForElementNotVisible(I_Common.div_generalDropdown);
	}

	public static String selectRandomStatus(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Details.txt_Status);
		Browser.waitForElementVisible(I_Common.div_generalDropdown);
		String status = Browser.selectRandomDropdown(Browser, I_Common.div_generalDropdown);
		return status;
	}

	public static String changeToAnotherState(SeleniumBrowser Browser) {
		String status = DHLValueList.firstState;

		// -- get current status ---

		// String currentStatus =
		// Browser.captureInterface(I_DHL_Details.txt_Status).getAttribute("value").toString();

		List<DetailField> generalInfo = F_DHLInbox.getGeneralInfo(Browser);
		String currentStatus = getValueByKey(generalInfo, "Status");

		System.out.print("current state: " + currentStatus);

		do {
			status = F_DHLInbox.selectRandomStatus(Browser);
		} while (currentStatus != "" && currentStatus.contains(status.trim()));

		return status;
	}

	public static String selectRandomIncidentType(SeleniumBrowser Browser) {
		String type = "";
		Browser.setTimeOut(Browser, 3);
		Browser.click(I_DHL_Details.txt_IncidentType);
		Browser.waitForElementVisible(I_DHL_Details.div_IncidentType);
		do {
			type = Browser.selectRandomDropdown(Browser, I_DHL_Details.div_IncidentType);
		} while (type.contains("Uncategorized") || Browser.captureInterface(I_DHL_Details.div_IncidentType) != null);

		Browser.resetTimeOut(Browser);
		return type;
	}

	public static void fillStreetAddress(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Details.txt_incidentAddr, pValue);
	}

	public static void fillCity(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Details.txt_incidentCity, pValue);
	}

	public static void fillPostCode(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Details.txt_incidentzipCode, pValue);
	}

	public static void selectMap(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Details.btn_zoomOutmap);
		Functions.waitForSeconds(1);
		Browser.click(I_DHL_Details.btn_zoomOutmap);
		Functions.waitForSeconds(1);
		Browser.click(I_DHL_Details.btn_selMapIcon);

		// selectMapArea(Browser, I_DHL_Details.btn_selMapIcon);
		selectMapArea(Browser, I_DHL_Details.incident_maps);

		Functions.waitForSeconds(2);
	}

	public static void selectMapArea(SeleniumBrowser Browser, By locator) {
		try {

			Actions action = new Actions(Browser.Driver);
			WebElement element = Browser.captureInterface(locator);

			action.moveToElement(element, Functions.randomInterger(0, 200), Functions.randomInterger(0, 200));

			// action.moveByOffset(900, 600);

			// action.wait(3);
			action.click();
			action.build().perform();

		} catch (Exception e) {

		}
	}

	public static void fillCustomFields(SeleniumBrowser Browser) {

		List<WebElement> inputList = Browser.Driver
				.findElements(By.xpath("//fieldset/legend[text()='Custom fields']/../../div//input"));

		for (WebElement input : inputList) {

			// WebElement input = cEle.(i).findElement(By.xpath("//input"));

			if (input.getAttribute("type").contains("number")) {
				Browser.scrollToEle(input);
				input.sendKeys(Functions.randomNumberString(10));
			}

			if (input.getAttribute("type") == null && input.getAttribute("aria-haspopup") == null) {
				Browser.scrollToEle(input);
				input.sendKeys(Functions.randomText(15));
			}

			if (input.getAttribute("type") == null && input.getAttribute("aria-haspopup") != null) {

				if (input.getAttribute("options") != ""
						&& input.getAttribute("options").contains("dateOnlyPickerOptions")) {

					Browser.scrollToEle(input);
					input.sendKeys(Functions.getCurrentDate());

				} else {

					Browser.scrollToEle(input);
					input.sendKeys(Functions.getCurrentDateTime());
				}
			}
		}
	}

	public static void fillCustomField(SeleniumBrowser Browser, String fieldName, String pValue) {
		By txt_customfield = By
				.xpath("//div/app-dynamic-form-control/div/label[contains(., '" + fieldName + "')]/../div//input");
		Browser.enter(txt_customfield, pValue);
	}

	public static void clearCustomField(SeleniumBrowser Browser, String fieldName) {
		By txt_customfield = By
				.xpath("//div/app-dynamic-form-control/div/label[contains(., '" + fieldName + "')]/../div//input");
		Browser.clear(txt_customfield);
	}

	public static List<DetailField> getGeneralInfo(SeleniumBrowser Browser) {
		List<DetailField> generalInfoList = new ArrayList<DetailField>();

		String fieldname = "";
		String value = "";
		By pvalue = null;
		Browser.setTimeOut(Browser, 2);
		for (int n = 0; n < 5; n++) {
			By plabel = By.xpath("//app-container/div/div[2]/div/div/div[3]/div/form/div[" + (n + 1) + "]/label");

			fieldname = Browser.captureInterface(plabel).getText().toString();

			if (Browser.captureInterface(By.xpath("//app-container/div/div[2]/div/div/div[3]/div/form/div[" + (n + 1)
					+ "]/label/../div//ng-select")) != null) {

				pvalue = By
						.xpath("//app-container/div/div[2]/div/div/div[3]/div/form/div[" + (n + 1) + "]/label/../div");
				value = Browser.captureInterface(pvalue).getText().toString();
			} else {
				pvalue = By.xpath(
						"//app-container/div/div[2]/div/div/div[3]/div/form/div[" + (n + 1) + "]/label/../div//input");
				value = Browser.captureInterface(pvalue).getAttribute("value").toString();
			}
			DetailField field = new DetailField();
			field.setfieldName(fieldname);
			field.setfieldValue(value);
			System.out.println(fieldname + " : " + value);
			generalInfoList.add(field);
		}
		Browser.resetTimeOut(Browser);
		return generalInfoList;
	}

	public static List<DetailField> getCustomFieldInfo(SeleniumBrowser Browser) {
		String fieldname = "";
		String value = "";
		List<DetailField> customFieldList = new ArrayList<DetailField>();

		By labelEle = By.xpath("//app-dynamic-form-control/div/label");
		int noOfFields = Browser.Driver.findElements(labelEle).size();
		for (int n = 0; n < noOfFields; n++) {
			By plabel = By.xpath("//form/div[" + (n + 1) + "]/app-dynamic-form-control/div/label");
			By pvalue = By.xpath(
					"//form/div[" + (n + 1) + "]/app-dynamic-form-control/div/label/..//div/div/div/child::*[1]");

			
			
			fieldname = Browser.captureInterface(plabel).getText();
			
			WebElement valueField = Browser.captureInterface(pvalue);
			if (valueField != null)
			{
				// By pvalue = By.xpath("//form/div[" + (n + 1) +
				// "]/app-dynamic-form-control/div/label/../div//input");
				Browser.scrollToEle(plabel);
				
				value = valueField.getAttribute("value");

			} else {

				By checkBoxValue = By.xpath("//form/div[" + (n + 1) + "]/app-dynamic-form-control/div/label/..//span");
				if (Browser.captureInterface(checkBoxValue) == null) {
					value = "";
				} else {
					if (Browser.captureInterface(checkBoxValue).isSelected() == true)
						value = "true";
					else
						value = "false";
				}
			}

			DetailField customfield = new DetailField();
			customfield.setfieldName(fieldname);
			customfield.setfieldValue(value);

			System.out.println(fieldname + " : " + value);
			customFieldList.add(customfield);
		}
		return customFieldList;
	}

	// ------------ Search ----

	public static void searchByCode(SeleniumBrowser Browser, String pValue) {
		Browser.waitForElementEnabled(I_DHL_Home.txt_incidentCode);
		Browser.enter(I_DHL_Home.txt_incidentCode, pValue);
		Mouse.FreeMouse(Browser);
		Functions.waitForSeconds(5);
	}

	public static void searchByType(SeleniumBrowser Browser, String pValue) {
		Browser.click(I_DHL_Home.txt_incidentType);
		Browser.selectDivDropdownByText(Browser, I_DHL_Home.txt_incidentType, pValue);
	}

	public static void searchByDate(SeleniumBrowser Browser, String pValue) {

	}

	public static void searchByStatus(SeleniumBrowser Browser, String pValue) {

	}

	public static void extendCustomFieldSearch(SeleniumBrowser Browser) {
		Browser.waitForElementVisible(I_DHL_Home.btn_extendCustomFilter);
		Browser.click(I_DHL_Home.btn_extendCustomFilter);

	}

	public static void searchByCustomField(SeleniumBrowser Browser, String fieldName, String pValue) {

		extendCustomFieldSearch(Browser);
		fillCustomField(Browser, fieldName, pValue);

		Mouse.FreeMouse(Browser);
		Functions.waitForSeconds(3);
	}

	public static void searchByLastEventLocation(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Home.txt_lastEventLocation, pValue);
		Mouse.FreeMouse(Browser);
		Functions.waitForSeconds(4);
	}

	public static void searchByLastStation(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Home.txt_lastStation, pValue);
		Mouse.FreeMouse(Browser);
		Functions.waitForSeconds(4);
	}

	public static void searchByAddInfo(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_DHL_Home.txt_addinfor, pValue);
		Mouse.FreeMouse(Browser);
		Functions.waitForSeconds(1);
	}

	// ---------------- Evidence Handling -------------

	public static void openEvidenceTab(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Details.tab_Evidence);
		Functions.waitForSeconds(1);
	}

	public static void clickFormButton(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Details.Evidence.btn_Form);
		Functions.waitForSeconds(1);

	}

	public static int evidenceCount(SeleniumBrowser Browser) {
		return Functions.countRow(Browser, "app-evidence-list");
	}

	public static String inputEvidenceMetadata(SeleniumBrowser Browser) {

		String desc = "";

		desc = Browser.captureInterface(I_DHL_Details.Evidence.txt_Desc).getAttribute("value");

		// Browser.enter(I_DHL_Details.Evidence.txt_Street,
		// Functions.randomText(10));
		// Browser.enter(I_DHL_Details.Evidence.txt_City,
		// Functions.randomText(6));
		// Browser.enter(I_DHL_Details.Evidence.txt_ZipCode,
		// Functions.randomNumberString(6));

		Browser.click(I_DHL_Details.Evidence.dd_Criticality);
		Browser.selectRandomDropdown(Browser, I_DHL_Details.Evidence.div_CriticalityDD);

		// selectLocationForEvidence(Browser);

		// Functions.waitForSeconds(1);
		// Browser.click(I_DHL_Details.Evidence.lbl_mapInfo);
		Functions.waitForSeconds(1);

		Browser.click(I_DHL_Details.Evidence.btn_Save);
		Functions.waitForSeconds(2);

		return desc;
	}

	public static void selectLocationForEvidence(SeleniumBrowser Browser) {

		Browser.waitForElementEnabled(I_DHL_Details.Evidence.btn_zoomOut);
		// Browser.waitAndClick(I_DHL_Details.Evidence.btn_zoomOut);
		Browser.clickJavascript(I_DHL_Details.Evidence.btn_zoomOut);
		// Browser.click(I_DHL_Details.Evidence.btn_zoomOut);
		Functions.waitForSeconds(1);
		Browser.clickJavascript(I_DHL_Details.Evidence.btn_zoomOut);
		// Browser.click(I_DHL_Details.Evidence.btn_zoomOut);
		// Functions.waitForSeconds(1);
		// Browser.click(I_DHL_Details.Evidence.btn_zoomOut);
		Browser.click(I_DHL_Details.Evidence.btn_marker);

		// Browser.click(I_DHL_Details.Evidence.lbl_mapInfo);
		// selectMapArea(Browser, I_DHL_Details.Evidence.lbl_mapInfo);
		Functions.waitForSeconds(1);
		int count = 0;
		do {

			Actions action = new Actions(Browser.Driver);
			action.moveToElement(Browser.captureInterface(I_DHL_Details.Evidence.btn_marker),
					Functions.randomInterger(30, 200), Functions.randomInterger(-100, 100));
			action.click();
			action.build().perform();
			count++;
		} while (!Browser.captureInterface(I_DHL_Details.Evidence.txt_Coordinates).getAttribute("class")
				.contains("ng-not-empty") && count < 4);

	}

	public static void selectRandomCritical(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Details.Evidence.dd_Criticality);
		F_AIMS.selectDropDown_AIMS(Browser, I_DHL_Details.Evidence.div_CriticalityDD);
	}

	public static void clickSaveEvidence(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Details.Evidence.btn_Save);
		Functions.waitForSeconds(3);
	}

	public static List<String> uploadRandomEvidence(SeleniumBrowser Browser, int numOffiles) {

		String folderPath = Constants.UploadFilePath;
		List<String> evidenceList = new ArrayList<>();
		int n = 0;
		n = numOffiles;

		File[] listFile = Functions.getListOfFile(folderPath);

		for (int i = 0; i < n; i++) {
			int r = Functions.randomInterger(1, listFile.length - 1);

			if (listFile[r].isFile()) {
				uploadAEvidence(Browser, listFile[r].getName());
				evidenceList.add(listFile[r].getName());
				inputEvidenceMetadata(Browser);
			}
		}
		return evidenceList;
	}

	/**
	 * Update a file to evidence
	 * 
	 * @param Browser
	 * @param filename
	 */
	public static void uploadAEvidence(SeleniumBrowser Browser, String filename) {

		String file_path = Constants.UploadFilePath;

		Browser.click(I_DHL_Details.Evidence.btn_File);

		Functions.uploadfile(file_path, filename);

	}

	/**
	 * Select an Evidence for Exporting
	 * 
	 * @param Browser
	 * @param incidentName
	 */
	public static void selectEvidence(SeleniumBrowser Browser, String incidentName, TCResult result) {

		String temp_r = "//app-evidence-list//table/tbody/tr/td[contains(text(),'" + incidentName + "')]";

		WebElement IncidentEle = Browser.Driver.findElement(By.xpath(temp_r));

		if (IncidentEle != null)
			IncidentEle.click();
		else {
			result.Result = false;
			result.Message = "The evidence " + incidentName + " does not exist!";
		}
	}

	/**
	 * Select random Evidence for Exporting
	 * 
	 * @param Browser
	 */
	public static String selectRandomEvidence(SeleniumBrowser Browser) {

		List<String> row = new ArrayList<>();
		int r = Functions.countRow(Browser);
		do {
			row = Functions.GetRowData(Browser, Functions.randomInterger(1, r), "app-evidence-list");
		} while (row.get(2).contains(":"));
		String temp_r = "//app-evidence-list//table/tbody/tr/td[contains(text(),'" + row.get(2) + "')]";
		WebElement IncidentEle = Browser.Driver.findElement(By.xpath(temp_r));

		IncidentEle.click();
		return row.get(2);
	}

	public static String updateFileAndSelect(SeleniumBrowser Browser, TCResult pResult) {
		List<String> uploadList = F_AIMS.uploadRandomEvidence(Browser, 3);
		String filename = "";
		filename = uploadList.get(Functions.randomInterger(0, uploadList.size() - 1));
		F_Navigation.RefreshPage(Browser);
		Browser.click(I_DHL_Details.tab_Evidence);
		F_AIMS.selectEvidence(Browser, filename, pResult);
		return filename;
	}

	public static String uploadIfEmptyAndSelect(SeleniumBrowser Browser, TCResult pResult) {
		String filename = "";
		int r = Functions.countRow(Browser);
		if (r <= 0) {
			List<String> uploadList = F_AIMS.uploadRandomEvidence(Browser, 1);

			filename = uploadList.get(Functions.randomInterger(0, uploadList.size() - 1));

			F_Navigation.RefreshPage(Browser);
			Browser.click(I_DHL_Details.tab_Evidence);
			F_AIMS.selectEvidence(Browser, filename, pResult);
		} else
			filename = F_AIMS.selectRandomEvidence(Browser);

		if (filename == "") {
			Browser.takeScreenshot("Homepage", "Verify_Delete_an_evidence", "Verify files selected or not");
			pResult.SetResult(false);
			pResult.SetMessage("Select Evident failed");
		}

		return filename;
	}

	public static void clickExport(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Details.Evidence.btn_Export);
		Functions.waitForSeconds(1);
		// Browser.click(I_DHL_Details.Evidence.btn_Exportdialog);
		Browser.clickJavascript(I_DHL_Details.Evidence.btn_Exportdialog);
		Functions.waitForSeconds(1);
	}

	public static void viewExportLog(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Details.Evidence.btn_ExportLog);
		Functions.waitForSeconds(1);
	}

	public static void viewEvidence(SeleniumBrowser Browser, String name, TCResult pResult) {

		selectEvidence(Browser, name, pResult);

		if (pResult.Result == true)
			Browser.click(I_DHL_Details.Evidence.btn_View);
	}

	public static void selectAllEvidence(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Details.Evidence.btn_SelectAll);
	}

	public static List<String> getEvidenceList(SeleniumBrowser Browser) {
		return Functions.getColumnData(Browser, 3, "app-evidence-list");
	}

	public static O_ExportLog getExLog(SeleniumBrowser Browser) {

		O_ExportLog elog = new O_ExportLog();

		return elog;
	}

	public static int getNumberOfEvidence(SeleniumBrowser Browser) {

		// String originalEvidencetab =
		// Browser.captureInterface(I_DHL_Details.tab_Evidence).getText();
		int itemCount = 0;
		String evidentTable = "//app-evidence-list//table/tbody/tr";
		List<WebElement> IncidentEle = Browser.Driver.findElements(By.xpath(evidentTable));
		itemCount = IncidentEle.size();
		return itemCount;
	}

	public static void captureImage(SeleniumBrowser Browser) {
		// Mouse.NavigateAndClick(Browser,
		// I_DHL_Details.Evidence.btn_PlayPause);
		clickPlayPause(Browser);
		// Mouse.NavigateAndClick(Browser,
		// I_DHL_Details.Evidence.btn_Capture);
		Functions.waitForSeconds(4); // wait for file updated success
		clickSnapshoot(Browser);
		clickPlayPause(Browser);
	}

	public static void clickPlayPause(SeleniumBrowser Browser) {
		Mouse.MoveMouseToElement(Browser, I_DHL_Details.Evidence.frm_Video);
		Browser.click(I_CP_Contraventions.btn_PlayPause);
	}

	public static void clickSnapshoot(SeleniumBrowser Browser) {
		Mouse.MoveMouseToElement(Browser, I_DHL_Details.Evidence.frm_Video);
		Browser.click(I_CP_Contraventions.btn_Capture);
	}

	public static void playAndCaptureVideo(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Details.Evidence.btn_PlayPause);
		Functions.waitForSeconds(5);
		Mouse.MoveMouseToElement(Browser, I_DHL_Details.Evidence.frm_Video);
		// Mouse.NavigateAndClick(Browser,
		// I_DHL_Details.Evidence.btn_PlayPause);
		Browser.click(I_DHL_Details.Evidence.btn_PlayPause);
		// Mouse.NavigateAndClick(Browser,
		// I_DHL_Details.Evidence.btn_Capture);
		Browser.click(I_DHL_Details.Evidence.btn_Capture);
	}

	public static void deleteEvidence(SeleniumBrowser Browser, String filename, String reason, TCResult Result) {
		Browser.click(I_DHL_Details.tab_Evidence);
		F_AIMS.selectEvidence(Browser, filename, Result);
		Functions.waitForSeconds(1);
		Browser.click(I_DHL_Details.Evidence.btn_Delete);
		Browser.enter(I_DHL_Details.Evidence.txt_deleteReason, reason);
		Browser.click(I_DHL_Details.Evidence.btn_OkDelete);
		Functions.waitForSeconds(1);
		Browser.click(I_DHL_Details.Evidence.btn_confirmdelete);
		Functions.waitForSeconds(2);
	}

	public static void clickDeleteEvidence(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Details.Evidence.btn_Delete);
		Browser.enter(I_DHL_Details.Evidence.txt_deleteReason, "text reason");
		Browser.click(I_DHL_Details.Evidence.btn_OkDelete);
		Functions.waitForSeconds(1);
		Browser.click(I_DHL_Details.Evidence.btn_confirmdelete);
	}

	public static void searchAndSelectRandomAddress(SeleniumBrowser Browser, TCResult Result) {
		Browser.enter(I_DHL_Details.Evidence.txt_Street, ValueList.MapSearchKeyword);
		Browser.click(I_DHL_Details.Evidence.btn_lookup);

		Browser.waitForElementVisible(I_DHL_Details.Evidence.dlg_Addresslist);
		F_GeneralVerification.verifyElementExist(Browser, "Address list", I_DHL_Details.Evidence.dlg_Addresslist,
				Result);

		F_GeneralVerification.verifyElementNotEnabled(Browser, "OK button of Address stress dialog",
				I_DHL_Details.Evidence.btn_AddressSubmit, Result);
		Browser.selectRandomDropdown(Browser, I_DHL_Details.Evidence.ul_Addresslist, "li");

		Browser.click(I_DHL_Details.Evidence.btn_AddressSubmit);
	}

	public static String createIncident(Long timestamp, boolean isAutoGenerateID) {
		// String json = getParcelTrackingMetaMsg(timestamp);

		String incidentCode = Functions.randomNumberString(20);
		String json = getParcelTrackingMetaJson(incidentCode, timestamp);

		sendParcelTrackingMetaEvent(json);
		if (isAutoGenerateID == true) {
			return null;
		} else
			return incidentCode;
	}

	public static String createIncidentWithRandomAddInfo(Long timestamp, String lastEventLocation,
			boolean isAutoGenerateID) {
		String json = "";
		String incidentCode = Functions.randomNumberString(20);

		json = "{\r\n" + "	\"shipmentId\": \"" + incidentCode + "\",\r\n" + "	\"description\": \"Testing\",\r\n"
				+ "	\"vip\": \"0\",\r\n" + "	\"vipDischarge\": \"0\",\r\n" + "	\"COD\": 434.2,\r\n"
				+ "	\"transportInsurance\": null,\r\n" + "	\"senderCountry\": \"DE\",\r\n"
				+ "	\"senderPostCode\": \"12163\",\r\n" + "	\"senderCity\": \"Berlin\",\r\n"
				+ "	\"senderStreet\": \"Schloßstr.\",\r\n" + "	\"senderHouseNumber\": \"110\",\r\n"
				+ "	\"sender\": \"Care Concept Fachkosmetik\",\r\n" + "	\"EKP\": 5062113638,\r\n"
				+ "	\"customer\": \"Care Concept Fachkosmetik\",\r\n" + "	\"receiverCountry\": \"DE\",\r\n"
				+ "	\"receiverPostCode\": \"21029\",\r\n" + "	\"receiverCity\": \"Hamburg\",\r\n"
				+ "	\"receiverStreet\": \"Mohnhof\",\r\n" + "	\"receiverHouseNumber\": \"8\",\r\n"
				+ "	\"receiver\": \"Beauty Lounge Bergedorf Svetlana Sc\",\r\n" + "	\"dataOriginId\": 3,\r\n"
				+ "	\"dataOrigin\": \"PZ Eingang\",\r\n" + "	\"postCode\": \"21029\",\r\n"
				+ "	\"streetNumber\": 76,\r\n" + "	\"houseNumber\": 8,\r\n"
				+ "	\"parcelLocation\": \"21.1.1.PZ\",\r\n" + "	\"productCode\": 1,\r\n"
				+ "	\"shiftDate\": \"2019-05-12\",\r\n" + "	\"maxDeliveryDate\": \"2019-05-15\",\r\n"
				+ "	\"lastStation\": 142,\r\n" + "	\"scanStation\": \"2\",\r\n"
				+ "	\"sortingDestination\": \"21.10.007.RE\",\r\n" + "	\"deliveryMethod\": \"RE\",\r\n"
				+ "	\"locationCode\": \"21.10.1.ZBPZ-Hamburg (Allermöhe)\",\r\n"
				+ "	\"branch\": \"7320 Hamburg\",\r\n" + "	\"rgb\": \"Nord\",\r\n" + "	\"height\": \"246\",\r\n"
				+ "	\"length\": \"356\",\r\n" + "	\"width\": \"256\",\r\n" + "	\"PANDate\": \"2019-05-12\",\r\n"
				+ "	\"arrivedWeight\": \"2.000\",\r\n" + "	\"dataSourceIdLastEvent\": 238,\r\n"
				+ "	\"dataSourceDescLastEvent\": \"Überweisungsauftrag CODO\",\r\n" + "	\"typeLastEvent\": \"OK\",\r\n"
				+ "	\"typeDescriptionLastEvent\": null,\r\n" + "	\"locationCodeLastEvent\": " + lastEventLocation
				+ ",\r\n" + "	\"criticality\": 80,\r\n" + "	\"timeStampMillis\": " + timestamp + ",\r\n"
				+ "	\"timeStampLastEvent\": " + timestamp + ",\r\n" + "	\"timeStampEvent\": " + timestamp + "}\r\n"
				+ "";

		sendParcelTrackingMetaEvent(json);
		if (isAutoGenerateID == true) {
			return null;
		} else
			return incidentCode;
	}

	public static void sendMsgInMQTracer(String json) {

		String btn_Simulator = "Imgs/Simulator.png";
		String txt_Message = "Imgs/DHLInputTxt.png";
		String btn_Send = "Imgs/btn_SendMessage.png";
		String btn_Minimize = "Imgs/Minimize.png";
		Functions.clickBySikuli(btn_Simulator);

		Functions.pasteValueBySikuli(txt_Message, json);
		// Functions.pasteValueBySikuli(json, 940, 580);
		Functions.clickBySikuli(btn_Send);
		// Functions.clickBySikuli(btn_Minimize);
		Functions.clickBySikuli(btn_Simulator);
	}

	public static void sendParcelTrackingMetaEvent(String json) {
		String btn_Simulator = "Imgs/Simulator.png";
		String txt_Message = "Imgs/txt_EditjsonMsg_inEventSimu.png";
		String tab_parcelTrackingMetaEventTab = "Imgs/tab_parcelTrackingMetaEventTab.png";
		String btn_SaveEditjson = "Imgs/Save.png";
		String btn_editJson = "Imgs/btn_EditJson.png";
		String btn_RunParcelTrackingMetaEvent = "Imgs/btn_RunMetaEvent.png";
		String btn_SaveEvent = "Imgs/btn_SaveEvents.png";
		String btn_Minimize = "Imgs/Minimize.png";

		Functions.clickBySikuli(btn_Simulator);
		Functions.clickBySikuli(tab_parcelTrackingMetaEventTab);
		Functions.clickBySikuli(btn_editJson);
		Functions.pasteValueBySikuli(txt_Message, json);
		Functions.clickBySikuli(btn_SaveEditjson);
		Functions.clickBySikuli(btn_SaveEvent);
		// Functions.pasteValueBySikuli(json, 940, 580);
		Functions.clickBySikuli(btn_RunParcelTrackingMetaEvent);
		// Functions.clickBySikuli(btn_Minimize);

		Functions.clickBySikuli(btn_Simulator);
	}

	public static void sendParcelTrackingEvent(String json) {

	}

	public static String getParcelTrackingMetaMsg(Long timestamp) {

		// boolean isAutoGenerate
		String json = "";
		String randomNo = Functions.randomNumberString(20);
		String incidentCode = randomNo; // + timestamp;
		// System.out.println(timestamp.toString());
		// String uuid = Functions.randomUUID().toUpperCase();
		json = getParcelTrackingMetaJson(incidentCode, timestamp);

		return json;

	}

	public static String getJson_old(String id, Long timestamp) {

		String json = "";

		json = "{\r\n" + "	\"aggregatedEventDTO\": {\r\n" + "		\"aggregatedEvent\": {\r\n"
				+ "			\"criticality\": 50,\r\n" + "			\"timeStampMillis\": " + timestamp + "\r\n"
				+ "		}\r\n" + "	},\r\n" + "	\"incident\": {\r\n" + "		\"incidentNbo\": \"" + id + "\",\r\n"
				+ "		\"classType\": \"com.dallmeier.asa.aims.domain.Incident\",\r\n" + "		\"customFields\": {\r\n"
				+ "			\"locationCodeLastEvent\": \"85.11.704.RE\",\r\n" + "			\"additionalInfo\": {\r\n"
				+ "				\"description\": \"PZE-Rundlaeufer\",\r\n" + "				\"vip\": 0,\r\n"
				+ "				\"vipDischarge\": 0,\r\n" + "				\"transportInsurance\": null,\r\n"
				+ "				\"senderCountry\": \"DE\",\r\n" + "				\"senderPostCode\": \"64546\",\r\n"
				+ "				\"senderCity\": \"M?rfelden-Walldorf\",\r\n"
				+ "				\"senderStreet\": \"Hessenring\",\r\n"
				+ "				\"senderHouseNumber\": \"900\",\r\n"
				+ "				\"sender\": \"YunExpress Logistics Co.Ltd\",\r\n"
				+ "				\"customer\": \"Worldtech Logistics GmbH\",\r\n"
				+ "				\"receiverCountry\": \"DE\",\r\n"
				+ "				\"receiverPostCode\": \"85049\",\r\n"
				+ "				\"receiverCity\": \"Ingolstadt\",\r\n"
				+ "				\"receiverStreet\": \"Permoser Str\",\r\n"
				+ "				\"receiverHouseNumber\": \"57\",\r\n"
				+ "				\"receiver\": \"Aylin aliya Aylin aliya\",\r\n"
				+ "				\"postCode\": \"85049\",\r\n" + "				\"streetNumber\": 245,\r\n"
				+ "				\"houseNumber\": 57,\r\n" + "				\"parcelLocation\": \"90.1.1.PZ\",\r\n"
				+ "				\"productCode\": 0,\r\n" + "				\"shiftDate\": \"2019-05-28\",\r\n"
				+ "				\"maxDeliveryDate\": \"2019-05-29\",\r\n" + "				\"lastStation\": 61,\r\n"
				+ "				\"scanStation\": \"2\",\r\n"
				+ "				\"sortingDestination\": \"85.101.055.BEZG\",\r\n"
				+ "				\"deliveryMethod\": \"BEZG\",\r\n"
				+ "				\"locationCode\": \"85.101.1.ZSP-Ingolstadt 1\",\r\n"
				+ "				\"branch\": \"7285 Freising\",\r\n" + "				\"rgb\": \"Sued\",\r\n"
				+ "				\"height\": \"45\",\r\n" + "				\"length\": \"240\",\r\n"
				+ "				\"width\": \"190\",\r\n" + "				\"arrivedWeight\": \"0.281\",\r\n"
				+ "				\"timeStampEvent\": null,\r\n"
				+ "				\"timeStampLastEvent\": 1577932872000,\r\n"
				+ "				\"type\": \"ParcelTrackingMetaEvent\",\r\n" + "				\"COD\": null,\r\n"
				+ "				\"EKP\": 6285072479,\r\n" + "				\"PANDate\": \"2019-04-27\",\r\n"
				+ "				\"dataSourceLastEvent\": {\r\n" + "					\"id\": 5,\r\n"
				+ "					\"description\": \"Zustellung HSC\"\r\n" + "				},\r\n"
				+ "				\"modalityLastEvent\": {\r\n" + "					\"code\": \"05\",\r\n"
				+ "					\"description\": \"Ausgeliefert\"\r\n" + "				},\r\n"
				+ "				\"dataSource\": {\r\n" + "					\"id\": 3,\r\n"
				+ "					\"description\": \"PZ Eingang\"\r\n" + "				}\r\n" + "			}\r\n"
				+ "		},\r\n" + "		\"timeStampFirstObserved\": " + timestamp + "\r\n" + "	}\r\n" + "}\r\n" + "";

		return json;

	}

	public static String getParcelTrackingMetaJson(String id, Long timestamp) {

		String json = "";
		// String randomText = Functions.randomText();
		json = "{\r\n" + "	\"shipmentId\": \"" + id + "\",\r\n" + "	\"description\": \"Testing\",\r\n"
				+ "	\"vip\": \"0\",\r\n" + "	\"vipDischarge\": \"0\",\r\n" + "	\"COD\": 434.2,\r\n"
				+ "	\"transportInsurance\": null,\r\n" + "	\"senderCountry\": \"DE\",\r\n"
				+ "	\"senderPostCode\": \"12163\",\r\n" + "	\"senderCity\": \"Berlin\",\r\n"
				+ "	\"senderStreet\": \"Schloßstr.\",\r\n" + "	\"senderHouseNumber\": \"110\",\r\n"
				+ "	\"sender\": \"Care Concept Fachkosmetik\",\r\n" + "	\"EKP\": 5062113638,\r\n"
				+ "	\"customer\": \"Care Concept Fachkosmetik\",\r\n" + "	\"receiverCountry\": \"DE\",\r\n"
				+ "	\"receiverPostCode\": \"21029\",\r\n" + "	\"receiverCity\": \"Hamburg\",\r\n"
				+ "	\"receiverStreet\": \"Mohnhof\",\r\n" + "	\"receiverHouseNumber\": \"8\",\r\n"
				+ "	\"receiver\": \"Beauty Lounge Bergedorf Svetlana Sc\",\r\n" + "	\"dataOriginId\": 3,\r\n"
				+ "	\"dataOrigin\": \"PZ Eingang\",\r\n" + "	\"postCode\": \"21029\",\r\n"
				+ "	\"streetNumber\": 76,\r\n" + "	\"houseNumber\": 8,\r\n"
				+ "	\"parcelLocation\": \"21.1.1.PZ\",\r\n" + "	\"productCode\": 1,\r\n"
				+ "	\"shiftDate\": \"2019-05-12\",\r\n" + "	\"maxDeliveryDate\": \"2019-05-15\",\r\n"
				+ "	\"lastStation\": 142,\r\n" + "	\"scanStation\": \"2\",\r\n"
				+ "	\"sortingDestination\": \"21.10.007.RE\",\r\n" + "	\"deliveryMethod\": \"RE\",\r\n"
				+ "	\"locationCode\": \"21.10.1.ZBPZ-Hamburg (Allermöhe)\",\r\n"
				+ "	\"branch\": \"7320 Hamburg\",\r\n" + "	\"rgb\": \"Nord\",\r\n" + "	\"height\": \"246\",\r\n"
				+ "	\"length\": \"356\",\r\n" + "	\"width\": \"256\",\r\n" + "	\"PANDate\": \"2019-05-12\",\r\n"
				+ "	\"arrivedWeight\": \"2.000\",\r\n" + "	\"dataSourceIdLastEvent\": 238,\r\n"
				+ "	\"dataSourceDescLastEvent\": \"Überweisungsauftrag CODO\",\r\n" + "	\"typeLastEvent\": \"OK\",\r\n"
				+ "	\"typeDescriptionLastEvent\": null,\r\n" + "	\"locationCodeLastEvent\": \"85.11.704.RE\",\r\n"
				+ "	\"criticality\": 80,\r\n" + "	\"timeStampMillis\": " + timestamp + ",\r\n"
				+ "	\"timeStampLastEvent\": " + timestamp + ",\r\n" + "	\"timeStampEvent\": " + timestamp + "}\r\n"
				+ "";

		return json;

	}

	public static String getJsonForCancel(String id, Long timestamp) {

		String json = "";

		json = "{\r\n" + "	\"incidentNbo\": \"" + id + "\",\r\n" + "	\"reason\": \"Finale Zustellung\",\r\n"
				+ "	\"timeStampMillis\": " + timestamp + "\r\n" + "}";

		return json;
	}

	public static O_DHLIncident getDetails(SeleniumBrowser Browser) {
		O_DHLIncident in = new O_DHLIncident();

		List<DetailField> generalInfo = F_DHLInbox.getGeneralInfo(Browser);
		// List<DetailField> customFields = F_DHLDetails.getCustomFieldInfo(Browser);

		in.Status = getValueByKey(generalInfo, "Staus");
		in.Assignee = getValueByKey(generalInfo, "Assignee");
		in.Coworker = getValueByKey(generalInfo, "Co-worker");
		in.Type = getValueByKey(generalInfo, "Type of incident");
		in.Datetime = getValueByKey(generalInfo, "Date, time");

//		in.incidentStreet = getValueByKey(generalInfo, "Street address");
//		in.incidentCity = getValueByKey(generalInfo, "City");
//		in.incidentZip = getValueByKey(generalInfo, "Postcode");
//		in.incidentCoordinates = getValueByKey(generalInfo, "Coordinates");

		return in;
	}

	public static String getValueByKey(List<DetailField> dataList, String key) {
		String value = "";
		for (int i = 0; i < dataList.size(); i++) {
			if (dataList.get(i).getfieldName().contains(key)) {
				value = dataList.get(i).getfieldValue();
				break;
			}
		}
		return value;
	}

	public static void clickConfirmChangeIncidentType(SeleniumBrowser Browser) {
		Browser.click(I_DHL_Details.btn_confirmChangeType);
	}

	public static int getTotalIncidentNo(SeleniumBrowser Browser) {
		int incidentCount = 0;
		String incidentsNo = Browser.captureInterface(I_DHL_Home.lbl_IncidentNo).getText();// getAttribute("value").toString();
		incidentCount = Functions.convertStringToInteger(incidentsNo);
		return incidentCount;
	}

	public static int getTotalNewIncidentNo(SeleniumBrowser Browser) {
		Functions.waitForSeconds(1.5);
		int incidentCount = 0;
		String incidentsNo = Browser.captureInterface(I_DHL_Home.lbl_newIncidentNo).getText();
		incidentCount = Functions.convertStringToInteger(incidentsNo);
		return incidentCount;
	}

	// --- Form ----
	public static boolean isTemplateExist(SeleniumBrowser Browser, String templateName) {
		By templateBy = By.xpath("//app-template-form/div[2]/div/ul[contains(., '" + templateName + "')] ");

		if (Browser.captureInterface(templateBy) != null) {
			return true;
		} else {
			return false;
		}
	}

	public static void selectATemplate(SeleniumBrowser Browser, String templateName) {
		By templateBy = By.xpath("//app-template-form/div[2]/div/ul[contains(., '" + templateName + "')] ");

		WebElement tempEle = Browser.captureInterface(templateBy);
		if (tempEle != null) {
			tempEle.click();
			Functions.waitForSeconds(1);
			clickOkbuttonInFormTemplate(Browser);
		}
	}

	public static void clickOkbuttonInFormTemplate(SeleniumBrowser Browser) {
		By Okbutton = By.xpath("//app-template-form/div[2]/div[2]/button");
		Browser.captureInterface(Okbutton).click();
	}

}
