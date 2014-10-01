package com.aveise.automaton.nodes;

import com.aveise.automaton.reader.DefaultEdgeManager;

/**
 * Represent the behavior of an <i>character appender</i> node of type
 * <b>edge</b>. The <i>character appender</i> node append a single character
 * to the tokens list.
 * 
 * @author pmvillafane
 */
public class EdgeCharacterAppender extends EdgeTransient {

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     * @param edgeManager an instance of the edge manager.
     */
    public EdgeCharacterAppender(final String name,
            final DefaultEdgeManager edgeManager) {
        super(name, edgeManager);
    }

    @Override
    protected void doSomething(final Character token) {
        edgeManager.append(token);
    }

}
