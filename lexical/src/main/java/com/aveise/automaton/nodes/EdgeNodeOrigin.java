package com.aveise.automaton.nodes;

import com.aveise.automaton.reader.DefaultEdgeManager;

/**
 * Represent the behavior of an <i>origin</i> node of type
 * <b>edge</b>. The <i>origin</i> node is used to assign the node origin.
 * 
 * @author pmvillafane
 */
public class EdgeNodeOrigin extends EdgeTransient {

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     * @param edgeManager an instance of the edge manager.
     */
    public EdgeNodeOrigin(final String name,
            final DefaultEdgeManager edgeManager) {
        super(name, edgeManager);
    }

    @Override
    protected void doSomething(final Character token) {
        edgeManager.assignNodeOrigin();
    }

}
