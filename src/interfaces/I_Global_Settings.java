package interfaces;

import org.openqa.selenium.By;

public class I_Global_Settings {

	public static By Tab_ParkingSite = By
			.xpath("//ul[@class='nav sidebar-nav']/li/a/span[contains(text(),'Parking Site')]");
	public static By Tab_Gateway = By.xpath("//ul[@class='nav sidebar-nav']/li/a/span[contains(text(),'Gateway')]");
	public static By Tab_Camera = By.xpath("//ul[@class='nav sidebar-nav']/li/a/span[contains(text(),'Camera')]");
	public static By Tab_DriverProfile = By
			.xpath("//ul[@class='nav sidebar-nav']/li/a/span[contains(text(),'Driver Profile')]");
	public static By Tab_TimeProfile = By
			.xpath("//ul[@class='nav sidebar-nav']/li/a/span[contains(text(),'Time Profile')]");
	public static By Tab_NumnberPlates = By
			.xpath("//ul[@class='nav sidebar-nav']/li/a/span[contains(text(),'Number Plate')]");
	public static By Tab_Whitelist = By.xpath("//ul[@class='nav sidebar-nav']/li/a/span[contains(text(),'Whitelist')]");
	public static By Tab_Blacklist = By.xpath("//ul[@class='nav sidebar-nav']/li/a/span[contains(text(),'Blacklist')]");
	public static By Tab_Transaction = By
			.xpath("//ul[@class='nav sidebar-nav']/li/a/span[contains(text(),'Transaction')]");

	public static By btn_Add = By.xpath("//button[@class='btn btn-dlm-tb navbar-btn ng-binding']");
	public static By txt_Name = By.xpath("//input[@name='uname']");
	public static By txt_Alias = By.xpath("//input[@name='alias']");
	//public static By btn_Save = By.xpath("//button[@class='btn btn-dlm-tb floatRight ng-binding' and @type='submit']");
	public static By btn_Save = By.xpath("//button[contains(@class, 'btn btn-dlm-tb floatRight ng-binding') and @type='submit']");
	public static By btn_Cancel = By
			.xpath("//button[@class='btn btn-dlm-tb floatRight ng-binding' and @type='button']");

	public static By btn_Edit = By.xpath("//div[@class='container-fluid']/button[2]");
	public static By btn_SaveEdit = By.xpath("//button[@class='btn btn-dlm-tb navbar-btn']");

	public static By btn_CloseEdit = By.xpath("//i[@class='close icons8-delete action-control-delete-icon']");

	public static By dl_AddNew = By.xpath("//md-dialog[@class='_md md-transition-in']");

	public static By pl_Rightpanel = By.xpath("//div[@class='row h100']/div[2]");

	public static By div_Rightpanel = By.xpath("//div[@class='col-md-4 sidebar']/div/div");
	// public static By btn_Details = By.xpath("//div[contains(@class,'list-wrapper
	// h100 full-table')]/button");
	public static By btn_Details = By.xpath("//div[@class='row h100']/div/button[1]");
	public static By frm_Success = By.xpath("//div[@class='alert alert-dismissable alert-success')]");

	
	
	
	public static By btn_pagination_Next = By.xpath("//div[@class='dataTables_paginate paging_simple_numbers']/ul/li/a[@class='next']");
	public static By btn_pagination_Previous = By.xpath("//div[@class='dataTables_paginate paging_simple_numbers']/ul/li/a[@class='previous']");
	public static By ul_pagination =  By.xpath("//ul[@class='pagination']");
	
	
	public static class ParkingSite {

		public static By txt_capacity = By.xpath("//input[@name='capacity']");
		public static By btn_Marker = By.xpath("//a[@class='leaflet-draw-draw-marker']");
		public static By div_mapArea = By.xpath("//div[@class='leaflet-tile-container leaflet-zoom-animated']/img[1]");
		// public static By tbl_psTable = By.id("DataTables_Table_1");

		public static By tbl_psTable = By.xpath("//table[contains(@id,'DataTables_Table')]");
		public static By tr_LastRow = By.xpath("//table[contains(@id,'DataTables_Table')]/tbody/tr[last()]");
		public static By tr_FirstRow = By.xpath("//table[contains(@id,'DataTables_Table')]/tbody/tr[1]");
	}

	public static class GateWay {
		public static By txt_Street = By.xpath("//input[@name='street']");
		public static By Sel_ParkingSiteDD = By.xpath("//select[@name='parkingSite']");
	}

	public static class Camera {
		public static By txt_IpAddress = By.xpath("//input[@name='ipAddress']");
		public static By Sel_GatewayDD = By.xpath("//select[@name='gateway']");

	}
	
	

	public static class DriverProfile {

