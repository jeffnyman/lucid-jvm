package com.testerstories.testing.config;

import com.testerstories.testing.pages.decohere.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class DriverFactory {
    private static WebDriver driver;
    private static final String browser = System.getProperty("browser").toUpperCase();
    private static final DriverType defaultDriver = DriverType.FIREFOX;

    protected App app;
    protected HomePage homePage;
    protected LandingPage landingPage;
    protected PlanetWeightPage planetWeightPage;
    protected StardatePage stardatePage;

    @BeforeMethod
    public static void createDriver() {
        DriverType selectedDriver = determineDriver();
        DesiredCapabilities desiredCapabilities = selectedDriver.getDesiredCapabilities();
        driver = selectedDriver.getWebDriver(desiredCapabilities);
    }

    public static WebDriver getDriver() {
        return driver;
    }

    @AfterMethod
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
