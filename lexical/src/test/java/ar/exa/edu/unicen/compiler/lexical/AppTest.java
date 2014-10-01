package ar.exa.edu.unicen.compiler.lexical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import ar.exa.edu.unicen.compiler.lexical.analyzer.LexicalAnalyzer;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;

import com.aveise.automaton.graph.Node;
import com.aveise.automaton.reader.AutomatonFileReader;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private static final String PROGRAM = "/program.test";

    @Test
    public void testProgramFile() throws Exception {

        final InputStream sourceCode =
                AppTest.class.getResourceAsStream(PROGRAM);
        final InputStream grammarFile =
                AppTest.class.getResourceAsStream(App.GRAMMAR_FILE);
        final LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
        final AutomatonFileReader automatonFileReader =
                new AutomatonFileReader(grammarFile, lexicalAnalyzer);
        final BufferedReader reader =
                new BufferedReader(new InputStreamReader(sourceCode));

        try {
            int r;
            Node<Character> node = automatonFileReader.initialNode();
            while ((r = reader.read()) != -1) {
                node = node.next((char) r);
            }
        } finally {
            reader.close();
        }

        assertNotNull(lexicalAnalyzer.getTuples());
        assertEquals(12, lexicalAnalyzer.getTuples().size());

        Tuple tuple = lexicalAnalyzer.getTuples().get(0);
        assertEquals(Token.INTEGER, tuple.getToken());
        assertEquals("entero", tuple.getLexeme());

        tuple = lexicalAnalyzer.getTuples().get(1);
        assertEquals(Token.ID, tuple.getToken());
        assertEquals("variable_muy", tuple.getLexeme());

        tuple = lexicalAnalyzer.getTuples().get(2);
        assertEquals(Token.ASSIGN, tuple.getToken());
        assertEquals(":=", tuple.getLexeme());

        tuple = lexicalAnalyzer.getTuples().get(3);
        assertEquals(Token.CONST_INTEGER, tuple.getToken());
        assertEquals("20", tuple.getLexeme());

        tuple = lexicalAnalyzer.getTuples().get(4);
        assertEquals(Token.IF, tuple.getToken());
        assertEquals("si", tuple.getLexeme());

        tuple = lexicalAnalyzer.getTuples().get(5);
        assertEquals(Token.ID, tuple.getToken());
        assertEquals("variable_muy", tuple.getLexeme());

        tuple = lexicalAnalyzer.getTuples().get(6);
        assertEquals(Token.LT, tuple.getToken());
        assertEquals("<", tuple.getLexeme());

        tuple = lexicalAnalyzer.getTuples().get(7);
        assertEquals(Token.CONST_INTEGER, tuple.getToken());
        assertEquals("33", tuple.getLexeme());

        tuple = lexicalAnalyzer.getTuples().get(8);
        assertEquals(Token.THEN, tuple.getToken());
        assertEquals("entonces", tuple.getLexeme());

        tuple = lexicalAnalyzer.getTuples().get(9);
        assertEquals(Token.PRINT, tuple.getToken());
        assertEquals("imprimir", tuple.getLexeme());

        tuple = lexicalAnalyzer.getTuples().get(10);
        assertEquals(Token.CONST_DOUBLE, tuple.getToken());
        assertEquals("123.4", tuple.getLexeme());

        tuple = lexicalAnalyzer.getTuples().get(11);
        assertEquals(Token.STRING, tuple.getToken());
        assertEquals("'acá va un + string    que continúa acá'", tuple.getLexeme());
    }

}
