package interfaces;

import org.openqa.selenium.By;

public class I_DHL_Home {
	public static By  tab_Settings     = By.xpath("//li[@title='setting']/a/i");
	public static By  tab_Inbox   =    By.xpath("//app-tabs/ul/li[@title='home']/a/i");

	public static By  btn_AddIncident   =    By.xpath("//i[@class='icons8-plus-math']");
	public static By btn_addIncidentOnToolbar    =   By.xpath("//div[@class='global-bar']//div[@class='btn m-r-10']/div[text()='New incident']");
	public static By btn_deleteIncidentOnToolbar    =   By.xpath("//div[@class='global-bar']//div[@class='btn m-r-10']/div[text()='Delete']");
	public static By btn_deleteIncidentIndetails    =   By.xpath("//app-incident-detail/div[@id='sidebar-right']/div/div[last()]/button[contains(.,'Delete')]");
	public static By btn_undeleteIncidentOnToolbar    =   By.xpath("//div[@class='global-bar']//div[@class='btn m-r-10']/div[text()='Undelete']");
	public static By btn_undeleteIncidentIndetails    =   By.xpath("//app-incident-detail/div[@id='sidebar-right']/div/div[last()]/button[contains(.,'Undelete')]");

	public static By btn_OkConfirmDialog = By.xpath("//app-confirm-delete-dialog//div[last()]/button[contains(.,'Ok')]");
	public static By btn_CancelConfirmDialog = By.xpath("//app-confirm-delete-dialog//div[last()]/button[contains(.,'Cancel')]");
	
	public static By  btn_Search   =    By.xpath("//div[@class='container-fluid']/div[1]/button[1]");
	public static By  ddl_IncidentType  =    By.xpath("//input[@class='ui-select-search input-xs ng-pristine ng-untouched ng-valid']");
	
	public static By tbl_IncidentTable = By.xpath("//table[@id='inbox-list']");
	public static By div_SuccessMessage = By.xpath("//div[contains(@class,'snotify snotify-rightBottom')]/ng-snotify-toast");
	public static By div_ErrorMessage =  By.xpath("//body/div[@class='ui-notification ng-scope error']/div");
	
	public static By div_AllOpeningTab =  By.xpath("//li[@class='active']/a/div");
	public static By lbl_TabName = By.xpath("//li[@class='active']/a/div");
	public static By btn_CloseCurrentTab = By.xpath("//li[@class='active']/i[@class='icon icons8-delete-3']");

	
	public static By lbl_newIncidentNo = By.xpath("//span[@id='nbNewIncidents']");
	
	public static By lbl_IncidentNo = By.xpath("//span[@id='nbIncidents']");
	
	public static By frm_newIncidentWarning = By.xpath("//app-broker-message/div/div");
	public static By btn_DeclineAllWarning = By.xpath("//div[@title='Decline all']");
	public static By btn_AcceptWarning = By.xpath("//div[@title='Accept']");
	public static By btn_DeclineWarning = By.xpath("//div[@title='Decline']");
	//------- Search panel ----
	public static By txt_incidentCode = By.xpath("//ul/li/div[2]/input[@name='incidentCode']");
	public static By txt_incidentType   = By.xpath("//app-dropdown-incident-type//input");

	public static By btn_extendCustomFilter   = By.xpath("//li/div/div[contains(.,'Custom fields')]/div");
	public static By txt_lastEventLocation = By.xpath("//ul/li/div[2]/input[@name='location']");
	public static By txt_lastStation = By.xpath("//ul/li/div[2]/input[@name='lastStation']");
	
	public static By txt_addinfor = By.xpath("//ul/li/div[2]/input[@name='additionalInfo']");
	//public static By div_incidentTypeDropdown   =	By.xpath("");
	
	
}
