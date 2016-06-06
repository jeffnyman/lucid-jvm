package com.testerstories.testing.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverFactory {
    private static WebDriver driver;
    private static final String browser = System.getProperty("browser").toUpperCase();
    private static final DriverType defaultDriver = DriverType.FIREFOX;

    public void createDriver() {
        DriverType selectedDriver = determineDriver();
        DesiredCapabilities desiredCapabilities = selectedDriver.getDesiredCapabilities();
        driver = selectedDriver.getWebDriver(desiredCapabilities);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void quitDriver() {
        driver.quit();
    }

    private static DriverType determineDriver() {
        DriverType driverType = defaultDriver;

        try {
            driverType = DriverType.valueOf(browser);
        } catch (IllegalArgumentException invalid) {
            System.err.println("Unknown driver specified. Defaulting to '" + driverType + "'.");
        }

        return driverType;
    }
}
