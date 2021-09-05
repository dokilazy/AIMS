package libraries.productFunctions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import interfaces.I_AIMS_Incident_Details;
import interfaces.I_CP_Common;
import interfaces.I_CP_Contraventions;
import interfaces.I_CP_Settings;
import interfaces.I_Global_Common;
import libraries.CPValueList;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Mouse;
import libraries.objects.O_contravention;
import libraries.verificationFunctions.F_GeneralVerification;

public class F_CPContraventions {

	public static void createNewContravention(String camId, String user, Long timestamp, double[] coordinate,
			boolean isVoid, String VoidReason) {
		String json = generateJsonMsg(camId, user, timestamp, coordinate, isVoid, VoidReason);
		String btn_Simulator = "Imgs/Simulator.png";

		String txt_Message = "Imgs/txt_jsonMessage.png";
		String btn_Send = "Imgs/btn_SendMessage.png";
		String btn_Minimize = "Imgs/Minimize.png";
		Functions.clickBySikuli(btn_Simulator);

		Functions.pasteValueBySikuli(txt_Message, json);
		// Functions.pasteValueBySikuli(json, 940, 580);
		Functions.clickBySikuli(btn_Send);
		// Functions.clickBySikuli(btn_Minimize);
		Functions.clickBySikuli(btn_Simulator);
	}

	public static void createContravention2(int n, String encoderNo, String user, Long timestamp, double[] coordinate,
			boolean isVoid, String VoidReason) {
		int i = 0;
		String json = "";
		String btn_Simulator = "Imgs/Simulator.png";
		String header = "Imgs/TracerHeader.png";
		String txt_Message = "Imgs/txt_jsonMessage.png";
		String btn_Send = "Imgs/btn_SendMessage.png";
		String btn_Minimize = "Imgs/Minimize.png";
		String lbl_freeMouse = "Imgs/freeMouse.png";
		Functions.clickBySikuli(btn_Simulator);

		json = generateJsonMsg(encoderNo, user, timestamp, coordinate, isVoid, VoidReason);
		// Functions.clickBySikuli(header);
		Functions.clickBySikuli(lbl_freeMouse);
		Functions.pasteValueBySikuli(txt_Message, json);
		// Functions.pasteValueBySikuli(json, 940, 560);
		Functions.waitForSeconds(0.5);
		Functions.clickBySikuli(btn_Send);
		// Functions.clickBySikuli(txt_Message);

		Functions.clickBySikuli(btn_Minimize);
	}

