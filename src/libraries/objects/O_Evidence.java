package libraries.objects;

import java.util.Date;
import libraries.Constants;
import libraries.SeleniumBrowser;

public class O_Evidence {

	public static String Criticality;
	public static String Type;
	public static String Desc;
	public static Date Observedon;
	public static String Author = Constants.GlobalAuthor;
	public static Date Uploadedon;
	public static String Camera;
	public static String Location;


	public O_Evidence(String ptype, String pDesc, Date pObservedon) {
		Date now = new Date();
		Uploadedon = now;
		Observedon = pObservedon;
		Type = ptype;
		Desc = pDesc;
	}
	
	public void selectEvidence(SeleniumBrowser Browser) {

	}

}