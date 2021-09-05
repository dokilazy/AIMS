package libraries.productFunctions;

import java.awt.List;
import java.text.MessageFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import interfaces.I_CP_Contraventions;
import interfaces.I_CP_EnforementLog;
import libraries.SeleniumBrowser;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Mouse;

public class F_CPEnformentLog {
	
	public static void gotoLogpage(SeleniumBrowser Browser) {
			Browser.clickJavascript(I_CP_EnforementLog.tab_EnLog);  
			//Browser.click(I_CP_Contraventions.tab_Home);
			Functions.waitForSeconds(1);
	}
	
	public static void searchByDateTime(SeleniumBrowser Browser, String duration) {
		String id = "dateTimeOption_0";
		switch(duration) {
		case "Today": 
			id = "dateTimeOption_1"; break;
		case "Last 24 hours": 
			id = "dateTimeOption_2"; break;
		case "This week": 
			id = "dateTimeOption_3"; break;
		case "Custom": 
			id = "dateTimeOption_4"; break;
		};
		String p = "//app-enforcement-search/div/ul/li/div/div/div/input[@id=\'"+ id + "\']";
		Browser.click(By.xpath(p));
		Functions.waitForSeconds(1);
	}
	
	public static void searchByStatus(SeleniumBrowser Browser, String pValue) {
		String status = Functions.convert1stUppercase(pValue);
		WebElement st = null;
		st = Browser.captureInterface(
				By.xpath("//*[@class='dlm-token-btn search-input']/label[contains(.,'" + status + "')]"));
		st.click();
		Functions.waitForSeconds(1);
	}
	
	public static List findContravention(SeleniumBrowser Browser, String id) {
		
			try {
				WebElement row = Browser.captureInterface(By.xpath(
						"//table/tbody/tr/td[contains(.,'" + id + "')]/.."));
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
	
	
	
	

	
	
}
