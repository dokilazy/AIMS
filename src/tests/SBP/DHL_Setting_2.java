package tests.SBP;

import org.testng.annotations.Test;

import java.text.MessageFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import interfaces.I_Common;
import interfaces.I_DHL_Home;
import interfaces.I_DHL_Settings;
import libraries.CPValueList;
import libraries.Constants;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.DHLValueList.Message;
import libraries.DHLValueList.dataType;
import libraries.DHLValueList.Form;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Mouse;
import libraries.productFunctions.F_DHLInbox;
import libraries.productFunctions.F_DHLSettings;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_DHLVerification;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;

public class DHL_Setting_2 extends DefaultAnnotations {

	
	@Test
	public void Verify_add_new_custom_fields_numberType() {
		String name = Functions.randomText(6);
		String UKlabel = Functions.randomText(6);
		String USlabel = Functions.randomText(6);
		String DElabel = Functions.randomText(6);
		String type = dataType.number;
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.CustomFields(Browser);
		F_DHLSettings.clickAddNew(Browser);
		F_DHLSettings.fillFieldName(Browser, name);
		F_DHLSettings.selectDataTypeForCustomField(Browser, type);
		F_DHLSettings.fillLableForUK(Browser, UKlabel);
		F_DHLSettings.fillLableForUS(Browser, USlabel);
		F_DHLSettings.fillLableForDE(Browser, DElabel);
		F_DHLSettings.clickSaveAtForm(Browser);
		F_DHLVerification.verifySuccessCreateMsg(Browser, name, Result);
		F_DHLVerification.verifyCustomField(Browser, name, type, UKlabel, USlabel, DElabel, "", Result);
		Assert.assertTrue(Result.Result, Result.Message);
		
		
		F_DHLVerification.verifyCustomFieldsAtDetails(Browser, UKlabel, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// --- Delete --
		F_DHLSettings.goToPage.CustomFields(Browser);
		F_DHLSettings.deleteCustomField(Browser, name);
	}

	@Test
	public void Verify_edit_custom_fields() {
		String name = Functions.randomText(6);
		String UKlabel = Functions.randomText(6);
		String USlabel = Functions.randomText(6);
		String DElabel = Functions.randomText(6);
		String type = dataType.string;
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.CustomFields(Browser);

		F_DHLSettings.addNewCustomField(Browser, name, type, UKlabel, USlabel, DElabel);
		F_DHLVerification.verifyCustomField(Browser, name, type, UKlabel, USlabel, DElabel, "", Result);

		String newUKlabel = Functions.randomText(6);
		String newUSlabel = Functions.randomText(6);
		String newDElabel = Functions.randomText(6);
		F_DHLSettings.goToPage.CustomFields(Browser);
		F_DHLSettings.selectCustomField(Browser, name);
		F_DHLSettings.clickEdit(Browser);
		F_DHLVerification.verifyFieldnameAndtypeNotEditable(Browser, Result);
		F_DHLSettings.updateCustomField(Browser, name, newUKlabel, newUSlabel, newDElabel);

		F_DHLSettings.clickSaveAtToolbar(Browser);
		F_DHLVerification.verifySuccessUpdateMsg(Browser, name, Result);
		F_DHLSettings.closeCurrentTab(Browser);
		F_DHLSettings.goToPage.CustomFields(Browser);
		F_DHLVerification.verifyCustomField(Browser, name, type, newUKlabel, newUSlabel, newDElabel, "", Result);
		Assert.assertTrue(Result.Result, Result.Message);
		
		
		F_DHLVerification.verifyCustomFieldsAtDetails(Browser, newUKlabel, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// --- Delete --
		F_DHLSettings.goToPage.CustomFields(Browser);
		F_DHLSettings.deleteCustomField(Browser, name);

	}

	@Test
	public void Verify_delete_custom_field() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.CustomFields(Browser);
		String name = Functions.randomText(6);
		String UKlabel = Functions.randomText(6);
		String USlabel = Functions.randomText(6);
		String DElabel = Functions.randomText(6);
		String type = dataType.datetime;

		F_DHLSettings.addNewCustomField(Browser, name, type, UKlabel, USlabel, DElabel);
		F_DHLVerification.verifyCustomField(Browser, name, type, UKlabel, USlabel, DElabel, "", Result);
		F_DHLSettings.goToPage.CustomFields(Browser);
		F_DHLSettings.selectCustomField(Browser, name);
		F_DHLSettings.clickDelete(Browser);
		F_DHLVerification.verifyDeleteConfirm(Browser, name, Result);
		F_DHLSettings.clickConfirmDelete(Browser);
		F_DHLVerification.verifySuccessDeleteMsg(Browser, name, Result);
		F_DHLVerification.verifyRowNotExist(Browser, name, Result);
		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test
	public void Verify_add_new_state_not_final_State() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.States(Browser);
		String state = Functions.randomText(6);
		String desc = Functions.randomText(30);
		F_DHLSettings.goToPage.States(Browser);
		F_DHLSettings.clickAddNew(Browser);

		F_DHLSettings.fillStateName(Browser, state);
		F_DHLSettings.fillStateDesc(Browser, desc);
		F_DHLSettings.clickSaveInline(Browser);
		F_DHLVerification.verifySuccessCreateMsg(Browser, state, Result);
		F_DHLVerification.verifyStates(Browser, state, desc, Result);
		Assert.assertTrue(Result.Result, Result.Message);
		// ----- delete data --
		F_DHLSettings.deleteRow(Browser, state);
	}

	@Test
	public void Verify_add_new_state_final_state() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);

