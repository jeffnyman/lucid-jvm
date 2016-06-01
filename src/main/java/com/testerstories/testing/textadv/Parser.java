package com.testerstories.testing.textadv;

import java.util.StringTokenizer;

public class Parser {
    private String directObject = null;
    private String indirectObject = null;

    /**
     * Parses a player command.
     *
     * @param input holds the action to parse
     * @return a Command object that holds the action's parts of speech
     */
    public Command parse(String input) {
        // Stop parsing if null input.
        if (input == null)
            return new Command("No command to parse.");

        // Remove leading and trailing whitespace.
        input = input.trim();

        String action = input;

        // Remove all trailing punctuation.
        while (action.length() > 0 &&
                !Character.isLetter(action.charAt(action.length() - 1))) {
            action = action.substring(0, action.length() -1);
        }

        // Stop parsing if input ends up empty.
        if (action.equals(""))
            return new Command("You entered an empty command.");

        String directive = parseDirective(action);

        if (directive != null) {
            action = stripFirstWord(action);
        }

        String verb = parseVerb(action);

        if (verb != null) {
            action = stripFirstWord(action);
        }

        String preposition = parsePreposition(action);

        if (preposition != null) {
            if (!action.isEmpty()) {
                directObject = stringBeforeWord(action, preposition);
                indirectObject = stringAfterWord(action, preposition);

                directObject = parseMultiObjectPhrase(directObject);
                indirectObject = parseMultiObjectPhrase(indirectObject);
            }
        } else {
            String article = parseArticle(action);

            if (article != null) {
                action = stringAfterWord(action, article);
            }

            if (!action.isEmpty()) {
                directObject = action;
            }
        }

        return new Command(directive, verb, directObject, preposition, indirectObject, "Success");
    }

    /**
     * Return the directive qualifier of a given command. This could refer
     * to an NPC actor or an object. Currently this must be a single word.
     *
     * @param input the command string to parse
     * @return the directive of the command
     */
    private String parseDirective(String input) {
        StringTokenizer st = new StringTokenizer(input);
        String directive = st.nextToken();

        // If there is no comma, there is no directive.
        if (directive.charAt(directive.length() - 1) != ',')
            return null;

        // Strip the ending comma.
        directive = directive.substring(0, directive.length() - 1);

        return directive;
    }

    /**
     * Return the verb portion of a given command. This must be the
     * first word in the command, unless there is a directive. In
     * that case, the verb must be the word that immediately follows
     * the directive.
     *
     * @param input the command string to parse
     * @return the verb of the command
     */
    private String parseVerb(String input) {
        StringTokenizer st = new StringTokenizer(input);
        return st.nextToken();
    }

    /**
     * Returns the article in a string.
     *
     * @param input the command string to parse
     * @return the article portion of the command
     */
    private String parseArticle(String input) {
        StringTokenizer st = new StringTokenizer(input);
        String article = null;
        String articles = ":a:an:the:";

        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            String articleWord = ":" + word + ":";

            if (articles.contains(articleWord)) {
                article = word;
            }
        }

        return article;
    }

    /**
     * Returns the preposition in a string.
     *
     * @param input the command string to parse
     * @return the preposition portion of the command
     */
    private String parsePreposition(String input) {
        StringTokenizer st = new StringTokenizer(input);
        String preposition = null;
        String prepositions = ":in:on:to:under:inside:outside:into:" +
                "with:before:after:behind:beneath:through:onto:alongside:around:" +
                "within:beyond:but:except:";

        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            String prepWord = ":" + word + ":";

            if (prepositions.contains(prepWord)) {
                preposition = word;
            }
        }

        return preposition;
    }

    /**
     * Parse a phrase that has multiple objects.
     *
     * @param input the command string to parse
     * @return the remaining portion of a particular phrase
     */
    private String parseMultiObjectPhrase(String input) {
        String remaining = input;
        StringTokenizer st = new StringTokenizer(remaining);

        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            String article = parseArticle(remaining);

            if (article != null)
                remaining = stringAfterWord(remaining, article);

            remaining.trim();
        }

        return remaining;
    }

    /**
     * Strip the first word from a string and return the remainder of that
     * string.
     *
     * @param input the command string to parse
     * @return the string with the first word removed
     */
    public String stripFirstWord(String input) {
        StringTokenizer st = new StringTokenizer(input);
        String firstWord = st.nextToken();
        int index = input.indexOf(firstWord) + firstWord.length();
        return input.substring(index).trim();
    }

    /**
     * For a given string and a given word, return the part of the string
     * that comes before the word.
     *
     * @param input the command string to parse
     * @param word the word to look for in the string
     * @return the portion of the string before the word
     */
    private String stringBeforeWord(String input, String word) {
        StringTokenizer st = new StringTokenizer(input);
        boolean pastWord = false;
        String firstPart = "";

        while (st.hasMoreTokens()) {
            String currentWord = st.nextToken();

            if (word.equals(currentWord))
                pastWord = true;

            if (!pastWord)
                firstPart += currentWord + " ";
        }

        return firstPart.trim();
    }

    /**
     * For a given string and a given word, return the part of the string
     * that comes after the word.
     *
     * @param input the command string to parse
     * @param word the word to look for in the string
     * @return the portion of the string after the word
     */
    private String stringAfterWord(String input, String word) {
        StringTokenizer st = new StringTokenizer(input);
        boolean pastWord = false;
        String lastPart = "";

        while (st.hasMoreTokens()) {
            String currentWord = st.nextToken();

            if (pastWord)
                lastPart += currentWord + " ";

            if (word.equals(currentWord))
                pastWord = true;
        }

        return lastPart.trim();
    }

    public static void main(String args[]) {
        String action = "cut tree with chainsaw";
        Parser parser = new Parser();
        Command command = parser.parse(action);
        System.out.println("Parse Command: " + action);
        System.out.println("Verb: " + command.getVerb());
        System.out.println("Direct Object: " + command.getDirectObject());
        System.out.println("Indirect Object: " + command.getIndirectObject());
    }
}
