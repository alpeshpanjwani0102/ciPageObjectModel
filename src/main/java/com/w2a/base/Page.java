package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.Utilities;

public class Page {
	
	
	    public static WebDriver driver;
	    public static Properties config = new Properties();
		public static Properties OR = new Properties();
		public static FileInputStream fis;
		public static Logger log = Logger.getLogger("devpinoyLogger");
		public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\com\\w2a\\excel\\TestData.xlsx");
		public static WebDriverWait wait;
		public ExtentReports rep = ExtentManager.getInstance();
		public static ExtentTest test;
		public static String browser;
	    public static TopMenu menu;
	    
	    /*
	     * logs
	     * property file - or and config
	     * extend report
	     * common words 
	     */
	    public Page() {
			
			if(driver == null){
				
				// Loading OR and Config properties files
				try {
					fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\com\\w2a\\properties\\config.properties");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					config.load(fis);
					log.debug("config file loaded");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("fis loaded to config");
				
				try {
					fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\com\\w2a\\properties\\OR.properties");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("second fis created");
				
				try {
					OR.load(fis);
					log.debug("OR file loaded");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("fis loaded to OR");
				// ENDS Loading OR and Config properties files ENDS
				
				// Jenkins browser filter configuration 
				if(System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
					
					browser = System.getenv("browser");
				} else {
					browser = config.getProperty("browser");
				}
				config.setProperty("browser", browser);
				// ENDS Jenkins browser filter configuration ENDS
				
			// Optimized code for launching browser	that is provided in properties file
			if(config.getProperty("browser").equalsIgnoreCase("firefox")) {
				log.debug("firefox detected!!");
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\com\\w2a\\executables\\geckodriver.exe");
				driver = new FirefoxDriver();
				System.out.println("firefox initiated!!");
			} else if(config.getProperty("browser").equalsIgnoreCase("chrome")) {
				log.debug("chrome detected!!");
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\com\\w2a\\executables\\chromedriver.exe");
			
				// Removing any pop-up, notifications, and extensions with ChromeOptions
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.notifications", 2);
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);
				options.addArguments("--diable-extensions");
				options.addArguments("--disable-infobars");
				// ENDS Removing any pop-up, notifications, and extensions with ChromeOptions ENDS
				
				driver = new ChromeDriver();
			} else if(config.getProperty("browser").equalsIgnoreCase("ie")) {
				log.debug("IE detected!!");
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\com\\w2a\\executables\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
			// ENDS Optimized code for launching browser	that is provided in properties file ENDS 
			
			driver.get(config.getProperty("testsiteurl"));
			log.debug("navigated to " + config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);
			menu = new TopMenu(driver);
			}
		}
	    
//	    public static void quit() {
//	    	driver.quit();
//	    }
	    
	    // Common KeyWords implementation 
	    // CLICK keyword
		public static void click(String locator) {
			
			if(locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
			} else if(locator.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locator))).click();
			} else if(locator.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locator))).click();
			}
			log.debug("Clicking on an element : " + locator);
			test.log(LogStatus.INFO, "Clicking on : "+locator);
		}
		// ENDS CLICK keyword ENDS 
		// TYPE Keyword
		public static void type(String locator, String value) {
			if(locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
			} else if(locator.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
			} else if(locator.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
			}
			log.debug("Typing at element : " + locator + " entered value as : " + value);
			test.log(LogStatus.INFO, "Entering : "+value+" at : " + locator);
		}
		// ENDS TYPE Keyword ENDS 
		// SELECT Keyword
		static WebElement dropdown;
		public void selectThis(String locator, String value) {
			if(locator.endsWith("_CSS")) {
				dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
			} else if(locator.endsWith("_XPATH")) {
				dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
			} else if(locator.endsWith("_ID")) {
				dropdown = driver.findElement(By.id(OR.getProperty(locator)));
			}
			Select select = new Select(dropdown);
			select.selectByVisibleText(value);
			
			log.debug("Selecting from an element : " +locator+ " value as : " + value);
			test.log(LogStatus.INFO, "Select: "+value+" from dropdown, at : " + locator);
		}
		// ENDS SELECT Keyword ENDS 
		
		// Element's existance check
		public boolean isElementPresent(By by) {
			try {
				driver.findElement(by);
				return true;
			} 
			catch (NoSuchElementException e){
				return false;
			}
		}
		// ENDS Element's existance check ENDS 
		
		// Soft Assertion
		public static void verifyEquals(String expected , String actual) throws IOException {
			try {
				Assert.assertEquals(actual, expected);
			}catch(Throwable t) {
				Utilities.captureScreenshot();
				Reporter.log("<br>"+ "Verification Failure : " + t.getMessage()+ "<br>");
				//Reporter.log("<a target=\"_blank\" href=\"C:\\Users\\alpes\\Pictures\\Saved Pictures\\alpesh.jpg\">Screenshot</a>");
				Reporter.log("<a target=\"_blank\" href=\"+ Utilities.screenshotName +\">Screenshot</a>");
				Reporter.log("<br>");
				test.log(LogStatus.FAIL, "Verification Failure with exception : "+ t.getMessage());
				test.log(LogStatus.FAIL, test.addScreenCapture(Utilities.screenshotName));
			}
		}
		
		// ENDS Soft Assertion ENDS 
	// ENDS Common KeyWords implementation ENDS 
}
