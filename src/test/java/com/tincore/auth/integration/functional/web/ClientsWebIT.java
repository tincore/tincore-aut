package com.tincore.auth.integration.functional.web;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tincore.auth.integration.functional.web.support.ClientsPage;
import com.tincore.auth.integration.functional.web.support.LoginPage;
import com.tincore.auth.server.service.ClientService;
import com.tincore.test.support.selenium.AbstractSeleniumFunctionalWebIT;

public class ClientsWebIT extends WebIT {

	@Autowired
	private LoginPage loginPage;

	@Autowired
	private ClientsPage clientsPage;

	@Autowired
	private ClientService clientService;

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testGivenPageThenClientsAreListed() {
		goTo("/login", loginPage);
		loginPage.doLogin("admin", "admin");

		goTo("/admin/clients", clientsPage);

		List<String> semQualifiers = clientsPage.getClientContainers().stream()
				.map(w -> w.getAttribute("data-sem-qualifier")).collect(Collectors.toList());
		String[] clientIds = StreamSupport.stream(clientService.findAll().spliterator(), false)
				.map(c -> c.getClientId()).toArray(String[]::new);

		assertThat(semQualifiers, containsInAnyOrder(clientIds));
		assertThat(semQualifiers.size(), is(clientIds.length));

	}
}