package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Truncates an identifier if it has size bigger than 12.
 * 
 * @author pmvillafane
 */
public class TruncateIdAction implements SemanticAction {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(TruncateIdAction.class);

    @Override
    public String doAction(final String lexeme) {

        if (lexeme.length() > 12) {

            final String truncated = lexeme.substring(0, 12);

            LOGGER.warn("Identificador \"{}\" fue truncado a \"{}\"", lexeme,
                    truncated);

            return truncated;
        }

        return lexeme;
    }

}
