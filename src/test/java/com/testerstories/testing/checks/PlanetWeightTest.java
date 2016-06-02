package com.testerstories.testing.checks;

import com.testerstories.testing.config.DriverFactory;
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

        WebElement weight = driver.findElement(By.id("wt"));
        weight.sendKeys("200");

        WebElement calculate = driver.findElement(By.id("calculate"));
        calculate.click();

        WebElement mercury_weight = driver.findElement(By.id("outputmrc"));
        assertThat(mercury_weight.getAttribute("value")).isEqualTo("75.6");
    }
}
