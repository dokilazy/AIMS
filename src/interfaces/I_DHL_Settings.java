package interfaces;

import org.openqa.selenium.By;

public class I_DHL_Settings {

	public static By tab_System = By.xpath("//ul/li/a[contains(.,'System')]");
	public static By tab_ValidationRule = By.xpath("//ul/li/a[contains(.,'Validation')]");
	public static By tab_FormTemplate = By.xpath("//ul/li/a[contains(.,'Form Template')]");
	public static By tab_customFields = By.xpath("//ul/li/a[contains(.,'Custom fields')]");
	public static By tab_IncidentTypes = By.xpath("//ul/li/a[contains(.,'Incident types')]");
	public static By tab_IncidentTypeAssignment = By.xpath("//ul/li/a[contains(.,'Incident Type Assignment')]");
	public static By tab_States = By.xpath("//ul/li/a[contains(.,'States')]");
	public static By tab_Roles = By.xpath("//ul/li/a[contains(.,'Roles')]");
	public static By tab_RoleAssignment = By.xpath("//ul/li/a[contains(.,'Role Assignment')]");
	public static By tab_Workflow = By.xpath("//ul/li/a[contains(.,'Workflow')]");

	public static By txt_commonSearch = By.xpath("//app-filter/form/input");
	public static By btn_Save = By.xpath("//div[@class='global-bar']/div[contains(.,'Save')]");
	public static By btn_SaveInline = By.xpath("//td//button[@type='submit' and @title='Save']");
	public static By btn_CancelInline = By.xpath("//td//button[@type='button' and @title='Cancel']");
	public static By btn_AddNew = By.xpath("//div[@class='global-bar']/div[contains(.,'New')]");
	public static By btn_Edit = By.xpath("//div[@class='global-bar']/div[contains(.,'Edit')]");

	public static By btn_Delete = By.xpath("//div[@class='global-bar']/div[contains(.,'Delete')]");

	public static By btn_SaveAtForm = By.xpath("//button[@class='confirm-dialog-btn' and @type='submit']");
	public static By btn_CancelAtForm = By.xpath("//div[@class='custom-modal-footer']/button[2]");
	public static By btn_SaveEdit = By.xpath("//div[@class='global-bar']/div");

	// ----- System page ----
	public static By txt_deletedMarkedIncident = By.xpath("//fieldset[contains(.,'Incident delete')]/../div[3]//input");
	public static By rd_automatic = By.xpath("//fieldset[contains(.,'Incident Code Generator')]/../div/div/input[1]");
	public static By rd_manual = By.xpath("//fieldset[contains(.,'Incident Code Generator')]/../div/div/input[2]");
	public static By chk_overwrite = By
			.xpath("//fieldset[contains(.,'Incident Code Generator')]/../div/div/div/input");

	// --------------- validation ---
	public static By txt_ruleName = By.xpath("//tbody/tr[1]/td[1]/input");
	public static By dd_dataType = By.xpath("//tbody/tr[1]/td[2]/ng-select/div");
	public static By div_dataTypedropdown = By.xpath("//tbody/tr/td[2]/ng-select/ng-dropdown-panel/div/div[2]");
	public static By txt_regex = By.xpath("//tbody/tr[1]/td[3]/input");
	public static By txt_ruleDesc = By.xpath("//tbody/tr[1]/td[4]/input");

	public static By a = By.xpath("");

	
	// --- Form ---
	public static By btn_CloseTemplateForm = By.xpath("//app-form-template-create/div[1]/button[1]");
	public static By btn_OkTemplateForm = By.xpath("//app-form-template-create/div[3]/button[1]");
	public static By btn_CancelTemplateForm = By.xpath("//app-form-template-create/div[3]/button[2]/i");
	
	
	// ----------------- Custom fileds ----
	
