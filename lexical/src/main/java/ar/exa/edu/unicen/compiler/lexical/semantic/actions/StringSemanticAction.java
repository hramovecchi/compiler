package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;

/**
 * Fixes a string representation: <br />
 * <br />
 * - Removes newline characters like \r, \n.
 * - Removes the character '+' in case of multiline.
 *
 * @author pmvillafane
 */
public class StringSemanticAction implements SemanticAction {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(StringSemanticAction.class);

    @Override
    public void doAction(final String lexeme, final List<Tuple> tuples,
            final Token token) {

        final StringBuilder fixedString = new StringBuilder();
        final String[] strFragments = lexeme.replace("\r", "").split("\n");
        for (String str : strFragments) {
            if (!str.startsWith("'")) {
                fixedString.append(str.trim().replaceFirst("\\+", ""));
            } else {
                fixedString.append(str);
            }
        }

        LOGGER.debug("Unificando string {}", fixedString.toString());
        tuples.add(new Tuple(fixedString.toString(), token));
    }

}
