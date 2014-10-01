package com.aveise.automaton.nodes;

import com.aveise.automaton.reader.DefaultEdgeManager;

/**
 * Represent the behavior of an <i>destiny</i> node of type
 * <b>edge</b>. The <i>destiny</i> node is used to assign the node destiny.
 * 
 * @author pmvillafane
 */
public class EdgeNodeDestiny extends EdgeTransient {

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     * @param edgeManager an instance of the edge manager.
     */
    public EdgeNodeDestiny(final String name,
            final DefaultEdgeManager edgeManager) {
        super(name, edgeManager);
    }

    @Override
    protected void doSomething(final Character token) {
        edgeManager.assignNodeDestiny();
    }

}
