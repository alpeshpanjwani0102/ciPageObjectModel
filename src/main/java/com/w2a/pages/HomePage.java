package com.w2a.pages;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import com.w2a.base.Page;

public class HomePage extends Page {
	
//	WebDriver driver;
//	
//	public HomePage(WebDriver driver) {
//		this.driver = driver;
//	}
//	
	
	
	public LoginPage goToLogin() {
		
		System.out.println("inside HomePage, before clicking login");
		click("loginlinK_XPATH");
		
		return new LoginPage();
	}
	
	public void goToSignUp() {
		
		
	}
	
	public void goToLearnMore() {
		
	}
	
	public void validateFooterLinks() {
		
	}
	
	
}
