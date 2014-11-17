package ar.exa.edu.unicen.compiler.cg;

import static ar.exa.edu.unicen.compiler.lexical.Lexical.GRAMMAR_FILE;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import ar.exa.edu.unicen.compiler.lexical.Lexical;
import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;
import ar.exa.edu.unicen.compiler.syntactic.Syntactic;
import ar.exa.edu.unicen.compiler.syntactic.SyntacticTest;

public class CodeGeneratorTest {

    private static final String SOURCE_CODE = "/test.prog";

    @Test
    public void testProgramFile() throws Exception {

        final InputStream sourceCode =
                SyntacticTest.class.getResourceAsStream(SOURCE_CODE);
        final InputStream grammarFile =
                Lexical.class.getResourceAsStream(GRAMMAR_FILE);
        final Lexical lexical = new Lexical(grammarFile);
        final Syntactic syntactic = new Syntactic(lexical, sourceCode);
        final List<Triplet> triples = syntactic.run();
        final CodeGenerator codeGenerator = new CodeGenerator();

        final String asm = codeGenerator.getAssemblerCode(triples);
        System.out.println(asm);
    }

}
