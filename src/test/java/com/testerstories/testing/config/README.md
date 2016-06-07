Here I'll break down a few of the components of the configuration aspects of the framework.

## Configuration

### DriverBase

All tests extend the DriverBase. DriverBase is a relatively small class that holds a a pool of driver objects.

Every page object makes a call to `DriverBase.getDriver()`. Note that this call actually delegates to `DriverFactory.getDriver()`. The DriverBase class uses a DriverFactory instance to get the driver.

Let's hop to DriverFactory for a second.

### DriverFactory

The DriverFactory class holds a reference to a WebDriver object, and ensures that every time you call `getDriver()` you get a valid instance of WebDriver back. If a WebDriver instance has been started up, you will get the existing one. If one hasn't been started up, the factory will start one for you.

Now let's hop back to DriverBase.

### DriverBase

I'm using a ThreadLocal object to instantiate the DriverFactory objects in separate threads. That's what `createDriver()` is doing. Note that this method is annotated with TestNG's `@BeforeSuite` annotation.

What @BeforeSuite means is that the annotated method will be run before all tests in a given suite have run. A suite is defined by one or more tests and can be represented by one XML file which TestNG will use to run the tests. The XML file will define the set of tests to be run. It is represented by the `<suite>` tag and it is the first tag that appears in the XML file. However, even if you don't have such a file, then all of the tests in the current run cycle are considered part of a suite. So what that means with my current repository is that `createDriver()` is called before any tests are run.

The base also has a `getDriver()` method that uses the `getDriver()` method on the DriverFactory object to pass each test a WebDriver instance it can use. The rationale here is to isolate each instance of WebDriver to make sure that there is no cross contamination between tests. When the tests start running in parallel, I don't want different tests to start firing commands to the same browser window. Each instance of WebDriver is now safely locked away in its own thread.

Since the base class is being used to start up all the browser instances, it's important to make sure that the browsers get closed down as well. To do this the `quitDriver()` method has an `@AfterMethod` annotation that will destroy the driver after any test has run. The TestNG `@AfterMethod` annotation does just what it sounds like: it runs the annotated method after each test method. This is what makes sure that a new browser is provided for each test.

Having the base handle this quitting of browser instances also has the added advantage of cleaning up if any given test fails to reach the line where it would normally call `driver.quit()` -- for example, if there was an error in the test that caused it to fail and finish early.

What all of this means is that any TestNG-based tests should extend DriverBase. Thus instead of instantiating a new FirefoxDriver (or other browser) instance in the test, the test calls DriverBase.getDriver() to get a valid WebDriver instance. Tests should also not include any calls to `driver.quit()` because that's also handled by the DriverBase class.

Since the base is delegating to the DriverFactory, let's hop back to that.

### DriverFactory

This class provides a `quitDriver()` method that will perform a `quit()` -- from the Selenium WebDriver API -- on your WebDriver object. This method also nullifies the WebDriver object held in the class. This prevents errors that would be caused by attempting to interact with a WebDriver object that has been closed.

The `getDriver()` method is a bit more interesting.

The DriverFactory class reads in a browser that has been specified, if any. This browser information is pulled in via a system property called “browser”.

It will help to understand DriverType a bit first.

### DriverType

