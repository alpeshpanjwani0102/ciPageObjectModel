package com.w2a.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.Page;
import com.w2a.utilities.Utilities;


public class CustomListeners extends Page implements ITestListener {

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
		test = rep.startTest(result.getName().toUpperCase());
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		//Reporter.log("<a target=\"_blank\" href=\"C:\\Users\\alpes\\Pictures\\Saved Pictures\\alpesh.jpg\">Screenshot</a>");
		
		test.log(LogStatus.PASS, result.getName()+" PASS");
		rep.endTest(test);
		rep.flush();
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		
		try {
			System.out.println("about to take screenshot");
			Utilities.captureScreenshot();
			System.out.println("screenshot taken!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		test.log(LogStatus.FAIL, result.getName()+" Failed with exception: "+ result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(Utilities.screenshotName));
		
		
		Reporter.log("Test failed, Capturing screenshot");
		//Reporter.log("<a target=\"_blank\" href=\"C:\\Users\\alpes\\Pictures\\Saved Pictures\\alpesh.jpg\">Screenshot</a>");
		
		Reporter.log("<a target=\"_blank\" href=\"+Utilities.screenshotName+\">Screenshot</a>");
		
		rep.endTest(test);
		rep.flush();
		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
