package com.testerstories.testing.qotd

import java.util.{Calendar, Date}

import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ScalaQotdServiceTest extends SpecificationWithJUnit {
  def service = new ScalaQotdService(Seq(
    "Be the change you wish to see in the world" + " - Mahatma Gandhi",
    "A person who never made a mistake never tried anything new" + " - Albert Einstein"
  ))

  val today = new Date()
  val tomorrow = incrementDay(today)
  val dayAfterTomorrow = incrementDay(tomorrow)

  def incrementDay(date: Date) = {
    val cal = Calendar.getInstance()
    cal.setTime(date)
    cal.add(Calendar.DATE, 1)
    cal.getTime
  }

  "Quote service" should {
    "return same quote for same date" in {
      service.getQuote(today) must be(service.getQuote(today))
    }

    "return different quote for different dates" in {
      service.getQuote(today) must not be(service.getQuote(tomorrow))
    }

    "repeat quotes on each alternate day" in {
      service.getQuote(today) must be(service.getQuote(dayAfterTomorrow))
    }
  }
}
