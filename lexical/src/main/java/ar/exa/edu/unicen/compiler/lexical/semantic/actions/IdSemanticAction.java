package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.warn;

import java.util.List;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;
import ar.exa.edu.unicen.compiler.lexical.utils.SymbolTable;
import ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.Phase;

/**
 * Truncates an identifier if it has size bigger than 12.
 * 
 * @author pmvillafane
 */
public class IdSemanticAction implements SemanticAction {

    private final SymbolTable symbolTable = SymbolTable.getInstance();

    @Override
    public void doAction(final String lexeme, final List<Tuple> tuples,
            final Token token, final int line) {

        if (lexeme.length() > 12) {

            final String truncated = lexeme.substring(0, 12);

            final String warn =
                    String.format("Identificador truncado [%s]", truncated);
            warn(Phase.LEXICAL, lexeme, token, line, warn);

            tuples.add(new Tuple(truncated, token, line));
            symbolTable.add(truncated, token, line);
            return;
        }

        tuples.add(new Tuple(lexeme, token, line));
        symbolTable.add(lexeme, token, line);
    }

}
