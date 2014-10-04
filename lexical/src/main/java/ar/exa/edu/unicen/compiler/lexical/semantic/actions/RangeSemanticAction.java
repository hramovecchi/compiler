package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.debug;
import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.error;

import java.util.List;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;
import ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.Phase;

/**
 * Verifies if the range operator is OK.
 *
 * @author pmvillafane
 */
public class RangeSemanticAction implements SemanticAction {

    /**
     * Checks if the integer is positive.
     *
     * @param number
     *            string representation of the integer to evaluate.
     * @param tuples
     *            list of tuples.
     * @param token
     *            the token being evaluated.
     * @param line
     *            line number.
     */
    private void checkInteger(final String number, final List<Tuple> tuples,
            final Token token, final int line) {

        final String debug = String.format("Entero [%s] detectado", number);
        debug(Phase.LEXICAL, number, token, line, debug);

        int value = Integer.valueOf(number);
        if (value < 0) {
            final String err = String.format(
                    "El límite debe ser entero positivo [%s]", number);
            error(Phase.LEXICAL, number, Token.CONST_INTEGER, line, err);
        }

        // Checks semantically if the integer is OK.
        new IntegerSemanticAction().doAction(number, tuples, Token.CONST_INTEGER, line);
    }

    @Override
    public void doAction(final String lexeme, final List<Tuple> tuples,
            final Token token, final int line) {

        String[] number = lexeme.split("\\..");
        if (number.length >= 1) {
            checkInteger(number[0], tuples, token, line);
        }

        tuples.add(new Tuple("..", token, line));

        if (number.length == 2) {
            checkInteger(number[1], tuples, token, line);
        }
    }

}
