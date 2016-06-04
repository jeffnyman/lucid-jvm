package com.testerstories.testing.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

interface DriverSetup {
    WebDriver getWebDriver(DesiredCapabilities desiredCapabilities);
    DesiredCapabilities getDesiredCapabilities();
}
