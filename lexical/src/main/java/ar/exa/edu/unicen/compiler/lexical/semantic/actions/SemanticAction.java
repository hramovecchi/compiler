package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

import java.util.List;

import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;

/**
 * Defines the behavior of a Semantic Action.
 * 
 * @author pmvillafane
 */
public interface SemanticAction {

    /**
     * Semantic action to perform.
     * 
     * @param lexeme
     *            the text to evaluate.
     * @param tuples
     *            list of tuples.
     * @param token
     *            the token being evaluated.
     * @param line
     *            line number.
     */
    void doAction(String lexeme, List<Tuple> tuples, Token token, int line);

}
