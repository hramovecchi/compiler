package com.aveise.automaton;

import com.aveise.automaton.graph.Node;

/**
 * Assign the initial state for the finite state machine.
 * 
 * @author pmvillafane
 * 
 * @param <T> the token element associated to the <i>Automaton</i>.
 */
public final class InitialState<T> {

    /**
     * Assign the initial state.
     * 
     * @param initial the initial state.
     * @return retrieve the initial node.
     */
    public Node<T> assign(final Node<T> initial) {
        return initial;
    }

}
