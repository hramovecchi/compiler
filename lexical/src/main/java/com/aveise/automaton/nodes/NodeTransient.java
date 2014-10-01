package com.aveise.automaton.nodes;

import com.aveise.automaton.graph.Node;
import com.aveise.automaton.reader.DefaultNodeManager;

/**
 * Represent the behavior of an <i>transient</i> node of type
 * <b>node</b>. The <i>transient</i> node is used to iterate between
 * the states of the nodes automaton without include any kind of additional
 * behavior.
 * 
 * @author pmvillafane
 */
public class NodeTransient extends Node<Character> {

    protected DefaultNodeManager nodeManager;

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     * @param nodeManager an instance of the node manager.
     */
    public NodeTransient(final String name, final DefaultNodeManager nodeManager) {
        super(name);
        this.nodeManager = nodeManager;
    }

    @Override
    protected void doSomething(final Character token) {

    }

}
