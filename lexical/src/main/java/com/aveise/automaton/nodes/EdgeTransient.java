package com.aveise.automaton.nodes;

import com.aveise.automaton.graph.Node;
import com.aveise.automaton.reader.DefaultEdgeManager;

/**
 * Represent the behavior of an <i>transient</i> node of type
 * <b>edge</b>. The <i>transient</i> node is used to iterate between the
 * states of the edges automaton without include any kind of additional
 * behavior.
 * 
 * @author pmvillafane
 */
public class EdgeTransient extends Node<Character> {

    protected DefaultEdgeManager edgeManager;

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     * @param edgeManager an instance of the edge manager.
     */
    public EdgeTransient(final String name, final DefaultEdgeManager edgeManager) {
        super(name);
        this.edgeManager = edgeManager;
    }

    @Override
    protected void doSomething(final Character token) {

    }

}
