package com.testerstories.testing.config;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.MarionetteDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    MARIONETTE {
        @Override
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability("marionette", true);
            return capabilities;
        }

        @Override
        public WebDriver getWebDriver(DesiredCapabilities desiredCapabilities) {
            MarionetteDriverManager.getInstance().setup();
            return new MarionetteDriver(desiredCapabilities);
        }
    },

    CHROME {
        @Override
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check", "--start-maximized"));
            HashMap<String, String> chromePreferences = new HashMap<String, String>();
            chromePreferences.put("profile.password_manager_enabled", "false");
            capabilities.setCapability("chrome.prefs", chromePreferences);
            return capabilities;
        }

        @Override
        public WebDriver getWebDriver(DesiredCapabilities desiredCapabilities) {
            ChromeDriverManager.getInstance().setup();
            return new ChromeDriver(desiredCapabilities);
        }
    },

    PHANTOMJS {
        @Override
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
            final List<String> cliArguments = new ArrayList<String>();
            cliArguments.add("--web-security=false");
            cliArguments.add("--ssl-protocol=any");
            cliArguments.add("--ignore-ssl-errors=true");
            cliArguments.add("--webdriver-loglevel=NONE");
            capabilities.setCapability("phantomjs.cli.args", cliArguments);
            capabilities.setCapability("takesScreenshot", true);
            Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.OFF);
            return capabilities;
        }

        @Override
        public WebDriver getWebDriver(DesiredCapabilities desiredCapabilities) {
            PhantomJsDriverManager.getInstance().setup();
            return new PhantomJSDriver(desiredCapabilities);
        }
    },

    IE {
        @Override
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
            capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
            capabilities.setCapability("requireWindowFocus", true);
            return capabilities;
        }

        @Override
        public WebDriver getWebDriver(DesiredCapabilities desiredCapabilities) {
            InternetExplorerDriverManager.getInstance().setup();
            return new InternetExplorerDriver(desiredCapabilities);
        }
    },

    EDGE {
        @Override
        public DesiredCapabilities getDesiredCapabilities() {
            return DesiredCapabilities.edge();
        }

        @Override
        public WebDriver getWebDriver(DesiredCapabilities desiredCapabilities) {
            return new EdgeDriver(desiredCapabilities);
        }
    },

    SAFARI {
        @Override
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.safari();
            capabilities.setCapability("safari.cleanSession", true);
            return capabilities;
        }

        @Override
        public WebDriver getWebDriver(DesiredCapabilities desiredCapabilities) {
            return new SafariDriver(desiredCapabilities);
        }
    }
}
