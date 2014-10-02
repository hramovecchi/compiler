package com.aveise.automaton.graph;

import static com.aveise.automaton.utils.ParamUtils.hasLength;
import static com.aveise.automaton.utils.ParamUtils.notNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Models the behavior of a node.
 * 
 * @author pmvillafane
 * 
 * @param <T>
 *            the token element associated to the <i>Automaton</i>.
 */
public abstract class Node<T> {

    // List of the destinies associated to the node.
    private final Map<T, Node<T>> destinies = new HashMap<T, Node<T>>();

    // Name of the node.
    private final String name;

    // Contains the reference to the previous node. This value is useful to
    // know the previous state.
    private Node<T> previousNode;

    // Identify the next node to move.
    protected Node<T> moveTo;

    /**
     * Default constructor.
     * 
     * @param name
     *            the name of the node. This value cannot be <b>null</b> or
     *            empty.
     */
    public Node(final String name) {
        hasLength(name, "The name of the node cannot be null or empty.");
        this.name = name;
    }

    /**
     * Add a destiny to the node.
     * 
     * @param tokens
     *            the list of tokens used to create the edge between the current
     *            node and the node destiny. The tokens cannot be <b>null</b> or
     *            empty.
     * @param destiny
     *            the node destiny. This value cannot be <b>null</b>.
     */
    public final void addDestiny(final Iterable<T> tokens, final Node<T> destiny) {
        notNull(destiny, "The node destiny cannot be null.");
        notNull(tokens, "The tokens cannot be null or empty.");
        for (T token : tokens) {
            destinies.put(token, destiny);
        }
    }

    /**
     * Retrieve the node destiny from the current node.
     * 
     * @param token
     *            the token to evaluate.
     * @return the node destiny.
     */
    public final Node<T> next(final T token) {
        Node<T> destiny = destinies.get(token);

        try {
            destiny.moveTo = destiny;
            destiny.previousNode = this;
            destiny.doSomething(token);
        } catch (NullPointerException e) {
            throw new IllegalStateException(name, token, e);
        }
        return destiny.moveTo;
    }

    /**
     * Gets the name of the node.
     * 
     * @return retrieve the name of the node. This value cannot be <b>null</b>
     *         or empty.
     */
    public final String getName() {
        return name;
    }

    /**
     * Gets the previous node.
     * 
     * @return retrieve the previous node. This value cannot be <b>null</b>.
     */
    public Node<T> getPreviousNode() {
        return previousNode;
    }

    /**
     * Abstract method required to give an special behavior to the node.
     * 
     * @param token
     *            the token to evaluate.
     */
    protected abstract void doSomething(T token);

}
