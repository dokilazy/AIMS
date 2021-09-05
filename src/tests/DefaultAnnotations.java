package tests;

import libraries.CPValueList;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.TestConfig;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Log;
import libraries.productFunctions.F_DHLSettings;
import libraries.productFunctions.F_Navigation;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.w3c.dom.Element;

public class DefaultAnnotations {

	public TCResult Result;
	public SeleniumBrowser Browser;

	@SuppressWarnings("deprecation")
	@BeforeMethod(groups = { TestConfig.Initialization }, alwaysRun = true)
	@Parameters({"remote", "browser" })
	public void beforeMethod(@Optional("") String pIsRemote, @Optional("") String pBrowser,
			Method pTestMethod) throws MalformedURLException {
		DOMConfigurator.configure("log4j.xml");
		Browser = new SeleniumBrowser();
		// if (Constants.Browsertype.equals("IE")) {
		if (pBrowser.equals("IE")) {
			if (pIsRemote.equals("") || pIsRemote.toUpperCase().equals("NO")) {
				System.setProperty("webdriver.ie.driver", Constants.Init_Folder + "/IEDriverServer.exe");
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
				dc.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
				dc.setJavascriptEnabled(true);
				Browser.Driver = new InternetExplorerDriver(dc);
			} else {
				DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
				dc.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
				dc.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
				dc.setJavascriptEnabled(true);
				String node = "http://10.1.67.30:4444/wd/hub";
				Browser.Driver = new RemoteWebDriver(new URL(node), dc);
			}
		}

		// else if (Constants.Browsertype.equals("firefox")) {
		else if (pBrowser.equals("firefox")) {
			if (pIsRemote.equals("") || pIsRemote.toUpperCase().equals("NO")) {

				System.setProperty("webdriver.gecko.driver", Constants.Init_Folder + "/geckodriver.exe");
				WebDriverManager.firefoxdriver().setup();
				Browser.Driver = new FirefoxDriver();
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
				dc.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
				dc.setJavascriptEnabled(true);
			}

			else {
				
				System.out.println("Start running Firefox");
				String node = "http://192.168.1.92:35861/wd/hub";
				DesiredCapabilities cap = DesiredCapabilities.firefox();

				cap.setBrowserName("firefox");
				cap.setPlatform(Platform.WIN10);
				System.setProperty("webdriver.gecko.driver", Constants.Init_Folder + "/geckodriver.exe");

				Browser.Driver = new RemoteWebDriver(new URL(node), cap);
			}
		}

		// else if (Constants.Browsertype.equals("chrome")) {
		else if (pBrowser.equals("chrome") || pBrowser.equals("")) {

			if (pIsRemote.equals("") || pIsRemote.toUpperCase().equals("NO")) {
				System.out.print(pIsRemote);

				// String downloadFilepath = Constants.DataPath + "/Download";
				String downloadFilepath = Functions.getCurrDirectory() + "\\" + Constants.DownloadFilePath;
				// System.out.print(downloadFilepath);
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.default_directory", downloadFilepath);

				System.setProperty("webdriver.chrome.driver", Constants.Init_Folder + "/chromedriver.exe");
				// WebDriverManager.chromedriver().setup(); //--- using lib
				ChromeOptions options = new ChromeOptions();

				options.addArguments("disable-infobars");
				
				// ---- Enable headless option  ----
//				 options.addArguments("test-type");
//				 options.addArguments("--headless", "window-size=1920,1080", "--no-sandbox");
				 
				options.setExperimentalOption("prefs", chromePrefs);
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				cap.setCapability(ChromeOptions.CAPABILITY, options);
				cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
				cap.setJavascriptEnabled(true);

				Browser.Driver = new ChromeDriver(cap);
				// Browser.Driver = new ChromeDriver(options);

			} else {
				System.out.println("Start running Chrome");
				String nodeURL = "http://192.168.1.92:3103/wd/hub";
				
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setBrowserName("chrome");
				cap.setPlatform(Platform.WIN10);
		
				System.setProperty("webdriver.chrome.driver", Constants.Init_Folder + "/chromedriver.exe");

				Browser.Driver = new RemoteWebDriver(new URL(nodeURL), cap);

				System.out.print("OPEN CHROME FROM NODE SUCCESS");

			}
		}
		// Browser.Driver.manage().window().setPosition(new Point(0, 0));
		Browser.Driver.manage().window().maximize();
		Browser.Driver.manage().timeouts().implicitlyWait(Constants.TimeOut, TimeUnit.SECONDS);
		Browser.Driver.manage().timeouts().pageLoadTimeout(Constants.TimeOut, TimeUnit.SECONDS);
		Browser.Driver.manage().timeouts().setScriptTimeout(Constants.TimeOut, TimeUnit.SECONDS);

		Browser.Wait = new WebDriverWait(Browser.Driver, Constants.TimeOut);
		// Constants.CurrentHandle.add(Browser.Driver.getWindowHandle());

		Result = new TCResult();

		System.out.println("Test case: " + pTestMethod.getName() + ".");
	}

