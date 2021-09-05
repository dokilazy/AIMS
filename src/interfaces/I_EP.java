package interfaces;

import org.openqa.selenium.By;

public class I_EP {

	public static By btn_SelectInc = By.xpath("//button[@ng-click='incidentDropdownOpen=!incidentDropdownOpen']");
	public static By div_IncidentDropdown = By.id("incidentsDropdown");
	public static By txt_Search = By.xpath("//input[@ng-model='incidentsListSearch']");

	public static By div_Incidents = By.xpath("//div[@id='incidentsDropdown']/div[3]/div/div[2]/div[1]/div[1]/div/div");

	public static By div_Evidences = By.xpath("//div[@class='f-stretch body list']/div/div/div/div/div/div");

	public static By btn_AutoExport = By.xpath("//button[@ng-click='autoExportData();']");
	public static By btn_ExportData = By.xpath("//button[@ng-click='xportPanelOpen=true;']");
	public static By btn_SendData = By.xpath("//button[@ng-click='startImmediateExport();']");
	public static By chx_keepInform = By.xpath("//input[@ng-model='heureka']");
	public static By chx_Archive = By.xpath("//input[@ng-model='createArchive']");
	public static By btn_Project = By.xpath("//button[@ng-click='projectsPanelOpen=true;']");
	public static By div_Project = By.xpath("//div[@class='abs list-container flex rows']");

	public static By btn_ = By.xpath("//button[@ng-click=]");
	
	public static By txt_ProjectName = By.xpath("//input[@ng-model='activeProject.project.title']");
	public static By txt_ProjectDesc = By.xpath("//textarea[@ng-model='activeProject.project.comments']");
	
	public static By btn_currentProTab = By.xpath("//div[@id='projectsPanel']/div/div[@class='body f-stretch flex cols']/div/div[1]");
	public static By btn_NewProTab = By.xpath("//div[@id='projectsPanel']/div/div[@class='body f-stretch flex cols']/div/div[2]");
	public static By btn_CloneProTab = By.xpath("//div[@id='projectsPanel']/div/div[@class='body f-stretch flex cols']/div/div[3]");
	public static By btn_ProListTab = By.xpath("//div[@id='projectsPanel']/div/div[@class='body f-stretch flex cols']/div/div[4]");
	
	public static By btn_UpdateProject = By.xpath("//button[@ng-click='updateCurrentProject();']");
	
	
	public static By txt_newProName = By.xpath("//input[@ng-model='newProject.title']");
	public static By txt_commentNewPro =  By.xpath("//textarea[@ng-model='newProject.comments']");
	public static By btn_CreateNew = By.xpath("//button[@ng-click='createNewEmptyProject();']");
	
	
	//--Auto Eport dialog --
	public static By div_Exportfeedback  = By.id("exportFeedback");
	public static By lbl_ExportComplete  = By.xpath("//div[@ng-show='complete']");
	
	
}
