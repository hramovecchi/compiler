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
            int value = Integer.parseInt(number[0]);
            if (value < 0) {
                final String err =
                        String.format("Rangos no puede ser menor a 0. "
                                + "Valor: %d < 0", value);
                throw new SemanticActionException(err);
            }

            tuples.add(new Tuple(number[0], Token.CONST_INTEGER));
        }

        tuples.add(new Tuple("..", token));
    }

}
