package com.qa.deeplinks;

import java.util.HashMap;

import com.qa.base.BaseTest;

import io.appium.java_client.android.AndroidDriver;

public class DeepLink {

	public static void OpenAppWith(String url) {
		BaseTest base = new BaseTest();
		AndroidDriver driver = base.getDriver();

		HashMap<String, String> deepUrl = new HashMap<String, String>();
		deepUrl.put("url", url);
		deepUrl.put("package", "com.swaglabsmobileapp");
		driver.executeScript("mobile: deepLink", deepUrl);
	}
}
