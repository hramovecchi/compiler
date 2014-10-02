package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;

/**
 * Truncates an identifier if it has size bigger than 12.
 * 
 * @author pmvillafane
 */
public class TruncateIdAction implements SemanticAction {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(TruncateIdAction.class);

    @Override
    public void doAction(final String lexeme, final List<Tuple> tuples,
            final Token token) {

        if (lexeme.length() > 12) {

            final String truncated = lexeme.substring(0, 12);

            LOGGER.warn("Identificador \"{}\" fue truncado a \"{}\"", lexeme,
                    truncated);
            tuples.add(new Tuple(truncated, token));
            return;
        }

        tuples.add(new Tuple(lexeme, token));
    }

}
