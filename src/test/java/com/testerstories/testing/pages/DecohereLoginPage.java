package com.testerstories.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DecohereLoginPage {
    private static By loginForm = By.id("openLogin");
    private static By email = By.id("username");
    private static By password = By.id("password");
    private static By signIn = By.id("login");
    private static By notice = By.className("notice");

    public static void logInAs(String user, String pass, WebDriver driver) {
        driver.findElement(loginForm).click();
        driver.findElement(email).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(signIn).click();

    }

    public static String checkMessage(WebDriver driver) {
        return driver.findElement(notice).getText();
    }
}
