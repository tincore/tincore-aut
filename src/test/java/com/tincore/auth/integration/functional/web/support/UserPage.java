package com.tincore.auth.integration.functional.web.support;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.tincore.test.support.selenium.SeleniumPage;

@SeleniumPage
public class UserPage extends NavbarPage {

	public UserPage(WebDriver driver) {
		super(driver);
	}

	public class UserPageAssert extends AbstractAssert<UserPageAssert, UserPage> {

		protected UserPageAssert(UserPage homePage) {
			super(homePage, UserPageAssert.class);
		}
	}

	@FindBy(css = "[data-sem=user_container]")
	private WebElement userContainer;

	@FindBy(css = "[data-sem=user_enabled]")
	private WebElement userEnabled;

	@FindBy(css = "[data-sem=user_username]")
	private WebElement userUsername;

	@FindBy(css = "[data-sem=user_password_new]")
	private WebElement userPassword;

	@FindBy(css = "[data-sem=user_firstname]")
	private WebElement userFirstname;

	@FindBy(css = "[data-sem=user_lastname]")
	private WebElement userLastname;
	
	@FindBy(css = "[data-sem=user_save]")
	private WebElement userSave;

	@FindBy(css = "[data-sem=user_save_cancel]")
	private WebElement userSaveCancel;

	@FindBy(css = "[data-sem=user_delete]")
	private WebElement userDelete;

	@FindBy(css = "[data-sem=user_delete_cancel]")
	private WebElement userDeleteCancel;

	
	
	public WebElement getUserEnabled() {
		return userEnabled;
	}

	public WebElement getUserPassword() {
		return userPassword;
	}

	public WebElement getUserFirstname() {
		return userFirstname;
	}

	public WebElement getUserLastname() {
		return userLastname;
	}

	public WebElement getUserSaveCancel() {
		return userSaveCancel;
	}

	public WebElement getUserDelete() {
		return userDelete;
	}

	public WebElement getUserDeleteCancel() {
		return userDeleteCancel;
	}

	public UserPageAssert assertThat() {
		return new UserPageAssert(this);
	}

	public WebElement getUserContainer() {
		return userContainer;
	}

	public WebElement getUserSave() {
		return userSave;
	}

	public void waitForVisibility() {
		waitForVisibility(userContainer);
	}

	public WebElement getUserUsername() {
		return userUsername;
	}

}
