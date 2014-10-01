package com.aveise.automaton.nodes;

import com.aveise.automaton.reader.DefaultNodeManager;

/**
 * Represent the behavior of an <i>character appender</i> node of type
 * <b>node</b>. The <i>character appender</i> node append a single character
 * to the tokens list.
 * 
 * @author pmvillafane
 */
public class NodeCharacterAppender extends NodeTransient {

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     * @param nodeManager an instance of the node manager.
     */
    public NodeCharacterAppender(final String name,
            final DefaultNodeManager nodeManager) {
        super(name, nodeManager);
    }

    @Override
    protected void doSomething(final Character token) {
        nodeManager.append(token);
    }

}
