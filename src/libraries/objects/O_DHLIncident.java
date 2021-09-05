package libraries.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import interfaces.I_AIMS_Home;
import interfaces.I_AIMS_Incident_Details;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.generalFunctions.Functions;
import libraries.productFunctions.F_AIMS;

public class O_DHLIncident {
	public static String Code;
	public static String Assignee;
	public static String Coworker;
	public static String Status;
	public static String Type;
	public static String Workflow;
	public static String Datetime;
	public static Date CreatedDate;
	public static String Author = Constants.GlobalAuthor;
	public static String incidentStreet;
	public static String incidentCity;
	public static String incidentZip;
	public static String incidentCoordinates;
	public static int numOfevidences;
	public static boolean IsDeleted;
	public static Map<String, String> metadata;
	public static String lastLocationCode;
	public static String scanStation;
	
}
