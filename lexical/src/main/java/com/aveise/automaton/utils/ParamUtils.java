package com.aveise.automaton.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Utilities useful for parameters verification.
 * 
 * @author pmvillafane
 */
public class ParamUtils {

    private static final String REGEX_TO_SPLIT_EQUAL =
            "[=]+(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

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

    /**
     * Builds a {@link Map} with the program arguments. For instance, if the
     * user define the <code>--arg1=value1 --arg2=value2</code> the {@link Map}
     * will be created in the following way:<br/>
     * <code>map.put("arg1", "value1");</code><br/>
     * <code>map.put("arg2", "value2");</code><br/>
     * 
     * @param args
     *            list of program arguments.
     * @return a {@link Map} containing the program arguments.
     */
    public static Map<String, String> buildProgramArguments(final String[] args) {
        final Map<String, String> programArguments =
                new HashMap<String, String>();

        for (String arg : args) {
            // All the program arguments must start with --
            if (arg.startsWith("--")) {
                final String[] keyValue = arg.split(REGEX_TO_SPLIT_EQUAL);
                programArguments.put(keyValue[0].substring(2), keyValue[1]
                        .replaceAll("^\"|\"$", ""));
            }
        }

        return programArguments;
    }

}
