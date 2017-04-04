package com.tincore.test.support.selenium;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.tincore.auth.integration.functional.web.WebITConfiguration;
import com.tincore.auth.server.TestConfiguration;
import com.tincore.auth.server.TincoreAuthorizationServerApplication;

@RunWith(SpringRunner.class)
//@SpringBootTest(properties = "server.port=0", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
//		TincoreAuthorizationServerApplication.class, TestConfiguration.class, FunctionalWebITConfiguration.class })
@ActiveProfiles("test")
@SeleniumTest
public abstract class AbstractSeleniumFunctionalWebIT {

	@Autowired
	protected WebDriver driver;

	@Value("${local.server.port}")
	private int serverPort;

	@Value("${server.protocol:http}")
	private String serverProtocol;

	@Value("${server.host:localhost}")
	private String serverHost;

	@Value("${server.context-path}")
	private String contextPath;

	@Value("${test.web.width:1024}")
	private int browserWidth;

	@Value("${test.web.height:768}")
	private int browserHeight;

	
	@Before
	public void setUp() throws Exception {
		Dimension dimension = new Dimension(browserWidth, browserHeight); //(400,800);
		driver.manage().window().setSize(dimension);
	}

	public void goTo(String subPath, SeleniumPageEnhanced page) {
		String fullPath = String.format("%s://%s:%d%s", serverProtocol, serverHost, serverPort, contextPath);
		if (StringUtils.isNotBlank(subPath)) {
			if (!subPath.startsWith("/")) {
				fullPath += "/";
			}
			fullPath += subPath;
		}
		driver.get(fullPath);
		if (page != null){
			page.waitForVisibility();
		}
	}

	public void goTo(String subPath) {
		goTo(subPath, null);
	}
}
