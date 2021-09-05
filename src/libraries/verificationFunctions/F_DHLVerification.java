package libraries.verificationFunctions;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import interfaces.I_AIMS_Incident_Details;
import interfaces.I_AIMS_Settings;
import interfaces.I_CP_Common;
import interfaces.I_CP_Contraventions;
import interfaces.I_Common;
import interfaces.I_DHL_Details;
import interfaces.I_DHL_Home;
import interfaces.I_DHL_Settings;
import libraries.CPValueList;
import libraries.Constants;
import libraries.DHLValueList;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.generalFunctions.Functions;
import libraries.objects.DetailField;
import libraries.objects.O_DHLIncident;
import libraries.objects.O_Evidence;
import libraries.objects.O_Incident;
import libraries.productFunctions.F_AIMS;
import libraries.productFunctions.F_CPSettings;
import libraries.productFunctions.F_DHLInbox;
import libraries.productFunctions.F_DHLSettings;
import libraries.productFunctions.F_Navigation;

public class F_DHLVerification {

	public static void verifySuccessCreateMsg(SeleniumBrowser Browser, String objectName, TCResult pResult) {
		F_GeneralVerification.verifyNoticationMessage(Browser, I_Common.div_SuccessMessage,
				MessageFormat.format(DHLValueList.Message.CreateSuccess, objectName), pResult);
	}

