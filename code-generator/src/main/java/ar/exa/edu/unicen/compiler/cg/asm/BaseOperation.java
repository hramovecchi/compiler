package ar.exa.edu.unicen.compiler.cg.asm;

import java.util.Map;

import ar.exa.edu.unicen.compiler.lexical.utils.SymbolTable;

public abstract class BaseOperation implements Operation {

    protected static final SymbolTable SYMBOL_TABLE = SymbolTable.getInstance();

    private final Map<String, String> variables;

    public BaseOperation(final Map<String, String> variables) {
        this.variables = variables;
    }

    protected String formatOperand(final Object operand) {
        return this.variables.get(operand.toString());
    }
}
