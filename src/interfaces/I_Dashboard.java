package interfaces;

import org.openqa.selenium.By;


public class I_Dashboard {
	public static By ltxt_SyncCenter = By.xpath("//a[@class='action-command ng-binding']");
	public static By tbl_Blacklist = By.xpath("//table[@id='DataTables_Table_0']");
	public static By tbl_CurrentParking = By.xpath("//table[@id='DataTables_Table_2']");
	public static By tbl_LatestEvent = By.xpath("//table[@id='DataTables_Table_1']");
	
	
	public static By frm_message = By.xpath("//div[@class='ui-notification custom-template toast-container in ng-scope primary clickable']");
	
	public static By lbl_message = By.xpath("//div[@class='ui-notification custom-template toast-container in ng-scope primary clickable']/div/div/span");
	
	

	
}
