package ar.exa.edu.unicen.compiler.lexical.analyzer;

import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.error;
import ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.Phase;

/**
 * Constant class that defines all the possible token recognizable by the
 * grammar.
 * 
 * @author pmvillafane
 */
public enum Token {

    // Keywords.
    IF("si", "Palabra Reservada", 100, Category.TEXT),
    THEN("entonces", "Palabra Reservada", 101, Category.TEXT),
    ELSE("sino", "Palabra Reservada", 102, Category.TEXT),
    FOR("para", "Palabra Reservada", 103, Category.TEXT),
    VECTOR("vector", "Palabra Reservada", 104, Category.TEXT),
    OF("de", "Palabra Reservada", 105, Category.TEXT),
    PRINT("imprimir", "Palabra Reservada", 106, Category.TEXT),
    INTEGER("entero", "Palabra Reservada", 107, Category.TEXT),
    DOUBLE("doble", "Palabra Reservada", 108, Category.TEXT),

    // Identifiers.
    ID("[a-zA-Z&$_]+", "Identificador", 200, Category.TEXT),
    STRING("'[\\s\\S]+'", "Cadena de Caracteres", 201, Category.TEXT),

    // Constants.
    CONST_INTEGER("(-?[0-9]+)", "Constante entera", 300, Category.CONSTANT),
    CONST_DOUBLE("((-?[1-9][0-9]*\\.?[0-9]*)|(\\.[0-9]+))([Bb][+-]?[0-9]+)?", "Constante doble", 301, Category.CONSTANT),

    // Arithmetic operators.
    ADD("\\+", "Operador Aritmético", 400, Category.OPERATOR),
    SUB("-", "Operador Aritmético", 401, Category.OPERATOR),
    MUL("\\*", "Operador Aritmético", 402, Category.OPERATOR),
    DIV("\\/", "Operador Aritmético", 403, Category.OPERATOR),

    // Assignment operators.
    ASSIGN(":=", "Operador de Asignación", 500, Category.OPERATOR),

    // Comparators.
    EQ("=", "Comparador", 600, Category.OPERATOR),
    NE("\\^=", "Comparador", 601, Category.OPERATOR),
    LT("<", "Comparador", 602, Category.OPERATOR),
    GT(">", "Comparador", 603, Category.OPERATOR),
    LE("<=", "Comparador", 604, Category.OPERATOR),
    GE(">=", "Comparador", 605, Category.OPERATOR),

    // Expression operators.
    LPAREN("\\(", "Sentencia de Bloque", 700, Category.OPERATOR),
    RPAREN("\\)", "Sentencia de Bloque", 701, Category.OPERATOR),
    LBRACK("\\[", "Sentencia de Bloque", 702, Category.OPERATOR),
    RBRACK("\\]", "Sentencia de Bloque", 703, Category.OPERATOR),
    LBRACE("\\{", "Sentencia de Bloque", 704, Category.OPERATOR),
    RBRACE("\\}", "Sentencia de Bloque", 705, Category.OPERATOR),
    COMMA("\\,", "Separador", 706, Category.OPERATOR),
    SEMICOLON("\\;", "Fin de Sentencia", 707, Category.OPERATOR),
    RANGE("-?[0-9]*[\\.]{2}-?[0-9]*", "Operador de Rango de Vectores", 708, Category.OPERATOR);

    private final String type;

    private final String description;

    private final int id;

    private final Category category;

    /**
     * Default constructor.
     * 
     * @param type
     *            token type.
     * @param description
     *            description that refers the token.
     * @param id
     *            an integer unique number that represents the token.
     * @param category
     *            each token is associated to a set of categories like
     *            operations, keywords, constants, etc.
     */
    private Token(final String type, final String description, final int id,
            final Category category) {
        this.type = type;
        this.description = description;
        this.id = id;
        this.category = category;
    }

    /**
     * Gets the token type.
     * 
     * @return token type.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the description that refers the token.
     * 
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets an integer unique number that represents the token.
     *
     * @return token identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the category associated to the token.
     *
     * @return each token is associated to a set of categories like operations,
     *         keywords, constants, etc.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Finds a token according to the given category and lexeme.
     * 
     * @param category
     *            the category assigned to the token.
     * @param lexeme
     *            the lexeme to evaluate.
     * @param line
     *            line number.
     * @return the token. This value cannot be <b>null</b>.
     */
    public static Token findToken(final Category category, final String lexeme,
            final int line) {

        for (Token token : Token.values()) {
            if (token.getCategory().equals(category)
                    && lexeme.matches(token.getType())) {
                return token;
            }
        }

        error(Phase.LEXICAL, line, String.format("Token %s no válido", lexeme));
        return null;
    }

}