	public static String generateJsonMsg(String encoderNo, String user, Long timestamp, double[] coordinate,
			boolean isVoid, String VoidReason) {

		String json = "";
		String coordinateArray = "";

		System.out.println(timestamp.toString());
		String uuid = Functions.randomUUID().toUpperCase();
		if (coordinate != null)
			coordinateArray = "[" + coordinate[0] + ", " + coordinate[1] + ", 0]";

		if (coordinate == null) {
			coordinateArray = "[0,0,0]";
		}

		json = "{\r\n" + "	\"encoderName\": \"VCA5200.154\",\r\n" + "	\"voidContravention\": {\r\n"
				+ "		\"reason\": \"" + VoidReason + "\",\r\n" + "		\"statement\": \"\",\r\n"
				+ "		\"voided\": " + isVoid + "\r\n" + "	},\r\n" + "	\"systemLocationCode\": 0,\r\n"
				+ "	\"author\": {\r\n" + "		\"username\": \"" + user + "\"\r\n" + "	},\r\n" + "	\"fileName\": \""
				+ "" + "\",\r\n" + "	\"criticality\": 100,\r\n"
				+ "	\"preview\": \"http://d114224.dallmeier.de:9003/semsyNext/rest/getFile/3791A85E-CEAA-4F8F-9F3D-27B274E4132D/preview/\",\r\n"
				+ "	\"url\": \"http://d114224.dallmeier.de:9003/semsyNext/rest/getFile/3791A85E-CEAA-4F8F-9F3D-27B274E4132D/mp4/\",\r\n"
				+ "	\"coordinates\": " + coordinateArray + ",\r\n" + "	\"success\": true,\r\n" + "	\"encoderNumber\": "
				+ encoderNo + ",\r\n" + "	\"jobID\": \"" + uuid + "\",\r\n"
				+ "	\"original\": \"http://d114224.dallmeier.de:9003/semsyNext/rest/getFile/3791A85E-CEAA-4F8F-9F3D-27B274E4132D/\",\r\n"
				+ "	\"jobMeta\": {\r\n" + "		\"ID\": \"" + uuid + "\",\r\n" + "		\"jobType\": \"t_david\",\r\n"
				+ "		\"parentID\": \"\",\r\n" + "		\"userID\": 5792,\r\n"
				+ "		\"hostname\": \"D115038\",\r\n" + "		\"cameraName\": \"Camera\",\r\n"
				+ "		\"start\": " + timestamp 
				+",\r\n" + "		\"stop\": 1551951633000,\r\n"
				+ "		\"incident\": \"\",\r\n" + "		\"statement\": \"\",\r\n"
				+ "		\"filepath\": \"\\\\\\\\D114224\\\\backupfiles\\\\20190307\\\\3791A85E-CEAA-4F8F-9F3D-27B274E4132D.mpg\",\r\n"
				+ "		\"subFolder\": \"\",\r\n" + "		\"subFolderID\": 264,\r\n" + "		\"filename\": \"\",\r\n"
				+ "		\"url\": \""+ CPValueList.videoRef +"\",\r\n"
				+ "		\"staffID\": \"ASA#278\",\r\n" + "		\"groupID\": \"\",\r\n"
				+ "		\"ipAddress\": \"10.2.146.228\",\r\n" + "		\"trackID\": 1048576,\r\n"
				+ "		\"username\": \"\",\r\n" + "		\"password\": \"F9C748A7BBADBB686817FB9DB7500313\",\r\n"
				+ "		\"cryptlogin\": false,\r\n" + "		\"areaCode\": "+ Constants.site + ",\r\n" + "		\"encoderNr\": " + encoderNo
				+ ",\r\n" + "		\"input\": 1,\r\n" + "		\"state\": \"p_done\",\r\n"
				+ "		\"progress\": 100,\r\n" + "		\"error\": \"\",\r\n" + "		\"errorCode\": 0,\r\n"
				+ "		\"inserted\": 1551951637000,\r\n" + "		\"updated\": -2209165200000,\r\n"
				+ "		\"fileInfo\": {\r\n" + "			\"originalHeaders\": true,\r\n"
				+ "			\"panomeraBackup\": false,\r\n" + "			\"tracks\": [{\r\n"
				+ "					\"index\": 0,\r\n" + "					\"format\": \"H264\",\r\n"
				+ "					\"width\": 1920,\r\n" + "					\"height\": 1080,\r\n"
				+ "					\"numberOfFrames\": 60,\r\n" + "					\"framerate\": 15,\r\n"
				+ "					\"cameraName\": \"VCA5200.154\",\r\n" + "					\"fishEye\": {\r\n"
				+ "						\"type\": -1,\r\n" + "						\"orientation\": -1\r\n"
				+ "					},\r\n" + "					\"timeOffsets\": [{\r\n"
				+ "							\"pos\": 0,\r\n" + "							\"timestamp\": 1592557537000}],\r\n"
				+ "					\"utcOffsets\": [{\r\n" + "							\"pos\": 0,\r\n"
				+ "							\"utcoffset\": 0\r\n" + "						}\r\n"
				+ "					],\r\n" + "					\"bitrate\": 3732506,\r\n"
				+ "					\"modifiedFrame\": []\r\n" + "				}\r\n" + "			]\r\n"
				+ "		},\r\n" + "		\"apiConfig\": {\r\n"
				+ "			\"value\": \"<DlmSDK>  <DlmConfig><Panomera AllowPanomera=\\\"1\\\" /></DlmConfig> <DlmConfig><Panomera PanomeraMode=\\\"3\\\" /></DlmConfig> </DlmSDK>\"\r\n"
				+ "		},\r\n" + "		\"subItems\": []\r\n" + "	}\r\n" + "}";

		//System.out.print(json);
		return json;

	}

	// --------------------- SEARCH -----------------
	public static void gotoSearchPage(SeleniumBrowser Browser) {
		Browser.clickJavascript(I_CP_Contraventions.tab_Home);
		Browser.click(I_CP_Contraventions.tab_Home);
		Functions.waitForSeconds(1);

	}

	public static void clickReset(SeleniumBrowser Browser, String critera) {
		Browser.captureInterface(
				By.xpath("//div[@class='search-detail']/div[contains(.,'" + critera + "')]/../div[2]/a[1]")).click();
	}

