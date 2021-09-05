package libraries;

public class ValueList {

	public static class WindowTitle {
		public static String NoteAndTask = "Notes and Task";
		public static String InsuranceBrokerTechnology = "Insurance Broker Technology";
	}

	public static final class PopupTitle {
		public static String DeclineWarning = "Decline Warning";
	}


	public static class AIMSMessage {
		public static String CreateIncidentSuccess = "Incident has been successfully created";
		public static String UpdateIncidentSuccess = "Incident has been updated successfully.";
		public static String UploadEvidenceSuccess = "Evidence has been uploaded successfully.";
		public static String DeleteEvidenceSuccess = "Evidence has been deleted successfully.";
		public static String CreateCategorySuccess = "Incident Catalog has been created successfully.";
		public static String AssignIncidentTypeSuccess = "Incident types have been assigned";
		public static String DeleteCategorySuccess = "Incident Catalog has been deleted successfully.";
		public static String DeleteCategoryError = "Cannot delete Incident Catelog";
		public static String DeleteEvidenceError = "Can not delete system file";
		public static String UpdateIncidentTypeSuccess = "Incident type has been updated successfully.";
	}

	public static class AIMSWarning {
		public static String IncidentCoordinateWarnning = "Please enter an address or pick a location on the map.";
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
	
	//public static final String ExportLog = "Export Log";
	public static String ExportLog = "Export Log";
	public static String MapSearchKeyword = "bac";
	
}
