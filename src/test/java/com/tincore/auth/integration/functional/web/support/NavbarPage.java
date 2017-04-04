package com.tincore.auth.integration.functional.web.support;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.tincore.test.support.selenium.SeleniumPageEnhanced;

public abstract class NavbarPage implements SeleniumPageEnhanced {

	@FindBy(css = "[data-sem=login_request]")
	private WebElement loginRequestElement;

	@FindBy(css = "[data-sem=logout_request]")
	private WebElement logoutElement;

	@FindBy(css = "[data-sem=users_request]")
	private WebElement usersRequestElement;

	@FindBy(css = "[data-sem=clients_request]")
	private WebElement clientsRequestElement;

	@FindBy(css = "[data-sem=h2_console_request]")
	private WebElement h2ConsoleRequestElement;

	@FindBy(css = "[data-sem=navigation_toggle]")
	private WebElement navigationToggleElement;

	@FindBy(css = "[data-sem=navigation_actions_container]")
	private WebElement navigationActionsContainer;

	@FindBy(css = "[data-sem=navigation_menu_request]")
	private WebElement navigationMenuRequestElement;

	@FindBy(css = "[data-sem=navigation_menu_container]")
	private WebElement navigationMenuContainer;

	@FindBy(css = "[data-sem=navigation_menu]")
	private WebElement navigationMenu;

	@FindBy(css = "[data-sem=user_create_request]")
	private WebElement userCreateRequest;

	@FindBy(css = "[data-sem=user_delete_request]")
	private WebElement userDeleteRequest;

	@FindBy(css = "[data-sem=client_create_request]")
	private WebElement clientCreateRequest;

	@FindBy(css = "[data-sem=client_delete_request]")
	private WebElement clientDeleteRequest;
	
	private WebDriver driver;

	public NavbarPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getClientCreateRequest() {
		return clientCreateRequest;
	}

	public WebElement getClientDeleteRequest() {
		return clientDeleteRequest;
	}

	public WebElement getClientsRequestElement() {
		return clientsRequestElement;
	}

	@Override
	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getH2ConsoleRequestElement() {
		return h2ConsoleRequestElement;
	}

	public WebElement getLoginRequestElement() {
		return loginRequestElement;
	}

	public WebElement getLogoutElement() {
		return logoutElement;
	}

	public WebElement getNavigationActionsContainer() {
		return navigationActionsContainer;
	}

	public WebElement getNavigationMenu() {
		return navigationMenu;
	}

	public WebElement getNavigationMenuContainer() {
		return navigationMenuContainer;
	}

	public WebElement getNavigationMenuRequestElement() {
		return navigationMenuRequestElement;
	}

	public WebElement getNavigationToggleElement() {
		return navigationToggleElement;
	}

	public WebElement getUserCreateRequest() {
		return userCreateRequest;
	}

	public WebElement getUserDeleteRequest() {
		return userDeleteRequest;
	}

	public WebElement getUsersRequestElement() {
		return usersRequestElement;
	}

	public void openNavigation() {
		if (!navigationMenuContainer.isDisplayed()) {
			navigationToggleElement.click();
		}
		try {
			if (!navigationMenu.isDisplayed()) {
				waitForVisibility(navigationMenuRequestElement);
				navigationMenuRequestElement.click();
			}
		} catch (NoSuchElementException e) {
			// ignore
		}
	}
}
