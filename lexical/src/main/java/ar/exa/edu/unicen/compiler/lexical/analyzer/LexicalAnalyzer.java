package ar.exa.edu.unicen.compiler.lexical.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.exa.edu.unicen.compiler.lexical.semantic.actions.DoubleRangeAction;
import ar.exa.edu.unicen.compiler.lexical.semantic.actions.RangeSemanticAction;
import ar.exa.edu.unicen.compiler.lexical.semantic.actions.SemanticAction;
import ar.exa.edu.unicen.compiler.lexical.semantic.actions.StringFixerAction;
import ar.exa.edu.unicen.compiler.lexical.semantic.actions.TruncateIdAction;

/**
 * Lexical analyzer in charge of detect the type of token coming from source
 * code and perform the corresponding semantic actions.
 * 
 * @author pmvillafane
 */
public class LexicalAnalyzer {

    private final Map<String, Category> nodeCategories =
            new HashMap<String, Category>();

    private final Map<Token, SemanticAction> semanticActions =
            new HashMap<Token, SemanticAction>();

    private List<Tuple> tuples;

    private StringBuilder text;

    /**
     * Default constructor.
     */
    public LexicalAnalyzer() {

        // Associates terminal nodes to the corresponding category.
        nodeCategories.put("01", Category.TEXT);
        nodeCategories.put("02", Category.CONSTANT);
        nodeCategories.put("04", Category.CONSTANT);
        nodeCategories.put("05", Category.CONSTANT);
        nodeCategories.put("07", Category.CONSTANT);
        nodeCategories.put("08", Category.OPERATOR);
        nodeCategories.put("09", Category.OPERATOR);
        nodeCategories.put("10", Category.OPERATOR);
        nodeCategories.put("12", Category.OPERATOR);
        nodeCategories.put("13", Category.COMMENT);
        nodeCategories.put("16", Category.TEXT);
        nodeCategories.put("17", Category.OPERATOR);

        // Defines the semantic actions to perform in case of token detection.
        semanticActions.put(Token.ID, new TruncateIdAction());
        semanticActions.put(Token.CONST_DOUBLE, new DoubleRangeAction());
        semanticActions.put(Token.STRING, new StringFixerAction());
        semanticActions.put(Token.RANGE, new RangeSemanticAction());
    }

    /**
     * Appends a new character to build the token.
     * 
     * @param c
     *            the character to append.
     */
    public void append(final Character c) {
        text.append(c);
    }

    /**
     * Builds a token using the information coming from the previous node name.
     * Also, checks if the latest character correspond to an operator.
     * 
     * @param previuosNodeName
     *            the node name corresponding to the previous state.
     * @param c
     *            latest character that was recollected.
     */
    public boolean buildToken(final String previuosNodeName) {

        final Category category = nodeCategories.get(previuosNodeName);

        // Discard comments.
        if (category != null && !Category.COMMENT.equals(category)) {
            String lexeme = text.toString();
            final Token token = Token.findToken(category, lexeme);

            // Perform semantic action.
            final SemanticAction action = semanticActions.get(token);
            if (action != null) {
                // Internally adds the new tuples to the token list.
                action.doAction(lexeme, tuples, token);
            } else {
                // Adds the new tuple to the tokens list.
                final Tuple tuple = new Tuple(lexeme, token);
                tuples.add(tuple);
            }

            return true;
        }

        // Re-initialize the token appender.
        text = new StringBuilder();

        return false;
    }

    /**
     * Gets a list of tuples conformed as &lt;Token, Lexeme&gt;
     * 
     * @return the list of tuples.
     */
    public List<Tuple> getTuples() {
        return tuples;
    }

    /**
     * Initializes Lexical Analyzer.
     */
    public void init() {
        tuples = new ArrayList<Tuple>();
        cleanTextAppender();
    }

    public void cleanTextAppender() {
        text = new StringBuilder();
    }

}
