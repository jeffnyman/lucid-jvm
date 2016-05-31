package com.testerstories.testing.spock

import spock.lang.Specification

class SpockBasicTest extends Specification {
    def "Spock class name suffix is 'Test'."() {
        setup:
        def str = 'Spock class name suffix is'

        when:
        str += ' "Test".'

        then:
        str == 'Spock class name suffix is "Test".'

        cleanup:
        println str
    }
}