package de.qaware.smartlab.core.data.meeting;


import de.qaware.smartlab.core.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static de.qaware.smartlab.core.miscellaneous.MeetingConfigurationLanguage.*;
import static de.qaware.smartlab.core.miscellaneous.StringUtils.*;
import static de.qaware.smartlab.core.miscellaneous.TimeUtils.isNowInProgress;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
@EqualsAndHashCode
@Slf4j
public class Meeting implements IMeeting {

    private final MeetingId id;
    private final String title;
    private final WorkgroupId workgroupId;
    private final List<IAgendaItem> agenda;
    private final Set<IAssistanceConfiguration> assistanceConfigurations;
    private final Instant start;
    private final Instant end;

    @Override
    public LocationId getLocationId() {
        return this.id.getLocationIdPart();
    }

    @Override
    public Duration getDuration() {
        return Duration.between(getStart(), getEnd());
    }

    @Override
    public boolean isInProgress() {
        return isNowInProgress(this.start, this.end);
    }

    @Override
    public IMeeting withEnd(Instant newEnd) {
        return Meeting.of(
                this.id,
                this.title,
                this.workgroupId,
                this.agenda,
                this.assistanceConfigurations,
                this.start,
                newEnd);
    }

    @Override
    public IMeeting withStartAndEnd(Instant newStart, Instant newEnd) {
        return Meeting.of(
                this.id,
                this.title,
                this.workgroupId,
                this.agenda,
                this.assistanceConfigurations,
                newStart,
                newEnd);
    }

    @Override
    public boolean isColliding(IMeeting meeting) {
        return (this.getLocationId().equals(meeting.getLocationId())
                && (this.getStart().equals(meeting.getStart()) && this.getEnd().equals(meeting.getEnd())
                || this.getStart().isAfter(meeting.getStart()) && this.getStart().isBefore(meeting.getEnd())
                || this.getEnd().isAfter(meeting.getStart()) && this.getEnd().isBefore(meeting.getEnd())
                || meeting.getStart().isAfter(this.getStart()) && meeting.getStart().isBefore(this.getEnd())
                || meeting.getEnd().isAfter(this.getStart()) && meeting.getEnd().isBefore(this.getEnd())));
    }

    @Override
    public IMeeting merge(IMeeting meeting) throws IllegalArgumentException {
        return Meeting.of(
                getMergedFieldValue(meeting, IEntity::getId),
                getMergedFieldValue(meeting, IMeeting::getTitle),
                getMergedFieldValue(meeting, IMeeting::getWorkgroupId),
                getMergedFieldValue(meeting, IMeeting::getAgenda),
                getMergedFieldValue(meeting, IMeeting::getAssistanceConfigurations),
                getMergedFieldValue(meeting, IMeeting::getStart),
                getMergedFieldValue(meeting, IMeeting::getEnd));
    }

    private <FieldT> FieldT getMergedFieldValue(IMeeting meeting, Function<IMeeting, FieldT> fieldGetter) throws IllegalArgumentException {
        requireNonConflictingField(meeting, fieldGetter);
        FieldT fieldValue1 = fieldGetter.apply(this);
        FieldT fieldValue2 = fieldGetter.apply(meeting);
        return isNull(fieldValue1) ? fieldValue2 : fieldValue1;
    }

    private void requireNonConflictingField(IMeeting meeting, Function<IMeeting, ?> fieldGetter) {
        Object fieldValue1 = fieldGetter.apply(this);
        Object fieldValue2 = fieldGetter.apply(meeting);
        if((nonNull(fieldValue1) && nonNull(fieldValue2)) && !fieldValue1.equals(fieldValue2)) {
            throw new IllegalArgumentException(
                    format(
                            "The meetings have conflicting fields with the values %s and %s",
                            fieldValue1,
                            fieldValue2));
        }
    }

    @Override
    public String toConfigLangString() {
        // TODO: This method currently only converts those fields into strings that are stored as a parsable string in the description field of Google calendar events.
        /*
        Depending on their capabilities other meeting management repositories (other than Google calendar) may need a
        greater part of the meeting entity to be stored as a parsable string instead of data fields. In this case this
        method and the ANTLR grammar of the meeting configuration language must be further extended to support
        parsing/unparsing of those additional fields.
         */
        StringBuilder configLangBuilder = new StringBuilder();
        configLangBuilder.append(CONFIG_TAG_BEGIN).append(NEW_LINE);
        appendWorkgroupId(configLangBuilder);
        appendAgenda(configLangBuilder);
        appendAssistanceConfigurations(configLangBuilder);
        configLangBuilder.append(CONFIG_TAG_END);
        return configLangBuilder.toString();
    }

    private void appendWorkgroupId(StringBuilder configLangBuilder) {
        if(nonNull(this.workgroupId)) {
            configLangBuilder
                    .append(TAB)
                    .append(WORKGROUP_TAG)
                    .append(EQUALS)
                    .append(format(DOUBLE_QUOTED_TEMPLATE, this.workgroupId.getIdValue()))
                    .append(NEW_LINE);
        }
    }

    private void appendAgenda(StringBuilder configLangBuilder) {
        if(nonNull(this.agenda)) {
            configLangBuilder.append(TAB).append(AGENDA_TAG_BEGIN).append(NEW_LINE);
            for(IAgendaItem agendaItem : this.agenda) {
                configLangBuilder.append(agendaItem.toConfigLangString());
            }
            configLangBuilder.append(TAB).append(AGENDA_TAG_END).append(NEW_LINE);
        }
    }

    private void appendAssistanceConfigurations(StringBuilder configLangBuilder) {
        if(nonNull(this.assistanceConfigurations)) {
            configLangBuilder.append(TAB).append(ASSISTANCES_TAG_BEGIN).append(NEW_LINE);
            for(IAssistanceConfiguration config : this.assistanceConfigurations) {
                configLangBuilder.append(config.toConfigLangString());
            }
            configLangBuilder.append(TAB).append(ASSISTANCES_TAG_END).append(NEW_LINE);
        }
    }

    public static Meeting newInstance() {
        return Meeting.builder().build();
    }

    public static Meeting of(
            MeetingId id,
            String title,
            WorkgroupId workgroupId,
            List<IAgendaItem> agenda,
            Set<IAssistanceConfiguration> assistanceConfigurations,
            Instant start,
            Instant end) {
        return new Meeting(id, title, workgroupId, agenda, assistanceConfigurations, start, end);
    }
}
