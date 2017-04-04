package com.tincore.test.support.selenium;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
class SeleniumWebDriverFactory {

	@Bean
	@Scope(SeleniumFunctionalTestScope.ID)
	public WebDriver getWebDriverInstance() {
		String chromeDriverPath = "/usr/lib/chromium-browser/chromedriver";
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		ChromeDriverService service = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File(chromeDriverPath)).usingAnyFreePort().build();
		try {
			service.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		RemoteWebDriver driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		return driver;

		// private WebDriver webDriver = new PhantomJSDriver();
		// <plugin>
		// <groupId>com.github.klieber</groupId>
		// <artifactId>phantomjs-maven-plugin</artifactId>
		// <version>0.4</version>
		// <executions>
		// <execution>
		// <goals>
		// <goal>install</goal>
		// </goals>
		// </execution>
		// </executions>
		// <configuration>
		// <version>1.9.8</version>
		// </configuration>
		// </plugin>

	}

}
