// Generated from C:/Users/d.schock/Desktop/Masterarbeit/smart_lab/Code/smart-lab/smart-lab-lib-modules/smart-lab-meeting/src/main/antlr\MeetingConfigurationLanguage.g4 by ANTLR 4.7
package de.qaware.smartlabcore.parser.antlr.generated;
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
		IDENTIFIER=1, INTEGER=2, STRING=3, WORKGROUP_TAG=4, CONFIG_TAG_BEGIN=5, 
		CONFIG_TAG_END=6, AGENDA_TAG_BEGIN=7, AGENDA_TAG_END=8, ASSISTANCES_TAG_BEGIN=9, 
		ASSISTANCES_TAG_END=10, SIGNAL_SEQUENCE=11, LPAREN=12, RPAREN=13, LBRACE=14, 
		RBRACE=15, LBRACK=16, RBRACK=17, COMMA=18, DOT=19, COLON=20, SEMICOLON=21, 
		EQUALS=22, AT=23, QUOTES=24, WHITESPACE=25, COMMENT=26, LINE_COMMENT=27;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"IDENTIFIER", "INTEGER", "STRING", "WORKGROUP_TAG", "CONFIG_TAG_BEGIN", 
		"CONFIG_TAG_END", "AGENDA_TAG_BEGIN", "AGENDA_TAG_END", "ASSISTANCES_TAG_BEGIN", 
		"ASSISTANCES_TAG_END", "SIGNAL_SEQUENCE", "LPAREN", "RPAREN", "LBRACE", 
		"RBRACE", "LBRACK", "RBRACK", "COMMA", "DOT", "COLON", "SEMICOLON", "EQUALS", 
		"AT", "QUOTES", "WHITESPACE", "COMMENT", "LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"'('", "')'", "'{'", "'}'", "'['", "']'", "','", "'.'", "':'", "';'", 
		"'='", "'@'", "'\"'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "IDENTIFIER", "INTEGER", "STRING", "WORKGROUP_TAG", "CONFIG_TAG_BEGIN", 
		"CONFIG_TAG_END", "AGENDA_TAG_BEGIN", "AGENDA_TAG_END", "ASSISTANCES_TAG_BEGIN", 
		"ASSISTANCES_TAG_END", "SIGNAL_SEQUENCE", "LPAREN", "RPAREN", "LBRACE", 
		"RBRACE", "LBRACK", "RBRACK", "COMMA", "DOT", "COLON", "SEMICOLON", "EQUALS", 
		"AT", "QUOTES", "WHITESPACE", "COMMENT", "LINE_COMMENT"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\35\u0108\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\6\2;\n\2\r\2\16\2<\3\2\7\2@\n"+
		"\2\f\2\16\2C\13\2\3\3\6\3F\n\3\r\3\16\3G\3\4\3\4\7\4L\n\4\f\4\16\4O\13"+
		"\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3"+
		"\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3"+
		"\27\3\30\3\30\3\31\3\31\3\32\6\32\u00ea\n\32\r\32\16\32\u00eb\3\32\3\32"+
		"\3\33\3\33\3\33\3\33\7\33\u00f4\n\33\f\33\16\33\u00f7\13\33\3\33\3\33"+
		"\3\33\3\33\3\33\3\34\3\34\3\34\3\34\7\34\u0102\n\34\f\34\16\34\u0105\13"+
		"\34\3\34\3\34\3\u00f5\2\35\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32"+
		"\63\33\65\34\67\35\3\2\b\3\2c|\5\2\62;C\\c|\3\2\62;\3\2$$\5\2\13\f\16"+
		"\17\"\"\4\2\f\f\17\17\2\u010e\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2"+
		"\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2"+
		"\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2"+
		"+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2"+
		"\2\67\3\2\2\2\3:\3\2\2\2\5E\3\2\2\2\7I\3\2\2\2\tS\3\2\2\2\13^\3\2\2\2"+
		"\rv\3\2\2\2\17\u008c\3\2\2\2\21\u009a\3\2\2\2\23\u00a6\3\2\2\2\25\u00b9"+
		"\3\2\2\2\27\u00ca\3\2\2\2\31\u00ce\3\2\2\2\33\u00d0\3\2\2\2\35\u00d2\3"+
		"\2\2\2\37\u00d4\3\2\2\2!\u00d6\3\2\2\2#\u00d8\3\2\2\2%\u00da\3\2\2\2\'"+
		"\u00dc\3\2\2\2)\u00de\3\2\2\2+\u00e0\3\2\2\2-\u00e2\3\2\2\2/\u00e4\3\2"+
		"\2\2\61\u00e6\3\2\2\2\63\u00e9\3\2\2\2\65\u00ef\3\2\2\2\67\u00fd\3\2\2"+
		"\29;\t\2\2\2:9\3\2\2\2;<\3\2\2\2<:\3\2\2\2<=\3\2\2\2=A\3\2\2\2>@\t\3\2"+
		"\2?>\3\2\2\2@C\3\2\2\2A?\3\2\2\2AB\3\2\2\2B\4\3\2\2\2CA\3\2\2\2DF\t\4"+
		"\2\2ED\3\2\2\2FG\3\2\2\2GE\3\2\2\2GH\3\2\2\2H\6\3\2\2\2IM\5\61\31\2JL"+
		"\n\5\2\2KJ\3\2\2\2LO\3\2\2\2MK\3\2\2\2MN\3\2\2\2NP\3\2\2\2OM\3\2\2\2P"+
		"Q\5\61\31\2QR\b\4\2\2R\b\3\2\2\2ST\5\27\f\2TU\7y\2\2UV\7q\2\2VW\7t\2\2"+
		"WX\7m\2\2XY\7i\2\2YZ\7t\2\2Z[\7q\2\2[\\\7w\2\2\\]\7r\2\2]\n\3\2\2\2^_"+
		"\5\27\f\2_`\7u\2\2`a\7o\2\2ab\7c\2\2bc\7t\2\2cd\7v\2\2de\7/\2\2ef\7n\2"+
		"\2fg\7c\2\2gh\7d\2\2hi\7/\2\2ij\7e\2\2jk\7q\2\2kl\7p\2\2lm\7h\2\2mn\7"+
		"k\2\2no\7i\2\2op\7/\2\2pq\7d\2\2qr\7g\2\2rs\7i\2\2st\7k\2\2tu\7p\2\2u"+
		"\f\3\2\2\2vw\5\27\f\2wx\7u\2\2xy\7o\2\2yz\7c\2\2z{\7t\2\2{|\7v\2\2|}\7"+
		"/\2\2}~\7n\2\2~\177\7c\2\2\177\u0080\7d\2\2\u0080\u0081\7/\2\2\u0081\u0082"+
		"\7e\2\2\u0082\u0083\7q\2\2\u0083\u0084\7p\2\2\u0084\u0085\7h\2\2\u0085"+
		"\u0086\7k\2\2\u0086\u0087\7i\2\2\u0087\u0088\7/\2\2\u0088\u0089\7g\2\2"+
		"\u0089\u008a\7p\2\2\u008a\u008b\7f\2\2\u008b\16\3\2\2\2\u008c\u008d\5"+
		"\27\f\2\u008d\u008e\7c\2\2\u008e\u008f\7i\2\2\u008f\u0090\7g\2\2\u0090"+
		"\u0091\7p\2\2\u0091\u0092\7f\2\2\u0092\u0093\7c\2\2\u0093\u0094\7/\2\2"+
		"\u0094\u0095\7d\2\2\u0095\u0096\7g\2\2\u0096\u0097\7i\2\2\u0097\u0098"+
		"\7k\2\2\u0098\u0099\7p\2\2\u0099\20\3\2\2\2\u009a\u009b\5\27\f\2\u009b"+
		"\u009c\7c\2\2\u009c\u009d\7i\2\2\u009d\u009e\7g\2\2\u009e\u009f\7p\2\2"+
		"\u009f\u00a0\7f\2\2\u00a0\u00a1\7c\2\2\u00a1\u00a2\7/\2\2\u00a2\u00a3"+
		"\7g\2\2\u00a3\u00a4\7p\2\2\u00a4\u00a5\7f\2\2\u00a5\22\3\2\2\2\u00a6\u00a7"+
		"\5\27\f\2\u00a7\u00a8\7c\2\2\u00a8\u00a9\7u\2\2\u00a9\u00aa\7u\2\2\u00aa"+
		"\u00ab\7k\2\2\u00ab\u00ac\7u\2\2\u00ac\u00ad\7v\2\2\u00ad\u00ae\7c\2\2"+
		"\u00ae\u00af\7p\2\2\u00af\u00b0\7e\2\2\u00b0\u00b1\7g\2\2\u00b1\u00b2"+
		"\7u\2\2\u00b2\u00b3\7/\2\2\u00b3\u00b4\7d\2\2\u00b4\u00b5\7g\2\2\u00b5"+
		"\u00b6\7i\2\2\u00b6\u00b7\7k\2\2\u00b7\u00b8\7p\2\2\u00b8\24\3\2\2\2\u00b9"+
		"\u00ba\5\27\f\2\u00ba\u00bb\7c\2\2\u00bb\u00bc\7u\2\2\u00bc\u00bd\7u\2"+
		"\2\u00bd\u00be\7k\2\2\u00be\u00bf\7u\2\2\u00bf\u00c0\7v\2\2\u00c0\u00c1"+
		"\7c\2\2\u00c1\u00c2\7p\2\2\u00c2\u00c3\7e\2\2\u00c3\u00c4\7g\2\2\u00c4"+
		"\u00c5\7u\2\2\u00c5\u00c6\7/\2\2\u00c6\u00c7\7g\2\2\u00c7\u00c8\7p\2\2"+
		"\u00c8\u00c9\7f\2\2\u00c9\26\3\2\2\2\u00ca\u00cb\5/\30\2\u00cb\u00cc\5"+
		"/\30\2\u00cc\u00cd\5/\30\2\u00cd\30\3\2\2\2\u00ce\u00cf\7*\2\2\u00cf\32"+
		"\3\2\2\2\u00d0\u00d1\7+\2\2\u00d1\34\3\2\2\2\u00d2\u00d3\7}\2\2\u00d3"+
		"\36\3\2\2\2\u00d4\u00d5\7\177\2\2\u00d5 \3\2\2\2\u00d6\u00d7\7]\2\2\u00d7"+
		"\"\3\2\2\2\u00d8\u00d9\7_\2\2\u00d9$\3\2\2\2\u00da\u00db\7.\2\2\u00db"+
		"&\3\2\2\2\u00dc\u00dd\7\60\2\2\u00dd(\3\2\2\2\u00de\u00df\7<\2\2\u00df"+
		"*\3\2\2\2\u00e0\u00e1\7=\2\2\u00e1,\3\2\2\2\u00e2\u00e3\7?\2\2\u00e3."+
		"\3\2\2\2\u00e4\u00e5\7B\2\2\u00e5\60\3\2\2\2\u00e6\u00e7\7$\2\2\u00e7"+
		"\62\3\2\2\2\u00e8\u00ea\t\6\2\2\u00e9\u00e8\3\2\2\2\u00ea\u00eb\3\2\2"+
		"\2\u00eb\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00ee"+
		"\b\32\3\2\u00ee\64\3\2\2\2\u00ef\u00f0\7\61\2\2\u00f0\u00f1\7,\2\2\u00f1"+
		"\u00f5\3\2\2\2\u00f2\u00f4\13\2\2\2\u00f3\u00f2\3\2\2\2\u00f4\u00f7\3"+
		"\2\2\2\u00f5\u00f6\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f6\u00f8\3\2\2\2\u00f7"+
		"\u00f5\3\2\2\2\u00f8\u00f9\7,\2\2\u00f9\u00fa\7\61\2\2\u00fa\u00fb\3\2"+
		"\2\2\u00fb\u00fc\b\33\4\2\u00fc\66\3\2\2\2\u00fd\u00fe\7\61\2\2\u00fe"+
		"\u00ff\7\61\2\2\u00ff\u0103\3\2\2\2\u0100\u0102\n\7\2\2\u0101\u0100\3"+
		"\2\2\2\u0102\u0105\3\2\2\2\u0103\u0101\3\2\2\2\u0103\u0104\3\2\2\2\u0104"+
		"\u0106\3\2\2\2\u0105\u0103\3\2\2\2\u0106\u0107\b\34\4\2\u01078\3\2\2\2"+
		"\n\2<AGM\u00eb\u00f5\u0103\5\3\4\2\b\2\2\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}