package tests.Sandbox;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import interfaces.I_CP_Settings;
import libraries.CPValueList;
import libraries.Constants;
import libraries.generalFunctions.AMQMessageProducer;
import libraries.generalFunctions.Functions;
import libraries.productFunctions.F_CPContraventions;
import libraries.productFunctions.F_CPSettings;
import libraries.productFunctions.F_CP_common;
import libraries.productFunctions.F_Navigation;
import tests.DefaultAnnotations;

public class CPTest {// extends DefaultAnnotations {

	// @Test()
	public void createVRMlist() {
		F_CP_common.randomListDVLA(1000);
	}

	@Test()
	public void createNewContravention() {
		// F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername,
		// Constants.Password);
		int n = 10;
		String user = Constants.ConvCreationUser;
		String encoderNo = Constants.site;

		// Functions.clickBySikuli("Imgs/Simulator.png");
		for (int i = 0; i < n; i++) {
			Long timeStamp = Functions.getCurrentTimeinLong();
			encoderNo = F_CP_common.getRandomEncoderNo();
			F_CPContraventions.createNewContravention(encoderNo, user, timeStamp, null, false, "");
		}
		Assert.assertTrue(true, "");
	}

	// @Test()
	public void createVoidcontravention() {
		// F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername,
		// Constants.Password);
		int n = 1;
		String user = Constants.ConvCreationUser;
		String encoderNo = "1"; // F_CP_common.getRandomEncoderNo();
		Long timeStamp = Functions.getCurrentTimeinLong();
		F_CPContraventions.createNewContravention(encoderNo, user, timeStamp, null, true, "Void reason from SeMSY");
		Assert.assertTrue(true, "");
	}

	// @Test
	public void getDate() {
		String s = Functions.getNextDate(CPValueList.dateFormat.formatUK, 5);
		System.out.println(s);
	}

	// @Test
	public void createCSV() {
		int s = 5;

		// String camera = F_CP_common.Camera(cameraEncoderNumber);
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
	}

	// @Test
	// public void selectDatePicker() {
	// F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername,
	// Constants.Password);
	// Reporter.log("The web is open and login");
	// F_CPSettings.goToSettings(Browser);
	// F_CPSettings.goToPage.VRM(Browser);
	// F_CPSettings.clickAddNew(Browser);
	//// F_CPSettings.inputExceptionDate(Browser, fromDate, toDate);
	//
	// // Functions.selectDatePicker(Browser,
	// I_CP_Settings.VRM.txt_ExceptionDateFrom, "2019", "07", "15");
	//
	// Functions.selectDatePicker(Browser, I_CP_Settings.VRM.txt_ExceptionDateFrom,
	// CPValueList.dateFormat.formatUS, "06/16/2019") ;
	// }

	//@Test
	public void createTopic() {

		String user = "initUser";
		String encoderNo = "15";

		Long timeStamp = Functions.getCurrentTimeinLong();
		encoderNo = F_CP_common.getRandomEncoderNo();
		try {
			String msg = F_CPContraventions.generateJsonMsg(encoderNo, user, timeStamp, null, false, "");
			//System.out.println(msg);
			AMQMessageProducer.createTopic(msg);

		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
