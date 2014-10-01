package com.aveise.automaton.reader;

import static com.aveise.automaton.utils.CharUtils.alphabet;
import static com.aveise.automaton.utils.CharUtils.alphanumeric;
import static com.aveise.automaton.utils.CharUtils.anything;
import static com.aveise.automaton.utils.CharUtils.inputs;
import static com.aveise.automaton.utils.CharUtils.separator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aveise.automaton.Automaton;
import com.aveise.automaton.graph.Edge;
import com.aveise.automaton.graph.Node;
import com.aveise.automaton.nodes.CommentTransient;
import com.aveise.automaton.nodes.EdgeCharacterAppender;
import com.aveise.automaton.nodes.EdgeMultiTokensAppender;
import com.aveise.automaton.nodes.EdgeMultiTokensLinker;
import com.aveise.automaton.nodes.EdgeNodeDestiny;
import com.aveise.automaton.nodes.EdgeNodeOrigin;
import com.aveise.automaton.nodes.EdgeSingleTokenAppender;
import com.aveise.automaton.nodes.EdgeSingleTokenLinker;
import com.aveise.automaton.nodes.EdgeTransient;
import com.aveise.automaton.nodes.InitialNodeCharacterAppender;
import com.aveise.automaton.nodes.InitialNodeNameDefinition;
import com.aveise.automaton.nodes.InitialNodeTransient;
import com.aveise.automaton.nodes.NodeCharacterAppender;
import com.aveise.automaton.nodes.NodeClassDeclaration;
import com.aveise.automaton.nodes.NodeNameDefinition;
import com.aveise.automaton.nodes.NodeTransient;

/**
 * Reads the structure of a finite state machine from a grammar file existing
 * in disk and uses these information to build the automaton.
 * 
 * @author pmvillafane
 */
public class AutomatonFileReader {

    private final Map<String, Node<Character>> nodes =
            new HashMap<String, Node<Character>>();

    private final List<Edge<Character>> edges =
            new ArrayList<Edge<Character>>();

    private final DefaultNodeManager nodeManager;
    private final DefaultEdgeManager edgeManager = new DefaultEdgeManager();
    private final DefaultInitialNodeManager initialNodeManager =
            new DefaultInitialNodeManager();

    private final Node<Character> node;

    /**
     * Create an instance of the automaton file reader.
     * 
     * @param grammarFile the grammar file in an {@link InputStream} format.
     * @param charset the charset of the grammar file.
     * @param grammarManager the grammar manager used to customize the
     *            behavior of the nodes. Each node will be instantiated with
     *            the instance of the {@link GrammarFile}.
     * @throws IOException an exception is thrown in case of error while
     *             reading the grammar file.
     */
    public AutomatonFileReader(final InputStream grammarFile,
            final Charset charset, final Object grammarManager)
            throws IOException {
        nodeManager = new CustomNodeManager(grammarManager);

        final Reader reader = new InputStreamReader(grammarFile, charset);
        final BufferedReader bufferedReader = new BufferedReader(reader);

        try {
            Node<Character> node = buildFiniteStateMachine();
            int r;
            while ((r = bufferedReader.read()) != -1) {
                node = node.next((char) r);
            }
        } catch (IllegalStateException e) {
            throw new InvalidFileFormatException(e);
        }

        bufferedReader.close();
        reader.close();

        final Automaton<Character> automaton = new Automaton<Character>();
        final Map<String, Node<Character>> nodes = nodeManager.getNodes();
        final List<Edge<Character>> edges = edgeManager.getEdges();
        final String initialNodeName = initialNodeManager.getInitialNode();
        final Node<Character> initialNode = nodes.get(initialNodeName);
        node = automaton.define(nodes).link(edges).assign(initialNode);
    }

    /**
     * Create an instance of the automaton file reader using the default
     * charset of the OS.
     * 
     * @param grammarFile the grammar file in an {@link InputStream} format.
     * @param grammarManager the grammar manager used to customize the
     *            behavior of the nodes. Each node will be instantiated with
     *            the instance of the {@link GrammarFile}.
     * @throws IOException an exception is thrown in case of error while
     *             reading the grammar file.
     */
    public AutomatonFileReader(final InputStream grammarFile,
            final Object grammarManager)
            throws IOException {
        this(grammarFile, Charset.defaultCharset(), grammarManager);
    }

    /**
     * Assign a node to the nodes map.
     * 
     * @param node the node to be assigned to the nodes map.
     */
    private void assignNode(final Node<Character> node) {
        nodes.put(node.getName(), node);
    }

