// Generated from ar\exa\edu\u005Cunicen\compiler\lexical\antlr\sources\Grammar.g4 by ANTLR 4.3
package ar.exa.edu.unicen.compiler.lexical.antlr.sources;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarParser#evaluate}.
	 * @param ctx the parse tree
	 */
	void enterEvaluate(@NotNull GrammarParser.EvaluateContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#evaluate}.
	 * @param ctx the parse tree
	 */
	void exitEvaluate(@NotNull GrammarParser.EvaluateContext ctx);
}