package com.testerstories.testing.unit

import org.junit.Test

import static org.junit.Assert.assertEquals

class JUnitDemoGroovyTest {
    @Test
    void JUnitAssertion() {
        def expected = 3
        def actual = 1 + 2
        assertEquals(expected, actual)
    }

    @Test
    void JUnitWithGroovyPowerAssert() {
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
}
