package libraries.productFunctions;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import libraries.Constants;
import libraries.DHLValueList;
import libraries.SeleniumBrowser;
import libraries.generalFunctions.Functions;
import libraries.objects.O_DHLIncident;

public class F_API {

	public static void loginToDHL() {
		RestAssured.baseURI = Constants.BaseUrl;
		// RestAssured.baseURI = System.getProperty("baseurl");
		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
		authScheme.setUserName("initUser");
		authScheme.setPassword("initUser");
		RestAssured.authentication = authScheme;
		Functions.waitForSeconds(1);
	}

//	public static void loginToDHLWebBrowser(SeleniumBrowser Browser) {
//
//		System.setProperty("webdriver.chrome.driver", Constants.Init_Folder + "/" + Constants.chromeDriverVer);
//		DesiredCapabilities cap = DesiredCapabilities.chrome();
//		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
////			  cap.setCapability(ChromeOptions.CAPABILITY, options);
//		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
//		cap.setJavascriptEnabled(true);
//		Browser.Driver = new ChromeDriver(cap);
//
//		// Browser.Driver.manage().window().setPosition(new Point(0, 0));
//		Browser.Driver.manage().window().maximize();
//		Browser.Driver.manage().timeouts().implicitlyWait(Constants.TimeOut, TimeUnit.SECONDS);
//		Browser.Driver.manage().timeouts().pageLoadTimeout(Constants.TimeOut, TimeUnit.SECONDS);
//		Browser.Driver.manage().timeouts().setScriptTimeout(Constants.TimeOut, TimeUnit.SECONDS);
//		Browser.Wait = new WebDriverWait(Browser.Driver, Constants.TimeOut);
//
//		F_Navigation.loginDHL(Browser, "initUser", "initUser");
//		
//	}

	public static Response createIncident(String incidentCode) {

		Date now = new Date();
		Long currentTime = Functions.getCurrentTimeinLong(now);

		RequestSpecification httpRequest = RestAssured.given();

		String bodyData = "{\"aggregatedEventDTO\":{\"aggregatedEvent\":{\"criticality\":50,\"timeStampMillis\":"
				+ currentTime + "},\"eventIds\":[]},\"incident\":{\"address\":\"\",\"incidentNbo\":\r\n" + ""
				+ incidentCode
				+ ",\"category\":null,\"city\":\"\",\"classType\":\"com.dallmeier.asa.aims.domain.Incident\",\"coWorkers\":[],\"customFields\":\r\n"
				+ "{\"additionalInfo\":{\"sortingDestination\":\"21.10.007.RE\",\"height\":\"246\",\"vip\":0,\"COD\":434.2,\"PANDate\":\"2019-05-12\",\"senderPostCode\":\"12163\",\"id\":1062582,\"EKP\":5062113638,\"productCode\":1,\"arrivedWeight\":\"2.000\",\"categorization\":{\"category\":\"\",\"originator\":\"\",\"value\":\"98035964181164751697\",\"previewimage\":\"\"},\"vipDischarge\":0,\"receiverCountry\":\"DE\",\"width\":\"256\",\"rgb\":\"Nord\",\"version\":0,\"branch\":\"7320 Hamburg\",\"receiverHouseNumber\":\"8\",\"senderStreet\":\"Schloßstr.\",\"transportInsurance\":null,\"type\":\"ParcelTrackingMetaEvent\",\"scanStation\":\"2\",\"lastStation\":142,\"senderCity\":\"Berlin\",\"updated\":1587010718533,\"description\":\"Testing\",\"criticality\":80,\"timeStampLastEvent\":1587010782000,\"shipmentId\":\"98035964181164751697\",\"receiverStreet\":\"Mohnhof\",\"senderCountry\":\"DE\",\"deliveryMethod\":\"RE\",\"expireTime\":null,\"maxDeliveryDate\":\"2019-05-15\",\"dataSource\":{\"id\":3,\"description\":\"PZ Eingang\"},\"timeStampEvent\":1587010782000,\"dataSourceLastEvent\":{\"id\":238,\"description\":\"Überweisungsauftrag CODO\"},\"senderHouseNumber\":\"110\",\"houseNumber\":8,\"locationCode\":\"21.10.1.ZBPZ-Hamburg (Allermöhe)\",\"timeStampMillis\":1587010788302,\"customer\":\"Care Concept Fachkosmetik\",\"sender\":\"Care Concept Fachkosmetik\",\"created\":1587010718533,\"shiftDate\":\"2019-05-12\",\"receiverPostCode\":\"21029\",\"receiverCity\":\"Hamburg\",\"modalityLastEvent\":{\"code\":\"OK\",\"description\":null},\"length\":\"356\",\"postCode\":\"21029\",\"streetNumber\":76,\"receiver\":\"Beauty Lounge Bergedorf Svetlana Sc\",\"parcelLocation\":\"21.1.1.PZ\",\"locationCodeLastEvent\":\"85.11.704.RE\"}},\"dispatcher\":null,\"scheduleDelete\":null,\"timeStampCreated\":1587010782000,\"timeStampFirstObserved\":"
				+ currentTime + ",\"zipCode\":\"\"}}";

		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(bodyData);

		Response response = httpRequest.request(Method.POST, "/aims2/rest/incident/remote");
		return response;

	}

