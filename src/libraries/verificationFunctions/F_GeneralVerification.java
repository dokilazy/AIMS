package libraries.verificationFunctions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.Finder;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import interfaces.I_CP_Common;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.ValueList;
import libraries.ValueList.WindowTitle;
import libraries.generalFunctions.Functions;

public class F_GeneralVerification {

	/**
	 * Check the Notification message on the top page
	 * 
	 * @param Browser
	 * @param pBy
	 * @param pExpectedMsg
	 * @param pResult
	 */
	public static void verifyNoticationMessage(SeleniumBrowser Browser, By pBy, String pExpectedMsg, TCResult pResult) {
		Functions.waitForSeconds(1);
		WebElement e = Browser.captureInterface(pBy);

		if (e == null) {
			pResult.SetResult(false);
			pResult.SetMessage("Notication Element is not dispalyed");
			return;
		}

		if (!e.getText().replaceAll("/n", "").replaceAll("/b", "").replaceAll("/t", "").trim().contains(pExpectedMsg)) {
			pResult.SetResult(false);
			pResult.SetMessage("Notification Msg displayed: " + e.getText() + " intead of:  " + pExpectedMsg);
		}

	}

	/**
	 * Verify that element is visible
	 * 
	 * @param pElement     Element to verify
	 * @param pElementName Name of element to be displayed in error message if error
	 *                     occurs
	 * @param pResult      Result of test case
	 */
	public static void verifyElementVisible(SeleniumBrowser Browser, By pBy, String pElementName, TCResult pResult) {
		if (!Browser.captureInterface(pBy).isDisplayed()) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + " is not displayed");
		}
	}

	/**
	 * Verify that element is visible
	 * 
	 * @param pElement     Element to verify
	 * @param pElementName Name of element to be displayed in error message if error
	 *                     occurs
	 * @param pResult      Result of test case
	 */
	public static void verifyElementVisible(SeleniumBrowser Browser, WebElement pElement, String pElementName,
			TCResult pResult) {
		if (!pElement.isDisplayed()) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + " is not displayed");
		}
	}

	/**
	 * Verify that element is visible (for Swift Elements)
	 * 
	 * @param pBy          A mechanism by which to find elements within a document
	 * @param pElementName Name of element to be displayed in error message if error
	 *                     occurs
	 * @param pResult      Result of test case
	 */
	public static void verifyElementVisibleForSwiftElement(SeleniumBrowser Browser, By pBy, String pElementName,
			TCResult pResult) {

		String mElementID = Browser.getElementID(pBy);
		WebElement mElement = Browser
				.captureInterface(By.xpath("//*[@id = '" + mElementID + "']/../following-sibling::*[1]"));
		if (!mElement.isDisplayed()) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + " is not displayed");
		}
	}

	/**
	 * Verify that element is invisible
	 * 
	 * @param pBy          Locator of element to verify
	 * @param pElementName Name of element to be displayed in error message if error
	 *                     occurs
	 * @param pResult      Result of test case
	 */
	public static void verifyElementInvisible(SeleniumBrowser Browser, By pBy, String pElementName, TCResult pResult) {
		Browser.Driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		WebElement mElement = Browser.captureInterface(pBy);
		if (mElement != null && mElement.isDisplayed()) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + " is still displayed");
		}
		Browser.Driver.manage().timeouts().implicitlyWait(Constants.TimeOut, TimeUnit.SECONDS);
	}

	/**
	 * Verify that element is invisible
	 * 
	 * @param pElement     Element to verify
	 * @param pElementName Name of element to be displayed in error message if error
	 *                     occurs
	 * @param pResult      Result of test case
	 */
	public static void verifyElementInvisible(WebElement pElement, String pElementName,
			TCResult pResult) {
		if (pElement != null && pElement.isDisplayed()) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + " is still displayed");
		}
	}

	/**
	 * Verify that element is selected
	 * 
	 * @param pElement     Element to verify
	 * @param pElementName Name of element to be displayed in error message if error
	 *                     occurs
	 * @param pResult      Result of test case
	 */
	public static void verifyElementSelected(WebElement pElement, String pElementName,
			TCResult pResult) {
		if (!pElement.isSelected()) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + " is not selected");
		}
	}

	/**
	 * Verify that element is not selected
	 * 
	 * @param pElement     Element to verify
	 * @param pElementName Name of element to be displayed in error message if error
	 *                     occurs
	 * @param pResult      Result of test case
	 */
	public static void verifyElementNotSelected(WebElement pElement, String pElementName,
			TCResult pResult) {
		if (pElement.isSelected()) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + " is selected");
		}
	}

	/**
	 * Compare text of actual and expected results
	 * 
	 * @param pFieldName      Name of the element
	 * @param pActualResult   Actual value needs to verify
	 * @param pExpectedResult Expected value
	 * @param pResult         Result of test case
	 */
	public static void verifyElementValue(String pElementName, String pActualResult,
			String pExpectedResult, TCResult pResult) {
		if (!pActualResult.equals(pExpectedResult)) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + " is displayed " + pActualResult + " instead of " + pExpectedResult);
		}
	}

	/**
	 * Compare text of actual and expected results
	 * 
	 * @param pFieldName      Name of the element
	 * @param pActualResult   Actual value needs to verify
	 * @param pExpectedResult Expected value
	 * @param pResult         Result of test case
	 */
	public static void verifyElementContent(SeleniumBrowser Browser, String pElementName, String pActualResult,
			String pExpectedResult, TCResult pResult) {
		if (!pActualResult.contains(pExpectedResult)) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + " is displayed " + pActualResult + " instead of " + pExpectedResult);
		}
	}

	/**
	 * Compare text of actual and expected results
	 * 
	 * @param pFieldName      Name of the element
	 * @param pActualResult   Actual value needs to verify
	 * @param pExpectedResult Expected value
	 * @param pResult         Result of test case
	 */
	public static void verifyElementContentNotNull(SeleniumBrowser Browser, String pElementName, By pBy,
			TCResult pResult) {
		String Actualvalue = Browser.captureInterface(pBy).getAttribute("value");
		if (Actualvalue == "" || Actualvalue == null) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + "'s value is NULL ");
		}
	}

	/**
	 * Compare text of actual and expected results (for Swift Elements)
	 * 
	 * @param pFieldName      Name of the element
	 * @param pBy             Mechanism of element that contains actual value needs
	 *                        to verify
	 * @param pExpectedResult Expected value
	 * @param pResult         Result of test case
	 */
	public static void verifyElementValueCustom(SeleniumBrowser Browser, String pElementName, By pBy,
			String pExpectedResult, TCResult pResult) {
		By mBy = By.xpath("//*[@id = '" + Browser.getElementID(pBy) + "']/../following-sibling::*[1]");
		WebElement mElement = Browser.captureInterface(mBy);
		if (!mElement.getText().equals(pExpectedResult)) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + " is displayed " + mElement.getText() + " instead of " + pExpectedResult);
		}
	}

	/**
	 * Verify selected value in a drop down list
	 * 
	 * @param pFieldName      Name of the element
	 * @param pBy             Mechanism of element that contains actual value needs
	 *                        to verify
	 * @param pExpectedResult Expected value
	 * @param pResult         Result of the test case
	 */
	public static void verifySelectedValueInDropdown(SeleniumBrowser Browser, String pElementName, By pBy,
			String pExpectedResult, TCResult pResult) {
		Select mSelect = new Select(Browser.captureInterface(pBy));
		if (!mSelect.getFirstSelectedOption().getText().equals(pExpectedResult)) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + " is displayed " + mSelect.getFirstSelectedOption().getText()
					+ " instead of " + pExpectedResult);
		}
	}

	/**
	 * Verify element enable
	 * 
	 * @param pElementName Name of the element
	 * @param pBy          Mechanism of element that contains actual value needs to
	 *                     verify
	 * @param pResult      Result of the test case
	 */
	public static void verifyElementEnabled(SeleniumBrowser Browser, String pElementName, By pBy, TCResult pResult) {
		WebElement mElement = Browser.captureInterface(pBy);
		if (!mElement.isEnabled()) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + " is not enabled");
		}
	}

	/**
	 * Verify element not enable
	 * 
	 * @param pElementName Name of the element
	 * @param pBy          Mechanism of element that contains actual value needs to
	 *                     verify
	 * @param pResult      Result of the test case
	 */
	public static void verifyElementNotEnabled(SeleniumBrowser Browser, String pElementName, By pBy, TCResult pResult) {
		WebElement mElement = Browser.captureInterface(pBy);
		if (mElement.isEnabled()) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + " is enabled");
		}
	}

	/**
	 * Verify Dialog displays
	 * 
	 * @param cTitle   Control for dialog title
	 * @param eTitle   Expected dialog title
	 * @param cMessage Control for dialog Message
	 * @param eMessage Expected dialog Message
	 * @param pResult  Result of validation
	 */
	public static void verifyPopupIsDisplayedCorrectly(SeleniumBrowser Browser, By cTitle, String eTitle, By cMessage,
			String eMessage, TCResult pResult) {

		// Browser.switchFrame(ValueList.Frame.Main);

		F_GeneralVerification.verifyElementValue("Popup title", Browser.captureInterface(cTitle).getText(),
				eTitle, pResult);
		F_GeneralVerification.verifyElementValue("Popup message", Browser.captureInterface(cMessage).getText(),
				eMessage, pResult);

		Browser.switchParentFrame();
	}

	/**
	 * Verify window is not displayed
	 */
	public static void verifyWindowNotDisplayed(SeleniumBrowser Browser, String pWindowTitle, TCResult pResult) {
		for (String winHandle : Browser.Driver.getWindowHandles()) {
			if (Browser.Driver.switchTo().window(winHandle).getTitle().equals(pWindowTitle)) {
				pResult.SetResult(false);
				pResult.SetMessage(pWindowTitle + " is displayed ");
				break;
			}
		}
		Browser.switchToWindowHasTitle(WindowTitle.InsuranceBrokerTechnology);
		Browser.switchParentFrame();
	}

	/**
	 * Verify popup is closed
	 * 
	 * @param pBy         Locator of element to verify
	 * @param pPopupTitle Title of the popup
	 * @param pResult     Result of test case
	 */
	public static void verifyPopupIsClosed(SeleniumBrowser Browser, By pBy, String pPopupTitle, TCResult pResult) {
		// Browser.switchFrame(Frame.Main);

		F_GeneralVerification.verifyElementInvisible(Browser, pBy, pPopupTitle, pResult);

		Browser.switchParentFrame();
	}

	/**
	 * Verify two values with tolerance
	 * 
	 * @param pFirstValue  First Value to verify
	 * @param pSecondValue Second Value to verify
	 * @param pResult      Result of test case
	 */
	public static void verifyTwoIntValuesWithTolerance(SeleniumBrowser Browser, String pElementName, int pFirstValue,
			int pSecondValue, int pTolerance, TCResult pResult) {
		if (Math.abs(pFirstValue - pSecondValue) > pTolerance) {
			pResult.SetResult(false);
			pResult.SetMessage(
					pElementName + pFirstValue + " and " + pSecondValue + "is not within tolerance as " + pTolerance);
		}
	}

	public static void verifyDatetime(SeleniumBrowser Browser, String pElementName, String pactualDatetime,
			Date pexpectedDatetime, int pToleranceinMinute, TCResult pResult) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// DateFormat dateFormat = new SimpleDateFormat("MM/d/yyyy HH:mm");
		if (pactualDatetime == "") {
			pResult.SetResult(false);
			pResult.SetMessage("Empty actual Datetime");
			return;
		}

		if (pactualDatetime.endsWith("M")) {
			dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
		}

		try {

			Date actualDatetime = dateFormat.parse(pactualDatetime);

			long diff = getDateDiff(actualDatetime, pexpectedDatetime, TimeUnit.MINUTES);

			if ((Math.abs(diff)) > pToleranceinMinute) {
				pResult.SetResult(false);
				pResult.SetMessage(pElementName + pactualDatetime + " and " + pexpectedDatetime.toString()
						+ " is not within tolerance as " + pToleranceinMinute + " minutes");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			pResult.SetResult(false);
			pResult.SetMessage("Parse string " + pactualDatetime + " To Data time Failed");
		}
	}

	/**
	 * Get a diff between two dates
	 * 
	 * @param date1    the oldest date
	 * @param date2    the newest date
	 * @param timeUnit the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	public static void verifyElementNotExist(SeleniumBrowser Browser, String pElementName, By pBy, TCResult pResult) {
		WebElement e = Browser.captureInterface(pBy);
		if (e != null) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + " " + "still exsit");
		}
	}

	public static void verifyElementExist(SeleniumBrowser Browser, String pElementName, By pBy, TCResult pResult) {
		WebElement e = Browser.captureInterface(pBy);
		if (e == null) {
			pResult.SetResult(false);
			pResult.SetMessage("Element " + pElementName + " does not exsit");
		}
	}

	public static void verifyTableNull(SeleniumBrowser Browser, TCResult pResult) {
		Browser.setTimeOut(Browser, 5);
		By colElement = By.xpath("//table/tbody");
		By rowElement = I_CP_Common.rowEle;
		verifyElementExist(Browser, "Table columns", colElement, pResult);
		verifyElementNotExist(Browser, "Table rows", rowElement, pResult);
		Browser.resetTimeOut(Browser);
	}

	public static void verifyTableHaveOnlyOneResult(SeleniumBrowser Browser, TCResult pResult) {
	 int rowCount =	Browser.countRow();
		
		if (rowCount != 1) {
			pResult.SetResult(false);
			pResult.SetMessage("Incident search result displays: " + rowCount+ " intead of 1");
		}
	}

	public static void verifyImageOnpage(String data, TCResult pResult) {
		Screen screen = new Screen();

		Pattern expectedimage = new Pattern(data);
		String img = screen.capture().save("target/screenshots/", "verifyImageOnpage");

		Finder f1 = new Finder(screen.capture().getImage());
		f1.find(expectedimage);
		if (f1.hasNext()) {
			Match m = f1.next();
			System.out.println("Match found with " + (m.getScore() * 100) + "%");
			f1.destroy();
		} else {
			System.out.println("No Match Found");
			pResult.SetResult(false);
			pResult.SetMessage("Image does not exsit");
		}
	}

	public static void verifyValue(String pElementName, int pActualResult, int pExpectedResult, TCResult pResult) {
		if (pActualResult != pExpectedResult) {
			pResult.SetResult(false);
			pResult.SetMessage(pElementName + " is displayed " + pActualResult + " instead of " + pExpectedResult);
		}
	}

}
