package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fixes a string representation: <br />
 * <br />
 * - Removes newline characters like \r, \n.
 * - Removes the character '+' in case of multiline.
 *
 * @author pmvillafane
 */
public class StringFixerAction implements SemanticAction {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(StringFixerAction.class);

    @Override
    public String doAction(final String lexeme) {

        final StringBuilder fixedString = new StringBuilder();
        final String[] strFragments = lexeme.replace("\r", "").split("\n");
        for (String str : strFragments) {
            if (!str.startsWith("'")) {
                fixedString.append(str.trim().replaceFirst("\\+", ""));
            } else {
                fixedString.append(str);
            }
        }

        LOGGER.warn("Unificando string {}", fixedString.toString());

        return fixedString.toString();
    }

}