	public static void verifySuccessCreateFormMsg(SeleniumBrowser Browser, TCResult pResult) {
		Browser.setTimeOut(Browser, 30);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_Common.div_SuccessMessage,
				DHLValueList.Message.CreateFormSuccess, pResult);
		Browser.resetTimeOut(Browser);
	}

	public static void verifySuccessUpdateMsg(SeleniumBrowser Browser, String objectName, TCResult pResult) {
		F_GeneralVerification.verifyNoticationMessage(Browser, I_Common.div_SuccessMessage,
				MessageFormat.format(DHLValueList.Message.UpdateSuccess, objectName), pResult);
	}

	public static void verifySuccessDeleteMsg(SeleniumBrowser Browser, String objectName, TCResult pResult) {
		F_GeneralVerification.verifyNoticationMessage(Browser, I_Common.div_SuccessMessage,
				MessageFormat.format(DHLValueList.Message.DeleteSuccess, objectName), pResult);
	}

	public static void verifySuccessUndeleteMsg(SeleniumBrowser Browser, String objectName, TCResult pResult) {
		F_GeneralVerification.verifyNoticationMessage(Browser, I_Common.div_SuccessMessage,
				MessageFormat.format(DHLValueList.Message.UndeleteSuccess, objectName), pResult);
	}

	public static void verifySuccessAssignMsg(SeleniumBrowser Browser, String objectName, TCResult pResult) {
		F_GeneralVerification.verifyNoticationMessage(Browser, I_Common.div_SuccessMessage,
				MessageFormat.format(DHLValueList.Message.AssignSuccess, objectName), pResult);
	}

	public static void verifyErrorMsg(SeleniumBrowser Browser, String msg, TCResult pResult) {

		Browser.setTimeOut(Browser, 30);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_Common.div_ErrorMessage, msg, pResult);
		Browser.resetTimeOut(Browser);
	}

	public static void verifyStatusIncident(SeleniumBrowser Browser, String expectedStatus, TCResult pResult) {
		List<String> firstRow = Functions.getFirstRow(Browser);
		String status = firstRow.get(5);
		F_GeneralVerification.verifyElementValue( "Status", status, expectedStatus, pResult);
	}

	public static void verifyNewlyCreatedincidentpopup(SeleniumBrowser Browser, TCResult pResult) {
		F_GeneralVerification.verifyElementVisible(Browser, I_DHL_Home.frm_newIncidentWarning,
				"new created incident warning", pResult);

	}

	public static void verifyCoordinateFormatOfIncident(SeleniumBrowser Browser, TCResult pResult) {
		String c = Functions.getText(Browser, I_DHL_Details.txt_incidentCoord);
		if (c == "" && c.length() < 12) {
			pResult.Result = false;
			pResult.Message += "coordinates is empty.";
		} else if (c.startsWith("Lon: ") != true && c.contains("Lat: ")) {
			pResult.Result = false;
			pResult.Message += "coordinates :" + c + " is incorrect format";
		}
	}

	public static void verifyEvidence(O_Evidence evidence, SeleniumBrowser Browser, TCResult pResult) {

		List<String> row = new ArrayList<>();
		String evidentList = "";
		int r = Functions.countRow(Browser);
		boolean flag = false;

		for (int i = 1; i <= r; i++) {
			row = Functions.GetRowData(Browser, i);
			if (row.get(2).contains(evidence.Desc)) {
				flag = true;
				break;
			}
			evidentList += row.get(2) + ",";

		}

		if (flag == false) {
			pResult.SetResult(false);
			pResult.SetMessage("Evidence " + evidence.Desc + " is not exist");
			pResult.SetMessage("List Evidence: " + evidentList);
			return;
		}
		F_GeneralVerification.verifyElementValue( "Type", row.get(1), evidence.Type, pResult);
		F_GeneralVerification.verifyElementValue( "Author", row.get(4), evidence.Author, pResult);

		if (evidence.Uploadedon != null) {
			F_GeneralVerification.verifyDatetime(Browser, "Uploaded on", row.get(5), evidence.Uploadedon, 5, pResult);

			if (evidence.Observedon == null) {
				evidence.Observedon = evidence.Uploadedon;
			}
			F_GeneralVerification.verifyDatetime(Browser, "Observed on", row.get(3), evidence.Observedon, 5, pResult);
		}
	}

	public static void verifyEvidenceMetadataFilled(SeleniumBrowser Browser, TCResult pResult) {
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Street address",
				I_AIMS_Incident_Details.Evidence.txt_Street, pResult);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "City", I_AIMS_Incident_Details.Evidence.txt_City,
				pResult);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Zip code",
				I_AIMS_Incident_Details.Evidence.txt_ZipCode, pResult);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Coordinates",
				I_AIMS_Incident_Details.Evidence.txt_Coordinates, pResult);
	}

	public static void verifyEvidenceDeleted(SeleniumBrowser Browser, String filename, int noOfEvidence,
			TCResult Result) {
		By evidenceName = By.xpath("//table[@id='evidences-list']/tbody/tr/td[contains(text(),'" + filename + "')]");
		F_GeneralVerification.verifyElementInvisible(Browser, evidenceName, "Evidence deleted:" + filename, Result);

		int currentEvidenceNum = F_AIMS.getNumberOfEvidence(Browser);
		if ((noOfEvidence - 1) != currentEvidenceNum) {
			Result.Result = false;
			Result.Message = "Evidence Number before deleting is: " + noOfEvidence
					+ "\n Evidence Number after deleting is: " + currentEvidenceNum;
		}
	}

	public static void verifyExportLog(O_Evidence evidence, SeleniumBrowser Browser, TCResult pResult) {

		List<String> row = new ArrayList<>();

		int r = Functions.countRow(Browser);
		boolean flag = false;

		CheckName: for (int i = 1; i <= r; i++) {
			row = Functions.GetRowData(Browser, i);
			if (row.get(2).contains(evidence.Desc)) {
				flag = true;
				break CheckName;
			}

		}

		if (flag == false) {
			pResult.SetResult(false);
			pResult.SetMessage("Evidence " + evidence.Desc + " is not exist");
			return;
		}

		F_GeneralVerification.verifyElementValue( "Author", row.get(4), evidence.Author, pResult);
		F_GeneralVerification.verifyElementValue( "Type", row.get(1), evidence.Type, pResult);
		F_GeneralVerification.verifyDatetime(Browser, "Update on", row.get(5), evidence.Uploadedon, 2, pResult);
	}

	public static void verifyExportLog(SeleniumBrowser Browser, String user, Date time, String zipName, String filename,
			TCResult pResult) {
		List<String> latestLog = Functions.getLastRow(Browser, "app-evidence-export-log");

		// Verify log
		F_GeneralVerification.verifyElementValue("User", latestLog.get(0), user, pResult);
		F_GeneralVerification.verifyDatetime(Browser, "Date/time", latestLog.get(1), time, 2, pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Zip file", latestLog.get(2), zipName, pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Exported evidences", latestLog.get(3), filename, pResult);
	}

	public static void verifyExportLog(SeleniumBrowser Browser, String user, Date time, String zipName,
			List<String> filename, TCResult pResult) {
		List<String> latestLog = Functions.getLastRow(Browser, "app-evidence-export-log");

		// Verify log
		F_GeneralVerification.verifyElementValue( "User", latestLog.get(0), user, pResult);
		F_GeneralVerification.verifyDatetime(Browser, "Date/time", latestLog.get(1), time, 2, pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Zip file", latestLog.get(2), zipName, pResult);
		for (int i = 0; i < filename.size(); i++) {
			System.out.println(filename.get(i));
			F_GeneralVerification.verifyElementContent(Browser, "Exported evidences", latestLog.get(3), filename.get(i),
					pResult);
		}
	}

	public static void verifyExportFile(SeleniumBrowser Browser, String filename, TCResult pResult) {
		String destDir = Functions.getCurrDirectory() + "\\" + Constants.TestData + "\\Unzip";

		File[] file = Functions.getListOfFile(destDir);
		int f_count = 0;
		int csv_count = 0;

		if (file.length <= 0) {
			pResult.SetResult(false);
			pResult.SetMessage("File is not exist");
		} else
			for (int i = 0; i < file.length; i++) {
				String name = file[i].getName();

				if (name.contains(filename))
					f_count++;
				if (name.contains(".csv"))
					csv_count++;

				if (file[i].length() < 1024) {
					pResult.SetResult(false);
					pResult.SetMessage("File " + name + " is damaged !");
					return;
				}
			}

		if (f_count != 1) {
			pResult.SetResult(false);
			pResult.SetMessage("File " + filename + " is not exist !");
		}

		if (csv_count != 1) {
			pResult.SetResult(false);
			pResult.SetMessage("CSV file is not exist !");
		}
	}

	public static void verifyExportFileList(SeleniumBrowser Browser, List<String> fileList, TCResult pResult) {
		String destDir = Functions.getCurrDirectory() + "\\" + Constants.TestData + "\\Unzip";

		File[] file = Functions.getListOfFile(destDir);
		int f_count = 0;
		int csv_count = 0;
		String actualList = "";
		String expectedList = "";

		if (file.length <= 0) {
			pResult.SetResult(false);
			pResult.SetMessage("File is not exist");
		} else
			for (int i = 0; i < file.length; i++) {
				String name = file[i].getName();
				actualList += name + "\n";
				for (int n = 0; i < fileList.size(); i++) {
					expectedList += fileList.get(n) + "\n";
					if (name.contains(fileList.get(n)))
						f_count++;
				}

				if (name.endsWith(".csv"))
					csv_count++;

				if (file[i].length() < 1024) {
					pResult.SetResult(false);
					pResult.SetMessage("File " + name + " is damaged !");
					return;
				}
			}

		if (f_count < fileList.size()) {
			pResult.SetResult(false);
			pResult.SetMessage("Number of File are not sufficient ! Number of file match is:" + f_count);
			pResult.SetMessage("Actual list:" + actualList + " instead of " + expectedList);
		}

		if (csv_count < 1) {
			pResult.SetResult(false);
			pResult.SetMessage("CSV file is not exist !: " + csv_count);
		}
	}

	public static void verifyIncident(O_Evidence evidence, SeleniumBrowser Browser, TCResult pResult) {
		List<String> row = new ArrayList<>();

		int r = Functions.countRow(Browser);
		boolean flag = false;

		CheckName: for (int i = 1; i <= r; i++) {
			row = Functions.GetRowData(Browser, i);
			if (row.get(2).contains(O_Evidence.Desc)) {
				flag = true;
				break CheckName;
			}

		}
	}

	public static void verifyMandataryFieldsError(SeleniumBrowser Browser, String fieldName, By pBy, String msg,
			TCResult pResult) {
		WebElement fieldEle = Browser.captureInterface(pBy);

		if (fieldEle == null) {
			pResult.SetResult(false);
			pResult.SetMessage("Field: " + fieldName + " does not display the Mandatary error ");
		} else if (!fieldEle.getText().contains(msg)) {
			pResult.SetResult(false);
			pResult.SetMessage("Warning: " + fieldEle.getText() + " instead of " + msg);
		}
	}

	public static void verifyCallLocationFilled(SeleniumBrowser Browser, TCResult pResult) {
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Street address",
				I_AIMS_Incident_Details.txt_callAddress, pResult);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "City", I_AIMS_Incident_Details.txt_callCity,
				pResult);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Zip code", I_AIMS_Incident_Details.txt_callzipCode,
				pResult);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Coordinates",
				I_AIMS_Incident_Details.txt_callcoordinates, pResult);

		String actualcallerAddress = Browser.captureInterface(I_AIMS_Incident_Details.txt_callAddress)
				.getAttribute("value");
		if (actualcallerAddress.length() < 7) {
			pResult.SetResult(false);
			pResult.SetMessage("Address is not auto filled");
		}

	}

	public static void verifyIncidentLocationFilled(SeleniumBrowser Browser, TCResult pResult) {

		F_GeneralVerification.verifyElementContentNotNull(Browser, "Street address",
				I_AIMS_Incident_Details.txt_incidentAddr, pResult);

		F_GeneralVerification.verifyElementContentNotNull(Browser, "City", I_AIMS_Incident_Details.txt_incidentCity,
				pResult);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Zip code",
				I_AIMS_Incident_Details.txt_incidentzipCode, pResult);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Coordinates",
				I_AIMS_Incident_Details.txt_incidentCoord, pResult);

		String actualAddress = Browser.captureInterface(I_AIMS_Incident_Details.txt_incidentAddr).getAttribute("value");
		if (actualAddress.length() < 7) {
			pResult.SetResult(false);
			pResult.SetMessage("Address is not auto filled");
		}
	}

	public static void verifyIncidentDetails(SeleniumBrowser Browser, O_Incident result_o, O_Incident new_o, Date now,
			TCResult pResult) {
		F_GeneralVerification.verifyElementValue( "Incident Date", result_o.Datetime, new_o.Datetime, pResult);
		F_GeneralVerification.verifyDatetime(Browser, "Incident Date", result_o.Datetime, now, 2, pResult);
		System.out.println(result_o.Type + " and " + new_o.Type);
		F_GeneralVerification.verifyElementValue( "Type of Incident", result_o.Type, new_o.Type, pResult);
		F_GeneralVerification.verifyDatetime(Browser, "Created Date", result_o.CreatedDate, now, 2, pResult);
		F_GeneralVerification.verifyElementValue( "Reporter", result_o.ReportingOfficer, new_o.ReportingOfficer,
				pResult);
		F_GeneralVerification.verifyElementValue( "Status", result_o.Status, new_o.Status, pResult);
	}

	public static void verifyMultiIncidentType(SeleniumBrowser Browser, List<String> types, TCResult pResult) {
		List<String> firstRow = Functions.getFirstRow(Browser);
		String actual_type = firstRow.get(2);
		for (int i = 0; i < types.size(); i++)
			F_GeneralVerification.verifyElementContent(Browser, "Incident Type name", firstRow.get(2).replace("-", " "),
					types.get(i).replace("-", " "), pResult);

	}

	public static void verifyValidationRule(SeleniumBrowser Browser, String name, String type, String regex,
			String desc, TCResult pResult) {
		F_AIMS.commonSearch(Browser, name);

		List<String> firstRow = Functions.getFirstRow(Browser);

		F_GeneralVerification.verifyElementValue( "Name", firstRow.get(0), name, pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Type", type, firstRow.get(1), pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Regular Expression", regex, firstRow.get(2), pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Description", desc, firstRow.get(3), pResult);
	}

	public static void verifyIncidentType(SeleniumBrowser Browser, String name, String code, String workflow,
			TCResult pResult) {
		F_AIMS.commonSearch(Browser, name);

		List<String> firstRow = Functions.getFirstRow(Browser);

		F_GeneralVerification.verifyElementValue( "Incident Type name", firstRow.get(0), name, pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Incident Code", code, firstRow.get(1), pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Workflow", workflow, firstRow.get(2), pResult);
	}

	public static void verifyNewIncidentTypeatAssignmentpage(SeleniumBrowser Browser, String name, TCResult Result) {

		F_Navigation.goToPage(Browser, I_AIMS_Settings.tab_incidentTypeAssignment);
		// Browser.scrollToEle(I_AIMS_Settings.node_EndItemOfUncategory);
		List<WebElement> list = Browser.Driver.findElements(I_AIMS_Settings.treenode_UncategorizedList);
		String msg = "";
		for (WebElement e : list) {
			msg += e.getText() + "\n";
			if (e.getText().contains(name) == true) {
				return;
			}
		}
		Result.SetResult(false);
		Result.SetMessage("Incident type list: " + msg + "not contain Incident type: " + name);
	}

	public static void verifyCatalogExist(SeleniumBrowser Browser, String catalog, TCResult pResult) {
		By pcategory = By.xpath("//span[contains(text(),'" + catalog + "')]");
		F_GeneralVerification.verifyElementExist(Browser, catalog, pcategory, pResult);
	}

	public static void verifyCatalogNotExist(SeleniumBrowser Browser, String catalog, TCResult pResult) {
		By pcategory = By.xpath("//span[contains(text(),'" + catalog + "')]");
		F_GeneralVerification.verifyElementNotExist(Browser, catalog, pcategory, pResult);
	}

	public static void verifyDeletedIncidentsSetting(SeleniumBrowser Browser, String expectedValue, TCResult Result) {
		// Browser.scrollToEle(I_DHL_Settings.txt_deletedIncidents);
		String actualvalue = Browser.captureInterface(I_DHL_Settings.txt_deletedMarkedIncident).getAttribute("value");
		F_GeneralVerification.verifyElementContent(Browser, "deleted incidents days", actualvalue, expectedValue,
				Result);
	}

	public static void verifyDeleteConfirm(SeleniumBrowser Browser, String itemName, TCResult Result) {
		WebElement dialogEle = Browser.captureInterface(I_CP_Common.frm_DeleteConfirm);
		if (dialogEle == null) {
			Result.SetResult(false);
			Result.SetMessage("Confirm dialog does not exsit");
		} else {
			Browser.waitForElementVisible(I_Common.lbl_DeleteConfirmMsg);
			F_GeneralVerification.verifyElementContent(Browser, "Message content",
					Browser.captureInterface(I_Common.lbl_DeleteConfirmMsg).getText().trim(),
					MessageFormat.format(DHLValueList.Warning.ConfirmDelete, itemName), Result);
		}
	}

	public static void verifyRowNotExist(SeleniumBrowser Browser, String name, TCResult Result) {
		F_GeneralVerification.verifyElementNotExist(Browser, "Name " + name,
				By.xpath("//tbody/tr/td[contains(.,'" + name + "')]"), Result);
	}

	public static void verifyCustomField(SeleniumBrowser Browser, String fieldName, String dataType, String UKlabel,
			String USlabel, String DElabel, String valRule, TCResult pResult) {
		List<String> latestLog = Functions.GetRowDataByValue(Browser, fieldName);

		F_GeneralVerification.verifyElementValue( "Origin", latestLog.get(1), "User", pResult);
		F_GeneralVerification.verifyElementValue( "Field Name", latestLog.get(2), fieldName, pResult);
		F_GeneralVerification.verifyElementValue( "Data type", latestLog.get(3), dataType, pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Label for English (UK)", latestLog.get(5), UKlabel,
				pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Label for English (US)", latestLog.get(6), USlabel,
				pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Label for Deutsch", latestLog.get(7), DElabel, pResult);
	}

	public static void verifyCustomFieldsAtDetails(SeleniumBrowser Browser, String UKfieldName, TCResult pResult) {
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLInbox.openLatestIncident(Browser);
		List<DetailField> customFields = F_DHLInbox.getCustomFieldInfo(Browser);
		for (DetailField field : customFields) {
			if (field.getfieldName().contains(UKfieldName))
				return;
		}
		pResult.SetResult(false);
		pResult.SetMessage("Custom field " + UKfieldName + " does not display in details");

	}

	public static void verifyFieldnameAndtypeNotEditable(SeleniumBrowser Browser, TCResult pResult) {

		WebElement txtnameEle = Browser.captureInterface(I_DHL_Settings.txt_fieldNameEdittab);
		WebElement txtTypeEle = Browser.captureInterface(I_DHL_Settings.txt_typeEdittab);
		if (!txtnameEle.getAttribute("class").contains("disable")) {
			pResult.SetResult(false);
			pResult.SetMessage("Field name is editable");
		}
		if (!txtTypeEle.getAttribute("class").contains("disable")) {
			pResult.SetResult(false);
			pResult.SetMessage("Datatype is editable");
		}
	}

	public static void verifyStates(SeleniumBrowser Browser, String state, String desc, TCResult Result) {
		F_DHLSettings.commonSearch(Browser, state);
		List<String> firstRow = Functions.getFirstRow(Browser);

		if (firstRow.size() < 1) {
			Result.SetResult(false);
			Result.SetMessage("Search result of State is 0");
			return;
		}
		F_GeneralVerification.verifyElementValue( "State", firstRow.get(2), state, Result);
		F_GeneralVerification.verifyElementValue( "Description", firstRow.get(6), desc, Result);
	}

	public static void verifyRoles(SeleniumBrowser Browser, String name, TCResult Result) {
		F_DHLSettings.commonSearch(Browser, name);
		List<String> firstRow = Functions.getFirstRow(Browser);

		if (firstRow.size() < 1) {
			Result.SetResult(false);
			Result.SetMessage("Search result is 0");
			return;
		}
		F_GeneralVerification.verifyElementValue( "Roles", firstRow.get(0), name, Result);
	}

	public static void verifyAssignmentRoles(SeleniumBrowser Browser, String user, String role, TCResult Result) {
		F_DHLSettings.commonSearch(Browser, user);
		List<String> firstRow = Functions.getFirstRow(Browser);

		if (firstRow.size() < 1) {
			Result.SetResult(false);
			Result.SetMessage("Search result is 0");
			return;
		}
		F_GeneralVerification.verifyElementValue( "User", firstRow.get(4), role, Result);
	}

	public static void verifyWorkflow(SeleniumBrowser Browser, String name, boolean isDefaultWF, String firstState,
			TCResult Result) {
		F_DHLSettings.commonSearch(Browser, name);
		List<String> firstRow = Functions.getFirstRow(Browser);

		F_GeneralVerification.verifyElementValue("Workflow Name", firstRow.get(0), name, Result);
		// F_GeneralVerification.verifyElementValue(Browser, "Roles", firstRow.get(1),
		// defaultWF, Result);

		By chk_defaultWorkflowAtTable = By.xpath("//table/tbody/tr/td[text()='" + name + "']/..//input");

		boolean actualDefaultWF = Browser.getCheckboxValue(chk_defaultWorkflowAtTable);
		System.out.println("Is default WF: " + actualDefaultWF);

		if (actualDefaultWF != isDefaultWF) {
			Result.SetResult(false);
			Result.SetMessage("Defalt workflow is displayed " + actualDefaultWF + " instead of " + isDefaultWF);
		}

//		F_DHLSettings.select(Browser, name);
//		F_DHLSettings.clickEdit(Browser);

	}

	public static void verifyIncidentAtInbox(SeleniumBrowser Browser, boolean isAutoGenagate,
			O_DHLIncident expectedIncident, TCResult pResult) {
		List<String> RowData = null;

		if (isAutoGenagate == true) {
			RowData = Functions.getFirstRow(Browser);
		} else {
			RowData = Functions.GetRowDataByValue(Browser, expectedIncident.Code);
		}

		if (RowData.size() < 1) {
			pResult.SetResult(false);
			pResult.SetMessage("Can not find row");
			return;
		}
		if (isAutoGenagate == true) {
			F_GeneralVerification.verifyElementContent(Browser, "ID", RowData.get(1), "R-", pResult);
		}
		
		F_GeneralVerification.verifyElementContent(Browser, "Incident date", RowData.get(2), expectedIncident.Datetime,
				pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Status", RowData.get(5), expectedIncident.Status, pResult);
//		F_GeneralVerification.verifyDatetime(Browser, "Create date", RowData.get(4), expectedIncident.CreatedDate, 5,
//				pResult);
		

	}

	public static void verifyIncidentAtDetail(SeleniumBrowser Browser, O_DHLIncident expectedIncident,
			TCResult pResult) {
		O_DHLIncident actualIncident = F_DHLInbox.getDetails(Browser);

		if (expectedIncident.Assignee != null)
			F_GeneralVerification.verifyElementContent(Browser, "Assignee", actualIncident.Assignee,
					expectedIncident.Assignee, pResult);

		if (expectedIncident.Status != null)
			F_GeneralVerification.verifyElementContent(Browser, "Status", actualIncident.Status,
					expectedIncident.Status, pResult);

		if (expectedIncident.Datetime != null)
			F_GeneralVerification.verifyElementContent(Browser, "Incident date", actualIncident.Datetime,
					expectedIncident.Datetime, pResult);

		if (expectedIncident.Workflow != null)
			F_GeneralVerification.verifyElementContent(Browser, "Workflow of incident", actualIncident.Workflow,
					expectedIncident.Workflow, pResult);
		// F_GeneralVerification.verifyElementContentNotNull(Browser, "Count",
		// I_CP_Contraventions.txt_count, Result);

//		if (expectedIncident.incidentCoordinates != null)
//			F_GeneralVerification.verifyElementContent(Browser, "incident Coordinates",
//					actualIncident.incidentCoordinates, expectedIncident.incidentCoordinates, pResult);

	}

	public static void verifyIncidentResultOfSearch(SeleniumBrowser Browser, String expectedIncidentID,
			TCResult pResult) {
		F_GeneralVerification.verifyTableHaveOnlyOneResult(Browser, pResult);

		if (pResult.Result == true) {
			List<String> RowData = null;
			RowData = Functions.GetRowDataByValue(Browser, expectedIncidentID);

			F_GeneralVerification.verifyElementContent(Browser, "Incident Code", RowData.get(1), expectedIncidentID,
					pResult);
		}
	}

	public static void verifyConfirmChangeIncidentType(SeleniumBrowser Browser, TCResult pResult) {

		String actualContent = Browser.captureInterface(I_DHL_Details.popup_confirmChangeTypeMsg).getText();

		F_GeneralVerification.verifyElementContent(Browser, "Confirm Message", actualContent,
				DHLValueList.Warning.ConfirmChangeIncidentType, pResult);
	}

	public static void verifyIncidentCounter(SeleniumBrowser Browser, int expectedTotal, int expectedNew,
			int actualTotal, int actualNew, TCResult pResult) {
		if (expectedTotal < expectedNew) {
			pResult.SetResult(false);
			pResult.SetMessage("Total of Original incident " + expectedTotal
					+ " is smaller than total of new incidents " + expectedNew);
			// return;
		}

		if (actualTotal < actualNew) {
			pResult.SetResult(false);
			pResult.SetMessage("Total of current incidents " + actualTotal + " is smaller than total of new incidents "
					+ actualNew);
			// return;
		}

		if (actualTotal != expectedTotal) {
			pResult.SetResult(false);
			pResult.SetMessage("Incident Total No " + " is displayed " + actualTotal + " instead of " + expectedTotal);
		}

		if (actualNew != expectedNew) {
			pResult.SetResult(false);
			pResult.SetMessage("New Incidents No " + " is displayed " + actualNew + " instead of " + expectedNew);
		}

	}

	public static void verifyHighlightIncidentStateHistory(SeleniumBrowser Browser, String expectedStatus,
			TCResult pResult) {
		// -- check expected status has been highlighed ----
		By highlightStateBy = By
				.xpath("//app-state-progess/div/div/a[@class='completed']/span[contains(.,'" + expectedStatus + "')]");

		if (Browser.captureInterface(highlightStateBy) == null) {
			pResult.SetResult(false);
			pResult.SetMessage("The state: " + expectedStatus + " is not highlighed.");
		}
	}

	public static void verifyNonHighlightIncidentStateHistory(SeleniumBrowser Browser, String expectedStatus,
			TCResult pResult) {
		// -- check expected status has been highlighed ----
		By highlightStateBy = By
				.xpath("//app-state-progess/div/div/a[@class='completed']/span[contains(.,'" + expectedStatus + "')]");
		Browser.setTimeOut(Browser, 3);
		if (Browser.captureInterface(highlightStateBy) != null) {
			pResult.SetResult(false);
			pResult.SetMessage("The state: " + expectedStatus + " is highlighed.");
		}

		Browser.resetTimeOut(Browser);
	}

	public static void verifyAllFieldsAreDisable(SeleniumBrowser Browser, TCResult pResult) {

		String actualContent = Browser.captureInterface(I_DHL_Details.popup_confirmChangeTypeMsg).getText();

		F_GeneralVerification.verifyElementContent(Browser, "Confirm Message", actualContent,
				DHLValueList.Warning.ConfirmChangeIncidentType, pResult);
	}

	public static void verifyTemplateForm(List<String> placeHoders, SeleniumBrowser Browser, TCResult pResult) {

		// --- Check Form Header
		WebElement header = Browser
				.captureInterface(By.xpath("//app-form-template-create/div[@class='custom-modal-header']/span"));

		F_GeneralVerification.verifyElementContent(Browser, "Header Name", header.getText(), "Document Template",
				pResult);

		// ---- Check label list --
		int fieldslistCount = Browser.Driver
				.findElements(By.xpath(
						"//app-form-template-create/div[@class='customBoxScroll']//div[@class='form-horizontal']/div"))
				.size();

		for (int i = 1; i < fieldslistCount; i++) {
			WebElement FieldLabel = Browser.captureInterface(By.xpath(
					"//app-form-template-create/div[@class='customBoxScroll']//div[@class='form-horizontal']/div[" + i
							+ "]/label"));

			System.out.printf("Label: " + FieldLabel.getText() + "\t\n");

			F_GeneralVerification.verifyElementContent(Browser, "Field Label", FieldLabel.getText().toString(),
					placeHoders.get(i - 1), pResult);
		}
		// --- Check drop down value list --

		// -- click on first field --
		Browser.captureInterface(By.xpath(
				"//app-form-template-create/div[@class='customBoxScroll']//div[@class='form-horizontal']/div[1]//ng-select"))
				.click();
		;
		Functions.waitForSeconds(2);
		int valueListCount = Browser.Driver
				.findElements(By.xpath("//app-form-template-create//ng-dropdown-panel/div/div/div")).size();

		String detailsList = "";
		for (int i = 1; i <= valueListCount; i++) {
			String field = Browser
					.captureInterface(By.xpath("//app-form-template-create//ng-dropdown-panel/div/div/div[" + i + "]"))
					.getText();
			detailsList += field + "," ;
		}
		
		if (!detailsList.contains("Assignee") || !detailsList.contains("Current time")
				|| !detailsList.contains("Current user") || !detailsList.contains("Incident Code")
				|| !detailsList.contains("Incident type") || !detailsList.contains("Status")
				|| !detailsList.contains("Incident Date") || !detailsList.contains("Workflow"))
		{
		//	System.out.printf(detailsList);
			pResult.SetResult(false);
			pResult.SetMessage("Details list " + detailsList + " is missing one/some field ");
		}

		/*
		 * switch (i) { case 1: F_GeneralVerification.verifyElementContent(Browser,
		 * "Assignee", field, "Assignee", pResult); break; case 2:
		 * F_GeneralVerification.verifyElementContent(Browser, "Current time", field,
		 * "Current time", pResult); break; case 3:
		 * F_GeneralVerification.verifyElementContent(Browser, "Current user", field,
		 * "Current user", pResult); break; case 4:
		 * F_GeneralVerification.verifyElementContent(Browser, "Incident Code", field,
		 * "Incident Code", pResult); break; case 5:
		 * F_GeneralVerification.verifyElementContent(Browser, "Incident Type", field,
		 * "Incident type", pResult); break; case 6:
		 * F_GeneralVerification.verifyElementContent(Browser, "Status", field,
		 * "Status", pResult); break; case 7:
		 * F_GeneralVerification.verifyElementContent(Browser, "Incident Date", field,
		 * "Incident Date", pResult); break; case 8:
		 * F_GeneralVerification.verifyElementContent(Browser, "Workflow", field,
		 * "Workflow", pResult); break; default:
		 * 
		 * }
		 * 
		 */

		// -- close dropdown list --
		Browser.captureInterface(By.xpath("//*[@id='modal-body']/div/div/div[1]/div/ng-select/div/span")).click();
		Functions.waitForSeconds(1);
	}

	public static void verifyTemplateValueExist(SeleniumBrowser Browser, String labelName, String expectedValue,
			TCResult pResult) {
		By actualValueEle = By.xpath(
				"//app-form-template-create/div[@class='customBoxScroll']//div[@class='form-horizontal']/div/label[contains(., '"
						+ labelName + "')]/following-sibling::div/ng-select/div/div//input");

		F_GeneralVerification.verifyElementContent(Browser, "Value of " + labelName,
				Browser.captureInterface(actualValueEle).getAttribute("value").toString(), expectedValue, pResult);
	}

	public static void verifyTemplateExist(String templateName, SeleniumBrowser Browser, TCResult pResult) {
		boolean isExist = F_DHLSettings.checkTemplateExist(Browser, templateName);
		if (isExist == false) {
			pResult.SetResult(false);
			pResult.SetMessage("The template " + templateName + " is not uploaded successfully.");
		}

	}

	public static void verifyTemplateExistInEvidenceTab(String templateName, SeleniumBrowser Browser,
			TCResult pResult) {
		F_DHLInbox.clickFormButton(Browser);

		boolean isExist = F_DHLInbox.isTemplateExist(Browser, templateName);
		if (isExist == false) {
			pResult.SetResult(false);
			pResult.SetMessage("The template " + templateName + " is not exist.");
		}

	}

	public static void verifyConfirmDeleteIncident(SeleniumBrowser Browser, TCResult pResult) {
		WebElement dialogEle = Browser.captureInterface(I_CP_Common.frm_DeleteConfirm);
		if (dialogEle == null) {
			pResult.SetResult(false);
			pResult.SetMessage("Confirm dialog does not exsit");
		} else {
			String headerName = Browser.captureInterface("//app-confirm-delete-dialog//div[1]").getText();
			String MsgContent = Browser.captureInterface("//app-confirm-delete-dialog//div[2]").getText();
			F_GeneralVerification.verifyElementContent(Browser, "Header Name", headerName, "Confirm", pResult);
			F_GeneralVerification.verifyElementContent(Browser, "Delete confirm content", MsgContent,
					DHLValueList.Warning.ConfirmDeleteIncident, pResult);
		}
	}

	public static void verifyIncidentInDeletingState(SeleniumBrowser Browser, String incidentCode, TCResult pResult) {
		WebElement delectedIncidentEle = Browser.Driver
				.findElement(By.xpath("//app-incident-list//table/tbody/tr/td/span[text()='" + incidentCode + "']/../.."));

		if (!delectedIncidentEle.getAttribute("class").contains("delete")) {
			pResult.SetResult(false);
			pResult.SetMessage("The incident " + incidentCode + " is not marked to delete.");
		}
	}

	public static void verifyIncidentNotInDeletingState(SeleniumBrowser Browser, String incidentCode,
			TCResult pResult) {
		WebElement delectedIncidentEle = Browser.Driver
				.findElement(By.xpath("//app-incident-list//table/tbody/tr/td/span[text()='" + incidentCode + "']/../.."));

		if (delectedIncidentEle.getAttribute("class").contains("delete")) {
			pResult.SetResult(false);
			pResult.SetMessage("The incident " + incidentCode + " is marked to delete.");
		}
	}

	public static void verifyAssigneeAtInboxDetails(SeleniumBrowser Browser, String assignee, TCResult pResult) {
		By assigneeEle = By.xpath(
				"//app-incident-detail/div/div/div[2]/div[@class='assignee']/div/span[contains(text(),'Assignee:')]/following-sibling::span");
		String actualAssignee = Browser.captureInterface(assigneeEle).getText();
		F_GeneralVerification.verifyElementContent(Browser, "Assignee", actualAssignee, assignee, pResult);
	}

	public static void verifyCoworkerAtInboxDetails(SeleniumBrowser Browser, List<String> coworker, TCResult pResult) {
		// -- check list

		String name = "";
		By coworkerEle = null;
		for (int i = 0; i < coworker.size(); i++) {
			name = coworker.get(i);
			coworkerEle = By
					.xpath("//app-incident-detail//div[@class='coWorkers-content open']/div/span[contains(text(), '"
							+ name + "')]");
			F_GeneralVerification.verifyElementExist(Browser, "Coworker", coworkerEle, pResult);
		}
		// --check number
		By numberEle = By.xpath("//app-incident-detail//div[@class='coWorkers-header-total']");

		F_GeneralVerification.verifyElementValue( "No of coworker",
				Browser.captureInterface(numberEle).getText(), String.valueOf(coworker.size()), pResult);
	}

}
