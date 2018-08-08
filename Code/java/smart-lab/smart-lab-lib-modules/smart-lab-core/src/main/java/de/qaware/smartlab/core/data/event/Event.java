package de.qaware.smartlab.core.data.event;


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

import static de.qaware.smartlab.core.miscellaneous.EventConfigurationLanguage.*;
import static de.qaware.smartlab.core.miscellaneous.StringUtils.*;
import static de.qaware.smartlab.core.miscellaneous.TimeUtils.isNowInProgress;
import static java.lang.String.format;
import static java.time.Duration.between;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
@EqualsAndHashCode
@Slf4j
public class Event implements IEvent {

    private final EventId id;
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
        return between(getStart(), getEnd());
    }

    @Override
    public boolean isInProgress() {
        return isNowInProgress(this.start, this.end);
    }

    @Override
    public IEvent withEnd(Instant newEnd) {
        return Event.of(
                this.id,
                this.title,
                this.workgroupId,
                this.agenda,
                this.assistanceConfigurations,
                this.start,
                newEnd);
    }

    @Override
    public IEvent withStartAndEnd(Instant newStart, Instant newEnd) {
        return Event.of(
                this.id,
                this.title,
                this.workgroupId,
                this.agenda,
                this.assistanceConfigurations,
                newStart,
                newEnd);
    }

    @Override
    public boolean isColliding(IEvent event) {
        return (this.getLocationId().equals(event.getLocationId())
                && (this.getStart().equals(event.getStart()) && this.getEnd().equals(event.getEnd())
                || this.getStart().isAfter(event.getStart()) && this.getStart().isBefore(event.getEnd())
                || this.getEnd().isAfter(event.getStart()) && this.getEnd().isBefore(event.getEnd())
                || event.getStart().isAfter(this.getStart()) && event.getStart().isBefore(this.getEnd())
                || event.getEnd().isAfter(this.getStart()) && event.getEnd().isBefore(this.getEnd())));
    }

    @Override
    public IEvent merge(IEvent event) throws IllegalArgumentException {
        return Event.of(
                getMergedFieldValue(event, IEntity::getId),
                getMergedFieldValue(event, IEvent::getTitle),
                getMergedFieldValue(event, IEvent::getWorkgroupId),
                getMergedFieldValue(event, IEvent::getAgenda),
                getMergedFieldValue(event, IEvent::getAssistanceConfigurations),
                getMergedFieldValue(event, IEvent::getStart),
                getMergedFieldValue(event, IEvent::getEnd));
    }

    private <FieldT> FieldT getMergedFieldValue(IEvent event, Function<IEvent, FieldT> fieldGetter) throws IllegalArgumentException {
        requireNonConflictingField(event, fieldGetter);
        FieldT fieldValue1 = fieldGetter.apply(this);
        FieldT fieldValue2 = fieldGetter.apply(event);
        return isNull(fieldValue1) ? fieldValue2 : fieldValue1;
    }

    private void requireNonConflictingField(IEvent event, Function<IEvent, ?> fieldGetter) {
        Object fieldValue1 = fieldGetter.apply(this);
        Object fieldValue2 = fieldGetter.apply(event);
        if((nonNull(fieldValue1) && nonNull(fieldValue2)) && !fieldValue1.equals(fieldValue2)) {
            throw new IllegalArgumentException(
                    format(
                            "The events have conflicting fields with the values %s and %s",
                            fieldValue1,
                            fieldValue2));
        }
    }

    @Override
    public String toConfigLangString() {
        // TODO: This method currently only converts those fields into strings that are stored as a parsable string in the description field of Google calendar events.
        /*
        Depending on their capabilities other event-management repositories (other than Google calendar) may need a
        greater part of the event entity to be stored as a parsable string instead of data fields. In this case this
        method and the ANTLR grammar of the event configuration language must be further extended to support
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

    public static Event newInstance() {
        return Event.builder().build();
    }

    public static Event of(
            EventId id,
            String title,
            WorkgroupId workgroupId,
            List<IAgendaItem> agenda,
            Set<IAssistanceConfiguration> assistanceConfigurations,
            Instant start,
            Instant end) {
        return new Event(id, title, workgroupId, agenda, assistanceConfigurations, start, end);
    }
}
