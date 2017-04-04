package com.tincore.auth.integration.functional.web.support;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

import com.tincore.test.support.selenium.SeleniumPage;

@SeleniumPage
public class UserRegisterPage extends NavbarPage {

	public class UserRegisterPageAssert extends AbstractAssert<UserRegisterPageAssert, UserRegisterPage> {

		protected UserRegisterPageAssert(UserRegisterPage registerUserPage) {
			super(registerUserPage, UserRegisterPageAssert.class);
		}
	}

	@FindBy(css = "[data-sem=user_register_container]")
	private WebElement userRegisterContainer;

	@FindBy(css = "[data-sem=user_register_username]")
	private WebElement userRegisterUsername;

	@FindBy(css = "[data-sem=user_register_password]")
	private WebElement userRegisterPassword;

	@FindBy(css = "[data-sem=user_register_firstname]")
	private WebElement userRegisterFirstname;

	@FindBy(css = "[data-sem=user_register_lastname]")
	private WebElement userRegisterLastname;

	@FindBy(css = "[data-sem=user_register]")
	private WebElement userRegisterAction;

	@FindBy(css = "[data-sem=error_container]")
	private WebElement errorContainer;

	
	public UserRegisterPage(@Autowired WebDriver driver) {
		super(driver);
	}

	public UserRegisterPageAssert assertThat() {
		return new UserRegisterPageAssert(this);
	}

	public WebElement getUserRegisterAction() {
		return userRegisterAction;
	}

	public WebElement getUserRegisterContainer() {
		return userRegisterContainer;
	}

	public WebElement getUserRegisterFirstname() {
		return userRegisterFirstname;
	}

	public WebElement getUserRegisterLastname() {
		return userRegisterLastname;
	}

	public WebElement getUserRegisterPassword() {
		return userRegisterPassword;
	}

	public WebElement getUserRegisterUsername() {
		return userRegisterUsername;
	}
	
	public WebElement getErrorContainer() {
		return errorContainer;
	}
	
	public void waitForVisibility() {
		waitForVisibility(userRegisterContainer);
	}

}
