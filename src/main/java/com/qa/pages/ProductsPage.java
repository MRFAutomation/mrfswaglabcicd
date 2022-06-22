package com.qa.pages;

import com.qa.base.MenuPage;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsPage extends MenuPage {

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView")
	private MobileElement productTitleTxtEle;

	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")
	private MobileElement slbTitle;

	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")
	private MobileElement slbPrice;

	public String getProductsPageTitleTxt() {
		return getAttribute(productTitleTxtEle, "text", "getText from ProductsPage");
	}

	public String getSLBTitle() {
		return getAttribute(slbTitle, "text", "getSLBTitle from ProductsPage");
	}

	public String getSLBPrice() {
		return getAttribute(slbPrice, "text", "getSLBPrice from ProductsPage");
	}

	public ProductDetailsPage pressSLBTitle() {
		click(slbTitle, "Press SLB Title from ProductsPage");
		return new ProductDetailsPage();
	}
}
