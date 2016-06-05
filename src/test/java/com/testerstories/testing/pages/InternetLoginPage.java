package com.testerstories.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InternetLoginPage {
    private static By username = By.id("username");
    private static By password = By.id("password");
    private static By login = By.tagName("button");
    private static By alert = By.className("success");

    public static void logInAs(String user, String pass, WebDriver driver) {
        driver.findElement(username).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(login).click();
    }

    public static String checkAlert(WebDriver driver) {
        return driver.findElement(alert).getText();
    }
}
