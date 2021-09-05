package libraries.generalFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import libraries.SeleniumBrowser;

public class Mouse {

	public static void NavigateAndClick(SeleniumBrowser Browser, By locator) {
		try {
			Actions action = new Actions(Browser.Driver);
			WebElement element = Browser.captureInterface(locator);
			action.moveToElement(element);
			action.wait(1);
			action.click();
			action.build().perform();
		} catch (Exception e) {

		}
	}

	public static void MoveMouseToElement(SeleniumBrowser Browser, By locator) {
		try {
			Actions action = new Actions(Browser.Driver);
			WebElement element = Browser.captureInterface(locator);
			action.moveToElement(element);
			action.build().perform();
		} catch (Exception e) {

		}
	}

	public static void RightclickOnElement(SeleniumBrowser Browser, WebElement element) {
		try {
			Actions action = new Actions(Browser.Driver);
			// WebElement element = Browser.captureInterface(locator);
			// action.moveToElement(element);
			action.contextClick(element);
			action.build().perform();
		} catch (Exception e) {

		}
	}

	public static void DragDropElement(SeleniumBrowser Browser, WebElement sourceEle, WebElement targetEle) {
		try {
			
			if (sourceEle.isDisplayed() && targetEle.isDisplayed()) {	
			Actions action = new Actions(Browser.Driver);
			action.dragAndDrop(sourceEle, targetEle);
			action.build().perform();
			}else {
				System.out.println("Element was not displayed to drag"); }
			
		} catch (Exception e) {
		}
	}

	public static void DragDropElement(SeleniumBrowser Browser, By sourceLocator, By targetLocator) {
		try {
			Actions action = new Actions(Browser.Driver);
			WebElement sourceEle = Browser.captureInterface(sourceLocator);
			WebElement targetEle = Browser.captureInterface(targetLocator);
			action.dragAndDrop(sourceEle, sourceEle);
			action.wait(1);
			action.build().perform();
		} catch (Exception e) {
		}
	}

	
	public static void FreeMouse(SeleniumBrowser Browser) {
		Browser.click(By.cssSelector("body"));
		Functions.waitForSeconds(0.5);
	}
	
	
}