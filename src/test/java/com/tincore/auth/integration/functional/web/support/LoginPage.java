package com.tincore.auth.integration.functional.web.support;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.tincore.test.support.selenium.SeleniumPage;

@SeleniumPage
public class LoginPage extends NavbarPage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public class LoginPageAssert extends AbstractAssert<LoginPageAssert, LoginPage> {

		protected LoginPageAssert(LoginPage homePage) {
			super(homePage, LoginPageAssert.class);
		}
	}

	@FindBy(css = "[data-sem=login_container]")
	private WebElement loginContainer;

	@FindBy(css = "[data-sem=login_username]")
	private WebElement loginUsernameElement;

	@FindBy(css = "[data-sem=login_password]")
	private WebElement loginPasswordElement;

	@FindBy(css = "[data-sem=login_request]")
	private WebElement loginDoElement;

	public LoginPageAssert assertThat() {
		return new LoginPageAssert(this);
	}

	public WebElement getLoginContainer() {
		return loginContainer;
	}

	public WebElement getLoginDoElement() {
		return loginDoElement;
	}

	public void doLogin(String username, String password) {
		loginUsernameElement.sendKeys(username);
		loginPasswordElement.sendKeys(password);
		loginDoElement.click();

	}

	public void waitForVisibility() {
		waitForVisibility(loginContainer);
	}

}
