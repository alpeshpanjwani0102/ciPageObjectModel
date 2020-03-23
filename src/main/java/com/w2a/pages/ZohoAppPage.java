package com.w2a.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.w2a.base.Page;
import com.w2a.pages.crm.CrmHomePage;

public class ZohoAppPage extends Page {
	
//	WebDriver driver;
//	
//	public ZohoAppPage(WebDriver driver) {
//		this.driver = driver;
//	}
	
	public CrmHomePage goToCRM() {
		click("crm_XPATH");
		return new CrmHomePage();
	}

}
