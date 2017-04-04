package com.tincore.auth.server.service;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.isPresent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.tincore.auth.server.IntegrationTestFixtureHelper;
import com.tincore.auth.server.TestConfiguration;
import com.tincore.auth.server.TincoreAuthorizationServerApplication;
import com.tincore.auth.server.domain.User;
import com.tincore.test.support.IntegrationTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TincoreAuthorizationServerApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
@Category(IntegrationTest.class)
public class UserDetailsServiceTest {

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Test
	public void testLoadUserByUsernameThenReturnsUserWithMatchingUsernameAndAuthoritiesAreAccesible() throws Exception {
		User testUser = testFixtureHelper.createUserTest();
		UserDetails user = userDetailsService.loadUserByUsername(testUser.getUsername());
		assertThat(user.getUsername(), is(testUser.getUsername()));
		assertThat(user.getAuthorities(), hasSize(1));
		assertThat(user.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()),
				hasItem(UserService.ROLE_USER));
	}

	@Test(expected = UserNotFoundException.class)
	public void testLoadUserByUsernameWhenUsernameNotStoredThenReturnsEmptyOption() throws Exception {
		userDetailsService.loadUserByUsername("noname");
	}

}
