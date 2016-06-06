package com.testerstories.testing.pages.decohere;

import com.testerstories.testing.DriverBase;
import com.testerstories.testing.config.Setting;
import com.testerstories.testing.helpers.Selenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Authentication {
    private static String admin_username = Setting.useSetting("decohere_admin_username");
    private static String admin_password = Setting.useSetting("decohere_admin_password");
    private Selenium selenium;

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
        PageFactory.initElements(DriverBase.getDriver(), this);
        selenium = new Selenium(DriverBase.getDriver());
    }

    public void logInAsAdmin() {
        loginForm.click();

        selenium.waitForPresence(email);

        email.sendKeys(admin_username);
        password.sendKeys(admin_password);

        signIn.click();
    }

    public Authentication enterEmail(String email_user) {
        openAuthentication();
        selenium.waitForPresence(email);
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
        logInAsAdmin();
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
