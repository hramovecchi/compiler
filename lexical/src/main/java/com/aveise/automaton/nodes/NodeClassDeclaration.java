package com.aveise.automaton.nodes;

import com.aveise.automaton.reader.DefaultNodeManager;

/**
 * Represent the behavior of an <i>class declaration</i> node of type
 * <b>node</b>. The <i>class declaration</i> node builds the instance of the
 * node using reflection according to the canonical name of the class
 * associated to the nodes in the nodes automaton.
 * 
 * @author pmvillafane
 */
public class NodeClassDeclaration extends NodeTransient {

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     * @param nodeManager an instance of the node manager.
     */
    public NodeClassDeclaration(final String name,
            final DefaultNodeManager nodeManager) {
        super(name, nodeManager);
    }

    @Override
    protected void doSomething(final Character token) {
        nodeManager.buildNode();
    }

}
