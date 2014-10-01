package com.aveise.automaton.reader;

/**
 * Exception in charge alert about an invalid grammar file format.
 * 
 * @author pmvillafane
 */
public class InvalidFileFormatException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * 
     * @param cause
     *            the cause of the invalid format.
     */
    public InvalidFileFormatException(final Throwable cause) {
        super("The grammar file has an invalid format", cause);
    }

}
