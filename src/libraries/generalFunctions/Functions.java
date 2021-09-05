package libraries.generalFunctions;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.MessageFormat;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TimeZone;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.FileNotFoundException;
//import java.awt.List;
import java.awt.Toolkit;
import java.util.UUID;
//import executionEngine.RunTestscript;
//import utility.Log;

import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.Test;

import libraries.CPValueList;
import libraries.Constants;
import libraries.SeleniumBrowser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.App;

public class Functions {

	public static void LogMethodName() {
		Log.info(MessageFormat.format("Method {0} throughed", Thread.currentThread().getStackTrace().toString()));
	}

	/**
	 * Random a string
	 * 
	 * @param pPrefix Prefix of the string
	 * @param mSuffix Suffix of the string
	 * @return
	 */
	public static String randomString(String pPrefix, String mSuffix) {
		// DateFormat mDateFormat = new SimpleDateFormat("yyMMddHHmmssSSSS");
		DateFormat mDateFormat = new SimpleDateFormat("yyMMddHHmmss");
		Date mDate = new Date();
		return pPrefix + mDateFormat.format(mDate) + mSuffix;
	}

	/**
	 * Random a ANPR Timestamp
	 * 
	 * 
	 * @return
	 */
	public static String randomTimeStamp() {
		// DateFormat mDateFormat = new SimpleDateFormat("yyMMddHHmmssSSSS");
		String timeStamp = "";
		DateFormat mDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat mDateFormat2 = new SimpleDateFormat("HH:mm:ss");
		Date mDate = new Date();
		timeStamp = mDateFormat1.format(mDate) + "T" + mDateFormat2.format(mDate);

		return timeStamp;
	}

	/**
	 * Random a ANPR Timestamp
	 * 
	 * 
	 * @return
	 */
	public static String randomTimeStamp(Date mDate) {
		// DateFormat mDateFormat = new SimpleDateFormat("yyMMddHHmmssSSSS");
		String timeStamp = "";
		DateFormat mDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat mDateFormat2 = new SimpleDateFormat("HH:mm:ss");
		// Date mDate = new Date();
		timeStamp = mDateFormat1.format(mDate) + "T" + mDateFormat2.format(mDate);

		return timeStamp;
	}

	@SuppressWarnings("deprecation")
	public static String getTimeStamp(int dayIndex, int hour) {
		Date time = new Date();

		time.setHours(hour);

		int cdayIn = time.getDay();
		if (cdayIn < (dayIndex - 1)) {
			time.setDate(time.getDate() + dayIndex - 1 - cdayIn);
		}
		if (cdayIn > (dayIndex - 1)) {
			time.setDate(time.getDate() + dayIndex - 1 - cdayIn + 7);
		}

		return Functions.randomTimeStamp(time);

	}

	public static String getCurrentDate() {

		DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date mDate = new Date();
		return mDateFormat.format(mDate);

	}

	public static String getCurrentDateTime() {

		DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		// DateFormat mDateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
		Date mDate = new Date();
		return mDateFormat.format(mDate);
	}

	/**
	 * format time to desired format
	 * 
	 * @param mDate
	 * @param format
	 * @return
	 */
	public static String formatDateTime(Date mDate, String format) {
		DateFormat mDateFormat = new SimpleDateFormat(format);
		return mDateFormat.format(mDate);
	}

	public static int createRandomIntBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	/**
	 * 
	 * @param startYear
	 * @param endYear
	 * @return 1986-03-17 1960-01-08 1970-07-09
	 */
	public static LocalDate createRandomDate(int startYear, int endYear) {
		int day = createRandomIntBetween(1, 28);
		int month = createRandomIntBetween(1, 12);
		int year = createRandomIntBetween(startYear, endYear);
		return LocalDate.of(year, month, day);
	}

	public static String changeDateFormat(String input, String format) {
		DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date mDate = null;
		try {
			mDate = mDateFormat.parse(input);
		} catch (ParseException e) {
		}

		return formatDateTime(mDate, format);
	}

	/**
	 * Randomize a number and return as a string
	 * 
	 * @param isFloat
	 * @return Return a number as a String
	 */
	public static String randomNumber(Boolean isFloat) {
		Random mRan = new Random();
		if (isFloat)
			return mRan.nextInt(11) + "." + mRan.nextInt(11);
		else
			return mRan.nextInt(11) + "";
	}

