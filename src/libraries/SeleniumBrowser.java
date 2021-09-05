package libraries;

import java.awt.List;
import java.io.File;
import java.text.MessageFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import interfaces.I_DHL_Settings;
import interfaces.I_Global_Settings;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Log;

public class SeleniumBrowser {

	public WebDriver Driver;
	public WebDriverWait Wait;

	public SeleniumBrowser() {

	}

	/**
	 * Click on a control on web application
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 */
	public void click(By pBy) {
		this.Driver.findElement(pBy).click();

	}

	/**
	 * Click on a control on web application
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 */
	public void clickCustom(By pBy) {
		By mBy = By.xpath("//*[@id = '" + this.getElementID(pBy) + "']/../following-sibling::*[1]");
		this.click(mBy);
	}

	/**
	 * Attempts to click on an element multiple times (to avoid stale element
	 * exceptions caused by rapid DOM refreshes)
	 *
	 * @param by
	 *            A mechanism by which to find elements within a document
	 */
	public void waitAndClick(By pBy) {
		final int MAX_STALE_ELEMENT_RETRIES = 2;
		int retries = 0;
		while (retries < 2) {
			try {
				this.Wait.until(ExpectedConditions.elementToBeClickable(pBy)).click();
				return;
			} catch (Exception e) {
				if (retries < MAX_STALE_ELEMENT_RETRIES) {
					retries++;
					Functions.waitForSeconds(2);
					continue;
				} else {
					System.out.println("Click - " + pBy + " Retry: " + retries);
				}
			}
		}
	}

	/**
	 * Click on a control on web application using Java script
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 */
	public void clickJavascript(By pBy) {
		((JavascriptExecutor) this.Driver).executeScript("arguments[0].click()", this.captureInterface(pBy));
	}

	/**
	 * scroll To view Element
	 * 
	 * @param pBy
	 */
	public void scrollToEle(By pBy) {
		((JavascriptExecutor) this.Driver).executeScript("arguments[0].scrollIntoView(true);",
				this.captureInterface(pBy));
		try {
			Thread.sleep(500);
		} catch (Exception ex) {
		}
	}

	/**
	 * scroll To view Element
	 * 
	 * @param Element
	 */
	public void scrollToEle(WebElement pEle) {
		((JavascriptExecutor) this.Driver).executeScript("arguments[0].scrollIntoView(true);", pEle);
		try {
			Thread.sleep(700);
		} catch (Exception ex) {
		}
	}


	/**
	 * scroll To view Element
	 * 
	 * @param Element
	 */
	public void scrolldown(int pixel) {
		((JavascriptExecutor) this.Driver).executeScript("window.scrollBy(0, "+ pixel + ")");
	}
	
	
	/**
	 * Select a check box on web application
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 */
	public void selectCheckbox(By pBy) {
		WebElement mElement = this.Driver.findElement(pBy);
		if (!mElement.isSelected())
			mElement.click();
	}

	/**
	 * Select a check box on web application (for Swift Elements)
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 */
	public void selectCheckboxCustom(By pBy) {

		WebElement mElement = this.Driver.findElement(pBy);
		if (!mElement.isSelected()) {
			By mBy = By.xpath("//*[@id = '" + this.getElementID(pBy) + "']/../following-sibling::*[1]");
			this.waitAndClick(mBy);
		}
	}

	/**
	 * Deselect a check box on web application
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 */
	public void deselectCheckbox(By pBy) {
		WebElement mElement = this.Driver.findElement(pBy);
		if (mElement.isSelected())
			mElement.click();
	}

	/**
	 * Deselect a check box on web application (for Swift Elements)
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 */
	public void deselectCheckboxCustom(By pBy) {

		WebElement mElement = this.Driver.findElement(pBy);
		if (mElement.isSelected()) {
			By mBy = By.xpath("//*[@id = '" + this.getElementID(pBy) + "']/../following-sibling::*[1]");
			this.click(mBy);
		}
	}

