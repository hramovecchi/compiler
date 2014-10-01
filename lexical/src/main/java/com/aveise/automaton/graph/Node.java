package com.aveise.automaton.graph;

import static com.aveise.automaton.utils.ParamUtils.hasLength;
import static com.aveise.automaton.utils.ParamUtils.notNull;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Models the behavior of a node.
 * 
 * @author pmvillafane
 * 
 * @param <T> the token element associated to the <i>Automaton</i>.
 */
public abstract class Node<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Node.class);

    // List of the destinies associated to the node.
    private final Map<T, Node<T>> destinies = new HashMap<T, Node<T>>();

    // Name of the node.
    private final String name;

    // Name assigned to the previous node.
    private String previousName;

    /**
     * Default constructor.
     * 
     * @param name the name of the node. This value cannot be <b>null</b> or
     *            empty.
     */
    public Node(final String name) {
        hasLength(name, "The name of the node cannot be null or empty.");
        this.name = name;
    }

    /**
     * Add a destiny to the node.
     * 
     * @param tokens the list of tokens used to create the edge between the
     *            current node and the node destiny. The tokens cannot be
     *            <b>null</b> or empty.
     * @param destiny the node destiny. This value cannot be <b>null</b>.
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
     * @param token the token to evaluate.
     * @return the node destiny.
     */
    public final Node<T> next(final T token) {
        Node<T> destiny = destinies.get(token);

        try {
            destiny.previousName = name;
            destiny.doSomething(token);
        } catch (NullPointerException e) {
            LOGGER.error("Error iterating on token: {}", token);
            throw new IllegalStateException(name, e);
        }
        return destiny;
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
     * Gets the name assigned to the previous node.
     * 
     * @return retrieve the name of the previous node. This value cannot be
     *         <b>null</b>
     *         or empty.
     */
    public String getPreviousName() {
        return previousName;
    }

    /**
     * Abstract method required to give an special behavior to the node.
     * 
     * @param token the token to evaluate.
     */
    protected abstract void doSomething(T token);

}
