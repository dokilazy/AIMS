
package libraries.productFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
//import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import libraries.generalFunctions.ExcelReader;
import libraries.objects.VRMExcel;

//@SuppressWarnings("unused")
public class CommonMethods {
	/**
	 * Read the test data from excel file
	 * 
	 * @param data
	 *            The TestData data object
	 */
	public static void readExcelData(VRMExcel data) {
		ArrayList<String> exceptionDate = new ArrayList<String>();
		ArrayList<String> cameraEncoderNumber = new ArrayList<String>();
		ArrayList<String> cameraSite = new ArrayList<String>();
		ArrayList<String> vrmNumber = new ArrayList<String>();

		// Get the data from excel file
		for (int rowCnt = 1; rowCnt < ExcelReader.RowCount(); rowCnt++) {
			exceptionDate.add(ExcelReader.ReadCell(ExcelReader.GetCell("exceptionDate"), rowCnt));
			cameraEncoderNumber.add(ExcelReader.ReadCell(ExcelReader.GetCell("cameraEncoderNumber"), rowCnt));
			cameraSite.add(ExcelReader.ReadCell(ExcelReader.GetCell("cameraSite"), rowCnt));
			vrmNumber.add(ExcelReader.ReadCell(ExcelReader.GetCell("vrmNumber"), rowCnt));
		}

		data.setExceptionDate(exceptionDate);
		data.setCameraEncoderNumber(cameraEncoderNumber);
		data.setCameraSite(cameraSite);
		data.setVrmNumber(vrmNumber);
	}

	public static void CreateSheet()  {
        try {
		// Creating Workbook instances
		Workbook wb = new HSSFWorkbook();

		// An output stream accepts output bytes and sends them to sink.
		OutputStream fileOut = new FileOutputStream("VRM.xlsx");

		// Creating Sheets using sheet object
		Sheet sheet1 = wb.createSheet("VRMlist");
		// Sheet sheet2 = wb.createSheet("String");

		System.out.println("Sheets Has been Created successfully");

		wb.write(fileOut);
        }
        catch (Exception e) {};
	}

	public static void POIforgfgWrite() {
		// // Blank workbook
		// XSSFWorkbook workbook = new XSSFWorkbook();
		//
		// // Create a blank sheet
		// XSSFSheet sheet = workbook.createSheet("student Details");

		// Creating Workbook instances
		Workbook wb = new HSSFWorkbook();

		// Creating Sheets using sheet object
		Sheet sheet1 = wb.createSheet("VRMlist");

		// This data needs to be written (Object[])
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1", new Object[] { "ID", "NAME", "LASTNAME" });
		data.put("2", new Object[] { 1, "Pankaj", "Kumar" });
		data.put("3", new Object[] { 2, "Prakashni", "Yadav" });
		data.put("4", new Object[] { 3, "Ayan", "Mondal" });
		data.put("5", new Object[] { 4, "Virat", "kohli" });

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
			FileOutputStream out = new FileOutputStream(new File("VRM.xlsx"));
			wb.write(out);
			out.close();
			System.out.println("VRM.xlsx written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// --- read a file at different location: Here’s how you can do it:
	private static final String FILE_NAME = "C:\\Users\\pankaj\\Desktop\\projectOutput\\mobilitymodel.xlsx";

	
	//throws FileNotFoundException, IOException
	public static void write()  {
		try {
		InputStream inp = new FileInputStream(FILE_NAME);
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ----You can append to the existing file using following code:
	private static final String FILE_NAME2 = "C:\\Users\\pankaj\\Desktop\\projectOutput\\blo.xlsx";

	public static void writeAppend() throws IOException {
		InputStream inp = new FileInputStream(FILE_NAME);
		
		try {
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0);
		int num = sheet.getLastRowNum();
		Row row = sheet.createRow(++num);
		row.createCell(0).setCellValue("xyz");

		// Now this Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
		wb.write(fileOut);
		fileOut.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void unzip(String zipFilePath, String destDir) {
		File dir = new File(destDir);
		// create output directory if it doesn't exist
		if (!dir.exists())
			dir.mkdirs();
		FileInputStream fis;
		// buffer for read and write data to file
		byte[] buffer = new byte[1024];
		try {
			fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(destDir + File.separator + fileName);
				System.out.println("Unzipping to " + newFile.getAbsolutePath());
				// create directories for sub directories in zip
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				// close this ZipEntry
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			// close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static NodeList getXmlNode(String filePath, String tagName) {
		try {
			File inputFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName(tagName);
			return nList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param filePath
	 * @param xpathText  ex:  = "//flow/TaskID/text()"
	 * @return
	 */
	public static List getXmlNodeByXpath(String filePath, String xpathText) {
		List eList = new ArrayList();

		File inputFile = new File(filePath);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = dbFactory.newDocumentBuilder();

			Document document = builder.parse(inputFile);

			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile(xpathText);

			Object result = expr.evaluate(document, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			for (int i = 0; i < nodes.getLength(); i++) {
				eList.add(nodes.item(i).getTextContent());
			}
			
			return eList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}

