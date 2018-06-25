// Generated from C:/Users/d.schock/Desktop/Masterarbeit/smart_lab/Code/smart-lab/smart-lab-lib-modules/smart-lab-meeting/src/main/antlr\MeetingConfigurationLanguage.g4 by ANTLR 4.7
package de.qaware.smartlabmeeting.repository.parser.antlr.generated;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MeetingConfigurationLanguageLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IDENTIFIER=1, INTEGER=2, STRING=3, WORKGROUP_IDENTIFIER=4, AGENDA_TAG=5, 
		ASSISTANCES_TAG=6, SPECIAL_CHAR=7, LPAREN=8, RPAREN=9, LBRACE=10, RBRACE=11, 
		LBRACK=12, RBRACK=13, COMMA=14, DOT=15, COLON=16, SEMICOLON=17, EQUALS=18, 
		AT=19, QUOTES=20, WHITESPACE=21, COMMENT=22, LINE_COMMENT=23;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"IDENTIFIER", "INTEGER", "STRING", "WORKGROUP_IDENTIFIER", "AGENDA_TAG", 
		"ASSISTANCES_TAG", "SPECIAL_CHAR", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
		"LBRACK", "RBRACK", "COMMA", "DOT", "COLON", "SEMICOLON", "EQUALS", "AT", 
		"QUOTES", "WHITESPACE", "COMMENT", "LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, "'workgroup'", null, null, null, "'('", "')'", 
		"'{'", "'}'", "'['", "']'", "','", "'.'", "':'", "';'", "'='", "'@'", 
		"'\"'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "IDENTIFIER", "INTEGER", "STRING", "WORKGROUP_IDENTIFIER", "AGENDA_TAG", 
		"ASSISTANCES_TAG", "SPECIAL_CHAR", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
		"LBRACK", "RBRACK", "COMMA", "DOT", "COLON", "SEMICOLON", "EQUALS", "AT", 
		"QUOTES", "WHITESPACE", "COMMENT", "LINE_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public MeetingConfigurationLanguageLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MeetingConfigurationLanguage.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 2:
			STRING_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void STRING_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			setText(getText().substring(1, getText().length()-1));
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\31\u00a6\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2"+
		"\6\2\63\n\2\r\2\16\2\64\3\2\7\28\n\2\f\2\16\2;\13\2\3\3\6\3>\n\3\r\3\16"+
		"\3?\3\4\3\4\7\4D\n\4\f\4\16\4G\13\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f"+
		"\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3"+
		"\23\3\24\3\24\3\25\3\25\3\26\6\26\u0088\n\26\r\26\16\26\u0089\3\26\3\26"+
		"\3\27\3\27\3\27\3\27\7\27\u0092\n\27\f\27\16\27\u0095\13\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\30\3\30\3\30\3\30\7\30\u00a0\n\30\f\30\16\30\u00a3\13"+
		"\30\3\30\3\30\3\u0093\2\31\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\3\2\b"+
		"\3\2c|\5\2\62;C\\c|\3\2\62;\3\2$$\5\2\13\f\16\17\"\"\4\2\f\f\17\17\2\u00ac"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\3\62\3\2\2\2\5=\3\2\2\2\7A\3\2\2\2\tK\3\2\2\2\13U\3\2\2\2\r]\3\2\2"+
		"\2\17j\3\2\2\2\21l\3\2\2\2\23n\3\2\2\2\25p\3\2\2\2\27r\3\2\2\2\31t\3\2"+
		"\2\2\33v\3\2\2\2\35x\3\2\2\2\37z\3\2\2\2!|\3\2\2\2#~\3\2\2\2%\u0080\3"+
		"\2\2\2\'\u0082\3\2\2\2)\u0084\3\2\2\2+\u0087\3\2\2\2-\u008d\3\2\2\2/\u009b"+
		"\3\2\2\2\61\63\t\2\2\2\62\61\3\2\2\2\63\64\3\2\2\2\64\62\3\2\2\2\64\65"+
		"\3\2\2\2\659\3\2\2\2\668\t\3\2\2\67\66\3\2\2\28;\3\2\2\29\67\3\2\2\29"+
		":\3\2\2\2:\4\3\2\2\2;9\3\2\2\2<>\t\4\2\2=<\3\2\2\2>?\3\2\2\2?=\3\2\2\2"+
		"?@\3\2\2\2@\6\3\2\2\2AE\5)\25\2BD\n\5\2\2CB\3\2\2\2DG\3\2\2\2EC\3\2\2"+
		"\2EF\3\2\2\2FH\3\2\2\2GE\3\2\2\2HI\5)\25\2IJ\b\4\2\2J\b\3\2\2\2KL\7y\2"+
		"\2LM\7q\2\2MN\7t\2\2NO\7m\2\2OP\7i\2\2PQ\7t\2\2QR\7q\2\2RS\7w\2\2ST\7"+
		"r\2\2T\n\3\2\2\2UV\5\17\b\2VW\7c\2\2WX\7i\2\2XY\7g\2\2YZ\7p\2\2Z[\7f\2"+
		"\2[\\\7c\2\2\\\f\3\2\2\2]^\5\17\b\2^_\7c\2\2_`\7u\2\2`a\7u\2\2ab\7k\2"+
		"\2bc\7u\2\2cd\7v\2\2de\7c\2\2ef\7p\2\2fg\7e\2\2gh\7g\2\2hi\7u\2\2i\16"+
		"\3\2\2\2jk\5\'\24\2k\20\3\2\2\2lm\7*\2\2m\22\3\2\2\2no\7+\2\2o\24\3\2"+
		"\2\2pq\7}\2\2q\26\3\2\2\2rs\7\177\2\2s\30\3\2\2\2tu\7]\2\2u\32\3\2\2\2"+
		"vw\7_\2\2w\34\3\2\2\2xy\7.\2\2y\36\3\2\2\2z{\7\60\2\2{ \3\2\2\2|}\7<\2"+
		"\2}\"\3\2\2\2~\177\7=\2\2\177$\3\2\2\2\u0080\u0081\7?\2\2\u0081&\3\2\2"+
		"\2\u0082\u0083\7B\2\2\u0083(\3\2\2\2\u0084\u0085\7$\2\2\u0085*\3\2\2\2"+
		"\u0086\u0088\t\6\2\2\u0087\u0086\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u0087"+
		"\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u008c\b\26\3\2"+
		"\u008c,\3\2\2\2\u008d\u008e\7\61\2\2\u008e\u008f\7,\2\2\u008f\u0093\3"+
		"\2\2\2\u0090\u0092\13\2\2\2\u0091\u0090\3\2\2\2\u0092\u0095\3\2\2\2\u0093"+
		"\u0094\3\2\2\2\u0093\u0091\3\2\2\2\u0094\u0096\3\2\2\2\u0095\u0093\3\2"+
		"\2\2\u0096\u0097\7,\2\2\u0097\u0098\7\61\2\2\u0098\u0099\3\2\2\2\u0099"+
		"\u009a\b\27\4\2\u009a.\3\2\2\2\u009b\u009c\7\61\2\2\u009c\u009d\7\61\2"+
		"\2\u009d\u00a1\3\2\2\2\u009e\u00a0\n\7\2\2\u009f\u009e\3\2\2\2\u00a0\u00a3"+
		"\3\2\2\2\u00a1\u009f\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\u00a4\3\2\2\2\u00a3"+
		"\u00a1\3\2\2\2\u00a4\u00a5\b\30\4\2\u00a5\60\3\2\2\2\n\2\649?E\u0089\u0093"+
		"\u00a1\5\3\4\2\b\2\2\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}