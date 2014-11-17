package ar.exa.edu.unicen.compiler.cg;

import java.util.List;

import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

public class CodeGenerator {

    private final TripletstHandler tripletHandler;

    public CodeGenerator() {
        this.tripletHandler = new TripletstHandler();
    }

    public String getAssemblerCode(final int index, final Triplet triplet) {
        return this.tripletHandler.getCodeForTriplet(index, triplet);
    }

    public String getAssemblerCode(final List<Triplet> triplets) {

        final StringBuilder asm = new StringBuilder();

        final String asmHeader = this.loadProgramHeader();
        asm.append(asmHeader);

        for (int i = 0; i < triplets.size(); i++) {

            final String asmBody = this.getAssemblerCode(i, triplets.get(i));
            asm.append(asmBody);
        }

        final String asmFooter = this.loadProgramFooter(triplets.size());
        asm.append(asmFooter);

        return asm.toString();
    }

    private String loadProgramHeader() {
        return "";
    }

    private String loadProgramFooter(final int size) {
        return String.format("LABEL%d:", size);
    }

}
