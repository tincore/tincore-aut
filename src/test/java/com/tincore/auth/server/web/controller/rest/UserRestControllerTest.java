package com.tincore.auth.server.web.controller.rest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tincore.auth.server.IntegrationTestFixtureHelper;
import com.tincore.auth.server.TestConfiguration;
import com.tincore.auth.server.TincoreAuthorizationServerApplication;
import com.tincore.auth.server.domain.User;
import com.tincore.test.support.IntegrationTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TincoreAuthorizationServerApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
@Category(IntegrationTest.class)
public class UserRestControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	private MockMvc mockMvc;

	private void assertUserJson(ResultActions resultActions, String parentNodePath, User user) throws Exception {
//		Hibernate.initialize(user.getAuthorities());
		resultActions.andExpect(jsonPath(parentNodePath + ".name", is(user.getUsername()))) //
				.andExpect(jsonPath(parentNodePath + ".authorities",
						is(user.getAuthorities().iterator().next().getAuthority())));
	}

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@After
	public void tearDown() {
		testFixtureHelper.clearAuthentication();
	}

	@Test
	public void testGetUserGivenNotAuthenticatedUserThenReturnsEmpty() throws Exception {
		mockMvc.perform(get("/me")).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(content().string("{}"));//
		;//
	}

	@Test
	@Transactional
	public void testGetUserGivenAuthenticatedUserThenReturnsUsernameAndAuthorities() throws Exception {
		User testUser = testFixtureHelper.createUserTest();
		testFixtureHelper.setAuthentication(testUser);

		ResultActions resultActions = mockMvc.perform(get("/me"))//
				.andExpect(status().is(HttpStatus.OK.value()))//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson));//
		assertUserJson(resultActions, "$", testUser);
	}

	@Test
	@Transactional
	public void testGetMeGivenAuthenticatedUserThenReturnsUsernameAndAuthorities() throws Exception {
		User testUser = testFixtureHelper.createUserTest();
		testFixtureHelper.setAuthentication(testUser);

		ResultActions resultActions2 = mockMvc.perform(get("/user"))//
				.andExpect(status().is(HttpStatus.OK.value()))//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson));//
		assertUserJson(resultActions2, "$", testUser);

	}

}
