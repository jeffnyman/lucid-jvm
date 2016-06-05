package com.testerstories.testing.checks;

import com.testerstories.testing.config.DriverFactory;
import com.testerstories.testing.pages.DecohereLoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DecohereLoginTest extends DriverFactory {
    @Test
    public void loginAsAdmin() {
        WebDriver driver = DriverFactory.getDriver();
        driver.get("https://decohere.herokuapp.com/");

        DecohereLoginPage.logInAs("admin@decohere.com", "admin", driver);
        assertThat(DecohereLoginPage.checkMessage(driver)).isEqualTo("You are now logged in as admin@decohere.com.");
    }
}
