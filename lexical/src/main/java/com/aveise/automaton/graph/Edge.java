package com.aveise.automaton.graph;

import static com.aveise.automaton.utils.ParamUtils.hasLength;
import static com.aveise.automaton.utils.ParamUtils.notNull;

/**
 * Models the behavior of an edge.
 * 
 * @author pmvillafane
 * 
 * @param <T> the token element associated to the <i>Automaton</i>.
 */
public class Edge<T> {

    // The name of the node origin.
    private final String origin;

    // The name of the node destiny.
    private final String destiny;

    // List of tokens which represent the edges between the node origin and the
    // node destiny.
    private final Iterable<T> tokens;

    /**
     * Default constructor.
     * 
     * @param origin the name of the node origin. This cannot be <b>null</b>
     *            or empty.
     * @param destiny the name of the node destiny. This cannot be <b>null</b>
     *            or empty.
     * @param tokens list of tokens which represent the edges between the node
     *            origin and the node destiny. The list of tokens cannot be
     *            <b>null<b> or empty.
     */
    public Edge(final String origin, final String destiny,
            final Iterable<T> tokens) {
        hasLength(origin,
                "The name of the node origin cannot be null or empty.");
        hasLength(destiny,
                "The name of the node destiny cannot be null or empty.");
        notNull(tokens, "The tokens list cannot be null.");

        this.origin = origin;
        this.destiny = destiny;
        this.tokens = tokens;
    }

    /**
     * Gets the name of the node origin.
     * 
     * @return retrieve the name of the node origin. This value cannot be
     *         <b>null</b> or empty.
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Gets the name of the node destiny.
     * 
     * @return retrieve the name of the node destiny. This value cannot be
     *         <b>null</b> or empty.
     */
    public String getDestiny() {
        return destiny;
    }

    /**
     * Gets the tokens which represent the edges between the node
     * origin and the node destiny.
     * 
     * @return retrieve the tokens. The tokens cannot be <b>null<b> or empty.
     */
    public Iterable<T> getTokens() {
        return tokens;
    }

}
