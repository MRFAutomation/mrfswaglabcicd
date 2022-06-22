package com.qa.pages;

import com.qa.base.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class SettingsPage extends BaseTest {

	@AndroidFindBy(accessibility = "test-LOGOUT")
	private MobileElement logoutBtn;

	public LoginPage pressLogoutBtn() {
		click(logoutBtn, "Press Logout Button from - SettingsPage");
		return new LoginPage();
	}
}
