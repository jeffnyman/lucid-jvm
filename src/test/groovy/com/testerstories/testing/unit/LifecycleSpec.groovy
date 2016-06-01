package com.testerstories.testing.unit

import spock.lang.Specification
import spock.lang.Unroll

class LifecycleSpec extends Specification {
    def setup() {
        println "Setup prepares next run"
    }

    @Unroll("Adder test #first + #second = #sum")
    def "simple addition"() {
        given: "a way to perform additions"
        Adder adder = new Adder()
        println "Given: block runs"

        when: "addition is performed"
        int result = adder.add(first,second)
        println "When: block runs for first = $first and second = $second"

        then: "the result should be the sum"
        result == sum
        println "Then: block is evaluated for sum = $sum"

        where: "examples"
        first | second || sum
        1     | 1      || 2
        3     | 2      || 5
        3     | -3     || 0
    }

    def cleanup() {
        println "Cleanup releases resources of last run\n"
    }
}