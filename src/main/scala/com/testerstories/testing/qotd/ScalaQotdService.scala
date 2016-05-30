package com.testerstories.testing.qotd

import java.util.{Calendar, Date}

class ScalaQotdService(quotes: Seq[String]) extends QotdService {
  def getQuote(day: Date) = {
    val calendar = Calendar.getInstance()
    calendar.setTime(day)

    quotes(calendar.get(Calendar.DAY_OF_YEAR) % quotes.size)
  }
}
