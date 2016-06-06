package com.testerstories.testing.pages.decohere;

import com.testerstories.testing.DriverBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class StardatePage {
    @FindBy(id = "enableForm")
    private WebElement enableForm;

    @FindBy(id = "tngEra")
    private WebElement tngEra;

    @FindBy(id = "tosEra")
    private WebElement tosEra;

    @FindBy(id = "convert")
    private WebElement convert;

    @FindBy(id = "stardateValue")
    private WebElement stardate;

    @FindBy(id = "calendarValue")
    private WebElement calendarDate;

    public StardatePage() {
        PageFactory.initElements(DriverBase.getDriver(), this);
    }

    public void verifyCalendarDateForTNGStardate(double value) {
        enableStardateForm();
        setTngEra();
        convertStardateValue(value);

        String result = calendarDate.getAttribute("value");
        Calendar calendar = modelTNGCalculation(value);

        int year = calendar.get(Calendar.YEAR);
        String month = new SimpleDateFormat("MMM").format(calendar.getTime());
        int date = calendar.get(Calendar.DATE);

        assertThat(result).as("Year was not valid").contains(Integer.toString(year));
        assertThat(result).as("Month was not valid").contains(month);
        assertThat(result).as("Date was not valid").contains(Integer.toString(date));
    }

    public void verifyCalendarDateForTOSStardate(double value) {
        enableStardateForm();
        setTosEra();
        convertStardateValue(value);

        String result = calendarDate.getAttribute("value");
        Calendar calendar = modelTOSCalculation(value);

        int year = calendar.get(Calendar.YEAR);
        String month = new SimpleDateFormat("MMM").format(calendar.getTime());
        int date = calendar.get(Calendar.DATE);

        assertThat(result).as("Year was not valid").contains(Integer.toString(year));
        assertThat(result).as("Month was not valid").contains(month);
        assertThat(result).as("Date was not valid").contains(Integer.toString(date));
    }

    public String calendarValue() {
        return calendarDate.getAttribute("value");
    }

    private Calendar modelTNGCalculation(double value) {
        double stardatesPerYear = value * 34367056.4;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss");
        Date origin = Date.from(LocalDateTime.parse("July 5, 2318 12:00:00", dtf).toInstant(ZoneOffset.UTC));

        double milliseconds = origin.getTime() + stardatesPerYear;
        Date dateResult = new Date();
        dateResult.setTime((long)milliseconds);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateResult);
        return calendar;
    }

    private Calendar modelTOSCalculation(double value) {
        double stardatesPerYear = value * 60 * 60 * 24 * 365.2422 / 2.63510833;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss");
        Date origin = Date.from(LocalDateTime.parse("April 25, 2265 00:00:00", dtf).toInstant(ZoneOffset.UTC));

        double milliseconds = origin.getTime() + stardatesPerYear;
        Date dateResult = new Date();
        dateResult.setTime((long)milliseconds);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateResult);
        return calendar;
    }

    private void enableStardateForm() {
        enableForm.click();
        assertThat(enableForm.isSelected()).isTrue();
    }

    private void setTngEra() {
        tngEra.click();
        assertThat(tngEra.isSelected()).isTrue();
    }

    private void setTosEra() {
        tosEra.click();
        assertThat(tosEra.isSelected()).isTrue();
    }

    private void convertStardateValue(double value) {
        stardate.clear();
        stardate.sendKeys(Double.toString(value));
        convert.click();
    }
}
