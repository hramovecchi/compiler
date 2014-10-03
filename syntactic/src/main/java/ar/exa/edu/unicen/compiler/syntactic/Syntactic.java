package ar.exa.edu.unicen.compiler.syntactic;

import static ar.exa.edu.unicen.compiler.lexical.Lexical.GRAMMAR_FILE;
import static com.aveise.automaton.utils.ParamUtils.buildProgramArguments;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.exa.edu.unicen.compiler.lexical.Lexical;
import ar.exa.edu.unicen.compiler.syntactic.parser.yacc.Parser;

public class Syntactic {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(Syntactic.class);

    private static final String SOURCE_CODE = "sourceCode";

    private final Parser parser;

    public Syntactic(final Lexical lexical, final InputStream sourceCode)
            throws IOException {
        parser = new Parser(lexical, sourceCode);
    }

    public void run() {
        parser.run();
    }

    public static void main(String[] args) throws IOException {
        final Map<String, String> programArgs = buildProgramArguments(args);
        final InputStream sourceCode =
                new FileInputStream(programArgs.get(SOURCE_CODE));
        final InputStream grammarFile =
                Lexical.class.getResourceAsStream(GRAMMAR_FILE);
        final Lexical lexical = new Lexical(grammarFile);
        final Syntactic syntactic = new Syntactic(lexical, sourceCode);

        try {
            syntactic.run();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

}
