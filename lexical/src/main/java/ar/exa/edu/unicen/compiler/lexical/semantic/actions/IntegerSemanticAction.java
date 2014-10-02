package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.error;

import java.util.List;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;
import ar.exa.edu.unicen.compiler.lexical.utils.SymbolTable;
import ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.Phase;

/**
 * Verifies integer ranges.
 * 
 * @author pmvillafane
 */
public class IntegerSemanticAction implements SemanticAction {

    private static final int minInteger = -32768;

    private static final int maxInteger = 32767;

    private final SymbolTable symbolTable = SymbolTable.getInstance();

    @Override
    public void doAction(final String lexeme, final List<Tuple> tuples,
            final Token token, final int line) {

        int value = Integer.valueOf(lexeme);

        if (minInteger < value && value < maxInteger) {
            tuples.add(new Tuple(lexeme, token, line));
            symbolTable.add(lexeme, token, line);
            return;
        }

        final String err = "Rango inválido. Rango debe ser -2^15 < x < 2^15-1";
        error(Phase.LEXICAL, lexeme, token, line, err);
    }

}
