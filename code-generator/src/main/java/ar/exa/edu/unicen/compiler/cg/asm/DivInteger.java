package ar.exa.edu.unicen.compiler.cg.asm;

import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

public class DivInteger extends BaseOperation {

    @Override
    public String toAsm(final int index, final Triplet triplet) {

        final String op1 = this.formatOperand(triplet.getOperand1());
        final String op2 = this.formatOperand(triplet.getOperand2());
        final StringBuilder sb = new StringBuilder();

        sb.append(String.format("LABEL%d:\n", index));
        sb.append(String.format("\tMOV ax, [%s]\n", op1));
        sb.append("\tMOV edx , 0\n");
        sb.append(String.format("\tMOV bx , [%s]\n", op2));
        sb.append("\tIDIV bx\n");
        sb.append(String.format("\tMOV [@_aux%d], ax\n", index));

        return sb.toString();
    }

}
