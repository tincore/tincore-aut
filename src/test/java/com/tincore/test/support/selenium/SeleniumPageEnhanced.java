package com.tincore.test.support.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface SeleniumPageEnhanced {

	public WebDriver getDriver();

	public default String getTitle() {
		return getDriver().getTitle();
	}

	public void waitForVisibility();

	public default void waitForVisibility(WebElement element, int timeoutSecs) throws Error {
		new WebDriverWait(getDriver(), timeoutSecs).until(ExpectedConditions.visibilityOf(element));
	}

	public default void waitForVisibility(WebElement element) throws Error {
		waitForVisibility(element, 5);
	}
	
	public default String getSemanticQualifier(WebElement webElement) {
		return webElement.getAttribute("data-sem-qualifier");
	}


}