	public List getDropdownList(By pBy, String childTag) {

		WebElement ddList = this.captureInterface(pBy);
		int count = ddList.findElements(By.xpath(childTag)).size();
		List items = new List(count, false);

		for (int i = 0; i < count; i++) {
			WebElement col = ddList.findElement(By.xpath(childTag + "[" + (i + 1) + "]"));
			items.add(col.getText(), i);
		}
		return items;
	}

	/**
	 * Select value in a drop down list on web application
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 * @param pValue
	 *            Value to select
	 */
	public void selectDropdownByValue(By pBy, String pValue) {
		Select mSelect = new Select(this.Driver.findElement(pBy));
		mSelect.selectByValue(pValue);
	}

	/**
	 * Select value in a drop down list on web application
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 * @param pValue
	 *            Value to select
	 */
	public void selectDivDropdownByText(SeleniumBrowser Browser, By pBy, String pValue) {
		Browser.waitForElementVisible(pBy);
		WebElement ddList = this.captureInterface(pBy);
		WebElement item = ddList.findElement(By.xpath("div[contains(.,'" + pValue + "')]"));
		Browser.scrollToEle(item);
		item.click();
	}

	/**
	 * Select value in a drop down list on web application
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 * @param pValue
	 *            Text to select
	 */
	public void selectDropdownByText(By pBy, String pText) {
		Select mSelect = new Select(this.Driver.findElement(pBy));
		mSelect.selectByVisibleText(pText);
	}

	/**
	 * Select random in a drop down list on web application
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 * @param pValue
	 *            Text to select
	 */
	public String selectRandomGeneralDropdown(By pBy) {
		Select mSelect = new Select(this.Driver.findElement(pBy));

		WebElement ddList = this.captureInterface(pBy);

		int count = ddList.findElements(By.xpath("option")).size();

		Random r = new Random();

		int s = r.nextInt(count - 1);

		List items = new List(count, false);
		for (int i = 0; i < count; i++) {
			WebElement col = ddList.findElement(By.xpath("option[" + (i + 1) + "]"));
			items.add(col.getText(), i);
		}

		String pText = items.getItem(s);
		if (pText.contentEquals("Select"))
			pText = items.getItem(s + 1);

		mSelect.selectByVisibleText(pText);
		return pText;
	}

	/**
	 * Select random in a drop down list on web application
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 * @param pValue
	 *            Text to select
	 */
	public String selectRandomDropdownSkipFisrt(By pBy) {
		Select mSelect = new Select(this.Driver.findElement(pBy));

		WebElement ddList = this.captureInterface(pBy);

		int count = ddList.findElements(By.xpath("option")).size();

		Random r = new Random();

		int s = r.nextInt(count - 1);

		List items = new List(count, false);
		for (int i = 1; i < count; i++) {
			WebElement col = ddList.findElement(By.xpath("option[" + (i + 1) + "]"));
			items.add(col.getText(), i - 1);
		}

		String pText = items.getItem(s);
		if (pText.contentEquals("Select"))
			pText = items.getItem(s + 1);

		mSelect.selectByVisibleText(pText);
		return pText;
	}

	/**
	 * 
	 * @param Browser
	 * @param pBy_ul
	 * @param tagName
	 *            = li or option
	 * @return
	 */
	public static String selectRandomDropdown(SeleniumBrowser Browser, By pBy_ul, String tagName) {
		WebElement ddList = Browser.captureInterface(pBy_ul);
		int count = ddList.findElements(By.xpath(tagName)).size();
		int s = 1;

		Random r = new Random();
		s = r.nextInt(count) + 1;

		WebElement item = ddList.findElement(By.xpath("" + tagName + "[" + s + "]"));
		String name = item.getText();
		Browser.scrollToEle(item);
		item.click();
		return name;
	}

