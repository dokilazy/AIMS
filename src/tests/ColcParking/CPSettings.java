package tests.ColcParking;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import interfaces.I_CP_Common;
import interfaces.I_CP_Settings;
import libraries.CPValueList;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.TestConfig.TestSuite;
import libraries.generalFunctions.Functions;
import libraries.productFunctions.F_AIMS;
import libraries.productFunctions.F_CPSettings;
import libraries.productFunctions.F_CP_common;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_CPVerification;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;
import java.text.MessageFormat;

public class CPSettings extends DefaultAnnotations {

	@Test(groups = { TestSuite.Smoke })
	public void Verify_add_new_VRM() {
		String vrm = F_CP_common.randomDVLA_VRM();
		System.out.println("VRM: " + vrm);
		String fromDate = Functions.getCurrentDate(Constants.Dateformat);
		String toDate = Functions.getNextDate(Constants.Dateformat, 7);

		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		Reporter.log("The web is open and login");
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.VRM(Browser);
		F_CPSettings.clickAddNew(Browser);
		F_CPSettings.fillVehicleNumber(Browser, vrm);
		F_CPSettings.inputExceptionDate(Browser, fromDate, toDate);
		String cam = F_CPSettings.selectRandomCamera(Browser);
		F_CPSettings.clickSaveVRM(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "VRM Exception"), Result);
		F_CPVerification.verifyVRMexistinTable(Browser, vrm, cam, fromDate, toDate, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_add_new_VRM_without_exceptionDateTo() {
		String vrm = F_CP_common.randomDVLA_VRM();
		System.out.println("VRM: " + vrm);
		String fromDate = Functions.getCurrentDate(Constants.Dateformat);
		String toDate = "N/A";

		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		Reporter.log("The web is open and login");
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.VRM(Browser);
		F_CPSettings.clickAddNew(Browser);
		F_CPSettings.fillVehicleNumber(Browser, vrm);
		F_CPSettings.inputExceptionDate(Browser, fromDate, "");
		String cam = F_CPSettings.selectRandomCamera(Browser);
		F_CPSettings.clickSaveVRM(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "VRM Exception"), Result);
		F_CPVerification.verifyVRMexistinTable(Browser, vrm, cam, fromDate, toDate, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_edit_VRM() {

		String newVrm = F_CP_common.randomDVLA_VRM();
		// String cam = "";
		String from = Functions.getCurrentDate(Constants.Dateformat);
		String to = Functions.getNextDate(Constants.Dateformat, 7);
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.VRM(Browser);

		F_CPSettings.selectRandomVRM(Browser);
		F_CPSettings.clickEditVRM(Browser);
		F_CPSettings.editVehicleNumber(Browser, newVrm);
		// cam = Functions.getValue(Browser, I_CP_Settings.VRM.txt_Camera);
		F_CPSettings.inputExceptionDate(Browser, from, to);
		F_CPSettings.clickSaveEdit(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "VRM Exception"), Result);
		F_CP_common.closeTab(Browser);

		F_Navigation.RefreshPage(Browser);
		F_CPSettings.goToPage.VRM(Browser);
		F_CPVerification.verifyVRMexistinTable(Browser, newVrm, "", from, to, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_delete_VRM() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);

		String vrm = F_CPSettings.addNewVRM(Browser);
		F_Navigation.RefreshPage(Browser);
		F_CPSettings.selectVRM(Browser, vrm);
		F_CPSettings.clickDeleteVRM(Browser);
		F_CPVerification.verifyDeleteConfirm(Browser, "VRM Exception", Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CPSettings.clickConfirmDelete(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.DeleteSuccess, "VRM Exception"), Result);
		F_GeneralVerification.verifyTableNull(Browser, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_add_new_VoidReason() {
		String voidReason = Functions.randomText();
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.VoidReason(Browser);
		F_CPSettings.clickAddNew(Browser);

		F_CPSettings.fillVRName(Browser, voidReason);
		F_CPSettings.selectRequired(Browser);
		F_CPSettings.clickSaveVR(Browser);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "Void Reason"), Result);
		F_CPVerification.verifyVRexistinTable(Browser, voidReason, "Yes", Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// ---- Post condition -----
		F_CPSettings.selectVR(Browser, voidReason);
		F_CPSettings.clickDeleteVR(Browser);
		F_CPSettings.clickConfirmDelete(Browser);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_edit_voidReason() {
		String voidReason = Functions.randomText();

		String voidReasonUpdate = Functions.randomText();
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.addNewVoidReason(Browser, voidReason, false);

		// F_CPSettings.selectRandomVR(Browser);
		F_CPSettings.selectVR(Browser, voidReason);
		F_CPSettings.clickEditVR(Browser);
		F_CPSettings.fillVRName(Browser, voidReasonUpdate);
		F_CPSettings.clickSaveEdit(Browser);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "Void Reason"), Result);
		F_CP_common.closeTab(Browser);

		F_CPVerification.verifyVRexistinTable(Browser, voidReasonUpdate, "No", Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// ---- Post condition -----
		F_CPSettings.selectVR(Browser, voidReasonUpdate);
		F_CPSettings.clickDeleteVR(Browser);
		F_CPSettings.clickConfirmDelete(Browser);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_delete_VoidReason() {
		String voidReason = Functions.randomText();
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.addNewVoidReason(Browser, voidReason, false);
		F_CPVerification.verifyVRexistinTable(Browser, voidReason, "No", Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// -------- Delete ----
		F_CPSettings.selectVR(Browser, voidReason);
		F_CPSettings.clickDeleteVR(Browser);
		F_CPVerification.verifyDeleteConfirm(Browser, "Void Reason", Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CPSettings.clickConfirmDelete(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.DeleteSuccess, "Void Reason"), Result);
		F_GeneralVerification.verifyTableNull(Browser, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_add_new_location() {
		String streetName = Functions.randomText();
		String streetId = Functions.randomText();
		String camId = Functions.randomNumberString(5);
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.Locations(Browser);
		F_CPSettings.clickAddNew(Browser);

		F_CPSettings.fillStreetName(Browser, streetName);
		F_CPSettings.fillCameraId(Browser, camId);
		F_CPSettings.fillStreetId(Browser, streetId);
		String cam = F_CPSettings.selectRandomCameraOfLocaion(Browser);

		F_CPSettings.clickSaveLocation(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "Contravention Location"), Result);

		F_Navigation.RefreshPage(Browser);
		F_CPVerification.verifyLocationExistinTable(Browser, camId, cam, streetName, streetId, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_edit_location() {
		String street = Functions.randomText();
		String streetId = Functions.randomText();
		String camId = Functions.randomNumberString(5);
		String newStreetName = Functions.randomText();

		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		String cam = F_CPSettings.createNewLocation(Browser, camId, street, streetId);
		F_CPSettings.commonSearch(Browser, camId);
		F_CPSettings.selectCamId(Browser, camId);
		F_CPSettings.clickEditLocation(Browser);

		F_CPSettings.fillStreetName(Browser, newStreetName);
		F_CPSettings.clickSaveEdit(Browser);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "Contravention Location"), Result);
		F_CP_common.closeTab(Browser);

		F_CPVerification.verifyLocationExistinTable(Browser, camId, cam, newStreetName, streetId, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_delete_location() {
		String street = Functions.randomText();
		String streetId = Functions.randomText();
		String camId = Functions.randomNumberString(5);
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		String cam = F_CPSettings.createNewLocation(Browser, camId, street, streetId);
		F_CPSettings.commonSearch(Browser, camId);
		F_CPSettings.selectCamId(Browser, camId);
		F_CPSettings.clickDeleteStreet(Browser);
		F_CPVerification.verifyDeleteConfirm(Browser, "Contravention Location", Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CPSettings.clickConfirmDelete(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.DeleteSuccess, "Contravention Location"), Result);
		// F_GeneralVerification.verifyTableNull(Browser, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_add_new_OffenceType() {
		String type = Functions.randomText(20);
		String code = Functions.randomText();

		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.OffenceTypes(Browser);
		F_CPSettings.clickAddNew(Browser);
		F_CPSettings.fillOffenceType(Browser, type);
		F_CPSettings.fillCode(Browser, code);
		F_CPSettings.clickSaveOffenceType(Browser);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "Offence Type"), Result);
		F_CPVerification.verifyOffenceExistinTable(Browser, type, code, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// ------ Post condition ---
		F_CPSettings.commonSearch(Browser, type);
		F_CPSettings.clickDeleteType(Browser, type);
		F_CPSettings.clickConfirmDelete(Browser);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_edit_OffenceType() {
		String type = Functions.randomText(20);
		String newType = Functions.randomText(25);
		String code = Functions.randomText();
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.createOffenceType(Browser, type, code);
		F_CPSettings.commonSearch(Browser, type);
		F_CPSettings.clickEditType(Browser, type);

		F_CPSettings.fillOffenceType(Browser, newType);
		F_CPSettings.clickSaveOffenceType(Browser);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "Offence Type"), Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// ------ Post condition ---
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.OffenceTypes(Browser);
		F_CPSettings.commonSearch(Browser, newType);
		F_CPSettings.clickDeleteType(Browser, newType);
		F_CPSettings.clickConfirmDelete(Browser);

	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_delete_OffenceType() {
		String type = Functions.randomText(20);
		String code = Functions.randomText();
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.createOffenceType(Browser, type, code);
		F_CPSettings.commonSearch(Browser, type);
		F_CPSettings.clickDeleteType(Browser, type);
		F_CPVerification.verifyDeleteConfirm(Browser, "Offence Type", Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CPSettings.clickConfirmDelete(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.DeleteSuccess, "Offence Type"), Result);
		F_CPSettings.commonSearch(Browser, type);
		F_GeneralVerification.verifyTableNull(Browser, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_add_new_Catalogue() {
		String catalog = Functions.randomText();
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.Assignment(Browser);
		F_CPSettings.clickAddCatalog(Browser, "OFFENCE CATALOG");
		F_CPSettings.fillCatalogName(Browser, catalog);
		F_CPSettings.clickSaveCatalogue(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "Offence Catalog"), Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// --- Post condition ---
		F_CPSettings.clickDeleteCatalog(Browser, catalog);
		F_CPSettings.clickConfirmDeleteCatalog(Browser);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_add_new_SubCatalogue() {
		String catalog = Functions.randomText();
		String subCatalog = Functions.randomText();
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.Assignment(Browser);
		F_CPSettings.clickAddCatalog(Browser, "OFFENCE CATALOG");
		F_CPSettings.fillCatalogName(Browser, catalog);
		F_CPSettings.clickSaveCatalogue(Browser);

		// --- Add sub catelog ----
		F_CPSettings.clickAddCatalog(Browser, catalog);
		F_CPSettings.expandCatalog(Browser, catalog);
		F_CPSettings.fillCatalogName(Browser, subCatalog);
		F_CPSettings.clickSaveCatalogue(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "Offence Catalog"), Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// --- post condition ---
		F_CPSettings.clickDeleteCatalog(Browser, subCatalog);
		F_CPSettings.clickConfirmDeleteCatalog(Browser);
		Functions.waitForSeconds(2);
		F_CPSettings.clickDeleteCatalog(Browser, catalog);
		F_CPSettings.clickConfirmDeleteCatalog(Browser);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_delete_Catalogue() {
		String catalog = Functions.randomText();
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.createCatalog(Browser, catalog);
		F_Navigation.RefreshPage(Browser);
		// F_CPSettings.collapseCatalog(Browser, "MAJOR");
		// F_CPSettings.collapseCatalog(Browser, "MINOR");
		F_CPSettings.clickDeleteCatalog(Browser, catalog);
		Functions.waitForSeconds(2);
		F_CPVerification.verifyDeleteConfirm(Browser, "Offence Catalog", Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CPSettings.clickConfirmDeleteCatalog(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.DeleteSuccess, "Offence Catalog"), Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_edit_Offence_Catalogue() {
		String originalName = Functions.randomText();

		String newName = Functions.randomText();
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);

		F_CPSettings.createCatalog(Browser, originalName);
		F_Navigation.RefreshPage(Browser);

		F_CPSettings.editCatalog(Browser, originalName, newName);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "Offence Catalog"), Result);
		Assert.assertTrue(Result.Result, Result.Message);
		
		F_AIMS.clickDeleteCatalog(Browser, newName);
		F_AIMS.clickConfirmDeleteCatalog(Browser);
	}

	 //@Test(groups = { TestSuite.Smoke })
	public void Verify_assign_OffenceType_to_Catalogue() {
		String type = Functions.randomText(20);
		String code = Functions.randomText();
		String catalog = Functions.randomText();
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.Assignment(Browser);
		// --- add new type ---
	//	F_CPSettings.createOffenceType(Browser, type, code);
		// --- add new Catalog ---
	//	F_CPSettings.createCatalog(Browser, catalog);
		
		// ---- drag drop new type to catalog
		F_CPSettings.dragdropType(Browser, type, catalog);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "Offence Catalog"), Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// ---- Post condtion ----
//		F_CPSettings.goToPage.OffenceTypes(Browser);
//		F_CPSettings.commonSearch(Browser, type);
//		F_CPSettings.clickDeleteType(Browser, type);
//		F_CPSettings.clickConfirmDelete(Browser);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_save_ICES_ExportConfig() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.ICES_Export(Browser);
		F_CPSettings.updateTimeOfICESSetings(Browser);
		F_CPSettings.clickSaveICESsettings(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "ICES FTP Setting"), Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_add_new_manufacturer() {
		String name = Functions.randomText();

		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.VehicleInfo(Browser);
		F_CPSettings.clickAddNew(Browser);

		F_CPSettings.fillNameManu(Browser, name);
		F_CPSettings.clickSaveManu(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "Manufacturer"), Result);
		F_CPVerification.verifyVehicleInfoExistinTable(Browser, name, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// --- Post condition ---
		F_CPSettings.deleteVI(Browser, name);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_delete_manufacturer() {
		String name = Functions.randomText();
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.createNewManu(Browser, name);
		F_CPSettings.selectVehicleInfo(Browser, name);

		F_CPSettings.clickDeleteVI(Browser);
		F_CPVerification.verifyDeleteConfirm(Browser, "Manufacturer", Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CPSettings.clickConfirmDelete(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.DeleteSuccess, "Manufacturer"), Result);
		F_CPSettings.searchVehicleInfo(Browser, name);
		F_CPVerification.verifyVehicleRowNotExist(Browser, name, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_add_new_model() {
		String name = Functions.randomText();

		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.VehicleInfo(Browser);
		F_CPSettings.clickModelTab(Browser);
		F_CPSettings.clickAddNew(Browser);
		F_CPSettings.fillNameModel(Browser, name);
		F_CPSettings.selectRandomManu(Browser);
		F_CPSettings.clickSaveModel(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "Model"), Result);
		F_CPSettings.searchVehicleInfo(Browser, name);
		F_CPVerification.verifyVehicleInfoExistinTable(Browser, name, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// --- Post condition ---
		F_CPSettings.deleteVI(Browser, name);

	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_delete_model() {
		String name = Functions.randomText();
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.createNewModel(Browser, name);
		F_CPSettings.selectVehicleInfo(Browser, name);

		F_CPSettings.clickDeleteVI(Browser);
		F_CPVerification.verifyDeleteConfirm(Browser, "Model", Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CPSettings.clickConfirmDelete(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.DeleteSuccess, "Model"), Result);
		F_CPVerification.verifyVehicleRowNotExist(Browser, name, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_add_new_colour() {
		String name = Functions.randomText();

		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.VehicleInfo(Browser);
		F_CPSettings.clickColourTab(Browser);
		F_CPSettings.clickAddNew(Browser);
		F_CPSettings.fillNamecolour(Browser, name);
		F_CPSettings.clickSaveColour(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "Colour"), Result);
		F_CPVerification.verifyVehicleInfoExistinTable(Browser, name, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// --- Post condition ---
		F_CPSettings.deleteVI(Browser, name);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_delete_colour() {
		String name = Functions.randomText();
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.createNewColour(Browser, name);

		F_CPSettings.selectVehicleInfo(Browser, name);

		F_CPSettings.clickDeleteVI(Browser);
		F_CPVerification.verifyDeleteConfirm(Browser, "Colour", Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CPSettings.clickConfirmDelete(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.DeleteSuccess, "Colour"), Result);
		F_CPSettings.searchVehicleInfo(Browser, name);
		F_CPVerification.verifyVehicleRowNotExist(Browser, name, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	// @Test(groups = { TestSuite.Smoke })
	public void Verify_VRM_import_by_Json() {
		int s = 5;

		String exceptionDate = Functions.getCurrentDate(CPValueList.dateFormat.formatImport);
		String cameraEncoderNumber = F_CP_common.getRandomEncoderNo();
		String camera = F_CP_common.Camera(cameraEncoderNumber);
		List vrmNumber = F_CP_common.createRandomVrmlist(s);
		F_CP_common.createVRMJson(exceptionDate, cameraEncoderNumber, vrmNumber);
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.VRM(Browser);
		F_CPSettings.clickImport(Browser);

		Functions.uploadfile(Constants.DataPath, "vrm.json");

		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "VRM Exception"), Result);

		F_CPVerification.verifyImportJson(Browser, exceptionDate, cameraEncoderNumber, vrmNumber, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	// @Test(groups = { TestSuite.Smoke })
	public void Verify_VRM_import_by_Excel() {
		int s = 5;

		// String camera = F_CP_common.Camera(cameraEncoderNumber);
		List vrmNumber = F_CP_common.createRandomVrmlist(s);
		List cameraEncoderNumber = F_CP_common.createRandomEncoderNoList(s);
		List exceptionDateFrom = F_CP_common.createExceptionDateFrom(s);
		List exceptionDateTo = F_CP_common.createExceptionDateTo(s);
		F_CP_common.createVRMExcel(exceptionDateFrom, exceptionDateTo, cameraEncoderNumber, vrmNumber);

		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.VRM(Browser);
		F_CPSettings.clickImport(Browser);

		Functions.uploadfile(Functions.getCurrDirectory() + "//" + Constants.TestData, "VRM.xlsx");

		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "VRM Exception"), Result);

		F_CPVerification.verifyImport(Browser, exceptionDateFrom, exceptionDateTo, cameraEncoderNumber, vrmNumber,
				Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_VRM_import_by_CSV() {
		int s = 5;

		List vrmNumber = F_CP_common.createRandomVrmlist(s);
		List cameraEncoderNumber = F_CP_common.createRandomEncoderNoList(s);
		List exceptionDateFrom = F_CP_common.createExceptionDateFrom(s);
		List exceptionDateTo = F_CP_common.createExceptionDateTo(s);
		try {
			F_CP_common.createVRMCSV(exceptionDateFrom, exceptionDateTo, cameraEncoderNumber, vrmNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.VRM(Browser);
		F_CPSettings.clickImport(Browser);

		Functions.uploadfile(Constants.UploadVRMFilePath, "VRM.csv");
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "VRM Exception"), Result);

		F_CPVerification.verifyImport(Browser, exceptionDateFrom, exceptionDateTo, cameraEncoderNumber, vrmNumber,
				Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_VRM_import_by_CSV_without_ExceptionDateTo() {
		int s = 5;

		List vrmNumber = F_CP_common.createRandomVrmlist(s);
		List cameraEncoderNumber = F_CP_common.createRandomEncoderNoList(s);
		List exceptionDateFrom = F_CP_common.createExceptionDateFrom(s);
		try {
			F_CP_common.createVRMCSV(exceptionDateFrom, cameraEncoderNumber, vrmNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.VRM(Browser);
		F_CPSettings.clickImport(Browser);

		Functions.uploadfile(Constants.UploadVRMFilePath, "VRM.csv");
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "VRM Exception"), Result);

		F_CPVerification.verifyImportWithoutTo(Browser, exceptionDateFrom, cameraEncoderNumber, vrmNumber, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	// @Test(groups = { TestSuite.Smoke })
	public void Verify_VRM_format_validation() {
		String vrm = F_CP_common.randomDVLA_VRM();

		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPSettings.goToSettings(Browser);
		F_CPSettings.goToPage.VRM(Browser);
		F_CPSettings.clickAddNew(Browser);

		F_CPVerification.verifyNonDVLAformat(Browser, Result);

		F_CPSettings.fillVehicleNumber(Browser, vrm);
		F_CPSettings.clickSaveVRM(Browser);

		Assert.assertTrue(Result.Result, Result.Message);
	}

}