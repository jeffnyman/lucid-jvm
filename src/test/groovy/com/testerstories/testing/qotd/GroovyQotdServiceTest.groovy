package com.testerstories.testing.qotd

import org.testng.Assert
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

public class GroovyQotdServiceTest {
    QotdService service
    Date today, tomorrow, dayAfterTomorrow

    def quotes = [
            "Be the change you wish to see in the world" + " - Mahatma Gandhi",
            "A person who never made a mistake never tried anything new" + " - Albert Einstein"
    ]

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        service = new GroovyQotdService(quotes)
        today = new Date()
        tomorrow = today + 1
        dayAfterTomorrow = tomorrow + 1
    }

    @Test(groups = "Smoke")
    void "return same quote for same date"() {
        Assert.assertEquals(service.getQuote(today), service.getQuote(today))
    }

    @Test(groups = "Smoke")
    void "return different quote for different dates"() {
        Assert.assertNotSame(service.getQuote(today), service.getQuote(tomorrow))
    }

    @Test(groups = "Smoke")
    void "repeat quotes on each alternate day"() {
        Assert.assertEquals(service.getQuote(today), service.getQuote(dayAfterTomorrow))
    }
}