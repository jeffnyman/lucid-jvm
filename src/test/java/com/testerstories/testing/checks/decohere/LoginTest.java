package com.testerstories.testing.checks.decohere;

import com.testerstories.testing.DriverBase;
import com.testerstories.testing.config.DriverFactory;
import com.testerstories.testing.pages.decohere.App;
import com.testerstories.testing.pages.decohere.HomePage;
import com.testerstories.testing.pages.decohere.LandingPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends DriverBase {
    @BeforeMethod
    public void setup() {
        getDriver().manage().window().maximize();
        getDriver().get("https://decohere.herokuapp.com/");
        //getDriver().manage().window().setSize(new Dimension(1441, 900));
    }

    @Test
    public void validLogin() {
        homePage = new HomePage();
        homePage.checkAvailable();
        homePage.authentication.logInAsAdmin();

        landingPage = new LandingPage();
        assertThat(landingPage.noticeMessage()).isEqualTo("You are now logged in as admin@decohere.com.");
    }

    @Test
    public void invalidLogin() {
        homePage = new HomePage();
        homePage.checkAvailable();

        homePage.authentication
                .enterEmail("jeffnyman@example.com")
                .enterPassword("testing")
                .andLogin();

        assertThat(homePage.errorMessage()).isEqualTo("Unable to login as jeffnyman@example.com.");
    }

    @Test
    public void loginAsAdminFluently() {
        app = new App();
        app.login.asAdmin();
    }
}
