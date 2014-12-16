package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.error;

import java.util.List;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;
import ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.Phase;
import ar.exa.edu.unicen.compiler.lexical.utils.SymbolTable;

/**
 * Verifies double ranges.
 *
 * @author pmvillafane
 */
public class DoubleSemanticAction implements SemanticAction {

    private static final double minDouble =
            stringToDouble("2.2250738585072014b-308");

    private static final double maxDouble =
            stringToDouble("1.7976931348623157b308");

    private final SymbolTable symbolTable = SymbolTable.getInstance();

    /**
     * Transforms a string to double,
     *
     * @param value
     *            string representation of a double number.
     * @return the double number.
     */
    public static double stringToDouble(final String value) {

        final String[] pair = value.split("[bB]");
        double result;
        if (pair.length == 1) {
            result = Double.parseDouble(pair[0]);
        } else {
            result =
                    Double.parseDouble(pair[0])
                    * Math.pow(10D, Double.parseDouble(pair[1]));
        }

        return result;
    }

    @Override
    public void doAction(final String lexeme, final List<Tuple> tuples,
            final Token token, final int line) {

        double value = stringToDouble(lexeme);

        // Applying modulus |value|.
        if (value < 0) {
            value *= -1;
        }

        if (minDouble < value && value < maxDouble) {
            tuples.add(new Tuple(lexeme, token, line));
            this.symbolTable.add(lexeme, token, line);
            return;
        }

        final String err =
                "Rango inválido. Rango debe ser 2.2250738585072014b-308 < "
                        + "|x| < 1.7976931348623157b308";
        error(Phase.LEXICAL, lexeme, token, line, err);
    }

}
