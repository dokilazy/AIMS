package libraries;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class CustomFailureListener extends TestListenerAdapter {

	@Override
	public void onTestFailure(ITestResult pResult){
//		try {
//			SeleniumHelper.captureScreenShot();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e){
//			e.printStackTrace();
//		}
	}
}
