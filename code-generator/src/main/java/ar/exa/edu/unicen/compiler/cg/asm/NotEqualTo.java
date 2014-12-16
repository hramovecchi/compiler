package ar.exa.edu.unicen.compiler.cg.asm;

import java.util.Map;

import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

public class NotEqualTo extends BaseOperation {

    public NotEqualTo(final Map<Object, String> variables) {
        super(variables);
    }

    @Override
    public String toAsm(final int index, final Triplet triplet) {

        final Integer op2 = (Integer) triplet.getOperand2();
        final StringBuilder sb = new StringBuilder();

        sb.append(String.format("LABEL%d:\n", index));
        sb.append(String.format("\tJE LABEL%d\n", op2));

        return sb.toString();
    }

}