		F_DHLSettings.goToPage.States(Browser);
		String status = Functions.randomText(6);
		String desc = Functions.randomText(30);
		F_DHLSettings.clickAddNew(Browser);

		F_DHLSettings.fillStateName(Browser, status);
		F_DHLSettings.selectFinalState(Browser, true);
		F_DHLSettings.fillStateDesc(Browser, desc);
		F_DHLSettings.clickSaveInline(Browser);
		F_DHLVerification.verifySuccessCreateMsg(Browser, status, Result);
		F_DHLVerification.verifyStates(Browser, status, desc, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// ----- delete data --
		F_DHLSettings.deleteRow(Browser, status);
	}

	@Test
	public void Verify_edit_state() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.States(Browser);
		String state = Functions.randomText(6);
		String desc = Functions.randomText(30);
		boolean isFinalState = false;
		F_DHLSettings.addNewState(Browser, state, desc, isFinalState);
		F_DHLSettings.select(Browser, state);
		F_DHLSettings.clickEdit(Browser);

		String newState = Functions.randomText(6);
		String newDesc = Functions.randomText(30);
		F_DHLSettings.fillStateName(Browser, newState);
		F_DHLSettings.fillStateDesc(Browser, newDesc);
		F_DHLSettings.clickSaveInline(Browser);

