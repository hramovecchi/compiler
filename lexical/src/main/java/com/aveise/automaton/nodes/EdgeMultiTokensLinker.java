package com.aveise.automaton.nodes;

import com.aveise.automaton.reader.DefaultEdgeManager;

/**
 * Represent the behavior of an <i>multi tokens linker</i> node of type
 * <b>edge</b>. The <i>multi tokens linker</i> node is a combination of
 * {@link EdgeMultiTokensAppender} and {@link EdgeSingleTokenLinker} that is
 * used to gather a list of tokens from predefined functions to simplify the
 * inclusion of tokens and link these tokens to the corresponding node origin
 * and node destiny.
 * 
 * @author pmvillafane
 */
public class EdgeMultiTokensLinker extends EdgeSingleTokenLinker {

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     * @param edgeManager an instance of the edge manager.
     */
    public EdgeMultiTokensLinker(final String name,
            final DefaultEdgeManager edgeManager) {
        super(name, edgeManager);
    }

    @Override
    protected void doSomething(final Character token) {
        edgeManager.gatherTokensFromUtils();
        super.doSomething(token);
    }

}
