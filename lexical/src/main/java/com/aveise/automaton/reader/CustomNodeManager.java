package com.aveise.automaton.reader;

/**
 * Manages all the instance of the nodes.
 * 
 * @author pmvillafane
 */
public class CustomNodeManager extends DefaultNodeManager {

    private final Object custom;

    /**
     * Default constructor.
     * 
     * @param custom any kind of object to be associated to the node, this
     *            object can be used for any purpose.
     */
    public CustomNodeManager(final Object custom) {
        this.custom = custom;
    }

    @Override
    protected Class<?>[] getConstructorClassArgs() {
        return new Class<?>[]{String.class, custom.getClass()};
    }

    @Override
    protected Object[] getConstructorObjectArgs(final String node) {
        return new Object[]{node, custom};
    }

}
