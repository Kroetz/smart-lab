package de.qaware.smartlab.event.management.service.repository.parser;

import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.core.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.event.AgendaItem;
import de.qaware.smartlab.core.data.event.Event;
import de.qaware.smartlab.core.data.event.IAgendaItem;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.resolver.IResolver;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.parser.InvalidSyntaxException;
import de.qaware.smartlab.core.parser.antlr.generated.EventConfigurationLanguageBaseVisitor;
import de.qaware.smartlab.core.parser.antlr.generated.EventConfigurationLanguageLexer;
import de.qaware.smartlab.core.parser.antlr.generated.EventConfigurationLanguageParser;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.qaware.smartlab.core.constant.EventConfigurationLanguage.CONFIG_TAG_BEGIN;
import static de.qaware.smartlab.core.constant.EventConfigurationLanguage.CONFIG_TAG_END;
import static de.qaware.smartlab.core.util.StringUtils.EMPTY;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
@Slf4j
public class EventParser implements IEventParser {

    private static final String PARSE_STRING_PATTERN = CONFIG_TAG_BEGIN + ".*" + CONFIG_TAG_END;

    private final EventConfigurationVisitor eventConfigurationVisitor;

    public EventParser(EventConfigurationVisitor eventConfigurationVisitor) {
        this.eventConfigurationVisitor = eventConfigurationVisitor;
    }

    public IEvent parse(String stringToParse) throws InvalidSyntaxException {
        String trimmed = trimToRelevant(stringToParse);
        CharStream charStream =
                CharStreams.fromString(trimmed);
        EventConfigurationLanguageLexer lexer =
                new EventConfigurationLanguageLexer(charStream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        EventConfigurationLanguageParser parser =
                new EventConfigurationLanguageParser(tokenStream);
        parser.setErrorHandler(new BailErrorStrategy());
        ParseTree parseTree;
        IEvent event;
        try {
            parseTree = parser.eventConfiguration();
            event = this.eventConfigurationVisitor.visit(parseTree);
        }
        catch(ParseCancellationException e) {
            log.error("Could not parse the following string: {}", trimmed);
            throw new InvalidSyntaxException("The syntax of the event configuration must be valid", e);
        }
        return isNull(event) ? Event.newInstance() : event;
    }

    private String trimToRelevant(String stringToTrim) {
        Pattern pattern = Pattern.compile(PARSE_STRING_PATTERN, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(stringToTrim);
        if(!matcher.find()) return EMPTY;
        return matcher.group(0);
    }

    @Override
    public String unparse(IEvent event) {
        return event.toConfigLangString();
    }

    @Component
    @Slf4j
    private static class EventConfigurationVisitor extends EventConfigurationLanguageBaseVisitor<IEvent> {

        private final AgendaItemVisitor agendaItemVisitor;
        private final AssistanceVisitor assistanceVisitor;

        private EventConfigurationVisitor(
                AgendaItemVisitor agendaItemVisitor,
                AssistanceVisitor assistanceVisitor) {
            this.agendaItemVisitor = agendaItemVisitor;
            this.assistanceVisitor = assistanceVisitor;
        }

        @Override
        public IEvent visitEventConfiguration(EventConfigurationLanguageParser.EventConfigurationContext ctx) {
            return super.visitEventConfiguration(ctx);
        }

        @Override
        public IEvent visitWorkgroupAssignment(EventConfigurationLanguageParser.WorkgroupAssignmentContext ctx) {
            return Event.builder()
                    .workgroupId(WorkgroupId.of(ctx.workgroupId.getText()))
                    .build();
        }

        @Override
        public IEvent visitAgendaItems(EventConfigurationLanguageParser.AgendaItemsContext ctx) {
            List<IAgendaItem> agendaItems = ctx.agendaItemList
                    .stream()
                    .map(agendaItem -> agendaItem.accept(this.agendaItemVisitor))
                    .collect(toList());
            return Event.builder().agenda(agendaItems).build();
        }

        @Override
        public IEvent visitAssistances(EventConfigurationLanguageParser.AssistancesContext ctx) {
            Set<IAssistanceConfiguration> configs = ctx.assistanceList
                    .stream()
                    .map(assistance -> assistance.accept(this.assistanceVisitor))
                    .collect(toSet());
            return Event.builder()
                    .assistanceConfigurations(configs)
                    .build();
        }

        @Override
        protected IEvent aggregateResult(IEvent aggregate, IEvent nextResult) {
            if(isNull(aggregate)) return nextResult;
            if(isNull(nextResult)) return aggregate;
            return aggregate.merge(nextResult);
        }
    }

    @Component
    @Slf4j
    private static class AgendaItemVisitor extends EventConfigurationLanguageBaseVisitor<IAgendaItem> {

        @Override
        public IAgendaItem visitAgendaItem(EventConfigurationLanguageParser.AgendaItemContext ctx) {
            return AgendaItem.of(ctx.content.getText());
        }
    }

    @Component
    @Slf4j
    private static class AssistanceVisitor extends EventConfigurationLanguageBaseVisitor<IAssistanceConfiguration> {

        private final IResolver<String, IAssistanceInfo> assistanceInfoResolver;
        private final AssistanceArgsVisitor assistanceArgsVisitor;

        private AssistanceVisitor(
                IResolver<String, IAssistanceInfo> assistanceInfoResolver,
                AssistanceArgsVisitor assistanceArgsVisitor) {
            this.assistanceInfoResolver = assistanceInfoResolver;
            this.assistanceArgsVisitor = assistanceArgsVisitor;
        }

        @Override
        public IAssistanceConfiguration visitAssistance(EventConfigurationLanguageParser.AssistanceContext ctx) {
            String id = ctx.assistanceCommand.getText();
            Map<String, String> args = ctx.assistanceArgs().accept(this.assistanceArgsVisitor);
            IAssistanceInfo assistance = this.assistanceInfoResolver.resolve(id);
            return assistance.createConfiguration(args);
        }
    }

    @Component
    @Slf4j
    private static class AssistanceArgsVisitor extends EventConfigurationLanguageBaseVisitor<Map<String, String>> {

        private final AssistanceArgVisitor assistanceArgVisitor;

        private AssistanceArgsVisitor(AssistanceArgVisitor assistanceArgVisitor) {
            this.assistanceArgVisitor = assistanceArgVisitor;
        }

        @Override
        public Map<String, String> visitPopulatedAssistanceArgs(EventConfigurationLanguageParser.PopulatedAssistanceArgsContext ctx) {
            final Map<String, String> assistanceArgs = new HashMap<>();
            ctx.assistanceArgList
                    .stream()
                    .map(assistanceArg -> assistanceArg.accept(this.assistanceArgVisitor))
                    .forEach(assistanceArg -> assistanceArgs.put(assistanceArg.getKey(), assistanceArg.getValue()));
            return assistanceArgs;
        }
    }

    @Component
    @Slf4j
    private static class AssistanceArgVisitor extends EventConfigurationLanguageBaseVisitor<Map.Entry<String, String>> {

        @Override
        public Map.Entry<String, String> visitAssistanceArg(EventConfigurationLanguageParser.AssistanceArgContext ctx) {
            String argKey = ctx.argKey.getText();
            String argValue = ctx.argValue.getText();
            return new AbstractMap.SimpleImmutableEntry<>(argKey, argValue);
        }
    }
}
