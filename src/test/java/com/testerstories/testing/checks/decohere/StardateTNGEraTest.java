package com.testerstories.testing.checks.decohere;

import com.testerstories.testing.config.DriverFactory;
import com.testerstories.testing.pages.decohere.App;
import com.testerstories.testing.pages.decohere.StardatePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StardateTNGEraTest extends DriverFactory {
    @BeforeMethod
    public void setUp() {
        getDriver().get("https://decohere.herokuapp.com/");
        getDriver().manage().window().maximize();
        //getDriver().manage().window().setSize(new Dimension(1441, 900));

        app = new App();
        app.login.asAdmin();
        app.navigate.toStardate();
    }

    @Test
    public void convertTNGStardate() {
        stardatePage = new StardatePage();
        stardatePage.verifyCalendarDateForTNGStardate(56844.9);

        assertThat(stardatePage.calendarValue()).contains("Sat May 31 2380");
    }
}
