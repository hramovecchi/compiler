package ar.exa.edu.unicen.compiler.cg.asm;

import java.util.Map;

import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

public class PrintString extends BaseOperation {

    public PrintString(final Map<Object, String> variables) {
        super(variables);
    }

    @Override
    public String toAsm(final int index, final Triplet triplet) {

        final String op1 = this.formatOperand(triplet.getOperand1());
        final StringBuilder sb = new StringBuilder();

        sb.append(String.format("LABEL%d:\n", index));
        sb.append(String.format(
                "\tinvoke MessageBox, NULL, addr %s, addr __MSG, MB_OK\n", op1));

        return sb.toString();
    }

}
