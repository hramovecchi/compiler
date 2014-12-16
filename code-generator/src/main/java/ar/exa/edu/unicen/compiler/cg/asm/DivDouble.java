package ar.exa.edu.unicen.compiler.cg.asm;

import java.util.Map;

import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

public class DivDouble extends BaseOperation {

    public DivDouble(final Map<Object, String> variables) {
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

        sb.append(String.format("\tFLD %s\n", op2));
        sb.append("\tFTST\n");
        sb.append("\tXOR BX, BX\n"); // set eax to 0
        sb.append("\tFSTSW AX \n");// paso los valores del copro al proc
        sb.append("\tSAHF \n");// cargo los valores
        sb.append("\tJE _division_cero\n");
        sb.append(String.format("\tFLD %s\n", op1));
        sb.append("\tFDIVR \n"); // o "FDIV ST, ST(1)\n";
        sb.append(String.format("\tFSTP @_aux%d\n", index));

        return sb.toString();
    }

}
