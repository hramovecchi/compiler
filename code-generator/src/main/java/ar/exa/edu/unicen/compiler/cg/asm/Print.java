package ar.exa.edu.unicen.compiler.cg.asm;

import java.util.Map;

import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

public class Print extends BaseOperation {

    public Print(final Map<String, String> variables) {
        super(variables);
    }

    @Override
    public String toAsm(final int index, final Triplet triplet) {

        final String op1 = this.formatOperand(triplet.getOperand1());
        final StringBuilder sb = new StringBuilder();

        sb.append(String.format("LABEL%d:\n", index));
        sb.append("\tMOV eax, 4\n");
        sb.append("\tMOV ebx, 1\n");
        sb.append(String.format("\tMOV ecx, %s\n", op1));
        sb.append(String.format("\tMOV edx, %s@\n", op1));
        sb.append("\tINT 80h\n");

        return sb.toString();
    }

}
