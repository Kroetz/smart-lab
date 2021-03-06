// Generated from C:/Users/d.schock/Desktop/Masterarbeit/smart_lab/Code/java/smart-lab/smart-lab-lib-modules/smart-lab-core/src/main/antlr\EventConfigurationLanguage.g4 by ANTLR 4.7
package de.qaware.smartlab.core.parser.antlr.generated;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class EventConfigurationLanguageParser extends Parser {
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
	public static final int
		RULE_eventConfiguration = 0, RULE_statement = 1, RULE_assignment = 2, 
		RULE_section = 3, RULE_agendaSection = 4, RULE_agendaItems = 5, RULE_agendaItem = 6, 
		RULE_assistancesSection = 7, RULE_assistances = 8, RULE_assistance = 9, 
		RULE_assistanceArgs = 10, RULE_assistanceArg = 11;
	public static final String[] ruleNames = {
		"eventConfiguration", "statement", "assignment", "section", "agendaSection", 
		"agendaItems", "agendaItem", "assistancesSection", "assistances", "assistance", 
		"assistanceArgs", "assistanceArg"
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

	@Override
	public String getGrammarFileName() { return "EventConfigurationLanguage.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public EventConfigurationLanguageParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class EventConfigurationContext extends ParserRuleContext {
		public TerminalNode CONFIG_TAG_BEGIN() { return getToken(EventConfigurationLanguageParser.CONFIG_TAG_BEGIN, 0); }
		public TerminalNode CONFIG_TAG_END() { return getToken(EventConfigurationLanguageParser.CONFIG_TAG_END, 0); }
		public TerminalNode EOF() { return getToken(EventConfigurationLanguageParser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public EventConfigurationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eventConfiguration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).enterEventConfiguration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).exitEventConfiguration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EventConfigurationLanguageVisitor ) return ((EventConfigurationLanguageVisitor<? extends T>)visitor).visitEventConfiguration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EventConfigurationContext eventConfiguration() throws RecognitionException {
		EventConfigurationContext _localctx = new EventConfigurationContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_eventConfiguration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(CONFIG_TAG_BEGIN);
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << WORKGROUP_TAG) | (1L << AGENDA_TAG_BEGIN) | (1L << ASSISTANCES_TAG_BEGIN))) != 0)) {
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
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EventConfigurationLanguageVisitor ) return ((EventConfigurationLanguageVisitor<? extends T>)visitor).visitStatement(this);
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
			case WORKGROUP_TAG:
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
		public TerminalNode WORKGROUP_TAG() { return getToken(EventConfigurationLanguageParser.WORKGROUP_TAG, 0); }
		public TerminalNode EQUALS() { return getToken(EventConfigurationLanguageParser.EQUALS, 0); }
		public TerminalNode STRING() { return getToken(EventConfigurationLanguageParser.STRING, 0); }
		public WorkgroupAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).enterWorkgroupAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).exitWorkgroupAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EventConfigurationLanguageVisitor ) return ((EventConfigurationLanguageVisitor<? extends T>)visitor).visitWorkgroupAssignment(this);
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
			match(WORKGROUP_TAG);
			setState(39);
			match(EQUALS);
			setState(40);
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
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).enterSection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).exitSection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EventConfigurationLanguageVisitor ) return ((EventConfigurationLanguageVisitor<? extends T>)visitor).visitSection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SectionContext section() throws RecognitionException {
		SectionContext _localctx = new SectionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_section);
		try {
			setState(44);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AGENDA_TAG_BEGIN:
				enterOuterAlt(_localctx, 1);
				{
				setState(42);
				agendaSection();
				}
				break;
			case ASSISTANCES_TAG_BEGIN:
				enterOuterAlt(_localctx, 2);
				{
				setState(43);
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
		public TerminalNode AGENDA_TAG_BEGIN() { return getToken(EventConfigurationLanguageParser.AGENDA_TAG_BEGIN, 0); }
		public AgendaItemsContext agendaItems() {
			return getRuleContext(AgendaItemsContext.class,0);
		}
		public TerminalNode AGENDA_TAG_END() { return getToken(EventConfigurationLanguageParser.AGENDA_TAG_END, 0); }
		public AgendaSectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_agendaSection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).enterAgendaSection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).exitAgendaSection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EventConfigurationLanguageVisitor ) return ((EventConfigurationLanguageVisitor<? extends T>)visitor).visitAgendaSection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AgendaSectionContext agendaSection() throws RecognitionException {
		AgendaSectionContext _localctx = new AgendaSectionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_agendaSection);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(AGENDA_TAG_BEGIN);
			setState(47);
			agendaItems();
			setState(48);
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
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).enterAgendaItems(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).exitAgendaItems(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EventConfigurationLanguageVisitor ) return ((EventConfigurationLanguageVisitor<? extends T>)visitor).visitAgendaItems(this);
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
			setState(53);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==STRING) {
				{
				{
				setState(50);
				((AgendaItemsContext)_localctx).agendaItem = agendaItem();
				((AgendaItemsContext)_localctx).agendaItemList.add(((AgendaItemsContext)_localctx).agendaItem);
				}
				}
				setState(55);
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
		public TerminalNode STRING() { return getToken(EventConfigurationLanguageParser.STRING, 0); }
		public AgendaItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_agendaItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).enterAgendaItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).exitAgendaItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EventConfigurationLanguageVisitor ) return ((EventConfigurationLanguageVisitor<? extends T>)visitor).visitAgendaItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AgendaItemContext agendaItem() throws RecognitionException {
		AgendaItemContext _localctx = new AgendaItemContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_agendaItem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
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
		public TerminalNode ASSISTANCES_TAG_BEGIN() { return getToken(EventConfigurationLanguageParser.ASSISTANCES_TAG_BEGIN, 0); }
		public AssistancesContext assistances() {
			return getRuleContext(AssistancesContext.class,0);
		}
		public TerminalNode ASSISTANCES_TAG_END() { return getToken(EventConfigurationLanguageParser.ASSISTANCES_TAG_END, 0); }
		public AssistancesSectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assistancesSection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).enterAssistancesSection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).exitAssistancesSection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EventConfigurationLanguageVisitor ) return ((EventConfigurationLanguageVisitor<? extends T>)visitor).visitAssistancesSection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssistancesSectionContext assistancesSection() throws RecognitionException {
		AssistancesSectionContext _localctx = new AssistancesSectionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_assistancesSection);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(ASSISTANCES_TAG_BEGIN);
			setState(59);
			assistances();
			setState(60);
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
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).enterAssistances(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).exitAssistances(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EventConfigurationLanguageVisitor ) return ((EventConfigurationLanguageVisitor<? extends T>)visitor).visitAssistances(this);
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
			setState(65);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(62);
				((AssistancesContext)_localctx).assistance = assistance();
				((AssistancesContext)_localctx).assistanceList.add(((AssistancesContext)_localctx).assistance);
				}
				}
				setState(67);
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
		public Token assistanceCommand;
		public TerminalNode LPAREN() { return getToken(EventConfigurationLanguageParser.LPAREN, 0); }
		public AssistanceArgsContext assistanceArgs() {
			return getRuleContext(AssistanceArgsContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(EventConfigurationLanguageParser.RPAREN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(EventConfigurationLanguageParser.IDENTIFIER, 0); }
		public AssistanceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assistance; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).enterAssistance(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).exitAssistance(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EventConfigurationLanguageVisitor ) return ((EventConfigurationLanguageVisitor<? extends T>)visitor).visitAssistance(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssistanceContext assistance() throws RecognitionException {
		AssistanceContext _localctx = new AssistanceContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_assistance);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			((AssistanceContext)_localctx).assistanceCommand = match(IDENTIFIER);
			setState(69);
			match(LPAREN);
			setState(70);
			assistanceArgs();
			setState(71);
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
		public List<TerminalNode> COMMA() { return getTokens(EventConfigurationLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(EventConfigurationLanguageParser.COMMA, i);
		}
		public PopulatedAssistanceArgsContext(AssistanceArgsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).enterPopulatedAssistanceArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).exitPopulatedAssistanceArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EventConfigurationLanguageVisitor ) return ((EventConfigurationLanguageVisitor<? extends T>)visitor).visitPopulatedAssistanceArgs(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EmptyAssistanceArgsContext extends AssistanceArgsContext {
		public EmptyAssistanceArgsContext(AssistanceArgsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).enterEmptyAssistanceArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).exitEmptyAssistanceArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EventConfigurationLanguageVisitor ) return ((EventConfigurationLanguageVisitor<? extends T>)visitor).visitEmptyAssistanceArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssistanceArgsContext assistanceArgs() throws RecognitionException {
		AssistanceArgsContext _localctx = new AssistanceArgsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_assistanceArgs);
		int _la;
		try {
			setState(82);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				_localctx = new PopulatedAssistanceArgsContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(73);
				((PopulatedAssistanceArgsContext)_localctx).assistanceArg = assistanceArg();
				((PopulatedAssistanceArgsContext)_localctx).assistanceArgList.add(((PopulatedAssistanceArgsContext)_localctx).assistanceArg);
				setState(78);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(74);
					match(COMMA);
					setState(75);
					((PopulatedAssistanceArgsContext)_localctx).assistanceArg = assistanceArg();
					((PopulatedAssistanceArgsContext)_localctx).assistanceArgList.add(((PopulatedAssistanceArgsContext)_localctx).assistanceArg);
					}
					}
					setState(80);
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
		public TerminalNode COLON() { return getToken(EventConfigurationLanguageParser.COLON, 0); }
		public TerminalNode IDENTIFIER() { return getToken(EventConfigurationLanguageParser.IDENTIFIER, 0); }
		public TerminalNode STRING() { return getToken(EventConfigurationLanguageParser.STRING, 0); }
		public AssistanceArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assistanceArg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).enterAssistanceArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EventConfigurationLanguageListener ) ((EventConfigurationLanguageListener)listener).exitAssistanceArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EventConfigurationLanguageVisitor ) return ((EventConfigurationLanguageVisitor<? extends T>)visitor).visitAssistanceArg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssistanceArgContext assistanceArg() throws RecognitionException {
		AssistanceArgContext _localctx = new AssistanceArgContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_assistanceArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			((AssistanceArgContext)_localctx).argKey = match(IDENTIFIER);
			setState(85);
			match(COLON);
			setState(86);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3#[\4\2\t\2\4\3\t\3"+
		"\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f"+
		"\t\f\4\r\t\r\3\2\3\2\7\2\35\n\2\f\2\16\2 \13\2\3\2\3\2\3\2\3\3\3\3\5\3"+
		"\'\n\3\3\4\3\4\3\4\3\4\3\5\3\5\5\5/\n\5\3\6\3\6\3\6\3\6\3\7\7\7\66\n\7"+
		"\f\7\16\79\13\7\3\b\3\b\3\t\3\t\3\t\3\t\3\n\7\nB\n\n\f\n\16\nE\13\n\3"+
		"\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\7\fO\n\f\f\f\16\fR\13\f\3\f\5\fU\n"+
		"\f\3\r\3\r\3\r\3\r\3\r\2\2\16\2\4\6\b\n\f\16\20\22\24\26\30\2\2\2U\2\32"+
		"\3\2\2\2\4&\3\2\2\2\6(\3\2\2\2\b.\3\2\2\2\n\60\3\2\2\2\f\67\3\2\2\2\16"+
		":\3\2\2\2\20<\3\2\2\2\22C\3\2\2\2\24F\3\2\2\2\26T\3\2\2\2\30V\3\2\2\2"+
		"\32\36\7\6\2\2\33\35\5\4\3\2\34\33\3\2\2\2\35 \3\2\2\2\36\34\3\2\2\2\36"+
		"\37\3\2\2\2\37!\3\2\2\2 \36\3\2\2\2!\"\7\b\2\2\"#\7\2\2\3#\3\3\2\2\2$"+
		"\'\5\6\4\2%\'\5\b\5\2&$\3\2\2\2&%\3\2\2\2\'\5\3\2\2\2()\7\n\2\2)*\7\37"+
		"\2\2*+\7\5\2\2+\7\3\2\2\2,/\5\n\6\2-/\5\20\t\2.,\3\2\2\2.-\3\2\2\2/\t"+
		"\3\2\2\2\60\61\7\f\2\2\61\62\5\f\7\2\62\63\7\16\2\2\63\13\3\2\2\2\64\66"+
		"\5\16\b\2\65\64\3\2\2\2\669\3\2\2\2\67\65\3\2\2\2\678\3\2\2\28\r\3\2\2"+
		"\29\67\3\2\2\2:;\7\5\2\2;\17\3\2\2\2<=\7\20\2\2=>\5\22\n\2>?\7\22\2\2"+
		"?\21\3\2\2\2@B\5\24\13\2A@\3\2\2\2BE\3\2\2\2CA\3\2\2\2CD\3\2\2\2D\23\3"+
		"\2\2\2EC\3\2\2\2FG\7\3\2\2GH\7\25\2\2HI\5\26\f\2IJ\7\26\2\2J\25\3\2\2"+
		"\2KP\5\30\r\2LM\7\33\2\2MO\5\30\r\2NL\3\2\2\2OR\3\2\2\2PN\3\2\2\2PQ\3"+
		"\2\2\2QU\3\2\2\2RP\3\2\2\2SU\3\2\2\2TK\3\2\2\2TS\3\2\2\2U\27\3\2\2\2V"+
		"W\7\3\2\2WX\7\35\2\2XY\7\5\2\2Y\31\3\2\2\2\t\36&.\67CPT";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}