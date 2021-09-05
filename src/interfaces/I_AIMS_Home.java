package interfaces;
import org.openqa.selenium.By;
public class I_AIMS_Home {
	
	
	public static By  tab_Settings     = By.xpath("//li[@title='setting']/a/i");
	
	public static By  btn_AddIncident   =    By.xpath("//i[@class='icons8-plus-math']");
	public static By btn_addIncidentOnToolbar    =   By.xpath("//div[@class='global-bar']//div[@class='btn m-r-10']/div[text()='New incident']");
	public static By  btn_Search   =    By.xpath("//div[@class='container-fluid']/div[1]/button[1]");
	public static By  ddl_IncidentType  =    By.xpath("//input[@class='ui-select-search input-xs ng-pristine ng-untouched ng-valid']");
	public static By  txt_IncidentCode   =    By.xpath("//span[@class='form-control incident-code ng-binding']");
	public static By  btn_HomeTab   =    By.xpath("//ul[@class='nav navbar-nav global-tab system-tabs ng-scope']/li[1]/a");
	public static By tbl_IncidentTable = By.xpath("//table[@id='inbox-list']");
	public static By div_SuccessMessage = By.xpath("//div[contains(@class,'snotify snotify-rightBottom')]/ng-snotify-toast");
	public static By div_ErrorMessage =  By.xpath("//body/div[@class='ui-notification ng-scope error']/div");
	
	public static By div_AllOpeningTab =  By.xpath("//li[@class='active']/a/div");
	public static By lbl_TabName = By.xpath("//li[@class='active']/a/div");
	public static By btn_CloseCurrentTab = By.xpath("//li[@class='active']/i[@class='icon icons8-delete-3']");
	
}
