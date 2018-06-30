package de.qaware.smartlabcore.data.meeting;


import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static de.qaware.smartlabcore.miscellaneous.MeetingConfigurationLanguage.*;
import static de.qaware.smartlabcore.miscellaneous.StringUtils.*;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Meeting implements IMeeting {

    private MeetingId id;
    private String title;
    private WorkgroupId workgroupId;
    private List<IAgendaItem> agenda;
    private Set<IAssistanceConfiguration> assistanceConfigurations;
    private Instant start;
    private Instant end;

    @Override
    public RoomId getRoomId() {
        return this.id.getLocationIdPart();
    }

    @Override
    public Duration getDuration() {
        return Duration.between(getStart(), getEnd());
    }

    @Override
    public IMeeting copy() {
        return toBuilder().build();
    }

    @Override
    public IMeeting merge(IMeeting meeting) throws IllegalArgumentException {
        return Meeting.builder()
                .id(getMergedFieldValue(meeting, IEntity::getId))
                .title(getMergedFieldValue(meeting, IMeeting::getTitle))
                .workgroupId(getMergedFieldValue(meeting, IMeeting::getWorkgroupId))
                .agenda(getMergedFieldValue(meeting, IMeeting::getAgenda))
                .assistanceConfigurations(getMergedFieldValue(meeting, IMeeting::getAssistanceConfigurations))
                .start(getMergedFieldValue(meeting, IMeeting::getStart))
                .end(getMergedFieldValue(meeting, IMeeting::getEnd))
                .build();
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

    private StringBuilder appendWorkgroupId(StringBuilder configLangBuilder) {
        if(nonNull(this.workgroupId)) {
            configLangBuilder
                    .append(TAB)
                    .append(WORKGROUP_TAG)
                    .append(EQUALS)
                    .append(format(DOUBLE_QUOTED_TEMPLATE, this.workgroupId.getIdValue()))
                    .append(NEW_LINE);
        }
        return configLangBuilder;
    }

    private StringBuilder appendAgenda(StringBuilder configLangBuilder) {
        if(nonNull(this.agenda)) {
            configLangBuilder.append(TAB).append(AGENDA_TAG_BEGIN).append(NEW_LINE);
            for(IAgendaItem agendaItem : this.agenda) {
                configLangBuilder.append(agendaItem.toConfigLangString());
            }
            configLangBuilder.append(TAB).append(AGENDA_TAG_END).append(NEW_LINE);
        }
        return configLangBuilder;
    }

    private StringBuilder appendAssistanceConfigurations(StringBuilder configLangBuilder) {
        if(nonNull(this.assistanceConfigurations)) {
            configLangBuilder.append(TAB).append(ASSISTANCES_TAG_BEGIN).append(NEW_LINE);
            for(IAssistanceConfiguration config : this.assistanceConfigurations) {
                configLangBuilder.append(config.toConfigLangString());
            }
            configLangBuilder.append(TAB).append(ASSISTANCES_TAG_END).append(NEW_LINE);
        }
        return configLangBuilder;
    }
}
