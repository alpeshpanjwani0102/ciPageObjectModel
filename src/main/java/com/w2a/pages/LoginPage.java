package com.w2a.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.w2a.base.Page;

public class LoginPage extends Page {
	
//	WebDriver driver;
//	
//	public LoginPage(WebDriver driver) {
//		this.driver = driver;
//	}
	
	public ZohoAppPage doLogin(String username, String password) {
		type("email_XPATH", username);
		click("nextbtn_XPATH");
		type("password_XPATH", password);
		click("signinbtn_XPATH");
		return new ZohoAppPage();
		
		}

}