	/**
	 *   Ham khong chay duoc
	 * @param Browser
	 * @param pBy_ul
	 * @param tagName
	 *            = li or option
	 * 
	 */
	public static void selectDropdown(SeleniumBrowser Browser, By pBy_ul, String tagName, String pValue) {
		WebElement ddList = Browser.captureInterface(pBy_ul);
		int count = ddList.findElements(By.xpath(tagName)).size();
		int s = 1;

		for (s = 1; s < count + 1; s++) {
			WebElement item = ddList.findElement(By.xpath("" + tagName + "[" + s + "]"));
			String name = item.getText();
			if (name == pValue) {
				Browser.scrollToEle(item);
				item.click();
				break;
			}
		}
	}

	/**
	 * 
	 * @param Browser
	 * @param pBy_div
	 * @return
	 */
	public static String selectRandomDropdown(SeleniumBrowser Browser, By pBy_div) {
		Browser.waitForElementVisible(pBy_div);
		WebElement ddList = Browser.captureInterface(pBy_div);
		int count = ddList.findElements(By.xpath("div")).size();
		if (count > 8)
			count = 8;
		int s = 1;

		Random r = new Random();
		s = r.nextInt(count) + 1;

		WebElement item = ddList.findElement(By.xpath("div[" + s + "]"));
		String name = item.getText();
		System.out.println(name);
		Browser.scrollToEle(item);
		item.click();
		Functions.waitForSeconds(0.5);
		return name;
	}
	

	/**
	 * Select value in a drop down list on web application (for Swift Elements)
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 * @param pValue
	 *            Value to select
	 */
	public void selectTelerikDropdown(By pBy, String pValue) {
		String mId = this.getElementID(pBy);
		By mBy = By.xpath("//*[@id = '" + mId + "']/../following-sibling::*[1]");
		this.waitAndClick(mBy);
		By mValue = By.xpath("//ul[@id = '" + mId.replace("input", "items") + "']/li[@data-label = '"
				+ (pValue == "" ? "&nbsp;" : pValue) + "']");
		this.click(mValue);
		this.waitForElementValueSelected(pBy, pValue);
	}

	/**
	 * Enter value to element on web application
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 * @param pValue
	 *            Value to enter
	 */
	public void sendKeys(By pBy, String pValue) {
		try {
			this.Wait.until(ExpectedConditions.elementToBeClickable(pBy));
		} catch (Exception e) {
		}
		WebElement mElement = this.Driver.findElement(pBy);
		try {
			this.Wait.until(ExpectedConditions.elementToBeClickable(mElement));
		} catch (Exception e) {
		}
		mElement.sendKeys(pValue);

	}

	/**
	 * Press key to element on web application
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 * @param pValue
	 *            Key to press
	 */
	public void sendKeys(By pBy, Keys pKey) {
		this.Driver.findElement(pBy).sendKeys(pKey);
	}

	/**
	 * Clear all text of element
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 */
	public void clear(By pBy) {
		try {
			this.Wait.until(ExpectedConditions.elementToBeClickable(pBy));
		} catch (Exception e) {
		}
		this.Driver.findElement(pBy).clear();
	}

	/**
	 * Enter value to telerik element on web application
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 * @param pValue
	 *            Value to enter
	 */
	public void telerikSendKeys(By pBy, String pValue) {
		Actions mBuilder = new Actions(this.Driver);
		Action action = mBuilder.sendKeys(this.Driver.findElement(pBy), (CharSequence) pValue).build();
		action.perform();
	}

	/**
	 * Clear field then enter value to element on web application
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 * @param pValue
	 *            Value to enter
	 */
	public void enter(By pBy, String pValue) {
		this.clear(pBy);
		this.sendKeys(pBy, pValue);
	}

