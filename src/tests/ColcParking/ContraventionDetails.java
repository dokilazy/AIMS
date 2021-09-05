package tests.ColcParking;

import java.text.MessageFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import interfaces.I_CP_Common;
import interfaces.I_CP_Contraventions;
import libraries.CPValueList;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.TestConfig.TestSuite;
import libraries.generalFunctions.Functions;
import libraries.objects.O_contravention;
import libraries.productFunctions.CommonMethods;
import libraries.productFunctions.F_CPContraventions;
import libraries.productFunctions.F_CPEnformentLog;
import libraries.productFunctions.F_CPSettings;
import libraries.productFunctions.F_CP_common;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_CPVerification;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;

public class ContraventionDetails extends DefaultAnnotations {

	@Test(groups = { TestSuite.Smoke })
	public void Verify_new_contravention() {
		String user = Constants.ConvCreationUser;
		String encoderNo = F_CP_common.getRandomEncoderNo();
		String camera = F_CP_common.Camera(encoderNo);
		Date observedTime = new Date();
		Long timeStamp = Functions.getCurrentTimeinLong(observedTime);
		String time = Functions.formatDateTime(observedTime, Constants.DateTimeformat);
		F_CPContraventions.createNewContravention(encoderNo, user, timeStamp, null, false, "");

		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.NEW.toString());
		F_CPContraventions.sortContravention(Browser, CPValueList.ContraventionColumn.time, true);
		F_CPVerification.verifyContraventionInSearch(Browser, CPValueList.Status.NEW.toString(), camera, time, user,
				Result);
		F_CPContraventions.viewFirstEntry(Browser);
		F_CPVerification.verifyBasicContraventionDetails(Browser, CPValueList.Status.NEW.toString(), camera, time, user,
				Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CPVerification.checkButtons(Browser, CPValueList.Status.NEW.toString(), Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_new_Void_contravention_from_SeMSy() {
		String user = Constants.ConvCreationUser;
		String status = CPValueList.Status.VOID.toString();
		String encoderNo = F_CP_common.getRandomEncoderNo();
		String camera = F_CP_common.Camera(encoderNo);
		String descVR = Functions.randomText(30);
		Date observedTime = new Date();
		Long timeStamp = Functions.getCurrentTimeinLong(observedTime);
		String time = Functions.formatDateTime(observedTime, Constants.Dateformat);
		F_CPContraventions.createNewContravention(encoderNo, user, timeStamp, null, true, descVR);

		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, status);
		F_CPContraventions.searchByCamera(Browser, camera);
		F_CPContraventions.sortContravention(Browser, CPValueList.ContraventionColumn.time, true);
		F_CPVerification.verifyContraventionInSearch(Browser, status, camera, time, user, Result);
		F_CPContraventions.viewFirstEntry(Browser);
		F_CPVerification.verifyBasicContraventionDetails(Browser, status, camera, time, user, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CPVerification.checkStatus(Browser, status, Result);
		F_CPVerification.checkButtons(Browser, status, Result);
		// F_CPVerification.verifyVoidReason(Browser, "Others", descVR, Result);
		F_CPVerification.verifyVoidReason(Browser, descVR, null, Result);
		Assert.assertTrue(Result.Result, Result.Message);
		
		
		// ---- Check in Enforcement log page ----
				O_contravention O_conv = F_CPContraventions.getDetails(Browser);
				F_CPEnformentLog.gotoLogpage(Browser);
				F_CPEnformentLog.searchByStatus(Browser, CPValueList.Status.VOID.toString());
				F_CPEnformentLog.searchByDateTime(Browser, CPValueList.dateTimeOption.today);
				F_CP_common.selectPageSize(Browser, CPValueList.pageSize.s80);
				F_CPVerification.verifyVoidContraventionLog(Browser, O_conv, Result);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_update_New_to_Void_contravention_without_description() {

		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.NEW.toString());
		String conv = F_CPContraventions.openLatestContravention(Browser);

		F_CPContraventions.clickVoid(Browser);

		String voidReason = F_CPContraventions.selectVoidReason(Browser, false, null);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "Contravention"), Result);

		F_CPVerification.checkStatus(Browser, CPValueList.Status.VOID.toString(), Result);
		F_CPVerification.checkButtons(Browser, CPValueList.Status.VOID.toString(), Result);
		F_CPVerification.verifyVoidReason(Browser, voidReason, null, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// ---- Check in Enforcement log page ----
		O_contravention O_conv = F_CPContraventions.getDetails(Browser);
		F_CPEnformentLog.gotoLogpage(Browser);
		F_CPEnformentLog.searchByStatus(Browser, CPValueList.Status.VOID.toString());
		F_CPEnformentLog.searchByDateTime(Browser, CPValueList.dateTimeOption.today);
		F_CP_common.selectPageSize(Browser, CPValueList.pageSize.s80);
		F_CPVerification.verifyVoidContraventionLog(Browser, O_conv, Result);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_update_New_to_Void_contravention_with_description() {

		String descVR = Functions.randomText(50);

		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.NEW.toString());
		String conv = F_CPContraventions.openLatestContravention(Browser);

		F_CPContraventions.clickVoid(Browser);
		F_CPContraventions.selectVoidReason(Browser, true, descVR);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "Contravention"), Result);
		F_CPVerification.checkStatus(Browser, CPValueList.Status.VOID.toString(), Result);
		F_CPVerification.checkButtons(Browser, CPValueList.Status.VOID.toString(), Result);
		F_CPVerification.verifyVoidReason(Browser, "Others", descVR, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// ---- Check in Enforcement log page ----
		O_contravention O_conv = F_CPContraventions.getDetails(Browser);
		F_CPEnformentLog.gotoLogpage(Browser);
		F_CPEnformentLog.searchByStatus(Browser, CPValueList.Status.VOID.toString());
		F_CPEnformentLog.searchByDateTime(Browser, CPValueList.dateTimeOption.today);
		F_CP_common.selectPageSize(Browser, CPValueList.pageSize.s80);
		F_CPVerification.verifyVoidContraventionLog(Browser, O_conv, Result);

	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_complete_contravention() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.NEW.toString());
		String conv = F_CPContraventions.openLatestContravention(Browser);

