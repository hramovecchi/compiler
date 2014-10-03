package ar.exa.edu.unicen.compiler.syntactic.parser;

import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.error;
import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.info;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import ar.exa.edu.unicen.compiler.lexical.Lexical;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Token;
import ar.exa.edu.unicen.compiler.lexical.analyzer.Tuple;
import ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.Phase;

public abstract class BaseParser {

    private final Iterator<Tuple> tuplesIt;

    private Tuple currentTuple;

    public BaseParser(final Lexical lexical, final InputStream sourceCode)
            throws IOException {
        final List<Tuple> tuples = lexical.run(sourceCode);
        tuplesIt = tuples.listIterator();
    }

    public int yylex() {
        if (tuplesIt.hasNext()) {
            currentTuple = tuplesIt.next();

            final Token token = currentTuple.getToken();
            final int tokenId = token.getId();
            return tokenId;
        }
        return -1;
    }

    public void yyinfo(final String info) {
        info(Phase.SYNTACTIC, currentTuple.getLexeme(),
                currentTuple.getToken(), currentTuple.getLine() - 1, info);
    }

    public void yyerror(final String err) {
        error(Phase.SYNTACTIC, currentTuple.getLexeme(), currentTuple
                .getToken(), currentTuple.getLine() - 1, err);
    }

}
