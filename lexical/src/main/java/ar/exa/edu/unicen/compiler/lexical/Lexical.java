package ar.exa.edu.unicen.compiler.lexical;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.exa.edu.unicen.compiler.lexical.analyzer.LexicalAnalyzer;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;
import ar.exa.edu.unicen.compiler.lexical.semantic.actions.SemanticActionException;

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

    private static final String REGEX_TO_SPLIT_EQUAL =
            "[=]+(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    public static final String GRAMMAR_FILE = "/language.grammar";

    private final AutomatonFileReader automatonFileReader;

    private final LexicalAnalyzer lexicalAnalyzer;

    /**
     * Default Lexical.
     *
     * @param grammarFile
     *            the grammar file.
     * @throws IOException
     *             throws an exception in case of error reading the file.
     */
    public Lexical(final InputStream grammarFile)
            throws IOException {
        lexicalAnalyzer = new LexicalAnalyzer();
        automatonFileReader =
                new AutomatonFileReader(grammarFile, lexicalAnalyzer);
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

        lexicalAnalyzer.init();

        final BufferedReader reader =
                new BufferedReader(new InputStreamReader(sourceCode));

        try {
            int r;
            Node<Character> node = automatonFileReader.initialNode();
            while ((r = reader.read()) != -1) {
                node = node.next((char) r);
            }
            reader.close();
        } catch (SemanticActionException e) {
            LOGGER.error(e.getMessage());
        } catch (IllegalStateException e) {
            LOGGER.error(String.format("Caracter inválido: %s", e.getToken()));
        }

        return lexicalAnalyzer.getTuples();
    }

    /**
     * Builds a {@link Map} with the program arguments. For instance, if the
     * user define the <code>--arg1=value1 --arg2=value2</code> the {@link Map}
     * will be created in the following way:<br/>
     * <code>map.put("arg1", "value1");</code><br/>
     * <code>map.put("arg2", "value2");</code><br/>
     * 
     * @param args
     *            list of program arguments.
     * @return a {@link Map} containing the program arguments.
     */
    public static Map<String, String> buildProgramArguments(final String[] args) {
        final Map<String, String> programArguments =
                new HashMap<String, String>();

        for (String arg : args) {
            // All the program arguments must start with --
            if (arg.startsWith("--")) {
                final String[] keyValue = arg.split(REGEX_TO_SPLIT_EQUAL);
                programArguments.put(keyValue[0].substring(2), keyValue[1]
                        .replaceAll("^\"|\"$", ""));
            }
        }

        return programArguments;
    }

    public static void main(String[] args) throws IOException {

        final Map<String, String> programArgs = buildProgramArguments(args);
        final InputStream sourceCode =
                new FileInputStream(programArgs.get(SOURCE_CODE));
        final InputStream grammarFile =
                Lexical.class.getResourceAsStream(GRAMMAR_FILE);
        final Lexical lexical = new Lexical(grammarFile);

        lexical.run(sourceCode);
    }

}