	public static Response createIncident2(String incidentCode) {

		Date now = new Date();
		Long currentTime = Functions.getCurrentTimeinLong(now);

		RequestSpecification httpRequest = RestAssured.given();

		String bodyData = "{\"aggregatedEventDTO\":{\"aggregatedEvent\":{\"criticality\":50,\"timeStampMillis\":"
				+ currentTime + "},\"eventIds\":[]},\"incident\":{\"address\":\"\",\"incidentNbo\":\r\n" + ""
				+ incidentCode
				+ ",\"category\":null,\"city\":\"\",\"classType\":\"com.dallmeier.asa.aims.domain.Incident\",\"coWorkers\":[],\"customFields\":\r\n"
				+ "{\"additionalInfo\":{\"sortingDestination\":\"21.10.007.RE\",\"height\":\"246\",\"vip\":0,\"COD\":434.2,\"PANDate\":\"2019-05-12\",\"senderPostCode\":\"12163\",\"id\":1062582,\"EKP\":5062113638,\"productCode\":1,\"arrivedWeight\":\"2.000\",\"categorization\":{\"category\":\"\",\"originator\":\"\",\"value\":\"98035964181164751697\",\"previewimage\":\"\"},\"vipDischarge\":0,\"receiverCountry\":\"DE\",\"width\":\"256\",\"rgb\":\"Nord\",\"version\":0,\"branch\":\"7320 Hamburg\",\"receiverHouseNumber\":\"8\",\"senderStreet\":\"Schloßstr.\",\"transportInsurance\":null,\"type\":\"ParcelTrackingMetaEvent\",\"scanStation\":\"2\",\"lastStation\":142,\"senderCity\":\"Berlin\",\"updated\":1587010718533,\"description\":\"Testing update\",\"criticality\":80,\"timeStampLastEvent\":1587010782000,\"shipmentId\":\"98035964181164751697\",\"receiverStreet\":\"Mohnhof\",\"senderCountry\":\"DE\",\"deliveryMethod\":\"RE\",\"expireTime\":null,\"maxDeliveryDate\":\"2019-05-15\",\"dataSource\":{\"id\":3,\"description\":\"PZ Eingang\"},\"timeStampEvent\":1587010782000,\"dataSourceLastEvent\":{\"id\":238,\"description\":\"Überweisungsauftrag CODO\"},\"senderHouseNumber\":\"110\",\"houseNumber\":8,\"locationCode\":\"21.10.1.ZBPZ-Hamburg (Allermöhe)\",\"timeStampMillis\":1587010788302,\"customer\":\"Care Concept Fachkosmetik\",\"sender\":\"Care Concept Fachkosmetik\",\"created\":1587010718533,\"shiftDate\":\"2019-05-12\",\"receiverPostCode\":\"21029\",\"receiverCity\":\"Hamburg\",\"modalityLastEvent\":{\"code\":\"OK\",\"description\":null},\"length\":\"356\",\"postCode\":\"21029\",\"streetNumber\":76,\"receiver\":\"Beauty Lounge Bergedorf Svetlana Sc\",\"parcelLocation\":\"21.1.1.PZ\",\"locationCodeLastEvent\":\"85.11.704.RE\"}},\"dispatcher\":null,\"scheduleDelete\":null,\"timeStampCreated\":1587010782000,\"timeStampFirstObserved\":"
				+ currentTime + ",\"zipCode\":\"\"}}";

		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(bodyData);

		Response response = httpRequest.request(Method.POST, "/aims2/rest/incident/remote");
		return response;

	}

