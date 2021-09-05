package interfaces;

import org.openqa.selenium.By;

public class I_CP_Contraventions {
	
	
	// ------------- Search ------------
	
	public static By tab_Home = By.xpath("//app-tabs/ul/li[1]/a");
	public static By txt_SearchID  = By.xpath("//input[@name='contraventionCode']");
	public static By txt_SearchVRM  = By.xpath("//input[@name='vehicleRegistrationMark']");
	public static By txt_SearchUser  = By.xpath("//ng-select[@formcontrolname='user']//input");
	
	public static By btn_dropdownUser = By.xpath("//ng-select[@formcontrolname='user']/div/span[last()]");
	public static By btn_dropdownOffenceCode = By.xpath("//ng-select[@formcontrolname='offenceTypeId']/div/span");
	public static By btn_dropdownCamera = By.xpath("//app-dropdown-camera/ng-select");
	public static By btn_Search = By.xpath("//div[@class='global-bar']/div[1]/button[1]");
	public static By btn_ViewInToolbar = By.xpath("//div[@class='global-bar']/div[3]/button[1]");
	public static By btn_ViewInDetails = By.xpath("//div[@class='btn-detail-action']/button");
	
	public static By btn_Details = By.xpath("//div[@class='global-bar']/div[4]/div");
//	public static By  = By.xpath("");

	
	// ------------- Details  ------------
	
	public static By txt_IDinDetails= By.xpath("//form/div[1]/div/input");
	public static By txt_User = By.xpath("//form/div[2]/div/input");
	public static By txt_Status = By.xpath("//form/div[3]/div/input");
	public static By txt_fileName = By.xpath("//form/div[4]/div/input");
	public static By txt_time = By.xpath("//form/div/label[contains(.,'Observed Time')]/../div/span");
	public static By txt_count = By.xpath("//form/div[6]/div/input");
	public static By txt_videoRef = By.xpath("//form/div[7]/div/input");
	
	public static By txt_desc = By.xpath("//form/div[8]/div/textarea");
	public static By txt_Camera = By.xpath("//label[contains(.,'Camera')]/../div/input");
	public static By txt_Street= By.xpath("//ng-select[@formcontrolname='contraventionLocationId']//input");
	public static By txt_Coordinates= By.xpath("//label[contains(.,'Coordinates')]/../div/input");
	public static By txt_Manufacturer = By.xpath("//ng-select[@formcontrolname='vehicleManufacturerId']//input");
	public static By txt_Model = By.xpath("//ng-select[@formcontrolname='vehicleModelId']//input");
	public static By txt_Colour = By.xpath("//ng-select[@formcontrolname='vehicleColorId']//input");
	public static By txt_OffenceCode = By.xpath("//ng-select[@formcontrolname='offenceTypeId']/div//input");
	public static By txt_VehicleNo = By.xpath("//ng-select[@formcontrolname='vrmId']/div//input");
	public static By span_VRM = By.xpath("//ng-select[@formcontrolname='vrmId']/div/div/div[2]/span[2]");
	public static By span_addItem =    By.xpath("//ng-select[@formcontrolname='vrmId']/ng-dropdown-panel/div/div[2]/div/span[contains(.,'Add item')]");
						
							
	public static By txt_VoidReason = By.xpath("//label[contains(.,'Void Reason')]/../div/input");
	public static By txt_VoidReasonText = By.xpath("//label[contains(.,'Void Reason')]/../div[2]/div/textarea");
	
	// ----------- Checkbox ----
	public static By chk_Unreadable = By.xpath("//input[@formcontrolname='unreadableFlag']/../span[1]");
	public static By chk_Foreign = By.xpath("//input[@formcontrolname='foreignVehicleFlag']/../span[1]");
	public static By chk_nonDVLA = By.xpath("//input[@formcontrolname='nonDvlaFlag']/../span[1]");
	public static By chk_Diplomatic = By.xpath("//input[@formcontrolname='diplomaticFlag']/../span[1]");
	
	//-----------  Buttons ---------
	public static By btn_Save  = By.xpath("//div[@class='contravention-detail']/div[2]/div[1]");
	public static By btn_Download  = By.xpath("//div[@class='contravention-detail']/div[2]/div[contains(.,'Download')]");
	public static By btn_Next  = By.xpath("//div[@class='contravention-detail']/div[2]/div[3]");
	public static By btn_Complete  = By.xpath("//div[@class='contravention-detail']/div[4]/button[contains(.,'Complete')]");
	public static By btn_Void  = By.xpath("//div[@class='contravention-detail']/div[4]/button[contains(.,'Void')]");
	public static By btn_Unvoid  = By.xpath("//div[@class='contravention-detail']/div[4]/button[contains(.,'Unvoid')]");
	public static By btn_Review  = By.xpath("//div[@class='contravention-detail']/div[4]/button[contains(.,'Review')]");
	
	public static By tab_Area = By.xpath("//tabset/ul/li[2]");
	public static By tab_Video = By.xpath("//tabset/ul/li[1]");
	public static By btn_snapshot = By.xpath("//div[@id='dlm-video-container']/div/div[1]/div/button[1]");
	
	//------- Void reason popup ------
	public static By sel_voidreason  = By.xpath("//form/div[2]/select");
	public static By txt_descVR = By.xpath("//form/div[2]//textarea");
	public static By btn_saveVR  = By.xpath("//form/div[3]/button[1]");
	public static By btn_CancelVR  = By.xpath("//form/div[3]/button[2]");
	

	
	
	// ---------------- Video player ---------
	
	public static By frm_Video = By.xpath( "//div[@id='dlm-video-container']/div/video");
	public static By btn_PlayPause = By.cssSelector("div.playback-btn.pause-play-circle") ;  
	public static By btn_Capture =  By.xpath("//div[@id='dlm-video-container']/div/div/div[2]/button[2]");
	public static By div_ImageCapture = By.id("image-capture");
	
	
	
	
	
	
}
