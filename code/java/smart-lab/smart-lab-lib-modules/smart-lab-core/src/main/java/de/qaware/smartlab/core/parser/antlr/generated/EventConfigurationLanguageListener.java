// Generated from C:/Users/d.schock/Desktop/Masterarbeit/smart_lab/Code/java/smart-lab/smart-lab-lib-modules/smart-lab-core/src/main/antlr\EventConfigurationLanguage.g4 by ANTLR 4.7
package de.qaware.smartlab.core.parser.antlr.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link EventConfigurationLanguageParser}.
 */
public interface EventConfigurationLanguageListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link EventConfigurationLanguageParser#eventConfiguration}.
	 * @param ctx the parse tree
	 */
	void enterEventConfiguration(EventConfigurationLanguageParser.EventConfigurationContext ctx);
	/**
	 * Exit a parse tree produced by {@link EventConfigurationLanguageParser#eventConfiguration}.
	 * @param ctx the parse tree
	 */
	void exitEventConfiguration(EventConfigurationLanguageParser.EventConfigurationContext ctx);
	/**
	 * Enter a parse tree produced by {@link EventConfigurationLanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(EventConfigurationLanguageParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link EventConfigurationLanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(EventConfigurationLanguageParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WorkgroupAssignment}
	 * labeled alternative in {@link EventConfigurationLanguageParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterWorkgroupAssignment(EventConfigurationLanguageParser.WorkgroupAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WorkgroupAssignment}
	 * labeled alternative in {@link EventConfigurationLanguageParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitWorkgroupAssignment(EventConfigurationLanguageParser.WorkgroupAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link EventConfigurationLanguageParser#section}.
	 * @param ctx the parse tree
	 */
	void enterSection(EventConfigurationLanguageParser.SectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EventConfigurationLanguageParser#section}.
	 * @param ctx the parse tree
	 */
	void exitSection(EventConfigurationLanguageParser.SectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EventConfigurationLanguageParser#agendaSection}.
	 * @param ctx the parse tree
	 */
	void enterAgendaSection(EventConfigurationLanguageParser.AgendaSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EventConfigurationLanguageParser#agendaSection}.
	 * @param ctx the parse tree
	 */
	void exitAgendaSection(EventConfigurationLanguageParser.AgendaSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EventConfigurationLanguageParser#agendaItems}.
	 * @param ctx the parse tree
	 */
	void enterAgendaItems(EventConfigurationLanguageParser.AgendaItemsContext ctx);
	/**
	 * Exit a parse tree produced by {@link EventConfigurationLanguageParser#agendaItems}.
	 * @param ctx the parse tree
	 */
	void exitAgendaItems(EventConfigurationLanguageParser.AgendaItemsContext ctx);
	/**
	 * Enter a parse tree produced by {@link EventConfigurationLanguageParser#agendaItem}.
	 * @param ctx the parse tree
	 */
	void enterAgendaItem(EventConfigurationLanguageParser.AgendaItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link EventConfigurationLanguageParser#agendaItem}.
	 * @param ctx the parse tree
	 */
	void exitAgendaItem(EventConfigurationLanguageParser.AgendaItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link EventConfigurationLanguageParser#assistancesSection}.
	 * @param ctx the parse tree
	 */
	void enterAssistancesSection(EventConfigurationLanguageParser.AssistancesSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EventConfigurationLanguageParser#assistancesSection}.
	 * @param ctx the parse tree
	 */
	void exitAssistancesSection(EventConfigurationLanguageParser.AssistancesSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EventConfigurationLanguageParser#assistances}.
	 * @param ctx the parse tree
	 */
	void enterAssistances(EventConfigurationLanguageParser.AssistancesContext ctx);
	/**
	 * Exit a parse tree produced by {@link EventConfigurationLanguageParser#assistances}.
	 * @param ctx the parse tree
	 */
	void exitAssistances(EventConfigurationLanguageParser.AssistancesContext ctx);
	/**
	 * Enter a parse tree produced by {@link EventConfigurationLanguageParser#assistance}.
	 * @param ctx the parse tree
	 */
	void enterAssistance(EventConfigurationLanguageParser.AssistanceContext ctx);
	/**
	 * Exit a parse tree produced by {@link EventConfigurationLanguageParser#assistance}.
	 * @param ctx the parse tree
	 */
	void exitAssistance(EventConfigurationLanguageParser.AssistanceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PopulatedAssistanceArgs}
	 * labeled alternative in {@link EventConfigurationLanguageParser#assistanceArgs}.
	 * @param ctx the parse tree
	 */
	void enterPopulatedAssistanceArgs(EventConfigurationLanguageParser.PopulatedAssistanceArgsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PopulatedAssistanceArgs}
	 * labeled alternative in {@link EventConfigurationLanguageParser#assistanceArgs}.
	 * @param ctx the parse tree
	 */
	void exitPopulatedAssistanceArgs(EventConfigurationLanguageParser.PopulatedAssistanceArgsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EmptyAssistanceArgs}
	 * labeled alternative in {@link EventConfigurationLanguageParser#assistanceArgs}.
	 * @param ctx the parse tree
	 */
	void enterEmptyAssistanceArgs(EventConfigurationLanguageParser.EmptyAssistanceArgsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EmptyAssistanceArgs}
	 * labeled alternative in {@link EventConfigurationLanguageParser#assistanceArgs}.
	 * @param ctx the parse tree
	 */
	void exitEmptyAssistanceArgs(EventConfigurationLanguageParser.EmptyAssistanceArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link EventConfigurationLanguageParser#assistanceArg}.
	 * @param ctx the parse tree
	 */
	void enterAssistanceArg(EventConfigurationLanguageParser.AssistanceArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link EventConfigurationLanguageParser#assistanceArg}.
	 * @param ctx the parse tree
	 */
	void exitAssistanceArg(EventConfigurationLanguageParser.AssistanceArgContext ctx);
}