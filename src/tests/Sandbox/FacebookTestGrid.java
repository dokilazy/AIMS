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

public class FacebookTestGrid  { // extends DefaultAnnotations {


	static WebDriver driver;
	static WebDriverWait wait;
	
	@Test (priority =1)
	public void setUp() throws MalformedURLException{
		
		String nodeURL = "http://192.168.1.92:35396/wd/hub";
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setBrowserName("chrome");
		cap.setVersion("91.0.4472.19");
		cap.setPlatform(Platform.WIN10);

		System.setProperty("webdriver.chrome.driver", "C:\\Work\\Automation\\myFW\\Init\\chromedriver.exe");
		System.setProperty("webdriver.chrome.whitelistedIps", "");
		driver = new RemoteWebDriver(new URL(nodeURL), cap);
	}
	
	
	@Test(priority =2)
	public void LoginFacebook() {
		
		String fbUrl = "https://facebook.com";
		String userName = "TestAccount123";
		
		driver.navigate().to(fbUrl);
		
		By txt_Username =  By.id("email");
		wait.until(ExpectedConditions.elementToBeClickable(txt_Username));
		driver.findElement(txt_Username).sendKeys(userName);
		
		By txt_Password =  By.xpath("//input[@id='pass']");
		wait.until(ExpectedConditions.elementToBeClickable(txt_Password));
		driver.findElement(txt_Password).sendKeys("Abcd1234");
		
		By btn_Login =  By.xpath("//button[@name='login']");
		driver.findElement(btn_Login).click();
		
		By txt_ErrorMsg = By.xpath("//div[contains(.,'Mật khẩu bạn đã nhập không chính xác.')]");
		wait.until(ExpectedConditions.elementToBeClickable(txt_ErrorMsg));
		
	}
}
