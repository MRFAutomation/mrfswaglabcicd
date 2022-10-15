package com.qa.test;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.base.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;

public class LoginTest extends BaseTest {
	LoginPage loginPage;
	ProductsPage productsPage;
	JSONObject loginUsers;

	@BeforeClass
	public void beforeClass() throws IOException {
		InputStream iStream = null;
		loginPage = new LoginPage();
		try {
			iStream = getClass().getClassLoader().getResourceAsStream("loginUsers.json");
			JSONTokener tokener = new JSONTokener(iStream);
			loginUsers = new JSONObject(tokener);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (iStream != null) {
				iStream.close();
			}
		}
	}

	@Test()
	public void incorrectUser() {
		loginPage.enterUsername(loginUsers.getJSONObject("invalidUser").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
		loginPage.pressLoginButton();

		String actualTxt = loginPage.getTxt();
		String expectedTxt = getStrings().get("login_page_invalid_username_or_password_error");

		Assert.assertEquals(actualTxt, expectedTxt);
	}

	@Test
	public void incorrectPassword() {
		loginPage.enterUsername(loginUsers.getJSONObject("invalidPassword").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
		loginPage.pressLoginButton();

		String actualTxt = loginPage.getTxt();
		String expectedTxt = getStrings().get("login_page_invalid_username_or_password_error");
		Assert.assertEquals(actualTxt, expectedTxt);
	}

	@Test
	public void successfulLogin() {
		loginPage.enterUsername(loginUsers.getJSONObject("validUsernamePassword").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("validUsernamePassword").getString("password"));
		productsPage = loginPage.pressLoginButton();

		String actualTxt = productsPage.getProductsPageTitleTxt();
		String expectedTxt = getStrings().get("products_page_expected_result");
		Assert.assertEquals(actualTxt, expectedTxt);
	}

	@AfterClass
	public void afterClass() {
	}

}
