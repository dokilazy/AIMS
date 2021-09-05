package libraries;

import java.text.MessageFormat;

import interfaces.I_Common;
import libraries.verificationFunctions.F_GeneralVerification;

public class DHLValueList {
		public static class Message {
		public static String CreateSuccess = "{0} has been created successfully.";
		public static String UpdateSuccess = "{0} has been updated successfully.";
		public static String DeleteSuccess = "{0} has been deleted successfully.";
		public static String UndeleteSuccess = "{0} has been undeleted successfully.";
		public static String AssignSuccess = "{0} has been assigned successfully";
		public static String CreateFormSuccess = "Form has been created and saved as evidence.";

	}
	public static class Warning {
		public static String IncidentCoordinateWarnning = "Please enter an address or pick a location on the map.";
		public static String DeleteCategoryError = "Cannot delete Incident Catelog";
		public static String DeleteEvidenceError = "Can not delete system file";
		public static String ConfirmDelete = "Do you want to delete the selected {0}? This action cannot be revoked.";
		public static String ConfirmDeleteIncident = "Do you want to delete the selected incident?";
		public static String ConfirmChangeIncidentType = "The state of incident will be reset to first state. Are you sure to update incident type?";
	}
	
	public static class timeFormat{
		public static String formatUK = "dd/MM/yyyy HH:mm:ss";
		public static String formatUS = "MM/dd/yyyy HH:mm:ss";
	}
	public static class dateFormat{
		public static String formatUK = "dd/MM/yyyy";
		public static String formatUS = "MM/dd/yyyy";
		public static String formatImport = "yyyy-MM-dd";
	}
	
	public static class EvidenceType{
		public static String jpeg = "image/jpeg";
		public static String png =  "image/png";
		public static String mp4 = "video/mp4";
		public static String json =  "application/json";
	}
	public static class UploadType{
		public static String image = "IMAGE";
		public static String video = "VIDEO";
		public static String any = "ANY";
	}
	
	public static class pageSize{
		public static String s5 = "5";
		public static String s10 = "10";
		public static String s20 = "20";
		public static String s80 = "80";
	}
	public static class dateTimeOption{
		public static String today = "Today";
		public static String last24hours = "Last 24 hours";
		public static String thisweek = "This week";
		
	}
	public static class dataType{
		public static String string = "String";
		public static String number = "Number";
		public static String truefalse = "Boolean";
		public static String list = "Enum";
		public static String timestamp = "TimeStamp";
		public static String datetime = "DateTime";
		public static String geo = "Geometry";
		
	}
	
	public static String firstState = "New";
	public static String finalState = "Closed";
	public static String defaultWF = "Default_1";
	public static String incidentNbo_notFirstState = "8888888888";
	
	public static class Form{
		public static String validForm = "Test Data\\Form\\valid";
		//public static String invalidForm = "D:\\QC\\Automation\\Framework\\myFW\\Test Data\\Form\\invalid";
		
		public static String invalidForm = "Test Data\\Form\\invalid";
		public static String templateName = "Anlage 3_zur KBV Sicherheit PeP_Erfordernis Bildauswertung_AutoTest.docx";
	}
	
	
	
	
	
	
}
