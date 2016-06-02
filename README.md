# Lucid-JVM

The goal of this repository is to provide a focused example regarding "lucid testing" on the JVM. I will use this read me file as an initial capture of material related to creation. Eventually it will be cleaned up as the repository evolves into a more stable state.

## Notes

With the sixth commit, I believe I see some of the changes on the horizon. My focus is definitely going to be on Gradle and likely using TestNG _and_ JUnit, mainly so that I can leverage different test frameworks. Right now that comes down to leveraging Spock and Geb (with Spock relying on JUnit) and then everything else being executed under TestNG. All of this becomes interesting because it makes me consider how I might structure the execution logic for the tests as well as the reporting.

***

## Polyglot Approach

One of my initial goals in setting up this repository was to leverage the power of the JVM. I wanted to try using multiple languages in the same project and then use Gradle to orchestrate the build for the entire project. I wanted to use Gradle because I prefer its approach to that of the XML of Maven.

### Gradle

As far as Gradle, there is a good (albeit biased, given the source) [Gradle / Maven feature comparison](http://gradle.org/maven_vs_gradle/). I've found that Gradle attempts to maintain the right balance between conventions and configurability.

Coming from a Ruby background, I liked that Gradle build scripts use Groovy. Not only is the Groovy syntax used -- with all of its syntactic terseness and expressiveness -- but this allows for the use of a rich and expressive DSL that provides high-level abstractions to represent common build-related logic. There is an API that Gradle exposes to your build scripts. You consume the API via the DSL. Internally, the declarative layer of Gradle is based on a powerful imperative layer, the latter of which can be accessed directly as required. The [Gradle DSL](https://docs.gradle.org/current/dsl/) is then put on top of that.

Like all DSLs, I found it was important to comprehend the Gradle DSL, rather than just trying to remember the syntax. It was important for me to keep in mind that, as with any DSL, simplifying things for humans can lead into an oversimplification of the interface, which in turn can lead to design issues when dealing with complex functionalities.

#### Gradle Wrapper

The Gradle Wrapper consists of a `gradlew` shell script (for Linux/Mac), a `gradlew.bat` batch script (for Windows), and a few helper files. The idea is that instead of using the system-wide `gradle` command, you can run the builds via the wrapper script. The wrapper script takes care of downloading and installing Gradle if need be. The wrapper uses a specific version of Gradle that your project needs. This means you can safely upgrade (or downgrade) the system-wide Gradle installation without affecting your projects. This is very useful in Continuous Integration build environments because you don't need to install/update Gradle on the servers.

To use the wrapper, just call the wrapper script based on your OS:

* On Mac OS/Linux: `./gradlew taskName`
* On Windows: `gradlew taskName`

### Plugins

I investigated the [standard Gradle plugins](https://docs.gradle.org/current/userguide/standard_plugins.html). The `java` plugin provides the basic functionality for the JVM-based projects. Language specific plugins, such as `scala` and `groovy`, extend the Java plugin to support common idioms in a consistent manner.

Some of the language plugins:

* [Java plugin](https://docs.gradle.org/current/userguide/java_plugin.html)
* [Groovy plugin](https://docs.gradle.org/current/userguide/groovy_plugin.html)
* [Scala plugin](https://docs.gradle.org/current/userguide/scala_plugin.html)

## Test Runner

I went with TestNG over JUnit, although I do use both to show that it is possible.

As far as making the choice, a few good sources are [TestNG vs JUnit](http://blog.tarams.com/index.php/2015/to-test-or-not-to-test-testng-vs-junit/) as well as [JUnit 4 vs TestNG](http://www.mkyong.com/unittest/junit-4-vs-testng-comparison/). JUnit will generally be used more by developers, because of its core focus on unit testing. TestNG is generally favored by testers because it has a focus on different types of testing, not just unit testing.

All this being said, using tools like Spock seems to be a bit easier if you use JUnit. There are many tools and libraries out there that are predicated upon some form of "JUnit runner" and, in many cases, there is no corresponding "TestNG runner."

## Sample Logic

I have a `HashTest`ary that is being used to do nothing more than a simple unit test written in Java, executed by TestNG.

I also have a Groovy service (`GroovyQotdServiceTest`) and a Scala service (`ScalaQotdServiceTest`). Both of those are used to interface with a Java service (`QotdService`). There are unit tests for the Groovy (`GroovyQotdServiceTest`) and Scala (`ScalaQotdServiceTest`) implementations. Of particular note, the Groovy test uses TestNG, but the Scala test must use JUnit. However, the reports from the two are combined (using a the `testReport` task). All of this was done to show the interoperation between JVM languages.

Note that the preceding are a contrived example -- and one that I borrowed in pieces from _Gradle Essentials_ -- where an interface was declared in Java and implemented in Groovy and Scala respectively. This was possible because the classes compiled by the `java` plugin are available to Groovy and Scala classes.

There are a series of JUnit and TestNG unit tests, written for both Java (`JUnitDemoTest`, `TestNGDemoTest`) and Groovy (`JUnitDemoGroovyTest`, `TestNGDemoGroovyTest`). These are in place so that I can continue to experiment with reporting as well as making sure that all execution remains the same across JVM languages. I'm currently putting much less emphasis on Scala and no emphasis at all on Clojure.

I have included some generic Spock material, in the form of a test (`SpockBasicTest`) and two specs (`SpockBasicSpec`, `LifecycleSpec`). These are executed by JUnit and it's not clear to me how to effectively run them via TestNG. I also have a text adventure parser application written in Java (`Parser`, `Command`) that is tested via Spock and Groovy (`ParserActionsSpec`, `ParserSpec`).

### Web-Based Testing

Anything that is focused on browser-based execution will be executed with TestNG, largely so that I can gain the possible benefit of using groups, which JUnit still has some issues with.

As part of testing out web-based solutions, I will be using a miniature Sinatra application I wrote called Decohere. This application can be run remotely by grabbing it from the [Decohere Github repo](https://github.com/jnyman/decohere) or can be run remotely via the [Decohere Heroku app](https://decohere.herokuapp.com/). Using the repo requires you downloading it and having a Ruby environment in which to run it. But it does make it a lot easier to test things out with.

I'll also be periodically using the [representative internet examples app](http://the-internet.herokuapp.com/) to test my logic against common web-based technology implementations.

I will be using the AssertJ as my assertion library. A lot of people seem to like Hamcrest, which is a declarative rule based object matcher framework. But the [core features](http://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html) of AssertJ won me over. The library basically uses a factory method (`Assertions.assertThat()`) to create a type specific assertion. The type specific assertions offer fluent interfaces that are largely polymorphic.

I have a simple test of logging in for bot the Internet Examples (`InternetLoginTest`) and my Decohere login (`DecohereLoginTest`). I also have a test for my "weight on other planets" page (`PlanetWeightTest`). Currently I have a factory class (`DriverFactory`) that each test class extends to remove as much of the WebDriver logic as possible from the test logic.

## Execution

Currently there are two execution modes that make sense to try:

* `gradle clean test`
* `gradle clean testng`

The first command will only execute JUnit based tests, which means the Scala-based Qotd service tests as well as any JUnit unit tests. The second command will execute all TestNG tests. Note that if these two tasks are run together, their test report output will overwrite each other.

## Reporting

JUnit and TestNG on their own generate completely different report formats, but Gradle nicely reconciles them into a standard look and feel.

There are a few reporting modes that can be attempted:

* `gradle clean testReportJUnit`
* `gradle clean testReportTestNG`
* `gradle clean testReport`

The first two commands do what they sound like: they run the JUNit-only tests (the `test` task) and the TestNG-only tests (the `testng` task). The main difference here is that these can be run together and they will generate reports in different directories. This is important when you consider this command:

* `gradle clean test testng`

That would run both test tasks but, because they use the default reporting structure, the results for one would always get overwritten by the other. However, consider this command:
 
* `gradle clean testReportJUnit testReportTestNG`

That would likewise run both test tasks but, in this case, different directories are specified for the reports and thus no possibility of anything being overwritten.

The third command from the list above will execute both `test` and `testng` but will make sure that both reports (from JUnit and TestNG) are combined in the final output. This is different than what I just described before. Running the `testReportJUnit` and `testReportTestNG` tasks will generate different reports in different directories. The `testReport` task, however, will combined the reports. 

The following locations provide the report output for each given task:

* test / testng: `build/reports/tests/index.html`
* testReport: `build/reports/allTests/index.html`
* testReportJUnit: `build/reports/tests/junit/index.html`
* testReportTestNG: `build/reports/tests/testng/index.html`

A report suitable for emailing is generated in the following location: `build/reports/tests/emailable-report.html`. Note that, currently, the emailable report does not combine JUnit and TestNG runs. I'm not convinced of the useful of this report anyway but wanted to explore my reporting options as well as my execution parameters.

I haven't played around with any of this for use with continuous integration yet so I have no idea how well these reports can be generated and synced up via such a system.

### Test Configuration

A file called `testng.xml` is a configuration file for TestNG. It's used to define test suites and tests as well as pass parameters to test methods.

Originally I was using this file for my test configuration but my dislike of XML in general led me to start using the `testng` task in my Gradle build file for the same purpose. I'm not sure if I can do everything in the build file that I can with the TestNG XML configuration file, but I'm going to try. I've also found that you can use the [Gradle test filter](https://docs.gradle.org/current/javadoc/org/gradle/api/tasks/testing/TestFilter.html) if need be.
