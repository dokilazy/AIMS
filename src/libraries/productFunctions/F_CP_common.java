package libraries.productFunctions;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import libraries.generalFunctions.Log;
import libraries.objects.O_contravention;
import interfaces.I_CP_Common;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Log;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import java.io.*;

public class F_CP_common {
	public static void selectRow(SeleniumBrowser Browser, int rowNumber) {
		Browser.click(By.xpath("//table/tbody/tr[" + rowNumber + "]/td[1]"));
		Functions.waitForSeconds(0.5);
	}

	public static void closeTab(SeleniumBrowser Browser) {
		Browser.clickJavascript(I_CP_Common.btn_closeLastTab);
		Functions.waitForSeconds(0.5);
	}

	public static String randomDVLA_VRM() {
		String vrm = "";
		vrm = randomfirstMemoryTab() + randomsecondMemoryTab() + randomAgeId() + randomLetters();
		return vrm;
	}

	public static String randomfirstMemoryTab() {

		String lexicon = "ABCDEFGHKLMNOPRSVWY";

		Random rand = new Random();
		Set<String> identifiers = new HashSet<String>();

		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			int length = 1;
			for (int i = 0; i < length; i++) {
				builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
			}
			if (identifiers.contains(builder.toString())) {
				builder = new StringBuilder();
			}
		}
		return builder.toString();
	}

	public static String randomsecondMemoryTab() {
		String lexicon = "ABCDEFGHJKLMNOPRSUVWXY";
		Random rand = new Random();
		Set<String> identifiers = new HashSet<String>();

		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			int length = 1;
			for (int i = 0; i < length; i++) {
				builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
			}
			if (identifiers.contains(builder.toString())) {
				builder = new StringBuilder();
			}
		}
		return builder.toString();
	}

	public static String randomLetters() {
		String lexicon = "ABCDEFGHJKLMNOPRSUVWXYZ";

		Random rand = new Random();
		Set<String> identifiers = new HashSet<String>();

		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			int length = 3;
			for (int i = 0; i < length; i++) {
				builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
			}
			if (identifiers.contains(builder.toString())) {
				builder = new StringBuilder();
			}
		}
		return builder.toString();
	}

	public static String randomAgeId() {

		String ageId = "";
		do
			ageId = Functions.randomNumberString(2);

		while (ageId.contains("00") || ageId.contains("01"));

		return ageId;
	}

	public static String getRandomEncoderNo() {
		String[] cam = {"1","3", "5"};
		
		int i = Functions.randomInterger(0, cam.length - 1);
		return cam[i];
	}

	public static void randomListDVLA(int count) {
		String vrm = "";
		for (int i = 0; i < count; i++) {
			vrm = randomDVLA_VRM();
			// Log.info(""+vrm+" /r/n");
			Log.info(vrm);
		}
	}

	public static String getStreetName(String encoder) {

		String street = "";
		switch (encoder) {
		case "1":
			//street = "Railway Station";
			street = "DOME";
			break;
		case "2":
//			street = "Park Street - Car Park";
			
			street = "PTZ 2";break;
		case "3":
//			street = "Commercial Road";
			street = "PTZ";
			break;
		case "4":
//			street = "CCTV Control Room Entrance Door";
			street = "Panomera";
			break;
			
		case "5":
//			street = "CCTV Control Room Entrance Door";
			street = "Dahua";
			break;
			
		case "6":
//			street = "CCTV Control Room Entrance Door";
			street = "Panomera S7";
			break;
		}
		return street;
	}

	public static String Camera(String encoder, String site, String street) {
		return encoder + "-" + site + "-" + street;
	}

	public static String Camera(String encoder) {
		// String msite = "0";
		// return encoder + "-" + msite + "-" + getStreetName(encoder, msite);
		return encoder + "-" + getStreetName(encoder);
	}

	public static void waitMsgDisappear() {
		Functions.waitForSeconds(5);
	}

	public static void selectPageSize(SeleniumBrowser Browser, String NoOfItem) {
		Browser.click(I_CP_Common.btn_showPageSize);
		Browser.selectDivDropdownByText(Browser, I_CP_Common.div_PageSize, NoOfItem);
		Functions.waitForSeconds(0.5);
	}

	public static void nextPagination(SeleniumBrowser Browser) {
		int total = countPagination(Browser);
		int current = getCurrentPagination(Browser);
		if (current < total) {
			Browser.click(I_CP_Common.btn_nextpagination);
			Functions.waitForSeconds(0.5);
		}
	}

	public static int getCurrentPagination(SeleniumBrowser Browser) {
		String n = Browser.captureInterface(I_CP_Common.lbl_activepagination).getText().toString();
		return Functions.convertStringToInteger(n);
	}

	public static int countPagination(SeleniumBrowser Browser) {
		WebElement paginationEle = Browser.captureInterface(I_CP_Common.ul_pagination);
		return paginationEle.findElements(By.tagName("li")).size() - 4;
	}

	public static void createVRMJson(String exceptionDate, String cameraEncoderNumber, List vehicleNo) {
		JSONObject vrm = new JSONObject();
		vrm.put("exceptionDate", exceptionDate);
		JSONObject exemptions = new JSONObject();
		exemptions.put("cameraEncoderNumber", cameraEncoderNumber);
		exemptions.put("cameraSite", "0");

		JSONArray vrmList = new JSONArray();
		for (int n = 0; n < vehicleNo.size(); n++) {
			vrmList.add(vehicleNo.get(n));
		}
		exemptions.put("vrmNumber", vrmList);
		JSONArray exemptionsList = new JSONArray();
		exemptionsList.add(exemptions);
		vrm.put("exemptions", exemptionsList);

		// Write JSON file
		try (FileWriter file = new FileWriter("src/Test Data/vrm.json")) {
			file.write(vrm.toJSONString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createVRMExcel(List exceptionDateFrom, List exceptionDateTo, List cameraEncoderNumber,
			List vehicleNo) {

		// Creating Workbook instances
		Workbook wb = new HSSFWorkbook();

		// Creating Sheets using sheet object
		Sheet sheet1 = wb.createSheet("VRMlist");

		// This data needs to be written (Object[])
		Map<String, Object[]> data = new TreeMap<String, Object[]>();

		data.put("1", new Object[] { "VRM", "encoder", "site", "from", "to" });

		for (int i = 0; i < vehicleNo.size(); i++) {
			int rowNo = i + 2;
			data.put("" + rowNo + "", new Object[] { vehicleNo.get(i), cameraEncoderNumber.get(i), "0",
					exceptionDateFrom.get(i), exceptionDateTo.get(i) });
		}

		// Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			// this creates a new row in the sheet
			Row row = sheet1.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				// this line creates a cell in the next column of that row
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}
		try {
			// this Writes the workbook gfgcontribute
			String Filename = Functions.getCurrDirectory() + "\\" + Constants.TestData + "\\VRM.xlsx";
			FileOutputStream out = new FileOutputStream(new File(Filename));
			wb.write(out);
			out.close();
			System.out.println("VRM.xlsx written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createVRMCSV(List exceptionDateFrom, List exceptionDateTo, List cameraEncoderNumber,
			List vehicleNo) throws IOException {
		
		List<List<String>> rows = Arrays.asList();

		FileWriter csvWriter = new FileWriter(Constants.UploadVRMFilePath + "\\VRM.csv");
		csvWriter.append("VRM");
		csvWriter.append(",");
		csvWriter.append("encoder");
		csvWriter.append(",");
		csvWriter.append("site");
		csvWriter.append(",");
		csvWriter.append("from");
		csvWriter.append(",");
		csvWriter.append("to");
		csvWriter.append("\n");

		for (int i = 0; i < vehicleNo.size(); i++) {
			List<String> rowData = new ArrayList<String>();
			rowData.add(vehicleNo.get(i).toString());
			rowData.add(cameraEncoderNumber.get(i).toString());
			rowData.add(Constants.site);
			rowData.add(exceptionDateFrom.get(i).toString());
			rowData.add(exceptionDateTo.get(i).toString());
			csvWriter.append(String.join(",", rowData));
			csvWriter.append("\n");
		}

		csvWriter.flush();
		csvWriter.close();
	}

	public static void createVRMCSV(List exceptionDateFrom, List cameraEncoderNumber, List vehicleNo)
			throws IOException {
		List<List<String>> rows = Arrays.asList();
		
		FileWriter csvWriter = new FileWriter(Constants.UploadVRMFilePath + "\\VRM.csv");
		csvWriter.append("VRM");
		csvWriter.append(",");
		csvWriter.append("encoder");
		csvWriter.append(",");
		csvWriter.append("site");
		csvWriter.append(",");
		csvWriter.append("from");
		csvWriter.append(",");
		csvWriter.append("to");
		csvWriter.append("\n");

		for (int i = 0; i < vehicleNo.size(); i++) {
			List<String> rowData = new ArrayList<String>();
			rowData.add(vehicleNo.get(i).toString());
			rowData.add(cameraEncoderNumber.get(i).toString());
			rowData.add(Constants.site);
			rowData.add(exceptionDateFrom.get(i).toString());
			// rowData.add(exceptionDateTo.get(i).toString());
			csvWriter.append(String.join(",", rowData));
			csvWriter.append("\n");
		}

		csvWriter.flush();
		csvWriter.close();
	}

	public static List createRandomVrmlist(int s) {
		List vrmNumber = new ArrayList();
		for (int n = 0; n < s; n++) {
			String vrm = F_CP_common.randomDVLA_VRM();
			vrmNumber.add(vrm);
		}
		return vrmNumber;
	}

	public static List createRandomEncoderNoList(int s) {
		List encoderNo = new ArrayList();
		for (int n = 0; n < s; n++) {
			String en = F_CP_common.getRandomEncoderNo();
			encoderNo.add(en);
		}
		return encoderNo;
	}

	/**
	 * get ramdom date
	 * 
	 * @param s
	 * @return format date YYYY-MM-DD
	 */
	public static List createExceptionDateFrom(int s) {
		List exceptionDate = new ArrayList();
		for (int n = 0; n < s; n++) {
			String ex = Functions.createRandomDate(2019, 2019).toString();
			exceptionDate.add(ex);
		}
		return exceptionDate;
	}

	public static List createExceptionDateTo(int s) {
		List exceptionDate = new ArrayList();
		for (int n = 0; n < s; n++) {
			String ex = Functions.createRandomDate(2020, 2020).toString();
			exceptionDate.add(ex);
		}
		return exceptionDate;
	}

	public static O_contravention getXmlContravention(String filePath) {
		O_contravention conv = new O_contravention();

		NodeList xml = CommonMethods.getXmlNode(filePath, "Incident");

		Node incident = xml.item(0);

		if (incident.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) incident;

			conv.Id = eElement.getAttribute("Incident_Ref");
			conv.VideoRef = eElement.getAttribute("Video_Ref");
			conv.ObservedTime = eElement.getAttribute("Incident_Date") + " " + eElement.getAttribute("Incident_Time");
			conv.Id = eElement.getAttribute("Incident_Ref");
			conv.OffenceCode = eElement.getAttribute("Offence_Code");
			conv.Vrm = eElement.getAttribute("VRM");
			conv.Manufacturer = eElement.getAttribute("Make");
			conv.Colour = eElement.getAttribute("Colour");
			conv.Street = eElement.getAttribute("Location");
			conv.IncidentType = eElement.getAttribute("Incident_Type");
			System.out.println(conv.IncidentType);
		}
		return conv;
	}

	public static List getXmlImageFileInfo(String filePath) {
		// List imageFilelist = new ArrayList();

		// NodeList xml = CommonMethods.getXmlNode(filePath, "Image_File");
		//
		// for(int i = 0; i < xml.getLength(); i++) {
		// Node image = xml.item(i);
		// Element eElement = (Element) image;
		// imageFilelist.add(eElement.getAttribute("File_Name").toString());
		// }

		String xpathText = "//Incident/Images/Image_File/File_Name/text()";
		List imageFilelist = CommonMethods.getXmlNodeByXpath(filePath, xpathText);

		System.out.println(imageFilelist.size());
		return imageFilelist;
	}

}
