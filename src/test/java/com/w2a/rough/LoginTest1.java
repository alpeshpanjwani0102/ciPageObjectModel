package com.w2a.rough;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.w2a.base.Page;
import com.w2a.pages.HomePage;
import com.w2a.pages.LoginPage;
import com.w2a.pages.ZohoAppPage;
import com.w2a.pages.crm.CrmHomePage;
import com.w2a.pages.crm.accounts.AccountsPage;
import com.w2a.pages.crm.accounts.CreateAccountPage;

public class LoginTest1  {
	
	public static void main(String[] args) {
		
//		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
//		
//		WebDriver driver = new ChromeDriver();
//		driver.get("https://www.zoho.com/");
//		driver.manage().window().maximize();
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		HomePage home = new HomePage();
		LoginPage login = home.goToLogin();
		ZohoAppPage zp = login.doLogin("andypaage@gmail.com", "2002watsup?Zo");
		CrmHomePage chp = zp.goToCRM();
		
		AccountsPage ap = Page.menu.goToAccounts();
//		CreateAccountPage cap = ap.GoToCreateAccounts();
//		cap.createAccount();
	}

}