	/**
	 * Capture a element
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 * @return Return a WebElement object
	 */
	public WebElement captureInterface(By pBy) {
		try {
			return this.Driver.findElement(pBy);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Capture a element
	 * 
	 * @param String
	 *            A mechanism by which to find elements within a document
	 * @return Return a WebElement object
	 */
	public WebElement captureInterface(String pBy) {
		try {
			return this.Driver.findElement(By.xpath(pBy));
		} catch (Exception e) {
			return null;
		}
	}
	
	


	/**
	 * Wait for a element visible
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 */
	public void waitForElementVisible(By pBy) {
		try {
			this.Wait.until(ExpectedConditions.visibilityOfElementLocated(pBy));
		} catch (Exception e) {

		}
	}

	/**
	 * Wait for a element invisible
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 */
	public void waitForElementNotVisible(By pBy) {
		try {
			this.Wait.until(ExpectedConditions.invisibilityOfElementLocated(pBy));
		} catch (Exception e) {
			System.out.println(pBy + " Not Visible");
		}
	}

	/**
	 * Wait for a value is populated in an element
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 * @param pValue
	 *            Expected value to wait for
	 */
	public void waitForElementValue(By pBy, String pValue) {
		try {
			this.Wait.until(ExpectedConditions.attributeToBe(pBy, "value", pValue));
		} catch (Exception e) {
		}
	}

	/**
	 * Wait for a value is selected in an element
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 * @param pValue
	 *            Expected value to wait for
	 */
	public void waitForElementValueSelected(By pBy, String pValue) {
		String mId;
		mId = this.getElementID(pBy);
		By mBy = By.id(mId.replace("input", "label"));
		if (pValue == "")
			pValue = " ";
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < 10000) {
			try {
				this.Wait.until(ExpectedConditions.attributeToBe(mBy, "innerText", pValue));
			} catch (Exception e) {
				try {
					Thread.sleep(500);
				} catch (Exception ex) {
				}
			}
		}
	}

	/**
	 * Switch to a specific frame
	 * 
	 * @param pFrameName
	 *            Frame name need to switch to
	 */
	public void switchFrame(String pFrameName) {
		try {
			this.Wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(pFrameName));
		} catch (Exception e) {
		}
	}

	/**
	 * Switch to parent frame of the current selected frame
	 */
	public void switchParentFrame() {
		this.Driver.switchTo().parentFrame();
	}

	/**
	 * Get id of element
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 * @return Return id of element
	 */
	public String getElementID(By pBy) {
		WebElement mElement;
		String mId;
		try {
			mId = this.captureInterface(pBy).getAttribute("id");
		} catch (StaleElementReferenceException e) {
			mElement = this.captureInterface(pBy);
			mId = mElement.getAttribute("id");
		}
		return mId;
	}

	/**
	 * Switch to second window
	 */
	public void switchToSecondWindow() {
		String mCurrentWindow = this.Driver.getWindowHandle();
		String mSwitchedWindow = "";
		Object[] mWindows = this.Driver.getWindowHandles().toArray();
		for (int i = 0; i < mWindows.length; i++) {
			mSwitchedWindow = mWindows[i].toString();
			if (!mCurrentWindow.equals(mSwitchedWindow))
				break;
		}
		this.Driver.switchTo().window(mSwitchedWindow);
	}

	/**
	 * Switch to latest window
	 */
	public void switchToTopWindow() {
		String[] mWindows = this.Driver.getWindowHandles().toArray(new String[0]);
		int res = 1;
		String mHandle = "";
		for (String object : mWindows) {
			if (!Constants.CurrentHandle.contains(object)) {
				res *= 0;
				mHandle = object.toString();
				break;
			}
		}
		if (res == 1)
			mHandle = mWindows[Constants.CurrentHandle.size() - 1].toString();
		this.Driver.switchTo().window(mHandle);
	}

