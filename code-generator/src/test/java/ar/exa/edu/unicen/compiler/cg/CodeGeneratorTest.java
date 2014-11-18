package ar.exa.edu.unicen.compiler.cg;

import static ar.exa.edu.unicen.compiler.lexical.Lexical.GRAMMAR_FILE;
import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.info;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import ar.exa.edu.unicen.compiler.lexical.Lexical;
import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;
import ar.exa.edu.unicen.compiler.syntactic.Syntactic;

public class CodeGeneratorTest {

    private static final String SOURCE_CODE = "/test.prog";

    @Test
    public void testProgramFile() throws Exception {

        final InputStream sourceCode =
                CodeGeneratorTest.class.getResourceAsStream(SOURCE_CODE);
        final InputStream grammarFile =
                Lexical.class.getResourceAsStream(GRAMMAR_FILE);
        final Lexical lexical = new Lexical(grammarFile);
        final Syntactic syntactic = new Syntactic(lexical, sourceCode);
        final CodeGenerator codeGenerator = new CodeGenerator(syntactic);

        final List<Triplet> triplets = syntactic.run();

        final String asmHeader = codeGenerator.loadProgramHeader(triplets);
        System.out.println(asmHeader);

        int count = 0;
        for (final Triplet triplet : triplets) {

            info(count, triplet.getOperator().toString(),
                    triplet.getOperand1(), triplet.getOperand2());

            final String asmBody =
                    codeGenerator.loadProgramBody(count, triplet);
            System.out.println(asmBody);

            count++;
        }

        final String asmFooter =
                codeGenerator.loadProgramFooter(triplets.size());
        System.out.println(asmFooter);
    }

}
