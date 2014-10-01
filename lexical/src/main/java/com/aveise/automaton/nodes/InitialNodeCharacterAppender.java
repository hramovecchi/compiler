package com.aveise.automaton.nodes;

import com.aveise.automaton.reader.DefaultInitialNodeManager;

/**
 * Represent the behavior of an <i>character appender</i> node of type
 * <b>initial node</b>. The <i>character appender</i> node append a single
 * character to the tokens list.
 * 
 * @author pmvillafane
 */
public class InitialNodeCharacterAppender extends InitialNodeTransient {

    /**
     * Default constructor.
     * 
     * @param name the name of the node.
     * @param initialNodeManager an instance of the initial node manager.
     */
    public InitialNodeCharacterAppender(final String name,
            final DefaultInitialNodeManager initialNodeManager) {
        super(name, initialNodeManager);
    }

    @Override
    protected void doSomething(final Character token) {
        initialNodeManager.append(token);
    }

}
