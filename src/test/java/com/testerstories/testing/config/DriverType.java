package com.testerstories.testing.config;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

enum DriverType implements DriverSetup {
    FIREFOX {
        @Override
        public DesiredCapabilities getDesiredCapabilities() {
            return DesiredCapabilities.firefox();
        }

        @Override
        public WebDriver getWebDriver(DesiredCapabilities desiredCapabilities) {
            return new FirefoxDriver(desiredCapabilities);
        }
    },

    CHROME {
        @Override
        public DesiredCapabilities getDesiredCapabilities() {
            return DesiredCapabilities.chrome();
        }

        @Override
        public WebDriver getWebDriver(DesiredCapabilities desiredCapabilities) {
            ChromeDriverManager.getInstance().setup();
            return new ChromeDriver(desiredCapabilities);
        }
    }
}
