package ar.exa.edu.unicen.compiler.cg.asm;

import java.util.Map;

import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

public class AddDouble extends BaseOperation {

    public AddDouble(final Map<Object, String> variables) {
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
        sb.append(String.format("\tFADD %s\n", op2));
        sb.append(String.format("\tFSTP @_ddaux%d\n", index));
        sb.append("\tFABS \n");
        sb.append("\tFCOM __MAX\n");
        sb.append("\tFSTSW AX \n");
        sb.append("\tSAHF \n");
        sb.append("\tJA _overflow_suma \n");
        sb.append("\tFCOM __MIN\n");
        sb.append("\tFSTSW AX\n");
        sb.append("\tSAHF\n");
        sb.append("\tJB _overflow_suma\n");

        return sb.toString();
    }

}
