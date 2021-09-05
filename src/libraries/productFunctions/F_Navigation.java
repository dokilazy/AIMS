package libraries.productFunctions;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import interfaces.I_Global_Common;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.ValueList;
import libraries.generalFunctions.Functions;


public class F_Navigation {
	
	
	/**
	 * Login to AIMS page with default user
	 */
	public static void loginAIMS(SeleniumBrowser Browser, String tUser, String tPass) {
		
		try {
			Browser.Driver.navigate().to(Constants.AIMS_Url);
		} catch (TimeoutException e) {
			try {
				Browser.Wait.until(ExpectedConditions.elementToBeClickable(I_Global_Common.txt_Username));
			} catch (Exception e2) {
				Functions.waitForSeconds(Constants.TimeOut);
			}
		}
		if(Browser.Driver.getTitle().contains("Login")) {
		Browser.clear(I_Global_Common.txt_Username);
		Browser.sendKeys(I_Global_Common.txt_Username, tUser);
		Browser.clear(I_Global_Common.txt_Password);
		Browser.sendKeys(I_Global_Common.txt_Password, tPass);
		Browser.waitAndClick(I_Global_Common.btn_Login);
		Functions.waitForSeconds(1);
		}
	}
	

	
	/**
	 * Login to Colc Parking page with default user
	 */
	public static void loginCoLcParking(SeleniumBrowser Browser, String tUser, String tPass) {
		
		try {
			Browser.Driver.navigate().to(Constants.ColcParking_Url);
		} catch (TimeoutException e) {
			try {
				Browser.Wait.until(ExpectedConditions.elementToBeClickable(I_Global_Common.txt_Username));
			} catch (Exception e2) {
				Functions.waitForSeconds(Constants.TimeOut);
			}
		}
		if(Browser.Driver.getTitle().contains("Login")) {
		Browser.clear(I_Global_Common.txt_Username);
		Browser.sendKeys(I_Global_Common.txt_Username, tUser);
		Browser.clear(I_Global_Common.txt_Password);
		Browser.sendKeys(I_Global_Common.txt_Password, tPass);
		Browser.waitAndClick(I_Global_Common.btn_Login);
		Functions.waitForSeconds(2);
		}
	}
	
	
	
	/**
	 * Login to DHL page with default user
	 */
	public static void loginDHL(SeleniumBrowser Browser, String tUser, String tPass) {
		
		try {
		//	Browser.Driver.navigate().to(Constants.BaseUrl);
			Browser.Driver.navigate().to(Constants.AIMS_Url);
		} catch (TimeoutException e) {
			try {
				Browser.Wait.until(ExpectedConditions.elementToBeClickable(I_Global_Common.txt_Username));
			} catch (Exception e2) {
				Functions.waitForSeconds(Constants.TimeOut);
			}
		}
		if(Browser.Driver.getTitle().contains("Login")) {
		Browser.clear(I_Global_Common.txt_Username);
		Browser.sendKeys(I_Global_Common.txt_Username, tUser);
		Browser.clear(I_Global_Common.txt_Password);
		Browser.sendKeys(I_Global_Common.txt_Password, tPass);
		Browser.waitAndClick(I_Global_Common.btn_Login);
		Functions.waitForSeconds(3);
		
//		By menulogo = By.id("logo");
//		Browser.click(menulogo);
//		
//		By aimslink = By.xpath("//a[@title='Alarm and Incident Management']");
//		Browser.click(aimslink);
//		Browser.switchToWindowHasTitle("ASA - Alarm and Incident Management System");

//		Browser.Driver.navigate().to(Constants.AIMS_Url);
//		Functions.waitForSeconds(1);
		
		}
	}
		
	 

	/**
	 * Wait for a window popup
	 */
	public static void waitForWindowPopup(SeleniumBrowser Browser) {
		int mTimeout = Constants.TimeOut;
		while (Browser.Driver.getWindowHandles().toArray().length < 2 && mTimeout > 5) {
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
			}
			mTimeout -= 5;
		}
	}

	/**
	 * Wait for a window to close
	 */
	public static void waitForWindowClose(SeleniumBrowser Browser) {
		while (Browser.Driver.getWindowHandles().toArray().length > 1) {
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * wait For an Alert appear
	 * @param driver
	 */
	public static void waitForAlert(WebDriver driver) {
		int i = 0;
		while (i++ < 5) {
			try {
				driver.switchTo().alert();
				break;
			} catch (NoAlertPresentException e) {
				Functions.waitForSeconds(1);
				continue;
			}
		}
		
	}

	
	/**
	 * Wait for loading screen complete
	 */
	public static void waitForLoading(SeleniumBrowser Browser) {
		By mLoadingImage =  By.xpath("//img[contains(@src,'ajax-loading.gif')]/..");
		Boolean Wait = true;
		String mStyle="";
		int mCount=0;
		WebElement mElement =  Browser.captureInterface(mLoadingImage); 
		while (Wait) {
			mCount+=1;
			mElement =  Browser.captureInterface(mLoadingImage); 
			try {
				mStyle=mElement.getAttribute("style");
			} catch (Exception e) {
				mStyle="";
			}
			if (mStyle!=null) {
				if (!mStyle.contains("display: none")) {
					Functions.waitForSeconds(2);
				} else {
					Wait = false;
				}
			}
			
			if (mCount > 180) {
				Wait = false;
			}
		}
	}
		/**
		 * Go to Tab/ Page
		 */
		
		public static void goToPage (SeleniumBrowser Browser, By pBy) {
		
			Browser.click(pBy);
			Functions.waitForSeconds(0.5);
			
		}
		
		/**
		 * Refresh Page
		 */
		public static void RefreshPage (SeleniumBrowser Browser) {
			Browser.Driver.navigate().refresh();
			Functions.waitForSeconds(5);
		}
		
		
		
	
}