	/**
	 * Randomize a string Name and return as a string
	 * 
	 * @return Return a String
	 */

	public static String randomText() {

		String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		Random rand = new Random();

		// consider using a Map<String,Boolean> to say whether the identifier is being
		// used or not
		Set<String> identifiers = new HashSet<String>();

		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			int length = rand.nextInt(5) + 5;
			for (int i = 0; i < length; i++) {
				builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
			}
			if (identifiers.contains(builder.toString())) {
				builder = new StringBuilder();
			}
		}
		return builder.toString() + "_TEST";
	}

	/**
	 * Randomize a string and return as a string
	 * 
	 * @param length
	 * @return Return a String
	 */
	public static String randomText(int length) {

		String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		Random rand = new Random();

		// consider using a Map<String,Boolean> to say whether the identifier is being
		// used or not
		Set<String> identifiers = new HashSet<String>();

		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			for (int i = 0; i < length; i++) {
				builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
			}
			if (identifiers.contains(builder.toString())) {
				builder = new StringBuilder();
			}
		}
		return builder.toString();
	}

	/**
	 * Randomize a string and return as a string
	 * 
	 * @param length
	 * @return Return a Number by String
	 */
	public static String randomNumberString(int length) {

		String lexicon = "0123456789";

		Random rand = new Random();

		// consider using a Map<String,Boolean> to say whether the identifier is being
		// used or not
		Set<String> identifiers = new HashSet<String>();

		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			// int length = rand.nextInt(5)+5;
			for (int i = 0; i < length; i++) {
				builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
			}
			if (identifiers.contains(builder.toString())) {
				builder = new StringBuilder();
			}
		}
		return builder.toString();
	}

	/**
	 * Randomize a string Name and return as a string
	 * 
	 * @return Return a String Name
	 */
	public static String randomFullName() {
		String fullname = "";
		String first_name = randomText(4);
		String middle_name = randomText(5);
		String last_name = randomText(6);
		fullname = first_name + " " + middle_name + " " + last_name;
		return fullname;

	}

	/**
	 * Randomize a number plate and return as a string
	 * 
	 * @return Return a String Name
	 */
	public static String randomNumberPlate() {
		String numberplate = "";
		String first = randomText(2);
		String last = "";

		String AB = "0123456789";
		SecureRandom rnd = new SecureRandom();

		StringBuilder sb = new StringBuilder(4);
		for (int i = 0; i < 4; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		last = sb.toString();

		numberplate = first + last;
		return numberplate;
	}

	public static int randomInterger(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	/**
	 * Randomize a Ip address and return as a string
	 * 
	 * @return Return a String
	 */
	public static String randomIpAddress() {
		Random r = new Random();
		return r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
	}

	/**
	 * Randomize a phone/fax number and return as a string
	 * 
	 * @return Return a phone/fax number as String
	 */
	public static String randomPhoneFaxNumber() {
		int set1, set2, set3;
		Random mRan = new Random();
		set1 = mRan.nextInt(899) + 100;
		set2 = mRan.nextInt(643) + 100;
		set3 = mRan.nextInt(8999) + 1000;
		return "" + set1 + " " + set2 + " " + set3;
	}

	/**
	 * Read data from CSV File by Scanner
	 * 
	 * 
	 */
	public static List<String> ReadCSV_Scanner(String filepath) {
		{
			List<String> valueList = new ArrayList<>();
			String COMMA_DELIMITER = ",";
			Scanner scanner = null;
			try {
				// Get the scanner instance
				scanner = new Scanner(new File(filepath));
				// Use Delimiter as COMMA
				scanner.useDelimiter(COMMA_DELIMITER);

				scanner.useDelimiter("\n");
				while (scanner.hasNext()) {
					// System.out.print(scanner.next()+" ");
					valueList.add(scanner.next());

				}
			} catch (FileNotFoundException fe) {
				fe.printStackTrace();
			} finally {
				scanner.close();
			}
			return valueList;
		}

	}

	/**
	 * Get date based on inputed date
	 * 
	 * @param pDate Inputed date
	 * @param count Date to add/subtract
	 */
	public static String getDate(String pDate, int count) {
		DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date mDate = null;
		try {
			mDate = mDateFormat.parse(pDate);
		} catch (ParseException e) {
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(mDate);
		cal.add(Calendar.DAY_OF_YEAR, count);
		Date expectedDate = cal.getTime();
		return mDateFormat.format(expectedDate);
	}

	/**
	 * Wait for specified time in seconds
	 */
	public static void waitForSeconds(double pSecond) {
		try {
			Thread.sleep((long) (pSecond * 1000));
		} catch (Exception e) {
		}
	}

	/**
	 * Get today's date MM/dd/yyyy or dd/MM/yyyy
	 */
	public static String getCurrentDate(String dateformat) {
		DateFormat mDateFormat = new SimpleDateFormat(dateformat);
		Date mDate = new Date();
		return mDateFormat.format(mDate);
	}

	/**
	 * Get desired date MM/dd/yyyy or dd/MM/yyyy
	 */
	public static String getNextDate(String dateformat, int x) {
		DateFormat mDateFormat = new SimpleDateFormat(dateformat);
		Date mDate = new Date();
		Calendar cal = Calendar.getInstance();
		int days = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, days + x);
		mDate = cal.getTime();

		return mDateFormat.format(mDate);
	}

	/**
	 * Add year to a date
	 */
	public static String addYear(String pDate, int NumOfYear) {
		DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date mDate;
		Date mNewDate = null;
		try {
			mDate = mDateFormat.parse(pDate);
			Calendar c = Calendar.getInstance();
			c.setTime(mDate);
			c.add(Calendar.YEAR, NumOfYear);
			mNewDate = c.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return mDateFormat.format(mNewDate);
	}

	/**
	 * Get current time
	 */
	public static String getCurrentTime() {
		DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm");
		Date mDate = new Date();
		return mDateFormat.format(mDate);
	}

	/**
	 * Get yesterday's date
	 */
	public static String getYesterdayDate() {
		DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date mDate = new Date();
		return Functions.getDate(mDateFormat.format(mDate), -1);
	}

	/**
	 * Get tomorow's date
	 */
	public static String getTomorowDate() {
		DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date mDate = new Date();
		return Functions.getDate(mDateFormat.format(mDate), 1);
	}

	/**
	 * Randomize and return an email
	 * 
	 * @return An email string
	 */
	public static String randomEmail() {
		String mSet1 = randomAlphabetic(5).toLowerCase();
		String mSet2 = randomAlphabetic(5).toLowerCase();
		String mSet3 = randomAlphabetic(3).toLowerCase();
		String mSet4 = randomAlphabetic(2).toLowerCase();
		Random mRan = new Random();
		if (mRan.nextBoolean())
			return mSet1 + "@" + mSet2 + "." + mSet3 + "." + mSet4;
		else
			return mSet1 + "@" + mSet2 + "." + mSet3;

	}

	/**
	 * Randomize a real number (either negative or positive from -10 to 10) and
	 * return as a string
	 * 
	 * @return Return a number as a String
	 */
	public static String randomRealNumber() {
		Random mRan = new Random();

		return (mRan.nextInt(21) - 11) + 1 + "";
	}

	/**
	 * Randomize a positive number from 1 to 10 and return as a string
	 * 
	 * @return Return a number as a String
	 */
	public static String randomPositiveNumber() {
		Random mRan = new Random();

		return (mRan.nextInt(10)) + 1 + "";
	}

	/**
	 * Check image whether exist on the Screen or not
	 * @param data: image page
	 * @return true or false
	 */
	public static boolean checkExist(String data) {
		Screen screen = new Screen();
		// Create object of Pattern class and specify the images path
		Pattern image = new Pattern(data);
		if (screen.exists(data) != null)
			return true;
		else
			return false;
	}

	/**
	 * Click by using SikuliX
	 * 
	 * @return 
	 */

	public static void clickBySikuli(String data) {

		Screen screen = new Screen();
		// Create object of Pattern class and specify the images path
		Pattern image = new Pattern(data);

		try {
			screen.click(image);

		} catch (FindFailed e) {
			e.printStackTrace();
		}

	}

	
	public static void sendSingleKeyBySikuli(String key) {
		Screen screen = new Screen();
		screen.type(key);
	}

	/**
	 *  Send a long text to input field by Sikuli by copy and paste input data
	 * @param data
	 * @param input
	 */
	public static void sendKeyBySikuli(String data, String input) {

		Screen screen = new Screen();
		// Create object of Pattern class and specify the images path
		Pattern image = new Pattern(data);

		try {
			screen.doubleClick(image);
			screen.type("a", Key.CTRL);
			screen.type(Key.DELETE);

			if (input.length() < 30)
				screen.type(input);
			else {
				Functions.setValueToclipboard(input);
				screen.type("v", Key.CTRL);
			}

		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	public static void sendKeyBySikuliWithoutClean(String input) {

		Screen screen = new Screen();
		if (input.length() < 30)
			screen.type(input);
		else {
			Functions.setValueToclipboard(input);
			screen.type("v", Key.CTRL);
		}
	}

	public static void pasteValueBySikuli(String data, String input) {

		Functions.setValueToclipboard(input);
		Screen screen = new Screen();

		Pattern image = new Pattern(data);

		try {
			// screen.doubleClick(940,560);
			screen.doubleClick(image);
			screen.type("a", Key.CTRL);
			screen.type(Key.DELETE);
			// screen.type(input);
			screen.type("v", Key.CTRL);

		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	public static void pasteValueBySikuli(String input, int x, int y) {

		Functions.setValueToclipboard(input);
		Screen screen = new Screen();

		try {
			screen.mouseMove(x, y);
			// screen.doubleClick(940,560);
			screen.doubleClick(x, y);
			screen.delayClick(500);

			// region.rightClick(Location(x+1150, y+200))
			screen.doubleClick(x, y);
			screen.type("a", Key.CTRL);
			screen.type(Key.DELETE);
			// screen.type(input);
			screen.type("v", Key.CTRL);

		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get duration between 2 times
	 * 
	 * @param eventTime1
	 * @param eventTime2
	 * @return duration as format d:hh:mm
	 */

	public static String getDuration(Date eventTime1, Date eventTime2) {

		Calendar c1 = Calendar.getInstance();
		c1.setTime(eventTime1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(eventTime2);
		long l = c2.getTimeInMillis() - c1.getTimeInMillis();
		long s = l / 1000;
		long hour = s / 3600;
		long min = s / 60;
		long sec = s - (min * 60) - (hour * 3600);

		String strHour = placeZeroIfNeede(hour);
		if (strHour == "00")
			strHour = "0";

		String strMin = placeZeroIfNeede(min);
		String strSec = placeZeroIfNeede(sec);
		String duration = String.format("%s:%s:%s", strHour, strMin, strSec);
		return duration;
	}

	public static String placeZeroIfNeede(long number) {
		return (number >= 10) ? Long.toString(number) : String.format("0%s", Long.toString(number));
	}

	/**
	 * Copy String to Clipboard
	 * 
	 * @param intput
	 */
	public static void setValueToclipboard(String intput) {

		StringSelection stringSelection = new StringSelection(intput);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}

	/**
	 * Convert a string to Integer
	 * 
	 * @param input
	 * @return int value
	 */
	public static int convertStringToInteger(String input) {
		return Integer.parseInt(input);

	}
	/**
	 * Convert a Integer to string
	 * 
	 * @param input
	 * @return String value
	 */
	public static String convertIntegerToString(int input) {
		return String.valueOf(input);

	}
	
	
	
	/**
	 * Update load files in Chrome
	 * 
	 * @param Folderpath
	 * @param filename
	 */
	public static void uploadfile(String Folderpath, String filename) {
		if (!Folderpath.substring(Folderpath.length() - 1).contains("\\")) {

			Folderpath = Folderpath + '\\';
		}

		String filePath = Folderpath + filename;
		System.out.println(filePath);
		String btn_Open = "Imgs/btn_Open_Upload.png";
		String txt_pathInput = "Imgs/txt_InputPath_Upload.png";
		clickBySikuli(txt_pathInput);
		sendKeyBySikuliWithoutClean(filePath);
		clickBySikuli(btn_Open);
	}

	public static void saveFile(String filePath) {
		String btn_Save = "Imgs/btn_Save.png";
		String txt_pathInput = "Imgs/txt_InputPath_Upload.png";
		// sendKeyBySikuli(txt_pathInput, filePath);
		clickBySikuli(txt_pathInput);
		sendSingleKeyBySikuli(Key.HOME);
		sendKeyBySikuliWithoutClean(filePath);
		clickBySikuli(btn_Save);
	}

	public static List<String> GetFileNameInFolder(String Folderpath) {
		List<String> filelist = new ArrayList<>();

		return filelist;
	}

	public static boolean searchFileInFolder(String Folderpath, String fileName) {
		boolean IsExist = false;

		return IsExist;
	}

	/**
	 * Get the list of filename on data folder
	 * 
	 * @return
	 */
	public static File[] getListOfFile(String folderPath) {

		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		/*
		 * 
		 * for (int i = 0; i < listOfFiles.length; i++) { if (listOfFiles[i].isFile()) {
		 * System.out.println("File " + listOfFiles[i].getName()); } else if
		 * (listOfFiles[i].isDirectory()) { System.out.println("Directory " +
		 * listOfFiles[i].getName()); }
		 */

		return listOfFiles;
	}

	/**
	 * Delete the list of files in folder
	 * 
	 * @return
	 */
	public static void deleteFiles(String folderPath) {

		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		if (listOfFiles != null && listOfFiles.length > 0) {
			for (int i = 0; i < listOfFiles.length; i++) {
				listOfFiles[i].delete();
			}
		}
	}

	public static long getLastModified(String fileName) {

		String folderPath = Constants.UploadFilePath;
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		long lastModified = 0;

		for (File l : listOfFiles) {
			if (l.getName().contains(fileName)) {
				lastModified = l.lastModified();
				break;
			}
		}
		return lastModified;
	}

	public static Date convertLongToDate(long timeInMili) {
		Date d = new Date(timeInMili);
		return d;
	}

	public static int countRow(SeleniumBrowser Browser) {
		List<WebElement> l = Browser.Driver.findElements(By.xpath("//tbody/tr"));
		return l.size();
	}

	public static int countRow(SeleniumBrowser Browser, String parentTabName) {
		List<WebElement> l = Browser.Driver.findElements(By.xpath("//" + parentTabName + "//tbody/tr"));
		return l.size();
	}

	/**
	 * Get Row Value data
	 * 
	 * @param Browser
	 * @param pTable
	 * @param byName  start from 1
	 * @return
	 */
	public static List<String> GetRowDataByValue(SeleniumBrowser Browser, String pValue) {

		List<String> RowData = new ArrayList<>();

		List<WebElement> row = Browser.Driver
				.findElements(By.xpath("//tbody/tr/td[contains(.,'" + pValue + "')]/../td"));

		for (WebElement e : row) {
			RowData.add(e.getText());
		}

		return RowData;
	}

	/**
	 * Get Row Value data
	 * 
	 * @param Browser
	 * @param pTable
	 * @param RowIndex start from 1
	 * @return
	 */
	public static List<String> GetRowData(SeleniumBrowser Browser, int RowIndex) {

		List<String> RowData = new ArrayList<>();

		// int max_col = Browser.Driver.findElements(By.xpath(pTable +
		// "/tbody/tr[1]/td")).size();

		List<WebElement> row = Browser.Driver.findElements(By.xpath("//tbody/tr[" + RowIndex + "]/td"));

		for (WebElement e : row) {
			RowData.add(e.getText());
		}

		return RowData;
	}

	public static List<String> GetRowData(SeleniumBrowser Browser, int RowIndex, String parentTabName) {

		List<String> RowData = new ArrayList<>();

		// int max_col = Browser.Driver.findElements(By.xpath(pTable +
		// "/tbody/tr[1]/td")).size();

		List<WebElement> row = Browser.Driver
				.findElements(By.xpath("//" + parentTabName + "//tbody/tr[" + RowIndex + "]/td"));

		for (WebElement e : row) {
			RowData.add(e.getText());
		}

		return RowData;
	}

	/**
	 * Get Column Value data
	 * 
	 * @param Browser
	 * @param ColIndex
	 * @return List of String Value
	 */
	public static List<String> getColumnData(SeleniumBrowser Browser, int ColIndex) {

		List<String> ColData = new ArrayList<>();

		List<WebElement> row = Browser.Driver.findElements(By.xpath("//tbody/tr/td[" + ColIndex + "]"));

		for (WebElement e : row) {
			ColData.add(e.getText());
		}
		return ColData;
	}

	/**
	 * Get Column Value data
	 * 
	 * @param Browser
	 * @param ColIndex
	 * @return List of String Value
	 */
	public static List<String> getColumnData(SeleniumBrowser Browser, int ColIndex, String parentTabName) {

		List<String> ColData = new ArrayList<>();

		List<WebElement> row = Browser.Driver
				.findElements(By.xpath("//" + parentTabName + "//tbody/tr/td[" + ColIndex + "]"));

		for (WebElement e : row) {
			ColData.add(e.getText());
		}
		return ColData;
	}

	public static List<String> getFirstRow(SeleniumBrowser Browser) {
		List<String> row = new ArrayList<>();
		row = GetRowData(Browser, 1);
		return row;
	}

	public static List<String> getFirstRow(SeleniumBrowser Browser, String parentTabName) {
		List<String> row = new ArrayList<>();
		row = GetRowData(Browser, 1, parentTabName);
		return row;
	}

	public static List<String> getLastRow(SeleniumBrowser Browser) {
		List<String> row = new ArrayList<>();
		System.out.println("Number of log row: " + countRow(Browser));
		row = GetRowData(Browser, countRow(Browser));
		return row;
	}

	public static List<String> getLastRow(SeleniumBrowser Browser, String parentTabName) {
		List<String> row = new ArrayList<>();
		System.out.println("Number of log row: " + countRow(Browser, parentTabName));
		row = GetRowData(Browser, countRow(Browser, parentTabName), parentTabName);
		return row;
	}

	public static List<WebElement> getElementDropdownList(SeleniumBrowser Browser, By pBy, String childTag) {
		WebElement ddList = Browser.captureInterface(pBy);
		List<WebElement> Elements = ddList.findElements(By.xpath(childTag));
		return Elements;
	}

	/**
	 * Get list of items in dropdown in String
	 * 
	 * @param Browser
	 * @param pBy
	 * @param childTag
	 * @return
	 */
	public static List<String> getDropdownList(SeleniumBrowser Browser, By pBy, String childTag) {

		WebElement ddList = Browser.captureInterface(pBy);
		List<String> items = new ArrayList<>();

		List<WebElement> Elements = ddList.findElements(By.xpath(childTag));

		for (WebElement e : Elements) {
			items.add(e.getText());
		}

		return items;
	}

	/**
	 * Randomize an UUID string
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * get current time in long type
	 */
	public static Long getCurrentTimeinLong() {
		Long millis = 12345678910L;
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		Date mDate = new Date();

		// now = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
		// //ofHours(+7)
		// now =
		// LocalDateTime.now().atZone(ZoneId.of("Europe/London")).toInstant().toEpochMilli();
		// //ofHours(+7)
		String a = dateFormatGmt.format(mDate);

		try {
			millis = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss").parse(a).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return millis;
	}

	/**
	 * get time in long type
	 */
	public static Long getCurrentTimeinLong(Date mDate) {
		Long millis = 12345678910L;
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT+7"));

		// now = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
		// //ofHours(+7)
		// now =
		// LocalDateTime.now().atZone(ZoneId.of("Europe/London")).toInstant().toEpochMilli();
		// //ofHours(+7)
		String a = dateFormatGmt.format(mDate);

		try {
			millis = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss").parse(a).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return millis;
	}

	/**
	 * Convert first character uppercase in a sentence
	 * 
	 * @param str
	 * @return
	 */
	public static String convert1stUppercase(String str) {

		// Create a char array of given String
		char ch[] = str.toCharArray();
		for (int i = 0; i < str.length(); i++) {

			// If first character of a word is found
			if (i == 0 && ch[i] != ' ' || ch[i] != ' ' && ch[i - 1] == ' ') {

				// If it is in lower-case
				if (ch[i] >= 'a' && ch[i] <= 'z') {

					// Convert into Upper-case
					ch[i] = (char) (ch[i] - 'a' + 'A');
				}
			}

			// If apart from first character
			// Any one is in Upper-case
			else if (ch[i] >= 'A' && ch[i] <= 'Z')

				// Convert into Lower-Case
				ch[i] = (char) (ch[i] + 'a' - 'A');
		}

		// Convert the char array to equivalent String
		String st = new String(ch);
		return st;
	}

	public static String getValue(SeleniumBrowser Browser, By ptxtBy) {
		return Browser.captureInterface(ptxtBy).getAttribute("value").toString();
	}

	public static String getText(SeleniumBrowser Browser, By ptxtBy) {
		String text = "";
		try {
			text = Browser.captureInterface(ptxtBy).getText().toString();
		} catch (Exception e) {
			return "";
		}

		return text;
	}

	public static void PasteValueBySikuli(String data, String input) {

		setValueToclipboard(input);
		Screen screen = new Screen();

		Pattern image = new Pattern(data);

		try {
			screen.doubleClick(image);
			screen.type("a", Key.CTRL);
			screen.type(Key.DELETE);
			// screen.type(input);
			screen.type("v", Key.CTRL);

		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	public static void selectDatePicker(SeleniumBrowser Browser, By pEleBy, String year, String month, String date) {

		// -- Open picker date --
		Browser.click(pEleBy);
		Functions.waitForSeconds(2);

		// -- select Year --
		Browser.captureInterface(By.xpath("//owl-date-time-calendar/div/div/button/span")).click();
		Functions.waitForSeconds(1);
		By pYear = By.xpath("//owl-date-time-multi-year-view/table/tbody/tr/td[contains(@aria-label,'" + year + "')]");
		Browser.click(pYear);
		Functions.waitForSeconds(1);
		String monthOfyear = "";

		if (month.contains("0"))
			month = month.replace("0", "").trim();

		switch (month) {
		case "1":
			monthOfyear = "Jan";
			break;
		case "2":
			monthOfyear = "Feb";
			break;
		case "3":
			monthOfyear = "Mar";
			break;
		case "4":
			monthOfyear = "Apr";
			break;
		case "5":
			monthOfyear = "May";
			break;
		case "6":
			monthOfyear = "Jun";
			break;
		case "7":
			monthOfyear = "Jul";
			break;
		case "8":
			monthOfyear = "Aug";
			break;
		case "9":
			monthOfyear = "Sep";
			break;
		case "10":
			monthOfyear = "Oct";
			break;
		case "11":
			monthOfyear = "Nov";
			break;
		case "12":
			monthOfyear = "Dec";
			break;
		}

		// -- select Month --

		By pMonth = By.xpath("//owl-date-time-year-view/table/tbody/tr/td[contains(.,'" + monthOfyear + "')]");
		Browser.click(pMonth);
		Functions.waitForSeconds(1);

		// -- select date --
		Browser.setTimeOut(Browser, 5);
		By pDate = By.xpath(
				"//owl-date-time-calendar//tbody/tr/td/span[@class='owl-dt-calendar-cell-content' or @class='owl-dt-calendar-cell-content owl-dt-calendar-cell-today'][text()= "
						+ date + "]");

		WebElement dEle = Browser.captureInterface(pDate);
//		if( dEle == null  )
//		{
//			pDate = By
//					.xpath("//owl-date-time-calendar//tbody/tr/td/span[@class='owl-dt-calendar-cell-content owl-dt-calendar-cell-today' and text()= "
//							+ date + "]");
//			dEle = Browser.captureInterface(pDate);
//		}
		dEle.click();
		Functions.waitForSeconds(2);
		Browser.resetTimeOut(Browser);
	}

	public static void selectPickerToday(SeleniumBrowser Browser, By pEleBy) {
		Browser.captureInterface(pEleBy).click();
		By today = By.xpath("//span[@class='owl-dt-calendar-cell-content owl-dt-calendar-cell-today']");
		Browser.captureInterface(today);
		Functions.waitForSeconds(0.5);
	}

	public static void selectDatePicker(SeleniumBrowser Browser, By pEle, String dateFormat, String input) {

		String year = "";
		String month = "";
		String date = "";
		String[] mdate = input.split("/");
		if (mdate.length == 0) {
			System.out.println("Input is wrong date format");
			return;
		}

		if (dateFormat.contains(CPValueList.dateFormat.formatUS)) {
			// List dList = new ArrayList();
			month = mdate[0];
			date = mdate[1];
			year = mdate[2];
		} else if (dateFormat.contains(CPValueList.dateFormat.formatUK)) {
			date = mdate[0];
			month = mdate[1];
			year = mdate[2];
		} else {
			System.out.println("Cannot General date format");
			return;
		}
		selectDatePicker(Browser, pEle, year, month, date);
	}

	
	public static String getCurrDirectory() {

		String path = System.getProperty("user.dir");

	//	System.out.println("Working Directory = " + path);

		return path;
	}

	public static String getDownloadFile(String folderPath) {

		File[] files = getListOfFile(folderPath);
		// Functions.saveFile(filePath);
		if (files == null || files.length == 0)
			return null;
		else
			return files[0].getPath();
	}

	public static String getFilenameFromFilePath(String filePath) {
		String fileName = "";
		// String[] s = filePath.split("//");
		int lastSlash = filePath.lastIndexOf("\\");
		fileName = filePath.substring(lastSlash + 1, filePath.length());
		return fileName;
	}

}
