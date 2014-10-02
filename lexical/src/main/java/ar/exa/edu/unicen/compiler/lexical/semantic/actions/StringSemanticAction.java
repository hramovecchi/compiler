package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.debug;

import java.util.List;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;
import ar.exa.edu.unicen.compiler.lexical.utils.SymbolTable;
import ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.Phase;

/**
 * Fixes a string representation: <br />
 * <br />
 * - Removes newline characters like \r, \n. - Removes the character '+' in case
 * of multiline.
 *
 * @author pmvillafane
 */
public class StringSemanticAction implements SemanticAction {

    private final SymbolTable symbolTable = SymbolTable.getInstance();

    @Override
    public void doAction(final String lexeme, final List<Tuple> tuples,
            final Token token, final int line) {

        final StringBuilder fixedString = new StringBuilder();
        final String[] strFragments = lexeme.replace("\r", "").split("\n");
        for (final String value : strFragments) {
            if (!value.startsWith("'")) {
                fixedString.append(value.trim().replaceFirst("\\+", ""));
            } else {
                fixedString.append(value);
            }
        }

        final String str = fixedString.toString();
        final String debug =
                String.format("Cadena de caracteres %s unificada", str);
        debug(Phase.LEXICAL, lexeme, token, line, debug);

        tuples.add(new Tuple(str, token, line));
        symbolTable.add(str, token, line);
    }

}
