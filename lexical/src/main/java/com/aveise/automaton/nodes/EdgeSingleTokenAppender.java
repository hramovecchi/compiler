package com.aveise.automaton.nodes;

import com.aveise.automaton.reader.DefaultEdgeManager;

/**
 * Represent the behavior of an <i>single token appender</i> node of type
 * <b>edge</b>. The <i>single token appender</i> node is used to append a new
 * token to the token list.
 * 
 * @author pmvillafane
 */
public class EdgeSingleTokenAppender extends EdgeTransient {

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     * @param edgeManager an instance of the edge manager.
     */
    public EdgeSingleTokenAppender(final String name,
            final DefaultEdgeManager edgeManager) {
        super(name, edgeManager);
    }

    @Override
    protected void doSomething(final Character token) {
        edgeManager.addToken(token);
    }

}
