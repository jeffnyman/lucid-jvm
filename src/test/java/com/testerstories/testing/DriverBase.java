package com.testerstories.testing;

import com.testerstories.testing.config.DriverFactory;
import com.testerstories.testing.listeners.ScreenshotListener;
import com.testerstories.testing.pages.decohere.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners(ScreenshotListener.class)
public class DriverBase {
    private static DriverFactory driverFactory;

    protected App app;
    protected HomePage homePage;
    protected LandingPage landingPage;
    protected PlanetWeightPage planetWeightPage;
    protected StardatePage stardatePage;

    @BeforeMethod
    public static void createDriver() {
        driverFactory = new DriverFactory();
        driverFactory.createDriver();
    }

    public static WebDriver getDriver() {
        return driverFactory.getDriver();
    }

    @AfterMethod
    public static void quitDriver() {
        driverFactory.quitDriver();
    }
}
