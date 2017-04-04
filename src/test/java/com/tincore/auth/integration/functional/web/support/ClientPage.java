package com.tincore.auth.integration.functional.web.support;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.tincore.test.support.selenium.SeleniumPage;

@SeleniumPage
public class ClientPage extends NavbarPage {

	public class ClientPageAssert extends AbstractAssert<ClientPageAssert, ClientPage> {

		protected ClientPageAssert(ClientPage page) {
			super(page, ClientPageAssert.class);
		}
	}

	@FindBy(css = "[data-sem=client_container]")
	private WebElement clientContainer;

	@FindBy(css = "[data-sem=client_enabled]")
	private WebElement clientEnabled;

	@FindBy(css = "[data-sem=client_clientid]")
	private WebElement clientClientId;

	@FindBy(css = "[data-sem=client_clientsecret]")
	private WebElement clientClientSecret;

	@FindBy(css = "[data-sem=client_scope]")
	private WebElement clientScope;

	@FindBy(css = "[data-sem=client_save]")
	private WebElement clientSave;

	@FindBy(css = "[data-sem=client_save_cancel]")
	private WebElement clientSaveCancel;

	@FindBy(css = "[data-sem=client_delete]")
	private WebElement clientDelete;

	@FindBy(css = "[data-sem=client_delete_cancel]")
	private WebElement clientDeleteCancel;

	public ClientPage(WebDriver driver) {
		super(driver);
	}

	public ClientPageAssert assertThat() {
		return new ClientPageAssert(this);
	}

	public WebElement getClientClientId() {
		return clientClientId;
	}

	public WebElement getClientClientSecret() {
		return clientClientSecret;
	}

	
	
	public WebElement getClientContainer() {
		return clientContainer;
	}

	public WebElement getClientDelete() {
		return clientDelete;
	}

	public WebElement getClientDeleteCancel() {
		return clientDeleteCancel;
	}

	public WebElement getClientEnabled() {
		return clientEnabled;
	}

	public WebElement getClientSave() {
		return clientSave;
	}

	public WebElement getClientSaveCancel() {
		return clientSaveCancel;
	}

	public WebElement getClientScope() {
		return clientScope;
	}

	public void waitForVisibility() {
		waitForVisibility(clientContainer);
	}

}
