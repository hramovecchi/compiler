package ar.exa.edu.unicen.compiler.cg.asm;

import java.util.Map;

import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

public class MulInteger extends BaseOperation {

    public MulInteger(final Map<Object, String> variables) {
        super(variables);
    }

    @Override
    public String toAsm(final int index, final Triplet triplet) {

        final String op1 = this.formatOperand(triplet.getOperand1());
        final String op2 = this.formatOperand(triplet.getOperand2());
        final StringBuilder sb = new StringBuilder();

        sb.append(String.format("LABEL%d:\n", index));
        sb.append(String.format("\tMOV AX, %s\n", op1));
        sb.append("\tMOV DX, 0\n");
        sb.append(String.format("\tIMUL AX, %s\n", op2));
        sb.append(String.format("\tMOV @_dwaux%d, AX\n", index));

        return sb.toString();
    }

}
