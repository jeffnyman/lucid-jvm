package com.testerstories.testing.checks.decohere;

import com.testerstories.testing.config.DriverFactory;
import com.testerstories.testing.pages.decohere.App;
import com.testerstories.testing.pages.decohere.HomePage;
import com.testerstories.testing.pages.decohere.LandingPage;
import com.testerstories.testing.pages.decohere.PlanetWeightPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanetWeightTest extends DriverFactory {
    @BeforeMethod
    public void setup() {
        getDriver().manage().window().maximize();
        getDriver().get("https://decohere.herokuapp.com/");

        // Non-fluent approach
        /*
        homePage = new HomePage();
        homePage.authentication.logInAs("admin@decohere.com", "admin");
        landingPage = new LandingPage();
        planetWeightPage = landingPage.navigation.goToPlanetWeight();
        */

        // Fluent approach
        app = new App();
        app.login.asAdmin();
        app.navigate.toPlanetWeight();

    }

    @Test
    public void calculateForMercury() {
        // Next line needed only for Fluent approach
        planetWeightPage = new PlanetWeightPage();

        planetWeightPage.calculateMercuryWeight(200);
        assertThat(planetWeightPage.mercuryWeightValue()).isEqualTo("75.6");
    }

    @Test
    public void calculateForVenus() {
        // Next line needed only for Fluent approach
        planetWeightPage = new PlanetWeightPage();

        planetWeightPage.calculateVenusWeight(200);
        assertThat(planetWeightPage.venusWeightValue()).isEqualTo("181.4");
    }
}
