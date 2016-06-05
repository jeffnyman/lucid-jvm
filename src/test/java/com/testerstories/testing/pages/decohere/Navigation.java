package com.testerstories.testing.pages.decohere;

import com.testerstories.testing.config.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Navigation {
    @FindBy(id = "pages")
    private WebElement pageMenu;

    @FindBy(id = "planets")
    private WebElement planets;

    @FindBy(id = "overlord")
    private WebElement overlord;

    @FindBy(id = "stardate")
    private WebElement stardate;

    Navigation() {
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }

    public void toPlanetWeight() {
        goToPlanetWeight();
    }

    public void toStardate() {
        goToStardate();
    }

    private PlanetWeightPage goToPlanetWeight() {
        pageMenu.click();
        planets.click();

        return new PlanetWeightPage();
    }

    private StardatePage goToStardate() {
        pageMenu.click();
        stardate.click();

        return new StardatePage();
    }
}
