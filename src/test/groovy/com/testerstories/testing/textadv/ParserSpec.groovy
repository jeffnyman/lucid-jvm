package com.testerstories.testing.textadv

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Narrative("""
The parser is the core of the text adventure game engine
and must be able to process a wide variety of commands.
""")
@Title("Text Adventure Parser")
@Subject(Parser)
class ParserSpec extends Specification {
    Parser parser
    Command command

    def setupSpec() {
        println "Runs once for the whole suite."
    }

    def setup() {
        parser = new Parser()
    }

    def "parser indicates if no command was received"() {
        when: "no command is received"
        command = parser.parse(null)

        then: "parser indicates no command was received"
        assert command.getMessage() == "No command to parse."
    }

    def "parser does not parse a blank command"() {
        when: "a blank command is received"
        command = parser.parse("")

        then: "parser indicates an empty command was received"
        assert command.getMessage() == "You entered an empty command."

        and: "parser command has no structure"
        assert command.getVerb() == null
        assert command.getDirectObject() == null
    }

    def "parser indicates empty command are punctuation is stripped"() {
        when: "a blank command is received"
        command = parser.parse(".")

        then: "parser indicates an empty command was received"
        assert command.getMessage() == "You entered an empty command."

        and: "parser command has no structure"
        assert command.getVerb() == null
        assert command.getDirectObject() == null
    }

    def "intransitive verbs"() {
        when: "a single word command is entered"
        command = parser.parse("wait")

        then: "parser successfully uses the command as a verb"
        assert command.getVerb() == "wait"
        assert command.getMessage() == "Success"

        and: "parser recognizes no other word phrases"
        assert command.hasDirectObject() == false
        assert command.hasIndirectObject() == false
        assert command.getDirectObject() == null
        assert command.getIndirectObject() == null
    }

    def "transitive verbs"() {
        when: "a two word command is entered"
        command = parser.parse("take sword")

        then: "parser successfully uses the command as a verb"
        assert command.getVerb() == "take"

        and: "parser successfully uses the direct object"
        with(command) {
            getDirectObject() == "sword"
            hasDirectObject() == true
            getMessage() == "Success"
        }

        and: "parser recognizes the command is not directive"
        assert command.hasDirective() == false
    }

    def "transitive verbs with article"() {
        when: "a two word command is entered with an article"
        command = parser.parse("take the sword")

        then: "parser processes the command as a two word command"
        command.with {
            getVerb() == "take"
            getDirectObject() == "sword"
            hasDirectObject() == true
            getMessage() == "Success"
        }
    }

    def "transitive verbs with indirect objects"() {
        when: "a command using an indirect object"
        command = parser.parse("dig hole with shovel")

        then: "parser processes the command as a three word command"
        command.with {
            hasDirectObject() == true
            hasIndirectObject() == true

            getVerb() == "dig"
            getDirectObject() == "hole"
            getPreposition() == "with"
            getIndirectObject() == "shovel"

            getMessage() == "Success"
        }
    }

    def "transitive verbs with indirect objects and articles"() {
        when: "a command using an indirect object with articles"
        command = parser.parse("dig a hole with the shovel")

        then: "parser processes the command as a three word command"
        command.with {
            hasDirectObject() == true
            hasIndirectObject() == true

            getVerb() == "dig"
            getDirectObject() == "hole"
            getPreposition() == "with"
            getIndirectObject() == "shovel"

            getMessage() == "Success"
        }
    }

    def "transitive verbs with an adjective"() {
        when: "a command using a direct object with an adjective"
        command = parser.parse("take the golden sword")

        then: "parser processes the command uses the adjective as the direct object"
        assert command.getVerb() == "take"
        assert command.getDirectObject() == "golden sword"
        assert command.getMessage() == "Success"
    }

    def "transitive verbs with an article in the direct object"() {
        when: "a command using a direct object with an adjective"
        command = parser.parse("take the golden sword of the ancients")

        then: "parser uses the entire phrase as the direct object"
        assert command.getVerb() == "take"
        assert command.getDirectObject() == "golden sword of the ancients"
        assert command.getMessage() == "Success"
    }

    def "actions that are directed to another character"() {
        when: "a command with a directive"
        command = parser.parse("floyd, wait")

        then: "the command is parsed as a regular command"
        assert command.getVerb() == "wait"

        and: "the directive is stored"
        assert command.hasDirective() == true
        assert command.getDirective() == "floyd"

        when: "a command with a direcetive"
        command = parser.parse("floyd, take the golden sword")

        then: "the command is parsed as a regular command"
        assert command.getDirective() == "floyd"
        assert command.getVerb() == "take"
        assert command.getDirectObject() == "golden sword"
    }

    def "punctuation is stripped from valid commands"() {
        when: "command is entered with punctuation"
        command = parser.parse("inventory.!?");

        then: "the command is stripped of punctuation and used normally"
        assert command.getVerb() == "inventory"
    }

    def cleanup() {
        parser = null
    }

    def cleanupSpec() {
        println "Runs once for the whole suite."
    }
}