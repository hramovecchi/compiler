package ar.exa.edu.unicen.compiler.lexical.analyzer;

/**
 * Models the pair <Lexeme, Token>.
 * 
 * @author pmvillafane
 * 
 */
public class Tuple {

    private final String lexeme;

    private final Token token;

    /**
     * Default constructor.
     * 
     * @param lexeme
     *            the lexeme.
     * @param token
     *            associated token.
     */
    public Tuple(final String lexeme, final Token token) {
        this.lexeme = lexeme;
        this.token = token;
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

}
