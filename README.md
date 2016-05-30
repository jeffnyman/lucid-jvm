# Lucid-JVM

The goal of this repository is to provide a focused example regarding "lucid testing" on the JVM. I will use this read me file as an initial capture of material related to creation. Eventually it will be cleaned up as the repository evolves into a more stable state.

## Polyglot Approach

One of my initial goals in setting up this repository was to leverage the power of the JVM. I wanted to try using multiple languages in the same project and then use Gradle to orchestrate the build for the entire project. I wanted to use Gradle because I prefer its approach to that of the XML of Maven.

### Gradle

As far as Gradle, there is a good (albeit biased, given the source) [Gradle / Maven feature comparison](http://gradle.org/maven_vs_gradle/). I've found that Gradle attempts to maintain the right balance between conventions and configurability.

Coming from a Ruby background, I liked that Gradle build scripts use Groovy. Not only is the Groovy syntax used -- with all of its syntactic terseness and expressiveness -- but this allows for the use a rich and expressive DSL that provides high-level abstractions to represent common build-related logics. There is an API that Gradle exposes to your build scripts. You consume the API via the DSL. Internally, the declarative layer of Gradle is based on a powerful imperative layer, which can be accessed directly as required. The [Gradle DSL](https://docs.gradle.org/current/dsl/) is then put on top of that.

Like all DSLs, I found it was important to comprehend the Gradle DSL, rather than just trying to remember the syntax. It was important for me to keep in mind that, as with any DSL, simplifying things for humans could lead into an oversimplification of the interface, which in turn could lead to design issues when dealing with complex functionalities.

#### Gradle Wrapper

The Gradle Wrapper consists of a `gradlew` shell script (for Linux/Mac), a `gradlew.bat` batch script (for Windows), and a few helper files. The idea is that instead of using the system-wide `gradle` command, you can run the builds via the wrapper script. The wrapper script takes care of downloading and installing Gradle if need be. It uses a specific version of Gradle that your project needs. This means you can safely upgrade (or downgrade) the system-wide Gradle installation without affecting your projects. This is very useful in Continuous Integration build environments because you don't need to install/update Gradle on the servers.

To use it, just call the wrapper script based on your OS:

* On Mac OS X/Linux: `./gradlew taskName`
* On Windows: `gradlew taskName`

### Plugins

So I investigated the [standard Gradle plugins](https://docs.gradle.org/current/userguide/standard_plugins.html). The java plugin provides the basic functionality for the JVM-based projects. Language specific plugins such as scala and groovy extend the java plugin to support common idioms in a consistent manner.

Some of the language plugins:

* [Java plugin](https://docs.gradle.org/current/userguide/java_plugin.html)
* [Groovy plugin](https://docs.gradle.org/current/userguide/groovy_plugin.html)
* [Scala plugin](https://docs.gradle.org/current/userguide/scala_plugin.html)

## Test Runner

I went with TestNG over JUnit, although I do use both to show that it is possible.

As far as making the choice, a few other good sources are [TestNG vs JUnit](http://blog.tarams.com/index.php/2015/to-test-or-not-to-test-testng-vs-junit/) as well as [JUnit 4 vs TestNG](http://www.mkyong.com/unittest/junit-4-vs-testng-comparison/). JUnit will generally be used more by developers, because of its core focus on unit testing. TestNG is generally favored by testers because it has a focus on different types of testing, not just unit testing.

## Sample Logic

I have a `HashTest` that is being used to do nothing more than a simple unit test written in Java, executed by TestNG.

I also have a Groovy service (`GroovyQotdServiceTest`) and a Scala service (`ScalaQotdServiceTest`). Both of those are used to interface with a Java service (`QotdService`). There are unit tests for the Groovy (`GroovyQotdServiceTest`) and Scala (`ScalaQotdServiceTest`) implementations. Of particular note, the Groovy test uses TestNG, but the Scala test must use JUnit. However, the reports from the two are combined (using a the `testReport` task). All of this was done to show the interoperation between JVM languages.

Note that the preceding are a contrived example -- and one that I borrowed in pieces from _Gradle Essentials_ -- where an interface was declared in Java and implemented in Groovy and Scala respectively. This was possible because the classes compiled by the `java` plugin are available to Groovy and Scala classes.

## Execution

Currently there are three execution modes that make sense to try:

* `gradle clean test`
* `gradle clean testng`
* `gradle clean testReport`

The first will only execute JUnit based tests, which mean the Scala-based Qotd service tests. The second will execute all TestNG tasks. Do note that there is a `testng.xml` file in place which further constrains the test parameters. Note that if these two tasks are run together, their test report output will overwrite each other. The third will execute both `test` and `testng` but will make sure that both reports (from JUnit and TestNG) are combined in the final output.
