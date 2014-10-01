package com.aveise.automaton.nodes;

import com.aveise.automaton.reader.DefaultEdgeManager;

/**
 * Represent the behavior of an <i>single token linker</i> node of type
 * <b>edge</b>. The <i>single token linker</i> node is used to link a single
 * token to the node origin and node destiny.
 * 
 * @author pmvillafane
 */
public class EdgeSingleTokenLinker extends EdgeTransient {

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     * @param edgeManager an instance of the edge manager.
     */
    public EdgeSingleTokenLinker(final String name,
            final DefaultEdgeManager edgeManager) {
        super(name, edgeManager);
    }

    @Override
    protected void doSomething(final Character token) {
        edgeManager.linkEdges();
    }

}
