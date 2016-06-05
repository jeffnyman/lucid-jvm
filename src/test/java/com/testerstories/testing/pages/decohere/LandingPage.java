package com.testerstories.testing.pages.decohere;

import com.testerstories.testing.config.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {
    @FindBy(className = "notice")
    private WebElement notice;

    @FindBy(linkText = "Planet Weight Calculator")
    private WebElement weightCalculator;

    @FindBy(linkText = "Stardate Calculator")
    private WebElement stardateCalculator;

    @FindBy(linkText = "Project Overlord")
    private WebElement projectOverlord;

    public Navigation navigation = new Navigation();

    public LandingPage() {
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }

    public String noticeMessage() {
        return notice.getText();
    }
}
