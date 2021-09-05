package libraries.verificationFunctions;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import interfaces.I_AIMS_Incident_Details;
import interfaces.I_CP_Common;
import interfaces.I_CP_Contraventions;
import libraries.CPValueList;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Mouse;
import libraries.objects.O_contravention;
import libraries.productFunctions.F_CPContraventions;
import libraries.productFunctions.F_CPEnformentLog;
import libraries.productFunctions.F_CPSettings;
import libraries.productFunctions.F_CP_common;

public class F_CPVerification {

	public static void verifyVRMexistinTable(SeleniumBrowser Browser, String vrm, String cam, String from, String to,
			TCResult Result) {
		F_CPSettings.commonSearch(Browser, vrm);
		List<String> firstRow = Functions.getFirstRow(Browser);
		
		if(firstRow.size() < 1) {
			Result.SetResult(false);
			Result.SetMessage("Search VRM result is 0");
			return;
		}
		F_GeneralVerification.verifyElementValue( "Vehicle Number", firstRow.get(0), vrm, Result);
		
		if (cam != "") {
		F_GeneralVerification.verifyElementContent(Browser, "Camera Name", firstRow.get(1).replace(" ", ""),
				cam.replace(" ", ""), Result);
		}
		F_GeneralVerification.verifyElementContent(Browser, "Exception Date from", firstRow.get(2), from, Result);
		
		F_GeneralVerification.verifyElementContent(Browser, "Exception Date to", firstRow.get(3), to, Result);
	}

	public static void verifyNonDVLAformat(SeleniumBrowser Browser, TCResult Result) {

	}

	public static void verifyDeleteConfirm(SeleniumBrowser Browser, String itemName, TCResult Result) {

		WebElement dialogEle = Browser.captureInterface(I_CP_Common.frm_DeleteConfirm);

		if (dialogEle == null) {
			Result.SetResult(false);
			Result.SetMessage("Confirm dialog does not exsit");
		} else {
			Browser.waitForElementVisible(I_CP_Common.lbl_DeleteConfirmMsg);
			F_GeneralVerification.verifyElementContent(Browser, "Message content",
					Browser.captureInterface(I_CP_Common.lbl_DeleteConfirmMsg).getText().trim(),
					MessageFormat.format(CPValueList.Message.ConfirmDelete, itemName), Result);
		}
	}

	public static void verifyExemptConfirm(SeleniumBrowser Browser, String vrm, TCResult Result) {

		WebElement dialogEle = Browser.captureInterface(I_CP_Common.frm_ConvConfirm);

		if (dialogEle == null) {
			Result.SetResult(false);
			Result.SetMessage("Confirm dialog does not exsit");
		} else {
			Browser.waitForElementVisible(I_CP_Common.lbl_ConvConfirm);
			F_GeneralVerification.verifyElementContent(Browser, "Exempt Vehicle Message content",
					Browser.captureInterface(I_CP_Common.lbl_ConvConfirm).getText().trim(),
					MessageFormat.format(CPValueList.Message.ExemptConfirm, vrm), Result);
		}
	}

	public static void verifyVRexistinTable(SeleniumBrowser Browser, String voidReason, String required,
			TCResult Result) {
		F_CPSettings.commonSearch(Browser, voidReason);
		List<String> firstRow = Functions.getFirstRow(Browser);
		if(firstRow.size() < 1) {
			Result.SetResult(false);
			Result.SetMessage("Search result is 0");
			return;
		}
		F_GeneralVerification.verifyElementValue("void Reason Name", firstRow.get(0), voidReason, Result);

		if (required != "")
			F_GeneralVerification.verifyElementContent(Browser, "Required Description", required, firstRow.get(1),
					Result);
	}

