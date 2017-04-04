package com.tincore.auth.integration.functional.web.support;

import java.util.List;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.tincore.test.support.selenium.SeleniumPage;

@SeleniumPage
public class UsersPage extends NavbarPage {

	public class UsersPageAssert extends AbstractAssert<UsersPageAssert, UsersPage> {

		protected UsersPageAssert(UsersPage usersPage) {
			super(usersPage, UsersPageAssert.class);
		}
	}

	@FindBy(css = "[data-sem=users_container]")
	private WebElement usersContainer;

	@FindBy(css = "[data-sem=user_container]")
	private List<WebElement> userContainers;

	@FindBy(css = "[data-sem=user_username]")
	private WebElement userUsername;

	@FindBy(css = "[data-sem=user_password]")
	private WebElement userPassword;

	@FindBy(css = "[data-sem=user_firstname]")
	private WebElement userFirstname;

	@FindBy(css = "[data-sem=user_lastname]")
	private WebElement userLastname;

	@FindBy(css = "[data-sem=user_save]")
	private WebElement userSave;
	
	public UsersPage(WebDriver driver) {
		super(driver);
	}

	
	public UsersPageAssert assertThat() {
		return new UsersPageAssert(this);
	}

	public List<WebElement> getUserContainers() {
		return userContainers;
	}

	public WebElement getUserFirstname() {
		return userFirstname;
	}

	public WebElement getUserLastname() {
		return userLastname;
	}

	public WebElement getUserPassword() {
		return userPassword;
	}

	public WebElement getUserSave() {
		return userSave;
	}

	public WebElement getUsersContainer() {
		return usersContainer;
	}

	public WebElement getUserUsername() {
		return userUsername;
	}

	public void waitForVisibility() {
		waitForVisibility(usersContainer);
	}

}
