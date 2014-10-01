package com.aveise.automaton.reader;

/**
 * Models a NodeManager used to give a behavior to the custom nodes to be
 * defined in the automaton.
 * 
 * @author pmvillafane
 */
public abstract class BaseNodeManager {

    private StringBuilder text = new StringBuilder();

    /**
     * Append a new token.
     * 
     * @param token
     *            the token to append.
     */
    public final void append(final Character token) {
        text.append(token);
    }

    /**
     * Empty the tokens list.
     */
    public final void empty() {
        text = new StringBuilder();
    }

    /**
     * Gets the text which is the representation of the tokens list.
     * 
     * @return the text which is the representation of the tokens list.
     */
    public final String getText() {
        return text.toString();
    }

}
