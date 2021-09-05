package tests.Sandbox;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.generalFunctions.Functions;
import tests.DefaultAnnotations;

public class FacebookTestParallel  extends DefaultAnnotations {


		
	@Test
	public void LoginFacebook() {
		
		String fbUrl = "https://facebook.com";
		String userName = "TestAccount123";
		
		Browser.Driver.navigate().to(fbUrl);
		
		By txt_Username =  By.id("email");
		Browser.Wait.until(ExpectedConditions.elementToBeClickable(txt_Username));
		Browser.Driver.findElement(txt_Username).sendKeys(userName);
		
		By txt_Password =  By.xpath("//input[@id='pass']");
		Browser.Wait.until(ExpectedConditions.elementToBeClickable(txt_Password));
		Browser.Driver.findElement(txt_Password).sendKeys("Abcd1234");
		
		By btn_Login =  By.xpath("//button[@name='login']");
		Browser.Driver.findElement(btn_Login).click();
		
		By txt_ErrorMsg = By.xpath("//div[contains(.,'Mật khẩu bạn đã nhập không chính xác.')]");
		Browser.Wait.until(ExpectedConditions.elementToBeClickable(txt_ErrorMsg));
	}
}