	public static By txt_fieldName = By.xpath("//form/div[@id='modal-body']//form/div[1]//input");
	public static By dd_dataTypeOfField = By.xpath("//form/div[@id='modal-body']//form/div[2]//input");
	public static By txt_noOfLine = By
			.xpath("//form/div[@id='modal-body']//form/div/label[contains(.,'Number of Line')]/..//input");
	public static By txt_EnumKey = By
			.xpath("//form/div[@id='modal-body']//form/div/label[contains(.,'Options')]/..//tr/td[1]/input");
	public static By txt_EnumValue = By
			.xpath("//form/div[@id='modal-body']//form/div/label[contains(.,'Options')]/..//tr/td[2]/input");
	public static By dd_Enumtype = By
			.xpath("//form/div[@id='modal-body']//form/div/label[contains(.,'Options')]/..//tr/td[3]/input");
	public static By txt_UKLabel = By
			.xpath("//form/div[@id='modal-body']//form/div/label[contains(.,'United Kingdom')]/..//input");
	public static By txt_USLabel = By
			.xpath("//form/div[@id='modal-body']//form/div/label[contains(.,'USA')]/..//input");
	public static By txt_DeuLabel = By
			.xpath("//form/div[@id='modal-body']//form/div/label[contains(.,'Deutsch')]/..//input");
	public static By dd_validation = By
			.xpath("//form/div[@id='modal-body']//form/div/label[contains(.,'Validation')]/..//input");

	// --- Edit tab ---
	public static By txt_fieldNameEdittab = By.xpath("//form/div/label[contains(.,'Field name')]/..//input");
	public static By txt_typeEdittab = By.xpath("//form/div/label[contains(.,'Data type')]/..//input");
	public static By txt_UKLabelEdittab = By.xpath("//form/div/label[contains(.,'United Kingdom')]/..//input");
	public static By txt_USLabelEdittab = By.xpath("//form/div/label[contains(.,'US')]/..//input");
	public static By txt_DeuLabelEdittab = By.xpath("//form/div/label[contains(.,'Deutsch')]/..//input");
	public static By dd_validationEdittab = By.xpath("//form/div/label[contains(.,'Validation')]/..//input");

	// --- Incident Type page -----
	public static By btn_addNewType = By.xpath("//div[@class='global-bar']/div[1]");
	public static By txt_InputName = By.xpath("//div/label[text() = 'Name']/../div/input");
	public static By txt_incidentCode = By.xpath("//div/label[text() = 'Code']/../div/input");
	public static By txt_workflowDD = By
			.xpath("//*[@id='modal-body']/div/form/div[3]/div/ng-select/div/div/div[2]/input");
	public static By div_workflowDropdown = By.xpath("//ng-dropdown-panel/div/div[2]");

	public static By btn_EditIncidentType = By.xpath("//div[@class='table-cell btn-detail-action']/button");

	// ---Incident Catalog page
	public static By treenode_UncategorizedList = By
			.xpath("//tree-root[@id='uncategorizedTree']//tree-node//div/tree-node//div//tree-node-content/span");
	public static By node_EndItemOfUncategory = By
			.xpath("//tree-root[@id='uncategorizedTree']//tree-node//div/tree-node[last()]");
	public static By btn_addNewCategory = By.xpath("//button[@ng-click='vmIncidentTypeAssignment.addNewCategory();']");
	public static By btn_SaveCategory = By.xpath("//button[@ng-click='vmIncidentTypeAssignment.saveCategory()']");

	// ------------- States ----------
	public static By txt_stateName = By.xpath("//table/tbody/tr/td[3]/input");
	public static By chk_finalState = By
			.xpath("//table/tbody/tr/td[3]/input/../following-sibling::td/div[@class='checkbox']/input");
	public static By dd_requiredFields = By.xpath("//table/tbody/tr/td[5]/input");
	public static By txt_stateDesc = By.xpath("//table/tbody/tr/td[7]/input");
	
	// --------------- Roles --------------
	public static By txt_roleName = By.xpath("//table/tbody/tr/td/input");
	
	// --------------- Roles Assignment ------------
	public static By dd_roles = By.xpath("//tbody/tr[@class='active']/td[5]/ng-select/div");
	public static By btn_clearAll = By.xpath("//span[@title='Clear all']");

	// --------------- Workflow ----------
	public static By txt_workflowName = By.xpath("//app-workflow-create/form/div[2]//form/div/div/input");
	public static By chk_defaultWorkflowAtForm = By
			.xpath("//app-workflow-create/form/div[2]//form/div[2]/div/div/input");

	public static By dd_firstState = By.xpath("//app-workflow-create/form/div[2]//form/div[3]/div");

	public static By txt_workflowNameAtEditTab = By.xpath("//form/div/div/div[1]//input");
	public static By chk_defaultWorkflowAtEditTab = By.xpath("//form/div/div/div[2]//input");
	public static By dd_firstStateAtEditTab = By.xpath("//form/div/div/div[3]//input");

}