	public static void verifyLocationExistinTable(SeleniumBrowser Browser, String camId, String cam, String streetName,
			String streetId, TCResult Result) {
		// WebElement streetEle = null;
		F_CPSettings.commonSearch(Browser, camId);
		List<String> firstRow = Functions.getFirstRow(Browser);
		if(firstRow.size() < 1) {
			Result.SetResult(false);
			Result.SetMessage("Search result is 0");
			return;
		}
		F_GeneralVerification.verifyElementContent(Browser, "Cam Id", firstRow.get(0), camId, Result);
		F_GeneralVerification.verifyElementContent(Browser, "Camera", firstRow.get(1), cam, Result);
		F_GeneralVerification.verifyElementContent(Browser, "Street name", firstRow.get(2), streetName, Result);
		F_GeneralVerification.verifyElementContent(Browser, "Street ID", firstRow.get(3), streetId, Result);

	}

	public static void verifyOffenceExistinTable(SeleniumBrowser Browser, String type, String code, TCResult Result) {
		F_CPSettings.commonSearch(Browser, type);
		List<String> firstRow = Functions.getFirstRow(Browser);
		if(firstRow.size() < 1) {
			Result.SetResult(false);
			Result.SetMessage("Search result is 0");
			return;
		}
		F_GeneralVerification.verifyElementValue( "Offence type", firstRow.get(0), type, Result);

		if (code != "")
			F_GeneralVerification.verifyElementContent(Browser, "Offence code", code, firstRow.get(1), Result);
	}

	public static void verifyVehicleInfoExistinTable(SeleniumBrowser Browser, String name, TCResult Result) {
		// WebElement streetEle = null;
		F_CPSettings.searchVehicleInfo(Browser, name);
		// if (streetId != "") {
		F_GeneralVerification.verifyElementExist(Browser, "Vehicle Info",
				By.xpath("//tbody/tr/td[contains(.,'" + name + "')]"), Result);
	}

	public static void verifyVehicleRowNotExist(SeleniumBrowser Browser, String name, TCResult Result) {
		F_GeneralVerification.verifyElementNotExist(Browser, "Name " + name,
				By.xpath("//tbody/tr/td[contains(.,'" + name + "')]"), Result);
	}

	public static void verifyContraventionInSearch(SeleniumBrowser Browser, String status, String camera,
			String observedTime, String user, TCResult Result) {
		List<String> firstRow = Functions.getFirstRow(Browser);
		if(firstRow.size() < 1) {
			Result.SetResult(false);
			Result.SetMessage("Search result is 0");
			return;
		}
		// F_GeneralVerification.verifyElementContent(Browser, "Observed Time",
		// firstRow.get(2), observedTime, Result);
		F_GeneralVerification.verifyElementContent(Browser, "User", firstRow.get(3), user, Result);
		F_GeneralVerification.verifyElementContent(Browser, "Status", firstRow.get(4),
				Functions.convert1stUppercase(status.toString()), Result);
		F_GeneralVerification.verifyElementContent(Browser, "Camera", firstRow.get(5), camera, Result);
	}

