package com.aveise.automaton.nodes;

import com.aveise.automaton.reader.DefaultNodeManager;

/**
 * Represent the behavior of an <i>name definition</i> node of type
 * <b>node</b>. The <i>name definition</i> assign the name of the node to the
 * node.
 * 
 * @author pmvillafane
 */
public class NodeNameDefinition extends NodeTransient {

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     * @param initialNodeManager an instance of the initial node manager.
     */
    public NodeNameDefinition(final String name,
            final DefaultNodeManager nodeManager) {
        super(name, nodeManager);
    }

    @Override
    protected void doSomething(final Character token) {
        nodeManager.assignNodeName();
    }

}
