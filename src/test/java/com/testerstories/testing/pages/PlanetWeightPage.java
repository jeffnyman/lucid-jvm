package com.testerstories.testing.pages;

import com.testerstories.testing.config.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PlanetWeightPage {
    @FindBy(id = "wt")
    private WebElement weight;

    @FindBy(id = "calculate")
    private WebElement calculate;

    @FindBy(id = "outputmrc")
    private WebElement mercuryWeight;

    public PlanetWeightPage() {
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }

    public void calculateMercuryWeight(String humanWeight) {
        weight.sendKeys(humanWeight);
        calculate.click();
    }

    public String mercuryWeightValue() {
        return mercuryWeight.getAttribute("value");
    }
}
