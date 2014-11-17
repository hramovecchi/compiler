package ar.exa.edu.unicen.compiler.cg;

import java.util.HashMap;
import java.util.Map;

import ar.exa.edu.unicen.compiler.cg.asm.AddDouble;
import ar.exa.edu.unicen.compiler.cg.asm.AssignDouble;
import ar.exa.edu.unicen.compiler.cg.asm.Comparator;
import ar.exa.edu.unicen.compiler.cg.asm.DivDouble;
import ar.exa.edu.unicen.compiler.cg.asm.EqualTo;
import ar.exa.edu.unicen.compiler.cg.asm.GoTo;
import ar.exa.edu.unicen.compiler.cg.asm.GreaterThan;
import ar.exa.edu.unicen.compiler.cg.asm.GreaterThanOrEqualTo;
import ar.exa.edu.unicen.compiler.cg.asm.LessThan;
import ar.exa.edu.unicen.compiler.cg.asm.LessThanOrEqualTo;
import ar.exa.edu.unicen.compiler.cg.asm.MulDouble;
import ar.exa.edu.unicen.compiler.cg.asm.NotEqualTo;
import ar.exa.edu.unicen.compiler.cg.asm.Operation;
import ar.exa.edu.unicen.compiler.cg.asm.Print;
import ar.exa.edu.unicen.compiler.cg.asm.SubDouble;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;
import ar.exa.edu.unicen.compiler.syntactic.Branch;

public class TripletstHandler {

    private final Map<String, Operation> operations =
            new HashMap<String, Operation>();

    private Token lastComparator;

    public TripletstHandler() {
        this.operations.put(Token.ADD.name(), new AddDouble());
        this.operations.put(Token.SUB.name(), new SubDouble());
        this.operations.put(Token.MUL.name(), new MulDouble());
        this.operations.put(Token.DIV.name(), new DivDouble());
        this.operations.put(Token.ASSIGN.name(), new AssignDouble());
        this.operations.put(Token.PRINT.name(), new Print());
        this.operations.put(Token.EQ.name(), new Comparator());
        this.operations.put(Token.NE.name(), new Comparator());
        this.operations.put(Token.LT.name(), new Comparator());
        this.operations.put(Token.LE.name(), new Comparator());
        this.operations.put(Token.GT.name(), new Comparator());
        this.operations.put(Token.GE.name(), new Comparator());
        this.operations.put(Branch.BI.name(), new GoTo());
        this.operations.put(Branch.BF.name() + Token.EQ.name(), new EqualTo());
        this.operations.put(Branch.BF.name() + Token.NE.name(),
                new NotEqualTo());
        this.operations.put(Branch.BF.name() + Token.LT.name(), new LessThan());
        this.operations.put(Branch.BF.name() + Token.LE.name(),
                new LessThanOrEqualTo());
        this.operations.put(Branch.BF.name() + Token.GT.name(),
                new GreaterThan());
        this.operations.put(Branch.BF.name() + Token.GE.name(),
                new GreaterThanOrEqualTo());
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

            final Operation op = this.operations.get(token.name());
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