	public static O_DHLIncident createIncident(boolean isAutoGenerateID) {

		F_API.loginToDHL();
		
		Date now = new Date();
		Long timestamp = Functions.getCurrentTimeinLong(now);
		String incidentCode = F_DHLInbox.getRandomCode();
		String randomLastLocationCode = "85.11.704.RE";
		String randomScanStation = "142";

		randomScanStation = Functions.randomNumberString(3);

		RequestSpecification httpRequest = RestAssured.given();

		String bodyData = "{\"aggregatedEventDTO\":{\"aggregatedEvent\":{\"criticality\":50,\"timeStampMillis\":"
				+ timestamp + "},\"eventIds\":[]},\"incident\":{\"address\":\"\",\"incidentNbo\":\r\n" + ""
				+ incidentCode
				+ ",\"category\":null,\"city\":\"\",\"classType\":\"com.dallmeier.asa.aims.domain.Incident\",\"coWorkers\":[],\"customFields\":\r\n"
				+ "{\"additionalInfo\":{\"sortingDestination\":\"21.10.007.RE\",\"height\":\"246\",\"vip\":0,\"COD\":434.2,\"PANDate\":\"2019-05-12\",\"senderPostCode\":\"12163\",\"id\":1062582,\"EKP\":5062113638,\"productCode\":1,\"arrivedWeight\":\"2.000\",\"categorization\":{\"category\":\"\",\"originator\":\"\",\"value\":\"98035964181164751697\",\"previewimage\":\"\"},\"vipDischarge\":0,\"receiverCountry\":\"DE\",\"width\":\"256\",\"rgb\":\"Nord\",\"version\":0,\"branch\":\"7320 Hamburg\",\"receiverHouseNumber\":\"8\",\"senderStreet\":\"Schloßstr.\",\"transportInsurance\":null,\"type\":\"ParcelTrackingMetaEvent\",\"scanStation\":\"2\",\"lastStation\":142,\"senderCity\":\"Berlin\",\"updated\":1587010718533,\"description\":\"Testing\",\"criticality\":80,\"timeStampLastEvent\":1587010782000,\"shipmentId\":\"98035964181164751697\",\"receiverStreet\":\"Mohnhof\",\"senderCountry\":\"DE\",\"deliveryMethod\":\"RE\",\"expireTime\":null,\"maxDeliveryDate\":\"2019-05-15\",\"dataSource\":{\"id\":3,\"description\":\"PZ Eingang\"},\"timeStampEvent\":1587010782000,\"dataSourceLastEvent\":{\"id\":238,\"description\":\"Überweisungsauftrag CODO\"},\"senderHouseNumber\":\"110\",\"houseNumber\":8,\"locationCode\":\"21.10.1.ZBPZ-Hamburg (Allermöhe)\",\"timeStampMillis\":1587010788302,\"customer\":\"Care Concept Fachkosmetik\",\"sender\":\"Care Concept Fachkosmetik\",\"created\":1587010718533,\"shiftDate\":\"2019-05-12\",\"receiverPostCode\":\"21029\",\"receiverCity\":\"Hamburg\",\"modalityLastEvent\":{\"code\":\"OK\",\"description\":null},\"length\":\"356\",\"postCode\":\"21029\",\"streetNumber\":76,\"receiver\":\"Beauty Lounge Bergedorf Svetlana Sc\",\"parcelLocation\":\"21.1.1.PZ\",\"locationCodeLastEvent\":\"85.11.704.RE\"}},\"dispatcher\":null,\"scheduleDelete\":null,\"timeStampCreated\":1587010782000,\"timeStampFirstObserved\":"
				+ timestamp + ",\"zipCode\":\"\"}}";

		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(bodyData);

		Response response = httpRequest.request(Method.POST, "/aims2/rest/incident/remote");
		System.out.print(incidentCode + "\n");

	
		String time = Functions.formatDateTime(now, Constants.Dateformat);

		O_DHLIncident incident = new O_DHLIncident();
		incident.Datetime = time;
		incident.CreatedDate = now;
		incident.Status = DHLValueList.firstState;
		incident.Workflow = DHLValueList.defaultWF;
		incident.lastLocationCode = randomLastLocationCode;
		incident.scanStation = randomScanStation;

		if (isAutoGenerateID == false) {
			incident.Code = incidentCode;
		}
		
		return incident;
	}

