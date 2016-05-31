package com.testerstories.testing.unit

import org.testng.annotations.DataProvider

import static org.testng.Assert.assertEquals

import org.testng.annotations.AfterClass
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterSuite
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.BeforeSuite
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test

class TestNGDemoGroovyTest {
    @BeforeSuite
    void beforeSuite() {
        println("beforeSuite()")
    }

    @AfterSuite
    void afterSuite() {
        println("afterSuite()")
    }

    @BeforeTest
    void beforeTest() {
        println("beforeTest()")
    }

    @AfterTest
    void afterTest() {
        println("afterTest()")
    }

    @BeforeClass
    void beforeClass() {
        println("beforeClass()")
    }

    @AfterClass
    void afterClass() {
        println("afterClass()")
    }

    @BeforeMethod
    void beforeMethod() {
        println("beforeMethod()")
    }

    @AfterMethod
    void afterMethod() {
        println("afterMethod()")
    }

    @Test(groups = "Smoke")
    void TestNGFromGroovy() {
        def list = [100, 200, 300]
        assertEquals(list.size(), 3)
    }

    @Test(groups = "Smoke")
    void TestNGWithGroovyPowerAssert() {
        def s1 = [1, 2, 3, 3, 4, 1, 3, 2] as Set
        def s2 = [4, 2, 1, 3] as Set
        def s3 = new HashSet([1, 3, 2, 4, 4])
        assert s1 == s2
        assert s2 == s3
        assert s3 == s1

        def l1 = [100, 200, 300]
        def l2 = [100, 200, 300]
        assert l1 == l2

        def m1 = [
                key1: 100,
                key2: [100, 200, 300],
                key3: [k1: 100, k2: 200, k3: 300],
                key4: null,
        ]
        def m2 = [
                key1: 100,
                key2: [100, 200, 300],
                key3: [k1: 100, k2: 200, k3: 300],
                key4: null,
        ]
        assert m1 == m2
    }

    @DataProvider(name = "dp1")
    Object[][] dp1() {
        return [
                [1, 2, 3] as Object[],
                [4, 5, 9] as Object[],
                [6, 7, 13] as Object[],
        ] as Object[][]
    }

    static int testAdd(int a, int b) {
        return a + b
    }

    @Test(groups = "Smoke", dataProvider = "dp1")
    void verifyAdd(int a, int b, int expected) {
        assertEquals(testAdd(a, b), expected)
    }

    @DataProvider(name = "dp2")
    Object[][] dp2() {
        return [
                [1, 2, 3],
                [4, 5, 9],
                [6, 7, 13]
        ]
    }

    @Test(dataProvider = "dp2")
    void verifyAddAgain(int a, int b, int expected) {
        assert testAdd(a, b) == expected;
    }

    @DataProvider(name = "dp3")
    Object[][] dp3() {
        return [
                ["", "", 0],
                ["a", "", 1],
                ["", "a", 1],
                ["a", "b", 2],
        ]
    }

    @Test(groups = "Smoke", dataProvider = "dp3")
    void verifyDataSample(String a, String b, int expected) {
        assert (a + b).length() == expected
    }
}
