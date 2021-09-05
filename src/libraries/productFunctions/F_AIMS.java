package libraries.productFunctions;

import java.util.List;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import interfaces.I_AIMS_Home;
import interfaces.I_AIMS_Incident_Details;
import interfaces.I_AIMS_Settings;
import interfaces.I_CP_Common;
import interfaces.I_CP_Contraventions;
import interfaces.I_CP_Settings;
//import executionEngine.RunTestscript;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.ValueList;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Log;
import libraries.generalFunctions.Mouse;
import libraries.objects.O_Evidence;
import libraries.objects.O_ExportLog;
import libraries.verificationFunctions.F_GeneralVerification;

public class F_AIMS {

	
	// ------------------------- Home and Details -------------
	public static void clickAddIncident(SeleniumBrowser Browser) {
		// Browser.clickJavascript(I_AIMS_Home.btn_AddIncident);

		// Browser.click(I_AIMS_Home.btn_AddIncident);
		Browser.click(I_AIMS_Home.btn_addIncidentOnToolbar);
		Functions.waitForSeconds(1);
	}

	public static void saveIncident(SeleniumBrowser Browser) {
		Browser.click(I_AIMS_Incident_Details.btn_Save);
		Functions.waitForSeconds(1);
	}

	public static void clickComplete(SeleniumBrowser Browser) {
		Browser.click(I_AIMS_Incident_Details.btn_Complete);
		Functions.waitForSeconds(1);
	}

	public static void clickReview(SeleniumBrowser Browser) {
		Browser.click(I_AIMS_Incident_Details.btn_Review);
		Functions.waitForSeconds(1);
	}

	public static String selectIncidentType(SeleniumBrowser Browser) {
		String type = "";
		Browser.setTimeOut(Browser, 3);
		Browser.click(I_AIMS_Incident_Details.txt_IncidentType);
		Functions.waitForSeconds(1);
		Browser.waitForElementVisible(I_AIMS_Incident_Details.div_IncidentType);
		do {
			type = F_AIMS.selectDropDown_AIMS(Browser, I_AIMS_Incident_Details.div_IncidentType);
		} while (type.contains("Uncategorized") || Browser.captureInterface(I_AIMS_Incident_Details.div_IncidentType) != null);
		
		Browser.resetTimeOut(Browser);
		return type;
	}

	public static void fillBasicInfo(SeleniumBrowser Browser) {

		Browser.click(I_AIMS_Incident_Details.div_Officer);
		Browser.waitForElementVisible(I_AIMS_Incident_Details.ddl_Officer);
		selectDropDown_AIMS(Browser, I_AIMS_Incident_Details.ddl_Officer);
		Browser.waitForElementNotVisible(I_AIMS_Incident_Details.ddl_Officer);

		Browser.click(I_AIMS_Incident_Details.txt_IncidentType);
		// Browser.selectDropdownByText(I_AIMS_Incident_Details.ddl_IncidentType,
		// Incident.IncidentType);
		Browser.waitForElementVisible(I_AIMS_Incident_Details.div_IncidentType);

		selectIncidentTypeDropdown(Browser, I_AIMS_Incident_Details.div_IncidentType);

		// Browser.click(I_AIMS_Incident_Details.btn_pickerDate);

		Browser.enter(I_AIMS_Incident_Details.txt_desc, Functions.randomText(20));

		// Caller
		Browser.enter(I_AIMS_Incident_Details.txt_firstName, Functions.randomText(6));
		Browser.enter(I_AIMS_Incident_Details.txt_lastName, Functions.randomText(6));

		// Browser.enter(I_AIMS_Incident_Details.txt_callCity, Incident.city);
		// Browser.enter(I_AIMS_Incident_Details.txt_callzipCode, Incident.zipCode);
		Browser.enter(I_AIMS_Incident_Details.txt_callPhone, Functions.randomNumberString(10));

		scrollToEndOfDetailsPanel(Browser);

		// Incident Location
		selectMap(Browser);
		// Browser.enter(I_AIMS_Incident_Details.txt_incidentCity, Incident.city);
		// Browser.enter(I_AIMS_Incident_Details.txt_incidentzipCode, Incident.zipCode);

		fillCustomFields(Browser);
	}

