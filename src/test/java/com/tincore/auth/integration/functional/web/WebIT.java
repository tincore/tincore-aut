package com.tincore.auth.integration.functional.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tincore.auth.integration.functional.web.support.LoginPage;
import com.tincore.auth.server.TestConfiguration;
import com.tincore.auth.server.TincoreAuthorizationServerApplication;
import com.tincore.test.support.selenium.AbstractSeleniumFunctionalWebIT;

@SpringBootTest(properties = "server.port=0", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
		TincoreAuthorizationServerApplication.class, TestConfiguration.class, WebITConfiguration.class })
public abstract class WebIT extends AbstractSeleniumFunctionalWebIT {

	@Autowired
	protected LoginPage loginPage;

	public void setUpLogin() {
		goTo("/login", loginPage);
		loginPage.doLogin("admin", "admin");
	}

}


