package libraries.productFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import interfaces.I_EP;
import interfaces.I_Global_Common;
import interfaces.I_Portal;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Log;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class F_EP {
	public static void OpenEP(SeleniumBrowser Browser, String tUser, String tPass) {

		try {
			Browser.Driver.navigate().to(Constants.BaseUrl);
		} catch (TimeoutException e) {
			try {
				Browser.Wait.until(ExpectedConditions.elementToBeClickable(I_Global_Common.txt_Username));
			} catch (Exception e2) {
				Functions.waitForSeconds(Constants.TimeOut);
			}
		}

		if (Browser.Driver.getTitle().contains("Login")) {
			Browser.clear(I_Global_Common.txt_Username);
			Browser.sendKeys(I_Global_Common.txt_Username, tUser);
			Browser.clear(I_Global_Common.txt_Password);
			Browser.sendKeys(I_Global_Common.txt_Password, tPass);
			Browser.waitAndClick(I_Global_Common.btn_Login);
			Functions.waitForSeconds(1);
		}

		F_Navigation.goToPage(Browser, I_Portal.btn_EPApp);
		F_Navigation.waitForLoading(Browser);
	}

	public static void OpenEPbyURL(SeleniumBrowser Browser, String tUser, String tPass) {
		try {
			Browser.Driver.navigate().to(Constants.BaseUrl + "/evidence-presenter/index.html");
		} catch (TimeoutException e) {
			try {
				Browser.Wait.until(ExpectedConditions.elementToBeClickable(I_Global_Common.txt_Username));
			} catch (Exception e2) {
				Functions.waitForSeconds(Constants.TimeOut);
			}
		}

		if (Browser.Driver.getTitle().contains("Login")) {
			Browser.clear(I_Global_Common.txt_Username);
			Browser.sendKeys(I_Global_Common.txt_Username, tUser);
			Browser.clear(I_Global_Common.txt_Password);
			Browser.sendKeys(I_Global_Common.txt_Password, tPass);
			Browser.waitAndClick(I_Global_Common.btn_Login);
			Functions.waitForSeconds(1);
		}

	}

	public static void openSearch(SeleniumBrowser Browser) {

		// Browser.waitForElementVisible(I_EP.btn_SelectInc);
		Functions.LogMethodName();
		int n = 0;
		do {
			Browser.clickJavascript(I_EP.btn_SelectInc);
			n++;
		} while (n <= 3 && !Browser.captureInterface(I_EP.div_IncidentDropdown).getAttribute("class").contains("open"));
	}

	public static void fillSearch(String input) {
		String txt_Search = "Imgs/SearchIncident.png";
		Functions.sendKeyBySikuli(txt_Search, input);
	}

	public static void SelectIncident(SeleniumBrowser Browser, String ReferID) {
		Browser.click(I_EP.btn_SelectInc);
		Browser.enter(I_EP.txt_Search, ReferID);
		Browser.click(By.cssSelector("body"));
		Browser.click(I_EP.div_Incidents);
		F_Navigation.waitForLoading(Browser);
	}

	
	public static void openPrjectMenu(SeleniumBrowser Browser) {
		Browser.click(I_EP.btn_Project);
		//Browser.waitForElementVisible(I_EP.div_Project);
	}
	
	public static void createNewProject(SeleniumBrowser Browser, String title, String desc) {
		openPrjectMenu(Browser);
		Browser.click(I_EP.btn_NewProTab);
		Browser.enter(I_EP.txt_newProName, title);
		Browser.enter(I_EP.txt_commentNewPro, desc);
		Browser.waitForElementVisible(I_EP.btn_CreateNew);
		Browser.waitForElementEnabled(I_EP.btn_CreateNew);
		Browser.click(I_EP.btn_CreateNew);
	}
	
	public static void updateCurrentProject(SeleniumBrowser Browser, String title, String desc) {
		
		
		Browser.enter(I_EP.txt_ProjectName, title);
		Browser.enter(I_EP.txt_ProjectDesc, desc);
		
		Browser.click(I_EP.btn_UpdateProject);
	}
	
	
	
	public static void VerifyIncidentList(SeleniumBrowser Browser, TCResult pResult) {

		List<WebElement> IncidentlIst = Browser.Driver.findElements(I_EP.div_Incidents);
		if (IncidentlIst.size() != 1) {
			pResult.SetResult(false);
			pResult.SetMessage("Number of incident is: " + IncidentlIst.size() + "instead of 1");
		}
	}

	public static void VerifyEvidenceList(SeleniumBrowser Browser, TCResult pResult, List<String> expectedEvidentList) {

		List<String> EvidentList = Functions.getDropdownList(Browser, I_EP.div_Evidences, "div/div[2]/div/div[6]");

		if (EvidentList == null) {
			pResult.SetResult(false);
			pResult.SetMessage("Cannot Get list");
			return;
		}

		int i = 0;
		for (i = 0; i < EvidentList.size(); i++) {
			if (!expectedEvidentList.contains(EvidentList.get(i))) {
				pResult.SetResult(false);
				pResult.SetMessage("Incident list not correct");
				break;
			}
		}

	}
	
	
	public static void waitLoader(SeleniumBrowser Browser) {
		Browser.waitForElementNotVisible(By.id("loader"));
	}
	

}