    /**
     * Assign all the nodes required to build the automaton to build the
     * nodes in the grammar file.
     */
    private void assignNodesForAutomatonNodes() {
        assignNode(new NodeTransient("n01", nodeManager));
        assignNode(new NodeTransient("n02", nodeManager));
        assignNode(new NodeTransient("n03", nodeManager));
        assignNode(new NodeTransient("n04", nodeManager));
        assignNode(new NodeTransient("n05", nodeManager));
        assignNode(new NodeTransient("n06", nodeManager));
        assignNode(new NodeTransient("n07", nodeManager));
        assignNode(new NodeTransient("n08", nodeManager));
        assignNode(new NodeTransient("n09", nodeManager));
        assignNode(new NodeCharacterAppender("n10", nodeManager));
        assignNode(new NodeTransient("n11", nodeManager));
        assignNode(new NodeNameDefinition("n12", nodeManager));
        assignNode(new NodeNameDefinition("n13", nodeManager));
        assignNode(new NodeTransient("n14", nodeManager));
        assignNode(new NodeCharacterAppender("n15", nodeManager));
        assignNode(new NodeCharacterAppender("n16", nodeManager));
        assignNode(new NodeTransient("n17", nodeManager));
        assignNode(new NodeTransient("n18", nodeManager));
        assignNode(new NodeClassDeclaration("n19", nodeManager));
        assignNode(new NodeTransient("n20", nodeManager));
        assignNode(new NodeTransient("n21", nodeManager));
    }

    /**
     * Assign all the nodes required to build the automaton to build the
     * edges in the grammar file.
     */
    private void assignNodesForAutomatonEdges() {
        assignNode(new EdgeTransient("e01", edgeManager));
        assignNode(new EdgeTransient("e02", edgeManager));
        assignNode(new EdgeTransient("e03", edgeManager));
        assignNode(new EdgeTransient("e04", edgeManager));
        assignNode(new EdgeTransient("e05", edgeManager));
        assignNode(new EdgeTransient("e06", edgeManager));
        assignNode(new EdgeTransient("e07", edgeManager));
        assignNode(new EdgeTransient("e08", edgeManager));
        assignNode(new EdgeTransient("e09", edgeManager));
        assignNode(new EdgeCharacterAppender("e10", edgeManager));
        assignNode(new EdgeTransient("e11", edgeManager));
        assignNode(new EdgeNodeOrigin("e12", edgeManager));
        assignNode(new EdgeTransient("e13", edgeManager));
        assignNode(new EdgeCharacterAppender("e14", edgeManager));
        assignNode(new EdgeTransient("e15", edgeManager));
        assignNode(new EdgeNodeDestiny("e16", edgeManager));
        assignNode(new EdgeSingleTokenAppender("e17", edgeManager));
        assignNode(new EdgeTransient("e18", edgeManager));
        assignNode(new EdgeTransient("e19", edgeManager));
        assignNode(new EdgeCharacterAppender("e20", edgeManager));
        assignNode(new EdgeTransient("e21", edgeManager));
        assignNode(new EdgeMultiTokensLinker("e22", edgeManager));
        assignNode(new EdgeMultiTokensAppender("e23", edgeManager));
        assignNode(new EdgeTransient("e24", edgeManager));
        assignNode(new EdgeSingleTokenLinker("e25", edgeManager));
        assignNode(new EdgeTransient("e26", edgeManager));
        assignNode(new EdgeTransient("e27", edgeManager));
        assignNode(new EdgeTransient("e28", edgeManager));
        assignNode(new EdgeTransient("e29", edgeManager));
    }

    /**
     * Assign all the nodes required to build the automaton to set the initial
     * node in the grammar file.
     */
    private void assignNodesForAutomatonInitialNode() {
        assignNode(new InitialNodeTransient("i01", initialNodeManager));
        assignNode(new InitialNodeTransient("i02", initialNodeManager));
        assignNode(new InitialNodeTransient("i03", initialNodeManager));
        assignNode(new InitialNodeTransient("i04", initialNodeManager));
        assignNode(new InitialNodeTransient("i05", initialNodeManager));
        assignNode(new InitialNodeTransient("i06", initialNodeManager));
        assignNode(new InitialNodeTransient("i07", initialNodeManager));
        assignNode(new InitialNodeTransient("i08", initialNodeManager));
        assignNode(new InitialNodeTransient("i09", initialNodeManager));
        assignNode(new InitialNodeTransient("i10", initialNodeManager));
        assignNode(new InitialNodeTransient("i11", initialNodeManager));
        assignNode(new InitialNodeCharacterAppender("i12", initialNodeManager));
        assignNode(new InitialNodeTransient("i13", initialNodeManager));
        assignNode(new InitialNodeNameDefinition("i14", initialNodeManager));
        assignNode(new InitialNodeTransient("i15", initialNodeManager));
        assignNode(new InitialNodeTransient("i16", initialNodeManager));
    }

