package com.aveise.automaton.reader;

import static com.aveise.automaton.utils.CharUtils.alphabet;
import static com.aveise.automaton.utils.CharUtils.alphabetLowerCase;
import static com.aveise.automaton.utils.CharUtils.alphabetUpperCase;
import static com.aveise.automaton.utils.CharUtils.alphanumeric;
import static com.aveise.automaton.utils.CharUtils.alphanumericLowerCase;
import static com.aveise.automaton.utils.CharUtils.alphanumericUpperCase;
import static com.aveise.automaton.utils.CharUtils.anything;
import static com.aveise.automaton.utils.CharUtils.carriageReturn;
import static com.aveise.automaton.utils.CharUtils.comma;
import static com.aveise.automaton.utils.CharUtils.newline;
import static com.aveise.automaton.utils.CharUtils.nonAlphabet;
import static com.aveise.automaton.utils.CharUtils.nonAlphabetLowerCase;
import static com.aveise.automaton.utils.CharUtils.nonAlphabetUpperCase;
import static com.aveise.automaton.utils.CharUtils.nonAlphanumeric;
import static com.aveise.automaton.utils.CharUtils.nonAlphanumericLowerCase;
import static com.aveise.automaton.utils.CharUtils.nonAlphanumericUpperCase;
import static com.aveise.automaton.utils.CharUtils.nonNumber;
import static com.aveise.automaton.utils.CharUtils.number;
import static com.aveise.automaton.utils.CharUtils.semicolon;
import static com.aveise.automaton.utils.CharUtils.separator;
import static com.aveise.automaton.utils.CharUtils.tab;
import static com.aveise.automaton.utils.CharUtils.whitespace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aveise.automaton.graph.Edge;

/**
 * Manages all the data related to the edges.
 * 
 * @author pmvillafane
 */
public class DefaultEdgeManager extends BaseNodeManager {

    private static final String ANYTHING = "anything";
    private static final String NUMBER = "number";
    private static final String ALPHABET = "alphabet";
    private static final String ALPHABET_LC = "alphabetLowerCase";
    private static final String ALPHABET_UP = "alphabetUpperCase";
    private static final String ALPHANUMERIC = "alphanumeric";
    private static final String ALPHANUMERIC_LC = "alphanumericLowerCase";
    private static final String ALPHANUMERIC_UP = "alphanumericUpperCase";
    private static final String NON_NUMBER = "!number";
    private static final String NON_ALPHABET = "!alphabet";
    private static final String NON_ALPHABET_LC = "!alphabetLowerCase";
    private static final String NON_ALPHABET_UP = "!alphabetUpperCase";
    private static final String NON_ALPHANUMERIC = "!alphanumeric";
    private static final String NON_ALPHANUMERIC_LC = "!alphanumericLowerCase";
    private static final String NON_ALPHANUMERIC_UP = "!alphanumericUpperCase";
    private static final String SEPARATOR = "separator";
    private static final String COMMA = "comma";
    private static final String SEMICOLON = "semicolon";
    private static final String WHITESPACE = "whitespace";
    private static final String NEWLINE = "newline";
    private static final String TAB = "tab";
    private static final String CARRIAGE_RETURN = "carriageReturn";

    private final Map<String, Collection<Character>> utils =
            new HashMap<String, Collection<Character>>();

    private final List<Edge<Character>> edges =
            new ArrayList<Edge<Character>>();

    private List<Character> tokens = new ArrayList<Character>();
    private String origin;
    private String destine;

    /**
     * Default constructor.
     */
    public DefaultEdgeManager() {
        utils.put(ANYTHING, anything);

        utils.put(NUMBER, number);
        utils.put(ALPHABET, alphabet);
        utils.put(ALPHABET_LC, alphabetLowerCase);
        utils.put(ALPHABET_UP, alphabetUpperCase);
        utils.put(ALPHANUMERIC, alphanumeric);
        utils.put(ALPHANUMERIC_LC, alphanumericLowerCase);
        utils.put(ALPHANUMERIC_UP, alphanumericUpperCase);

        utils.put(NON_NUMBER, nonNumber);
        utils.put(NON_ALPHABET, nonAlphabet);
        utils.put(NON_ALPHABET_LC, nonAlphabetLowerCase);
        utils.put(NON_ALPHABET_UP, nonAlphabetUpperCase);
        utils.put(NON_ALPHANUMERIC, nonAlphanumeric);
        utils.put(NON_ALPHANUMERIC_LC, nonAlphanumericLowerCase);
        utils.put(NON_ALPHANUMERIC_UP, nonAlphanumericUpperCase);

        utils.put(SEPARATOR, separator);
        utils.put(COMMA, comma);
        utils.put(SEMICOLON, semicolon);
        utils.put(WHITESPACE, whitespace);
        utils.put(NEWLINE, newline);
        utils.put(TAB, tab);
        utils.put(CARRIAGE_RETURN, carriageReturn);
    }

    /**
     * Assign the name of the node origin.
     */
    public final void assignNodeOrigin() {
        origin = getText();
        empty();
    }

    /**
     * Assign the name of the node destiny.
     */
    public final void assignNodeDestiny() {
        destine = getText();
        empty();
    }

    /**
     * Add a token token to the token list.
     * 
     * @param token the token to add.
     */
    public final void addToken(final Character token) {
        tokens.add(token);
    }

    /**
     * Gathers a list of tokens from the <i>Token Utilities</i>.
     */
    public final void gatherTokensFromUtils() {
        final String functionName = getText();
        final Collection<Character> inputs = utils.get(functionName);
        tokens.addAll(inputs);
        empty();
    }

    /**
     * Link the edges to the nodes.
     */
    public final void linkEdges() {
        edges.add(new Edge<Character>(origin, destine, tokens));
        tokens = new ArrayList<Character>();
    }

    /**
     * Gets the unmodifiable list of edges.
     * 
     * @return the unmodifiable list of edges.
     */
    public final List<Edge<Character>> getEdges() {
        return Collections.unmodifiableList(edges);
    }

}
