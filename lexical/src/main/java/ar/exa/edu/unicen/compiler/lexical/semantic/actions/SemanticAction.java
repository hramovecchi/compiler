package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

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
     * @return the lexeme after evaluation.
     */
    String doAction(String lexeme);

}
