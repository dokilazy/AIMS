package libraries;

public class Message {
	public static class AttributeMaintenance {
		public static String AttValueConfigTitle = "Attribute Value Configuration";
		public static String AttFieldConfigTitle = "Attribute Field Configuration";
		public static String EditAttValueMsg = "Would you like to Edit Attribute Values?";
		public static String TerminateAttFieldMsg = "This will terminate this Attribute Field (and all associated Attribute Values), do you wish to continue?";
		public static String TerminateAttValueMsg = "This will terminate this Attribute Field value, do you wish to continue?";
		public static String ExpDateEarlierThanEffDateMsg = "The expiry date cannot be earlier than the Effective Date";
	}

	public static class NoteAndTask {
		public static String EmailNotificationTitle = "Email Notification";
		public static String EmailNotificationMessage = "DO YOU WANT TO SEND A NOTIFICATION EMAIL TO THE ASSIGNED CONTACT?";
		public static String CompleteTaskTitle = "Complete Task";
		public static String CompleteTaskMessage = "Are you sure you wish to Close this task";
		public static String ChangeAssignedUser = "The assigned user has been changed. Do you want to send a notification email to the new assigned contact?";
	}

	public static class ContactItem {
		public static String DeleteContactPopupTitle = "Edit Contact Item";
		public static String DeleteContactPopupMessage = "Are you sure you wish to delete this item?";
		public static String AddressSavedSuccessfully = "Address saved successfully.";
		public static String AddressUpdatedSuccessfully = "Address updated successfully.";
		public static String ContactSavedSuccessfully = "Contact Items saved/updated successfully";
		public static String ContactDeletedSuccessfully = "Contact Item deleted successfully";
		public static String UpdatedSuccessfully = "Unable to delete the selected address. It has been referenced by one or more invoices.";
		public static String DeleteAddressPopupTitle = "Delete Address";
		public static String DeleteAddressPopupMessage = "Are you sure you wish to delete this item?";
		public static String DeleteSuccessfully = "Address deleted successfully.";
	}

	public static String ValidationErrorTitle = "Validation Error";
	public static String InfoTitle = "Info";
	public static String SuccessTitle = "Success";
	public static String SavedSuccessfully = "Saved successfully";
	
	public static class ErrorWarning{
		public static String EvidenceCoordinateError = "Please enter an address or pick a location on the map.";
	}
	
	
	
}
