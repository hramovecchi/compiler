package ar.exa.edu.unicen.compiler.syntactic.parser;

import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.errorWithoutException;
import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.info;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import ar.exa.edu.unicen.compiler.lexical.Lexical;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;
import ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.Phase;

/**
 * Base parser class in charge of defines the non-implemented methods generated
 * by YACC.
 *
 * @author pmvillafane
 *
 */
public abstract class BaseParser {

    private final Iterator<Tuple> tuplesIt;

    private Tuple currentTuple;

    /**
     * Default handler.
     *
     * @param lexical
     *            reference to the lexical analyzer.
     * @param sourceCode
     *            source code to evaluate.
     * @throws IOException
     *             throws an exception in case of error reading the source code
     *             file.
     */
    public BaseParser(final Lexical lexical, final InputStream sourceCode)
            throws IOException {
        final List<Tuple> tuples = lexical.run(sourceCode);
        this.tuplesIt = tuples.listIterator();
    }

    public int yylex() {
        if (this.tuplesIt.hasNext()) {
            this.currentTuple = this.tuplesIt.next();

            final Token token = this.currentTuple.getToken();
            final int tokenId = token.getId();
            return tokenId;
        }
        return -1;
    }

    /**
     * Reports a syntax message with INFO priority level.
     *
     * @param info
     *            the message to report.
     */
    public void yyinfo(final String info) {
        info(Phase.SYNTACTIC, this.currentTuple.getLine(), info);
    }

    /**
     * Reports a syntax error with ERROR priority level.
     *
     * @param err
     *            the error to report.
     */
    public void yyerror(final String err) {
        errorWithoutException(Phase.SYNTACTIC, this.currentTuple.getLine(), err);
    }

}
