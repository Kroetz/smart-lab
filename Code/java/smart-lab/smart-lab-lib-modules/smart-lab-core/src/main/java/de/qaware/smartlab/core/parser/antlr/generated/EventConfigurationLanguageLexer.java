// Generated from C:/Users/d.schock/Desktop/Masterarbeit/smart_lab/Code/java/smart-lab/smart-lab-lib-modules/smart-lab-core/src/main/antlr\EventConfigurationLanguage.g4 by ANTLR 4.7
package de.qaware.smartlab.core.parser.antlr.generated;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class EventConfigurationLanguageLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IDENTIFIER=1, INTEGER=2, STRING=3, CONFIG_TAG_BEGIN=4, CONFIG_TAG_BEGIN_IDENTIFIER=5, 
		CONFIG_TAG_END=6, CONFIG_TAG_END_IDENTIFIER=7, WORKGROUP_TAG=8, WORKGROUP_TAG_IDENTIFIER=9, 
		AGENDA_TAG_BEGIN=10, AGENDA_TAG_BEGIN_IDENTIFIER=11, AGENDA_TAG_END=12, 
		AGENDA_TAG_END_IDENTIFIER=13, ASSISTANCES_TAG_BEGIN=14, ASSISTANCES_TAG_BEGIN_IDENTIFIER=15, 
		ASSISTANCES_TAG_END=16, ASSISTANCES_TAG_END_IDENTIFIER=17, SIGNAL_SEQUENCE=18, 
		LPAREN=19, RPAREN=20, LBRACE=21, RBRACE=22, LBRACK=23, RBRACK=24, COMMA=25, 
		DOT=26, COLON=27, SEMICOLON=28, EQUALS=29, QUOTES=30, WHITESPACE=31, COMMENT=32, 
		LINE_COMMENT=33;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"IDENTIFIER", "INTEGER", "STRING", "CONFIG_TAG_BEGIN", "CONFIG_TAG_BEGIN_IDENTIFIER", 
		"CONFIG_TAG_END", "CONFIG_TAG_END_IDENTIFIER", "WORKGROUP_TAG", "WORKGROUP_TAG_IDENTIFIER", 
		"AGENDA_TAG_BEGIN", "AGENDA_TAG_BEGIN_IDENTIFIER", "AGENDA_TAG_END", "AGENDA_TAG_END_IDENTIFIER", 
		"ASSISTANCES_TAG_BEGIN", "ASSISTANCES_TAG_BEGIN_IDENTIFIER", "ASSISTANCES_TAG_END", 
		"ASSISTANCES_TAG_END_IDENTIFIER", "SIGNAL_SEQUENCE", "LPAREN", "RPAREN", 
		"LBRACE", "RBRACE", "LBRACK", "RBRACK", "COMMA", "DOT", "COLON", "SEMICOLON", 
		"EQUALS", "QUOTES", "WHITESPACE", "COMMENT", "LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, "'smart-lab-config-begin'", null, "'smart-lab-config-end'", 
		null, "'workgroup'", null, "'agenda-begin'", null, "'agenda-end'", null, 
		"'assistances-begin'", null, "'assistances-end'", "'@'", "'('", "')'", 
		"'{'", "'}'", "'['", "']'", "','", "'.'", "':'", "';'", "'='", "'\"'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "IDENTIFIER", "INTEGER", "STRING", "CONFIG_TAG_BEGIN", "CONFIG_TAG_BEGIN_IDENTIFIER", 
		"CONFIG_TAG_END", "CONFIG_TAG_END_IDENTIFIER", "WORKGROUP_TAG", "WORKGROUP_TAG_IDENTIFIER", 
		"AGENDA_TAG_BEGIN", "AGENDA_TAG_BEGIN_IDENTIFIER", "AGENDA_TAG_END", "AGENDA_TAG_END_IDENTIFIER", 
		"ASSISTANCES_TAG_BEGIN", "ASSISTANCES_TAG_BEGIN_IDENTIFIER", "ASSISTANCES_TAG_END", 
		"ASSISTANCES_TAG_END_IDENTIFIER", "SIGNAL_SEQUENCE", "LPAREN", "RPAREN", 
		"LBRACE", "RBRACE", "LBRACK", "RBRACK", "COMMA", "DOT", "COLON", "SEMICOLON", 
		"EQUALS", "QUOTES", "WHITESPACE", "COMMENT", "LINE_COMMENT"
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


	public EventConfigurationLanguageLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "EventConfigurationLanguage.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2#\u011e\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\3\2\6\2G\n\2\r\2\16\2H\3\2\7\2L\n\2\f\2\16\2O\13\2\3\3\6\3"+
		"R\n\3\r\3\16\3S\3\4\3\4\7\4X\n\4\f\4\16\4[\13\4\3\4\3\4\3\4\3\5\3\5\3"+
		"\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3"+
		"\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3"+
		"\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3"+
		"\37\3\37\3 \6 \u0100\n \r \16 \u0101\3 \3 \3!\3!\3!\3!\7!\u010a\n!\f!"+
		"\16!\u010d\13!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\7\"\u0118\n\"\f\"\16\"\u011b"+
		"\13\"\3\"\3\"\3\u010b\2#\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f"+
		"\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63"+
		"\33\65\34\67\359\36;\37= ?!A\"C#\3\2\b\3\2c|\5\2\62;C\\c|\3\2\62;\3\2"+
		"$$\5\2\13\f\16\17\"\"\4\2\f\f\17\17\2\u0124\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2"+
		"\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2"+
		"\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2"+
		"\2\2A\3\2\2\2\2C\3\2\2\2\3F\3\2\2\2\5Q\3\2\2\2\7U\3\2\2\2\t_\3\2\2\2\13"+
		"b\3\2\2\2\ry\3\2\2\2\17|\3\2\2\2\21\u0091\3\2\2\2\23\u0094\3\2\2\2\25"+
		"\u009e\3\2\2\2\27\u00a1\3\2\2\2\31\u00ae\3\2\2\2\33\u00b1\3\2\2\2\35\u00bc"+
		"\3\2\2\2\37\u00bf\3\2\2\2!\u00d1\3\2\2\2#\u00d4\3\2\2\2%\u00e4\3\2\2\2"+
		"\'\u00e6\3\2\2\2)\u00e8\3\2\2\2+\u00ea\3\2\2\2-\u00ec\3\2\2\2/\u00ee\3"+
		"\2\2\2\61\u00f0\3\2\2\2\63\u00f2\3\2\2\2\65\u00f4\3\2\2\2\67\u00f6\3\2"+
		"\2\29\u00f8\3\2\2\2;\u00fa\3\2\2\2=\u00fc\3\2\2\2?\u00ff\3\2\2\2A\u0105"+
		"\3\2\2\2C\u0113\3\2\2\2EG\t\2\2\2FE\3\2\2\2GH\3\2\2\2HF\3\2\2\2HI\3\2"+
		"\2\2IM\3\2\2\2JL\t\3\2\2KJ\3\2\2\2LO\3\2\2\2MK\3\2\2\2MN\3\2\2\2N\4\3"+
		"\2\2\2OM\3\2\2\2PR\t\4\2\2QP\3\2\2\2RS\3\2\2\2SQ\3\2\2\2ST\3\2\2\2T\6"+
		"\3\2\2\2UY\5=\37\2VX\n\5\2\2WV\3\2\2\2X[\3\2\2\2YW\3\2\2\2YZ\3\2\2\2Z"+
		"\\\3\2\2\2[Y\3\2\2\2\\]\5=\37\2]^\b\4\2\2^\b\3\2\2\2_`\5%\23\2`a\5\13"+
		"\6\2a\n\3\2\2\2bc\7u\2\2cd\7o\2\2de\7c\2\2ef\7t\2\2fg\7v\2\2gh\7/\2\2"+
		"hi\7n\2\2ij\7c\2\2jk\7d\2\2kl\7/\2\2lm\7e\2\2mn\7q\2\2no\7p\2\2op\7h\2"+
		"\2pq\7k\2\2qr\7i\2\2rs\7/\2\2st\7d\2\2tu\7g\2\2uv\7i\2\2vw\7k\2\2wx\7"+
		"p\2\2x\f\3\2\2\2yz\5%\23\2z{\5\17\b\2{\16\3\2\2\2|}\7u\2\2}~\7o\2\2~\177"+
		"\7c\2\2\177\u0080\7t\2\2\u0080\u0081\7v\2\2\u0081\u0082\7/\2\2\u0082\u0083"+
		"\7n\2\2\u0083\u0084\7c\2\2\u0084\u0085\7d\2\2\u0085\u0086\7/\2\2\u0086"+
		"\u0087\7e\2\2\u0087\u0088\7q\2\2\u0088\u0089\7p\2\2\u0089\u008a\7h\2\2"+
		"\u008a\u008b\7k\2\2\u008b\u008c\7i\2\2\u008c\u008d\7/\2\2\u008d\u008e"+
		"\7g\2\2\u008e\u008f\7p\2\2\u008f\u0090\7f\2\2\u0090\20\3\2\2\2\u0091\u0092"+
		"\5%\23\2\u0092\u0093\5\23\n\2\u0093\22\3\2\2\2\u0094\u0095\7y\2\2\u0095"+
		"\u0096\7q\2\2\u0096\u0097\7t\2\2\u0097\u0098\7m\2\2\u0098\u0099\7i\2\2"+
		"\u0099\u009a\7t\2\2\u009a\u009b\7q\2\2\u009b\u009c\7w\2\2\u009c\u009d"+
		"\7r\2\2\u009d\24\3\2\2\2\u009e\u009f\5%\23\2\u009f\u00a0\5\27\f\2\u00a0"+
		"\26\3\2\2\2\u00a1\u00a2\7c\2\2\u00a2\u00a3\7i\2\2\u00a3\u00a4\7g\2\2\u00a4"+
		"\u00a5\7p\2\2\u00a5\u00a6\7f\2\2\u00a6\u00a7\7c\2\2\u00a7\u00a8\7/\2\2"+
		"\u00a8\u00a9\7d\2\2\u00a9\u00aa\7g\2\2\u00aa\u00ab\7i\2\2\u00ab\u00ac"+
		"\7k\2\2\u00ac\u00ad\7p\2\2\u00ad\30\3\2\2\2\u00ae\u00af\5%\23\2\u00af"+
		"\u00b0\5\33\16\2\u00b0\32\3\2\2\2\u00b1\u00b2\7c\2\2\u00b2\u00b3\7i\2"+
		"\2\u00b3\u00b4\7g\2\2\u00b4\u00b5\7p\2\2\u00b5\u00b6\7f\2\2\u00b6\u00b7"+
		"\7c\2\2\u00b7\u00b8\7/\2\2\u00b8\u00b9\7g\2\2\u00b9\u00ba\7p\2\2\u00ba"+
		"\u00bb\7f\2\2\u00bb\34\3\2\2\2\u00bc\u00bd\5%\23\2\u00bd\u00be\5\37\20"+
		"\2\u00be\36\3\2\2\2\u00bf\u00c0\7c\2\2\u00c0\u00c1\7u\2\2\u00c1\u00c2"+
		"\7u\2\2\u00c2\u00c3\7k\2\2\u00c3\u00c4\7u\2\2\u00c4\u00c5\7v\2\2\u00c5"+
		"\u00c6\7c\2\2\u00c6\u00c7\7p\2\2\u00c7\u00c8\7e\2\2\u00c8\u00c9\7g\2\2"+
		"\u00c9\u00ca\7u\2\2\u00ca\u00cb\7/\2\2\u00cb\u00cc\7d\2\2\u00cc\u00cd"+
		"\7g\2\2\u00cd\u00ce\7i\2\2\u00ce\u00cf\7k\2\2\u00cf\u00d0\7p\2\2\u00d0"+
		" \3\2\2\2\u00d1\u00d2\5%\23\2\u00d2\u00d3\5#\22\2\u00d3\"\3\2\2\2\u00d4"+
		"\u00d5\7c\2\2\u00d5\u00d6\7u\2\2\u00d6\u00d7\7u\2\2\u00d7\u00d8\7k\2\2"+
		"\u00d8\u00d9\7u\2\2\u00d9\u00da\7v\2\2\u00da\u00db\7c\2\2\u00db\u00dc"+
		"\7p\2\2\u00dc\u00dd\7e\2\2\u00dd\u00de\7g\2\2\u00de\u00df\7u\2\2\u00df"+
		"\u00e0\7/\2\2\u00e0\u00e1\7g\2\2\u00e1\u00e2\7p\2\2\u00e2\u00e3\7f\2\2"+
		"\u00e3$\3\2\2\2\u00e4\u00e5\7B\2\2\u00e5&\3\2\2\2\u00e6\u00e7\7*\2\2\u00e7"+
		"(\3\2\2\2\u00e8\u00e9\7+\2\2\u00e9*\3\2\2\2\u00ea\u00eb\7}\2\2\u00eb,"+
		"\3\2\2\2\u00ec\u00ed\7\177\2\2\u00ed.\3\2\2\2\u00ee\u00ef\7]\2\2\u00ef"+
		"\60\3\2\2\2\u00f0\u00f1\7_\2\2\u00f1\62\3\2\2\2\u00f2\u00f3\7.\2\2\u00f3"+
		"\64\3\2\2\2\u00f4\u00f5\7\60\2\2\u00f5\66\3\2\2\2\u00f6\u00f7\7<\2\2\u00f7"+
		"8\3\2\2\2\u00f8\u00f9\7=\2\2\u00f9:\3\2\2\2\u00fa\u00fb\7?\2\2\u00fb<"+
		"\3\2\2\2\u00fc\u00fd\7$\2\2\u00fd>\3\2\2\2\u00fe\u0100\t\6\2\2\u00ff\u00fe"+
		"\3\2\2\2\u0100\u0101\3\2\2\2\u0101\u00ff\3\2\2\2\u0101\u0102\3\2\2\2\u0102"+
		"\u0103\3\2\2\2\u0103\u0104\b \3\2\u0104@\3\2\2\2\u0105\u0106\7\61\2\2"+
		"\u0106\u0107\7,\2\2\u0107\u010b\3\2\2\2\u0108\u010a\13\2\2\2\u0109\u0108"+
		"\3\2\2\2\u010a\u010d\3\2\2\2\u010b\u010c\3\2\2\2\u010b\u0109\3\2\2\2\u010c"+
		"\u010e\3\2\2\2\u010d\u010b\3\2\2\2\u010e\u010f\7,\2\2\u010f\u0110\7\61"+
		"\2\2\u0110\u0111\3\2\2\2\u0111\u0112\b!\4\2\u0112B\3\2\2\2\u0113\u0114"+
		"\7\61\2\2\u0114\u0115\7\61\2\2\u0115\u0119\3\2\2\2\u0116\u0118\n\7\2\2"+
		"\u0117\u0116\3\2\2\2\u0118\u011b\3\2\2\2\u0119\u0117\3\2\2\2\u0119\u011a"+
		"\3\2\2\2\u011a\u011c\3\2\2\2\u011b\u0119\3\2\2\2\u011c\u011d\b\"\4\2\u011d"+
		"D\3\2\2\2\n\2HMSY\u0101\u010b\u0119\5\3\4\2\b\2\2\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}