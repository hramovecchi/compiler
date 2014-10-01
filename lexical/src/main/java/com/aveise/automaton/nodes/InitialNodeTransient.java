package com.aveise.automaton.nodes;

import com.aveise.automaton.graph.Node;
import com.aveise.automaton.reader.DefaultInitialNodeManager;

/**
 * Represent the behavior of an <i>transient</i> node of type
 * <b>initial node</b>. The <i>transient</i> node is used to iterate between
 * the states of the initial node automaton without include any kind of
 * additional behavior.
 * 
 * @author pmvillafane
 */
public class InitialNodeTransient extends Node<Character> {

    protected DefaultInitialNodeManager initialNodeManager;

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     * @param initialNodeManager an instance of the initial node manager.
     */
    public InitialNodeTransient(final String name,
            final DefaultInitialNodeManager initialNodeManager) {
        super(name);
        this.initialNodeManager = initialNodeManager;
    }

    @Override
    protected void doSomething(final Character token) {

    }

}
