package com.qa.pages;

import com.qa.base.MenuPage;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductDetailsPage extends MenuPage {

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
	private MobileElement prodDetailsSlbTitle;

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
	private MobileElement prodDetailsSlbDetails;

	@AndroidFindBy(accessibility = "test-BACK TO PRODUCTS")
	private MobileElement backProdDetailsToProductsBtn;

	@AndroidFindBy(accessibility = "test-Price")
	private MobileElement prodDetailsSLBPrice;

	public String getSLBTitleProdDetails() {
		return getAttribute(prodDetailsSlbTitle, "text", "getSLBTitleProdDetails from ProductDetailsPage");
	}

	public String getProdDetailsSlbDetails() {
		return getAttribute(prodDetailsSlbDetails, "text", "getProdDetailsSlbDetails from ProductDetailsPage");
	}

	public ProductsPage pressBackToProductsBtn() {
		click(backProdDetailsToProductsBtn, "Press pressBackToProductsBtn from ProductDetailsPage");
		return new ProductsPage();
	}

	public String getProdDetailsSlbPrice() {
		return getAttribute(prodDetailsSLBPrice, "text", "getProdDetailsSlbPrice from ProductDetailsPage");
	}
}
