package com.tincore.auth.integration.functional.web;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tincore.auth.integration.functional.web.support.LoginPage;
import com.tincore.auth.integration.functional.web.support.UserPage;
import com.tincore.auth.integration.functional.web.support.UsersPage;
import com.tincore.auth.server.domain.User;
import com.tincore.auth.server.form.UserCreateForm;
import com.tincore.auth.server.service.UserService;

public class UserWebIT extends WebIT {

	@Autowired
	private UserService userService;

	@Autowired
	private UserPage userPage;

	@Autowired
	private UsersPage usersPage;

	private User user;
	private CharSequence newFirstName = "change_fn";
	private CharSequence newLastName = "change_ln";

	@Before
	public void setUp() throws Exception {
		super.setUp();
		UserCreateForm userForm = new UserCreateForm("user" + System.currentTimeMillis(), "somepass");
		userForm.setFirstName("fn");
		userForm.setLastName("ln");
		user = userService.createUser(userForm);

	}

	@Test
	public void testGivenPageAndExistingUserAndNotLoggedInThenDisplaysLoginPage() {
		goTo("/admin/users/" + user.getUsername());

		loginPage.waitForVisibility();
	}

	@Test
	public void testGivenPageAndExistingUserThenDisplaysUserInputFields() {
		setUpLogin();

		goTo("/admin/users/" + user.getUsername(), userPage);

		assertThat(userPage.getUserUsername().getAttribute("value"), is(user.getUsername()));
		assertThat(userPage.getUserFirstname().getAttribute("value"), is(user.getFirstName()));
		assertThat(userPage.getUserLastname().getAttribute("value"), is(user.getLastName()));
	}

	@Test
	public void testGivenPageAndExistingUserWhenUserIsEditedAndSaveIsClickedThenUserIsUpdatedAndUsersIsDisplayed() {
		setUpLogin();

		goTo("/admin/users/" + user.getUsername(), userPage);

		userPage.getUserFirstname().clear();
		userPage.getUserFirstname().sendKeys(newFirstName);
		userPage.getUserLastname().clear();
		userPage.getUserLastname().sendKeys(newLastName);
		userPage.getUserSave().click();

		usersPage.waitForVisibility();

		User updatedUser = userService.getUser(user.getUsername());

		assertThat(updatedUser.getFirstName(), is(newFirstName));
		assertThat(updatedUser.getLastName(), is(newLastName));
	}

	@Test
	public void testGivenPageAndExistingUserWhenUserIsEditedAndCancelIsClickedThenUserIsNotUpdatedAndUsersIsDisplayed() {
		setUpLogin();

		goTo("/admin/users/" + user.getUsername(), userPage);

		userPage.getUserFirstname().clear();
		userPage.getUserFirstname().sendKeys(newFirstName);
		userPage.getUserSaveCancel().click();

		usersPage.waitForVisibility();

		User updatedUser = userService.getUser(user.getUsername());

		assertThat(updatedUser.getFirstName(), is(user.getFirstName()));
	}

	@Test
	public void testGivenPageAndExistingUserWhenDeleteIsRequestedAndDeleteIsClickedThenUserIsRemoved() {
		setUpLogin();

		goTo("/admin/users/" + user.getUsername(), userPage);

		userPage.getUserDeleteRequest().click();

		userPage.waitForVisibility(userPage.getUserDelete());

		userPage.getUserDelete().click();

		usersPage.waitForVisibility();

		assertThat(userService.findByUsername(user.getUsername()).isPresent(), is(false));
	}

	@Test
	public void testGivenPageAndExistingUserWhenDeleteIsRequestedAndCancelIsClickedThenUserIsNotRemoved() {
		setUpLogin();

		goTo("/admin/users/" + user.getUsername(), userPage);

		userPage.getUserDeleteRequest().click();

		userPage.waitForVisibility(userPage.getUserDelete());

		userPage.getUserDeleteCancel().click();

		assertThat(userService.findByUsername(user.getUsername()).isPresent(), is(true));
	}

}