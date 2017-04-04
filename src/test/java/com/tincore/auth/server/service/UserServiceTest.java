package com.tincore.auth.server.service;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.isPresent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.tincore.auth.server.IntegrationTestFixtureHelper;
import com.tincore.auth.server.TestConfiguration;
import com.tincore.auth.server.TincoreAuthorizationServerApplication;
import com.tincore.auth.server.domain.User;
import com.tincore.auth.server.form.UserCreateForm;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TincoreAuthorizationServerApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class UserServiceTest {

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	private String username = "user" + System.nanoTime();

	@Test
	public void testCreateUserGivenNewUserThenCreatesUserWithUserRoleInDatabase() throws Exception {
		User user = userService.createUser(new UserCreateForm(username, "password"));

		assertThat(user.getUsername(), is(equalTo(username)));

		assertThat(user.getAuthorities(), hasSize(1));
		assertThat(user.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()), hasItem(UserService.ROLE_USER));
	}

	@Test
	public void testDeleteWhenNoUserPrincipalThenRemovesSuccesfully() throws Exception {
		User user = testFixtureHelper.createUserTest();

		userService.delete(user.getUsername(), Optional.empty());

		assertThat(userRepository.findOne(user.getId()), is(nullValue()));
	}

	@Test
	public void testDeleteWhenUserPrincipalDifferentFromUsernameThenRemovesSuccesfully() throws Exception {
		User user = testFixtureHelper.createUserTest();
		User user2 = testFixtureHelper.createUserAdmin();

		userService.delete(user.getUsername(), Optional.of(user2));

		assertThat(userRepository.findOne(user.getId()), is(nullValue()));
	}

	@Test(expected=UserInvalidException.class)
	public void testDeleteWhenUserPrincipalSameAsUsernameThenThrowsInvalidUserException() throws Exception {
		User user = testFixtureHelper.createUserTest();

		userService.delete(user.getUsername(), Optional.of(user));
	}
	
	@Test
	public void testFindAllThenReturnsAllUsers() throws Exception {
		List<String> userNames = userService.findAll().stream().map(a -> a.getUsername()).collect(Collectors.toList());
		String[] dbUserNames = StreamSupport.stream(userRepository.findAll().spliterator(), false).map(a -> a.getUsername()).toArray(String[]::new);
		assertThat(userNames, hasSize(dbUserNames.length));
		assertThat(userNames, containsInAnyOrder(dbUserNames));
	}
	
	@Test
	public void testFindByUsernameThenReturnsUserWithMatchingUsername() throws Exception {
		User testUser = testFixtureHelper.createUserTest();
		Optional<User> user = userService.findByUsername(testUser.getUsername());
		assertThat(user.get().getId(), is(testUser.getId()));
	}

	@Test
	public void testFindByUsernameWhenUsernameNotStoredThenReturnsEmptyOption() throws Exception {
		Optional<User> user = userService.findByUsername("noname");
		assertThat(user.isPresent(), is(false));
	}

	
	@Test
	public void testGetUserThenReturnsUserWithMatchingUsername() throws Exception {
		User testUser = testFixtureHelper.createUserTest();
		User user = userService.getUser(testUser.getUsername());
		assertThat(user.getId(), is(testUser.getId()));
	}

	@Test(expected=UserNotFoundException.class)
	public void testGetUserWhenUsernameNotStoredThenReturnsEmptyOption() throws Exception {
		userService.getUser("noname");
	}
	
}



//@Override
//public User getUser(String username) {
//	return findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
//}
//
//public boolean isRolePresent(User user, String role) {
//	return user.getUserAuthorities() != null && user.getUserAuthorities().stream()
//			.filter(a -> a.getAuthority().equals(role)).findFirst().isPresent();
//}
//
//@Override
//@Transactional
//public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//	User user = getUser(username);
//	Hibernate.initialize(user.getAuthorities());
//	return user;
//}
//
//@Override
//public User updateUser(User user, String newPassword) {
//	if (StringUtils.isNotBlank(newPassword)) {
//		user.setPassword(passwordEncoder.encode(newPassword));
//	}
//
//	return userRepository.save(user);
//}
//
//@Override
//public Optional<User> findByUsername(String username) {
//	return userRepository.findByUsername(username);
//}
