package ar.exa.edu.unicen.compiler.lexical.semantic.actions;

/**
 * Default exception to report semantic errors.
 * 
 * @author pmvillafane
 */
public class SemanticActionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SemanticActionException() {
        super("Semantic unrecoverable error");
    }

}
