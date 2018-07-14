// Generated from C:/Users/d.schock/Desktop/Masterarbeit/smart_lab/Code/smart-lab/smart-lab-lib-modules/smart-lab-meeting/src/main/antlr\MeetingConfigurationLanguage.g4 by ANTLR 4.7
package de.qaware.smartlabcore.parser.antlr.generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MeetingConfigurationLanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MeetingConfigurationLanguageVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MeetingConfigurationLanguageParser#meetingConfiguration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMeetingConfiguration(MeetingConfigurationLanguageParser.MeetingConfigurationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeetingConfigurationLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MeetingConfigurationLanguageParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WorkgroupAssignment}
	 * labeled alternative in {@link MeetingConfigurationLanguageParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWorkgroupAssignment(MeetingConfigurationLanguageParser.WorkgroupAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeetingConfigurationLanguageParser#section}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSection(MeetingConfigurationLanguageParser.SectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeetingConfigurationLanguageParser#agendaSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAgendaSection(MeetingConfigurationLanguageParser.AgendaSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeetingConfigurationLanguageParser#agendaItems}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAgendaItems(MeetingConfigurationLanguageParser.AgendaItemsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeetingConfigurationLanguageParser#agendaItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAgendaItem(MeetingConfigurationLanguageParser.AgendaItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeetingConfigurationLanguageParser#assistancesSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssistancesSection(MeetingConfigurationLanguageParser.AssistancesSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeetingConfigurationLanguageParser#assistances}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssistances(MeetingConfigurationLanguageParser.AssistancesContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeetingConfigurationLanguageParser#assistance}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssistance(MeetingConfigurationLanguageParser.AssistanceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PopulatedAssistanceArgs}
	 * labeled alternative in {@link MeetingConfigurationLanguageParser#assistanceArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPopulatedAssistanceArgs(MeetingConfigurationLanguageParser.PopulatedAssistanceArgsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EmptyAssistanceArgs}
	 * labeled alternative in {@link MeetingConfigurationLanguageParser#assistanceArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyAssistanceArgs(MeetingConfigurationLanguageParser.EmptyAssistanceArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeetingConfigurationLanguageParser#assistanceArg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssistanceArg(MeetingConfigurationLanguageParser.AssistanceArgContext ctx);
}