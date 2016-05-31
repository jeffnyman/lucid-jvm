package com.testerstories.testing.unit;

import org.testng.annotations.*;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class TestNGDemoJavaTest {
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("beforeSuite()");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("afterSuite()");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("beforeTest()");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("afterTest()");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("beforeClass()");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("afterClass()");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("beforeMethod()");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("afterMethod()");
    }

    @DataProvider(name = "dp1")
    public Object[][] dp1() {
        return new Object[][] { { 1, 2, 3 }, { 4, 5, 9 }, { 6, 7, 13 } };
    }

    private int testAdd(int a, int b) {
        return a + b;
    }

    @Test(groups = "Smoke", dataProvider = "dp1")
    public void verifyAdd(int a, int b, int expected) {
        assertEquals(expected, testAdd(a, b));
    }

    private List<String> testList = Arrays.asList("123", "456");

    @Test(groups = "Smoke")
    public void listLength1() {
        testList = Arrays.asList("abc", "def", "ghi");
        assertEquals(3, testList.size());
    }

    @Test(groups = "Smoke")
    public void listLength2() {
        assertEquals(3, testList.size());
    }
}
