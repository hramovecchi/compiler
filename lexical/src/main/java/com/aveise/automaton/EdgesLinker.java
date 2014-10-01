package com.aveise.automaton;

import java.util.Map;

import com.aveise.automaton.graph.Edge;
import com.aveise.automaton.graph.Node;

/**
 * Links the edges to the nodes.
 * 
 * @author pmvillafane
 * 
 * @param <T> the token element associated to the <i>Automaton</i>.
 */
public final class EdgesLinker<T> {

    private final Map<String, Node<T>> nodes;

    /**
     * Map of all the nodes which conform the state finite machine.
     * 
     * @param nodes a map of nodes.
     */
    public EdgesLinker(final Map<String, Node<T>> nodes) {
        this.nodes = nodes;
    }

    /**
     * Links the edges to the specific nodes.
     * 
     * @param edges a list of edges to link the to the nodes.
     * @return retrieve initial node of the automaton..
     */
    public final InitialState<T> link(final Iterable<Edge<T>> edges) {
        for (Edge<T> edge : edges) {
            final Node<T> origin = nodes.get(edge.getOrigin());
            final Node<T> destine = nodes.get(edge.getDestiny());

            origin.addDestiny(edge.getTokens(), destine);
        }
        return new InitialState<T>();
    }

}
