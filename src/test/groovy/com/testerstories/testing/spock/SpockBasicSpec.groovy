package com.testerstories.testing.spock

import spock.lang.Specification

class SpockBasicSpec extends Specification {
    def setup() { println 'fixture method(setup)' }
    def cleanup() { println 'fixture method(cleanup)' }
    def setupSpec() { println 'fixture method(setupSpec)' }
    def cleanupSpec() { println 'fixture method(cleanupSpec)' }

    def "Spock Test 1"() {
        setup:
        def map = [:]
        map['abc'] = 100

        when:
        map['def'] = 200

        then:
        matchHelper(map)

        cleanup:
        map.clear()
    }

    def "Spock Test 2"() {
        expect:
        name.size() == length

        where:
        name     | length
        "Spock"  | 5
        "Kirk"   | 4
        "Scotty" | 6
    }

    void matchHelper(map) {
        assert map['abc'] + map['def'] == 300
    }
}