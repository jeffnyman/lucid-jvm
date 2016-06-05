package com.testerstories.testing.checks;

import com.testerstories.testing.config.DriverFactory;
import com.testerstories.testing.pages.PlanetWeightPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanetWeightTest extends DriverFactory {
    @Test
    public void calculateForMercury() {
        WebDriver driver = DriverFactory.getDriver();
        driver.get("https://decohere.herokuapp.com/planets");

        PlanetWeightPage.calculateMercuryWeight("200", driver);
        assertThat(PlanetWeightPage.mercuryWeightValue(driver)).isEqualTo("75.6");
    }
}
