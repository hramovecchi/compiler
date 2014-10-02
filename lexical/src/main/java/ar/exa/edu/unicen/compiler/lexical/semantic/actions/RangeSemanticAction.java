package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

import java.util.List;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;

/**
 * Verifies if the range operator is OK.
 *
 * @author pmvillafane
 */
public class RangeSemanticAction implements SemanticAction {

    @Override
    public void doAction(final String lexeme, final List<Tuple> tuples,
            final Token token) {

        String[] number = lexeme.split("\\..");
        if (number.length == 1) {
            tuples.add(new Tuple(number[0], Token.CONST_INTEGER));
        }
        tuples.add(new Tuple("..", token));
    }

}