    /**
     * Assign all the nodes required to build the automaton to add comments in
     * the grammar file.
     */
    private void assignNodesForAutomatonComments() {
        assignNode(new CommentTransient("c01"));
        assignNode(new CommentTransient("c02"));
        assignNode(new CommentTransient("c03"));
    }

    /**
     * Add an edge between the node origin and the node destiny.
     * 
     * @param origin the node origin.
     * @param destiny the node destiny.
     * @param input the tokens required to link the node origin to the node
     *            destiny.
     */
    private void addEdge(final String origin, final String destiny,
            final Iterable<Character> input) {
        edges.add(new Edge<Character>(origin, destiny, input));
    }

    /**
     * Add all the required edges to link the nodes automaton used to build
     * and validate the format of the grammar file.
     */
    private void addEdgesForAutomatonNodes() {
        addEdge("n01", "n02", inputs('n', 'N'));
        addEdge("n02", "n03", inputs('o', 'O'));
        addEdge("n03", "n04", inputs('d', 'D'));
        addEdge("n04", "n05", inputs('e', 'E'));
        addEdge("n05", "n06", inputs('s', 'S'));
        addEdge("n06", "n07", separator);
        addEdge("n06", "n08", inputs('{'));
        addEdge("n07", "n07", separator);
        addEdge("n07", "n08", inputs('{'));
        addEdge("n08", "n09", separator);
        addEdge("n08", "n10", alphanumeric);
        addEdge("n09", "n09", separator);
        addEdge("n09", "n10", alphanumeric);
        addEdge("n10", "n10", alphanumeric);
        addEdge("n10", "n11", separator);
        addEdge("n10", "n12", inputs(','));
        addEdge("n10", "n13", inputs(':'));
        addEdge("n11", "n11", separator);
        addEdge("n11", "n12", inputs(','));
        addEdge("n11", "n13", inputs(':'));
        addEdge("n12", "n09", separator);
        addEdge("n12", "n10", alphanumeric);
        addEdge("n13", "n14", separator);
        addEdge("n13", "n15", alphabet);
        addEdge("n14", "n14", separator);
        addEdge("n14", "n15", alphabet);
        addEdge("n15", "n15", alphanumeric);
        addEdge("n15", "n16", inputs('.'));
        addEdge("n15", "n18", separator);
        addEdge("n15", "n19", inputs(';'));
        addEdge("n16", "n15", alphabet);
        addEdge("n16", "n17", separator);
        addEdge("n17", "n17", separator);
        addEdge("n17", "n15", alphabet);
        addEdge("n18", "n16", inputs('.'));
        addEdge("n18", "n18", separator);
        addEdge("n18", "n19", inputs(';'));
        addEdge("n19", "n10", alphanumeric);
        addEdge("n19", "n20", separator);
        addEdge("n19", "n21", inputs('}'));
        addEdge("n20", "n10", alphanumeric);
        addEdge("n20", "n20", separator);
        addEdge("n20", "n21", inputs('}'));
    }

    /**
     * Add all the required edges to link the edges automaton used to build
     * and validate the format of grammar file.
     */
    private void addEdgesForAutomatonEdges() {
        addEdge("e01", "e02", inputs('e', 'E'));
        addEdge("e02", "e03", inputs('d', 'D'));
        addEdge("e03", "e04", inputs('g', 'G'));
        addEdge("e04", "e05", inputs('e', 'E'));
        addEdge("e05", "e06", inputs('s', 'S'));
        addEdge("e06", "e07", separator);
        addEdge("e06", "e08", inputs('{'));
        addEdge("e07", "e07", separator);
        addEdge("e07", "e08", inputs('{'));
        addEdge("e08", "e09", separator);
        addEdge("e08", "e10", alphanumeric);
        addEdge("e09", "e09", separator);
        addEdge("e09", "e10", alphanumeric);
        addEdge("e10", "e10", alphanumeric);
        addEdge("e10", "e11", separator);
        addEdge("e10", "e12", inputs(','));
        addEdge("e11", "e11", separator);
        addEdge("e11", "e12", inputs(','));
        addEdge("e12", "e13", separator);
        addEdge("e12", "e14", alphanumeric);
        addEdge("e13", "e13", separator);
        addEdge("e13", "e14", alphanumeric);
        addEdge("e14", "e14", alphanumeric);
        addEdge("e14", "e15", separator);
        addEdge("e14", "e16", inputs(':'));
        addEdge("e15", "e15", separator);
        addEdge("e15", "e16", inputs(':'));
        addEdge("e16", "e17", anything);
        addEdge("e16", "e18", separator);
        addEdge("e16", "e19", inputs('@'));
        addEdge("e17", "e24", separator);
        addEdge("e17", "e25", inputs(';'));
        addEdge("e17", "e26", inputs(','));
        addEdge("e18", "e17", anything);
        addEdge("e18", "e18", separator);
        addEdge("e18", "e19", inputs('@'));
        addEdge("e19", "e17", inputs('@'));
        addEdge("e19", "e20", alphanumeric);
        addEdge("e19", "e20", inputs('!'));
        addEdge("e20", "e20", alphanumeric);
        addEdge("e20", "e21", separator);
        addEdge("e20", "e22", inputs(';'));
        addEdge("e20", "e23", inputs(','));
        addEdge("e21", "e21", separator);
        addEdge("e21", "e22", inputs(';'));
        addEdge("e21", "e23", inputs(','));
        addEdge("e22", "e27", separator);
        addEdge("e22", "e29", inputs('}'));
        addEdge("e23", "e17", anything);
        addEdge("e23", "e19", inputs('@'));
        addEdge("e23", "e28", separator);
        addEdge("e24", "e24", separator);
        addEdge("e24", "e25", inputs(';'));
        addEdge("e24", "e26", inputs(','));
        addEdge("e25", "e10", alphanumeric);
        addEdge("e25", "e27", separator);
        addEdge("e25", "e29", inputs('}'));
        addEdge("e26", "e17", anything);
        addEdge("e26", "e19", inputs('@'));
        addEdge("e26", "e28", separator);
        addEdge("e27", "e10", alphanumeric);
        addEdge("e27", "e27", separator);
        addEdge("e27", "e29", inputs('}'));
        addEdge("e28", "e17", anything);
        addEdge("e28", "e19", inputs('@'));
        addEdge("e28", "e28", separator);
    }