	public static void searchByStatus(SeleniumBrowser Browser, String pValue) {
		String status = Functions.convert1stUppercase(pValue);
		WebElement st = null;
//		st = Browser.captureInterface(
//				By.xpath("//*[@class='dlm-token-btn search-input']/label[contains(.,'" + status + "')]"));
		
		Browser.waitAndClick(By.xpath("//*[@class='dlm-token-btn search-input']/label[contains(.,'" + status + "')]"));
		//st.click();
		Functions.waitForSeconds(2);
	}

	public static void searchByID(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_CP_Contraventions.txt_SearchID, pValue);
		Mouse.FreeMouse(Browser);
		Functions.waitForSeconds(2);
	}

	public static void searchByCamera(SeleniumBrowser Browser, String pValue) {
		Browser.click(I_CP_Contraventions.btn_dropdownCamera);
		Browser.selectDivDropdownByText(Browser, I_CP_Common.div_detailDropdown, pValue.split("-")[0] + "-");
		Functions.waitForSeconds(2);
	}

	public static void searchByUser(SeleniumBrowser Browser, String pValue) {
	//	Browser.enter(I_CP_Contraventions.txt_SearchUser, pValue);
	
//		Browser.click(I_CP_Contraventions.btn_dropdownUser);
		Browser.click(I_CP_Contraventions.txt_SearchUser);
		Browser.selectDivDropdownByText(Browser, I_CP_Common.div_detailDropdown, pValue);
		Functions.waitForSeconds(2);
	}

	public static void searchByVRM(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_CP_Contraventions.txt_SearchVRM, pValue);
		Mouse.FreeMouse(Browser);
		Functions.waitForSeconds(1);
	}

	public static void selectContravention(String ID) {

	}

	public static void clickView(SeleniumBrowser Browser) {
		Browser.click(I_CP_Contraventions.btn_ViewInToolbar);
		Functions.waitForSeconds(1);
	}

	public static void hideSearchpanel() {
	}

	public static int contraventionNumber() {
		int conNo = 0;

		return conNo;
	}

	public static void sortContravention(SeleniumBrowser Browser, String columnName, boolean IsDes) {
		Browser.Driver.findElement(By.xpath("//table/thead/tr/th[contains(.,'" + columnName + "')]")).click();
		Functions.waitForSeconds(2);
	}

	public static String openLatestContravention(SeleniumBrowser Browser) {
		// sort
		Functions.waitForSeconds(1);
		String id = "";
		sortContravention(Browser, CPValueList.ContraventionColumn.time, true);
		List<String> RowData = Functions.getFirstRow(Browser);
		viewFirstEntry(Browser);
		return RowData.get(0);
	}

	public static String viewFirstEntry(SeleniumBrowser Browser) {

		List<String> RowData = Functions.getFirstRow(Browser);
		
		if (RowData == null)
			return null;
		F_CP_common.selectRow(Browser, 1);
		Functions.waitForSeconds(0.5);
		clickView(Browser);
		System.out.println("Open contravention:" + RowData.get(0));
		return RowData.get(0);
	}

	public static List<String> getListOfConvId(SeleniumBrowser Browser) {
		return Functions.getColumnData(Browser, 1);
	}

	// --------------- Details -------------------------
	public static void clickSave(SeleniumBrowser Browser) {
		Browser.click(I_CP_Contraventions.btn_Save);
		Functions.waitForSeconds(0.5);
	}

	public static void clickReview(SeleniumBrowser Browser) {
		Browser.click(I_CP_Contraventions.btn_Review);
		Functions.waitForSeconds(0.5);
	}

	public static void clickComplete(SeleniumBrowser Browser) {
		Browser.click(I_CP_Contraventions.btn_Complete);
		Functions.waitForSeconds(0.5);
	}

	public static void clickVoid(SeleniumBrowser Browser) {
		Browser.click(I_CP_Contraventions.btn_Void);
		Functions.waitForSeconds(0.5);
	}

	public static void clickUnVoid(SeleniumBrowser Browser) {
		Browser.click(I_CP_Contraventions.btn_Unvoid);
		Functions.waitForSeconds(0.5);
	}

	public static void clickNext(SeleniumBrowser Browser) {
		Browser.click(I_CP_Contraventions.btn_Next);
		Functions.waitForSeconds(1);
	}

	public static void clickVideoTab(SeleniumBrowser Browser) {
		Browser.click(I_CP_Contraventions.tab_Video);
		Functions.waitForSeconds(0.5);
	}

	public static void clickAreaTab(SeleniumBrowser Browser) {
		Browser.click(I_CP_Contraventions.tab_Area);
		Functions.waitForSeconds(0.5);
	}

	public static O_contravention getDetails(SeleniumBrowser Browser) {
		O_contravention conv = new O_contravention();
		conv.Id = Functions.getValue(Browser, I_CP_Contraventions.txt_IDinDetails);
		conv.Status = Functions.getValue(Browser, I_CP_Contraventions.txt_Status);
		conv.ObservedTime = Browser.captureInterface(I_CP_Contraventions.txt_time).getText();
		conv.OffenceCode = Functions.getValue(Browser, I_CP_Contraventions.txt_OffenceCode);
		conv.Desc = Functions.getValue(Browser, I_CP_Contraventions.txt_desc);
		// if(Browser.captureInterface(I_CP_Contraventions.span_VRM) != null)
		if (Browser.captureInterface(By.xpath("//ng-select[@formcontrolname='vrmId']/div")).getAttribute("class")
				.toString().contains("ng-has-value"))
			conv.Vrm = Functions.getText(Browser, I_CP_Contraventions.span_VRM);
		conv.VideoRef = Functions.getValue(Browser, I_CP_Contraventions.txt_videoRef);
		// conv.Diplomatic = Functions.getValue(Browser,
		// I_CP_Contraventions.chk_Diplomatic);
		if (conv.Status.contains("VOID")) {
			conv.VoidReason = Functions.getValue(Browser, I_CP_Contraventions.txt_VoidReason);
		}
		if (conv.VoidReason == "Others" || conv.VoidReason == "Unclear") {
			conv.VoidReasonDesc = Functions.getValue(Browser, I_CP_Contraventions.txt_VoidReasonText);
		}
		conv.Camera = Functions.getValue(Browser, I_CP_Contraventions.txt_Camera);
		conv.Street = Functions.getValue(Browser, I_CP_Contraventions.txt_Street);
		conv.Manufacturer = Functions.getValue(Browser, I_CP_Contraventions.txt_Manufacturer);
		conv.Model = Functions.getValue(Browser, I_CP_Contraventions.txt_Model);
		Browser.scrollToEle(I_CP_Contraventions.txt_Colour);
		O_contravention.Colour = Functions.getValue(Browser, I_CP_Contraventions.txt_Colour);
		
		
		return conv;
	}

	public static String selectVoidReason(SeleniumBrowser Browser, boolean IdDescRequired, String desc) {
		String vr = "";
		Browser.waitForElementVisible(I_CP_Contraventions.sel_voidreason);
		Browser.click(I_CP_Contraventions.sel_voidreason);

		if (IdDescRequired == true && desc != null) {
			Browser.selectDropdownByText(I_CP_Contraventions.sel_voidreason, "Others");
			Browser.enter(I_CP_Contraventions.txt_descVR, desc);
		} else {
			vr = Browser.selectRandomDropdown(Browser, I_CP_Contraventions.sel_voidreason, "option");
		}
		Browser.click(I_CP_Contraventions.btn_saveVR);
		return vr;
	}

	public static String selectRandomValue(SeleniumBrowser Browser, By dropdownEle) {
		Browser.click(dropdownEle);
		Functions.waitForSeconds(1);
		String value = Browser.selectRandomDropdown(Browser, I_CP_Common.div_detailDropdown);
		Functions.waitForSeconds(1);
		// --- close dropdown ----
		// if(Browser.captureInterface(I_CP_Common.div_detailDropdown) != null)
		// Browser.click(dropdownEle);
		return value;
	}

	public static O_contravention selectRandomMandatoryFields(SeleniumBrowser Browser) {
		O_contravention conv = new O_contravention();
		Functions.waitForSeconds(1);
		do {
		conv.OffenceCode = selectRandomValue(Browser, I_CP_Contraventions.txt_OffenceCode);}
		while(conv.OffenceCode.contains("UNCATEGORIZED") || conv.OffenceCode.contains("TRANSPORT"));
		conv.Street = selectRandomValue(Browser, I_CP_Contraventions.txt_Street);
		conv.Manufacturer = selectRandomValue(Browser, I_CP_Contraventions.txt_Manufacturer);
		conv.Colour = selectRandomValue(Browser, I_CP_Contraventions.txt_Colour);
		return conv;
	}

	public static String inputDVLAVehicleNo(SeleniumBrowser Browser) {
		String vrm = F_CP_common.randomDVLA_VRM();
		Browser.enter(I_CP_Contraventions.txt_VehicleNo, vrm);
		Browser.click(I_CP_Contraventions.span_addItem);
		return vrm;
	}

	public static String inputNonDVLAVehicleNo(SeleniumBrowser Browser) {
		String vrm = Functions.randomText(7);
		Browser.enter(I_CP_Contraventions.txt_VehicleNo, vrm);
		Browser.click(I_CP_Contraventions.span_addItem);
		return vrm;
	}

	public static String inputDesc(SeleniumBrowser Browser) {
		String desc = Functions.randomText(20);
		Browser.enter(I_CP_Contraventions.txt_desc, desc);
		return desc;
	}

	public static O_contravention reviewContravention(SeleniumBrowser Browser) {

		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.NEW.toString());
		String convId = F_CPContraventions.openLatestContravention(Browser);

		F_CPContraventions.clickComplete(Browser);
		O_contravention conv = F_CPContraventions.selectRandomMandatoryFields(Browser);
		String vrm = F_CPContraventions.inputDVLAVehicleNo(Browser);
		F_CPContraventions.clickVideoTab(Browser);
		F_CPContraventions.captureImage(Browser);
		F_CPContraventions.captureImage(Browser);
		F_CPContraventions.clickReview(Browser);

		F_CP_common.closeTab(Browser);
		conv.Vrm = vrm;
		conv.Id = convId;
		return conv;
	}

	public static void selectVRM(SeleniumBrowser Browser, String vrm) {
		Browser.click(I_CP_Contraventions.txt_VehicleNo);
		Browser.enter(I_CP_Contraventions.txt_VehicleNo, vrm.substring(0, 1));
		Functions.waitForSeconds(0.5);
		Browser.selectDivDropdownByText(Browser, I_CP_Common.div_detailDropdown, vrm);
	}

	public static void clickConfirmOk(SeleniumBrowser Browser) {
		// Browser.waitForElementVisible(I_CP_Common.btn_OkConfirm);
		Browser.click(I_CP_Common.btn_OkConfirm);
	}

	// -------------- Video Player ---------

	public static void captureImage(SeleniumBrowser Browser) {

		// Mouse.NavigateAndClick(Browser,
		// I_AIMS_Incident_Details.Evidence.btn_PlayPause);
		clickPlayPause(Browser);
		// Mouse.NavigateAndClick(Browser,
		// I_AIMS_Incident_Details.Evidence.btn_Capture);
		Functions.waitForSeconds(3);
		clickSnapshoot(Browser);
		clickPlayPause(Browser);
	}

	public static void clickPlayPause(SeleniumBrowser Browser) {
		Mouse.MoveMouseToElement(Browser, I_CP_Contraventions.frm_Video);
		Browser.click(I_CP_Contraventions.btn_PlayPause);
	}

	public static void clickSnapshoot(SeleniumBrowser Browser) {
		Mouse.MoveMouseToElement(Browser, I_CP_Contraventions.frm_Video);
		Browser.click(I_CP_Contraventions.btn_Capture);
	}

	public static int imageCount(SeleniumBrowser Browser) {
		Browser.setTimeOut(Browser, 4);

		WebElement imagePanel = null;
		imagePanel = Browser.captureInterface(By.xpath("//div[@id='image-capture']"));
		List<WebElement> imageList = imagePanel.findElements(By.xpath("div"));
		Browser.resetTimeOut(Browser);
		return imageList.size();
	}

	public static void deleteRandomImage(SeleniumBrowser Browser) {
		WebElement imagePanel = null;
		imagePanel = Browser.captureInterface(By.xpath("//div[@id='image-capture']"));
		List<WebElement> imageList = imagePanel.findElements(By.xpath("div"));

		int count = imageList.size();
		if (count > 0) {
			// imageList
			int s = 1;
			Random r = new Random();
			s = r.nextInt(count) + 1;

			WebElement item = imageList.get(s - 1).findElement(By.tagName("button"));
			// Browser.scrollToEle(item);
			item.click();
		}
	}

	public static void clickDownload(SeleniumBrowser Browser) {
		Browser.click(I_CP_Contraventions.btn_Download);
		//-- wait for downloading finish --
		Functions.waitForSeconds(10);
	}


	
	
	

}
