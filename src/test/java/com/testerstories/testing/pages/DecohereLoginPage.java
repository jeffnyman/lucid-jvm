package com.testerstories.testing.pages;

import com.testerstories.testing.config.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DecohereLoginPage {
    @FindBy(id = "openLogin")
    private WebElement loginForm;

    @FindBy(id = "username")
    private WebElement email;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "login")
    private WebElement signIn;

    @FindBy(className = "notice")
    private WebElement notice;

    public DecohereLoginPage() {
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }

    public void logInAs(String user, String pass) {
        loginForm.click();
        email.sendKeys(user);
        password.sendKeys(pass);
        signIn.click();
    }

    public String checkMessage() {
        return notice.getText();
    }
}
