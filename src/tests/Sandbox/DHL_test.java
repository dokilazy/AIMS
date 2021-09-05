package tests.Sandbox;

import org.testng.annotations.Test;

import libraries.Constants;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.generalFunctions.Functions;
import libraries.productFunctions.F_DHLSettings;
import libraries.productFunctions.F_Navigation;
import tests.DefaultAnnotations;

public class DHL_test extends DefaultAnnotations {

	@Test
	public void Add_Indident_Types_for_DHL() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		
		
		Functions.waitForSeconds(5);
		
	}
}
