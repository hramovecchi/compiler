package com.aveise.automaton.reader;

/**
 * Manages all the data related to the initial node.
 * 
 * @author pmvillafane
 */
public class DefaultInitialNodeManager extends BaseNodeManager {

    private String initialNode;

    /**
     * Assign the name of the initial node.
     */
    public final void assignName() {
        initialNode = getText();
    }

    /**
     * Gets the name of the initial node.
     * 
     * @return the name of the initial node.
     */
    public final String getInitialNode() {
        return initialNode;
    }

}
