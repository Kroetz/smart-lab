// Generated from C:/Users/d.schock/Desktop/Masterarbeit/smart_lab/Code/java/smart-lab/smart-lab-lib-modules/smart-lab-core/src/main/antlr\EventConfigurationLanguage.g4 by ANTLR 4.7
package de.qaware.smartlab.core.parser.antlr.generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link EventConfigurationLanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface EventConfigurationLanguageVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link EventConfigurationLanguageParser#eventConfiguration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEventConfiguration(EventConfigurationLanguageParser.EventConfigurationContext ctx);
	/**
	 * Visit a parse tree produced by {@link EventConfigurationLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(EventConfigurationLanguageParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WorkgroupAssignment}
	 * labeled alternative in {@link EventConfigurationLanguageParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWorkgroupAssignment(EventConfigurationLanguageParser.WorkgroupAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link EventConfigurationLanguageParser#section}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSection(EventConfigurationLanguageParser.SectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EventConfigurationLanguageParser#agendaSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAgendaSection(EventConfigurationLanguageParser.AgendaSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EventConfigurationLanguageParser#agendaItems}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAgendaItems(EventConfigurationLanguageParser.AgendaItemsContext ctx);
	/**
	 * Visit a parse tree produced by {@link EventConfigurationLanguageParser#agendaItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAgendaItem(EventConfigurationLanguageParser.AgendaItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link EventConfigurationLanguageParser#assistancesSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssistancesSection(EventConfigurationLanguageParser.AssistancesSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EventConfigurationLanguageParser#assistances}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssistances(EventConfigurationLanguageParser.AssistancesContext ctx);
	/**
	 * Visit a parse tree produced by {@link EventConfigurationLanguageParser#assistance}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssistance(EventConfigurationLanguageParser.AssistanceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PopulatedAssistanceArgs}
	 * labeled alternative in {@link EventConfigurationLanguageParser#assistanceArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPopulatedAssistanceArgs(EventConfigurationLanguageParser.PopulatedAssistanceArgsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EmptyAssistanceArgs}
	 * labeled alternative in {@link EventConfigurationLanguageParser#assistanceArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyAssistanceArgs(EventConfigurationLanguageParser.EmptyAssistanceArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link EventConfigurationLanguageParser#assistanceArg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssistanceArg(EventConfigurationLanguageParser.AssistanceArgContext ctx);
}