	public static void selectMap(SeleniumBrowser Browser) {
		Browser.click(I_AIMS_Incident_Details.btn_zoomOutmap);
		Functions.waitForSeconds(1);
		Browser.click(I_AIMS_Incident_Details.btn_zoomOutmap);
		Functions.waitForSeconds(1);
		Browser.click(I_AIMS_Incident_Details.btn_selMapIcon);

		// selectMapArea(Browser, I_AIMS_Incident_Details.btn_selMapIcon);
		selectMapArea(Browser, I_AIMS_Incident_Details.incident_maps);

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

	public static void closeIncident(SeleniumBrowser Browser) {

		F_AIMS.closeCurrentTab(Browser);
		Functions.waitForSeconds(1);

		Browser.setTimeOut(Browser, 2);
		WebElement a = Browser.captureInterface(I_AIMS_Incident_Details.dlg_CloseTab);

		if (a != null && a.isDisplayed()) {
			Browser.click(I_AIMS_Incident_Details.btn_SaveChanges);
			Functions.waitForSeconds(2);
		}

		if (Browser.captureInterface(I_AIMS_Home.btn_CloseCurrentTab) != null)
			F_AIMS.closeCurrentTab(Browser);

		Browser.resetTimeOut(Browser);
	}

	public static void fillCustomFields(SeleniumBrowser Browser) {

		List<WebElement> inputList = Browser.Driver
				.findElements(By.xpath("//fieldset/legend[text()='Custom fields']/../../div//input"));

		for (WebElement input : inputList) {

			// WebElement input = cEle.get(i).findElement(By.xpath("//input"));

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

	public static String updateDescription(SeleniumBrowser Browser) {
		String randomText = Functions.randomText(15) + " update";
		Browser.enter(I_AIMS_Incident_Details.txt_desc, randomText);

		return randomText;
	}

	public static String selectDropDown_AIMS(SeleniumBrowser Browser, By pBy) {
		return Browser.selectRandomDropdown(Browser, pBy);
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

		Browser.scrollToEle(I_AIMS_Incident_Details.pnl_CustomFields);
	}

	public static void declineWarning(SeleniumBrowser Browser) {

		WebElement wElement = Browser.captureInterface(I_AIMS_Incident_Details.btn_DeclineWwarning);
		if (wElement != null) {
			// Browser.waitForElementVisible(I_AIMS_Incident_Details.btn_DeclineWwarning);
			// Browser.click(I_AIMS_Incident_Details.btn_DeclineWwarning);

			Browser.clickJavascript(I_AIMS_Incident_Details.btn_DeclineWwarning);

		}
	}

	public static void addNewIncidentType(SeleniumBrowser Browser, String name, String code) {

		F_Navigation.goToPage(Browser, I_AIMS_Settings.tab_IncidentTypes);
		Browser.click(I_AIMS_Settings.btn_addNewType);
		Browser.enter(I_AIMS_Settings.txt_InputName, name);
		Browser.enter(I_AIMS_Settings.txt_incidentCode, code);
		Browser.click(I_AIMS_Settings.btn_Save);
	}

	public static void importIncidentType(SeleniumBrowser Browser) {
//		String path = "..\\Test Data\\IncidentTypes.csv";
		
		String path = "D:\\QC\\Automation\\Framework\\myFW\\Test Data\\IncidentTypes.csv";
		List<String> list = Functions.ReadCSV_Scanner(path);

		String incident = "";

		F_AIMS.gotoIncidentTypepage(Browser);

		for (int i = 0; i < list.size(); i++) {

			incident = list.get(i);
			if (incident != "") {
				Browser.click(I_AIMS_Settings.btn_addNewType);
				Browser.enter(I_AIMS_Settings.txt_InputName, incident);
				Browser.enter(I_AIMS_Settings.txt_incidentCode, Functions.randomNumberString(5));
				
				Browser.click(I_AIMS_Settings.btn_Save);
				Functions.waitForSeconds(0.5);
				Log.info("Incident type addded success = " + incident);
			}
		}
	}

	/**
	 * Open incident
	 * 
	 * @param Browser
	 * @param ReferId
	 */
	public static void openIncident(SeleniumBrowser Browser, String ReferId) {

		String temp_r = "//table[@id='inbox-list']/tbody/tr/td[contains(text(),'" + ReferId + "')]";

		WebElement IncidentEle = Browser.Driver.findElement(By.xpath(temp_r));

		IncidentEle.click();
		Actions action = new Actions(Browser.Driver);
		action.moveToElement(IncidentEle).doubleClick().perform();

	}

	public static void openLatestIncident(SeleniumBrowser Browser) {

		String temp_r = "//app-incident-list//table/tbody/tr[1]/td[2]";

		WebElement IncidentEle = Browser.Driver.findElement(By.xpath(temp_r));

		// IncidentEle.click();
		Actions action = new Actions(Browser.Driver);
		action.moveToElement(IncidentEle).doubleClick().perform();
		Functions.waitForSeconds(1);
	}

	public static List<String> createIncidentWithMultiType(SeleniumBrowser Browser, int NoOfType) {
		String type = "";
		List<String> incidentList = new ArrayList<>();
		F_AIMS.clickAddIncident(Browser);
		// Browser.clickJavascript(I_AIMS_Incident_Details.txt_Officer);
		Browser.click(I_AIMS_Incident_Details.div_Officer);
		Browser.waitForElementVisible(I_AIMS_Incident_Details.ddl_Officer);

		F_AIMS.selectDropDown_AIMS(Browser, I_AIMS_Incident_Details.ddl_Officer);
		// Browser.waitForElementNotVisible(I_AIMS_Incident_Details.ddl_Officer);

		for (int i = 0; i < NoOfType; i++) {
			type = F_AIMS.selectIncidentType(Browser);
			incidentList.add(type);
		}

		Browser.enter(I_AIMS_Incident_Details.txt_desc, Functions.randomText(20));

		// -- Fill Caller
		Browser.enter(I_AIMS_Incident_Details.txt_firstName, Functions.randomText(6));
		Browser.enter(I_AIMS_Incident_Details.txt_lastName, Functions.randomText(6));

		F_AIMS.scrollToEndOfDetailsPanel(Browser);

		// Incident Location
		F_AIMS.selectMap(Browser);
		// Browser.enter(I_AIMS_Incident_Details.txt_incidentCity, Incident.city);
		// Browser.enter(I_AIMS_Incident_Details.txt_incidentzipCode, Incident.zipCode);
		F_AIMS.saveIncident(Browser);
		return incidentList;
	}

	// ---------------- Evidence Handling -------------

	public static void openEvidenceTab(SeleniumBrowser Browser) {
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);
		Functions.waitForSeconds(1);
	}

	public static int evidenceCount(SeleniumBrowser Browser) {
		return Functions.countRow(Browser, "app-evidence-list");
	}

	public static String inputEvidenceMetadata(SeleniumBrowser Browser) {

		String desc = "";

		desc = Browser.captureInterface(I_AIMS_Incident_Details.Evidence.txt_Desc).getAttribute("value");

		// Browser.enter(I_AIMS_Incident_Details.Evidence.txt_Street,
		// Functions.randomText(10));
		// Browser.enter(I_AIMS_Incident_Details.Evidence.txt_City,
		// Functions.randomText(6));
		// Browser.enter(I_AIMS_Incident_Details.Evidence.txt_ZipCode,
		// Functions.randomNumberString(6));

		Browser.click(I_AIMS_Incident_Details.Evidence.dd_Criticality);
		selectDropDown_AIMS(Browser, I_AIMS_Incident_Details.Evidence.div_CriticalityDD);

		// selectLocationForEvidence(Browser);

		// Functions.waitForSeconds(1);
		// Browser.click(I_AIMS_Incident_Details.Evidence.lbl_mapInfo);
		Functions.waitForSeconds(1);

		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Save);
		Functions.waitForSeconds(2);

		return desc;
	}

	public static void selectLocationForEvidence(SeleniumBrowser Browser) {

		Browser.waitForElementEnabled(I_AIMS_Incident_Details.Evidence.btn_zoomOut);
		// Browser.waitAndClick(I_AIMS_Incident_Details.Evidence.btn_zoomOut);
		Browser.clickJavascript(I_AIMS_Incident_Details.Evidence.btn_zoomOut);
		// Browser.click(I_AIMS_Incident_Details.Evidence.btn_zoomOut);
		Functions.waitForSeconds(1);
		Browser.clickJavascript(I_AIMS_Incident_Details.Evidence.btn_zoomOut);
		// Browser.click(I_AIMS_Incident_Details.Evidence.btn_zoomOut);
		// Functions.waitForSeconds(1);
		// Browser.click(I_AIMS_Incident_Details.Evidence.btn_zoomOut);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_marker);

		// Browser.click(I_AIMS_Incident_Details.Evidence.lbl_mapInfo);
		// selectMapArea(Browser, I_AIMS_Incident_Details.Evidence.lbl_mapInfo);
		Functions.waitForSeconds(1);
		int count = 0;
		do {

			Actions action = new Actions(Browser.Driver);
			action.moveToElement(Browser.captureInterface(I_AIMS_Incident_Details.Evidence.btn_marker),
					Functions.randomInterger(30, 200), Functions.randomInterger(-100, 100));
			action.click();
			action.build().perform();
			count++;
		} while (!Browser.captureInterface(I_AIMS_Incident_Details.Evidence.txt_Coordinates).getAttribute("class")
				.contains("ng-not-empty") && count < 4);

	}

	public static void selectRandomCritical(SeleniumBrowser Browser) {
		Browser.click(I_AIMS_Incident_Details.Evidence.dd_Criticality);
		F_AIMS.selectDropDown_AIMS(Browser, I_AIMS_Incident_Details.Evidence.div_CriticalityDD);
	}

	public static void clickSaveEvidence(SeleniumBrowser Browser) {
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Save);
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

		Browser.click(I_AIMS_Incident_Details.Evidence.btn_File);

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
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);
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
			Browser.click(I_AIMS_Incident_Details.tab_Evidence);
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
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Export);
		Functions.waitForSeconds(1);
		// Browser.click(I_AIMS_Incident_Details.Evidence.btn_Exportdialog);
		Browser.clickJavascript(I_AIMS_Incident_Details.Evidence.btn_Exportdialog);
		Functions.waitForSeconds(1);
	}

	public static void viewExportLog(SeleniumBrowser Browser) {
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_ExportLog);
		Functions.waitForSeconds(1);
	}

	public static void viewEvidence(SeleniumBrowser Browser, String name, TCResult pResult) {

		selectEvidence(Browser, name, pResult);

		if (pResult.Result == true)
			Browser.click(I_AIMS_Incident_Details.Evidence.btn_View);
	}

	public static void selectAllEvidence(SeleniumBrowser Browser) {
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_SelectAll);
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
		// Browser.captureInterface(I_AIMS_Incident_Details.tab_Evidence).getText();
		int itemCount = 0;
		String evidentTable = "//app-evidence-list//table/tbody/tr";
		List<WebElement> IncidentEle = Browser.Driver.findElements(By.xpath(evidentTable));
		itemCount = IncidentEle.size();
		return itemCount;
	}

	public static void captureImage(SeleniumBrowser Browser) {
		// Mouse.NavigateAndClick(Browser,
		// I_AIMS_Incident_Details.Evidence.btn_PlayPause);
		clickPlayPause(Browser);
		// Mouse.NavigateAndClick(Browser,
		// I_AIMS_Incident_Details.Evidence.btn_Capture);
		Functions.waitForSeconds(4); // wait for file updated success
		clickSnapshoot(Browser);
		clickPlayPause(Browser);
	}

	public static void clickPlayPause(SeleniumBrowser Browser) {
		Mouse.MoveMouseToElement(Browser, I_AIMS_Incident_Details.Evidence.frm_Video);
		Browser.click(I_CP_Contraventions.btn_PlayPause);
	}

	public static void clickSnapshoot(SeleniumBrowser Browser) {
		Mouse.MoveMouseToElement(Browser, I_AIMS_Incident_Details.Evidence.frm_Video);
		Browser.click(I_CP_Contraventions.btn_Capture);
	}

	public static void playAndCaptureVideo(SeleniumBrowser Browser) {
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_PlayPause);
		Functions.waitForSeconds(5);
		Mouse.MoveMouseToElement(Browser, I_AIMS_Incident_Details.Evidence.frm_Video);
		// Mouse.NavigateAndClick(Browser,
		// I_AIMS_Incident_Details.Evidence.btn_PlayPause);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_PlayPause);
		// Mouse.NavigateAndClick(Browser,
		// I_AIMS_Incident_Details.Evidence.btn_Capture);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Capture);
	}

	public static void deleteEvidence(SeleniumBrowser Browser, String filename, String reason, TCResult Result) {
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);
		F_AIMS.selectEvidence(Browser, filename, Result);
		Functions.waitForSeconds(1);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Delete);
		Browser.enter(I_AIMS_Incident_Details.Evidence.txt_deleteReason, reason);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_OkDelete);
		Functions.waitForSeconds(1);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_confirmdelete);
		Functions.waitForSeconds(2);
	}

	public static void clickDeleteEvidence(SeleniumBrowser Browser) {
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Delete);
		Browser.enter(I_AIMS_Incident_Details.Evidence.txt_deleteReason, "text reason");
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_OkDelete);
		Functions.waitForSeconds(1);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_confirmdelete);
	}

	public static void searchAndSelectRandomAddress(SeleniumBrowser Browser, TCResult Result) {
		Browser.enter(I_AIMS_Incident_Details.Evidence.txt_Street, ValueList.MapSearchKeyword);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_lookup);

		Browser.waitForElementVisible(I_AIMS_Incident_Details.Evidence.dlg_Addresslist);
		F_GeneralVerification.verifyElementExist(Browser, "Address list",
				I_AIMS_Incident_Details.Evidence.dlg_Addresslist, Result);

		F_GeneralVerification.verifyElementNotEnabled(Browser, "OK button of Address stress dialog",
				I_AIMS_Incident_Details.Evidence.btn_AddressSubmit, Result);
		Browser.selectRandomDropdown(Browser, I_AIMS_Incident_Details.Evidence.ul_Addresslist, "li");

		Browser.click(I_AIMS_Incident_Details.Evidence.btn_AddressSubmit);
	}
	// ------------ SETTINGS ------------------

	// ------- Settings --------

		public static void commonSearch(SeleniumBrowser Browser, String pValue) {
			Browser.enter(I_AIMS_Settings.txt_commonSearch, pValue);
			// Browser.switchParentFrame();
			Mouse.FreeMouse(Browser);
			Functions.waitForSeconds(2);
		}

		public static void gotoAssignmentpage(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_AIMS_Home.tab_Settings);
			Functions.waitForSeconds(0.5);
			F_Navigation.goToPage(Browser, I_AIMS_Settings.tab_incidentTypeAssignment);
		}

		public static void gotoIncidentTypepage(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_AIMS_Home.tab_Settings);
			Browser.click(I_AIMS_Settings.tab_IncidentTypes);
			Functions.waitForSeconds(1);
		}

		public static void closeCurrentTab(SeleniumBrowser Browser) {
			Browser.click(I_AIMS_Home.btn_CloseCurrentTab);
			// Browser.clickJavascript(I_AIMS_Home.btn_CloseCurrentTab);
			Functions.waitForSeconds(1);
		}

		public static void selectToEdit(SeleniumBrowser Browser, String pValue) {
			commonSearch(Browser, pValue);
			Browser.selectRow(1);
		}

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

		public static void clickConfirmDeleteCatalog(SeleniumBrowser Browser) {
			Browser.waitForElementEnabled(I_CP_Common.btn_OkConfirm);
			// Browser.click(I_CP_Common.btn_OkDeleteConfirm);
			Browser.clickJavascript(I_CP_Common.btn_OkDeleteConfirm);
		}

		public static void dragdropType(SeleniumBrowser Browser, String typeName, String catelogName) {
			// By typeEle =
			// By.xpath("//tree-node-wrapper/div/div/tree-node-content/span[text() = \""+
			// typeName + "\"]");

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
	 * 
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
		Browser.click(I_AIMS_Settings.btn_SaveCategory);
		return randomName;
	}

	public static void extendCatItems(SeleniumBrowser Browser, String categoryName) {
		By pbtnExtend = By.xpath("//span[contains(text(),'" + categoryName + "')]/../span[1]/i");
		WebElement btnExtend = Browser.Driver.findElement(pbtnExtend);
		if (btnExtend.getAttribute("class").contains("right"))
			Browser.scrollToEle(btnExtend);
		btnExtend.click();
	}

}
