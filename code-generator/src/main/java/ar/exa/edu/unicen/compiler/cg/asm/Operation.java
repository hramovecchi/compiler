package ar.exa.edu.unicen.compiler.cg.asm;

import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

public interface Operation {

    String toAsm(int index, Triplet triplet);

}