	public static void verifyBasicContraventionDetails(SeleniumBrowser Browser, String status, String camera,
			String time, String user, TCResult Result) {
		O_contravention newconv = F_CPContraventions.getDetails(Browser);
		F_GeneralVerification.verifyElementContent(Browser, "Status", newconv.Status, status, Result);
		F_GeneralVerification.verifyElementContent(Browser, "Observed Time", newconv.ObservedTime, time, Result);
		F_GeneralVerification.verifyElementContent(Browser, "Video Ref", newconv.VideoRef, CPValueList.videoRef,
				Result);
		F_GeneralVerification.verifyElementContent(Browser, "Camera", newconv.Camera, camera, Result);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Count", I_CP_Contraventions.txt_count, Result);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Coordinates", I_CP_Contraventions.txt_Coordinates,
				Result);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "ID", I_CP_Contraventions.txt_IDinDetails, Result);
	}

	public static void checkSavebuttonVisible(SeleniumBrowser Browser, TCResult Result) {
		F_GeneralVerification.verifyElementVisible(Browser, I_CP_Contraventions.btn_Save, "Save button", Result);
	}

	public static void checkCompletebuttonVisible(SeleniumBrowser Browser, TCResult Result) {
		F_GeneralVerification.verifyElementVisible(Browser, I_CP_Contraventions.btn_Complete, "Complete button",
				Result);
	}

	public static void checkVoidbuttonVisible(SeleniumBrowser Browser, TCResult Result) {
		F_GeneralVerification.verifyElementVisible(Browser, I_CP_Contraventions.btn_Void, "Void button", Result);
	}

	public static void checkUnvoidbuttonVisible(SeleniumBrowser Browser, TCResult Result) {
		F_GeneralVerification.verifyElementVisible(Browser, I_CP_Contraventions.btn_Unvoid, "Void button", Result);
	}

	public static void checkNextbuttonVisible(SeleniumBrowser Browser, TCResult Result) {
		F_GeneralVerification.verifyElementVisible(Browser, I_CP_Contraventions.btn_Next, "Next button", Result);
	}

	public static void checkReviewbuttonVisible(SeleniumBrowser Browser, TCResult Result) {
		F_GeneralVerification.verifyElementVisible(Browser, I_CP_Contraventions.btn_Review, "Next button", Result);
	}

	public static void checkSavebuttonInvisible(SeleniumBrowser Browser, TCResult Result) {
		F_GeneralVerification.verifyElementInvisible(Browser, I_CP_Contraventions.btn_Save, "Save button", Result);
	}

	public static void checkCompletebuttonInvisible(SeleniumBrowser Browser, TCResult Result) {
		F_GeneralVerification.verifyElementInvisible(Browser, I_CP_Contraventions.btn_Complete, "Complete button",
				Result);
	}

	public static void checkVoidbuttonInvisible(SeleniumBrowser Browser, TCResult Result) {
		F_GeneralVerification.verifyElementInvisible(Browser, I_CP_Contraventions.btn_Void, "Void button", Result);
	}

	public static void checkNextbuttonInvisible(SeleniumBrowser Browser, TCResult Result) {
		F_GeneralVerification.verifyElementInvisible(Browser, I_CP_Contraventions.btn_Next, "Next button", Result);
	}

	public static void checkReviewbuttonInvisible(SeleniumBrowser Browser, TCResult Result) {
		F_GeneralVerification.verifyElementInvisible(Browser, I_CP_Contraventions.btn_Review, "Next button", Result);
	}

	public static void checkButtons(SeleniumBrowser Browser, String status, TCResult Result) {
		switch (status) {
		case "NEW": {
			F_CPVerification.checkVoidbuttonVisible(Browser, Result);
			F_CPVerification.checkNextbuttonVisible(Browser, Result);
			F_CPVerification.checkSavebuttonVisible(Browser, Result);
			F_CPVerification.checkCompletebuttonVisible(Browser, Result);
		}
			break;
		case "VOID": {
			F_CPVerification.checkVoidbuttonInvisible(Browser, Result);
			F_CPVerification.checkCompletebuttonInvisible(Browser, Result);
			checkReviewbuttonInvisible(Browser, Result);
			checkUnvoidbuttonVisible(Browser, Result);
			// F_CPVerification.checkSavebuttonInvisible(Browser, Result);
		}
			break;
		case "COMPLETED": {
			F_CPVerification.checkVoidbuttonVisible(Browser, Result);
			F_CPVerification.checkNextbuttonVisible(Browser, Result);
			F_CPVerification.checkSavebuttonVisible(Browser, Result);
			F_CPVerification.checkCompletebuttonInvisible(Browser, Result);
			F_CPVerification.checkReviewbuttonVisible(Browser, Result);
		}
			break;
		case "REVIEWED": {
			F_CPVerification.checkVoidbuttonInvisible(Browser, Result);
			F_CPVerification.checkNextbuttonInvisible(Browser, Result);
			// F_CPVerification.checkSavebuttonInvisible(Browser, Result);
			F_CPVerification.checkCompletebuttonInvisible(Browser, Result);
			checkReviewbuttonInvisible(Browser, Result);
		}
			break;
		}
	}

	public static void checkID(SeleniumBrowser Browser, String expectedValue, TCResult Result) {
		O_contravention conv = F_CPContraventions.getDetails(Browser);
		F_GeneralVerification.verifyElementContent(Browser, "ID", conv.Id, expectedValue, Result);
	}

	public static void checkStatus(SeleniumBrowser Browser, String expectedStatus, TCResult Result) {
		O_contravention conv = F_CPContraventions.getDetails(Browser);
		F_GeneralVerification.verifyElementContent(Browser, "Status", conv.Status, expectedStatus, Result);
	}

	public static void checkDesc(SeleniumBrowser Browser, String expectedvalue, TCResult Result) {
		O_contravention conv = F_CPContraventions.getDetails(Browser);
		F_GeneralVerification.verifyElementContent(Browser, "Description", conv.Desc, expectedvalue, Result);
	}

	public static void checkVRM(SeleniumBrowser Browser, String expectedVRM, TCResult Result) {
		O_contravention conv = F_CPContraventions.getDetails(Browser);
		F_GeneralVerification.verifyElementContent(Browser, "Vehicle Number", conv.Vrm, expectedVRM, Result);
	}

	public static void checkStreet(SeleniumBrowser Browser, String expectedValue, TCResult Result) {
		O_contravention conv = F_CPContraventions.getDetails(Browser);
		F_GeneralVerification.verifyElementContent(Browser, "Street address", conv.Street, expectedValue, Result);
	}

	public static void checkManufacturer(SeleniumBrowser Browser, String expectedValue, TCResult Result) {
		O_contravention conv = F_CPContraventions.getDetails(Browser);
		F_GeneralVerification.verifyElementContent(Browser, "Manufacturer", conv.Manufacturer, expectedValue, Result);
	}

	public static void verifyVoidReason(SeleniumBrowser Browser, String expectedVoidReason, String Desc,
			TCResult Result) {
		String pActualVR = Functions.getValue(Browser, I_CP_Contraventions.txt_VoidReason);

		if (expectedVoidReason != "Others" || expectedVoidReason != "Unclear") {
			F_GeneralVerification.verifyElementContent(Browser, "Void reason", pActualVR, expectedVoidReason, Result);
		} else {
			F_GeneralVerification.verifyElementContent(Browser, "Void reason", pActualVR, expectedVoidReason, Result);
			F_GeneralVerification.verifyElementContent(Browser, "Void reason description", pActualVR, Desc, Result);
		}

	}

	public static void verifyNotEmptyMessage(SeleniumBrowser Browser, String pfieldName, TCResult pResult) {

		String m = pfieldName + " must not be empty";
		By pText = By.xpath("//label[contains(.,'" + pfieldName + "')]/../div/div[contains(.,'" + m + "')]");
		F_GeneralVerification.verifyElementVisible(Browser, pText, pfieldName, pResult);

	}

	public static void verifyImageNumber(SeleniumBrowser Browser, String pfieldName, TCResult pResult) {

	}

	// ------ Searching page-----------
	public static void verifyOnlyOneResultReturned(SeleniumBrowser Browser, TCResult pResult) {

	}

	public static void verifyResultNotEmpty(SeleniumBrowser Browser, TCResult pResult) {
		WebElement table = Browser.captureInterface(By.xpath("//table/tbody/tr[1]/td[1]"));
		if (table == null) {
			pResult.SetResult(false);
			pResult.SetMessage("Result is Empty!");
		}
	}

	// ---------------------
	public static void verifyImage(SeleniumBrowser Browser, TCResult pResult) {
		String data = "Imgs/Evidence.png";
		Functions.waitForSeconds(3);
		Mouse.MoveMouseToElement(Browser, I_AIMS_Incident_Details.Evidence.frm_Video);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_PlayPause);

		F_GeneralVerification.verifyImageOnpage(data, pResult);
	}

	// public static void verifyImageCreated(SeleniumBrowser Browser,int pAcutual,
	// int pExpected, TCResult pResult) {
	// int noOfImages = F_CPContraventions.imageCount(Browser);
	// if(noOfImages - originalImages != 1) {
	// pResult.SetResult(false);
	// pResult.SetMessage("Num of Images is displayed " + noOfImages + " instead of
	// " + originalImages + 1);
	// }
	// }

	// -------------- Enforcement Log ----------
	public static void verifyVoidContraventionLog(SeleniumBrowser Browser, O_contravention pExpectedConv,
			TCResult pResult) {
		java.awt.List contraventionLog = F_CPEnformentLog.findContravention(Browser, pExpectedConv.Id);
		if (contraventionLog == null) {
			pResult.SetResult(false);
			pResult.SetMessage("Can not find the contravention " + pExpectedConv.Id);
		} else {
			F_GeneralVerification.verifyElementContent(Browser, "TIME", contraventionLog.getItem(0),
					pExpectedConv.ObservedTime, pResult);
			F_GeneralVerification.verifyElementContent(Browser, "ID", contraventionLog.getItem(1), pExpectedConv.Id,
					pResult);
			F_GeneralVerification.verifyElementContent(Browser, "COMMENTS", contraventionLog.getItem(6),
					pExpectedConv.VoidReason, pResult);
			if (pExpectedConv.VoidReasonDesc != null) {
				F_GeneralVerification.verifyElementContent(Browser, "COMMENTS", contraventionLog.getItem(6),
						pExpectedConv.VoidReasonDesc, pResult);
			}
		}

	}

	public static void verifyReviewedContraventionLog(SeleniumBrowser Browser, O_contravention pExpectedConv,
			TCResult pResult) {
		java.awt.List contraventionLog = F_CPEnformentLog.findContravention(Browser, pExpectedConv.Id);
		if (contraventionLog == null) {
			pResult.SetResult(false);
			pResult.SetMessage("Can not find the contravention " + pExpectedConv.Id);
		} else {
			F_GeneralVerification.verifyElementContent(Browser, "TIME", contraventionLog.getItem(0),
					pExpectedConv.ObservedTime, pResult);
			F_GeneralVerification.verifyElementContent(Browser, "ID", contraventionLog.getItem(1), pExpectedConv.Id,
					pResult);
			F_GeneralVerification.verifyElementContent(Browser, "CAMERA ID", contraventionLog.getItem(2),
					pExpectedConv.Camera, pResult);
			// F_GeneralVerification.verifyElementContent(Browser, "LOCATION",
			// contraventionLog.getItem(3), pExpectedConv.Street, pResult);
			F_GeneralVerification.verifyElementContent(Browser, "VRM", contraventionLog.getItem(4), pExpectedConv.Vrm,
					pResult);
			F_GeneralVerification.verifyElementContent(Browser, "Offence Code", contraventionLog.getItem(5),
					pExpectedConv.OffenceCode, pResult);
		}
	}

	public static void verifyImportJson(SeleniumBrowser Browser, String exceptionDateFrom, String cameraEncoderNumber,
			List vrmNumber, TCResult pResult) {
		for (int n = 0; n < vrmNumber.size(); n++) {
//			F_CPVerification.verifyVRMexistinTable(Browser, vrmNumber.get(n).toString(),
//					F_CP_common.Camera(cameraEncoderNumber), exceptionDateFrom,exceptionDateTo,  pResult);
		}
	}

	public static void verifyImport(SeleniumBrowser Browser, List exceptionDateFrom, List exceptionDateTo, List cameraEncoderNumber,
			List vrmNumber, TCResult pResult) {
		for (int n = 0; n < vrmNumber.size(); n++) {
			String fromDate = Functions.changeDateFormat(exceptionDateFrom.get(n).toString(), Constants.Dateformat);
			String toDate = Functions.changeDateFormat(exceptionDateTo.get(n).toString(), Constants.Dateformat);
			F_CPVerification.verifyVRMexistinTable(Browser, vrmNumber.get(n).toString(),
					F_CP_common.Camera(cameraEncoderNumber.get(n).toString()), fromDate, toDate, pResult);
		}
	}
	
	public static void verifyImportWithoutTo(SeleniumBrowser Browser, List exceptionDateFrom, List cameraEncoderNumber,
			List vrmNumber, TCResult pResult) {
		String toDate = "N/A";
		for (int n = 0; n < vrmNumber.size(); n++) {
			String fromDate = Functions.changeDateFormat(exceptionDateFrom.get(n).toString(), Constants.Dateformat);
			
			F_CPVerification.verifyVRMexistinTable(Browser, vrmNumber.get(n).toString(),
					F_CP_common.Camera(cameraEncoderNumber.get(n).toString()), fromDate, toDate, pResult);
		}
	}

	public static void verifyDownloadedFile(SeleniumBrowser Browser, O_contravention conv, String destDir, TCResult pResult) {


		File[] file = Functions.getListOfFile(destDir);
		int v_count = 0;
		int i_count = 0;
		int xml_count = 0;

		if (file.length <= 0) {
			pResult.SetResult(false);
			pResult.SetMessage("File is not exist");
		} else
			for (int i = 0; i < file.length; i++) {
				String name = file[i].getName();

				if (name.contains(".jpg"))
					i_count++;
				if (name.contains(".xml"))
					xml_count++;
				if (name.contains(".mpg"))
					v_count++;

				if (file[i].length() < 1024) {
					pResult.SetResult(false);
					pResult.SetMessage("File " + name + " is damaged !");
					return;
				}
			}

		if (v_count != 1) {
			pResult.SetResult(false);
			pResult.SetMessage("Video is not available!");
		}

		if (xml_count != 1) {
			pResult.SetResult(false);
			pResult.SetMessage("Xml file is not available!");
		}

		if (i_count != conv.NoOfImages) {
			pResult.SetResult(false);
			pResult.SetMessage("No of Images: " + i_count + " instead of " + conv.NoOfImages);
		}

	}

	public static void verifyXML(SeleniumBrowser Browser, O_contravention conv, TCResult pResult) {

		String destDir = "D:\\QC\\Automation\\Framework\\myFW\\src\\Test Data\\Unzip";

		File[] file = Functions.getListOfFile(destDir);

		String filePath = null;
		for (int i = 0; i < file.length; i++) {
			String name = file[i].getName();
			if (name.contains(".xml")) {
				filePath = file[i].getPath();
				break;
			}
		}
		System.out.println(filePath);
		O_contravention xmlDetail = F_CP_common.getXmlContravention(filePath);

		F_GeneralVerification.verifyElementContent(Browser, "ID", xmlDetail.Id, conv.Id, pResult);
		F_GeneralVerification.verifyElementContent(Browser, "ObservedTime", xmlDetail.ObservedTime, conv.ObservedTime,
				pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Video Ref", xmlDetail.VideoRef, conv.VideoRef, pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Offence Code", conv.OffenceCode, xmlDetail.OffenceCode,
				pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Incident_Type", xmlDetail.IncidentType, conv.OffenceCode,
				pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Street", xmlDetail.Street, conv.Street, pResult);
		F_GeneralVerification.verifyElementContent(Browser, "VRM", xmlDetail.Vrm, conv.Vrm, pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Manufacturer", xmlDetail.Manufacturer, conv.Manufacturer,
				pResult);
		F_GeneralVerification.verifyElementContent(Browser, "Colour", xmlDetail.Colour, conv.Colour, pResult);

		List xmlfileNamelist = F_CP_common.getXmlImageFileInfo(filePath);

		F_CPVerification.verifyCompareFileInFolderAndXml(Browser, xmlfileNamelist, file, pResult);
		// F_GeneralVerification.verifyElementContent(Browser, "File name",
		// xmlFileName.get(0).toString(), file[0].getName(), pResult);
	}

	public static void verifyCompareFileInFolderAndXml(SeleniumBrowser Browser, List fileNamelist, File[] file,
			TCResult pResult) {
		System.out.println(fileNamelist.size());
		boolean f = false;
		int count = 0;
		for (int n = 0; n < file.length - 2; n++) {
			for (int m = 0; m < file.length; n++) {
				if (fileNamelist.get(n).toString() == file[m].getName()) {
					System.out.println(fileNamelist.get(n).toString() + " compare to " + file[m].getName());
					f = true;
					count++;
					continue;
				}
			}

			if (f == false && count != file.length - 1) {
				pResult.SetResult(false);
				pResult.SetMessage("File attachment is incorrect. " + "There are " + count + " correct files");
			}

		}

	}

}
