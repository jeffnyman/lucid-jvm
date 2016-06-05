package com.testerstories.testing.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Selenium {
    private WebDriver driver;

    public Selenium(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForPresence(WebElement identifier) {
        WebDriverWait wait = new WebDriverWait(driver, 10, 500);
        wait.until(ExpectedConditions.visibilityOf(identifier));
    }

    public void waitForPageWithTitle(String title) {
        WebDriverWait wait = new WebDriverWait(driver, 10, 500);
        wait.until(ExpectedConditions.titleContains(title));
    }

    public WebElement withElement(By identifier) {
        return driver.findElement(identifier);
    }

    public void waitForAJAX() {
        WebDriverWait wait = new WebDriverWait(driver, 15, 100);
        wait.until(jQueryAJAXCallsHaveCompleted());
    }

    public void waitForAngular() {
        WebDriverWait wait = new WebDriverWait(driver, 15, 100);
        wait.until(angularHasProcessed());
    }

    private static ExpectedCondition<Boolean> jQueryAJAXCallsHaveCompleted() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return (Boolean) ((JavascriptExecutor) driver)
                        .executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
            }
        };
    }

    private static ExpectedCondition<Boolean> angularHasProcessed() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return Boolean.valueOf(((JavascriptExecutor) driver)
                        .executeScript("return (window.angular !== undefined)" +
                                "&& (angular.element(document).injector() !== undefined)" +
                                "&& (angular.element(document).injector().get('$http').pendingRequests.length === 0)").toString());
            }
        };
    }
}
