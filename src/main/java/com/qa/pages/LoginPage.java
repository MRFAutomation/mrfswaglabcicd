package com.qa.pages;

import com.qa.base.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BaseTest{

	@AndroidFindBy (accessibility = "test-Username") private MobileElement userNameTxtEle;
	@AndroidFindBy (accessibility = "test-Password") private MobileElement passwordTxtEle;
	@AndroidFindBy (accessibility = "test-LOGIN") private MobileElement loginBtnEle;
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView") private MobileElement errTxtEle;
	
	public LoginPage enterUsername(String usrTxt) {
		sendKeys(userNameTxtEle, usrTxt, "Enter UserName");
		return this;
	}
	
	public LoginPage enterPassword(String pwdTxt) {
		sendKeys(passwordTxtEle, pwdTxt, "Enter Password");
		return this;
	}
	
	public ProductsPage pressLoginButton() {
		click(loginBtnEle, "Press Login Button");
		return new ProductsPage();
	}
	
	public String getTxt() {
		return getAttribute(errTxtEle, "text", "Get error Text from LoginPage");
	}
	
	public ProductsPage login(String un, String pw) {
		System.out.println("LoginPage.login()");
		enterUsername(un);
		enterPassword(pw);
		return pressLoginButton();
	}

}