	public static String createIncident(Long timestamp, boolean isAutoGenerateID, String lastLocation,
			int lastStation) {

		F_API.loginToDHL();
		String incidentCode = F_DHLInbox.getRandomCode();

		RequestSpecification httpRequest = RestAssured.given();

		String bodyData = "{\"aggregatedEventDTO\":{\"aggregatedEvent\":{\"criticality\":50,\"timeStampMillis\":"
				+ timestamp + "},\"eventIds\":[]},\"incident\":{\"address\":\"\",\"incidentNbo\":\r\n" + ""
				+ incidentCode
				+ ",\"category\":null,\"city\":\"\",\"classType\":\"com.dallmeier.asa.aims.domain.Incident\",\"coWorkers\":[],\"customFields\":\r\n"
				+ "{\"additionalInfo\":{\"sortingDestination\":\"21.10.007.RE\",\"height\":\"246\",\"vip\":0,\"COD\":434.2,\"PANDate\":\"2019-05-12\",\"senderPostCode\":\"12163\",\"id\":1062582,\"EKP\":5062113638,\"productCode\":1,\"arrivedWeight\":\"2.000\",\"categorization\":{\"category\":\"\",\"originator\":\"\",\"value\":\"98035964181164751697\",\"previewimage\":\"\"},\"vipDischarge\":0,\"receiverCountry\":\"DE\",\"width\":\"256\",\"rgb\":\"Nord\",\"version\":0,\"branch\":\"7320 Hamburg\",\"receiverHouseNumber\":\"8\",\"senderStreet\":\"Schloßstr.\",\"transportInsurance\":null,\"type\":\"ParcelTrackingMetaEvent\",\"scanStation\":\"2\",\"lastStation\":"
				+ lastStation
				+ ",\"senderCity\":\"Berlin\",\"updated\":1587010718533,\"description\":\"Testing\",\"criticality\":80,\"timeStampLastEvent\":1587010782000,\"shipmentId\":\"98035964181164751697\",\"receiverStreet\":\"Mohnhof\",\"senderCountry\":\"DE\",\"deliveryMethod\":\"RE\",\"expireTime\":null,\"maxDeliveryDate\":\"2019-05-15\",\"dataSource\":{\"id\":3,\"description\":\"PZ Eingang\"},\"timeStampEvent\":1587010782000,\"dataSourceLastEvent\":{\"id\":238,\"description\":\"Überweisungsauftrag CODO\"},\"senderHouseNumber\":\"110\",\"houseNumber\":8,\"locationCode\":\"21.10.1.ZBPZ-Hamburg (Allermöhe)\",\"timeStampMillis\":1587010788302,\"customer\":\"Care Concept Fachkosmetik\",\"sender\":\"Care Concept Fachkosmetik\",\"created\":1587010718533,\"shiftDate\":\"2019-05-12\",\"receiverPostCode\":\"21029\",\"receiverCity\":\"Hamburg\",\"modalityLastEvent\":{\"code\":\"OK\",\"description\":null},\"length\":\"356\",\"postCode\":\"21029\",\"streetNumber\":76,\"receiver\":\"Beauty Lounge Bergedorf Svetlana Sc\",\"parcelLocation\":\"21.1.1.PZ\",\"locationCodeLastEvent\":\""
				+ lastLocation
				+ "\"}},\"dispatcher\":null,\"scheduleDelete\":null,\"timeStampCreated\":1587010782000,\"timeStampFirstObserved\":"
				+ timestamp + ",\"zipCode\":\"\"}}";
		System.out.print(bodyData);

		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(bodyData);

		Response response = httpRequest.request(Method.POST, "/aims2/rest/incident/remote");
		System.out.print(incidentCode + "\n");

		if (isAutoGenerateID == true) {
			return null;
		} else
			return incidentCode;
	}

	public static Response cancelIncident(String incidentCode) {

		Date now = new Date();
		Long currentTime = Functions.getCurrentTimeinLong(now);

		RequestSpecification httpRequest = RestAssured.given();

		String bodyData = "{\r\n" + "\r\n" + "\"incidentNbo\": \"" + incidentCode + "\",\r\n" + "\r\n"
				+ "\"reason\": \"Cancel incident Testing\",\r\n" + "\r\n" + "\"timeStampMillis\": " + currentTime
				+ "\r\n" + "\r\n" + "}";

		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(bodyData);

		Response response = httpRequest.request(Method.POST, "/aims2/rest/incident/cancel");
		return response;

	}

}
