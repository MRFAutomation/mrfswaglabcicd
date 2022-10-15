package com.qa.listeners;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qa.base.BaseTest;
import com.qa.utils.TestUtils;
import com.qa.reports.ExtentReport;

public class TestListeners implements ITestListener {
	TestUtils utils = new TestUtils();

	public void onTestFailure(ITestResult result) {
		if (result.getThrowable() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			result.getThrowable().printStackTrace(pw);
			System.out.println(sw.toString());
//			utils.log(sw.toString());
		}

		String scrShortPath = "screenshot" + File.separator + "Android_OS12_Emulator" + File.separator
				+ new TestUtils().dateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName()
				+ File.separator + result.getName() + ".png";

		String scrCompletePath = System.getProperty("user.dir") + File.separator + scrShortPath;

		File file = new BaseTest().getDriver().getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file, new File(scrShortPath));
			Reporter.log("Error screenshot");
			Reporter.log("<a href = '" + scrCompletePath + "'><img src='" + scrCompletePath
					+ "' height='50' width='50'/></a>");

		} catch (IOException e) {
			e.printStackTrace();
		}
		ExtentReport.getTest().fail("Test case failed with exception: " + result.getThrowable(),
				MediaEntityBuilder.createScreenCaptureFromPath(scrCompletePath).build());
	}

	public void onTestStart(ITestResult result) {
		ExtentReport.startTest(result.getName(), result.getMethod().getDescription()).assignCategory("Android_Device")
				.assignAuthor("Muhammad Rana Farhan");
	}

	public void onTestSuccess(ITestResult result) {
		ExtentReport.getTest().log(Status.PASS, "Test Passed");
	}

	public void onFinish(ITestContext context) {
		ExtentReport.getExtentReporter().flush();
	}
}
