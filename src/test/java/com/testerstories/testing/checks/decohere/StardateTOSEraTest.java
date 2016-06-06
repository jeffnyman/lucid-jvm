package com.testerstories.testing.checks.decohere;

import com.testerstories.testing.DriverBase;
import com.testerstories.testing.config.Setting;
import com.testerstories.testing.pages.decohere.App;
import com.testerstories.testing.pages.decohere.StardatePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StardateTOSEraTest extends DriverBase {
    @BeforeMethod
    public void setUp() {
        String domain = Setting.useSetting("domain");
        getDriver().manage().window().maximize();
        //getDriver().manage().window().setSize(new Dimension(1441, 900));
        getDriver().get(domain);

        app = new App();
        app.login.asAdmin();
        app.navigate.toStardate();
    }

    @Test
    public void convertTOSStardate() {
        stardatePage = new StardatePage();
        stardatePage.verifyCalendarDateForTOSStardate(1312.4);

        assertThat(stardatePage.calendarValue()).contains("Mon Oct 23 2265");
    }
}
