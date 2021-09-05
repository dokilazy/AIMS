package tests.ColcParking;

import org.testng.Assert;
import org.testng.annotations.Test;

import libraries.CPValueList;
import libraries.Constants;
import libraries.TestConfig.TestSuite;
import libraries.productFunctions.F_CPContraventions;
import libraries.productFunctions.F_CPEnformentLog;
import libraries.productFunctions.F_Navigation;
import tests.DefaultAnnotations;

public class EnforementLog extends DefaultAnnotations {
		
		
	@Test(groups = { TestSuite.Smoke })
	public void Verify_Void_contravention_in_enforcement_log() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		F_CPEnformentLog.searchByStatus(Browser, CPValueList.Status.NEW.toString());
		F_CPEnformentLog.searchByDateTime(Browser, "Today");
		
		
		Assert.assertTrue(Result.Result, Result.Message);
	}
	
	@Test(groups = { TestSuite.Smoke })
	public void Verify_Reviewed_contravention_in_enforcement_log() {
		F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
		
		F_CPContraventions.reviewContravention(Browser);
		
		
		Assert.assertTrue(Result.Result, Result.Message);
	}
	
	
}
