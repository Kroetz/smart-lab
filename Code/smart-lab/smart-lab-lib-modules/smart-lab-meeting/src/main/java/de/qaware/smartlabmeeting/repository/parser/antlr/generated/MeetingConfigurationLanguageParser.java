// Generated from C:/Users/d.schock/Desktop/Masterarbeit/smart_lab/Code/smart-lab/smart-lab-lib-modules/smart-lab-meeting/src/main/antlr\MeetingConfigurationLanguage.g4 by ANTLR 4.7
package de.qaware.smartlabmeeting.repository.parser.antlr.generated;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MeetingConfigurationLanguageParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IDENTIFIER=1, INTEGER=2, STRING=3, WORKGROUP_IDENTIFIER=4, CONFIG_TAG_BEGIN=5, 
		CONFIG_TAG_END=6, AGENDA_TAG_BEGIN=7, AGENDA_TAG_END=8, ASSISTANCES_TAG_BEGIN=9, 
		ASSISTANCES_TAG_END=10, SIGNAL_SEQUENCE=11, LPAREN=12, RPAREN=13, LBRACE=14, 
		RBRACE=15, LBRACK=16, RBRACK=17, COMMA=18, DOT=19, COLON=20, SEMICOLON=21, 
		EQUALS=22, AT=23, QUOTES=24, WHITESPACE=25, COMMENT=26, LINE_COMMENT=27;
	public static final int
		RULE_meetingConfiguration = 0, RULE_statement = 1, RULE_assignment = 2, 
		RULE_section = 3, RULE_agendaSection = 4, RULE_agendaItems = 5, RULE_agendaItem = 6, 
		RULE_assistancesSection = 7, RULE_assistances = 8, RULE_assistance = 9, 
		RULE_assistanceArgs = 10, RULE_assistanceArg = 11;
	public static final String[] ruleNames = {
		"meetingConfiguration", "statement", "assignment", "section", "agendaSection", 
		"agendaItems", "agendaItem", "assistancesSection", "assistances", "assistance", 
		"assistanceArgs", "assistanceArg"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, "'workgroup'", null, null, null, null, null, null, 
		null, "'('", "')'", "'{'", "'}'", "'['", "']'", "','", "'.'", "':'", "';'", 
		"'='", "'@'", "'\"'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "IDENTIFIER", "INTEGER", "STRING", "WORKGROUP_IDENTIFIER", "CONFIG_TAG_BEGIN", 
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

	@Override
	public String getGrammarFileName() { return "MeetingConfigurationLanguage.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MeetingConfigurationLanguageParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class MeetingConfigurationContext extends ParserRuleContext {
		public TerminalNode CONFIG_TAG_BEGIN() { return getToken(MeetingConfigurationLanguageParser.CONFIG_TAG_BEGIN, 0); }
		public TerminalNode CONFIG_TAG_END() { return getToken(MeetingConfigurationLanguageParser.CONFIG_TAG_END, 0); }
		public TerminalNode EOF() { return getToken(MeetingConfigurationLanguageParser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public MeetingConfigurationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_meetingConfiguration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).enterMeetingConfiguration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).exitMeetingConfiguration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeetingConfigurationLanguageVisitor ) return ((MeetingConfigurationLanguageVisitor<? extends T>)visitor).visitMeetingConfiguration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MeetingConfigurationContext meetingConfiguration() throws RecognitionException {
		MeetingConfigurationContext _localctx = new MeetingConfigurationContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_meetingConfiguration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(CONFIG_TAG_BEGIN);
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AGENDA_TAG_BEGIN) | (1L << ASSISTANCES_TAG_BEGIN) | (1L << SIGNAL_SEQUENCE))) != 0)) {
				{
				{
				setState(25);
				statement();
				}
				}
				setState(30);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(31);
			match(CONFIG_TAG_END);
			setState(32);
			match(EOF);
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

	public static class StatementContext extends ParserRuleContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public SectionContext section() {
			return getRuleContext(SectionContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeetingConfigurationLanguageVisitor ) return ((MeetingConfigurationLanguageVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(36);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SIGNAL_SEQUENCE:
				enterOuterAlt(_localctx, 1);
				{
				setState(34);
				assignment();
				}
				break;
			case AGENDA_TAG_BEGIN:
			case ASSISTANCES_TAG_BEGIN:
				enterOuterAlt(_localctx, 2);
				{
				setState(35);
				section();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class AssignmentContext extends ParserRuleContext {
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
	 
		public AssignmentContext() { }
		public void copyFrom(AssignmentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class WorkgroupAssignmentContext extends AssignmentContext {
		public Token workgroupId;
		public TerminalNode SIGNAL_SEQUENCE() { return getToken(MeetingConfigurationLanguageParser.SIGNAL_SEQUENCE, 0); }
		public TerminalNode WORKGROUP_IDENTIFIER() { return getToken(MeetingConfigurationLanguageParser.WORKGROUP_IDENTIFIER, 0); }
		public TerminalNode EQUALS() { return getToken(MeetingConfigurationLanguageParser.EQUALS, 0); }
		public TerminalNode STRING() { return getToken(MeetingConfigurationLanguageParser.STRING, 0); }
		public WorkgroupAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).enterWorkgroupAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).exitWorkgroupAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeetingConfigurationLanguageVisitor ) return ((MeetingConfigurationLanguageVisitor<? extends T>)visitor).visitWorkgroupAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_assignment);
		try {
			_localctx = new WorkgroupAssignmentContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			match(SIGNAL_SEQUENCE);
			setState(39);
			match(WORKGROUP_IDENTIFIER);
			setState(40);
			match(EQUALS);
			setState(41);
			((WorkgroupAssignmentContext)_localctx).workgroupId = match(STRING);
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

	public static class SectionContext extends ParserRuleContext {
		public AgendaSectionContext agendaSection() {
			return getRuleContext(AgendaSectionContext.class,0);
		}
		public AssistancesSectionContext assistancesSection() {
			return getRuleContext(AssistancesSectionContext.class,0);
		}
		public SectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_section; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).enterSection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).exitSection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeetingConfigurationLanguageVisitor ) return ((MeetingConfigurationLanguageVisitor<? extends T>)visitor).visitSection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SectionContext section() throws RecognitionException {
		SectionContext _localctx = new SectionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_section);
		try {
			setState(45);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AGENDA_TAG_BEGIN:
				enterOuterAlt(_localctx, 1);
				{
				setState(43);
				agendaSection();
				}
				break;
			case ASSISTANCES_TAG_BEGIN:
				enterOuterAlt(_localctx, 2);
				{
				setState(44);
				assistancesSection();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class AgendaSectionContext extends ParserRuleContext {
		public TerminalNode AGENDA_TAG_BEGIN() { return getToken(MeetingConfigurationLanguageParser.AGENDA_TAG_BEGIN, 0); }
		public AgendaItemsContext agendaItems() {
			return getRuleContext(AgendaItemsContext.class,0);
		}
		public TerminalNode AGENDA_TAG_END() { return getToken(MeetingConfigurationLanguageParser.AGENDA_TAG_END, 0); }
		public AgendaSectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_agendaSection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).enterAgendaSection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).exitAgendaSection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeetingConfigurationLanguageVisitor ) return ((MeetingConfigurationLanguageVisitor<? extends T>)visitor).visitAgendaSection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AgendaSectionContext agendaSection() throws RecognitionException {
		AgendaSectionContext _localctx = new AgendaSectionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_agendaSection);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(AGENDA_TAG_BEGIN);
			setState(48);
			agendaItems();
			setState(49);
			match(AGENDA_TAG_END);
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

	public static class AgendaItemsContext extends ParserRuleContext {
		public AgendaItemContext agendaItem;
		public List<AgendaItemContext> agendaItemList = new ArrayList<AgendaItemContext>();
		public List<AgendaItemContext> agendaItem() {
			return getRuleContexts(AgendaItemContext.class);
		}
		public AgendaItemContext agendaItem(int i) {
			return getRuleContext(AgendaItemContext.class,i);
		}
		public AgendaItemsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_agendaItems; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).enterAgendaItems(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).exitAgendaItems(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeetingConfigurationLanguageVisitor ) return ((MeetingConfigurationLanguageVisitor<? extends T>)visitor).visitAgendaItems(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AgendaItemsContext agendaItems() throws RecognitionException {
		AgendaItemsContext _localctx = new AgendaItemsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_agendaItems);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==STRING) {
				{
				{
				setState(51);
				((AgendaItemsContext)_localctx).agendaItem = agendaItem();
				((AgendaItemsContext)_localctx).agendaItemList.add(((AgendaItemsContext)_localctx).agendaItem);
				}
				}
				setState(56);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class AgendaItemContext extends ParserRuleContext {
		public Token content;
		public TerminalNode STRING() { return getToken(MeetingConfigurationLanguageParser.STRING, 0); }
		public AgendaItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_agendaItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).enterAgendaItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).exitAgendaItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeetingConfigurationLanguageVisitor ) return ((MeetingConfigurationLanguageVisitor<? extends T>)visitor).visitAgendaItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AgendaItemContext agendaItem() throws RecognitionException {
		AgendaItemContext _localctx = new AgendaItemContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_agendaItem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			((AgendaItemContext)_localctx).content = match(STRING);
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

	public static class AssistancesSectionContext extends ParserRuleContext {
		public TerminalNode ASSISTANCES_TAG_BEGIN() { return getToken(MeetingConfigurationLanguageParser.ASSISTANCES_TAG_BEGIN, 0); }
		public AssistancesContext assistances() {
			return getRuleContext(AssistancesContext.class,0);
		}
		public TerminalNode ASSISTANCES_TAG_END() { return getToken(MeetingConfigurationLanguageParser.ASSISTANCES_TAG_END, 0); }
		public AssistancesSectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assistancesSection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).enterAssistancesSection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).exitAssistancesSection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeetingConfigurationLanguageVisitor ) return ((MeetingConfigurationLanguageVisitor<? extends T>)visitor).visitAssistancesSection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssistancesSectionContext assistancesSection() throws RecognitionException {
		AssistancesSectionContext _localctx = new AssistancesSectionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_assistancesSection);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			match(ASSISTANCES_TAG_BEGIN);
			setState(60);
			assistances();
			setState(61);
			match(ASSISTANCES_TAG_END);
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

	public static class AssistancesContext extends ParserRuleContext {
		public AssistanceContext assistance;
		public List<AssistanceContext> assistanceList = new ArrayList<AssistanceContext>();
		public List<AssistanceContext> assistance() {
			return getRuleContexts(AssistanceContext.class);
		}
		public AssistanceContext assistance(int i) {
			return getRuleContext(AssistanceContext.class,i);
		}
		public AssistancesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assistances; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).enterAssistances(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).exitAssistances(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeetingConfigurationLanguageVisitor ) return ((MeetingConfigurationLanguageVisitor<? extends T>)visitor).visitAssistances(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssistancesContext assistances() throws RecognitionException {
		AssistancesContext _localctx = new AssistancesContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_assistances);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(63);
				((AssistancesContext)_localctx).assistance = assistance();
				((AssistancesContext)_localctx).assistanceList.add(((AssistancesContext)_localctx).assistance);
				}
				}
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class AssistanceContext extends ParserRuleContext {
		public Token assistanceId;
		public TerminalNode LPAREN() { return getToken(MeetingConfigurationLanguageParser.LPAREN, 0); }
		public AssistanceArgsContext assistanceArgs() {
			return getRuleContext(AssistanceArgsContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(MeetingConfigurationLanguageParser.RPAREN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(MeetingConfigurationLanguageParser.IDENTIFIER, 0); }
		public AssistanceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assistance; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).enterAssistance(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).exitAssistance(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeetingConfigurationLanguageVisitor ) return ((MeetingConfigurationLanguageVisitor<? extends T>)visitor).visitAssistance(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssistanceContext assistance() throws RecognitionException {
		AssistanceContext _localctx = new AssistanceContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_assistance);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			((AssistanceContext)_localctx).assistanceId = match(IDENTIFIER);
			setState(70);
			match(LPAREN);
			setState(71);
			assistanceArgs();
			setState(72);
			match(RPAREN);
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

	public static class AssistanceArgsContext extends ParserRuleContext {
		public AssistanceArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assistanceArgs; }
	 
		public AssistanceArgsContext() { }
		public void copyFrom(AssistanceArgsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PopulatedAssistanceArgsContext extends AssistanceArgsContext {
		public AssistanceArgContext assistanceArg;
		public List<AssistanceArgContext> assistanceArgList = new ArrayList<AssistanceArgContext>();
		public List<AssistanceArgContext> assistanceArg() {
			return getRuleContexts(AssistanceArgContext.class);
		}
		public AssistanceArgContext assistanceArg(int i) {
			return getRuleContext(AssistanceArgContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MeetingConfigurationLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MeetingConfigurationLanguageParser.COMMA, i);
		}
		public PopulatedAssistanceArgsContext(AssistanceArgsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).enterPopulatedAssistanceArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).exitPopulatedAssistanceArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeetingConfigurationLanguageVisitor ) return ((MeetingConfigurationLanguageVisitor<? extends T>)visitor).visitPopulatedAssistanceArgs(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EmptyAssistanceArgsContext extends AssistanceArgsContext {
		public EmptyAssistanceArgsContext(AssistanceArgsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).enterEmptyAssistanceArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).exitEmptyAssistanceArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeetingConfigurationLanguageVisitor ) return ((MeetingConfigurationLanguageVisitor<? extends T>)visitor).visitEmptyAssistanceArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssistanceArgsContext assistanceArgs() throws RecognitionException {
		AssistanceArgsContext _localctx = new AssistanceArgsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_assistanceArgs);
		int _la;
		try {
			setState(83);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				_localctx = new PopulatedAssistanceArgsContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(74);
				((PopulatedAssistanceArgsContext)_localctx).assistanceArg = assistanceArg();
				((PopulatedAssistanceArgsContext)_localctx).assistanceArgList.add(((PopulatedAssistanceArgsContext)_localctx).assistanceArg);
				setState(79);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(75);
					match(COMMA);
					setState(76);
					((PopulatedAssistanceArgsContext)_localctx).assistanceArg = assistanceArg();
					((PopulatedAssistanceArgsContext)_localctx).assistanceArgList.add(((PopulatedAssistanceArgsContext)_localctx).assistanceArg);
					}
					}
					setState(81);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case RPAREN:
				_localctx = new EmptyAssistanceArgsContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class AssistanceArgContext extends ParserRuleContext {
		public Token argKey;
		public Token argValue;
		public TerminalNode COLON() { return getToken(MeetingConfigurationLanguageParser.COLON, 0); }
		public TerminalNode IDENTIFIER() { return getToken(MeetingConfigurationLanguageParser.IDENTIFIER, 0); }
		public TerminalNode STRING() { return getToken(MeetingConfigurationLanguageParser.STRING, 0); }
		public AssistanceArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assistanceArg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).enterAssistanceArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeetingConfigurationLanguageListener ) ((MeetingConfigurationLanguageListener)listener).exitAssistanceArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeetingConfigurationLanguageVisitor ) return ((MeetingConfigurationLanguageVisitor<? extends T>)visitor).visitAssistanceArg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssistanceArgContext assistanceArg() throws RecognitionException {
		AssistanceArgContext _localctx = new AssistanceArgContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_assistanceArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			((AssistanceArgContext)_localctx).argKey = match(IDENTIFIER);
			setState(86);
			match(COLON);
			setState(87);
			((AssistanceArgContext)_localctx).argValue = match(STRING);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\35\\\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13"+
		"\4\f\t\f\4\r\t\r\3\2\3\2\7\2\35\n\2\f\2\16\2 \13\2\3\2\3\2\3\2\3\3\3\3"+
		"\5\3\'\n\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\5\5\60\n\5\3\6\3\6\3\6\3\6\3\7"+
		"\7\7\67\n\7\f\7\16\7:\13\7\3\b\3\b\3\t\3\t\3\t\3\t\3\n\7\nC\n\n\f\n\16"+
		"\nF\13\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\7\fP\n\f\f\f\16\fS\13\f"+
		"\3\f\5\fV\n\f\3\r\3\r\3\r\3\r\3\r\2\2\16\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\2\2\2V\2\32\3\2\2\2\4&\3\2\2\2\6(\3\2\2\2\b/\3\2\2\2\n\61\3\2\2\2\f8"+
		"\3\2\2\2\16;\3\2\2\2\20=\3\2\2\2\22D\3\2\2\2\24G\3\2\2\2\26U\3\2\2\2\30"+
		"W\3\2\2\2\32\36\7\7\2\2\33\35\5\4\3\2\34\33\3\2\2\2\35 \3\2\2\2\36\34"+
		"\3\2\2\2\36\37\3\2\2\2\37!\3\2\2\2 \36\3\2\2\2!\"\7\b\2\2\"#\7\2\2\3#"+
		"\3\3\2\2\2$\'\5\6\4\2%\'\5\b\5\2&$\3\2\2\2&%\3\2\2\2\'\5\3\2\2\2()\7\r"+
		"\2\2)*\7\6\2\2*+\7\30\2\2+,\7\5\2\2,\7\3\2\2\2-\60\5\n\6\2.\60\5\20\t"+
		"\2/-\3\2\2\2/.\3\2\2\2\60\t\3\2\2\2\61\62\7\t\2\2\62\63\5\f\7\2\63\64"+
		"\7\n\2\2\64\13\3\2\2\2\65\67\5\16\b\2\66\65\3\2\2\2\67:\3\2\2\28\66\3"+
		"\2\2\289\3\2\2\29\r\3\2\2\2:8\3\2\2\2;<\7\5\2\2<\17\3\2\2\2=>\7\13\2\2"+
		">?\5\22\n\2?@\7\f\2\2@\21\3\2\2\2AC\5\24\13\2BA\3\2\2\2CF\3\2\2\2DB\3"+
		"\2\2\2DE\3\2\2\2E\23\3\2\2\2FD\3\2\2\2GH\7\3\2\2HI\7\16\2\2IJ\5\26\f\2"+
		"JK\7\17\2\2K\25\3\2\2\2LQ\5\30\r\2MN\7\24\2\2NP\5\30\r\2OM\3\2\2\2PS\3"+
		"\2\2\2QO\3\2\2\2QR\3\2\2\2RV\3\2\2\2SQ\3\2\2\2TV\3\2\2\2UL\3\2\2\2UT\3"+
		"\2\2\2V\27\3\2\2\2WX\7\3\2\2XY\7\26\2\2YZ\7\5\2\2Z\31\3\2\2\2\t\36&/8"+
		"DQU";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}