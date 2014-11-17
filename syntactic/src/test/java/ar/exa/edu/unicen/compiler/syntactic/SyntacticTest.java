package ar.exa.edu.unicen.compiler.syntactic;

import static ar.exa.edu.unicen.compiler.lexical.Lexical.GRAMMAR_FILE;
import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.info;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import ar.exa.edu.unicen.compiler.lexical.Lexical;
import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

/**
 * Unit test for Syntax analyzer.
 */
public class SyntacticTest {

    private static final String SOURCE_CODE = "/test.prog";

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
            info(count++, triplet.getOperator().toString(), triplet
                    .getOperand1(), triplet.getOperand2());
        }
    }

}
