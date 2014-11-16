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

    private final Map<String, Element> table = new HashMap<String, Element>();

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
        this.table.put(lexeme, new Element(token));
        info(Phase.SYMBOL_TABLE, lexeme, token, line);
    }

    /**
     * Assigns the type.
     *
     * @param lexeme
     *            the lexeme to add.
     * @param type
     *            the type corresponding to the lexeme.
     * @param line
     *            line number where lexeme was found. <b>Note: </b> this value
     *            is used to print the content.
     */
    public void assignType(final String lexeme, final String type,
            final int line) {
        final Element symbolData = this.table.get(lexeme);
        symbolData.setType(type);
        info(Phase.SYMBOL_TABLE, lexeme, symbolData.getToken(), type, line);
    }
    
	public boolean isVariableInteger(final String lexeme) {
		final Element element = this.table.get(lexeme); 
		return Token.ID.equals(element.getToken()) && Token.INTEGER.name().equals(element.getType());
	}

	public boolean isVariableDouble(final String lexeme) {
		final Element element = this.table.get(lexeme); 
		return Token.ID.equals(element.getToken()) && Token.DOUBLE.name().equals(element.getType());
	}

	public boolean isConstInteger(final String lexeme) {
		final Element element = this.table.get(lexeme); 
		return Token.CONST_INTEGER.equals(element.getToken());
	}

	public boolean isConstDouble(final String lexeme) {
		final Element element = this.table.get(lexeme); 
		return Token.CONST_DOUBLE.equals(element.getToken());
	}

	public boolean isConstString(final String lexeme) {
		final Element element = this.table.get(lexeme); 
		return Token.STRING.equals(element.getToken());
	}

}
