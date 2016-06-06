package com.testerstories.testing.config;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    private WebDriver driver;
    private DriverType selectedDriver;

    private final boolean useRemoteWebDriver = Boolean.getBoolean("remote");
    private final String browser = System.getProperty("browser").toUpperCase();
    private final DriverType defaultDriver = DriverType.FIREFOX;

    public WebDriver getDriver() {
        if (null == driver) {
            selectedDriver = determineDriver();
            DesiredCapabilities desiredCapabilities = selectedDriver.getDesiredCapabilities();
            establishDriver(desiredCapabilities);

        }
        return driver;
    }

    public void quitDriver() {
        if (null != driver) {
            driver.quit();
        }
    }

    private DriverType determineDriver() {
        DriverType driverType = defaultDriver;

        try {
            driverType = DriverType.valueOf(browser);
        } catch (IllegalArgumentException invalid) {
            System.err.println("Unknown driver specified. Defaulting to '" + driverType + "'.");
        }

        return driverType;
    }

    private void establishDriver(DesiredCapabilities desiredCapabilities) {
        if (useRemoteWebDriver) {
            URL seleniumGridURL = null;

            try {
                seleniumGridURL = new URL(System.getProperty("gridURL"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            String desiredBrowserVersion = System.getProperty("gridBrowserVersion");
            String desiredPlatform = System.getProperty("gridPlatform");

            if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
                desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
            }

            if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
                desiredCapabilities.setVersion(desiredBrowserVersion);
            }

            driver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
        } else {
            driver = selectedDriver.getWebDriver(desiredCapabilities);
        }
    }
}
