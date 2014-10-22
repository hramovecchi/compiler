package ar.exa.edu.unicen.compiler.lexical;

import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.error;
import static com.aveise.automaton.utils.ParamUtils.buildProgramArguments;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.exa.edu.unicen.compiler.lexical.analyzer.LexicalAnalyzer;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;
import ar.exa.edu.unicen.compiler.lexical.utils.CompilerException;
import ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.Phase;

import com.aveise.automaton.graph.IllegalStateException;
import com.aveise.automaton.graph.Node;
import com.aveise.automaton.reader.AutomatonFileReader;

/**
 * Main class used to perform the lexical analyzer.
 *
 * @author pmvillafane
 */
public class Lexical {

    private static final Logger LOGGER = LoggerFactory.getLogger(Lexical.class);

    private static final String SOURCE_CODE = "sourceCode";

    public static final String GRAMMAR_FILE = "/language.grammar";

    private final AutomatonFileReader automatonFileReader;

    private final LexicalAnalyzer lexicalAnalyzer;

    /**
     * Default constructor.
     *
     * @param grammarFile
     *            the grammar file.
     * @throws IOException
     *             throws an exception in case of error reading the file.
     */
    public Lexical(final InputStream grammarFile)
            throws IOException {
        this.lexicalAnalyzer = new LexicalAnalyzer();
        this.automatonFileReader =
                new AutomatonFileReader(grammarFile, this.lexicalAnalyzer);
    }

    /**
     * Runs the Lexical Analyzer to evaluate the given a source code.
     *
     * @param sourceCode
     *            the source code to evaluate in lexically.
     * @return list of tuples detected during the lexical analysis.
     * @throws IOException
     *             throws an exception in case of error reading the file.
     */
    public List<Tuple> run(final InputStream sourceCode) throws IOException {

        this.lexicalAnalyzer.init();

        final BufferedReader reader =
                new BufferedReader(new InputStreamReader(sourceCode));

        try {
            int r;
            Node<Character> node = this.automatonFileReader.initialNode();
            while ((r = reader.read()) != -1) {
                final char c = (char) r;

                // Counting lines.
                this.lexicalAnalyzer.countLine(c);

                // Moving to the next token.
                node = node.next(c);
            }

            // Moving to the next token to consider a particular case when EOF
            // is the last character wrote.
            node = node.next(' ');

            reader.close();
        } catch (final CompilerException e) {
            LOGGER.error(e.getMessage());

            // Terminate the program in case of error.
            System.exit(0);
        } catch (final IllegalStateException e) {

            try {
                error(Phase.LEXICAL, this.lexicalAnalyzer.getLine(), String
                        .format("Caracter no válido %s", e.getToken()));
            } catch (final CompilerException ex) {
                LOGGER.error(ex.getMessage());
            }
            // Terminate the program in case of error.
            System.exit(0);

        }

        return this.lexicalAnalyzer.getTuples();
    }

    /**
     * Runs batch process to analyze lexically the given source code.
     *
     * @param args
     *            to specify the source code file to analyzer write
     *            --sourceCode=filename from console. For instance, something
     *            like <code>java -jar program.jar --sourceCode=filename</code>
     * @throws IOException
     *             throws an exception in case of error reading the source code
     *             file.
     */
    public static void main(final String[] args) throws IOException {

        final Map<String, String> programArgs = buildProgramArguments(args);
        final InputStream sourceCode =
                new FileInputStream(programArgs.get(SOURCE_CODE));
        final InputStream grammarFile =
                Lexical.class.getResourceAsStream(GRAMMAR_FILE);
        final Lexical lexical = new Lexical(grammarFile);

        lexical.run(sourceCode);
    }

}
