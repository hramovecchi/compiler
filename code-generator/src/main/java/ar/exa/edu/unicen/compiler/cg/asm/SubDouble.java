package ar.exa.edu.unicen.compiler.cg.asm;

import java.util.Map;

import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

public class SubDouble extends BaseOperation {

    public SubDouble(final Map<Object, String> variables) {
        super(variables);
    }

    @Override
    public String toAsm(final int index, final Triplet triplet) {

        final StringBuilder sb = new StringBuilder();

        sb.append(String.format("LABEL%d:\n", index));

        final String op1Formatted = this.formatOperand(triplet.getOperand1());
        final String op1 = this.convertIntegerToDouble(sb, op1Formatted);
        final String op2Formatted = this.formatOperand(triplet.getOperand2());
        final String op2 = this.convertIntegerToDouble(sb, op2Formatted);

        sb.append(String.format("\tFLD %s\n", op1));
        sb.append(String.format("\tFSUB %s\n", op2));
        sb.append(String.format("\tFSTP @_ddaux%d\n", index));

        return sb.toString();
    }

}
