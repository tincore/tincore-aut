package com.tincore.auth.integration.functional.web.support;

import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.tincore.test.support.selenium.SeleniumPage;

@SeleniumPage
public class ClientsPage extends NavbarPage {

	public ClientsPage(WebDriver driver) {
		super(driver);
	}

	public class ClientsPageAssert extends AbstractAssert<ClientsPageAssert, ClientsPage> {

		protected ClientsPageAssert(ClientsPage clientsPage) {
			super(clientsPage, ClientsPageAssert.class);
		}
	}

	@FindBy(css = "[data-sem=clients_container]")
	private WebElement clientsContainer;


	@FindBy(css = "[data-sem=client_container]")
	private List<WebElement> clientContainers;
	
	public ClientsPageAssert assertThat() {
		return new ClientsPageAssert(this);
	}
	
	public WebElement getClientsContainer() {
		return clientsContainer;
	}

	public void waitForVisibility() {
		waitForVisibility(clientsContainer);
	}

	public List<WebElement> getClientContainers() {
		return clientContainers;
	}
	
}
