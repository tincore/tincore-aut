package com.tincore.auth.integration.functional.web;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tincore.auth.integration.functional.web.support.ClientPage;
import com.tincore.auth.integration.functional.web.support.ClientsPage;
import com.tincore.auth.server.domain.OauthClientDetails;
import com.tincore.auth.server.service.ClientService;

public class ClientWebIT extends WebIT {

	@Autowired
	private ClientService clientService;

	@Autowired
	private ClientPage clientPage;

	@Autowired
	private ClientsPage clientsPage;

	private OauthClientDetails client;

	private CharSequence newClientSecret = "change_sc";

	@Before
	public void setUp() throws Exception {
		super.setUp();
		OauthClientDetails clientDetails = clientService.getDefaultOauthClientDetails();
		clientDetails.setClientId("cl" + System.currentTimeMillis());
		clientDetails.setClientSecret("sc" + System.currentTimeMillis());
		client = clientService.save(clientDetails);

	}

	@Test
	public void testGivenPageAndExistingClientAndNotLoggedInThenDisplaysLoginPage() {
		goTo("/admin/clients/" + client.getClientId());

		loginPage.waitForVisibility();
	}

	@Test
	public void testGivenPageAndExistingClientThenDisplaysClientInputFields() {
		setUpLogin();

		goTo("/admin/clients/" + client.getClientId(), clientPage);

		assertThat(clientPage.getClientClientId().getAttribute("value"), is(client.getClientId()));
		assertThat(clientPage.getClientClientSecret().getAttribute("value"), is(client.getClientSecret()));
		assertThat(clientPage.getClientScope().getAttribute("value"), is(client.getScope()));
	}

	@Test
	public void testGivenPageAndExistingClientWhenClientIsEditedAndSaveIsClickedThenClientIsUpdatedAndClientsIsDisplayed() {
		setUpLogin();

		goTo("/admin/clients/" + client.getClientId(), clientPage);

		clientPage.getClientClientSecret().clear();
		clientPage.getClientClientSecret().sendKeys(newClientSecret);
		clientPage.getClientSave().click();

		clientsPage.waitForVisibility();

		OauthClientDetails updatedClient = clientService.findByClientId(client.getClientId()).get();

		assertThat(updatedClient.getClientSecret(), is(newClientSecret));
	}

	@Test
	public void testGivenPageAndExistingClientWhenClientIsEditedAndCancelIsClickedThenClientIsNotUpdatedAndClientsIsDisplayed() {
		setUpLogin();

		goTo("/admin/clients/" + client.getClientId(), clientPage);

		clientPage.getClientClientSecret().clear();
		clientPage.getClientClientSecret().sendKeys(newClientSecret);
		clientPage.getClientSaveCancel().click();

		clientsPage.waitForVisibility();

		OauthClientDetails updatedClient = clientService.findByClientId(client.getClientId()).get();

		assertThat(updatedClient.getClientSecret(), is(client.getClientSecret()));
	}

	@Test
	public void testGivenPageAndExistingClientWhenDeleteIsRequestedAndDeleteIsClickedThenClientIsRemoved() {
		setUpLogin();

		goTo("/admin/clients/" + client.getClientId(), clientPage);

		clientPage.getClientDeleteRequest().click();
		clientPage.waitForVisibility(clientPage.getClientDelete());
		clientPage.getClientDelete().click();

		clientsPage.waitForVisibility();

		assertThat(clientService.findByClientId(client.getClientId()).isPresent(), is(false));
	}

	@Test
	public void testGivenPageAndExistingClientWhenDeleteIsRequestedAndCancelIsClickedThenClientIsNotRemoved() {
		setUpLogin();

		goTo("/admin/clients/" + client.getClientId(), clientPage);

		clientPage.getClientDeleteRequest().click();
		clientPage.waitForVisibility(clientPage.getClientDelete());
		clientPage.getClientDeleteCancel().click();

		assertThat(clientService.findByClientId(client.getClientId()).isPresent(), is(true));
	}

}