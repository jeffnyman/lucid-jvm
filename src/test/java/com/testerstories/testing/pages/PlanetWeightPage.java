package com.testerstories.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PlanetWeightPage {
    private static By weight = By.id("wt");
    private static By calculate = By.id("calculate");
    private static By mercuryWeight = By.id("outputmrc");

    public static void calculateMercuryWeight(String humanWeight, WebDriver driver) {
        driver.findElement(weight).sendKeys(humanWeight);
        driver.findElement(calculate).click();
    }

    public static String mercuryWeightValue(WebDriver driver) {
        return driver.findElement(mercuryWeight).getAttribute("value");
    }
}
