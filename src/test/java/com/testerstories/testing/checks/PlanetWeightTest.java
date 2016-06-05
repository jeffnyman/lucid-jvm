package com.testerstories.testing.checks;

import com.testerstories.testing.config.DriverFactory;
import com.testerstories.testing.pages.PlanetWeightPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanetWeightTest extends DriverFactory {
    @Test
    public void calculateForMercury() {
        WebDriver driver = DriverFactory.getDriver();
        driver.get("https://decohere.herokuapp.com/planets");

        PlanetWeightPage planetPage = new PlanetWeightPage();

        planetPage.calculateMercuryWeight("200");
        assertThat(planetPage.mercuryWeightValue()).isEqualTo("75.6");
    }
}
