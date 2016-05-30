package com.testerstories.testing.qotd

class GroovyQotdService implements QotdService {
    List quotes

    GroovyQotdService(List quotes) {
        this.quotes = quotes
    }

    @Override
    String getQuote(Date day) {
        quotes[day[Calendar.DAY_OF_YEAR] % quotes.size()]
    }
}