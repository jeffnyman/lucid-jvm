package com.testerstories.testing.unit;

import org.junit.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JUnitDemoJavaTest {
    @BeforeClass
    public static void setUpBeforeClass() {
        System.out.println("setUpBeforeClass()");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        System.out.println("tearDownAfterClass()");
    }

    @Before
    public void setUp() {
        System.out.println("setUp()");
    }

    @After
    public void tearDown() {
        System.out.println("tearDown()");
    }

    private List<String> testList = Arrays.asList("123", "456");

    @Test
    public void listLength1() {
        testList = Arrays.asList("abc", "def", "ghi");
        assertEquals(3, testList.size());
    }

    @Test
    public void listLength2() {
        assertEquals(2, testList.size());
    }
}
