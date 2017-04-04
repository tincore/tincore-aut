package com.tincore.auth.integration.functional.web;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tincore.auth.integration.functional.web.support.LoginPage;
import com.tincore.auth.integration.functional.web.support.UserRegisterPage;
import com.tincore.auth.server.service.UserService;
import com.tincore.test.support.selenium.AbstractSeleniumFunctionalWebIT;

public class UserRegisterWebIT extends WebIT {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRegisterPage userRegisterPage;

	@Autowired
	private LoginPage loginPage;

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testGivenPageThenDisplaysInputFields() {
		goTo("/user/register", userRegisterPage);
		
		assertThat(userRegisterPage.getUserRegisterContainer().isDisplayed(), is(true));
		assertThat(userRegisterPage.getUserRegisterUsername().isDisplayed(), is(true));
		assertThat(userRegisterPage.getUserRegisterPassword().isDisplayed(), is(true));
		assertThat(userRegisterPage.getUserRegisterFirstname().isDisplayed(), is(true));
		assertThat(userRegisterPage.getUserRegisterLastname().isDisplayed(), is(true));
		assertThat(userRegisterPage.getUserRegisterAction().isDisplayed(), is(true));
	}

	@Test
	public void testGivenPageWhenInputValidDataThenCreatesNewUserAndDisplaysHomePage() {
		goTo("/user/register", userRegisterPage);

		String username = "user" + System.currentTimeMillis();
		String password = "pass" + System.currentTimeMillis();

		userRegisterPage.getUserRegisterUsername().sendKeys(username);
		userRegisterPage.getUserRegisterPassword().sendKeys(password);
		userRegisterPage.getUserRegisterFirstname().sendKeys("fname");
		userRegisterPage.getUserRegisterLastname().sendKeys("lname");
		userRegisterPage.getUserRegisterAction().click();

		assertThat(userService.getUser(username), notNullValue());

		assertThat(loginPage.getLoginContainer().isDisplayed(), is(true));
	}

	@Test
	public void testGivenPageWhenInputDuplicateUsernameThenDisplaysError() {
		goTo("/user/register", userRegisterPage);

		userRegisterPage.getUserRegisterUsername().sendKeys("admin");
		userRegisterPage.getUserRegisterPassword().sendKeys("admin");
		userRegisterPage.getUserRegisterFirstname().sendKeys("fname");
		userRegisterPage.getUserRegisterLastname().sendKeys("lname");
		userRegisterPage.getUserRegisterAction().click();

		assertThat(userRegisterPage.getUserRegisterContainer().isDisplayed(), is(true));
		assertThat(userRegisterPage.getErrorContainer().isDisplayed(), is(true));

	}

	
	@Test
	public void testGivenPageWhenInputTooShortPasswordThenDisplaysError() {
		goTo("/user/register", userRegisterPage);

		String username = "user" + System.currentTimeMillis();
		userRegisterPage.getUserRegisterUsername().sendKeys(username);
		userRegisterPage.getUserRegisterPassword().sendKeys("x");
		userRegisterPage.getUserRegisterFirstname().sendKeys("fname");
		userRegisterPage.getUserRegisterLastname().sendKeys("lname");
		userRegisterPage.getUserRegisterAction().click();

		assertThat(userRegisterPage.getUserRegisterContainer().isDisplayed(), is(true));
		assertThat(userRegisterPage.getErrorContainer().isDisplayed(), is(true));

	}

}