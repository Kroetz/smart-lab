package de.qaware.smartlabmeeting.repository.parser;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.assistance.IAssistanceInfo;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.data.meeting.AgendaItem;
import de.qaware.smartlabcore.data.meeting.IAgendaItem;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.Meeting;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.InvalidSyntaxException;
import de.qaware.smartlabmeeting.repository.parser.antlr.generated.MeetingConfigurationLanguageBaseVisitor;
import de.qaware.smartlabmeeting.repository.parser.antlr.generated.MeetingConfigurationLanguageLexer;
import de.qaware.smartlabmeeting.repository.parser.antlr.generated.MeetingConfigurationLanguageParser;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@Slf4j
public class MeetingParser implements IMeetingParser {

    private static final String CONFIG_TAG_BEGIN = "@@@smart-lab-config-begin";
    private static final String CONFIG_TAG_END = "@@@smart-lab-config-end";
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
        try {
            parseTree = parser.meetingConfiguration();
        }
        catch(ParseCancellationException e) {
            log.error("Could not parse the following string: {}", trimmed);
            throw new InvalidSyntaxException("The syntax of the meeting configuration must be valid", e);
        }
        return this.meetingConfigurationVisitor.visit(parseTree);
    }

    private String trimToRelevant(String stringToTrim) {
        Pattern pattern = Pattern.compile(PARSE_STRING_PATTERN, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(stringToTrim);
        if(!matcher.find()) return StringUtils.EMPTY;
        return matcher.group(0);
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
                    .collect(Collectors.toList());
            return Meeting.builder().agenda(agendaItems).build();
        }

        @Override
        public IMeeting visitAssistances(MeetingConfigurationLanguageParser.AssistancesContext ctx) {
            Set<IAssistanceConfiguration> configs = ctx.assistanceList
                    .stream()
                    .map(assistance -> assistance.accept(this.assistanceVisitor))
                    .collect(Collectors.toSet());
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
            return AgendaItem.builder()
                    .content(ctx.content.getText())
                    .build();
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
            String id = ctx.assistanceId.getText();
            Map<String, String> args = ctx.assistanceArgs().accept(this.assistanceArgsVisitor);
            // TODO: Better exception
            IAssistanceInfo assistance = this.assistanceInfoResolver.resolve(id).orElseThrow(RuntimeException::new);
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
            return new AbstractMap.SimpleEntry<>(argKey, argValue);
        }
    }
}
