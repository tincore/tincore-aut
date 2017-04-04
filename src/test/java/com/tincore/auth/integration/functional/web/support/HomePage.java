package com.tincore.auth.integration.functional.web.support;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

import com.tincore.test.support.selenium.SeleniumPage;

@SeleniumPage
public class HomePage extends NavbarPage {

	public class HomePageAssert extends AbstractAssert<HomePageAssert, HomePage> {

		protected HomePageAssert(HomePage homePage) {
			super(homePage, HomePageAssert.class);
		}
	}

	@FindBy(css = "[data-sem=home_container]")
	@CacheLookup
	private WebElement homeContainer;

	public HomePage(@Autowired WebDriver driver) {
		super(driver);
	}

	public HomePageAssert assertThat() {
		return new HomePageAssert(this);
	}

	public WebElement getHomeContainer() {
		return homeContainer;
	}
	
	public void waitForVisibility() {
		waitForVisibility(homeContainer);
	}

}
