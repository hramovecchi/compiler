package com.aveise.automaton.nodes;

/**
 * Exception used to notify about an error during the instantiation of a node
 * via reflection.
 * 
 * @author pmvillafane
 */
public class DynamicClassLoadingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * 
     * @param clazz
     *            the canonical name of the class to invoke using reflection.
     * @param cause
     *            the cause of the reflection error.
     */
    public DynamicClassLoadingException(final String clazz,
            final Throwable cause) {
        super("Reflection error on class " + clazz, cause);
    }

}
