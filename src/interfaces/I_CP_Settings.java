package interfaces;

import org.openqa.selenium.By;

public class I_CP_Settings {

	public static By tab_Settings = By.xpath("//app-tabs/ul/li[2]");
	public static By btn_New = By.xpath("//div[contains(@class,'global-bar')]/div[1]/div[contains(.,'New')]");
	public static By txt_commonSearch = By.xpath("//app-filter/form/input");
	public static By btn_SaveEdit = By.xpath("//div[@class='global-bar']/div/div");
	
	public static class VRM {
		public static By tab_VRM = By
				.xpath("//ul[@class='nav nav-tabs sub-tabs-container']/li/a/span[contains(.,'VRM Exceptions')]");
		public static By btn_New = By.xpath("");
		public static By btn_Delete = By.xpath("//div[@class='global-bar ng-untouched ng-pristine ng-valid']//div[contains(.,'Delete')]");
		public static By btn_Edit = By.xpath("//div[@id='sidebar-right']/div/div/button");
		public static By btn_Import = By.xpath("//label[@class='btn btn-upload-json']");

		public static By txt_VehicleNumber = By.id("vrmNumber");
		public static By txt_EditVehicleNumber = By.xpath("//input[@formcontrolname='vrmNumber']");
		
		public static By select_CameraName = By.xpath("//app-dropdown-camera-multi/ng-select");
		public static By txt_Camera= By.xpath("//input[@formcontrolname= 'camera']");
		public static By txt_ExceptionDateFrom = By.xpath("//input[@formcontrolname= 'dateTimeRange' or @formcontrolname= 'exceptionDate'][@placeholder='From']");
		public static By txt_ExceptionDateTo = By.xpath("//input[@formcontrolname= 'dateTimeRange' or @formcontrolname= 'exceptionDate'][@placeholder='To']");
		public static By btn_Save = By.xpath("//app-vrm-create/form/div/button[@type='submit']");
		public static By btn_cancel = By.xpath("//app-vrm-create/form/div/button[@type='button']");
	
	}

	public static class VoidReasons {
		public static By tab_VoidReasons = By
				.xpath("//ul[@class='nav nav-tabs sub-tabs-container']/li/a/span[contains(.,'Void Reasons')]");
		public static By btn_Delete = By.xpath("//div[@class='global-bar']/div[3]/div[contains(.,'Delete')]");
		public static By btn_Edit = By.xpath("//div[@id='sidebar-right']/div/div/button");
		public static By txt_Name = By.xpath("//input[@formcontrolname='name']");
		public static By chk_RequiredDesc = By.xpath("//input[@formcontrolname='type']/../span");
		public static By btn_Save = By.xpath("//form/div/button[@type='submit']");	
		public static By btn_Cancel = By.xpath("//form/div/button[@type='type']");
		
	}

	public static class Location {
		public static By tab_Location = By.xpath(
				"//ul[@class='nav nav-tabs sub-tabs-container']/li/a/span[contains(.,'Contravention Locations')]");
		public static By btn_Delete = By.xpath("//div[@class='global-bar']/div[3]/div[contains(.,'Delete')]");
		public static By btn_Edit = By.xpath("//div[@id='sidebar-right']/div/div/div/button");
		public static By dd_Camera = By.xpath("//app-dropdown-camera/ng-select");
		public static By div_Cameralist = By.xpath("//app-dropdown-camera/ng-select//ng-dropdown-panel/div/div[2]");
		
		public static By txt_streetName = By.xpath("//input[@formcontrolname='icesStreetName']");
		public static By txt_CamId = By.xpath("//input[@formcontrolname='icesCameraId']");
		public static By txt_streetId = By.xpath("//input[@formcontrolname='icesStreetId']");
		public static By btn_Save = By.xpath("//form/div/button[@type='submit']");

	}

	public static class VehicleInfo {

		public static By tab_Vehicle = By
				.xpath("//ul[@class='nav nav-tabs sub-tabs-container']/li/a/span[contains(.,'Vehicle Information')]");
		public static By btn_Edit = By.xpath("//div[@class='global-bar']/div[3]/div[contains(.,'Edit')]");
		public static By btn_Delete = By.xpath("//div[@class='global-bar']/div[5]/div[contains(.,'Delete')]");
		public static By txt_Name_manu = By.xpath("//input[@id='new-manufacturer-input']");
		public static By btn_Save_manu = By.xpath("//input[@id='new-manufacturer-input']/../button[1]");
		public static By btn_Cancel_manu = By.xpath("//input[@id='new-manufacturer-input']/../button[2]");
		
		public static By txt_Name_model = By.xpath("//input[@id='new-model-input']");
		public static By btn_Save_model = By.xpath("//select[@formcontrolname='manufacturerId']/../button[1]");
		public static By btn_Cancel_model = By.xpath("//select[@formcontrolname='manufacturerId']/../button[2]");
		public static By sel_manu_dropdown = By.xpath("//select[@formcontrolname='manufacturerId']");
		
		public static By txt_Name_color = By.xpath("//input[@id='new-colour-input']");
		public static By btn_Save_colour = By.xpath("//input[@id='new-colour-input']/../button[1]");
		public static By btn_Cancel_colour = By.xpath("//input[@id='new-colour-input']/../button[2]");
		
		public static By tab_Manu = By.xpath("//button[contains(.,'Vehicle Manufacturer')]");
		public static By tab_Model = By.xpath("//button[contains(.,'Vehicle Model')]");
		public static By tab_Colour = By.xpath("//button[contains(.,'Vehicle Colour')]");
		public static By txt_SearchVehicleInfo = By.xpath("//div[@class='panel-collapse collapse in show']//app-filter/form/input");
		
		
	}

	public static class OffenceTypes {

		public static By tab_OffenceTypes = By
				.xpath("//ul[@class='nav nav-tabs sub-tabs-container']/li/a/span[contains(.,'Offence Types')]");

		public static By btn_Delete = By.xpath("");
		public static By btn_Edit = By.xpath("");
		public static By txt_Name = By.xpath("//input[@formcontrolname='name']");
		public static By txt_Code = By.xpath("//input[@formcontrolname='code']");
		public static By btn_Save = By.xpath("//td/button[@type='submit']");
		public static By btn_Cancel = By.xpath("//td/button[@type='button' and @title='Cancel']");

	}

	public static class OffenceTypeAssign {

		public static By tab_OffenceTypeAssign = By.xpath(
				"//ul[@class='nav nav-tabs sub-tabs-container']/li/a/span[contains(.,'OffenceType Assignment')]");
		public static By btn_extendUncategorized = By.xpath("//*[@id='uncategorizedTree']//tree-node-expander");
	
		public static By txt_Name = By.xpath("//tree-node-content/form/input[@formcontrolname='name']");
		public static By btn_Save = By.xpath("//input[@formcontrolname='name']/../button[1]");
		public static By btn_SaveEdit = By.xpath("//input[@formcontrolname='name']/../span/button[1]");
		
	}

	public static class ICESExport {

		public static By tab_ICESExport = By
				.xpath("//ul[@class='nav nav-tabs sub-tabs-container']/li/a/span[contains(.,'ICES Export')]");
		public static By btn_Save = By.xpath("//app-ices-ftp-setting//button");
		 public static By txt_Time = By.xpath("//div[@class='timePicker']/input");
		 public static By txt_Timehour = By.xpath("//owl-date-time-timer/owl-date-time-timer-box[1]/label/input");
		 public static By txt_Timeminute = By.xpath("//owl-date-time-timer/owl-date-time-timer-box[1]/label/input");
		// public static By = By.xpath("");
		// public static By = By.xpath("");
	}

}