	/**
	 * Switch to main window
	 */
	public void SwitchToMainWindow() {
		Object[] mWindows = this.Driver.getWindowHandles().toArray();
		Boolean mWait = true;
		int mTimes = 6;
		while (mWait && mTimes > 0) {
			mWindows = this.Driver.getWindowHandles().toArray();
			if (mWindows.length > 1) {
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
				}
				mTimes--;
			} else
				mWait = false;
		}
		this.Driver.switchTo().window(mWindows[0].toString());
	}

	/**
	 * Wait for element enabled
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 */
	public void waitForElementEnabled(By pBy) {
		try {
			this.Wait.until(ExpectedConditions.elementToBeClickable(pBy));
		} catch (Exception e) {
		}
	}

	/**
	 * Wait for element disabled
	 * 
	 * @param pBy
	 *            A mechanism by which to find elements within a document
	 */
	public void waitForElementDisabled(By pBy) {
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < Constants.TimeOut * 1000) {
			WebElement mElement = this.captureInterface(pBy);
			try {
				if (!mElement.getAttribute("disabled").contains("true")) {
					Functions.waitForSeconds(2);
				} else {
					break;
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Create a new WebDriver
	 */
	@SuppressWarnings("deprecation")
	public void openNewWebDriver() {
		if (Constants.Browsertype.equals("IE")) {
			System.setProperty("webdriver.ie.driver", Constants.Init_Folder + "/IEDriverServer.exe");
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
			dc.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
			dc.setJavascriptEnabled(true);
			this.Driver = new InternetExplorerDriver(dc);
		} else if (Constants.Browsertype.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", Constants.Init_Folder + "/geckodriver.exe");
			this.Driver = new FirefoxDriver();
		}

		else if (Constants.Browsertype.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", Constants.Init_Folder + "/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-infobars");
			this.Driver = new ChromeDriver(options);

		}

		this.Driver.manage().window().maximize();
		this.Driver.manage().timeouts().implicitlyWait(Constants.TimeOut, TimeUnit.SECONDS);
		this.Driver.manage().timeouts().pageLoadTimeout(Constants.TimeOut, TimeUnit.SECONDS);
		this.Driver.manage().timeouts().setScriptTimeout(Constants.TimeOut, TimeUnit.SECONDS);
		this.Wait = new WebDriverWait(this.Driver, Constants.TimeOut);
	}

	/**
	 * Switch to window has Title
	 */
	public void switchToWindowHasTitle(String ptitle) {
		String currentWindow = this.Driver.getWindowHandle();
		for (String winHandle : this.Driver.getWindowHandles()) {
			if (this.Driver.switchTo().window(winHandle).getTitle().equals(ptitle)) {
				break;
			} else {
				this.Driver.switchTo().window(currentWindow);
			}
		}
	}

	public void switchToNewTab() {
		ArrayList<String> tabs = new ArrayList<String>(this.Driver.getWindowHandles());
		this.Driver.switchTo().window(tabs.get(0));
	}

	/**
	 * Wait for table row selected based on aria-selected is true
	 * 
	 * @param pBy
	 */
	public void waitForTableRowSelected(By pBy) {
		this.Wait.until(ExpectedConditions.attributeContains(pBy, "aria-selected", "true"));
	}

	/**
	 * Wait for table row selected based on aria-selected is false
	 * 
	 * @param pBy
	 */
	public void waitForTableRowNotSelected(By pBy) {
		this.Wait.until(ExpectedConditions.attributeContains(pBy, "aria-selected", "false"));
	}

	/**
	 * Wait for number of handles to increase
	 * 
	 * @param pNumOfCurrentHandles
	 *            Number of current handles
	 */
	public void waitForNewWindowHandle(int pNumOfCurrentHandles) {
		int mCount = 0;
		while (this.Driver.getWindowHandles().toArray().length <= pNumOfCurrentHandles) {
			Functions.waitForSeconds(2);
			mCount += 1;
			if (mCount >= 15) {
				break;
			}
		}
		System.out.println("mCount new" + mCount);
	}

	/**
	 * Wait for number of handles to decrease
	 * 
	 * @param pNumOfCurrentHandles
	 *            Number of current handles
	 */
	public void waitForWindowClosed(int pNumOfCurrentHandles) {
		int mCount = 0;
		while (this.Driver.getWindowHandles().toArray().length >= pNumOfCurrentHandles) {
			Functions.waitForSeconds(2);
			mCount += 1;
			if (mCount >= 15) {
				break;
			}
		}
	}

	/**
	 * Wait until an Attribute has expected value
	 * 
	 * @param pBy
	 * @param pAttributeName
	 * @param pAttributeValue
	 */
	public void waitForAttributeValue(By pBy, String pAttributeName, String pAttributeValue) {
		int mCount = 0;
		while (!this.Driver.findElement(pBy).getCssValue(pAttributeName).equalsIgnoreCase(pAttributeValue)
				&& mCount < 10) {
			Functions.waitForSeconds(1);
			mCount += 1;
		}
	}

	public boolean getCheckboxValue(By pBy){
		return this.captureInterface(pBy).isSelected();
	}
	
	public int countRow() {
		return	this.Driver.findElements(By.xpath("//app-incident-list//table/tbody/tr")).size();
	}
	
	public void selectRow(int rowNumber) {
		this.click(By.xpath("//table/tbody/tr[" + rowNumber + "]/td[1]"));
		Functions.waitForSeconds(0.5);
	}
	
	/**
	 * Get Last Row of Table
	 * 
	 * @param pTable
	 * 
	 */
	public List getLastRowElements(By pTable) {
		WebElement Table = this.captureInterface(pTable);

		WebElement last_rows_table = Table.findElement(By.xpath("tbody/tr[last()]"));
		int NumOfCol = last_rows_table.findElements(By.xpath("td")).size();

		List lastRow = new List(NumOfCol, false);
		for (int i = 0; i < NumOfCol; i++) {
			int n = i + 1;
			WebElement col = last_rows_table.findElement(By.xpath("td[" + n + "]"));
			lastRow.add(col.getText(), i);
		}
		return lastRow;
	}

	/**
	 * Get First Row of Table
	 * 
	 * @param pTable
	 * 
	 */
	public List getFirstRowElements(By pTable) {
		WebElement Table = this.captureInterface(pTable);

		WebElement first_rows_table = Table.findElement(By.xpath("tbody/tr[1]"));
		int NumOfCol = first_rows_table.findElements(By.xpath("td")).size();

		List Row = new List(NumOfCol, false);

		for (int i = 0; i < NumOfCol; i++) {
			int n = i + 1;
			try {
				WebElement col = first_rows_table.findElement(By.xpath("td[" + n + "]"));
				Row.add(col.getText(), i);
			} catch (NoSuchElementException e) {
				Row.add("", i);
			}
		}

		return Row;
	}

	/**
	 * Get Row of Table by Value
	 * 
	 * @param pTable
	 * 
	 */
	public List findRowElements(String SearchValue) {
		try {
			WebElement row = this.Driver.findElement(By.xpath(
					"//table[contains(@id,'DataTables_Table')]/tbody/tr/td[contains(.,'" + SearchValue + "')]/.."));
			int NumOfCol = row.findElements(By.xpath("td")).size();

			List rowL = new List(NumOfCol, false);
			for (int i = 0; i < NumOfCol; i++) {
				int n = i + 1;
				WebElement col = row.findElement(By.xpath("td[" + n + "]"));
				rowL.add(col.getText(), i);
			}
			return rowL;
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	/**
	 * Get Row of Table by Value (Edit in line)
	 * 
	 * @param pTable
	 * 
	 */
	public List findRowElementsEditInline(String SearchValue) {
		try {
			WebElement row = this.Driver.findElement(
					By.xpath("//table[contains(@id,'DataTables_Table')]/tbody/tr/td/form/input[contains(@attr-value,'"
							+ SearchValue + "')]/../../.."));
			int NumOfCol = row.findElements(By.xpath("td")).size();

			List rowL = new List(NumOfCol, false);
			for (int i = 0; i < NumOfCol; i++) {
				int n = i + 1;
				WebElement col = row.findElement(By.xpath("td[" + n + "]/form/input"));
				rowL.add(col.getAttribute("attr-value"), i);
			}
			return rowL;
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	/**
	 * Get First Row of Table in Driver Profile table
	 * 
	 * @param pTable
	 * 
	 */
	public List getFirstRowOfDriverProfile() {
		WebElement Table = this.Driver.findElement(I_Global_Settings.ParkingSite.tbl_psTable);

		WebElement first_rows_table = Table.findElement(By.xpath("tbody/tr[1]"));
		int NumOfCol = first_rows_table.findElements(By.xpath("td")).size();

		List firstRow = new List(NumOfCol, false);
		for (int i = 0; i < NumOfCol; i++) {
			int n = i + 1;
			WebElement col = first_rows_table.findElement(By.xpath("td[" + n + "]/form/input"));
			firstRow.add(col.getAttribute("attr-value"), i);
		}
		return firstRow;
	}

	/**
	 * Take Screen Shot for failed test cases
	 * 
	 * @param className
	 * @param method
	 * @param timestamp
	 */

	public void takeScreenshot(String className, String method, LocalTime timestamp) {
		if (this.Driver instanceof TakesScreenshot) {
			TakesScreenshot screenshotTakingDriver = (TakesScreenshot) this.Driver;
			try {
				File localScreenshots = new File(new File("target"), "Failed Case Screenshots");
				if (!localScreenshots.exists() || !localScreenshots.isDirectory()) {
					localScreenshots.mkdirs();
				}
				File screenshot = new File(localScreenshots,
						className + "_" + method + "_" + timestamp.getHour() + "." + timestamp.getMinute() + ".png");
				FileUtils.moveFile(screenshotTakingDriver.getScreenshotAs(OutputType.FILE), screenshot);
				Log.info(MessageFormat.format("Screenshot for class= {0} method={1} saved in: {2}", className, method,
						screenshot.getAbsolutePath()));
			} catch (Exception e1) {
				Log.error("Unable to take screenshot");
			}
		} else {
			Log.info(MessageFormat.format("Driver '{0}' can't take screenshots so skipping it.",
					this.Driver.getClass()));
		}
	}

	/**
	 * General take Screenshot
	 * 
	 * @param className
	 * @param method
	 * @param msg
	 */
	public void takeScreenshot(String className, String method, String msg) {
		if (this.Driver instanceof TakesScreenshot) {
			TakesScreenshot screenshotTakingDriver = (TakesScreenshot) this.Driver;
			try {
				// File localScreenshots = new File(new File("target"), "screenshots");
				File localScreenshots = new File(new File("target"), "Failed Case Screenshots");
				if (!localScreenshots.exists() || !localScreenshots.isDirectory()) {
					localScreenshots.mkdirs();
				}
				File screenshot = new File(localScreenshots, className + "_" + method + "_" + msg + ".png");
				FileUtils.moveFile(screenshotTakingDriver.getScreenshotAs(OutputType.FILE), screenshot);
				Log.info(MessageFormat.format("Screenshot for class= {0} method={1} saved in: {2}", className, method,
						screenshot.getAbsolutePath()));
			} catch (Exception e1) {
				Log.error("Unable to take screenshot");
			}
		} else {
			Log.info(MessageFormat.format("Driver '{0}' can't take screenshots so skipping it.",
					this.Driver.getClass()));
		}
	}

	public static void setTimeOut(SeleniumBrowser Browser, int seconds) {
		Browser.Driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
		Browser.Wait = new WebDriverWait(Browser.Driver, seconds);
	}

	public static void resetTimeOut(SeleniumBrowser Browser) {
		Browser.Driver.manage().timeouts().implicitlyWait(Constants.TimeOut, TimeUnit.SECONDS);
		Browser.Wait = new WebDriverWait(Browser.Driver, Constants.TimeOut);
	}

}
