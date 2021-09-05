package interfaces;

import org.openqa.selenium.By;

public class I_CP_Common {

	public static By frm_NotificationSuccess = By.xpath("");

	public static By btn_closeLastTab = By.xpath("//ul[@class='nav nav-tabs tabs-container']/li[last()]/i");
	public static By div_SuccessMessage = By.xpath("//div[contains(@class,'snotify snotify-rightBottom')]/ng-snotify-toast");
	public static By div_ErrorMessage = By.xpath("//div[@class='ui-notification ng-scope error']/div");
	public static By frm_DeleteConfirm = By.xpath("//app-confirm-delete-dialog");
	public static By lbl_DeleteConfirmMsg = By.xpath("//app-confirm-delete-dialog/div[2]/div");
	public static By btn_OkConfirm = By.xpath("//button[@class='confirm-dialog-btn' and contains(., 'Ok')]");
	
	public static By btn_OkDeleteConfirm = By.xpath("//app-confirm-delete-dialog/div[3]/button[1]");
	public static By btn_CancelConfirm = By.xpath("//button[@class='confirm-dialog-btn' and contains(., 'Cancel')]");
	public static By frm_ConvConfirm = By.xpath("//app-contravention-dialog");
	public static By lbl_ConvConfirm = By.xpath("//app-contravention-dialog/div[2]/div");
	public static By rowEle = By.xpath("//table/tbody/tr/td");
	
	public static By div_detailDropdown = By.xpath("//ng-select/ng-dropdown-panel/div/div[2]");
	
	public static By btn_showPageSize = By.xpath("//div[@class='pagination-container']/div/div/button");
	public static By div_PageSize = By.xpath("//div[@class='pagination-container']/div/div/div");
	public static By ul_pagination= By.xpath("//pagination/ul");
	public static By btn_nextpagination= By.xpath("//pagination/ul/li[@class='pagination-next page-item']");
	public static By btn_latpagination= By.xpath("//pagination/ul/li[last()");
	public static By lbl_activepagination= By.xpath("//pagination/ul/li[@class='pagination-page page-item active']/a");

}