    /**
     * Add all the required edges to link the initial node automaton used to
     * build and validate the format of the grammar file.
     */
    private void addEdgesForAutomatonInitialNode() {
        addEdge("i01", "i02", inputs('i', 'I'));
        addEdge("i02", "i03", inputs('n', 'N'));
        addEdge("i03", "i04", inputs('i', 'I'));
        addEdge("i04", "i05", inputs('t', 'T'));
        addEdge("i05", "i06", inputs('i', 'I'));
        addEdge("i06", "i07", inputs('a', 'A'));
        addEdge("i07", "i08", inputs('l', 'L'));
        addEdge("i08", "i09", separator);
        addEdge("i08", "i10", inputs('{'));
        addEdge("i09", "i09", separator);
        addEdge("i09", "i10", inputs('{'));
        addEdge("i10", "i11", separator);
        addEdge("i10", "i12", alphanumeric);
        addEdge("i11", "i11", separator);
        addEdge("i11", "i12", alphanumeric);
        addEdge("i12", "i12", alphanumeric);
        addEdge("i12", "i13", separator);
        addEdge("i12", "i14", inputs(';'));
        addEdge("i13", "i13", separator);
        addEdge("i13", "i14", inputs(';'));
        addEdge("i14", "i15", separator);
        addEdge("i14", "i16", inputs('}'));
        addEdge("i15", "i15", separator);
        addEdge("i15", "i16", inputs('}'));
        addEdge("i16", "i16", separator);
    }

    /**
     * Add all the required edges to link the comments automaton used to build
     * and validate the format of grammar file.
     */
    private void addEdgesForAutomatonComments() {
        addEdge("n01", "n01", separator);
        addEdge("n01", "c01", inputs('#'));
        addEdge("c01", "c01", anything);
        addEdge("c01", "n01", inputs('\n'));

        addEdge("n21", "e01", separator);
        addEdge("n21", "c02", inputs('#'));
        addEdge("e01", "e01", separator);
        addEdge("e01", "c02", inputs('#'));
        addEdge("c02", "c02", anything);
        addEdge("c02", "e01", inputs('\n'));

        addEdge("e29", "i01", separator);
        addEdge("e29", "c03", inputs('#'));
        addEdge("i01", "i01", separator);
        addEdge("i01", "c03", inputs('#'));
        addEdge("c03", "c03", anything);
        addEdge("c03", "i01", inputs('\n'));
    }

    /**
     * Builds the finite state machine associated to the grammar file.
     * 
     * @return retrieve the initial node of the finite state machine.
     */
    private Node<Character> buildFiniteStateMachine() {
        assignNodesForAutomatonNodes();
        addEdgesForAutomatonNodes();

        assignNodesForAutomatonEdges();
        addEdgesForAutomatonEdges();

        assignNodesForAutomatonInitialNode();
        addEdgesForAutomatonInitialNode();

        assignNodesForAutomatonComments();
        addEdgesForAutomatonComments();

        Automaton<Character> automaton = new Automaton<Character>();
        return automaton.define(nodes).link(edges).assign(nodes.get("n01"));
    }

    /**
     * Gets the initial node.
     * 
     * @return initial node.
     */
    public Node<Character> initialNode() {
        return node;
    }

}
