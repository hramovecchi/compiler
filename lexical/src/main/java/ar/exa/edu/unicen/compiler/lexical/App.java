package ar.exa.edu.unicen.compiler.lexical;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import ar.exa.edu.unicen.compiler.lexical.analyzer.LexicalAnalyzer;

import com.aveise.automaton.graph.Node;
import com.aveise.automaton.reader.AutomatonFileReader;

public class App {

    private static final String SOURCE_CODE = "sourceCode";

    private static final String REGEX_TO_SPLIT_EQUAL =
            "[=]+(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    public static final String GRAMMAR_FILE = "/language.grammar";

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
                App.class.getResourceAsStream(GRAMMAR_FILE);
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
    }

}
