package com.aveise.automaton.reader;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aveise.automaton.graph.Node;
import com.aveise.automaton.nodes.DynamicClassLoadingException;

/**
 * Manages all the data related to the nodes.
 * 
 * @author pmvillafane
 */
public class DefaultNodeManager extends BaseNodeManager {

    private List<String> nodeNames = new ArrayList<String>();

    private final Map<String, Node<Character>> nodes =
            new HashMap<String, Node<Character>>();

    /**
     * Assign the name of the node.
     */
    public final void assignNodeName() {
        nodeNames.add(getText());
        empty();
    }

    /**
     * Gets the class arguments used to construct the node using reflection.
     * 
     * @return retrieve an array which contains all the classes instantiated.
     */
    protected Class<?>[] getConstructorClassArgs() {
        return new Class<?>[]{String.class};
    }

    /**
     * Gets the objects arguments used to construct the node using reflection.
     * 
     * @param node the name of the node.
     * @return retrieve an array which contains all the objects instantiated.
     */
    protected Object[] getConstructorObjectArgs(final String node) {
        return new Object[]{node};
    }

    /**
     * Builds the instance of the node using reflection.
     */
    public final void buildNode() {
        final String clazz = getText();

        try {
            @SuppressWarnings("unchecked")
            final Class<Node<Character>> nodeClass =
                    (Class<Node<Character>>) Class.forName(clazz);

            for (String nodeName : nodeNames) {
                final Node<Character> node =
                        nodeClass
                                .getConstructor(getConstructorClassArgs())
                                .newInstance(getConstructorObjectArgs(nodeName));
                nodes.put(nodeName, node);
            }
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new DynamicClassLoadingException(clazz, e);
        }

        empty();
        nodeNames = new ArrayList<String>();
    }

    /**
     * Gets a map which contains all the nodes definition.
     * 
     * @return a map of nodes.
     */
    public final Map<String, Node<Character>> getNodes() {
        return Collections.unmodifiableMap(nodes);
    }

}
