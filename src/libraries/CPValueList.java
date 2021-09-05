package libraries;

public class CPValueList {
	public static class Message {
		public static String CreateSuccess = "{0} has been created successfully.";
		public static String UpdateSuccess = "{0} has been updated successfully.";
		public static String DeleteSuccess = "{0} has been deleted successfully.";
		public static String ConfirmDelete = "Do you want to delete the selected {0}?";
	//	public static String ConfirmDelete = "Do you want to delete the selected {0}? This action cannot be revoked.";
		public static String ExemptConfirm = "The vehicle with VRM {0} is exempt. Do you want to VOID the contravention, with reason Exempt Vehicle?";
		public static String MaximumImages = "Maximum 7 images are captured";
	}

	public enum Status {
		NEW, COMPLETED, REVIEWED, EXPORTED, VOID;
	}
	public static class ContraventionColumn {
		public static String id = "ID";
		public static String desc = "Description";
		public static String time = "Observed Time";
		public static String status = "Status";
		public static String CamId = "Camera No";
	}
	
	public static String videoRef = "http://192.168.1.83:8885/k.mp4";
	
	public static class timeFormat{
		public static String formatUK = "dd/MM/yyyy HH:mm:ss";
		public static String formatUS = "MM/dd/yyyy HH:mm:ss";
	}
	public static class dateFormat{
		public static String formatUK = "dd/MM/yyyy";
		public static String formatUS = "MM/dd/yyyy";
		public static String formatImport = "yyyy-MM-dd";
	}
	
	public static class errorMessage{
		public static String vrmError = "Vehicle Number must not be empty."; 
		public static String takeImage = "Please take 2-7 images"; 
		public static String lastContravention = "You are already in last contravention with this status";
	
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
	
	
	
	
	
}
