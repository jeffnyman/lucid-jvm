package com.testerstories.testing.pages.decohere;

import com.testerstories.testing.config.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanetWeightPage {
    @FindBy(id = "wt")
    private WebElement weight;

    @FindBy(id = "calculate")
    private WebElement calculate;

    @FindBy(id = "outputmrc")
    private WebElement mercuryWeight;

    @FindBy(id = "outputvn")
    private WebElement venusWeight;

    public PlanetWeightPage() {
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }

    public void calculateMercuryWeight(int humanWeight) {
        weight.sendKeys(String.valueOf(humanWeight));
        calculate.click();

        String planetWeight = mercuryWeightValue();
        double calculatedWeight = (10 * humanWeight * .378) / 10;

        assertThat(Double.parseDouble(planetWeight)).isEqualTo(calculatedWeight);
    }

    public void calculateVenusWeight(int humanWeight) {
        weight.sendKeys(String.valueOf(humanWeight));
        calculate.click();

        String planetWeight = venusWeightValue();
        double calculatedWeight = (10 * humanWeight * .907) / 10;

        assertThat(Double.parseDouble(planetWeight)).isEqualTo(calculatedWeight);
    }

    public String mercuryWeightValue() {
        return mercuryWeight.getAttribute("value");
    }

    public String venusWeightValue() {
        return venusWeight.getAttribute("value");
    }
}
