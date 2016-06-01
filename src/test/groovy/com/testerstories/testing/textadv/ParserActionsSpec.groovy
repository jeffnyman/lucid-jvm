package com.testerstories.testing.textadv

import spock.lang.Specification

class ParserActionsSpec extends Specification {
    Parser parser
    Command command

    def setup() {
        parser = new Parser()
    }

    def "valid commands are processed by the parser"() {
        when: "commands are checked"
        command = parser.parse(action)

        then: "sentence structure is successfully broken out"
        command.getVerb() == verb
        command.getDirectObject() == directObject
        command.getIndirectObject() == indirectObject

        where: "sample commands are"
        action                 || verb   | directObject | indirectObject
        "wait"                 || "wait" | null         | null
        "take sword"           || "take" | "sword"      | null
        "dig hole with shovel" || "dig"  | "hole"       | "shovel"
    }

    def cleanup() {
        parser = null
    }
}