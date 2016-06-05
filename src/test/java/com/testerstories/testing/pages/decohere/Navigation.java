package com.testerstories.testing.pages.decohere;

import com.testerstories.testing.config.DriverFactory;
import com.testerstories.testing.helpers.Selenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Navigation {
    private Selenium selenium;

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
        selenium = new Selenium(DriverFactory.getDriver());
    }

    public void toPlanetWeight() {
        goToPlanetWeight();
    }

    public void toStardate() {
        goToStardate();
    }

    private PlanetWeightPage goToPlanetWeight() {
        selenium.waitForPresence(pageMenu);
        pageMenu.click();
        planets.click();
        selenium.waitForPageWithTitle("Planet Weight Calculator");

        return new PlanetWeightPage();
    }

    private StardatePage goToStardate() {
        selenium.waitForPresence(pageMenu);
        pageMenu.click();
        stardate.click();
        selenium.waitForPageWithTitle("Stardate Calculator");

        return new StardatePage();
    }
}
