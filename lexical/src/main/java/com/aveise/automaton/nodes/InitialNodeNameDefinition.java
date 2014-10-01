package com.aveise.automaton.nodes;

import com.aveise.automaton.reader.DefaultInitialNodeManager;

/**
 * Represent the behavior of an <i>name definition</i> node of type
 * <b>initial node</b>. The <i>name definition</i> assign the name of the node
 * to the initial node.
 * 
 * @author pmvillafane
 */
public class InitialNodeNameDefinition extends InitialNodeTransient {

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     * @param initialNodeManager an instance of the initial node manager.
     */
    public InitialNodeNameDefinition(final String name,
            final DefaultInitialNodeManager initialNodeManager) {
        super(name, initialNodeManager);
    }

    @Override
    protected void doSomething(final Character token) {
        initialNodeManager.assignName();
    }

}
