package libraries.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import interfaces.I_AIMS_Home;
import interfaces.I_AIMS_Incident_Details;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.generalFunctions.Functions;
import libraries.productFunctions.F_AIMS;

public class O_Incident {

	public static String RefNo;
	public static String ReportingOfficer;
	public static String Type;
	public static String Datetime;
	public static String CreatedDate;
	public static String Desc;
	public static String Author = Constants.GlobalAuthor;
	public static String Firstname;
	public static String Lastname;
	public static String callerStreet;
	public static String callerCity;
	public static String callerZip;
	public static String callerPhone;
	public static String incidentStreet;
	public static String incidentCity;
	public static String incidentZip;
	public static String incidentCoordinates;
	public static int numOfevidences;
	public static String Status;
	public static boolean IsDeleted;
	public static Map<String, String> metadata;

	public static void createIncident(SeleniumBrowser Browser) {

		F_AIMS.clickAddIncident(Browser);
		// Browser.clickJavascript(I_AIMS_Incident_Details.txt_Officer);
		Browser.click(I_AIMS_Incident_Details.div_Officer);
		Browser.waitForElementVisible(I_AIMS_Incident_Details.ddl_Officer);

		ReportingOfficer = F_AIMS.selectDropDown_AIMS(Browser, I_AIMS_Incident_Details.ddl_Officer);
		//Browser.waitForElementNotVisible(I_AIMS_Incident_Details.ddl_Officer);
		Type = F_AIMS.selectIncidentType(Browser);
		
		Desc = Functions.randomText(40);
		Browser.enter(I_AIMS_Incident_Details.txt_desc, Desc);
		
		//-- Fill Caller
		Firstname = Functions.randomText(6);
		Lastname = Functions.randomText(6);
		Browser.enter(I_AIMS_Incident_Details.txt_firstName, Firstname);
		Browser.enter(I_AIMS_Incident_Details.txt_lastName, Lastname);
		// Browser.enter(I_AIMS_Incident_Details.txt_callCity, Incident.city);
		// Browser.enter(I_AIMS_Incident_Details.txt_callzipCode, Incident.zipCode);
		callerPhone = Functions.randomNumberString(10);
		Browser.enter(I_AIMS_Incident_Details.txt_callPhone, callerPhone);

		F_AIMS.scrollToEndOfDetailsPanel(Browser);

		// Incident Location
		F_AIMS.selectMap(Browser);
		// Browser.enter(I_AIMS_Incident_Details.txt_incidentCity, Incident.city);
		// Browser.enter(I_AIMS_Incident_Details.txt_incidentzipCode, Incident.zipCode);

		//F_AIMS.FillCustomFields(Browser);
		
		F_AIMS.saveIncident(Browser);
		RefNo = Browser.captureInterface(I_AIMS_Home.lbl_TabName).getText();
	}

	public static void createIncidentWithoutCustomfields(SeleniumBrowser Browser) {
		F_AIMS.clickAddIncident(Browser);
		Browser.click(I_AIMS_Incident_Details.div_Officer);
		Browser.waitForElementVisible(I_AIMS_Incident_Details.ddl_Officer);

		ReportingOfficer = F_AIMS.selectDropDown_AIMS(Browser, I_AIMS_Incident_Details.ddl_Officer);
		Browser.waitForElementNotVisible(I_AIMS_Incident_Details.ddl_Officer);

		Browser.click(I_AIMS_Incident_Details.txt_IncidentType);
		Functions.waitForSeconds(1);
		Browser.waitForElementVisible(I_AIMS_Incident_Details.div_IncidentType);
		Type = F_AIMS.selectRandomIncidentType(Browser, I_AIMS_Incident_Details.div_IncidentType);
		Desc = Functions.randomText(20);
		Browser.enter(I_AIMS_Incident_Details.txt_desc, Desc);
		// Caller
		Firstname = Functions.randomText(6);
		Lastname = Functions.randomText(6);
		Browser.enter(I_AIMS_Incident_Details.txt_firstName, Firstname);
		Browser.enter(I_AIMS_Incident_Details.txt_lastName, Lastname);

		F_AIMS.scrollToEndOfDetailsPanel(Browser);

		// Incident Location
		F_AIMS.selectMap(Browser);
		F_AIMS.saveIncident(Browser);
		RefNo = Browser.captureInterface(I_AIMS_Home.lbl_TabName).getText();
	}

	public static List<String> uploadEvidence(SeleniumBrowser Browser, String incidentType, int NoOfFiles) {
		String folderPath = Constants.UploadFilePath;
		F_AIMS.openIncident(Browser, RefNo);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);

		int n = 0;

		File[] listFile = Functions.getListOfFile(folderPath);
		n = listFile.length;

		List<String> uploadList = new ArrayList<>();
		switch (incidentType) {
		case "IMAGE": 
			int si = 0;
			for (int i = 0; i < n && si < NoOfFiles; i++) {
				if (listFile[i].getName().contains(".png")
						|| listFile[i].getName().contains(".jpg")) {
					uploadList.add(listFile[i].getName());
					F_AIMS.uploadAEvidence(Browser, listFile[i].getName());
					F_AIMS.inputEvidenceMetadata(Browser);
					si++;
				}
				
			}
			break;
		
		case "VIDEO": 
			int sv = 0;
			for (int i = 0; i < n && sv < NoOfFiles; i++) {
				if (listFile[i].getName().contains(".mp4")) {
					uploadList.add(listFile[i].getName());
					F_AIMS.uploadAEvidence(Browser, listFile[i].getName());
					F_AIMS.inputEvidenceMetadata(Browser);
					sv++;
				}
			}
		 break;
		default: {
			for (int i = 0; i < NoOfFiles; i++) {
				 int r = Functions.randomInterger(1, listFile.length - 1);
				// ----upload --
				if (listFile[i].isFile()) {
					F_AIMS.uploadAEvidence(Browser, listFile[r].getName());
					uploadList.add(listFile[r].getName());
					F_AIMS.inputEvidenceMetadata(Browser);
				}
			}

		}
		}
		return uploadList;
	}

	/**
	 * Get metadata of latest Incident
	 * 
	 * @param Browser
	 */
	public static void getIncidentData(SeleniumBrowser Browser) {
		List<String> firstRow = Functions.getFirstRow(Browser);
		RefNo = firstRow.get(1);
		Type = firstRow.get(2);
		Datetime = firstRow.get(3);
		CreatedDate = firstRow.get(4);
		Status = firstRow.get(5);

		F_AIMS.openLatestIncident(Browser);
		ReportingOfficer = Browser.captureInterface(I_AIMS_Incident_Details.div_Officer).getText();
		Desc = Browser.captureInterface(I_AIMS_Incident_Details.txt_desc).getAttribute("value");
		Firstname = Browser.captureInterface(I_AIMS_Incident_Details.txt_firstName).getAttribute("value");
		Lastname = Browser.captureInterface(I_AIMS_Incident_Details.txt_lastName).getAttribute("value");

		callerPhone = Browser.captureInterface(I_AIMS_Incident_Details.txt_callPhone).getAttribute("value");
		Desc = Browser.captureInterface(I_AIMS_Incident_Details.txt_desc).getAttribute("value");
	}

}