The DriverType enumeration implements the DriverSetup interface. Okay, so let’s talk about DriverSetup first. (It's really simple.)

### DriverSetup

DriverSetup is a very simple interface that DriverType will implement. This means each enum entry DriverType must implement a `getDesiredCapabilities()` method and a `getWebDriver()` method.

See? That was simple. Now back to DriverType.

### DriverType

The basic enum allows you to choose one of the default browsers supported by Selenium. This setup lets you get a default set of capabilities that you can extend, if required. These desired capabilities can then be used to instantiate a new WebDriver object when calling the getWebDriver() method.

The DriverType is going to be the place that instantiates a driver object so this is the best place to add any capabilities you might need for a given web driver.

I do use a [WebDriver Manager](https://github.com/bonigarcia/webdrivermanager) here. What this does is allow for the automatic downloading of browser drivers.

Now let's hope back to DriverFactory and continue with the getDriver() method.

### DriverFactory

The DriverFactory class sets a default browser type, in case one has not been specified. This class also holds the type of driver that is going to be used.

The main thing here is that the `determineDriver()` method will take the browser variable and then try and work out which enum value maps across to the value it holds. If it cannot map a DriverType across to the value of browser, it will log an error and then default to the defaultDriver.

Remote execution comes into play here as well. I'm using a `useRemoteWebDriver` boolean to work out whether I want to instantiate a normal WebDriver object or a RemoteWebDriver object. If I want to instantiate a RemoteWebDriver object I start off by reading in the system properties that are set in the gradle build file or from the command line. The most important bit of information is gridURL. If this isn't available, then I don't know where to go to connect to the grid. My solution reads in the system property and tries to generate a URL from it. If the URL is not valid an `InvalidURLException` will be thrown; this is good because I won't be able to connect to a grid anyway at this point so there's no point trying to run the checks.

The other two bits of information are optional. If a gridPlatform and gridBrowserVersion are supplied, the Selenium Grid will use an agent matching these criteria. If this information isn't supplied then the Selenium Grid will just grab any free agent and run the checks against it. With the code as it is set up, each DesiredCapabilities object will set a browser type by default. So if I create DesiredCapabilities.firefox(), I'll be asking the Selenium Grid to run my check against Firefox.

## Thread / Parallel with Gradle and TestNG

The next couple of sections will talk about parallel execution as well as execution among threads.

Looking in the `build.gradle` you'll sell the the `useTestNG { parallel = ...; threadCount = ...; }` construct. This is the TestNG feature to execute tests in multiple threads (one JVM). You'll also see `test.maxParallelForks`, which is the generic Gradle feature to execute (JUnit or TestNG) tests in multiple JVMs (one thread per JVM).
 
It's entirely possible to run into some bad interaction between TestNG features and Gradle test parallelization, or TestNG parallelization and Gradle test features. I may be running into some of that with this current implementation of how threading is working.

## Parallel Execution

Gradle can execute tests in parallel. This means Gradle will start multiple test processes concurrently. A test process only executes a single test at a time. By enabling parallel test execution, the total execution time of the test task can drastically decrease, if you have a lot of tests.

When tests reach a certain level of proliferation within a project, there is a motivation to run them in parallel to get the results faster. However, there would be a great overhead to running every unit test in its own JVM. Gradle provides an intelligent compromise in that it offers a maxParallelForks setting that governs the maximum simultaneous JVMs that are spawned.

To get parallel execution, you need to specify the number of forked JVM processes. You have to use the maxParallelForks property to set how many test processes you want to run in parallel. The default value is 1, which means that the tests don't run in parallel. Each test process sets a system property of the name org.gradle.test.worker with a unique value.

All this being said, if you have a lot of tests that are executed by a single test process, you might get heap size or PermGen problems. So you can also set the number of maximum test classes to execute per forked test process. With the property forkEvery, we can set how many tests need to run in a single test process, before a new test process is started to execute more tests. So, if Gradle sees that the number of tests exceeds the given number assigned to the forkEvery property, the test process is restarted and the following set of tests is executed.

Keep in mind the rationale here. Tests, in their quest to touch everything and exercise as much as possible, can cause unnatural pressure on the JVM’s memory allocation. In short, it is what Java developers term a “leak”. It can merely be the loading of every class causing the problem. This isn’t really a leak since the problem stems from the fact that loaded class definitions are not garbage collected but instead are loaded into permgen space. The forkEvery setting causes a test-running JVM to close and be replaced by a brand new one after the specified number of tests have run under an instance. Though these two settings have very different goals, they may often be seen used in combination when a project has a large battery of tests.

To be clear on the settings:

* `maxParallelForks`: The maximum number of forked test processes to execute in parallel. The default value is 1 (no parallel test execution).
* `forkEvery`: The maximum number of test classes to execute in a forked test process. The forked test process will be restarted when this limit is reached. The default value is 0 (no maximum).

### Calculate Parallel Execution

In `build.gradle` I use a simple formula to calculate the number of forks by available processors on a given machine. This is what gets stored in maxParallelForks. The "divide by 2" part of the formula tries to account for virtual vs. physical cores, and has to be adapted depending on your hardware. Depending on the kind of tests (CPU vs. IO bound) and specification/load of the machine, a number lower or higher than the number of cores may work better. The best thing to do is to experiment.

To visualize the execution behavior based on a test suite with 18 test classes, consider that the number of parallel test processes is calculated based on the number of logical cores available to your JVM, either virtual or physical. Let's assume this number is four. Therefore, the assigned value of the property maxParallelForks is 2. With the property forkEvery set to 5, each forked test process will execute a group of five test classes.

### Forking vs Threading

A fork is nothing more than a new process that looks exactly like the parent process but still is a different process with different process ID and having it's own memory. In contrast, threads are considered Light Weight Processes. Traditionally, a thread is just a CPU state -- and some other minimal state) -- with the process containing everything else (data, stack, I/O, signals).

Threads require less overhead than forking or spawning a new process because the system does not initialize a new system virtual memory space and environment for the process. That said, threads are more subject to race conditions and there is an extra emphasis of making sure that code is thread safe.

## Threading

_NOTE:_ There are still some kinks in threaded execution that I'm attempting to work out.

By default, the use of TestNG plus Gradle means that your execution suite will already run with a certain number of threads. Specifically, each test method will be considered for execution in a separate thread given the resources of the current machine that the tests are executing against. You can change the number of threads by doing this:

    gradle clean testng -Dthreads=4

There are two settings you can use specific to TestNG and these settings are handled in my `build.gradle` file.

* The setting `parallel="methods"` means that TestNG will run all your test methods in separate threads. Dependent methods will also run in separate threads but they will respect the order that you specified.

* The setting `parallel="tests"` means that TestNG will run all the methods in the same tag in the same thread, but each tag will be in a separate thread. This allows you to group all your classes that are not thread safe in the same and guarantee they will all run in the same thread while taking advantage of TestNG using as many threads as possible to run your tests.

In this case, I've set the `parallel` configuration setting to `methods`. This will search through the project for methods that have the `@Test` annotation and will collect them all into a single pool of tests. The failsafe plugin will then take tests out of this pool and run them. The number of tests that will be run concurrently will depend on how many threads are available. The `threadCount` property can be used to control how many threads are used.

### Thread Considerations

With very small tests (or very small sets of tests), you will not see some massive decrease in the time taken to run the complete suite just because you increased the number of threads. This is because most of the time is spent compiling the code (a downside of Java) and loading up browsers. However, as you add more tests, the decrease in the time taken to run the tests ideally becomes more and more apparent. However, this also depends on how long each given test method runs which is a direct function of how much it is checking as part of its execution.

There's no magic solution here. You have to play around with the number of threads and see how many concurrent browsers you can get up-and-running at the same time. This may even differ depending on which browser you try this with. You'll want to note your execution times to see what speed gains you are actually getting. Clearly resources aren't infinite, so there will come a point where you reach the limits of your computer's hardware and, in that case, adding more threads will actually slow things down rather than making them faster. Tuning your tests to your hardware environment is an important part of running your tests in multiple threads.

One major note: starting up a web browser is a computationally intensive task, particularly with how the Java bindings do it. So you could choose not to close the browser after every test. This is as opposed to how I currently do it, where each test method creates a new browser instance. (I handle the closing of the browser after each test method in the DriverFactory class.) Based on your previous experimentation, you will probably have a rough idea of what the sweet spot for your hardware is in terms of what you can support to get execution time down as much as possible.

You can then time how long it takes to execute your tests again when you close all the browsers down when all the tests have finished executing, as opposed to after each test. It's also important to keep in mind that not closing the browser obviously can have some side effects. Probably one of the most serious is that it can lead to leaky tests: meaning session state is not cleared out and this can be very detrimental to execution.