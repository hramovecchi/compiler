package ar.exa.edu.unicen.compiler.lexical;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.junit.Test;

import ar.exa.edu.unicen.compiler.lexical.antlr.sources.GrammarLexer;
import ar.exa.edu.unicen.compiler.lexical.antlr.sources.GrammarParser;

/**
 * Unit test for simple App.
 */

public class AppTest {

    @Test
    public void testGrammar() throws Exception {

        GrammarLexer l =
                new GrammarLexer(new ANTLRInputStream(getClass()
                        .getResourceAsStream("/example.grammar")));

        GrammarParser parser = new GrammarParser(new CommonTokenStream(l));

        parser.addErrorListener(new BaseErrorListener() {

            @Override
            public void syntaxError(Recognizer<?, ?> recognizer,
                    Object offendingSymbol, int line, int charPositionInLine,
                    String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line "
                        + line + " due to " + msg, e);
            }
        });

        parser.evaluate();
    }
}
