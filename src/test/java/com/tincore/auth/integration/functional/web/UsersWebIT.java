package com.tincore.auth.integration.functional.web;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import com.tincore.auth.integration.functional.web.support.UserPage;
import com.tincore.auth.integration.functional.web.support.UsersPage;
import com.tincore.auth.server.service.UserService;

public class UsersWebIT extends WebIT {

	@Autowired
	private UsersPage usersPage;

	@Autowired
	private UserPage userPage;

	@Autowired
	private UserService userService;

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testGivenPageThenUsersAreListed() {
		setUpLogin();

		goTo("/admin/users", usersPage);

		List<String> semQualifiers = usersPage.getUserContainers().stream()
				.map(w -> w.getAttribute("data-sem-qualifier")).collect(Collectors.toList());
		String[] userIds = StreamSupport.stream(userService.findAll().spliterator(), false).map(u -> u.getUsername())
				.toArray(String[]::new);

		assertThat(semQualifiers, containsInAnyOrder(userIds));
		assertThat(semQualifiers.size(), is(userIds.length));

	}

	@Test
	public void testGivenPageWithUsersWhenClickOnUserThenUsersPageIsDisplayed() {
		setUpLogin();

		goTo("/admin/users", usersPage);

		WebElement userContainer = usersPage.getUserContainers().get(0);
		String usersUserQualifier = userContainer.getAttribute("data-sem-qualifier");

		userContainer.click();

		userPage.waitForVisibility();

		String userQualifier = userPage.getSemanticQualifier(userPage.getUserContainer());

		assertThat(userQualifier, is(usersUserQualifier));
	}

	@Test
	public void testGivenPageWithUsersWhenClickOnUserAddAndFillDataThenUsersIsCreated() {
		setUpLogin();

		goTo("/admin/users", usersPage);

		usersPage.getUserCreateRequest().click();
		String username = "user" + System.currentTimeMillis();
		usersPage.waitForVisibility(usersPage.getUserUsername());
		usersPage.getUserUsername().sendKeys(username);
		usersPage.getUserPassword().sendKeys("somepass");
		usersPage.getUserFirstname().sendKeys(username + "_f");
		usersPage.getUserLastname().sendKeys(username + "_l");
		usersPage.getUserSave().click();

		List<String> semQualifiers = usersPage.getUserContainers().stream()
				.map(w -> w.getAttribute("data-sem-qualifier")).collect(Collectors.toList());
		assertThat(semQualifiers, hasItem(username));

	}

}