package de.qaware.smartlab.event.management.service.repository.parser;

import de.qaware.smartlab.core.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.core.data.generic.IResolver;
import de.qaware.smartlab.core.data.meeting.AgendaItem;
import de.qaware.smartlab.core.data.meeting.IAgendaItem;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.data.meeting.Meeting;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.InvalidSyntaxException;
import de.qaware.smartlab.core.parser.antlr.generated.MeetingConfigurationLanguageBaseVisitor;
import de.qaware.smartlab.core.parser.antlr.generated.MeetingConfigurationLanguageLexer;
import de.qaware.smartlab.core.parser.antlr.generated.MeetingConfigurationLanguageParser;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.qaware.smartlab.core.miscellaneous.MeetingConfigurationLanguage.CONFIG_TAG_BEGIN;
import static de.qaware.smartlab.core.miscellaneous.MeetingConfigurationLanguage.CONFIG_TAG_END;
import static de.qaware.smartlab.core.miscellaneous.StringUtils.EMPTY;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
@Slf4j
public class MeetingParser implements IMeetingParser {

    private static final String PARSE_STRING_PATTERN = CONFIG_TAG_BEGIN + ".*" + CONFIG_TAG_END;

    private final MeetingConfigurationVisitor meetingConfigurationVisitor;

    public MeetingParser(MeetingConfigurationVisitor meetingConfigurationVisitor) {
        this.meetingConfigurationVisitor = meetingConfigurationVisitor;
    }

    public IMeeting parse(String stringToParse) throws InvalidSyntaxException {
        String trimmed = trimToRelevant(stringToParse);
        CharStream charStream =
                CharStreams.fromString(trimmed);
        MeetingConfigurationLanguageLexer lexer =
                new MeetingConfigurationLanguageLexer(charStream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        MeetingConfigurationLanguageParser parser =
                new MeetingConfigurationLanguageParser(tokenStream);
        parser.setErrorHandler(new BailErrorStrategy());
        ParseTree parseTree;
        IMeeting meeting;
        try {
            parseTree = parser.meetingConfiguration();
            meeting = this.meetingConfigurationVisitor.visit(parseTree);
        }
        catch(ParseCancellationException e) {
            log.error("Could not parse the following string: {}", trimmed);
            throw new InvalidSyntaxException("The syntax of the meeting configuration must be valid", e);
        }
        return isNull(meeting) ? Meeting.newInstance() : meeting;
    }

    private String trimToRelevant(String stringToTrim) {
        Pattern pattern = Pattern.compile(PARSE_STRING_PATTERN, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(stringToTrim);
        if(!matcher.find()) return EMPTY;
        return matcher.group(0);
    }

    @Override
    public String unparse(IMeeting meeting) {
        return meeting.toConfigLangString();
    }

    @Component
    @Slf4j
    private static class MeetingConfigurationVisitor extends MeetingConfigurationLanguageBaseVisitor<IMeeting> {

        private final AgendaItemVisitor agendaItemVisitor;
        private final AssistanceVisitor assistanceVisitor;

        private MeetingConfigurationVisitor(
                AgendaItemVisitor agendaItemVisitor,
                AssistanceVisitor assistanceVisitor) {
            this.agendaItemVisitor = agendaItemVisitor;
            this.assistanceVisitor = assistanceVisitor;
        }

        @Override
        public IMeeting visitMeetingConfiguration(MeetingConfigurationLanguageParser.MeetingConfigurationContext ctx) {
            return super.visitMeetingConfiguration(ctx);
        }

        @Override
        public IMeeting visitWorkgroupAssignment(MeetingConfigurationLanguageParser.WorkgroupAssignmentContext ctx) {
            return Meeting.builder()
                    .workgroupId(WorkgroupId.of(ctx.workgroupId.getText()))
                    .build();
        }

        @Override
        public IMeeting visitAgendaItems(MeetingConfigurationLanguageParser.AgendaItemsContext ctx) {
            List<IAgendaItem> agendaItems = ctx.agendaItemList
                    .stream()
                    .map(agendaItem -> agendaItem.accept(this.agendaItemVisitor))
                    .collect(toList());
            return Meeting.builder().agenda(agendaItems).build();
        }

        @Override
        public IMeeting visitAssistances(MeetingConfigurationLanguageParser.AssistancesContext ctx) {
            Set<IAssistanceConfiguration> configs = ctx.assistanceList
                    .stream()
                    .map(assistance -> assistance.accept(this.assistanceVisitor))
                    .collect(toSet());
            return Meeting.builder()
                    .assistanceConfigurations(configs)
                    .build();
        }

        @Override
        protected IMeeting aggregateResult(IMeeting aggregate, IMeeting nextResult) {
            if(isNull(aggregate)) return nextResult;
            if(isNull(nextResult)) return aggregate;
            return aggregate.merge(nextResult);
        }
    }

    @Component
    @Slf4j
    private static class AgendaItemVisitor extends MeetingConfigurationLanguageBaseVisitor<IAgendaItem> {

        @Override
        public IAgendaItem visitAgendaItem(MeetingConfigurationLanguageParser.AgendaItemContext ctx) {
            return AgendaItem.of(ctx.content.getText());
        }
    }

    @Component
    @Slf4j
    private static class AssistanceVisitor extends MeetingConfigurationLanguageBaseVisitor<IAssistanceConfiguration> {

        private final IResolver<String, IAssistanceInfo> assistanceInfoResolver;
        private final AssistanceArgsVisitor assistanceArgsVisitor;

        private AssistanceVisitor(
                IResolver<String, IAssistanceInfo> assistanceInfoResolver,
                AssistanceArgsVisitor assistanceArgsVisitor) {
            this.assistanceInfoResolver = assistanceInfoResolver;
            this.assistanceArgsVisitor = assistanceArgsVisitor;
        }

        @Override
        public IAssistanceConfiguration visitAssistance(MeetingConfigurationLanguageParser.AssistanceContext ctx) {
            String id = ctx.assistanceCommand.getText();
            Map<String, String> args = ctx.assistanceArgs().accept(this.assistanceArgsVisitor);
            IAssistanceInfo assistance = this.assistanceInfoResolver.resolve(id);
            return assistance.createConfiguration(args);
        }
    }

    @Component
    @Slf4j
    private static class AssistanceArgsVisitor extends MeetingConfigurationLanguageBaseVisitor<Map<String, String>> {

        private final AssistanceArgVisitor assistanceArgVisitor;

        private AssistanceArgsVisitor(AssistanceArgVisitor assistanceArgVisitor) {
            this.assistanceArgVisitor = assistanceArgVisitor;
        }

        @Override
        public Map<String, String> visitPopulatedAssistanceArgs(MeetingConfigurationLanguageParser.PopulatedAssistanceArgsContext ctx) {
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
    private static class AssistanceArgVisitor extends MeetingConfigurationLanguageBaseVisitor<Map.Entry<String, String>> {

        @Override
        public Map.Entry<String, String> visitAssistanceArg(MeetingConfigurationLanguageParser.AssistanceArgContext ctx) {
            String argKey = ctx.argKey.getText();
            String argValue = ctx.argValue.getText();
            return new AbstractMap.SimpleImmutableEntry<>(argKey, argValue);
        }
    }
}