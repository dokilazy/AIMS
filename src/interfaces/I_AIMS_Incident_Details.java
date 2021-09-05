package interfaces;

import org.openqa.selenium.By;

public class I_AIMS_Incident_Details {

	public static By div_Officer = By.xpath("//div/label[text() ='Reporting officer']/../div");
	
	public static By ddl_Officer = By.xpath("//ng-dropdown-panel/div/div[2]");
	public static By txt_IncidentType = By.xpath("//app-dropdown-incident-type/ng-select/div/div/div[2]");
	public static By div_IncidentType = By.xpath("//ng-dropdown-panel/div/div[2]");

	public static By btn_pickerDate = By.xpath("//span[@class='input-group-addon']");
	public static By txt_DateTime = By.xpath("//input[@formcontrolname='timeStampFirstObserved']");
	public static By txt_desc = By.xpath("//textarea");
	
	public static By txt_firstName = By.xpath("//input[@formcontrolname='firstName']");  
	public static By txt_lastName =  By.xpath("//input[@formcontrolname='lastName']");
	public static By txt_callAddress =  By.xpath("//input[@formcontrolname='address']");
	public static By txt_callCity =  By.xpath("//input[@formcontrolname='city']");
	public static By txt_callzipCode =  By.xpath("//input[@formcontrolname='zipCode']");
	public static By txt_callcoordinates =   By.xpath("//input[@formcontrolname='coordinates']");
	public static By txt_callPhone = By.xpath("//input[@formcontrolname='phoneNumber']"); 
	public static By btn_callLookup = By.xpath("//form/div[12]//button[contains(text(), 'Look up')]");
	public static By btn_inLoLookup = By.xpath("//form/div[17]//button[contains(text(), 'Look up')]");

	public static By txt_incidentAddr = By.xpath("//input[@formcontrolname='incidentAddress']");
	public static By txt_incidentCity = By.xpath("//input[@formcontrolname='incidentCity']']");
	public static By txt_incidentzipCode = By.xpath("//input[@formcontrolname='incidentZipCode']");
	public static By txt_incidentCoord = By.xpath("//input[@formcontrolname='incidentLocation']");
	
	public static By txt_customFields = By.xpath("//div[@class='outer-container box-scroll incident-main']/div/fieldset/../div//input");
	
	
	public static By btn_selMapIcon = By.xpath("//a[@class='leaflet-draw-draw-marker']");
	public static By incident_maps = By.xpath("//app-map/div");
	public static By btn_zoomOutmap = By.xpath("//a[@class='leaflet-control-zoom-out']");

	
	public static By btn_Save = By.xpath("//div[@class='global-bar']/div/div[text()='Save']");
	public static By btn_Complete = By.xpath("//div[@class='incident-editing-footer']/button[1]");
	public static By btn_Review = By.xpath("//div[@class='incident-editing-footer']/button[2]");
	
	public static By dlg_CloseTab = By.xpath("//div[@class='modal-content']");
	public static By btn_SaveChanges = By.xpath("//div[@class='modal-content']//button[text()='Save changes']");
	public static By btn_DiscardChanges = By.xpath("//div[@class='modal-content']//button[text()='Discard changes']");
	
	public static By pnl_CustomFields = By.xpath("//fieldset/legend[text() = 'Custom fields']");
	
	public static By tab_Evidence = By.xpath("//ul[@class='nav nav-tabs']/li[2]/a");
	
	public static By btn_DeclineWwarning = By.xpath("//div[@title='Decline all']");
	
	public static By popup_Messages = By.xpath("//div[@class='message ng-binding']");
	

	//---- mandatary fileds  ----
//	public static By div_OfficerValid = By.xpath("//form[@name='vmMasterData.basicInfoForm']/fieldset[1]/div");
//	public static By div_IncidentTypeValid = By.xpath("//form[@name='vmMasterData.basicInfoForm']/fieldset[1]/div[2]");
//	public static By div_descValid = By.xpath("//form[@name='vmMasterData.basicInfoForm']/fieldset[1]/div[5]");
	public static By div_incidentCoorError = By.xpath("//div[@class='error-message']");
	
	
	