		F_CPContraventions.clickComplete(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "Contravention"), Result);
		F_CPVerification.checkStatus(Browser, CPValueList.Status.COMPLETED.toString(), Result);
		F_CPVerification.checkButtons(Browser, CPValueList.Status.COMPLETED.toString(), Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_review_contravention() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.NEW.toString());
		String convId = F_CPContraventions.openLatestContravention(Browser);

		F_CPContraventions.clickComplete(Browser);
		O_contravention conv = F_CPContraventions.selectRandomMandatoryFields(Browser);
		String vrm = F_CPContraventions.inputDVLAVehicleNo(Browser);
		F_CPContraventions.clickVideoTab(Browser);
		F_CPContraventions.captureImage(Browser);
		F_CPContraventions.captureImage(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "Image"), Result);
		F_CP_common.waitMsgDisappear();
		F_CPContraventions.clickReview(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "Contravention"), Result);

		F_CPVerification.checkStatus(Browser, CPValueList.Status.REVIEWED.toString(), Result);
		F_CPVerification.checkButtons(Browser, CPValueList.Status.REVIEWED.toString(), Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CP_common.closeTab(Browser);
		F_CPContraventions.clickReset(Browser, "Status");
		F_CPContraventions.searchByID(Browser, convId);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.REVIEWED.toString());
		F_CPContraventions.viewFirstEntry(Browser);
		F_CPVerification.checkStatus(Browser, CPValueList.Status.REVIEWED.toString(), Result);
		F_CPVerification.checkVRM(Browser, vrm, Result);
		F_CPVerification.checkStreet(Browser, conv.Street, Result);
		F_CPVerification.checkManufacturer(Browser, conv.Manufacturer, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// ---- Check in Enforcement log page ----
		O_contravention O_conv = F_CPContraventions.getDetails(Browser);
		F_CPEnformentLog.gotoLogpage(Browser);
		F_CPEnformentLog.searchByDateTime(Browser, CPValueList.dateTimeOption.today);
		F_CP_common.selectPageSize(Browser, CPValueList.pageSize.s80);
		F_CPVerification.verifyReviewedContraventionLog(Browser, O_conv, Result);

	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_mandatory_fields() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.NEW.toString());
		String conv = F_CPContraventions.openLatestContravention(Browser);
		F_CPContraventions.clickComplete(Browser);
		F_CP_common.waitMsgDisappear();
		F_CPContraventions.clickReview(Browser);
		F_CPVerification.verifyNotEmptyMessage(Browser, "Vehicle Number", Result);
		F_CPContraventions.inputDVLAVehicleNo(Browser);

		F_CPContraventions.clickReview(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				CPValueList.errorMessage.takeImage, Result);
		F_CP_common.waitMsgDisappear();
		Assert.assertTrue(Result.Result, Result.Message);

		F_CPContraventions.clickVideoTab(Browser);
		F_CPContraventions.captureImage(Browser);
		F_CPContraventions.captureImage(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.CreateSuccess, "Image"), Result);
		F_CP_common.waitMsgDisappear();
		F_CPContraventions.clickReview(Browser);

		F_CPVerification.verifyNotEmptyMessage(Browser, "Offence Code", Result);
		F_CPContraventions.selectRandomValue(Browser, I_CP_Contraventions.txt_OffenceCode);
		F_CPContraventions.clickReview(Browser);
		F_CPVerification.verifyNotEmptyMessage(Browser, "Street address", Result);
		F_CPContraventions.selectRandomValue(Browser, I_CP_Contraventions.txt_Street);
		F_CPContraventions.clickReview(Browser);
		F_CPVerification.verifyNotEmptyMessage(Browser, "Vehicle Manufacturer", Result);
		F_CPContraventions.selectRandomValue(Browser, I_CP_Contraventions.txt_Manufacturer);
		F_CPContraventions.clickReview(Browser);
		F_CPVerification.verifyNotEmptyMessage(Browser, "Vehicle Colour", Result);
		F_CPContraventions.selectRandomValue(Browser, I_CP_Contraventions.txt_Colour);

		F_CPContraventions.clickReview(Browser);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "Contravention"), Result);

		F_CPVerification.checkStatus(Browser, CPValueList.Status.REVIEWED.toString(), Result);
		F_CPVerification.checkButtons(Browser, CPValueList.Status.REVIEWED.toString(), Result);
		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_capture_images_less_than_2() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.NEW.toString());
		F_CPContraventions.searchByUser(Browser, Constants.ConvCreationUser);
		String convId = F_CPContraventions.openLatestContravention(Browser);

		F_CPContraventions.clickComplete(Browser);
		O_contravention conv = F_CPContraventions.selectRandomMandatoryFields(Browser);
		String vrm = F_CPContraventions.inputDVLAVehicleNo(Browser);

		F_CPContraventions.clickVideoTab(Browser);
		F_CPContraventions.captureImage(Browser);
		F_CP_common.waitMsgDisappear();

		F_CPContraventions.clickReview(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				CPValueList.errorMessage.takeImage, Result);
		F_CP_common.waitMsgDisappear();
		F_CPContraventions.captureImage(Browser);
		F_CP_common.waitMsgDisappear();
		// Browser.waitForElementDisabled(I_CP_Common.div_SuccessMessage);

		F_CPContraventions.clickReview(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "Contravention"), Result);

		F_CPVerification.checkStatus(Browser, CPValueList.Status.REVIEWED.toString(), Result);
		F_CPVerification.checkButtons(Browser, CPValueList.Status.REVIEWED.toString(), Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CP_common.closeTab(Browser);
		F_CPContraventions.clickReset(Browser, CPValueList.ContraventionColumn.status);
		F_CPContraventions.searchByID(Browser, convId);
		F_CPContraventions.viewFirstEntry(Browser);

		F_CPVerification.checkStatus(Browser, CPValueList.Status.REVIEWED.toString(), Result);
		F_CPVerification.checkVRM(Browser, vrm, Result);
		F_CPVerification.checkStreet(Browser, conv.Street, Result);
		F_CPVerification.checkManufacturer(Browser, conv.Manufacturer, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_review_own_contravention() {
		String user = Constants.ConvCreationUser;
		F_Navigation.loginCoLcParking(Browser, user, Constants.ConvCreationPass);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.NEW.toString());
		F_CPContraventions.searchByUser(Browser, user);
		F_CPContraventions.openLatestContravention(Browser);

		F_CPVerification.checkStatus(Browser, CPValueList.Status.NEW.toString(), Result);
		F_CPVerification.checkButtons(Browser, CPValueList.Status.REVIEWED.toString(), Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CP_common.closeTab(Browser);
		F_CPContraventions.clickReset(Browser, "Status");
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.COMPLETED.toString());
		F_CPContraventions.searchByUser(Browser, user);
		F_CPContraventions.openLatestContravention(Browser);
		F_CPVerification.checkStatus(Browser, CPValueList.Status.COMPLETED.toString(), Result);
		F_CPVerification.checkButtons(Browser, CPValueList.Status.REVIEWED.toString(), Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_unvoid_contravention() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.VOID.toString());
		// F_CPContraventions.viewFirstEntry(Browser);
		F_CPContraventions.openLatestContravention(Browser);

		F_CPVerification.checkStatus(Browser, CPValueList.Status.VOID.toString(), Result);
		F_CPVerification.checkButtons(Browser, CPValueList.Status.VOID.toString(), Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CPContraventions.clickUnVoid(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "Contravention"), Result);

		F_CPVerification.checkStatus(Browser, CPValueList.Status.COMPLETED.toString(), Result);
		F_CPVerification.checkVoidbuttonVisible(Browser, Result);
		F_CPVerification.checkSavebuttonVisible(Browser, Result);
		F_CPVerification.checkCompletebuttonInvisible(Browser, Result);
		F_CPVerification.checkReviewbuttonVisible(Browser, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_update_contravention() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.COMPLETED.toString());
		F_CPContraventions.searchByUser(Browser, Constants.ConvCreationUser);
		// F_CPContraventions.searchByCamera(Browser, "8-0-Railway Station");
		String convId = F_CPContraventions.viewFirstEntry(Browser);
		O_contravention conv = F_CPContraventions.selectRandomMandatoryFields(Browser);
		String vrm = F_CPContraventions.inputDVLAVehicleNo(Browser);
		String desc = F_CPContraventions.inputDesc(Browser);

		F_CPContraventions.clickSave(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "Contravention"), Result);

		F_CP_common.closeTab(Browser);
		F_CPContraventions.searchByID(Browser, convId);
		F_CPContraventions.viewFirstEntry(Browser);
		F_CPVerification.checkStatus(Browser, CPValueList.Status.COMPLETED.toString(), Result);
		F_CPVerification.checkVRM(Browser, vrm, Result);
		F_CPVerification.checkStreet(Browser, conv.Street, Result);
		F_CPVerification.checkManufacturer(Browser, conv.Manufacturer, Result);
		F_CPVerification.checkDesc(Browser, desc, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_vehicle_belong_to_VRM() {

		String user = Constants.ConvCreationUser;
		String vrm = F_CP_common.randomDVLA_VRM();

		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		String cam = F_CPSettings.addNewVRM(Browser, vrm);

		String encoderNo = cam.split("-")[0].toString();
		Date observedTime = new Date();
		Long timeStamp = Functions.getCurrentTimeinLong(observedTime);
		String time = Functions.formatDateTime(observedTime, Constants.Dateformat);
		F_CPContraventions.createNewContravention(encoderNo, user, timeStamp, null, false, "");

		F_CPContraventions.gotoSearchPage(Browser);
		F_CPContraventions.searchByCamera(Browser, cam);
		System.out.println(cam);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.NEW.toString());
		F_CPVerification.verifyResultNotEmpty(Browser, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CPContraventions.sortContravention(Browser, CPValueList.ContraventionColumn.time, true);
		String conId = F_CPContraventions.viewFirstEntry(Browser);
		System.out.println(conId);

		F_CPContraventions.selectVRM(Browser, vrm);
		F_CPVerification.verifyExemptConfirm(Browser, vrm, Result);
		F_CPContraventions.clickConfirmOk(Browser);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.UpdateSuccess, "Contravention"), Result);
		F_Navigation.RefreshPage(Browser);
		F_CPVerification.checkStatus(Browser, CPValueList.Status.VOID.toString(), Result);
		F_CPVerification.checkButtons(Browser, CPValueList.Status.VOID.toString(), Result);
		F_CPVerification.verifyVoidReason(Browser, "Exempt vehicle", null, Result);
		F_CPVerification.checkVRM(Browser, vrm, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_capture_images_more_than_7() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.NEW.toString());
		String convId = F_CPContraventions.openLatestContravention(Browser);

		int originalImages = F_CPContraventions.imageCount(Browser);

		for (int i = 0; i < 7 - originalImages; i++) {
			F_CPContraventions.captureImage(Browser);
		}

		F_CPContraventions.captureImage(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				CPValueList.Message.MaximumImages, Result);

		int NoOfImages = F_CPContraventions.imageCount(Browser);
		F_GeneralVerification.verifyValue("Number of Captured Images", NoOfImages, 7, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_delete_images() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.NEW.toString());
		String convId = F_CPContraventions.openLatestContravention(Browser);

		int originalImages = F_CPContraventions.imageCount(Browser);

		if (originalImages < 7) {
			F_CPContraventions.captureImage(Browser);
			F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
					MessageFormat.format(CPValueList.Message.CreateSuccess, "Image"), Result);
			F_CP_common.waitMsgDisappear();

			int NoOfImages = F_CPContraventions.imageCount(Browser);
			F_GeneralVerification.verifyValue("Number of Captured Imagesn is:", NoOfImages, originalImages + 1, Result);
			Assert.assertTrue(Result.Result, Result.Message);
		}

		int currentImages = F_CPContraventions.imageCount(Browser);

		F_CPContraventions.deleteRandomImage(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				MessageFormat.format(CPValueList.Message.DeleteSuccess, "Image"), Result);
		int NoOfImagesAfterDelete = F_CPContraventions.imageCount(Browser);
		F_GeneralVerification.verifyValue("Number of Captured Images", NoOfImagesAfterDelete, currentImages - 1,
				Result);
		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_next_new_contravention() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.NEW.toString());
		F_CPContraventions.searchByUser(Browser, Constants.ConvCreationUser);
		String nextId = F_CPContraventions.getListOfConvId(Browser).get(1).trim();
		String nextId2 = F_CPContraventions.getListOfConvId(Browser).get(2).trim();
		String convId = F_CPContraventions.viewFirstEntry(Browser);

		F_CPContraventions.clickNext(Browser);
		F_CPVerification.checkStatus(Browser, CPValueList.Status.NEW.toString(), Result);
		F_CPVerification.checkID(Browser, nextId, Result);

		F_CPContraventions.clickNext(Browser);
		F_CPVerification.checkStatus(Browser, CPValueList.Status.NEW.toString(), Result);
		F_CPVerification.checkID(Browser, nextId2, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_next_completed_contravention() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
	
		F_CPContraventions.searchByUser(Browser, Constants.ConvCreationUser);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.COMPLETED.toString());

		//F_CPContraventions.sortContravention(Browser, CPValueList.ContraventionColumn.time, true);
		String nextId = F_CPContraventions.getListOfConvId(Browser).get(1).trim();
		String convId = F_CPContraventions.viewFirstEntry(Browser);

		F_CPContraventions.clickNext(Browser);
		F_CPVerification.checkStatus(Browser, CPValueList.Status.COMPLETED.toString(), Result);
		F_CPVerification.checkID(Browser, nextId, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_next_at_last_new_contravention() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.NEW.toString());
		String convId = F_CPContraventions.openLatestContravention(Browser);

		F_CPVerification.checkStatus(Browser, CPValueList.Status.NEW.toString(), Result);
		F_CPVerification.checkButtons(Browser, CPValueList.Status.NEW.toString(), Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CPContraventions.clickNext(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				CPValueList.errorMessage.lastContravention, Result);
		F_CPVerification.checkID(Browser, convId, Result);
		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_next_at_last_completed_contravention() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.COMPLETED.toString());
		F_CPContraventions.searchByUser(Browser, Constants.ConvCreationUser);
		String convId = F_CPContraventions.openLatestContravention(Browser);

		F_CPVerification.checkStatus(Browser, CPValueList.Status.COMPLETED.toString(), Result);
		F_CPVerification.checkButtons(Browser, CPValueList.Status.COMPLETED.toString(), Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_CPContraventions.clickNext(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_CP_Common.div_SuccessMessage,
				CPValueList.errorMessage.lastContravention, Result);
		F_CPVerification.checkID(Browser, convId, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { TestSuite.Smoke })
	public void Verify_download_contravention() {

		String destDir =  Constants.DataPath  + "/Unzip"; 
		String downloadDir = Constants.DataPath  + "/Download"; 
		
		Functions.deleteFiles(destDir);
		Functions.deleteFiles(downloadDir);

		System.out.println(destDir);
		System.out.println(downloadDir);
		
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPContraventions.searchByStatus(Browser, CPValueList.Status.EXPORTED.toString());
		String convId = F_CPContraventions.openLatestContravention(Browser);
		
		O_contravention conv = F_CPContraventions.getDetails(Browser);
		conv.NoOfImages = F_CPContraventions.imageCount(Browser);
		F_CPContraventions.clickDownload(Browser);

		//String zipFilePath = Functions.getDownloadFile(Constants.DataPath + "\\Download");
		 String zipFilePath = Functions.getDownloadFile(downloadDir);
		// Functions.getCurrDirectory();
		if (zipFilePath == null) {
			Result.SetResult(false);
			Result.SetMessage("File is not found!");
		}
		Assert.assertTrue(Result.Result, Result.Message);

		System.out.println(zipFilePath);
		CommonMethods.unzip(zipFilePath, destDir);

		F_CPVerification.verifyDownloadedFile(Browser, conv, destDir,  Result);
		// F_CPVerification.verifyXML(Browser, conv, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		if (Result.Result == true) {
			Functions.deleteFiles(downloadDir);
		}
    
	}

}
