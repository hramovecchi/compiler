package ar.exa.edu.unicen.compiler.cg.asm;

import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

public class Comparator extends BaseOperation {

    @Override
    public String toAsm(final int index, final Triplet triplet) {

        final String op1 = this.formatOperand(triplet.getOperand1());
        final Integer op2 = (Integer) triplet.getOperand2();
        final StringBuilder sb = new StringBuilder();

        sb.append(String.format("LABEL%d:\n", index));
        sb.append("\tFCOMPP\n");
        sb.append(String.format("\tFLD dword[%s]\n", op2));
        sb.append(String.format("\tFCOMP dword[%s]\n", op1));
        sb.append("\tFSTSW ax\n");
        sb.append("\tSAHF\n");

        return sb.toString();
    }

}
