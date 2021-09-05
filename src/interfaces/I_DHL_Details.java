package interfaces;

import org.openqa.selenium.By;

public class I_DHL_Details {
	
//	public static By txt_Assignee = By.xpath("//div/label[text() ='Assignee']/../div//input");
	public static By txt_Assignee = By.xpath("//div/label[contains(text(),'Assignee')]/../div/ng-select//div[@class='ng-input']/input");
	public static By txt_Coworker = By.xpath("//div/label[contains(text(),'Co-worker')]/../div/ng-select//div[@class='ng-input']/input");
	public static By txt_CoworkerArrow = By.xpath("//div/label[contains(text(),'Co-worker')]/../div/ng-select/div/span[last()]");
	
	public static By ddl_Assignee = By.xpath("//ng-dropdown-panel/div/div[2]");
	public static By ddl_Coworker = By.xpath("//ng-dropdown-panel/div/div[2]");
	public static By txt_Status = By.xpath("//app-dropdown-state/ng-select//input");
	public static By txt_Status2 = By.xpath("//app-container/div/div[2]/div/div/div[3]/div/form/div[2]/label/../div//input");
	
	public static By txt_IncidentType = By.xpath("//app-dropdown-incident-type/ng-select/div/div/div[2]/input");
	public static By div_IncidentType = By.xpath("//ng-dropdown-panel/div/div[2]");
	public static By txt_workflow = By.xpath("//div/label[contains(text(),'Workflow of incident')]/../div//input");
	public static By btn_pickerDate = By.xpath("//span[@class='input-group-addon']");
	public static By txt_DateTime = By.xpath("//input[@formcontrolname='timeStampFirstObserved']");
	public static By btn_inLoLookup = By.xpath("//form/div[17]//button[contains(text(), 'Look up')]");
	public static By txt_incidentAddr = By.xpath("//input[@formcontrolname='incidentAddress']");
	public static By txt_incidentCity = By.xpath("//input[@formcontrolname='incidentCity']");
	public static By txt_incidentzipCode = By.xpath("//input[@formcontrolname='incidentZipCode']");
	public static By txt_incidentCoord = By.xpath("//input[@formcontrolname='incidentLocation']");
	public static By txt_customFields = By.xpath("//app-dynamic-form/div/form/div//input");
	
	
	public static By btn_selMapIcon = By.xpath("//a[@class='leaflet-draw-draw-marker']");
	public static By incident_maps = By.xpath("//app-map/div");
	public static By btn_zoomOutmap = By.xpath("//a[@class='leaflet-control-zoom-out']");
	
	public static By btn_Save = By.xpath("//div[@class='global-bar']/div/div[text()='Save']");
	public static By dlg_CloseTab = By.xpath("//div[@class='modal-content']");
	public static By btn_SaveChanges = By.xpath("//div[@class='modal-content']//button[text()='Save changes']");
	public static By btn_DiscardChanges = By.xpath("//div[@class='modal-content']//button[text()='Discard changes']");
	public static By pnl_CustomFields = By.xpath("//fieldset/legend[contains(text(),'Custom fields')]");
	
	public static By tab_Area = By.xpath("//ul[@class='nav nav-tabs']/li/a//span[contains(text(),'Area')]");
	public static By tab_Evidence = By.xpath("//ul[@class='nav nav-tabs']/li/a//span[contains(text(),'Evidence')]");
	public static By tab_Comment = By.xpath("//ul[@class='nav nav-tabs']/li/a//span[contains(text(),'Comment')]");

	public static By popup_Messages = By.xpath("//div[@class='message ng-binding']");
	public static By div_incidentCoorError = By.xpath("//div[@class='error-message']");
	
	public static By popup_confirmChangeTypeMsg = By.xpath("//div[@id='modal-body']/div");
	public static By btn_confirmChangeType = By.xpath("//div[@id='modal-body']/../div[3]/button[1]");
	
	
	
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

}
