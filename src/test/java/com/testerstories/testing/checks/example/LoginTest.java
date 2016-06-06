package com.testerstories.testing.checks.example;

import com.testerstories.testing.DriverBase;
import com.testerstories.testing.pages.example.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends DriverBase {
    @Test
    private void login() {
        WebDriver driver = DriverBase.getDriver();
        driver.get("http://the-internet.herokuapp.com/login");

        LoginPage loginPage = new LoginPage();

        loginPage.logInAs("tomsmith", "SuperSecretPassword!");
        assertThat(loginPage.checkAlert()).contains("You logged into a secure area!");
    }
}
