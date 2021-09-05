package libraries.productFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import interfaces.I_CP_Common;
import interfaces.I_CP_Settings;
import interfaces.I_Global_Settings;
import libraries.CPValueList;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Log;
import libraries.generalFunctions.Mouse;

public class F_CPSettings {


	public static void goToSettings(SeleniumBrowser Browser) {
		F_Navigation.goToPage(Browser, I_CP_Settings.tab_Settings);
	}

	public static void clickAddNew(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.btn_New);
		Functions.waitForSeconds(1);
	}

	public static void commonSearch(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_CP_Settings.txt_commonSearch, pValue);
		// Browser.switchParentFrame();
		Mouse.FreeMouse(Browser);
		Functions.waitForSeconds(2);
	}

	public static void clickSaveEdit(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.btn_SaveEdit);
		Log.info("Clicked Save Edit");
	}

	public static class goToPage {

		public static void VRM(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_CP_Settings.VRM.tab_VRM);
			Browser.waitForElementVisible(I_CP_Common.rowEle);
		}

		public static void VoidReason(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_CP_Settings.VoidReasons.tab_VoidReasons);
			Browser.waitForElementVisible(I_CP_Common.rowEle);
		}

		public static void Locations(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_CP_Settings.Location.tab_Location);
		}

		public static void VehicleInfo(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_CP_Settings.VehicleInfo.tab_Vehicle);
			Browser.waitForElementVisible(I_CP_Common.rowEle);
		}

		public static void OffenceTypes(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_CP_Settings.OffenceTypes.tab_OffenceTypes);
			Browser.waitForElementVisible(I_CP_Common.rowEle);
		}

		public static void Assignment(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_CP_Settings.OffenceTypeAssign.tab_OffenceTypeAssign);
			Browser.waitForElementVisible(I_CP_Common.rowEle);
		}

		public static void ICES_Export(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_CP_Settings.ICESExport.tab_ICESExport);
			Browser.waitForElementVisible(I_CP_Common.rowEle);
		}

	}

	// ------------- VRM --------------
	public static void fillVehicleNumber(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_CP_Settings.VRM.txt_VehicleNumber, pValue);
	}

	public static void editVehicleNumber(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_CP_Settings.VRM.txt_EditVehicleNumber, pValue);
	}

	public static void selectCameraName(SeleniumBrowser Browser, String pText) {
		Browser.selectDropdownByText(I_CP_Settings.VRM.select_CameraName, pText);
	}

	public static String selectRandomCamera(SeleniumBrowser Browser) {
	//	 Browser.selectRandomDropdown(I_CP_Settings.VRM.select_CameraName);
		Browser.click(I_CP_Settings.VRM.select_CameraName);
		Functions.waitForSeconds(1);
	//By dropdownEle = By.xpath("//app-dropdown-camera/ng-select/ng-dropdown-panel/div/div[2]");
	
	return Browser.selectRandomDropdown( Browser, I_CP_Common.div_detailDropdown );
	}

	public static void inputExceptionDate(SeleniumBrowser Browser, String fromValue , String toValue) {
//		Browser.enter(I_CP_Settings.VRM.txt_ExceptionDateFrom, fromValue);
//		Browser.enter(I_CP_Settings.VRM.txt_ExceptionDateTo, toValue);
	
		if (fromValue != "")
		Functions.selectDatePicker(Browser, I_CP_Settings.VRM.txt_ExceptionDateFrom, Constants.Dateformat, fromValue) ;
		
		if (toValue != "")
		Functions.selectDatePicker(Browser, I_CP_Settings.VRM.txt_ExceptionDateTo, Constants.Dateformat, toValue) ;
	
	
	}

	public static void clickSaveVRM(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.VRM.btn_Save);
		Functions.waitForSeconds(1);
	}

	public static void clickCancelVRM(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.VRM.btn_cancel);
	}

	public static void selectVRM(SeleniumBrowser Browser, String pValue) {
		commonSearch(Browser, pValue);
		F_CP_common.selectRow(Browser, 1);
	}

	public static void selectRandomVRM(SeleniumBrowser Browser) {
		int r = Functions.randomInterger(1, 10);
		F_CP_common.selectRow(Browser, r);
	}

	public static void clickEditVRM(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.VRM.btn_Edit);
		Functions.waitForSeconds(0.5);
	}

	public static String addNewVRM(SeleniumBrowser Browser) {
		String vrm = F_CP_common.randomDVLA_VRM();
		String from = Functions.getCurrentDate(Constants.Dateformat); 
		String to = Functions.getNextDate(Constants.Dateformat, 7); 
		goToSettings(Browser);
		goToPage.VRM(Browser);
		clickAddNew(Browser);
		fillVehicleNumber(Browser, vrm);
		inputExceptionDate(Browser, from, to);
		selectRandomCamera(Browser);
		clickSaveVRM(Browser);
		F_CP_common.waitMsgDisappear();
		return vrm;
	}
	
	public static String addNewVRM(SeleniumBrowser Browser, String vrm) {
		String from = Functions.getCurrentDate(Constants.Dateformat); 
		String to = Functions.getNextDate(Constants.Dateformat, 7); 
		goToSettings(Browser);
		goToPage.VRM(Browser);
		clickAddNew(Browser);
		fillVehicleNumber(Browser, vrm);
		inputExceptionDate(Browser, from, to);
		
		String cam = selectRandomCamera(Browser);
		clickSaveVRM(Browser);
		return cam;
	}

	public static void clickDeleteVRM(SeleniumBrowser Browser) {
		Browser.waitForElementEnabled(I_CP_Settings.VRM.btn_Delete);
		Browser.click(I_CP_Settings.VRM.btn_Delete);
	}

	public static void clickConfirmDelete(SeleniumBrowser Browser) {
		//Browser.waitForElementVisible(I_CP_Common.btn_OkConfirm);
		//Browser.click(I_CP_Common.btn_OkConfirm);
		Browser.clickJavascript(I_CP_Common.btn_OkConfirm);   
		//Functions.waitForSeconds(2);
	}

	public static void clickConfirmDeleteCatalog(SeleniumBrowser Browser) {
		Browser.waitForElementEnabled(I_CP_Common.btn_OkConfirm);
		//Browser.click(I_CP_Common.btn_OkDeleteConfirm);
		Browser.clickJavascript(I_CP_Common.btn_OkDeleteConfirm);  
		Functions.waitForSeconds(2);
	}
	
	public static void clickImport(SeleniumBrowser Browser) {
		Browser.waitForElementEnabled(I_CP_Settings.VRM.btn_Import);
		Browser.click(I_CP_Settings.VRM.btn_Import);
	}
	
	// --------- Void reason -----------
	public static void fillVRName(SeleniumBrowser Browser, String pValue) {
		Functions.waitForSeconds(1);
		Browser.clear(I_CP_Settings.VoidReasons.txt_Name);
		Browser.enter(I_CP_Settings.VoidReasons.txt_Name, pValue);
	}

	public static void selectRequired(SeleniumBrowser Browser) {
		Browser.selectCheckbox(I_CP_Settings.VoidReasons.chk_RequiredDesc);
	}

	public static void clickSaveVR(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.VoidReasons.btn_Save);
	}

	public static void clickCancelVR(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.VoidReasons.btn_Cancel);
	}

	public static void addNewVoidReason(SeleniumBrowser Browser, String voidReason, boolean required) {
		goToSettings(Browser);
		goToPage.VoidReason(Browser);
		clickAddNew(Browser);

		fillVRName(Browser, voidReason);
		if (required == true)
			selectRequired(Browser);
		clickSaveVR(Browser);
		F_CP_common.waitMsgDisappear();
	}

	public static void clickDeleteVR(SeleniumBrowser Browser) {
		Browser.waitForElementEnabled(I_CP_Settings.VoidReasons.btn_Delete);
		Browser.click(I_CP_Settings.VoidReasons.btn_Delete);
	}

	public static void selectVR(SeleniumBrowser Browser, String pValue) {
		commonSearch(Browser, pValue);
		F_CP_common.selectRow(Browser, 1);
	}

	public static void selectRandomVR(SeleniumBrowser Browser) {
		int r = Functions.randomInterger(1, 7);
		F_CP_common.selectRow(Browser, r);
	}

	public static void clickEditVR(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.VoidReasons.btn_Edit);
	}

	// ------------- Locations --------------

	public static void fillStreetName(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_CP_Settings.Location.txt_streetName, pValue);
	}

	public static void fillCameraId(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_CP_Settings.Location.txt_CamId, pValue);
	}
	public static void fillStreetId(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_CP_Settings.Location.txt_streetId, pValue);
	}

	public static String selectRandomCameraOfLocaion(SeleniumBrowser Browser) {
		 Browser.click(I_CP_Settings.Location.dd_Camera);
		 
		return Browser.selectRandomDropdown(Browser, I_CP_Settings.Location.div_Cameralist);
	}

	public static void clickSaveLocation(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.Location.btn_Save);
	}

	public static void clickDeleteStreet(SeleniumBrowser Browser) {
		Browser.waitForElementEnabled(I_CP_Settings.VRM.btn_Delete);
		Browser.click(I_CP_Settings.Location.btn_Delete);
	}


	public static void selectCamId(SeleniumBrowser Browser, String camId) {
		WebElement st = null;
		st = Browser.captureInterface(By.xpath("//tbody/tr[1]/td[contains(.,'" + camId + "')]"));
		Browser.scrollToEle(st);
		st.click();
	}

	public static String createNewLocation(SeleniumBrowser Browser, String camId, String street, String id) {
		goToSettings(Browser);
		goToPage.Locations(Browser);
		clickAddNew(Browser);
		fillStreetName(Browser, street);
		fillStreetId(Browser, id);
		fillCameraId(Browser, camId);
		String cam = F_CPSettings.selectRandomCameraOfLocaion(Browser);
		clickSaveLocation(Browser);
		F_CP_common.waitMsgDisappear();
		return cam;
	}

	public static void clickEditLocation(SeleniumBrowser Browser) {

		Browser.click(I_CP_Settings.Location.btn_Edit);
	}

	// ------------- Vehicle Info --------------

	public static void clickManuTab(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.VehicleInfo.tab_Manu);
	}

	public static void clickModelTab(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.VehicleInfo.tab_Model);
	}

	public static void clickColourTab(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.VehicleInfo.tab_Colour);
	}

	public static void fillNameManu(SeleniumBrowser Browser, String pValue) {
		Browser.waitForElementVisible(I_CP_Settings.VehicleInfo.txt_Name_manu);
		Browser.enter(I_CP_Settings.VehicleInfo.txt_Name_manu, pValue);
	}

	public static void clickSaveManu(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.VehicleInfo.btn_Save_manu);
	}

	public static void clickDeleteVI(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.VehicleInfo.btn_Delete);
	}

	public static void fillNameModel(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_CP_Settings.VehicleInfo.txt_Name_model, pValue);
	}

	public static String selectRandomManu(SeleniumBrowser Browser) {
		// Browser.selectDropdownByText(I_CP_Settings.VehicleInfo.sel_manu_dropdown,
		// pText);
		Browser.click(I_CP_Settings.VehicleInfo.sel_manu_dropdown);
		String manufacturer = "AMERICAN"; // Browser.selectRandomDropdown(Browser,I_CP_Settings.VehicleInfo.sel_manu_dropdown, "option");

		Browser.selectDropdownByText(I_CP_Settings.VehicleInfo.sel_manu_dropdown, manufacturer);
		Browser.click(I_CP_Settings.VehicleInfo.sel_manu_dropdown);
		return manufacturer;
	}

	public static void clickSaveModel(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.VehicleInfo.btn_Save_model);
	}

	public static void fillNamecolour(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_CP_Settings.VehicleInfo.txt_Name_color, pValue);
	}

	public static void clickSaveColour(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.VehicleInfo.btn_Save_colour);
	}

	public static void searchVehicleInfo(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_CP_Settings.VehicleInfo.txt_SearchVehicleInfo, pValue);
		Mouse.FreeMouse(Browser);
		Functions.waitForSeconds(1);
	}

	public static void selectVehicleInfo(SeleniumBrowser Browser, String pValue) {
		searchVehicleInfo(Browser, pValue);
		Browser.click(By.xpath("//div[@class='panel-collapse collapse in show']//table/tbody/tr[1]/td[1]"));
		Functions.waitForSeconds(0.5);
	}

	public static void createNewManu(SeleniumBrowser Browser, String name) {
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.VehicleInfo(Browser);
		F_CPSettings.clickAddNew(Browser);
		F_CPSettings.fillNameManu(Browser, name);
		F_CPSettings.clickSaveManu(Browser);
		F_CP_common.waitMsgDisappear();
	}

	public static void createNewModel(SeleniumBrowser Browser, String name) {
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.VehicleInfo(Browser);
		F_CPSettings.clickModelTab(Browser);
		F_CPSettings.clickAddNew(Browser);
		F_CPSettings.fillNameModel(Browser, name);
		F_CPSettings.selectRandomManu(Browser);
		F_CPSettings.clickSaveModel(Browser);
		F_CP_common.waitMsgDisappear();
	}

	public static void createNewColour(SeleniumBrowser Browser, String name) {
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.VehicleInfo(Browser);
		F_CPSettings.clickColourTab(Browser);
		F_CPSettings.clickAddNew(Browser);
		F_CPSettings.fillNamecolour(Browser, name);
		F_CPSettings.clickSaveColour(Browser);
		F_CP_common.waitMsgDisappear();
	}

	
	public static void deleteVI(SeleniumBrowser Browser, String name) {
		F_CPSettings.selectVehicleInfo(Browser, name);
		F_CPSettings.clickDeleteVI(Browser);
		F_CPSettings.clickConfirmDelete(Browser);
		Functions.waitForSeconds(0.5);
	}
	// ------------- Offence Types --------------

	public static void fillOffenceType(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_CP_Settings.OffenceTypes.txt_Name, pValue);
	}

	public static void fillCode(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_CP_Settings.OffenceTypes.txt_Code, pValue);
	}

	public static void clickSaveOffenceType(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.OffenceTypes.btn_Save);
	}

	public static void clickCancelOffenceType(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.OffenceTypes.btn_Cancel);
	}

	public static void createOffenceType(SeleniumBrowser Browser, String type, String code) {
		goToSettings(Browser);
		goToPage.OffenceTypes(Browser);
		clickAddNew(Browser);
		fillOffenceType(Browser, type);
		fillCode(Browser, code);
		clickSaveOffenceType(Browser);
		F_CP_common.waitMsgDisappear();
	}

	public static void clickEditType(SeleniumBrowser Browser, String name) {
		WebElement editEle = Browser
				.captureInterface(By.xpath("//tbody/tr/td[contains(.,'" + name + "')]/../td[3]/button[1]"));
		editEle.click();
	}
	

	public static void clickDeleteType(SeleniumBrowser Browser, String name) {
		WebElement editEle = Browser
				.captureInterface(By.xpath("//tbody/tr/td[contains(.,'" + name + "')]/../td[3]/button[2]"));
		editEle.click();
	}

	// ------------------ Assignment -----------
	public static void clickAddCatalog(SeleniumBrowser Browser, String name) {
		WebElement editEle = Browser
				.captureInterface(By.xpath("//tree-node-wrapper/div/div/tree-node-content[contains(.,'" + name + "')]/button[1]/i"));
		editEle.click();
		Functions.waitForSeconds(1);
	}

	// public static void clickAddSubCatalog(SeleniumBrowser Browser, String name) {
	// WebElement editEle = Browser.captureInterface(By
	// .xpath("//tree-node-content[contains(.,'" + name + "')]/button[1]/i"));
	// editEle.click();
	// }

	public static void clickDeleteCatalog(SeleniumBrowser Browser, String name) {
		By deleteCatEle = By.xpath("//tree-node/div/tree-node-wrapper//tree-node-content[contains(.,'" + name + "')]/button[2]/i");
		WebElement editEle = Browser.captureInterface(deleteCatEle);
		Browser.scrollToEle(editEle);
	//	editEle.click();
		Browser.clickJavascript(deleteCatEle);  
	}

	public static void fillCatalogName(SeleniumBrowser Browser, String pValue) {
		Browser.scrollToEle(I_CP_Settings.OffenceTypeAssign.txt_Name);
		Browser.enter(I_CP_Settings.OffenceTypeAssign.txt_Name, pValue);
	}

	public static void clickSaveCatalogue(SeleniumBrowser Browser) {
		Browser.scrollToEle(I_CP_Settings.OffenceTypeAssign.btn_Save);
		Browser.click(I_CP_Settings.OffenceTypeAssign.btn_Save);
		
	}

	public static void clickSaveEditCatalogue(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.OffenceTypeAssign.btn_SaveEdit);
	}

	public static void expandCatalog(SeleniumBrowser Browser, String name) {
		By expandBy = By.xpath("//tree-node-content[contains(.,'" + name + "')]/../../tree-node-expander/span");
		WebElement catalogEle = Browser.captureInterface(expandBy);
		String classValue = "toggle-children-wrapper-collapsed";
		String a = catalogEle.getAttribute("class");
		System.out.println(a);
		if (catalogEle.getAttribute("class").contains(classValue)) {
			catalogEle.click();
			Browser.waitForAttributeValue(expandBy, "class", "toggle-children-wrapper-expanded");
		}
	}

	public static void collapseCatalog(SeleniumBrowser Browser, String name) {
		By expandBy = By.xpath("//tree-node-content[contains(.,'" + name + "')]/../../tree-node-expander/span");
		WebElement catalogEle = Browser.captureInterface(expandBy);
		String classValue = "toggle-children-wrapper-expanded";
		String a = catalogEle.getAttribute("class");
		System.out.println(a);
		if (catalogEle.getAttribute("class").contains(classValue)) {
			catalogEle.click();
			Browser.waitForAttributeValue(expandBy, "class", "toggle-children-wrapper-collapsed");
		}
	}
	public static void clickExtendUncategorized(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.OffenceTypeAssign.btn_extendUncategorized);
	}

	public static void createCatalog(SeleniumBrowser Browser, String name) {
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.Assignment(Browser);
		F_CPSettings.clickAddCatalog(Browser, "OFFENCE CATALOG");
		F_CPSettings.fillCatalogName(Browser, name);
		F_CPSettings.clickSaveCatalogue(Browser);
	}

	public static void editCatalog(SeleniumBrowser Browser, String originalName, String newName) {
		WebElement catalogEle = Browser
				.captureInterface(By.xpath("//img/following-sibling::span[contains(.,'" + originalName + "')]"));
		Mouse.RightclickOnElement(Browser, catalogEle);
		fillCatalogName(Browser, newName);
		clickSaveEditCatalogue(Browser);
	}

	public static void dragdropType(SeleniumBrowser Browser, String typeName, String catelogName)
	{
		//By typeEle = By.xpath("//tree-node-wrapper/div/div/tree-node-content/span[text() = \""+ typeName + "\"]");
		
		By typeBy = By.xpath("//tree-node-wrapper/div/div/tree-node-content/span[text() = \"Vehicle Type Prohibition (motor vehicles)\"]");
		WebElement typeEle = Browser.captureInterface(typeBy);
		System.out.println(typeEle.getSize());
		System.out.println(typeEle.getText());
//		WebElement catalogEle = Browser
//				.captureInterface(By.xpath("//img/following-sibling::span[contains(.,'" + catelogName + "')]"));
		WebElement catalogEle = Browser
				.captureInterface(By.xpath("//img/following-sibling::span[contains(.,'ZEOKIQ_TEST')]"));
		
		System.out.println(catalogEle.getSize());
		System.out.println(catalogEle.getText());
		Mouse.DragDropElement(Browser, typeEle, catalogEle);
		
	}
	// ---------- ICES setup -----
	public static String updateTimeOfICESSetings(SeleniumBrowser Browser) {
		String time = "";
		int hour = Functions.randomInterger(0, 24);
		int min = Functions.randomInterger(0, 59);
		Browser.click(I_CP_Settings.ICESExport.txt_Time);
		
		Browser.enter(I_CP_Settings.ICESExport.txt_Timehour, Integer.toString(hour));
		Browser.enter(I_CP_Settings.ICESExport.txt_Timeminute,  Integer.toString(min));
		Mouse.FreeMouse(Browser);
		Functions.waitForSeconds(1);
		time = Integer.toString(hour)+ ":" + Integer.toString(min);
		return time;
	}
	
	public static void clickSaveICESsettings(SeleniumBrowser Browser) {
		Browser.click(I_CP_Settings.ICESExport.btn_Save);
	}

}
