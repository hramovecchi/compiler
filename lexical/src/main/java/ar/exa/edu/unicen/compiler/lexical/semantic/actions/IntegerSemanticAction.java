package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

import java.util.List;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;

/**
 * Verifies integer ranges.
 * 
 * @author pmvillafane
 */
public class IntegerSemanticAction implements SemanticAction {

    private static final int minInteger = -32768;

    private static final int maxInteger = 32767;

    @Override
    public void doAction(final String lexeme, final List<Tuple> tuples,
            final Token token) {

        int value = Integer.valueOf(lexeme);

        if (minInteger < value && value < maxInteger) {
            tuples.add(new Tuple(lexeme, token));
            return;
        }

        final String err =
                String.format("Valor integer %s está fuera de rango."
                        + " Rango válido: -2^15 < |x| < 2^15-1", lexeme);
        throw new SemanticActionException(err);
    }

}
