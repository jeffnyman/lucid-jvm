package com.testerstories.testing.pages.decohere;

import com.testerstories.testing.config.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Authentication {
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

    @FindBy(css = "ul.nav > li")
    private WebElement loginFormState;

    Authentication() {
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }

    public void logInAs(String user, String pass) {
        loginForm.click();
        email.sendKeys(user);
        password.sendKeys(pass);
        signIn.click();
    }

    public Authentication enterEmail(String email_user) {
        openAuthentication();
        email.sendKeys(email_user);
        return this;
    }

    public Authentication enterPassword(String pass) {
        openAuthentication();
        password.sendKeys(pass);
        return this;
    }

    public void andLogin() {
        signIn.click();
    }

    public void asAdmin() {
        logInAs("admin@decohere.com", "admin");
    }

    private boolean isFormOpen() {
        // Alternative: Use aria-expanded of openLogin; if true, form is open
        String openState = loginFormState.getAttribute("class");
        return openState.contains("open");
    }

    private void openAuthentication() {
        if (!isFormOpen()) {
            loginForm.click();
        }
    }
}
