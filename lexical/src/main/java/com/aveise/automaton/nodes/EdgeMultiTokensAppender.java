package com.aveise.automaton.nodes;

import com.aveise.automaton.reader.DefaultEdgeManager;

/**
 * Represent the behavior of an <i>multi tokens appender</i> node of type
 * <b>edge</b>. The <i>multi tokens appender</i> node is used to gather a list
 * of tokens from predefined functions to simplify the inclusion of tokens.
 * For instance, adding the directive @alphanumeric to the grammar file all
 * the characters which conform the alphabet and numbers from 0 to 9 will be
 * added as tokens.
 * 
 * @author pmvillafane
 */
public class EdgeMultiTokensAppender extends EdgeTransient {

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     * @param edgeManager an instance of the edge manager.
     */
    public EdgeMultiTokensAppender(final String name,
            final DefaultEdgeManager edgeManager) {
        super(name, edgeManager);
    }

    @Override
    protected void doSomething(final Character token) {
        edgeManager.gatherTokensFromUtils();
    }

}
