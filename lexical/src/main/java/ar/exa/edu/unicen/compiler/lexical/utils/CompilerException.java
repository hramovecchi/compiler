package ar.exa.edu.unicen.compiler.lexical.utils;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.Phase;

/**
 * Default exception to report semantic errors.
 * 
 * @author pmvillafane
 */
public class CompilerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a {@link RuntimeException} using token information.
     * 
     * @param phase
     *            compilation phase.
     * @param lexeme
     *            the lexeme.
     * @param token
     *            associated token.
     * @param line
     *            line number.
     * @param err
     *            the error message.
     */
    public CompilerException(final Phase phase, final String lexeme,
            final Token token, final String line, final String err) {
        super(String.format("%s - Línea %s: Token %d - %s [%s] - %s", phase
                .getName(), line, token.getId(), token.getDescription(),
                lexeme, err));
    }

    // /**
    // * Constructs a {@link RuntimeException} without use token information.
    // *
    // * @param phase
    // * compilation phase.
    // * @param lexeme
    // * the lexeme.
    // * @param line
    // * line number.
    // * @param err
    // * the error message.
    // */
    // public CompilerException(final Phase phase, final String lexeme,
    // final String line, final String err) {
    // super(String.format("%s - Línea %s: Char %s - %s", phase.getName(),
    // line, lexeme, err));
    // }

    /**
     * Constructs a {@link RuntimeException} without use token information.
     * 
     * @param phase
     *            compilation phase.
     * @param line
     *            line number.
     * @param err
     *            the error message.
     */
    public CompilerException(final Phase phase, final String line,
            final String err) {
        super(String.format("%s - Línea %s: %s", phase.getName(), line, err));
    }

}
