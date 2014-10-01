package com.aveise.automaton;

import java.util.Map;

import com.aveise.automaton.graph.Node;

/**
 * Builds an <i>Automaton</i>.
 * 
 * @author pmvillafane
 * 
 * @param <T> the token element associated to the <i>Automaton</i>.
 */
public final class Automaton<T> {

    /**
     * Define all the nodes which will conform the finite state machine.
     * 
     * @param nodes map of nodes which will conform the finite state machine.
     * @return retrieve an {@link EdgesLinker} instance required to like the
     *         edges between nodes.
     */
    public final EdgesLinker<T> define(final Map<String, Node<T>> nodes) {
        return new EdgesLinker<T>(nodes);
    }

}
