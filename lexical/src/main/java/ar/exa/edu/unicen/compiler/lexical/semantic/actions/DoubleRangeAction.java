package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Verifies the double ranges.
 * 
 * @author pmvillafane
 */
public class DoubleRangeAction implements SemanticAction {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(DoubleRangeAction.class);

    private static final double minDouble =
            stringToDouble("2.2250738585072014b-308");

    private static final double maxDouble =
            stringToDouble("1.7976931348623157b308");

    /**
     * Transforms a string to double,
     * 
     * @param value
     *            string representation of a double number.
     * @return the double number.
     */
    private static double stringToDouble(final String value) {

        String[] pair = value.split("[bB]");
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
    public String doAction(final String lexeme) {

        double value = stringToDouble(lexeme);

        // Applying modulus |value|.
        if (value < 0) {
            value *= -1;
        }

        if (minDouble < value && value < maxDouble) {
            return lexeme;
        }

        LOGGER.error("Valor doble {} está fuera de rango. Rango válido: "
                + "2.2250738585072014b-308 < |x| < 1.7976931348623157b308",
                lexeme);

        throw new SemanticActionException();
    }

}
