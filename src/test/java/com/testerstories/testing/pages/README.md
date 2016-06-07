## Page Objects

You need to design a test framework that keeps the changes that you need to make the test cases to a minimum. An efficient framework is one that needs minimal refactoring to adapt to new changes in the target application. The PageObject pattern is one such design pattern that can be used for this purpose.

The nice thing about this pattern is that you can build extensible page objects to drive the tests. You can also make a readable DSL using the page objects. I have examples of each of these usages. This being said, I think it’s important not to get lost in making clever abstractions such that it’s hard for someone to figure out how to construct tests in your framework.

As is now widely recognized in the industry, “page objects” -- with an emphasis on the word “page” -- are somewhat poorly named. They don't have to refer to a page: just a collection of related elements that may interact together. In many cases, this will be an entire page, but in other cases, it may be a part of a page or a component that is reused across many pages.

### Finding Elements

All page objects are stored in pages package. In all cases, I'm using a PageFactory to predefine a series of WebElements. These WebElement objects into proxied objects using a Java Proxy class. Annotations transform these proxied objects into real WebElement objects.

Basically how this works is the FindBy annotation provides a way to pass a By object into a driver.findElement() call to create a WebElement. This driver.findElement() call is completely transparent and is performed in the background whenever a script uses the WebElement that has an annotation applied to it. It's also important to note that I get the driver object as part of initializing the page factory, all of which is done in the constructor of the page object. This allows me to avoid the situation where I'm passing around a reference to the driver object wherever it may be needed as part of script execution.

### Caching Elements

Using the @FindBy annotation means that every time you call a method on the WebElement object, the driver will go and find it on the current page again. That's certainly useful in applications where elements are dynamically loaded or when you have AJAX-heavy applications that provide a lot of DOM state changes on the fly. However, in applications where you know that the element is always going to be there and stay as it is originally loaded, without any changes, you can cache the element once it's found.

You can use @CacheLookup under your @FindBy annotation. This tells the the PageFactory to cache the element once it's located. In general, tests work faster with cached elements when those elements are used repeatedly.

## Checks and Pages

If you look at LoginTest, you'll see an example of a fluent API (in this case, via method chaining) in the invalidLogin() test method.

You'll also see another approach to this via the idea of page objects that return objects, which allow you to chain calls to instances. You can see this in the loginAsAdmin() method of that same test class.

There's a couple of approaches happening here. In one case, I return page objects from methods in a page object. This gets into the debate about page objects returning page objects.

My view is that I try to avoid this in general. I always avoid returning the same page objects if I'm on the same page and state of the page is not changing. The only reason to return a page object would be if you are navigating from one page to another. It wouldn't make sense to return the same object when you want to get some text or get a selected option from a page, since essentially nothing changed. Some argue that if the state of the page is changing, then you would need to return the new page object otherwise you may likely face StaleElementException. But if you are proxying the web elements in your page object, then this isn't true because each find of the object should find the updated web element.

If you look at PlanetWeightTest or the Stardate tests, you'll see approaches which show a way to be a bit more fluent with test expression, as compared to the commented out parts which show as slightly less fluent approach.

What's happening here is that I instantiate page objects from within page objects. Specifically, see the App class, I instantiated a page object from a page object. I made this public so that anyone who creates a new instance of the parent page object automatically gets to use all the subpage objects that were defined in the parent as well. This allows you to stream the actions of each page object together, making a very concise, readable line of code.

So the point here is that a fluent interface is an API that uses chains of commands to describe the action(s) that you are performing. Each chained command will return either a reference to itself, a reference to a new method, or a void.

You'll find a series of protected page references in DriverBase. These mean the pages are always declared which would be slightly less boilerplate in your code.