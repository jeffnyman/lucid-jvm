package com.testerstories.testing.checks;

import com.testerstories.testing.config.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InternetLoginTest extends DriverFactory {
    @Test
    private void login() {
        WebDriver driver = DriverFactory.getDriver();
        driver.get("http://the-internet.herokuapp.com/login");

        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("tomsmith");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("SuperSecretPassword!");

        WebElement login = driver.findElement(By.tagName("button"));
        login.click();

        WebElement alert = driver.findElement(By.className("success"));
        assertThat(alert.getText()).contains("You logged into a secure area!");
    }
}
