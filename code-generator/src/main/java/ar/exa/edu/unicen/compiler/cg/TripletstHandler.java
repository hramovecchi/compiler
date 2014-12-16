package ar.exa.edu.unicen.compiler.cg;

import java.util.HashMap;
import java.util.Map;

import ar.exa.edu.unicen.compiler.cg.asm.AddDouble;
import ar.exa.edu.unicen.compiler.cg.asm.AddInteger;
import ar.exa.edu.unicen.compiler.cg.asm.AssignDouble;
import ar.exa.edu.unicen.compiler.cg.asm.AssignInteger;
import ar.exa.edu.unicen.compiler.cg.asm.ComparatorDouble;
import ar.exa.edu.unicen.compiler.cg.asm.ComparatorInteger;
import ar.exa.edu.unicen.compiler.cg.asm.DivDouble;
import ar.exa.edu.unicen.compiler.cg.asm.DivInteger;
import ar.exa.edu.unicen.compiler.cg.asm.EqualTo;
import ar.exa.edu.unicen.compiler.cg.asm.GoTo;
import ar.exa.edu.unicen.compiler.cg.asm.GreaterThan;
import ar.exa.edu.unicen.compiler.cg.asm.GreaterThanOrEqualTo;
import ar.exa.edu.unicen.compiler.cg.asm.LessThan;
import ar.exa.edu.unicen.compiler.cg.asm.LessThanOrEqualTo;
import ar.exa.edu.unicen.compiler.cg.asm.MulDouble;
import ar.exa.edu.unicen.compiler.cg.asm.MulInteger;
import ar.exa.edu.unicen.compiler.cg.asm.NotEqualTo;
import ar.exa.edu.unicen.compiler.cg.asm.Operation;
import ar.exa.edu.unicen.compiler.cg.asm.PrintString;
import ar.exa.edu.unicen.compiler.cg.asm.SubDouble;
import ar.exa.edu.unicen.compiler.cg.asm.SubInteger;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;
import ar.exa.edu.unicen.compiler.syntactic.Branch;

public class TripletstHandler {

    private final Map<String, Operation> operations =
            new HashMap<String, Operation>();

    private Token lastComparator;

    private final Map<Object, String> variables;

    public TripletstHandler(final Map<Object, String> variables) {

        this.variables = variables;

        this.operations.put(Token.INTEGER.name() + Token.ADD.name(),
                new AddInteger(variables));
        this.operations.put(Token.DOUBLE.name() + Token.ADD.name(),
                new AddDouble(variables));
        this.operations.put(Token.INTEGER.name() + Token.SUB.name(),
                new SubInteger(variables));
        this.operations.put(Token.DOUBLE.name() + Token.SUB.name(),
                new SubDouble(variables));
        this.operations.put(Token.INTEGER.name() + Token.MUL.name(),
                new MulInteger(variables));
        this.operations.put(Token.DOUBLE.name() + Token.MUL.name(),
                new MulDouble(variables));
        this.operations.put(Token.INTEGER.name() + Token.DIV.name(),
                new DivInteger(variables));
        this.operations.put(Token.DOUBLE.name() + Token.DIV.name(),
                new DivDouble(variables));
        this.operations.put(Token.INTEGER.name() + Token.ASSIGN.name(),
                new AssignInteger(variables));
        this.operations.put(Token.DOUBLE.name() + Token.ASSIGN.name(),
                new AssignDouble(variables));

        this.operations.put(Token.INTEGER.name() + Token.EQ.name(),
                new ComparatorInteger(variables));
        this.operations.put(Token.DOUBLE.name() + Token.EQ.name(),
                new ComparatorDouble(variables));
        this.operations.put(Token.INTEGER.name() + Token.NE.name(),
                new ComparatorInteger(variables));
        this.operations.put(Token.DOUBLE.name() + Token.NE.name(),
                new ComparatorDouble(variables));
        this.operations.put(Token.INTEGER.name() + Token.LT.name(),
                new ComparatorInteger(variables));
        this.operations.put(Token.DOUBLE.name() + Token.LT.name(),
                new ComparatorDouble(variables));
        this.operations.put(Token.INTEGER.name() + Token.LE.name(),
                new ComparatorInteger(variables));
        this.operations.put(Token.DOUBLE.name() + Token.LE.name(),
                new ComparatorDouble(variables));
        this.operations.put(Token.INTEGER.name() + Token.GT.name(),
                new ComparatorInteger(variables));
        this.operations.put(Token.DOUBLE.name() + Token.GT.name(),
                new ComparatorDouble(variables));
        this.operations.put(Token.INTEGER.name() + Token.GE.name(),
                new ComparatorInteger(variables));
        this.operations.put(Token.DOUBLE.name() + Token.GE.name(),
                new ComparatorDouble(variables));

        this.operations.put(Branch.BI.name(), new GoTo(variables));
        this.operations.put(Branch.BF.name() + Token.EQ.name(), new EqualTo(
                variables));
        this.operations.put(Branch.BF.name() + Token.NE.name(), new NotEqualTo(
                variables));
        this.operations.put(Branch.BF.name() + Token.LT.name(), new LessThan(
                variables));
        this.operations.put(Branch.BF.name() + Token.LE.name(),
                new LessThanOrEqualTo(variables));
        this.operations.put(Branch.BF.name() + Token.GT.name(),
                new GreaterThan(variables));
        this.operations.put(Branch.BF.name() + Token.GE.name(),
                new GreaterThanOrEqualTo(variables));
        this.operations.put(Token.PRINT.name(), new PrintString(variables));
    }

    private String identifyOperatorType(final Object op) {

        if (op != null) {
            final String var = this.variables.get(op.toString());
            if (var != null) {
                if (var.startsWith("@_dw") || var.startsWith("@_cdw")) {
                    return Token.INTEGER.name();
                }
                return Token.DOUBLE.name();
            }
        }

        return null;
    }

    public String getCodeForTriplet(final int index, final Triplet triplet) {

        final Object operator = triplet.getOperator();

        if (operator instanceof Token) {

            final Token token = (Token) operator;
            if (Token.EQ.equals(token) || Token.NE.equals(token)
                    || Token.LT.equals(token) || Token.LE.equals(token)
                    || Token.GT.equals(token) || Token.GT.equals(token)) {
                this.lastComparator = token;
            }

            final String type1 =
                    this.identifyOperatorType(triplet.getOperand1());
            final String type2 =
                    this.identifyOperatorType(triplet.getOperand2());

            final String type;
            if (type1.equals(type2)) {

                if (Token.INTEGER.name().equals(type1)) {
                    type = Token.INTEGER.name();
                } else {
                    type = Token.DOUBLE.name();
                }

            } else {
                type = Token.DOUBLE.name();
            }

            Operation op = this.operations.get(token.name());
            if (op == null) {
                op = this.operations.get(type + token.name());
            }
            return op.toAsm(index, triplet);

        } else {

            final Branch branch = (Branch) operator;
            if (Branch.BF.equals(branch)) {

                final Operation op = this.operations.get(branch.name() //
                        + this.lastComparator.name());
                return op.toAsm(index, triplet);

            } else if (Branch.BI.equals(branch)) {

                final Operation op = this.operations.get(branch.name());
                return op.toAsm(index, triplet);

            }

        }

        return "";
    }

}
