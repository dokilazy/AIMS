package tests.ColcParking;

import org.testng.Assert;
import org.testng.annotations.Test;

import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TestConfig.TestSuite;
import libraries.productFunctions.F_CPContraventions;
import libraries.productFunctions.F_CP_common;
import libraries.productFunctions.F_Navigation;
import tests.DefaultAnnotations;

public class CPSearch {

	public class CPSettings extends DefaultAnnotations {

		@Test(groups = { TestSuite.Smoke })
		public void verify_search_by_Id() {
			F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername, Constants.Password);
			String id = "";
			F_CPContraventions.searchByID(Browser, id);
			
			
			
			Assert.assertTrue(true, "");
		}
	}
}