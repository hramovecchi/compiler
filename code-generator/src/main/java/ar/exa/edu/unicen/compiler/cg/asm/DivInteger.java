package ar.exa.edu.unicen.compiler.cg.asm;

import java.util.Map;

import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

public class DivInteger extends BaseOperation {

    public DivInteger(final Map<Object, String> variables) {
        super(variables);
    }

    @Override
    public String toAsm(final int index, final Triplet triplet) {

        final String op1 = this.formatOperand(triplet.getOperand1());
        final String op2 = this.formatOperand(triplet.getOperand2());
        final StringBuilder sb = new StringBuilder();

        sb.append(String.format("LABEL%d:\n", index));
        sb.append("\tXOR BX, BX\n"); // set eax to 0
        sb.append(String.format("\tADD BX, %s\n", op2));
        sb.append("\tJZ _division_cero\n");
        // Cargo la parte alta con cero, porque todos
        sb.append("\tMOV DX, 0\n");
        // los enteros son de 16 bits no más
        sb.append(String.format("\tMOV AX, %s\n", op1));
        sb.append("\tCWD\n");// copia el signo a DX
        sb.append(String.format("\tIDIV %s\n", op2));
        sb.append(String.format("\tMOV @_dwaux%d, AX\n", index));

        return sb.toString();
    }

}
