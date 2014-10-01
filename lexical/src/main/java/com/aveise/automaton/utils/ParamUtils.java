package com.aveise.automaton.utils;

/**
 * Utilities useful for parameters verification.
 * 
 * @author pmvillafane
 */
public class ParamUtils {

    /**
     * Checks if the given string is not blank. If it is <b>null</b>, empty or
     * whitespace only throws {@link RuntimeException} notifying about the
     * error.
     * 
     * @param evaluate
     *            the string to evaluate.
     * @param message
     *            the message to print in case of error.
     */
    public static void hasLength(final String evaluate, final String message) {
        if (evaluate == null || "".equals(evaluate.trim())) {
            throw new RuntimeException(message);
        }
    }

    /**
     * Checks if the given object is not <b>null</b>. If it is <b>null</b>
     * throws {@link RuntimeException} notifying about the error.
     * 
     * @param evaluate
     *            the object to evaluate.
     * @param message
     *            the message to print in case of error.
     */
    public static void notNull(final Object evaluate, final String message) {
        if (evaluate == null) {
            throw new RuntimeException(message);
        }
    }

}
