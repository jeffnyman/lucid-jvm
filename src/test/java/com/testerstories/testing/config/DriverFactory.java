package com.testerstories.testing.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public class DriverFactory {
    private static WebDriver driver;

    @BeforeClass
    public static void createDriver() {
        driver = new FirefoxDriver();
    }

    protected static WebDriver getDriver() {
        return driver;
    }

    @AfterMethod
    public void quitDriver() {
        driver.quit();
    }
}
