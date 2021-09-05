package tests.Sandbox;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import interfaces.I_CP_Settings;
import interfaces.I_Global_Common;
import libraries.CPValueList;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.generalFunctions.AMQMessageProducer;
import libraries.generalFunctions.Functions;
import libraries.productFunctions.F_CPContraventions;
import libraries.productFunctions.F_CPSettings;
import libraries.productFunctions.F_CP_common;
import libraries.productFunctions.F_Navigation;
import tests.DefaultAnnotations;

public class VoteTest {//extends DefaultAnnotations {

	@Test()
	public void vote() {

//		public SeleniumBrowser Browser;
//		  DesiredCapabilities cap = DesiredCapabilities.chrome();
//		  cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
////		  cap.setCapability(ChromeOptions.CAPABILITY, options);
//		  cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
//		  cap.setJavascriptEnabled(true);	
//		
//		 Browser.Driver = new ChromeDriver(cap);
//		
//		 Browser.Driver.manage().window().maximize();
//			Browser.Driver.manage().timeouts().implicitlyWait(Constants.TimeOut, TimeUnit.SECONDS);
//			Browser.Driver.manage().timeouts().pageLoadTimeout(Constants.TimeOut, TimeUnit.SECONDS);
//			Browser.Driver.manage().timeouts().setScriptTimeout(Constants.TimeOut, TimeUnit.SECONDS);
//			
//			Browser.Wait = new WebDriverWait(Browser.Driver, Constants.TimeOut);
//		//	Constants.CurrentHandle.add(Browser.Driver.getWindowHandle());
//			

		String itemName = "Imgs/lbl_PicName.png";
		String pageURL = "https://yolohayoga.com/artist-contest/";

//		try {
//			Browser.Driver.navigate().to(pageURL);
//		} catch (TimeoutException e) {
//			try {
//				Browser.Wait.until(ExpectedConditions.elementToBeClickable(I_Global_Common.txt_Username));
//			} catch (Exception e2) {
//				Functions.waitForSeconds(Constants.TimeOut);
//			}
//		}
//		
				
		for (int i = 0; i < 5; i++) {
	
			
			
			if(i == 0)
		Functions.clickBySikuli("Imgs/tab_openChrome.png");
		Functions.waitForSeconds(1);
		
		Functions.clickBySikuli("Imgs/btn_clearCookie.png");
		Functions.waitForSeconds(1);
		Functions.clickBySikuli("Imgs/btn_refreshpage.png");
		
		
		Functions.clickBySikuli("Imgs/btn_openRandomUser.png");
		
		Functions.waitForSeconds(1);
		Functions.clickBySikuli("Imgs/btn_refreshNew.png");
		Functions.waitForSeconds(1);
		
		//Functions.clickBySikuli("Imgs/tab_openChrome.png");

	//	F_Navigation.RefreshPage(Browser);
		Functions.waitForSeconds(4);
			
//			By section1 = By.xpath("//main/div/section[2]/div[2]/div//iframe");
//			By section2 = By.xpath("//main/div/section[2]/div[2]/div//iframe");
//			By section3 = By.xpath("//main/div/section[3]/div[2]/div//iframe");
//			Browser.scrollToEle(section1);
//			Browser.scrollToEle(section2);
			for (int n = 0; n < 20; n++) {
				Functions.clickBySikuli("Imgs/btn_scrollDown.png");
			//	Browser.scrolldown(1000);
				
				Functions.waitForSeconds(0.5);
				
				if (Functions.checkExist(itemName) == true) {
					System.out.print("Da Tim ra hinh !");
			//		Browser.scrolldown(200);
					Functions.clickBySikuli("Imgs/btn_LikePic3.png");
					// Functions.clickBySikuli("Imgs/btn_LikePic3.png");
					// Functions.clickBySikuli("Imgs/btn_LikePic3.png");
					break;
				}
				
				
			}
			
			

			
		//	Browser.Driver.quit();

		}

		// Functions.waitForSeconds(10);

		// Functions.waitForSeconds(4);
		// Functions.clickBySikuli(itemName);

		

	}

}
