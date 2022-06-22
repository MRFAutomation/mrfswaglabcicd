package com.qa.base;

import com.qa.pages.SettingsPage;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MenuPage extends BaseTest {

	@AndroidFindBy(accessibility = "test-Menu")
	private MobileElement menuBtn;

	public SettingsPage pressMenuBtn() {
		click(menuBtn, "Press Menu Button from - MenuPage");
		return new SettingsPage();
	}
}
