// Generated from C:/Users/d.schock/Desktop/Masterarbeit/smart_lab/Code/smart-lab/smart-lab-lib-modules/smart-lab-meeting/src/main/antlr\MeetingConfigurationLanguage.g4 by ANTLR 4.7
package de.qaware.smartlabmeeting.repository.parser.antlr.generated;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MeetingConfigurationLanguageParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IDENTIFIER=1, INTEGER=2, STRING=3, WORKGROUP_IDENTIFIER=4, AGENDA_TAG=5, 
		ASSISTANCES_TAG=6, SPECIAL_CHAR=7, LPAREN=8, RPAREN=9, LBRACE=10, RBRACE=11, 
		LBRACK=12, RBRACK=13, COMMA=14, DOT=15, COLON=16, SEMICOLON=17, EQUALS=18, 
		AT=19, QUOTES=20, WHITESPACE=21, COMMENT=22, LINE_COMMENT=23;
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
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AGENDA_TAG) | (1L << ASSISTANCES_TAG) | (1L << SPECIAL_CHAR))) != 0)) {
				{
				{
				setState(24);
				statement();
				}
				}
				setState(29);
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
			setState(32);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SPECIAL_CHAR:
				enterOuterAlt(_localctx, 1);
				{
				setState(30);
				assignment();
				}
				break;
			case AGENDA_TAG:
			case ASSISTANCES_TAG:
				enterOuterAlt(_localctx, 2);
				{
				setState(31);
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
		public TerminalNode SPECIAL_CHAR() { return getToken(MeetingConfigurationLanguageParser.SPECIAL_CHAR, 0); }
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
			setState(34);
			match(SPECIAL_CHAR);
			setState(35);
			match(WORKGROUP_IDENTIFIER);
			setState(36);
			match(EQUALS);
			setState(37);
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
			setState(41);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AGENDA_TAG:
				enterOuterAlt(_localctx, 1);
				{
				setState(39);
				agendaSection();
				}
				break;
			case ASSISTANCES_TAG:
				enterOuterAlt(_localctx, 2);
				{
				setState(40);
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
		public List<TerminalNode> AGENDA_TAG() { return getTokens(MeetingConfigurationLanguageParser.AGENDA_TAG); }
		public TerminalNode AGENDA_TAG(int i) {
			return getToken(MeetingConfigurationLanguageParser.AGENDA_TAG, i);
		}
		public AgendaItemsContext agendaItems() {
			return getRuleContext(AgendaItemsContext.class,0);
		}
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
			setState(43);
			match(AGENDA_TAG);
			setState(44);
			agendaItems();
			setState(45);
			match(AGENDA_TAG);
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
			setState(50);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==STRING) {
				{
				{
				setState(47);
				((AgendaItemsContext)_localctx).agendaItem = agendaItem();
				((AgendaItemsContext)_localctx).agendaItemList.add(((AgendaItemsContext)_localctx).agendaItem);
				}
				}
				setState(52);
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
			setState(53);
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
		public List<TerminalNode> ASSISTANCES_TAG() { return getTokens(MeetingConfigurationLanguageParser.ASSISTANCES_TAG); }
		public TerminalNode ASSISTANCES_TAG(int i) {
			return getToken(MeetingConfigurationLanguageParser.ASSISTANCES_TAG, i);
		}
		public AssistancesContext assistances() {
			return getRuleContext(AssistancesContext.class,0);
		}
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
			setState(55);
			match(ASSISTANCES_TAG);
			setState(56);
			assistances();
			setState(57);
			match(ASSISTANCES_TAG);
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
			setState(62);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(59);
				((AssistancesContext)_localctx).assistance = assistance();
				((AssistancesContext)_localctx).assistanceList.add(((AssistancesContext)_localctx).assistance);
				}
				}
				setState(64);
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
			setState(65);
			((AssistanceContext)_localctx).assistanceId = match(IDENTIFIER);
			setState(66);
			match(LPAREN);
			setState(67);
			assistanceArgs();
			setState(68);
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
			setState(79);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				_localctx = new PopulatedAssistanceArgsContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(70);
				((PopulatedAssistanceArgsContext)_localctx).assistanceArg = assistanceArg();
				((PopulatedAssistanceArgsContext)_localctx).assistanceArgList.add(((PopulatedAssistanceArgsContext)_localctx).assistanceArg);
				setState(75);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(71);
					match(COMMA);
					setState(72);
					((PopulatedAssistanceArgsContext)_localctx).assistanceArg = assistanceArg();
					((PopulatedAssistanceArgsContext)_localctx).assistanceArgList.add(((PopulatedAssistanceArgsContext)_localctx).assistanceArg);
					}
					}
					setState(77);
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
			setState(81);
			((AssistanceArgContext)_localctx).argKey = match(IDENTIFIER);
			setState(82);
			match(COLON);
			setState(83);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\31X\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\3\2\7\2\34\n\2\f\2\16\2\37\13\2\3\3\3\3\5\3#\n\3\3\4\3"+
		"\4\3\4\3\4\3\4\3\5\3\5\5\5,\n\5\3\6\3\6\3\6\3\6\3\7\7\7\63\n\7\f\7\16"+
		"\7\66\13\7\3\b\3\b\3\t\3\t\3\t\3\t\3\n\7\n?\n\n\f\n\16\nB\13\n\3\13\3"+
		"\13\3\13\3\13\3\13\3\f\3\f\3\f\7\fL\n\f\f\f\16\fO\13\f\3\f\5\fR\n\f\3"+
		"\r\3\r\3\r\3\r\3\r\2\2\16\2\4\6\b\n\f\16\20\22\24\26\30\2\2\2R\2\35\3"+
		"\2\2\2\4\"\3\2\2\2\6$\3\2\2\2\b+\3\2\2\2\n-\3\2\2\2\f\64\3\2\2\2\16\67"+
		"\3\2\2\2\209\3\2\2\2\22@\3\2\2\2\24C\3\2\2\2\26Q\3\2\2\2\30S\3\2\2\2\32"+
		"\34\5\4\3\2\33\32\3\2\2\2\34\37\3\2\2\2\35\33\3\2\2\2\35\36\3\2\2\2\36"+
		"\3\3\2\2\2\37\35\3\2\2\2 #\5\6\4\2!#\5\b\5\2\" \3\2\2\2\"!\3\2\2\2#\5"+
		"\3\2\2\2$%\7\t\2\2%&\7\6\2\2&\'\7\24\2\2\'(\7\5\2\2(\7\3\2\2\2),\5\n\6"+
		"\2*,\5\20\t\2+)\3\2\2\2+*\3\2\2\2,\t\3\2\2\2-.\7\7\2\2./\5\f\7\2/\60\7"+
		"\7\2\2\60\13\3\2\2\2\61\63\5\16\b\2\62\61\3\2\2\2\63\66\3\2\2\2\64\62"+
		"\3\2\2\2\64\65\3\2\2\2\65\r\3\2\2\2\66\64\3\2\2\2\678\7\5\2\28\17\3\2"+
		"\2\29:\7\b\2\2:;\5\22\n\2;<\7\b\2\2<\21\3\2\2\2=?\5\24\13\2>=\3\2\2\2"+
		"?B\3\2\2\2@>\3\2\2\2@A\3\2\2\2A\23\3\2\2\2B@\3\2\2\2CD\7\3\2\2DE\7\n\2"+
		"\2EF\5\26\f\2FG\7\13\2\2G\25\3\2\2\2HM\5\30\r\2IJ\7\20\2\2JL\5\30\r\2"+
		"KI\3\2\2\2LO\3\2\2\2MK\3\2\2\2MN\3\2\2\2NR\3\2\2\2OM\3\2\2\2PR\3\2\2\2"+
		"QH\3\2\2\2QP\3\2\2\2R\27\3\2\2\2ST\7\3\2\2TU\7\22\2\2UV\7\5\2\2V\31\3"+
		"\2\2\2\t\35\"+\64@MQ";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}