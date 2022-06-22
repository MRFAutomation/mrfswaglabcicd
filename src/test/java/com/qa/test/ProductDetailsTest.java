package com.qa.test;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.base.BaseTest;
import com.qa.base.MenuPage;
import com.qa.deeplinks.DeepLink;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;

public class ProductDetailsTest extends BaseTest {
	LoginPage loginPage;
	ProductsPage productsPage;
	InputStream iStream;
	JSONObject loginUsers;
	ProductDetailsPage productDetailsPage;
	MenuPage menuPage;
	SettingsPage settingsPage;

	@BeforeClass
	public void beforeClass() throws IOException {
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

	@Test(priority = 1)
	public void validateProdTitlePrice() {
		testUtils.log().info(" ++++++++++++++ validateProdTitlePrice ++++++++++++++ ");
		SoftAssert sa = new SoftAssert();
		productsPage = loginPage.login(loginUsers.getJSONObject("validUsernamePassword").getString("username"),
				loginUsers.getJSONObject("validUsernamePassword").getString("password"));

		String slbTitle = productsPage.getSLBTitle();
		System.out.println("SLB title: " + slbTitle);
		sa.assertEquals(slbTitle, strings.get("product_slb_title"));

		String slbPrice = productsPage.getSLBPrice();
		System.out.println("SLB price: " + slbPrice);
		sa.assertEquals(slbPrice, strings.get("product_slb_price"));

		sa.assertAll();

		settingsPage = productsPage.pressMenuBtn();
		settingsPage.pressLogoutBtn();
	}

	@Test(priority = 2)
	public void validateProdTitleDetailsPrice() {
		testUtils.log().info(" ++++++++++++++ validateProdTitleDetailsPrice ++++++++++++++ ");
		SoftAssert sa = new SoftAssert();
		productsPage = loginPage.login(loginUsers.getJSONObject("validUsernamePassword").getString("username"),
				loginUsers.getJSONObject("validUsernamePassword").getString("password"));
		productDetailsPage = productsPage.pressSLBTitle();

		String slbTitle = productDetailsPage.getSLBTitleProdDetails();
		System.out.println("SLB title: " + slbTitle);
		sa.assertEquals(slbTitle, strings.get("product_details_slb_title"));

		String slbDetails = productDetailsPage.getProdDetailsSlbDetails();
		System.out.println("SLB Details: " + slbDetails);
		sa.assertEquals(slbDetails, strings.get("product_details_slb_details"));

		scrollToElement("Methode scrollToElement called from ProductDetailsTest_validateProdTitleDetailsPrice");
		String slbPDPrice = productDetailsPage.getProdDetailsSlbPrice();
		System.out.println("SLB Price: " + slbPDPrice);
		sa.assertEquals(slbPDPrice, strings.get("product_details_slb_price"));

		sa.assertAll();

		settingsPage = productDetailsPage.pressMenuBtn();
		settingsPage.pressLogoutBtn();
	}

	@Test(priority = 3)
	public void validateProdDetailsAndPrice() {
		testUtils.log().info(" ++++++++++++++ validateProdDetailsAndPrice ++++++++++++++ ");
		SoftAssert sa = new SoftAssert();
		productsPage = loginPage.login(loginUsers.getJSONObject("validUsernamePassword").getString("username"),
				loginUsers.getJSONObject("validUsernamePassword").getString("password"));
		productDetailsPage = productsPage.pressSLBTitle();

		String slbPDTitle = productDetailsPage.getSLBTitleProdDetails();
		System.out.println("SLB title: " + slbPDTitle);
		sa.assertEquals(slbPDTitle, strings.get("product_details_slb_title"));

		String slbDetails = productDetailsPage.getProdDetailsSlbDetails();
		System.out.println("SLB Details: " + slbDetails);
		sa.assertEquals(slbDetails, strings.get("product_details_slb_details"));

		scrollToElement("Methode scrollToElement called from ProductDetailsTest_validateProdDetailsAndPrice");
		String slbPDPrice = productDetailsPage.getProdDetailsSlbPrice();
		System.out.println("SLB Price: " + slbPDPrice);
		sa.assertEquals(slbPDPrice, strings.get("product_details_slb_price"));

		productsPage = productDetailsPage.pressBackToProductsBtn();

		String slbPTitle = productsPage.getSLBTitle();
		System.out.println("SLB title: " + slbPTitle);
		sa.assertEquals(slbPTitle, strings.get("product_slb_title"));

		String slbPrice = productsPage.getSLBPrice();
		System.out.println("SLB price: " + slbPrice);
		sa.assertEquals(slbPrice, strings.get("product_slb_price"));

		sa.assertAll();

		settingsPage = productsPage.pressMenuBtn();
		settingsPage.pressLogoutBtn();
	}

	@Test(priority = 4)
	public void validateProdTitlePriceWithDeeplink() {
		testUtils.log().info(" ++++++++++++++ validateProdTitlePriceWithDeeplink ++++++++++++++ ");

		SoftAssert sa = new SoftAssert();

		DeepLink.OpenAppWith("swaglabs://swag-overview/0,5");

		String slbTitle = productsPage.getSLBTitle();
		System.out.println("SLB title: " + slbTitle);
		sa.assertEquals(slbTitle, strings.get("product_slb_title"));

		String slbPrice = productsPage.getSLBPrice();
		System.out.println("SLB price: " + slbPrice);
		sa.assertEquals(slbPrice, strings.get("product_slb_price"));

		sa.assertAll();

		settingsPage = productsPage.pressMenuBtn();
		settingsPage.pressLogoutBtn();
	}

	@Test(priority = 5)
	public void validateProdDetailsAndPriceWithDeeplink() {
		testUtils.log().info(" ++++++++++++++ validateProdDetailsAndPriceWithDeeplink ++++++++++++++");

		SoftAssert sa = new SoftAssert();

		DeepLink.OpenAppWith("swaglabs://swag-overview/0,5");

		productDetailsPage = productsPage.pressSLBTitle();

		String slbPDTitle = productDetailsPage.getSLBTitleProdDetails();
		System.out.println("SLB title: " + slbPDTitle);
		sa.assertEquals(slbPDTitle, strings.get("product_details_slb_title"));

		String slbDetails = productDetailsPage.getProdDetailsSlbDetails();
		System.out.println("SLB Details: " + slbDetails);
		sa.assertEquals(slbDetails, strings.get("product_details_slb_details"));

		scrollToElement("Methode scrollToElement called from ProductDetailsTest_validateProdDetailsAndPrice");
		String slbPDPrice = productDetailsPage.getProdDetailsSlbPrice();
		System.out.println("SLB Price: " + slbPDPrice);
		sa.assertEquals(slbPDPrice, strings.get("product_details_slb_price"));

		productsPage = productDetailsPage.pressBackToProductsBtn();

		String slbPTitle = productsPage.getSLBTitle();
		System.out.println("SLB title: " + slbPTitle);
		sa.assertEquals(slbPTitle, strings.get("product_slb_title"));

		String slbPrice = productsPage.getSLBPrice();
		System.out.println("SLB price: " + slbPrice);
		sa.assertEquals(slbPrice, strings.get("product_slb_price"));

		sa.assertAll();

		settingsPage = productsPage.pressMenuBtn();
		settingsPage.pressLogoutBtn();
	}

	@AfterClass
	public void afterClass() {
	}

}
