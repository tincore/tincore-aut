package com.tincore.auth.integration.functional.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.tincore.test.support.selenium.AbstractSeleniumFunctionalWebITConfiguration;

@Configuration
@ComponentScan("com.tincore.auth.integration.functional.web")
public class WebITConfiguration extends AbstractSeleniumFunctionalWebITConfiguration {

}
