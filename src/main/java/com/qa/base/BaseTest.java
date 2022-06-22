package com.qa.base;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReport;
import com.qa.utils.TestUtils;

import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseTest {
	protected static AndroidDriver driver;
	protected Properties props;
	InputStream configis;
	InputStream stringis;
	protected TestUtils testUtils = new TestUtils();
	protected HashMap<String, String> strings = new HashMap<String, String>();

	public BaseTest() {
		// Constructor
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@BeforeTest
	public void beforeTest() throws IOException {

		try {
			props = new Properties();
			testUtils.log().info("Properties object created");

			configis = getClass().getClassLoader().getResourceAsStream("config.properties");
			props.load(configis);
			testUtils.log().info("Properties file loaded");
			stringis = getClass().getClassLoader().getResourceAsStream("strings.xml");

			strings = testUtils.parseStringXML(stringis);
			testUtils.log().info("parseStringXML method called");

			DesiredCapabilities caps = new DesiredCapabilities();
			testUtils.log().info("DesiredCapabilities object created");

			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_4");
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
			caps.setCapability(MobileCapabilityType.UDID, "emulator-5554");
			caps.setCapability("avd", "Pixel_4");
			caps.setCapability("avdLaunchTimeout", 180000);
			caps.setCapability("appPackage", props.getProperty("androidAppPackage"));
			caps.setCapability("appActivity", props.getProperty("androidAppActivity"));
			testUtils.log().info("Required DesiredCapabilities are set");

			URL url = new URL(props.getProperty("appiumURL"));

			driver = new AndroidDriver(url, caps);
			testUtils.log().info("AndroidDriver object created");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (configis != null) {
				testUtils.log().info("closing configis");
				configis.close();
			}
			if (stringis != null) {
				testUtils.log().info("closing stringis");
				stringis.close();
			}
		}
	}

	public void waitForElementVisibility(MobileElement e) {
		testUtils.log().info("waitForElementVisibility - " + e.toString());
		ExtentReport.getTest().log(Status.INFO, "waitForElementVisibility - " + e.toString());
		WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
		wait.until(ExpectedConditions.visibilityOf(e));
	}

	public void click(MobileElement e, String msg) {
		waitForElementVisibility(e);
		testUtils.log().info("click - " + e.toString() + ": " + msg);
		ExtentReport.getTest().log(Status.INFO, "click - " + e.toString() + ": " + msg);
		e.click();
	}

	public void sendKeys(MobileElement e, String txt, String msg) {
		waitForElementVisibility(e);
		testUtils.log().info("sendKeys - " + e.toString() + ": " + msg);
		ExtentReport.getTest().log(Status.INFO, "sendKeys - " + e.toString() + ": " + msg);
		e.sendKeys(txt);
	}

	public String getAttribute(MobileElement e, String attr, String msg) {
		waitForElementVisibility(e);
		testUtils.log().info("getAttribute - " + e.toString() + attr + ": " + msg);
		ExtentReport.getTest().log(Status.INFO, "getAttribute - " + e.toString() + attr + ": " + msg);
		return e.getAttribute(attr);
	}

	public MobileElement scrollToElement(String msg) {
		testUtils.log().info("scrollToElement - " + msg);
		ExtentReport.getTest().log(Status.INFO, "scrollToElement - " + msg);
		return (MobileElement) ((FindsByAndroidUIAutomator) driver)
				.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
						+ ".scrollable(true)).scrollIntoView(" + "new UiSelector().description(\"test-Price\"));");
	}

	public AndroidDriver getDriver() {
		testUtils.log().info("getting android driver");
		return driver;
	}

	@AfterTest
	public void afterTest() {
		testUtils.log().info("afterTest - quitting driver");
		driver.quit();
	}

	public void waitForSeconds(int timeInSeconds) {
		int timeInMilliSeconds = timeInSeconds * 1000;
		testUtils.log().info("waitForSeconds - " + timeInMilliSeconds);
		ExtentReport.getTest().log(Status.INFO, "waitForSeconds - " + timeInMilliSeconds);
		try {
			Thread.sleep(timeInMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
