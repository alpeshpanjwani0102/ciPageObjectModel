package com.w2a.utilities;



import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.Page;



public class Utilities extends Page {
	
	
	// Screenshot taker
	public static String screenshotName;
	public static void captureScreenshot() throws IOException {
		
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		Date d = new Date();
		
		screenshotName = d.toString().replace(" IST 2020", "").replace(":", "_").replace(" ", "_") + ".jpg";
		
		FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+screenshotName));
		//copyFile(srcFile, new File(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+screenshotName));
	}
	// ENDS Screenshot taker ENDS 	
	
	
	// Common Dataprovider
	@DataProvider(name="dp")
	public Object[][] getData(Method m){
		
		String sheetName = m.getName();
		System.out.println(excel.getRowCount(sheetName));
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		
		Object[][] data = new Object[rows-1][1];
		Hashtable<String, String> table = null;
		
		
		for(int rowNum = 2; rowNum <= rows; rowNum++) {
			table = new Hashtable<String, String>();
			
			for(int colNum = 0; colNum < cols; colNum++) {
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				
				data[rowNum-2][0] = table;
			}
			
		}
		return data;
		
	}
	// ENDS Common Dataprovider ENDS 
	
	
//	THIS IS MOVED TO Page Class(Base class) // Soft Assertion 
//	public static void verifyEquals(String expected , String actual) throws IOException {
//		
//		try {
//			
//			Assert.assertEquals(actual, expected);
//			
//			
//		}catch(Throwable t) {
//			
//			TestUtil.captureScreenshot();
//			
//			Reporter.log("<br>"+ "Verification Failure : " + t.getMessage()+ "<br>");
//			//Reporter.log("<a target=\"_blank\" href=\"C:\\Users\\alpes\\Pictures\\Saved Pictures\\alpesh.jpg\">Screenshot</a>");
//			
//			Reporter.log("<a target=\"_blank\" href=\"+TestUtil.screenshotName+\">Screenshot</a>");
//			Reporter.log("<br>");
//			
//			test.log(LogStatus.FAIL, "Verification Failure with exception : "+ t.getMessage());
//			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
//			
//		}
//		
//	}
//	
//	// ENDS Soft Assertion ENDS 
	
	
	// Setting up RUN MODE
	public static boolean isTestRunnable(String testName, ExcelReader excel) {
		String sheetName = "test_suite";
		int rows = excel.getRowCount(sheetName);
		
		
		for(int rowNum = 2; rowNum<=rows; rowNum++) {
			String testCase = excel.getCellData(sheetName, "TCID", rowNum);
			
			if(testName.equalsIgnoreCase(testCase)) {
				String runMode= excel.getCellData(sheetName, "Runmode", rowNum);
				if(runMode.equalsIgnoreCase("Y")) 
					return true;
				else return false;
			} 
			
		}
		
		return false;
		
	}
	// ENDS Setting up RUN MODE ENDS 
	
	
}
