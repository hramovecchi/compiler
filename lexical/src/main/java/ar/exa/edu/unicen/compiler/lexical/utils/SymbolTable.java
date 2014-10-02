package ar.exa.edu.unicen.compiler.lexical.utils;

import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.info;

import java.util.HashMap;
import java.util.Map;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.Phase;

/**
 * Models the Symbol Table to group lexemes and tokens.
 * 
 * @author pmvillafane
 */
public class SymbolTable {

    private static SymbolTable symbolTable = null;

    private Map<String, Token> table = new HashMap<String, Token>();

    /**
     * Default constructor.
     */
    private SymbolTable() {

    }

    /**
     * Retrieves an instance of the Symbol Table.
     * 
     * @return the Symbol Table.
     */
    public static final SymbolTable getInstance() {

        if (symbolTable == null) {
            symbolTable = new SymbolTable();
        }
        return symbolTable;
    }

    /**
     * Adds an element to the symbol table.
     *
     * @param lexeme
     *            the lexeme to add.
     * @param token
     *            the token to add.
     * @param line
     *            line number where lexeme was found. <b>Note: </b> this value
     *            is used to print the content.
     */
    public void add(final String lexeme, final Token token, final int line) {
        table.put(lexeme, token);
        info(Phase.SYMBOL_TABLE, lexeme, token, line);
    }

}
