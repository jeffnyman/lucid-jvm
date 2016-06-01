package com.testerstories.testing.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class SimpleWebTest {
    private void login(String user, String pass) {
        WebDriver driver = new FirefoxDriver();
        driver.get("https://decohere.herokuapp.com/");

        WebElement loginForm = driver.findElement(By.id("openLogin"));
        loginForm.click();

        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(user);

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(pass);

        WebElement signIn = driver.findElement(By.id("login"));
        signIn.click();

        driver.quit();
    }

    @Test
    public void loginAsAdmin() {
        login("admin@decohere.com", "admin");
    }
}
