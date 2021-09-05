package interfaces;

import org.openqa.selenium.By;

public class I_Global_Common {
	public static By txt_Username = By.id("username");
	public static By txt_Password = By.id("password");
	public static By btn_Login = By.id("button");

	public static By Tab_Dashboard = By.xpath("//li/a[@title='Dashboard']");
	public static By Tab_Event = By.xpath("//i[@class='icons8-system-task']");
	public static By Tab_Settings = By.xpath("//i[@class='icons8-settings-3']");
	public static By dd_selectSize = By.xpath("//label[contains(.,'Show')]/select");
	
}