	public static class Evidence {
		public static By btn_File = By.xpath("//app-evidence-list/div[@class='global-bar']/div[1]");
		public static By btn_Form = By.xpath("//app-evidence-list/div[@class='global-bar']/div[2]");
		public static By btn_Delete = By.xpath("//app-evidence-list/div[@class='global-bar']/div[4]");
		public static By frm_DeleteConfirm = By.xpath("//div[@class='modal-content']");
		public static By txt_deleteReason = By.xpath("//app-reason-delete-diaglog/div[2]//input");
		public static By btn_OkDelete = By.xpath("//div[@class='custom-modal-footer']/button[1]");
		public static By btn_confirmdelete = By.xpath("//button[@class='confirm-dialog-btn'][1]");
		
		
		public static By btn_View = By.xpath("//app-evidence-list/div[@class='global-bar']/div[5]");
		public static By btn_Export = By.xpath("//app-evidence-list/div[@class='global-bar']/div[6]");
		public static By btn_ExportLog = By.xpath("//app-evidence-list/div[@class='global-bar']/div[7]");
		public static By btn_SelectAll =   By.xpath("//app-evidence-list/div[@class='global-bar']/div[last()]/div[1]");    
		public static By btn_gribView =   By.xpath("//app-evidence-list/div[@class='global-bar']/div[last()]/div[last()]/label/div/label[1]");
		public static By btn_thumbnailView = By.xpath("//app-evidence-list/div[@class='global-bar']/div[last()]/div[last()]/label/div/label[2]");

		// ---- Evidence Metadata Input Form -----
		public static By frm_AddEvidence = By.xpath("app-adding-evidence-dialog/div[@class='modal-content]");
		public static By txt_Desc = By.xpath("//div[@id='modal-body']/div/div/div[2]/div[1]/div/input");
		public static By txt_ObservedOn = By.xpath("//div[@id='modal-body']/div/div/div[2]/div[2]/div/input");
		public static By dd_Criticality = By.xpath("//div[@id='modal-body']/div/div/div[2]/div[3]/div");
		public static By div_CriticalityDD = By.xpath("//app-dropdown-criticality/ng-select//ng-dropdown-panel/div/div[2]");
		public static By txt_Street = By.xpath("//div[@id='modal-body']/div/div/div[2]/div[4]/div/input");
		public static By txt_City = By.xpath("//div[@id='modal-body']/div/div/div[2]/div[5]/div/input");
		public static By txt_ZipCode = By.xpath("//div[@id='modal-body']/div/div/div[2]/div[6]/div/input");
		public static By txt_Coordinates = By.xpath("//div[@id='modal-body']/div/div/div[2]/div[7]/div/input");
		
		public static By btn_zoomOut = By.xpath("//app-adding-evidence-dialog//div[@class='leaflet-top leaflet-left']/div[@class='leaflet-control-zoom leaflet-bar leaflet-control']/a[2]");
		public static By btn_marker = By.xpath("//app-adding-evidence-dialog//div[@class='global-bar']");
		public static By lbl_mapInfo = By.xpath("//div[@class='leaflet-bottom leaflet-right']");

		public static By btn_lookup = By.xpath("//app-adding-evidence-dialog//div[@class='global-bar']/div");
		
		public static By dlg_Addresslist = By.xpath("//div[@class='modal-dialog ']/div[@class='modal-content']/div[contains(@class, 'selectAddress')][1]");
		public static By ul_Addresslist = By.xpath("//div[@class='modal-dialog ']/div[@class='modal-content']/div[2]/div/div/ul[@class='list-group']");
		public static By btn_AddressSubmit = By.xpath("//button[@ng-click='vmSelectAddress.submit();']");
		
		
		public static By btn_Save = By.xpath("//div[@class='custom-modal-footer']/button[@class='confirm-dialog-btn'][1]");
		public static By btn_Cancel = By.xpath("//div[@class='custom-modal-footer']/button[@class='confirm-dialog-btn'][2]");

		public static By btn_Exportdialog = By.xpath("//modal-container/div/div/div[@class='custom-modal-footer']/button[1]");
		public static By frm_EvidenceError = By.xpath("//div[@class='form-group has-error']/div/div[@class='error-info']/span");
		
		
		
		//Playback 
		public static By frm_Video = By.xpath( "//div[@id='dlm-video-container']/div/video");
		public static By btn_PlayPause = By.cssSelector("div.playback-btn.pause-play-circle") ;  
		public static By btn_Capture =  By.xpath("//div[@id='dlm-video-container']/div/div/div[2]/button[1]");
		public static By btn_FullScreen=  By.xpath("//div[@id='dlm-video-container']/div/div/div[2]/button[2]");
		public static By div_ImageCapture = By.id("image-capture");
		
	}

	class Customs_Details {

	}

}
