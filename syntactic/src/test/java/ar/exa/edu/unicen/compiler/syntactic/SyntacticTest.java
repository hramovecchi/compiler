package ar.exa.edu.unicen.compiler.syntactic;

import static ar.exa.edu.unicen.compiler.lexical.Lexical.GRAMMAR_FILE;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.exa.edu.unicen.compiler.lexical.Lexical;
import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

/**
 * Unit test for Syntax analyzer.
 */
public class SyntacticTest {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SyntacticTest.class);

    private static final String SOURCE_CODE = "/test.prog";

    private void printTriplet(final int count, final String operator,
            final Object operand1, final Object operand2) {

        if (operand1 instanceof Integer
                && (operand2 instanceof String || operand2 == null)) {
            LOGGER.debug("{}. ({}, [{}], {})", count, operator, operand1,
                    operand2);
        } else if (operand1 instanceof String && operand2 instanceof Integer) {
            LOGGER.debug("{}. ({}, {}, [{}])", count, operator, operand1,
                    operand2);
        } else if (operand1 instanceof Integer && operand2 instanceof Integer) {
            LOGGER.debug("{}. ({}, [{}], [{}])", count, operator, operand1,
                    operand2);
        } else {
            LOGGER.debug("{}. ({}, {}, {})", count, operator, operand1,
                    operand2);
        }
    }

    @Test
    public void testProgramFile() throws IOException {

        final InputStream sourceCode =
                SyntacticTest.class.getResourceAsStream(SOURCE_CODE);
        final InputStream grammarFile =
                Lexical.class.getResourceAsStream(GRAMMAR_FILE);
        final Lexical lexical = new Lexical(grammarFile);
        final Syntactic syntactic = new Syntactic(lexical, sourceCode);

        int count = 0;
        final List<Triplet> triples = syntactic.run();
        for (final Triplet triplet : triples) {
            this.printTriplet(count++, triplet.getOperator().toString(),
                    triplet.getOperand1(), triplet.getOperand2());
        }
    }

}
