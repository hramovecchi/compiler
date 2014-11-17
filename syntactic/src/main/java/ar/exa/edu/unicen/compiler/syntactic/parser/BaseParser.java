package ar.exa.edu.unicen.compiler.syntactic.parser;

import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.errorWithoutException;
import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.info;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import ar.exa.edu.unicen.compiler.lexical.Lexical;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;
import ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.Phase;
import ar.exa.edu.unicen.compiler.lexical.utils.SymbolTable;
import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;
import ar.exa.edu.unicen.compiler.syntactic.Branch;

/**
 * Base parser class in charge of defines the non-implemented methods generated
 * by YACC.
 *
 * @author pmvillafane
 */
public abstract class BaseParser {

    private final Iterator<Tuple> tuplesIt;

    private Tuple currentTuple;

    private Token lastType = null;

    private static final SymbolTable SYMBOL_TABLE = SymbolTable.getInstance();

    private final List<Triplet> triplets = new ArrayList<Triplet>();

    private final Stack<Object> stack = new Stack<Object>();

    /**
     * Default handler.
     *
     * @param lexical
     *            reference to the lexical analyzer.
     * @param sourceCode
     *            source code to evaluate.
     * @throws IOException
     *             throws an exception in case of error reading the source code
     *             file.
     */
    public BaseParser(final Lexical lexical, final InputStream sourceCode)
            throws IOException {
        final List<Tuple> tuples = lexical.run(sourceCode);
        this.tuplesIt = tuples.listIterator();
    }

    /**
     * Transforms a string to double,
     *
     * @param value
     *            string representation of a double number.
     * @return the double number formatted as {@link String}.
     */
    private static String stringToDouble(final String value) {

        final String[] pair = value.split("[bB]");
        double result;
        if (pair.length == 1) {
            result = Double.parseDouble(pair[0]);
        } else {
            result =
                    Double.parseDouble(pair[0])
                            * Math.pow(10D, Double.parseDouble(pair[1]));
        }

        return String.valueOf(result);
    }

    public List<Triplet> getTriplets() {
        return this.triplets;
    }

    public int yylex() {
        if (this.tuplesIt.hasNext()) {
            this.currentTuple = this.tuplesIt.next();

            final Token token = this.currentTuple.getToken();

            switch (token) {
                case CONST_DOUBLE:
                    final String doubleVal =
                            stringToDouble(this.currentTuple.getLexeme());
                    this.stack.push(doubleVal);
                    break;
                case CONST_INTEGER:
                case ID:
                case STRING:
                    this.stack.push(this.currentTuple.getLexeme());
                    break;
                default:
                    break;
            }

            if (Token.INTEGER.equals(token) //
                    || Token.DOUBLE.equals(token) //
                    || Token.VECTOR.equals(token)) {

                this.lastType = token;

            } else if (this.lastType != null && Token.ID.equals(token)) {
                SYMBOL_TABLE.assignType(this.currentTuple.getLexeme(),
                        this.lastType.name(), this.currentTuple.getLine());

                this.lastType = null;
            }

            final int tokenId = token.getId();
            return tokenId;
        }
        return -1;
    }

    /**
     * Reports a syntax message with INFO priority level.
     *
     * @param info
     *            the message to report.
     */
    protected void yyinfo(final String info) {
        info(Phase.SYNTACTIC, this.currentTuple.getLine(), info);
    }

    /**
     * Reports a syntax error with ERROR priority level.
     *
     * @param err
     *            the error to report.
     */
    protected void yyerror(final String err) {
        errorWithoutException(Phase.SYNTACTIC, this.currentTuple.getLine(), err);
    }

