package ar.exa.edu.unicen.compiler.lexical.analyzer;

import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.info;
import ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.Phase;

/**
 * Models the pair <Lexeme, Token>.
 * 
 * @author pmvillafane
 * 
 */
public class Tuple {

    private final String lexeme;

    private final Token token;

    private final int line;

    /**
     * Default constructor.
     * 
     * @param lexeme
     *            the lexeme.
     * @param token
     *            associated token.
     * @param line
     *            line number.
     */
    public Tuple(final String lexeme, final Token token, final int line) {

        this.lexeme = lexeme;
        this.token = token;
        this.line = line;

        info(Phase.LEXICAL, lexeme, token, line);
    }

    /**
     * Gets the lexeme.
     * 
     * @return the lexeme.
     */
    public String getLexeme() {
        return lexeme;
    }

    /**
     * Gets the token.
     * 
     * @return the token.
     */
    public Token getToken() {
        return token;
    }

    /**
     * Gets the line number.
     * 
     * @return the line number.
     */
    public int getLine() {
        return line;
    }

}
