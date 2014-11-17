package ar.exa.edu.unicen.compiler.cg.asm;

import ar.exa.edu.unicen.compiler.lexical.utils.SymbolTable;

public abstract class BaseOperation implements Operation {

    protected static final SymbolTable SYMBOL_TABLE = SymbolTable.getInstance();

    private static final String AUX_VAR = "@_aux";

    protected String formatOperand(final Object operand) {

        if (operand instanceof Integer) {
            return AUX_VAR + operand;
        } else if (SYMBOL_TABLE.isVariableInteger(operand.toString())) {// isnumeric
            return "_" + operand;
        } else if (SYMBOL_TABLE.isVariableDouble(operand.toString())) {// isnumeric
            return "_" + operand;
        } else if (SYMBOL_TABLE.isConstInteger(operand.toString())) {// isnumeric
            return operand.toString();
        } else if (SYMBOL_TABLE.isConstDouble(operand.toString())) {// isnumeric
            return operand.toString();
        }

        // String constant used to print messages by console.
        return operand.toString();
    }

}
