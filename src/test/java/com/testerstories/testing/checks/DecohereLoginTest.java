package com.testerstories.testing.checks;

import com.testerstories.testing.config.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DecohereLoginTest extends DriverFactory {
    @Test
    public void loginAsAdmin() {
        WebDriver driver = DriverFactory.getDriver();
        driver.get("https://decohere.herokuapp.com/");

        WebElement loginForm = driver.findElement(By.id("openLogin"));
        loginForm.click();

        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys("admin@decohere.com");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("admin");

        WebElement signIn = driver.findElement(By.id("login"));
        signIn.click();

        WebElement notice = driver.findElement(By.className("notice"));
        assertThat(notice.getText()).isEqualTo("You are now logged in as admin@decohere.com.");
    }
}