		F_DHLVerification.verifySuccessUpdateMsg(Browser, newState, Result);
		F_DHLVerification.verifyStates(Browser, newState, newDesc, Result);
		Assert.assertTrue(Result.Result, Result.Message);
		// ----- delete data --
		F_DHLSettings.deleteRow(Browser, newState);
	}

	@Test
	public void Verify_delete_state() {
		String state = Functions.randomText(6);
		String desc = Functions.randomText(30);
		boolean isFinalState = false;
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.States(Browser);
		F_DHLSettings.addNewState(Browser, state, desc, isFinalState);
		F_DHLSettings.select(Browser, state);
		F_DHLSettings.clickDelete(Browser);
		F_DHLVerification.verifyDeleteConfirm(Browser, state, Result);
		F_DHLSettings.clickConfirmDelete(Browser);
		F_DHLVerification.verifySuccessDeleteMsg(Browser, state, Result);
		F_DHLVerification.verifyRowNotExist(Browser, state, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	
	
//	@Test
//	public void Verify_add_new_role() {
//		
//		String name = Functions.randomText(6);
//		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
//		F_DHLSettings.goToPage.Roles(Browser);
//		
//		F_DHLSettings.clickAddNew(Browser);
//
//		F_DHLSettings.fillRoleName(Browser, name);
//		F_DHLSettings.clickSaveInline(Browser);
//		F_DHLVerification.verifySuccessCreateMsg(Browser, name, Result);
//		F_DHLVerification.verifyRoles(Browser, name, Result);
//		Assert.assertTrue(Result.Result, Result.Message);
//
//		// ----- delete test data --
//		F_DHLSettings.deleteRow(Browser, name);
//	}
//
//	@Test
//	public void Verify_edit_role() {
//		String role = Functions.randomText(6);
//		
//		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
//		F_DHLSettings.goToPage.Roles(Browser);
//		
//		F_DHLSettings.addNewrole(Browser, role);
//		F_DHLSettings.select(Browser, role);
//		F_DHLSettings.clickEdit(Browser);
//		F_DHLSettings.fillRoleName(Browser, role);
//		F_DHLSettings.clickSaveInline(Browser);
//		F_DHLVerification.verifySuccessUpdateMsg(Browser, role, Result);
//		F_DHLVerification.verifyRoles(Browser, role, Result);
//		Assert.assertTrue(Result.Result, Result.Message);
//
//		// ----- delete test data --
//		F_DHLSettings.deleteRow(Browser, role);
//	}
//
//	@Test
//	public void Verify_delete_role() {
//		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
//		F_DHLSettings.goToPage.Roles(Browser);
//		String role = Functions.randomText(6);
//		F_DHLSettings.addNewrole(Browser, role);
//		F_DHLSettings.select(Browser, role);
//		F_DHLSettings.clickDelete(Browser);
//		F_DHLVerification.verifyDeleteConfirm(Browser, role, Result);
//		F_DHLSettings.clickConfirmDelete(Browser);
//		F_DHLVerification.verifySuccessDeleteMsg(Browser, role, Result);
//		F_DHLVerification.verifyRowNotExist(Browser, role, Result);
//		Assert.assertTrue(Result.Result, Result.Message);
//	}
//	
//	@Test
//	public void Verify_assign_role() {
//		String initialRole = "Default role";
//		String username = "vunguyen";
//		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
//		F_DHLSettings.goToPage.Roles(Browser);
//		
//		//--add a role --
//		String role = Functions.randomText(6);
//		F_DHLSettings.addNewrole(Browser, role);
//		
//		//-- assign new role to SS user --
//		F_DHLSettings.goToPage.RoleAssignment(Browser);
//		F_DHLSettings.updateRoleForUser(Browser, username, role);
//		
//		F_DHLVerification.verifySuccessUpdateMsg(Browser, "Username", Result);
//		F_DHLVerification.verifyAssignmentRoles(Browser, username, role, Result);
//		Assert.assertTrue(Result.Result, Result.Message);
//		
//		//-- post condition --
//		F_DHLSettings.updateRoleForUser(Browser, username, initialRole);
//		F_DHLSettings.goToPage.Roles(Browser);
//		F_DHLSettings.deleteRow(Browser, role);
//		
//	}

	@Test
	public void Verify_add_new_workflow() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.Workflow(Browser);
		String wf = Functions.randomText(6);
		F_DHLSettings.clickAddNew(Browser);
		F_DHLSettings.fillWorkflowName(Browser, wf);
		String firstState = F_DHLSettings.selectRandomFirstState(Browser);
		F_DHLSettings.clickSaveAtForm(Browser);
		F_DHLVerification.verifySuccessCreateMsg(Browser, wf, Result);
		F_DHLVerification.verifyWorkflow(Browser, wf, false, firstState, Result);
		Assert.assertTrue(Result.Result, Result.Message);
		// ----------clean data ----
		F_DHLSettings.deleteWF(Browser, wf);

	}

	// @Test
	public void Verify_add_new_default_workflow() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.Workflow(Browser);
		String wf = Functions.randomText(6);
		F_DHLSettings.clickAddNew(Browser);
		F_DHLSettings.fillWorkflowName(Browser, wf);
		F_DHLSettings.selectDefaultWorkflow(Browser, true);
		String firstState = F_DHLSettings.selectRandomFirstState(Browser);
		F_DHLSettings.clickSaveAtForm(Browser);
		F_DHLVerification.verifySuccessCreateMsg(Browser, wf, Result);
		F_DHLVerification.verifyWorkflow(Browser, wf, true, firstState, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// ----------clean data ----
		F_DHLSettings.deleteWF(Browser, wf);
	}

	@Test
	public void Verify_edit_workflow() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.Workflow(Browser);
		String wf = Functions.randomText(6);
		String firststate = null;
		F_DHLSettings.addNewWorkflow(Browser, wf, firststate);

		String newwf = Functions.randomText(6);
		F_DHLSettings.select(Browser, wf);
		F_DHLSettings.clickEdit(Browser);
		F_DHLSettings.renameWF(Browser, newwf);
		String newfirstState = F_DHLSettings.selectRandomFirstStateAtEditTab(Browser);
		F_DHLSettings.clickSaveAtToolbar(Browser);
		F_DHLVerification.verifySuccessUpdateMsg(Browser, newwf, Result);
		F_DHLSettings.closeCurrentTab(Browser);

		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLSettings.goToPage.Workflow(Browser);
		F_DHLVerification.verifyWorkflow(Browser, newwf, false, newfirstState, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// ----------clean data ----
		F_DHLSettings.deleteWF(Browser, newwf);

	}

	@Test
	public void Verify_delete_workflow() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.Workflow(Browser);
		String wf = Functions.randomText(6);
		String firststate = null;
		F_DHLSettings.addNewWorkflow(Browser, wf, firststate);
		
		F_DHLInbox.gotoInboxpage(Browser);
		F_DHLSettings.goToPage.Workflow(Browser);
		F_DHLSettings.select(Browser, wf);
		F_DHLSettings.clickDelete(Browser);
		F_DHLVerification.verifyDeleteConfirm(Browser, wf, Result);
		F_DHLSettings.clickConfirmDelete(Browser);
		F_DHLVerification.verifySuccessDeleteMsg(Browser, wf, Result);
		F_DHLVerification.verifyRowNotExist(Browser, wf, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	
	@Test
	public void Verify_upload_invalidForm() {
		F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
		F_DHLSettings.goToPage.FormTemplate(Browser);
		F_DHLSettings.clickAddNew(Browser);
		String errorMsg = "The template does not contain a placeholder";
		String formName = "invalidForm.docx";
		String file_path =  Functions.getCurrDirectory() +  "\\" +  Form.invalidForm;
		System.out.printf(file_path);
		Functions.uploadfile(file_path, formName);
		F_DHLVerification.verifyErrorMsg(Browser, errorMsg, Result);
		F_DHLVerification.verifyRowNotExist(Browser, formName, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}
	
	
	
	

	
}
