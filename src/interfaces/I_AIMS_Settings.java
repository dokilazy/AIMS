package interfaces;
import org.openqa.selenium.By;
public class I_AIMS_Settings {
	
	public static By txt_commonSearch = By.xpath("//app-filter/form/input");
	public static By tab_IncidentTypes = By.xpath("//ul/li/a[contains(.,'Incident types')]");
	public static By tab_Categories = By.xpath("//li/a[@ui-sref='root.settings.categories']");
	public static By tab_incidentTypeAssignment = By.xpath("//ul/li/a[contains(.,'Incident Type')]");
	public static By tab_customFields = By.xpath("//ul/li/a[contains(.,'Custom fields')]");
	
	
	//--- Incident Type page -----
	public static By btn_addNewType = By.xpath("//div[@class='global-bar']/div[1]");
	public static By txt_InputName = By.xpath("//div/label[text() = 'Name']/../div/input");
	public static By txt_incidentCode = By.xpath("//div/label[text() = 'Code']/../div/input");
	public static By btn_Save = By.xpath("//div[@class='custom-modal-footer']/button[1]");
	public static By btn_Cancel = By.xpath("//div[@class='custom-modal-footer']/button[2]");
	
	
	public static By btn_SaveEdit = By.xpath("//div[@class='global-bar']/div");
	public static By btn_EditIncidentType =  By.xpath("//div[@class='table-cell btn-detail-action']/button");
	
	
	//---Incident Catalog page
	public static By treenode_UncategorizedList = By.xpath("//tree-root[@id='uncategorizedTree']//tree-node//div/tree-node//div//tree-node-content/span");
	public static By node_EndItemOfUncategory = By.xpath("//tree-root[@id='uncategorizedTree']//tree-node//div/tree-node[last()]");
	public static By btn_addNewCategory = By.xpath("//button[@ng-click='vmIncidentTypeAssignment.addNewCategory();']");
	public static By btn_SaveCategory = By.xpath("//button[@ng-click='vmIncidentTypeAssignment.saveCategory()']");
	
	
	
	
	
}
