// Generated from C:/Users/d.schock/Desktop/Masterarbeit/smart_lab/Code/smart-lab/smart-lab-lib-modules/smart-lab-core/src/main/antlr\MeetingConfigurationLanguage.g4 by ANTLR 4.7
package de.qaware.smartlab.core.parser.antlr.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MeetingConfigurationLanguageParser}.
 */
public interface MeetingConfigurationLanguageListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MeetingConfigurationLanguageParser#meetingConfiguration}.
	 * @param ctx the parse tree
	 */
	void enterMeetingConfiguration(MeetingConfigurationLanguageParser.MeetingConfigurationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeetingConfigurationLanguageParser#meetingConfiguration}.
	 * @param ctx the parse tree
	 */
	void exitMeetingConfiguration(MeetingConfigurationLanguageParser.MeetingConfigurationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeetingConfigurationLanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MeetingConfigurationLanguageParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeetingConfigurationLanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MeetingConfigurationLanguageParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WorkgroupAssignment}
	 * labeled alternative in {@link MeetingConfigurationLanguageParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterWorkgroupAssignment(MeetingConfigurationLanguageParser.WorkgroupAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WorkgroupAssignment}
	 * labeled alternative in {@link MeetingConfigurationLanguageParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitWorkgroupAssignment(MeetingConfigurationLanguageParser.WorkgroupAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeetingConfigurationLanguageParser#section}.
	 * @param ctx the parse tree
	 */
	void enterSection(MeetingConfigurationLanguageParser.SectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeetingConfigurationLanguageParser#section}.
	 * @param ctx the parse tree
	 */
	void exitSection(MeetingConfigurationLanguageParser.SectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeetingConfigurationLanguageParser#agendaSection}.
	 * @param ctx the parse tree
	 */
	void enterAgendaSection(MeetingConfigurationLanguageParser.AgendaSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeetingConfigurationLanguageParser#agendaSection}.
	 * @param ctx the parse tree
	 */
	void exitAgendaSection(MeetingConfigurationLanguageParser.AgendaSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeetingConfigurationLanguageParser#agendaItems}.
	 * @param ctx the parse tree
	 */
	void enterAgendaItems(MeetingConfigurationLanguageParser.AgendaItemsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeetingConfigurationLanguageParser#agendaItems}.
	 * @param ctx the parse tree
	 */
	void exitAgendaItems(MeetingConfigurationLanguageParser.AgendaItemsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeetingConfigurationLanguageParser#agendaItem}.
	 * @param ctx the parse tree
	 */
	void enterAgendaItem(MeetingConfigurationLanguageParser.AgendaItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeetingConfigurationLanguageParser#agendaItem}.
	 * @param ctx the parse tree
	 */
	void exitAgendaItem(MeetingConfigurationLanguageParser.AgendaItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeetingConfigurationLanguageParser#assistancesSection}.
	 * @param ctx the parse tree
	 */
	void enterAssistancesSection(MeetingConfigurationLanguageParser.AssistancesSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeetingConfigurationLanguageParser#assistancesSection}.
	 * @param ctx the parse tree
	 */
	void exitAssistancesSection(MeetingConfigurationLanguageParser.AssistancesSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeetingConfigurationLanguageParser#assistances}.
	 * @param ctx the parse tree
	 */
	void enterAssistances(MeetingConfigurationLanguageParser.AssistancesContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeetingConfigurationLanguageParser#assistances}.
	 * @param ctx the parse tree
	 */
	void exitAssistances(MeetingConfigurationLanguageParser.AssistancesContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeetingConfigurationLanguageParser#assistance}.
	 * @param ctx the parse tree
	 */
	void enterAssistance(MeetingConfigurationLanguageParser.AssistanceContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeetingConfigurationLanguageParser#assistance}.
	 * @param ctx the parse tree
	 */
	void exitAssistance(MeetingConfigurationLanguageParser.AssistanceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PopulatedAssistanceArgs}
	 * labeled alternative in {@link MeetingConfigurationLanguageParser#assistanceArgs}.
	 * @param ctx the parse tree
	 */
	void enterPopulatedAssistanceArgs(MeetingConfigurationLanguageParser.PopulatedAssistanceArgsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PopulatedAssistanceArgs}
	 * labeled alternative in {@link MeetingConfigurationLanguageParser#assistanceArgs}.
	 * @param ctx the parse tree
	 */
	void exitPopulatedAssistanceArgs(MeetingConfigurationLanguageParser.PopulatedAssistanceArgsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EmptyAssistanceArgs}
	 * labeled alternative in {@link MeetingConfigurationLanguageParser#assistanceArgs}.
	 * @param ctx the parse tree
	 */
	void enterEmptyAssistanceArgs(MeetingConfigurationLanguageParser.EmptyAssistanceArgsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EmptyAssistanceArgs}
	 * labeled alternative in {@link MeetingConfigurationLanguageParser#assistanceArgs}.
	 * @param ctx the parse tree
	 */
	void exitEmptyAssistanceArgs(MeetingConfigurationLanguageParser.EmptyAssistanceArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeetingConfigurationLanguageParser#assistanceArg}.
	 * @param ctx the parse tree
	 */
	void enterAssistanceArg(MeetingConfigurationLanguageParser.AssistanceArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeetingConfigurationLanguageParser#assistanceArg}.
	 * @param ctx the parse tree
	 */
	void exitAssistanceArg(MeetingConfigurationLanguageParser.AssistanceArgContext ctx);
}