package ar.exa.edu.unicen.compiler.cg.asm;

import java.util.Map;

import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

public class GoTo extends BaseOperation {

    public GoTo(final Map<String, String> variables) {
        super(variables);
    }

    @Override
    public String toAsm(final int index, final Triplet triplet) {

        final Integer op1 = (Integer) triplet.getOperand1();
        final StringBuilder sb = new StringBuilder();

        sb.append(String.format("LABEL%d:\n", index));
        sb.append(String.format("\tJMP LABEL%d\n", op1));

        return sb.toString();
    }

}
