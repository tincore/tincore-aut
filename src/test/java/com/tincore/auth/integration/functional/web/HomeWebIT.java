package com.tincore.auth.integration.functional.web;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tincore.auth.integration.functional.web.support.HomePage;
import com.tincore.auth.integration.functional.web.support.LoginPage;
import com.tincore.test.support.selenium.AbstractSeleniumFunctionalWebIT;

public class HomeWebIT extends WebIT {

	@Autowired
	private HomePage homePage;

	@Test
	public void testGivenNotLoggedInThenDisplaysLogin() {
		goTo("/", homePage);

		homePage.openNavigation();
		assertThat(homePage.getHomeContainer().isDisplayed(), is(true));
		assertThat(homePage.getLoginRequestElement().isDisplayed(), is(true));
	}

	@Test
	public void testGivenNotLoggedInWhenLoginRequestThenLoginPageIsDisplayed() {
		goTo("/", homePage);

		homePage.openNavigation();
		homePage.getLoginRequestElement().click();

		loginPage.waitForVisibility();
		assertThat(loginPage.getLoginContainer().isDisplayed(), is(true));
	}

	@Test
	public void testGivenHomePageWhenAdminIsLoggedInThenAdminMenuElementsArePresent() {
		goTo("/", homePage);

		homePage.openNavigation();
		homePage.getLoginRequestElement().click();
		loginPage.doLogin("admin", "admin");

		homePage.openNavigation();

		homePage.waitForVisibility(homePage.getClientsRequestElement());
		assertThat(homePage.getClientsRequestElement().isDisplayed(), is(true));
		assertThat(homePage.getUsersRequestElement().isDisplayed(), is(true));
		assertThat(homePage.getLogoutElement().isDisplayed(), is(true));
	}

}