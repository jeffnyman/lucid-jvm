package com.testerstories.testing.pages.decohere;

import com.testerstories.testing.DriverBase;
import com.testerstories.testing.helpers.Selenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HomePage {
    private Selenium selenium;

    @FindBy(id = "site-image")
    private WebElement testGorilla;

    @FindBy(css = "#index p")
    private List<WebElement> mainText;

    @FindBy(className = "error")
    private WebElement error;

    public Authentication authentication = new Authentication();

    public HomePage() {
        PageFactory.initElements(DriverBase.getDriver(), this);
        selenium = new Selenium(DriverBase.getDriver());
    }

    public void checkAvailable() {
        assertThat(mainTextIsDisplayed()).isTrue();
    }

    private boolean mainTextIsDisplayed() {
        return mainText.size() > 1;
    }

    public String errorMessage() {
        return error.getText();
    }
}
