// Generated from ar\exa\edu\u005Cunicen\compiler\lexical\antlr\sources\Grammar.g4 by ANTLR 4.3
package ar.exa.edu.unicen.compiler.lexical.antlr.sources;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WILDCARDS=1, DIGIT=2, ID=3, COMMENT=4;
	public static final String[] tokenNames = {
		"<INVALID>", "WILDCARDS", "DIGIT", "ID", "COMMENT"
	};
	public static final int
		RULE_evaluate = 0;
	public static final String[] ruleNames = {
		"evaluate"
	};

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class EvaluateContext extends ParserRuleContext {
		public List<TerminalNode> COMMENT() { return getTokens(GrammarParser.COMMENT); }
		public List<TerminalNode> ID() { return getTokens(GrammarParser.ID); }
		public TerminalNode EOF() { return getToken(GrammarParser.EOF, 0); }
		public TerminalNode COMMENT(int i) {
			return getToken(GrammarParser.COMMENT, i);
		}
		public TerminalNode ID(int i) {
			return getToken(GrammarParser.ID, i);
		}
		public EvaluateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_evaluate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterEvaluate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitEvaluate(this);
		}
	}

	public final EvaluateContext evaluate() throws RecognitionException {
		EvaluateContext _localctx = new EvaluateContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_evaluate);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(5);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID || _la==COMMENT) {
				{
				{
				setState(2);
				_la = _input.LA(1);
				if ( !(_la==ID || _la==COMMENT) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				}
				}
				setState(7);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(8); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\6\r\4\2\t\2\3\2\7"+
		"\2\6\n\2\f\2\16\2\t\13\2\3\2\3\2\3\2\2\2\3\2\2\3\3\2\5\6\f\2\7\3\2\2\2"+
		"\4\6\t\2\2\2\5\4\3\2\2\2\6\t\3\2\2\2\7\5\3\2\2\2\7\b\3\2\2\2\b\n\3\2\2"+
		"\2\t\7\3\2\2\2\n\13\7\2\2\3\13\3\3\2\2\2\3\7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}