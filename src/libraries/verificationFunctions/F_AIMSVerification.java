package libraries.verificationFunctions;

import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Mouse;
import libraries.objects.O_Evidence;
import libraries.objects.O_Incident;
import libraries.productFunctions.F_AIMS;
import libraries.productFunctions.F_Navigation;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.google.common.collect.Table;

import interfaces.I_AIMS_Incident_Details;
import interfaces.I_AIMS_Settings;
import libraries.Constants;

public class F_AIMSVerification {

	public static void verifyStatus(SeleniumBrowser Browser, String expectedStatus, TCResult pResult) {
		List<String> firstRow = Functions.getFirstRow(Browser);
		String status = firstRow.get(5);
		F_GeneralVerification.verifyElementValue("Status", status, expectedStatus, pResult);
	}

	public static void verifyCoordinateFormatOfIncident(SeleniumBrowser Browser, TCResult pResult) {
		String c = Functions.getText(Browser, I_AIMS_Incident_Details.txt_incidentCoord);
		if (c == "" && c.length() < 12) {
			pResult.Result = false;
			pResult.Message += "coordinates is empty.";
		} else if (c.startsWith("Lon: ") != true && c.contains("Lat: ")) {
			pResult.Result = false;
			pResult.Message += "coordinates :" + c + " is incorrect format";
		}
	}

	public static void verifyCoordinateFormatOfCaller(SeleniumBrowser Browser, TCResult pResult) {
		String c = Functions.getText(Browser, I_AIMS_Incident_Details.txt_callcoordinates);
		if (c == "") {
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

		F_GeneralVerification.verifyElementValue("Author", row.get(4), evidence.Author, pResult);
		F_GeneralVerification.verifyElementValue("Type", row.get(1), evidence.Type, pResult);
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
		F_GeneralVerification.verifyElementValue("User", latestLog.get(0), user, pResult);
		F_GeneralVerification.verifyDatetime(Browser, "Date/time", latestLog.get(1), time, 2, pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Zip file", latestLog.get(2), zipName, pResult);
		for (int i = 0; i < filename.size(); i++)
		{	System.out.println(filename.get(i));
			F_GeneralVerification.verifyElementContent(Browser, "Exported evidences", latestLog.get(3), filename.get(i),
					pResult);}
	}

	public static void verifyExportFile(SeleniumBrowser Browser, String filename, TCResult pResult) {
		String destDir = Constants.DataPath + "\\Unzip";

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
		String destDir = Constants.DataPath + "\\Unzip";

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
		F_GeneralVerification.verifyElementValue("Incident Date", result_o.Datetime, new_o.Datetime, pResult);
		F_GeneralVerification.verifyDatetime(Browser, "Incident Date", result_o.Datetime, now, 2, pResult);
		System.out.println(result_o.Type + " and " + new_o.Type );
		F_GeneralVerification.verifyElementValue("Type of Incident", result_o.Type, new_o.Type, pResult);
		F_GeneralVerification.verifyDatetime(Browser, "Created Date", result_o.CreatedDate, now, 2, pResult);
		F_GeneralVerification.verifyElementValue( "Reporter", result_o.ReportingOfficer, new_o.ReportingOfficer,
				pResult);
		F_GeneralVerification.verifyElementValue( "Description", result_o.Desc, new_o.Desc, pResult);
		F_GeneralVerification.verifyElementValue( "First Name", result_o.Firstname, new_o.Firstname, pResult);
		F_GeneralVerification.verifyElementValue( "Last Name", result_o.Lastname, new_o.Lastname, pResult);
		F_GeneralVerification.verifyElementValue( "Caller Phone", result_o.callerPhone, new_o.callerPhone,
				pResult);
		F_GeneralVerification.verifyElementValue( "Status", result_o.Status, new_o.Status, pResult);
	}

	public static void verifyMultiIncidentType(SeleniumBrowser Browser, List<String> types, TCResult pResult) {
		List<String> firstRow = Functions.getFirstRow(Browser);
		String actual_type = firstRow.get(2);
		for(int i = 0; i <types.size(); i ++ )
			F_GeneralVerification.verifyElementContent(Browser, "Incident Type name", firstRow.get(2).replace("-", " "), types.get(i).replace("-", " "), pResult);
		
	}
	public static void verify_IncidentType(SeleniumBrowser Browser, String name, String code, TCResult Result) {
		F_AIMS.commonSearch(Browser, name);

		List<String> firstRow = Functions.getFirstRow(Browser);

		F_GeneralVerification.verifyElementValue("Incident Type name", firstRow.get(0), name, Result);
		F_GeneralVerification.verifyElementContent(Browser, "Incident Code", code, firstRow.get(1), Result);
	}

	public static void verify_new_incidentType_at_Assignment_page(SeleniumBrowser Browser, String name,
			TCResult Result) {

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

	public static void verifyCatalogExist(SeleniumBrowser Browser, String catalog, TCResult Result) {
		By pcategory = By.xpath("//span[contains(text(),'" + catalog + "')]");
		F_GeneralVerification.verifyElementExist(Browser, catalog, pcategory, Result);
	}
}
