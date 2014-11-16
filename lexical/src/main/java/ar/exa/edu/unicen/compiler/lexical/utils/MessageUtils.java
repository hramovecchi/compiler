package ar.exa.edu.unicen.compiler.lexical.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;

public class MessageUtils {

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
            return fixedLengthString(this.name, -14);
        }

    }

    /**
     * Gets the logger instance according to the phase name (e.g: LEXICAL,
     * SYNTACTIC, SYMBOL_TABLE).
     *
     * @param phase
     *            the phase that is running.
     * @return the corresponding logger instance according to the phase that is
     *         in execution.
     */
    private static final Logger getLogger(final Phase phase) {
        return LoggerFactory.getLogger(phase.name());
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
    private static String fixedLengthString(final String string,
            final int length) {
        return String.format("%1$" + length + "s", string);
    }

    public static void debug(final Phase phase, final String lexeme,
            final Token token, final int line) {
        getLogger(phase).debug("{} - Línea {}: Token {} - {} [{}]",
                phase.getName(),
                fixedLengthString(String.valueOf(line), MAX_LINE_FIXED),
                token.getId(), token.getDescription(), lexeme);
    }

    public static void debug(final Phase phase, final String lexeme,
            final Token token, final int line, final String msg) {
        getLogger(phase).debug("{} - Línea {}: Token {} - {} [{}] - {}",
                phase.getName(),
                fixedLengthString(String.valueOf(line), MAX_LINE_FIXED),
                token.getId(), token.getDescription(), lexeme, msg);
    }

    public static void info(final Phase phase, final String lexeme,
            final Token token, final int line) {
        getLogger(phase).info("{} - Línea {}: Token {} - {} [{}]",
                phase.getName(),
                fixedLengthString(String.valueOf(line), MAX_LINE_FIXED),
                token.getId(), token.getDescription(), lexeme);
    }

    public static void info(final Phase phase, final String lexeme,
            final Token token, final String type, final int line) {
        getLogger(phase).info("{} - Línea {}: Token {} - {} [{}] | Tipo {}",
                phase.getName(),
                fixedLengthString(String.valueOf(line), MAX_LINE_FIXED),
                token.getId(), token.getDescription(), lexeme, type);
    }

    public static void info(final Phase phase, final int line, final String msg) {
        getLogger(phase).info("{} - Línea {}: {}", phase.getName(),
                fixedLengthString(String.valueOf(line), MAX_LINE_FIXED), msg);
    }

    public static void info(final Phase phase, final String lexeme,
            final Token token, final int line, final String msg) {
        getLogger(phase).info("{} - Línea {}: Token {} - {} [{}] - {}",
                phase.getName(),
                fixedLengthString(String.valueOf(line), MAX_LINE_FIXED),
                token.getId(), token.getDescription(), lexeme, msg);
    }

    public static void warn(final Phase phase, final String lexeme,
            final Token token, final int line) {
        getLogger(phase).warn("{} - Línea {}: Token {} - {} [{}]",
                phase.getName(),
                fixedLengthString(String.valueOf(line), MAX_LINE_FIXED),
                token.getId(), token.getDescription(), lexeme);
    }

    public static void warn(final Phase phase, final String lexeme,
            final Token token, final int line, final String msg) {
        getLogger(phase).warn("{} - Línea {}: Token {} - {} [{}] - {}",
                phase.getName(),
                fixedLengthString(String.valueOf(line), MAX_LINE_FIXED),
                token.getId(), token.getDescription(), lexeme, msg);
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

    public static void errorWithoutException(final Phase phase, final int line,
            final String err) {
        getLogger(phase).error("{} - Línea {}: {}", phase.getName(),
                fixedLengthString(String.valueOf(line), MAX_LINE_FIXED), err);
    }

}
