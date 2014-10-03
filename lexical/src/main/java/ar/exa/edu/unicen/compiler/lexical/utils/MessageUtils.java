package ar.exa.edu.unicen.compiler.lexical.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;

public class MessageUtils {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MessageUtils.class);

    private static final int MAX_LINE_FIXED = 2;

    public enum Phase {
        LEXICAL("Léxico"),
        SYNTACTIC("Sintáctico"),
        SYMBOL_TABLE("Tabla Símbolos");

        private final String name;

        private Phase(final String name) {
            this.name = name;
        }

        public String getName() {
            return fixedLengthString(name, -14);
        }

    }

    /**
     * Fixes the length of a {@link String}.
     * 
     * @param string
     *            the string to be fixed.
     * @param length
     *            the number of characters to fix.
     * @return the fixed string.
     */
    private static String fixedLengthString(String string, int length) {
        return String.format("%1$" + length + "s", string);
    }

    public static void debug(final Phase phase, final String lexeme,
            final Token token, final int line) {
        LOGGER.debug("{} - Línea {}: Token {} - {} [{}]", phase.getName(),
                fixedLengthString(String.valueOf(line), MAX_LINE_FIXED), token
                        .getId(), token.getDescription(), lexeme);
    }

    public static void debug(final Phase phase, final String lexeme,
            final Token token, final int line, final String msg) {
        LOGGER.debug("{} - Línea {}: Token {} - {} [{}] - {}", phase.getName(),
                fixedLengthString(String.valueOf(line), MAX_LINE_FIXED), token
                        .getId(), token.getDescription(), lexeme, msg);
    }

    public static void info(final Phase phase, final String lexeme,
            final Token token, final int line) {
        LOGGER.info("{} - Línea {}: Token {} - {} [{}]", phase.getName(),
                fixedLengthString(String.valueOf(line), MAX_LINE_FIXED), token
                        .getId(), token.getDescription(), lexeme);
    }

    public static void info(final Phase phase, final String lexeme,
            final Token token, final int line, final String msg) {
        LOGGER.info("{} - Línea {}: Token {} - {} [{}] - {}", phase.getName(),
                fixedLengthString(String.valueOf(line), MAX_LINE_FIXED), token
                        .getId(), token.getDescription(), lexeme, msg);
    }

    public static void warn(final Phase phase, final String lexeme,
            final Token token, final int line) {
        LOGGER.warn("{} - Línea {}: Token {} - {} [{}]", phase.getName(),
                fixedLengthString(String.valueOf(line), MAX_LINE_FIXED), token
                        .getId(), token.getDescription(), lexeme);
    }

    public static void warn(final Phase phase, final String lexeme,
            final Token token, final int line, final String msg) {
        LOGGER.warn("{} - Línea {}: Token {} - {} [{}] - {}", phase.getName(),
                fixedLengthString(String.valueOf(line), MAX_LINE_FIXED), token
                        .getId(), token.getDescription(), lexeme, msg);
    }

    public static void error(final Phase phase, final String lexeme,
            final Token token, final int line, final String err) {
        throw new CompilerException(phase, lexeme, token, fixedLengthString(
                String.valueOf(line), MAX_LINE_FIXED), err);
    }

    public static void error(final Phase phase, final int line, final String err) {
        throw new CompilerException(phase, fixedLengthString(String
                .valueOf(line), MAX_LINE_FIXED), err);
    }

}
