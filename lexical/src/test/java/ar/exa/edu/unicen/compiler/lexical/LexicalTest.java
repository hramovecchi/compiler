package ar.exa.edu.unicen.compiler.lexical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;

/**
 * Unit test for Lexical analyzer.
 */
public class LexicalTest {

    private static final String PROGRAM = "/program.test";

    @Test
    public void testProgramFile() throws Exception {

        final InputStream sourceCode =
                LexicalTest.class.getResourceAsStream(PROGRAM);
        final InputStream grammarFile =
                LexicalTest.class.getResourceAsStream(Lexical.GRAMMAR_FILE);
        final Lexical lexical = new Lexical(grammarFile);
        final List<Tuple> tuples = lexical.run(sourceCode);

        assertNotNull(tuples);
        assertEquals(12, tuples.size());

        Tuple tuple = tuples.get(0);
        assertEquals(Token.INTEGER, tuple.getToken());
        assertEquals("entero", tuple.getLexeme());

        tuple = tuples.get(1);
        assertEquals(Token.ID, tuple.getToken());
        assertEquals("variable_muy", tuple.getLexeme());

        tuple = tuples.get(2);
        assertEquals(Token.ASSIGN, tuple.getToken());
        assertEquals(":=", tuple.getLexeme());

        tuple = tuples.get(3);
        assertEquals(Token.CONST_INTEGER, tuple.getToken());
        assertEquals("20", tuple.getLexeme());

        tuple = tuples.get(4);
        assertEquals(Token.IF, tuple.getToken());
        assertEquals("si", tuple.getLexeme());

        tuple = tuples.get(5);
        assertEquals(Token.ID, tuple.getToken());
        assertEquals("variable_muy", tuple.getLexeme());

        tuple = tuples.get(6);
        assertEquals(Token.LT, tuple.getToken());
        assertEquals("<", tuple.getLexeme());

        tuple = tuples.get(7);
        assertEquals(Token.CONST_INTEGER, tuple.getToken());
        assertEquals("33", tuple.getLexeme());

        tuple = tuples.get(8);
        assertEquals(Token.THEN, tuple.getToken());
        assertEquals("entonces", tuple.getLexeme());

        tuple = tuples.get(9);
        assertEquals(Token.PRINT, tuple.getToken());
        assertEquals("imprimir", tuple.getLexeme());

        tuple = tuples.get(10);
        assertEquals(Token.CONST_DOUBLE, tuple.getToken());
        assertEquals("123.4", tuple.getLexeme());

        tuple = tuples.get(11);
        assertEquals(Token.STRING, tuple.getToken());
        assertEquals("'acá va un + string    que continúa acá'", tuple.getLexeme());
    }

}
