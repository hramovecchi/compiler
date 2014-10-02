package ar.exa.edu.unicen.compiler.lexical.transition.states;

import ar.exa.edu.unicen.compiler.lexical.analyzer.LexicalAnalyzer;

import com.aveise.automaton.graph.Node;

/**
 * Models terminal nodes.
 * 
 * @author pmvillafane
 */
public class NodeTerminal extends Node<Character> {

    protected LexicalAnalyzer lexicalAnalyzer;

    /**
     * Default constructor.
     * 
     * @param name
     *            name assigned to the node.
     * @param lexicalAnalyzer
     *            lexical analyzer.
     */
    public NodeTerminal(final String name, final LexicalAnalyzer lexicalAnalyzer) {
        super(name);
        this.lexicalAnalyzer = lexicalAnalyzer;
    }

    @Override
    protected void doSomething(final Character c) {
        // Cleanup the characters appended and detect the type of token.
        final boolean wasTerminal =
                this.lexicalAnalyzer.buildToken(this.getPreviousNode().getName());

        if (wasTerminal) {
            this.lexicalAnalyzer.cleanTextAppender();
            this.moveTo = this.next(c);
        }
    }

}
