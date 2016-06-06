package com.testerstories.testing;

import com.testerstories.testing.config.DriverFactory;
import com.testerstories.testing.listeners.ScreenshotListener;
import com.testerstories.testing.pages.decohere.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Listeners(ScreenshotListener.class)
public class DriverBase {
    private static ThreadLocal<DriverFactory> driverFactory;
    private static List<DriverFactory> threadPool = Collections.synchronizedList(new ArrayList<DriverFactory>());

    protected App app;
    protected HomePage homePage;
    protected LandingPage landingPage;
    protected PlanetWeightPage planetWeightPage;
    protected StardatePage stardatePage;

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() {
        driverFactory = new ThreadLocal<DriverFactory>() {
            @Override
            protected DriverFactory initialValue() {
                DriverFactory driverFactory = new DriverFactory();
                threadPool.add(driverFactory);
                return driverFactory;
            }
        };
    }

    public static WebDriver getDriver() {
        return driverFactory.get().getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public static void quitDriver() {
        getDriver().manage().deleteAllCookies();
        for (DriverFactory driverFactory : threadPool) {
            driverFactory.quitDriver();
        }
    }
}
