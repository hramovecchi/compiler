package com.aveise.automaton.nodes;

import com.aveise.automaton.graph.Node;

/**
 * Represent the behavior of a <i>transient</i> node of type <b>comment</b>.
 * The <i>transient</i> node is used to iterate between the states of the edge
 * automaton without include any kind of additional behavior.
 * 
 * @author pmvillafane
 */
public class CommentTransient extends Node<Character> {

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     */
    public CommentTransient(final String name) {
        super(name);
    }

    @Override
    protected void doSomething(final Character token) {

    }

}
