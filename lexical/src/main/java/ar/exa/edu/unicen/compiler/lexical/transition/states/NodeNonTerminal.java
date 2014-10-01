package ar.exa.edu.unicen.compiler.lexical.transition.states;

import ar.exa.edu.unicen.compiler.lexical.analyzer.LexicalAnalyzer;

import com.aveise.automaton.graph.Node;

/**
 * Models transient nodes.
 * 
 * @author pmvillafane
 */
public class NodeNonTerminal extends Node<Character> {

    protected LexicalAnalyzer lexicalAnalyzer;

    /**
     * Default constructor.
     * 
     * @param name name assigned to the node.
     * @param lexicalAnalyzer grammar manager.
     */
    public NodeNonTerminal(final String name,
            final LexicalAnalyzer lexicalAnalyzer) {
        super(name);
        this.lexicalAnalyzer = lexicalAnalyzer;
    }

    @Override
    protected void doSomething(final Character c) {
        // Appends the character to build the token.
        this.lexicalAnalyzer.append(c);
    }

}
