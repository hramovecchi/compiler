package ar.exa.edu.unicen.compiler.cg.asm;

import java.util.Map;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.utils.SymbolTable;

public abstract class BaseOperation implements Operation {

    protected static final SymbolTable SYMBOL_TABLE = SymbolTable.getInstance();

    private final Map<Object, String> variables;

    public BaseOperation(final Map<Object, String> variables) {
        this.variables = variables;
    }

    protected String formatOperand(final Object operand) {
        return this.variables.get(operand);
    }

    private String identifyOperatorType(final String op) {

        if (op != null) {
            if (op.startsWith("@_dw") || op.startsWith("@_cdw")) {
                return Token.INTEGER.name();
            }
            return Token.DOUBLE.name();
        }
        return Token.INTEGER.name();
    }

    protected String convertIntegerToDouble(final StringBuilder sb,
            final String op) {
        final String type = this.identifyOperatorType(op);

        if (Token.INTEGER.name().equals(type)) {

            final String intOpToDouble = "__convAux";

            sb.append(String.format("\tFILD %s\n", op));
            sb.append(String.format("\tFSTP %s\n", intOpToDouble));

            return intOpToDouble;
        }

        return op;
    }

}
