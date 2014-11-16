package ar.exa.edu.unicen.compiler.lexical.utils;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;

public class Element {

    private final Token token;

    private String type;

    public Element(final Token token) {
        this.token = token;
    }

    public Token getToken() {
        return this.token;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

}
