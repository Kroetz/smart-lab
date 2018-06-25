// Generated from C:/Users/d.schock/Desktop/Masterarbeit/smart_lab/Code/smart-lab/smart-lab-lib-modules/smart-lab-meeting/src/main/antlr\MeetingConfigurationLanguage.g4 by ANTLR 4.7
package de.qaware.smartlabmeeting.repository.parser.antlr.generated;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MeetingConfigurationLanguageLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IDENTIFIER=1, INTEGER=2, STRING=3, WORKGROUP_IDENTIFIER=4, AGENDA_TAG_BEGIN=5, 
		AGENDA_TAG_END=6, ASSISTANCES_TAG_BEGIN=7, ASSISTANCES_TAG_END=8, SIGNAL_SEQUENCE=9, 
		LPAREN=10, RPAREN=11, LBRACE=12, RBRACE=13, LBRACK=14, RBRACK=15, COMMA=16, 
		DOT=17, COLON=18, SEMICOLON=19, EQUALS=20, AT=21, QUOTES=22, WHITESPACE=23, 
		COMMENT=24, LINE_COMMENT=25;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"IDENTIFIER", "INTEGER", "STRING", "WORKGROUP_IDENTIFIER", "AGENDA_TAG_BEGIN", 
		"AGENDA_TAG_END", "ASSISTANCES_TAG_BEGIN", "ASSISTANCES_TAG_END", "SIGNAL_SEQUENCE", 
		"LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "COMMA", "DOT", 
		"COLON", "SEMICOLON", "EQUALS", "AT", "QUOTES", "WHITESPACE", "COMMENT", 
		"LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, "'workgroup'", null, null, null, null, null, "'('", 
		"')'", "'{'", "'}'", "'['", "']'", "','", "'.'", "':'", "';'", "'='", 
		"'@'", "'\"'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "IDENTIFIER", "INTEGER", "STRING", "WORKGROUP_IDENTIFIER", "AGENDA_TAG_BEGIN", 
		"AGENDA_TAG_END", "ASSISTANCES_TAG_BEGIN", "ASSISTANCES_TAG_END", "SIGNAL_SEQUENCE", 
		"LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "COMMA", "DOT", 
		"COLON", "SEMICOLON", "EQUALS", "AT", "QUOTES", "WHITESPACE", "COMMENT", 
		"LINE_COMMENT"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\33\u00d5\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\3\2\6\2\67\n\2\r\2\16\28\3\2\7\2<\n\2\f\2\16\2?\13\2\3"+
		"\3\6\3B\n\3\r\3\16\3C\3\4\3\4\7\4H\n\4\f\4\16\4K\13\4\3\4\3\4\3\4\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20"+
		"\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27"+
		"\3\27\3\30\6\30\u00b7\n\30\r\30\16\30\u00b8\3\30\3\30\3\31\3\31\3\31\3"+
		"\31\7\31\u00c1\n\31\f\31\16\31\u00c4\13\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\32\3\32\3\32\3\32\7\32\u00cf\n\32\f\32\16\32\u00d2\13\32\3\32\3\32\3"+
		"\u00c2\2\33\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33"+
		"\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\3\2\b\3\2"+
		"c|\5\2\62;C\\c|\3\2\62;\3\2$$\5\2\13\f\16\17\"\"\4\2\f\f\17\17\2\u00db"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\3\66\3\2\2\2\5A\3\2\2\2\7E\3\2\2\2\tO\3\2"+
		"\2\2\13Y\3\2\2\2\rg\3\2\2\2\17s\3\2\2\2\21\u0086\3\2\2\2\23\u0097\3\2"+
		"\2\2\25\u009b\3\2\2\2\27\u009d\3\2\2\2\31\u009f\3\2\2\2\33\u00a1\3\2\2"+
		"\2\35\u00a3\3\2\2\2\37\u00a5\3\2\2\2!\u00a7\3\2\2\2#\u00a9\3\2\2\2%\u00ab"+
		"\3\2\2\2\'\u00ad\3\2\2\2)\u00af\3\2\2\2+\u00b1\3\2\2\2-\u00b3\3\2\2\2"+
		"/\u00b6\3\2\2\2\61\u00bc\3\2\2\2\63\u00ca\3\2\2\2\65\67\t\2\2\2\66\65"+
		"\3\2\2\2\678\3\2\2\28\66\3\2\2\289\3\2\2\29=\3\2\2\2:<\t\3\2\2;:\3\2\2"+
		"\2<?\3\2\2\2=;\3\2\2\2=>\3\2\2\2>\4\3\2\2\2?=\3\2\2\2@B\t\4\2\2A@\3\2"+
		"\2\2BC\3\2\2\2CA\3\2\2\2CD\3\2\2\2D\6\3\2\2\2EI\5-\27\2FH\n\5\2\2GF\3"+
		"\2\2\2HK\3\2\2\2IG\3\2\2\2IJ\3\2\2\2JL\3\2\2\2KI\3\2\2\2LM\5-\27\2MN\b"+
		"\4\2\2N\b\3\2\2\2OP\7y\2\2PQ\7q\2\2QR\7t\2\2RS\7m\2\2ST\7i\2\2TU\7t\2"+
		"\2UV\7q\2\2VW\7w\2\2WX\7r\2\2X\n\3\2\2\2YZ\5\23\n\2Z[\7c\2\2[\\\7i\2\2"+
		"\\]\7g\2\2]^\7p\2\2^_\7f\2\2_`\7c\2\2`a\7/\2\2ab\7d\2\2bc\7g\2\2cd\7i"+
		"\2\2de\7k\2\2ef\7p\2\2f\f\3\2\2\2gh\5\23\n\2hi\7c\2\2ij\7i\2\2jk\7g\2"+
		"\2kl\7p\2\2lm\7f\2\2mn\7c\2\2no\7/\2\2op\7g\2\2pq\7p\2\2qr\7f\2\2r\16"+
		"\3\2\2\2st\5\23\n\2tu\7c\2\2uv\7u\2\2vw\7u\2\2wx\7k\2\2xy\7u\2\2yz\7v"+
		"\2\2z{\7c\2\2{|\7p\2\2|}\7e\2\2}~\7g\2\2~\177\7u\2\2\177\u0080\7/\2\2"+
		"\u0080\u0081\7d\2\2\u0081\u0082\7g\2\2\u0082\u0083\7i\2\2\u0083\u0084"+
		"\7k\2\2\u0084\u0085\7p\2\2\u0085\20\3\2\2\2\u0086\u0087\5\23\n\2\u0087"+
		"\u0088\7c\2\2\u0088\u0089\7u\2\2\u0089\u008a\7u\2\2\u008a\u008b\7k\2\2"+
		"\u008b\u008c\7u\2\2\u008c\u008d\7v\2\2\u008d\u008e\7c\2\2\u008e\u008f"+
		"\7p\2\2\u008f\u0090\7e\2\2\u0090\u0091\7g\2\2\u0091\u0092\7u\2\2\u0092"+
		"\u0093\7/\2\2\u0093\u0094\7g\2\2\u0094\u0095\7p\2\2\u0095\u0096\7f\2\2"+
		"\u0096\22\3\2\2\2\u0097\u0098\5+\26\2\u0098\u0099\5+\26\2\u0099\u009a"+
		"\5+\26\2\u009a\24\3\2\2\2\u009b\u009c\7*\2\2\u009c\26\3\2\2\2\u009d\u009e"+
		"\7+\2\2\u009e\30\3\2\2\2\u009f\u00a0\7}\2\2\u00a0\32\3\2\2\2\u00a1\u00a2"+
		"\7\177\2\2\u00a2\34\3\2\2\2\u00a3\u00a4\7]\2\2\u00a4\36\3\2\2\2\u00a5"+
		"\u00a6\7_\2\2\u00a6 \3\2\2\2\u00a7\u00a8\7.\2\2\u00a8\"\3\2\2\2\u00a9"+
		"\u00aa\7\60\2\2\u00aa$\3\2\2\2\u00ab\u00ac\7<\2\2\u00ac&\3\2\2\2\u00ad"+
		"\u00ae\7=\2\2\u00ae(\3\2\2\2\u00af\u00b0\7?\2\2\u00b0*\3\2\2\2\u00b1\u00b2"+
		"\7B\2\2\u00b2,\3\2\2\2\u00b3\u00b4\7$\2\2\u00b4.\3\2\2\2\u00b5\u00b7\t"+
		"\6\2\2\u00b6\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8"+
		"\u00b9\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00bb\b\30\3\2\u00bb\60\3\2\2"+
		"\2\u00bc\u00bd\7\61\2\2\u00bd\u00be\7,\2\2\u00be\u00c2\3\2\2\2\u00bf\u00c1"+
		"\13\2\2\2\u00c0\u00bf\3\2\2\2\u00c1\u00c4\3\2\2\2\u00c2\u00c3\3\2\2\2"+
		"\u00c2\u00c0\3\2\2\2\u00c3\u00c5\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c5\u00c6"+
		"\7,\2\2\u00c6\u00c7\7\61\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00c9\b\31\4\2"+
		"\u00c9\62\3\2\2\2\u00ca\u00cb\7\61\2\2\u00cb\u00cc\7\61\2\2\u00cc\u00d0"+
		"\3\2\2\2\u00cd\u00cf\n\7\2\2\u00ce\u00cd\3\2\2\2\u00cf\u00d2\3\2\2\2\u00d0"+
		"\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d3\3\2\2\2\u00d2\u00d0\3\2"+
		"\2\2\u00d3\u00d4\b\32\4\2\u00d4\64\3\2\2\2\n\28=CI\u00b8\u00c2\u00d0\5"+
		"\3\4\2\b\2\2\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}