    protected void idAt() {

        final Object operand2 = this.stack.pop();
        final Object operand1 = this.stack.pop();
        final Triplet triplet = new Triplet(Token.ID, operand1, operand2);
        this.stack.push(this.triplets.size());
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());
    }

    protected void ifCondition() {

        final Object operand = this.stack.pop();
        if (operand instanceof Triplet) {

            final Triplet triplet = (Triplet) operand;
            triplet.setOperand2(this.triplets.size());

            info(this.triplets.size() - 1, triplet.getOperator().toString(),
                    triplet.getOperand1(), triplet.getOperand2());
        } else {

            final Triplet triplet = (Triplet) this.stack.pop();
            triplet.setOperand2(this.triplets.size());
            this.stack.push(operand);

            info(this.triplets.size() - 1, triplet.getOperator().toString(),
                    triplet.getOperand1(), triplet.getOperand2());
        }
    }

    protected void ifElseCondition() {

        final Triplet triplet = (Triplet) this.stack.pop();
        triplet.setOperand2(this.triplets.size() - 1);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());
    }

    protected void forCondition() {

        Triplet triplet = (Triplet) this.stack.pop();
        triplet.setOperand2(this.triplets.size() + 1);

        triplet = new Triplet(Branch.BI, triplet.getOperand1(), null);
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());
    }

    protected void print() {

        final Object operand1 = this.stack.pop();
        final Triplet triplet = new Triplet(Token.PRINT, operand1, null);
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());
    }

    protected void vector() {

        Object operand2 = this.stack.pop();
        Object operand1 = this.stack.pop();
        Triplet triplet = new Triplet(Token.RANGE, operand1, operand2);
        this.stack.push(this.triplets.size());
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());

        operand2 = this.stack.pop();
        operand1 = this.stack.pop();
        triplet = new Triplet(Token.VECTOR, operand1, operand2);
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());
    }

    protected void assignToId() {

        final Object operand2 = this.stack.pop();
        final Object operand1 = this.stack.pop();
        final Triplet triplet = new Triplet(Token.ASSIGN, operand1, operand2);
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());
    }

    protected void assignToVector() {

        final Object operand3 = this.stack.pop();
        final Object operand2 = this.stack.pop();
        final Object operand1 = this.stack.pop();
        Triplet triplet = new Triplet(Token.ID, operand1, operand2);
        this.stack.push(this.triplets.size());
        this.triplets.add(triplet);

        final Object operand4 = this.stack.pop();
        triplet = new Triplet(Token.ID, operand4, operand3);
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());
    }

    private void branchFail() {

        final Object operand1 = this.stack.pop();
        final Triplet triplet = new Triplet(Branch.BF, operand1, null);
        this.stack.push(triplet);
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());
    }

    protected void eq() {

        final Object operand2 = this.stack.pop();
        final Object operand1 = this.stack.pop();
        final Triplet triplet = new Triplet(Token.EQ, operand1, operand2);
        this.stack.push(this.triplets.size());
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());

        this.branchFail();
    }

    protected void ne() {

        final Object operand2 = this.stack.pop();
        final Object operand1 = this.stack.pop();
        final Triplet triplet = new Triplet(Token.NE, operand1, operand2);
        this.stack.push(this.triplets.size());
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());

        this.branchFail();
    }

    protected void lt() {

        final Object operand2 = this.stack.pop();
        final Object operand1 = this.stack.pop();
        final Triplet triplet = new Triplet(Token.LT, operand1, operand2);
        this.stack.push(this.triplets.size());
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());

        this.branchFail();
    }

    protected void gt() {

        final Object operand2 = this.stack.pop();
        final Object operand1 = this.stack.pop();
        final Triplet triplet = new Triplet(Token.GT, operand1, operand2);
        this.stack.push(this.triplets.size());
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());

        this.branchFail();
    }

    protected void le() {

        final Object operand2 = this.stack.pop();
        final Object operand1 = this.stack.pop();
        final Triplet triplet = new Triplet(Token.LE, operand1, operand2);
        this.stack.push(this.triplets.size());
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());

        this.branchFail();
    }

    protected void ge() {

        final Object operand2 = this.stack.pop();
        final Object operand1 = this.stack.pop();
        final Triplet triplet = new Triplet(Token.GE, operand1, operand2);
        this.stack.push(this.triplets.size());
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());

        this.branchFail();
    }

    protected void add() {

        final Object operand2 = this.stack.pop();
        final Object operand1 = this.stack.pop();
        final Triplet triplet = new Triplet(Token.ADD, operand1, operand2);
        this.stack.push(this.triplets.size());
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());
    }

    protected void sub() {

        final Object operand2 = this.stack.pop();
        final Object operand1 = this.stack.pop();
        final Triplet triplet = new Triplet(Token.SUB, operand1, operand2);
        this.stack.push(this.triplets.size());
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());
    }

    protected void mul() {

        final Object operand2 = this.stack.pop();
        final Object operand1 = this.stack.pop();
        final Triplet triplet = new Triplet(Token.MUL, operand1, operand2);
        this.stack.push(this.triplets.size());
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());
    }

    protected void div() {

        final Object operand2 = this.stack.pop();
        final Object operand1 = this.stack.pop();
        final Triplet triplet = new Triplet(Token.DIV, operand1, operand2);
        this.stack.push(this.triplets.size());
        this.triplets.add(triplet);

        info(this.triplets.size() - 1, triplet.getOperator().toString(),
                triplet.getOperand1(), triplet.getOperand2());
    }

}
