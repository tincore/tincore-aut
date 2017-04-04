package com.tincore.test.support.selenium;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

class SeleniumTestExecutionListener extends AbstractTestExecutionListener {

	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {

		WebDriver webDriver = testContext.getApplicationContext().getBean(WebDriver.class);
		if (testContext.getTestException() != null && webDriver instanceof TakesScreenshot) {
			File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
			String testName = testContext.getTestClass().getName();
			String methodName = testContext.getTestMethod().getName();

			Path targetPath = Paths.get("target/screenshots", testName + "-" + methodName + "-" + screenshot.getName());
			Files.createDirectories(targetPath.getParent());
			Files.copy(screenshot.toPath(), targetPath);

			return;
		}

		webDriver.close();
		webDriver.quit();
		testContext.getApplicationContext().getBean(SeleniumFunctionalTestScope.class).reset();
	}
}
