package com.tincore.test.support.selenium;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@ComponentScan(basePackageClasses = AbstractSeleniumFunctionalWebITConfiguration.class)
public abstract class AbstractSeleniumFunctionalWebITConfiguration {

	@Bean
	public SeleniumFunctionalTestScope getSeleniumFunctionalTestScope() {
		return new SeleniumFunctionalTestScope();
	}

	@Bean
	public CustomScopeConfigurer customScopeConfigurer() {
		CustomScopeConfigurer scopeConfigurer = new CustomScopeConfigurer();
		Map<String, Object> scopes = new HashMap<>();
		scopes.put(SeleniumFunctionalTestScope.ID, getSeleniumFunctionalTestScope());
		scopeConfigurer.setScopes(scopes);
		return scopeConfigurer;
	}
}