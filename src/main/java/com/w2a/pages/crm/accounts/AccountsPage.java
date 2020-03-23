package com.w2a.pages.crm.accounts;

import com.w2a.base.Page;
import com.w2a.pages.crm.CrmHomePage;

public class AccountsPage extends Page {
	
	public CreateAccountPage goToAddAccounts() {
		
//		Page.menu.goToAccounts();
		click("createaccountbtn_XPATH");
		return new CreateAccountPage();
		
	}
	
	public void importAccounts() {
		
	}

}