		public static By txt_LicenseType = By.xpath("//input[@name='licenseType']");
		public static By txt_LicenseId = By.xpath("//input[@name='licenseId']");
	}

	public static class NumberPlates {
		public static By btn_AddVisitor = By
				.xpath("//nav[@class='navbar navbar-local navbar-local-fixed']/div/button[1]");
		public static By btn_AddRegular = By
				.xpath("//nav[@class='navbar navbar-local navbar-local-fixed']/div/button[2]");
		public static By rd_Visitor = By
				.xpath("//nav[@class='navbar navbar-local navbar-local-fixed']/div/label[1]/input");
		public static By rd_Regular = By
				.xpath("//nav[@class='navbar navbar-local navbar-local-fixed']/div/label[2]/input");
		public static By rd_ALL = By.xpath("//nav[@class='navbar navbar-local navbar-local-fixed']/div/label[3]/input");

		// Visitor Form
		public static By txt_numberplate = By.xpath("//input[@name='numberPlate']");
		public static By Sel_ParkingSiteDD = By.xpath("//select[@name='parkingSite']");
		public static By Sel_TimeProfileDD = By.xpath("//select[@name='timeProfile']");
		public static By txt_Period = By.xpath("//input[@name='period']");
		public static By txt_DriverName = By.xpath("//input[@name='driverName']");
		public static By txt_VehicleName = By.xpath("//input[@name='vehicleName']");
		public static By txt_VehicleType = By.xpath("//input[@name='vehicleType']");
		public static By txt_Desc = By.xpath("//textarea[@ng-model='numberPlate.vehicleDescription']");
		public static By sel_today = By.xpath("//td[contains(@class,'active start-date active end-date available')]");
		public static By sel_endOfCanlendar = By
				.xpath("//div[@class='calendar right']/div[2]/table/tbody/tr[last()]/td[last()]");
		public static By btn_Apply = By.xpath("//button[@class='applyBtn btn btn-sm btn-success']");

		// Regular Form
		public static By Sel_WhitelistDD = By.xpath("//select[@name='whitelistId']");
		public static By Sel_DriverProfileDD = By.xpath("//select[@name='driverProfile']");
		public static By rd_ExistingDP = By.xpath("//div[@class='col-md-9']/label[2]/input[1]");
		public static By rd_NewDP = By.xpath("//div[@class='col-md-9']/label[1]/input[1]");

	}

	public static class TimeProfile {
		public static By btn_Clear = By.xpath("//div[@class='dayparts-min ng-scope']/button");
	}

	public static class Whitelist {
		public static By txt_groupName = By.xpath("//div[@class='whitelist-container']//input[@name='uname']");
		public static By btn_AssignAccess = By.xpath("//button[@ng-click='whiteListVm.onAssignWhitelist($event)']");
		public static By Sel_ParkingSiteDD = By.xpath("//select[@id='parkingSite']");
		public static By Sel_TimeProfileDD = By.xpath("//select[@name='timeProfile']");
	}

	public static class Blacklist {
		public static By txt_NumplateName = By.xpath("//input[@id='numberPlate_value']");
		public static By txt_DriverName = By.xpath("//input[@name='driverName']");
		public static By txt_LicenseType = By.xpath("//input[@name='licenseType']");
		public static By txt_LicenseId = By.xpath("//input[@name='licenseId']");
		public static By txt_VehicleName = By.xpath("//input[@name='vehicleName']");
		public static By txt_VehicleType = By.xpath("//input[@name='vehicleType']");
		public static By txt_period = By.xpath("//input[@name='period']");
		public static By div_datepicker = By
				.xpath("//div[@class='daterangepicker dropdown-menu ltr opensright show-calendar']");

		public static By pr_1day = By.xpath("//div[contains(@class,'daterangepicker')]/div/ul/li[1]");
		public static By pr_1week = By.xpath("//div[contains(@class,'daterangepicker')]/div/ul/li[2]");
		public static By pr_1Month = By.xpath("//div[contains(@class,'daterangepicker')]/div/ul/li[3]");
		public static By pr_1Year = By.xpath("//div[contains(@class,'daterangepicker')]/div/ul/li[4]");
		public static By pr_custom = By.xpath("//div[contains(@class,'daterangepicker')]/div/ul/li[last()]");

		public static By btn_Save = By.xpath("//md-dialog/form/md-dialog-actions/button[@type='submit']");

		public static By btn_Delete = By.xpath("//div[@class='table-cell btn-detail-action']/button");
		public static By dlg_Confirm = By.xpath("//md-dialog[@aria-label='Confirm Dialog']");
		public static By btn_YesConfirm = By
				.xpath("//md-dialog[@aria-label='Confirm Dialog']/md-dialog-actions/button[2]");

	}

	public static class Transactions {

	}

}
