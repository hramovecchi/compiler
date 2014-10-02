package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.debug;

import java.util.List;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;
import ar.exa.edu.unicen.compiler.lexical.utils.SymbolTable;
import ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.Phase;

/**
 * Verifies if the range operator is OK.
 *
 * @author pmvillafane
 */
public class RangeSemanticAction implements SemanticAction {

    private final SymbolTable symbolTable = SymbolTable.getInstance();

    @Override
    public void doAction(final String lexeme, final List<Tuple> tuples,
            final Token token, final int line) {

        String[] number = lexeme.split("\\..");
        if (number.length == 1) {

            final String debug = String.format("Entero [%s] detectado", number[0]);
            debug(Phase.LEXICAL, lexeme, token, line, debug);

            tuples.add(new Tuple(number[0], Token.CONST_INTEGER, line));
            symbolTable.add(number[0], Token.CONST_INTEGER, line);
        }

        tuples.add(new Tuple("..", token, line));
        symbolTable.add("..", token, line);
    }

}
