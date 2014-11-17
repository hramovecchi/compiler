package ar.exa.edu.unicen.compiler.cg.asm;

import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

public class GoTo extends BaseOperation {

    @Override
    public String toAsm(final int index, final Triplet triplet) {

        final Integer op1 = (Integer) triplet.getOperand2();
        final StringBuilder sb = new StringBuilder();

        sb.append(String.format("LABEL%d:\n", index));
        sb.append(String.format("\tJMP LABEL%d\n", op1));

        return sb.toString();
    }

}