	@AfterMethod(groups = { TestConfig.Initialization })
	public void afterMethod(ITestResult pResult) {

		switch (pResult.getStatus()) {
		case ITestResult.SUCCESS:
			System.out.println("Result: PASSED.");
			break;
		case ITestResult.FAILURE:
			System.out.println("Result: FAILED.");
			Browser.takeScreenshot(pResult.getTestClass().getName(), pResult.getName(), LocalTime.now());

			break;
		case ITestResult.SKIP:
			System.out.println("Result: SKIPPED.");
			break;
		}

		try {
			Browser.Driver.close();
			Browser.Driver.quit();
		} catch (Exception e) {

		}

	}

	@BeforeSuite(groups = { TestConfig.Initialization })
	public void beforeTest() {
		try {
			File inputFile = new File(Constants.Init_Folder + "/Init.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);

			NodeList nList = doc.getElementsByTagName("Global");
			Constants.Browsertype = ((Element) nList.item(0)).getElementsByTagName("Browser").item(0).getTextContent();
			Constants.BaseUrl = ((Element) nList.item(0)).getElementsByTagName("URL").item(0).getTextContent();
			if (System.getProperty("Environment") != null) {
				Constants.BaseUrl = System.getProperty("Environment");
				// System.out.println(System.getProperty("Environment"));
			}
			Constants.GlobalUsername = ((Element) nList.item(0)).getElementsByTagName("Username").item(0)
					.getTextContent();
			Constants.Password = ((Element) nList.item(0)).getElementsByTagName("Password").item(0).getTextContent();
			// Constants.Broker = ((Element)
			// nList.item(0)).getElementsByTagName("Broker").item(0).getTextContent();
			// Constants.ProcessOwner = ((Element)
			// nList.item(0)).getElementsByTagName("ProcessOwner").item(0)
			// .getTextContent();

			Constants.GlobalFullName = ((Element) nList.item(0)).getElementsByTagName("FullName").item(0)
					.getTextContent();
			Constants.GlobalAuthor = ((Element) nList.item(0)).getElementsByTagName("Author").item(0).getTextContent();

			Constants.Dateformat = CPValueList.dateFormat.formatUK;
			Constants.DateTimeformat = CPValueList.timeFormat.formatUK;
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean IsResetData = false;

		if (IsResetData == true) {
			// Clean required comment and input custom fields in the Settings
			Browser = new SeleniumBrowser();
			Browser.openNewWebDriver();
			F_Navigation.loginDHL(Browser, Constants.GlobalUsername, Constants.Password);
			F_DHLSettings.goToPage.States(Browser);

			try {
				Browser.Driver.close();
				Browser.Driver.quit();
			} catch (Exception e) {
			}

		}
	}

	@AfterSuite(groups = { TestConfig.Initialization })
	public void afterTest() {
		Browser.Driver.quit();
	}

}