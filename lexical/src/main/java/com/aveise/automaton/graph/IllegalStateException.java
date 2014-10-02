package com.aveise.automaton.graph;

/**
 * Exception thrown if an illegal state occur when an iteration happens in the
 * automaton.
 * 
 * @author pmvillafane
 */
public class IllegalStateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Object token;

    /**
     * Default constructor.
     * 
     * @param name
     *            name of the node where the illegal state took place.
     * @param token
     *            the cause of the error.
     * @param cause
     *            the cause of the illegal state.
     */
    public IllegalStateException(final String name, final Object token,
            final Throwable cause) {
        super("Illegal state after node " + name, cause);
        this.token = token;
    }

    /**
     * Gets the token invalid token that causes the error.
     * 
     * @return the token.
     */
    public Object getToken() {
        return token;
    }

}
