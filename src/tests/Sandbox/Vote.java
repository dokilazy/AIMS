package tests.Sandbox;

import java.io.File;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.google.common.base.Function;
import com.google.common.base.Verify;
import interfaces.I_AIMS_Home;
import interfaces.I_AIMS_Incident_Details;
import interfaces.I_Global_Common;
import interfaces.I_Global_Settings;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.ValueList;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Log;
import libraries.productFunctions.F_AIMS;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;
import org.openqa.selenium.By;

public class Vote extends DefaultAnnotations {

	@Test
	public void vote() {
		
		int i = 10;
		
		for(int n =0 ; n <i; n++)
		{
		String url = "https://docs.google.com/forms/d/e/1FAIpQLSepKoUbGKqBk6xcJYm5GMhE3XPLyxehM6707IgmbyZASxienA/viewform";

		try {
			Browser.Driver.navigate().to(url);
		} catch (TimeoutException e) {
			try {
				Browser.Wait.until(ExpectedConditions.elementToBeClickable(I_Global_Common.txt_Username));
			} catch (Exception e2) {
				Functions.waitForSeconds(Constants.TimeOut);
			}
		}

		By chkbox1 = By.xpath(
				"//div[@class=' freebirdFormviewerComponentsQuestionCheckboxImageCheckboxGroupContainer']/div[1]/label/div[2]/div/div[2]");
		
		By chkbox1_value = By.xpath(
				"//div[@class=' freebirdFormviewerComponentsQuestionCheckboxImageCheckboxGroupContainer']/div[1]/label/div[2]/div");

		By Send_btn = By.xpath("//span[@class='appsMaterialWizButtonPaperbuttonContent exportButtonContent']");
				
		Browser.click(chkbox1);
		//Browser.waitForAttributeValue(chkbox1_value, "aria-checked", "true");

//		if(Browser.getCheckboxValue(chkbox1_value) == false)
//		{
//			Browser.click(chkbox1);
//		}
		
		Functions.waitForSeconds(0.2);
		Browser.scrollToEle(Send_btn);
		Browser.click(Send_btn);
		Functions.waitForSeconds(1);
		}
		
	}

}
