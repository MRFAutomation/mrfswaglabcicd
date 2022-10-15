package com.qa.base;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReport;
import com.qa.utils.TestUtils;

import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class BaseTest {
	protected static ThreadLocal<AndroidDriver> driver = new ThreadLocal<AndroidDriver>();
	protected ThreadLocal<Properties> props = new ThreadLocal<Properties>();
	protected TestUtils testUtils = new TestUtils();
	protected ThreadLocal<HashMap<String, String>> strings = new ThreadLocal<HashMap<String, String>>();
	private static AppiumDriverLocalService server;
	protected ThreadLocal<String> deviceName1 = new ThreadLocal<String>();

	/* Getter and Setter for driver instance */
	public void setDriver(AndroidDriver driver1) {
		driver.set(driver1);
	}

	public AndroidDriver getDriver() {
		testUtils.log().info("getting android driver");
		return driver.get();
	}

	/* Getter and Setter for Properties instance */
	public void setProps(Properties props1) {
		props.set(props1);
	}

	public Properties getProps() {
		return props.get();
	}

	/* Getter and Setter for strings HashMap instance */
	public void setStrings(HashMap<String, String> strings1) {
		strings.set(strings1);
	}

	public HashMap<String, String> getStrings() {
		return strings.get();
	}

	/* Getter and Setter for device name */
	public void setDeviceName(String deviceName2) {
		deviceName1.set(deviceName2);
	}

	public String getDeviceName() {
		return deviceName1.get();
	}

	public BaseTest() {
		// Constructor
		PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}

	@Parameters({ "deviceType", "udid", "deviceName", "emulatorFlag" })
	@BeforeTest
	public void beforeTest(String deviceType, String udid, String deviceName,@Optional("emulatorOnly")String emulatorFlag) throws IOException {
		AndroidDriver driver;
		InputStream configis = null;
		InputStream stringis = null;
		setDeviceName(deviceName);
		Properties props = new Properties();
		try {
			testUtils.log().info("Properties object created");
			configis = getClass().getClassLoader().getResourceAsStream("config.properties");
			props.load(configis);
			setProps(props);
			testUtils.log().info("Properties file loaded");
			stringis = getClass().getClassLoader().getResourceAsStream("strings.xml");
			setStrings(testUtils.parseStringXML(stringis));
			testUtils.log().info("parseStringXML method called");

			DesiredCapabilities caps = new DesiredCapabilities();
			testUtils.log().info("DesiredCapabilities object created");
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
			if (emulatorFlag.equalsIgnoreCase("true")) {
				caps.setCapability("avd", "Pixel_4");
				caps.setCapability("avdLaunchTimeout", props.getProperty("avdTime"));
			}
			caps.setCapability("appPackage", props.getProperty("androidAppPackage"));
			caps.setCapability("appActivity", props.getProperty("androidAppActivity"));
			caps.setCapability("chromeDriverPort", props.getProperty("chromePort"));
			testUtils.log().info("Required DesiredCapabilities are set");
			URL url = new URL(getProps().getProperty("appiumURL"));

			switch (deviceType) {
			case "emulator":
				caps.setCapability(MobileCapabilityType.UDID, "udid");
				testUtils.log().info("Desired Capabilities for " + getDeviceName() + " have been set");
				driver = new AndroidDriver(url, caps);
				break;
			case "real":
				caps.setCapability(MobileCapabilityType.UDID, udid);
				testUtils.log().info("Desired Capabilities for " + getDeviceName() + " have been set");
				driver = new AndroidDriver(url, caps);
				break;
			default:
				throw new Exception("Invalid platform! - " + deviceType);
			}
			setDriver(driver);
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

	public void startAppiumServer() {
		/*
		 * server = AppiumDriverLocalService.buildService( new
		 * AppiumServiceBuilder().usingDriverExecutable(new
		 * File("C:\\Program Files\\nodejs\\node.exe")) .withAppiumJS(new
		 * File("C:\\Users\\eBricks\\AppData\\Local\\Programs\\Appium Server GUI\\resources\\app\\node_modules\\appium\\build\\lib\\main.js"
		 * )) .withIPAddress("0.0.0.0") .withArgument(GeneralServerFlag.BASEPATH,
		 * "/wd/hub").usingPort(4723) .withLogFile(new File("ServerLogs\\server.log")));
		 */
		server = AppiumDriverLocalService.buildDefaultService();
		server.start();
		server.clearOutPutStreams();
		testUtils.log().info("Appium Server Started...");
	}

	public void waitForElementVisibility(MobileElement e) {
		testUtils.log().info("waitForElementVisibility - " + e.toString());
//		testUtils.log("waitForElementVisibility - " + e.toString());
		ExtentReport.getTest().log(Status.INFO, "waitForElementVisibility - " + e.toString());
		WebDriverWait wait = new WebDriverWait(getDriver(), TestUtils.WAIT);
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
		return (MobileElement) ((FindsByAndroidUIAutomator) getDriver())
				.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
						+ ".scrollable(true)).scrollIntoView(" + "new UiSelector().description(\"test-Price\"));");
	}

	@AfterTest
	public void afterTest() {
		testUtils.log().info("afterTest - quitting driver");
		getDriver().quit();
	}

	/*
	 * @BeforeSuite public void beforeSuite() { // startAppiumServer();
	 * testUtils.log().info("Appium server called in before suite..."); }
	 */

	/*
	 * @AfterSuite(alwaysRun = true) public void afterSuite() { if
	 * (server.isRunning()) { server.stop();
	 * testUtils.log().info("Appium server stopped"); } }
	 */

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
