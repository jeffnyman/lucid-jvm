package com.testerstories.testing.textadv;

public class Command {
    private String message;
    private String directive;
    private String verb;
    private String directObject;
    private String preposition;
    private String indirectObject;

    public Command(String message) {
        this.message = message;
    }

    public Command(String directive, String verb, String directObject, String preposition, String indirectObject, String message) {
        this.directive = directive;
        this.verb = verb;
        this.directObject = directObject;
        this.preposition = preposition;
        this.indirectObject = indirectObject;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getDirective() {
        return directive;
    }

    public String getVerb() {
        return verb;
    }

    public String getDirectObject() {
        return directObject;
    }

    public String getPreposition() {
        return preposition;
    }

    public String getIndirectObject() {
        return indirectObject;
    }

    public boolean hasDirectObject() {
        return (directObject != null);
    }

    public boolean hasIndirectObject() {
        return (indirectObject != null);
    }

    public boolean hasDirective() {
        return (directive != null);
    }
}
