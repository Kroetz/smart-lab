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
    private RoomId roomId;
    private List<IAgendaItem> agenda;
    private Set<IAssistanceConfiguration> assistanceConfigurations;
    private Instant start;
    private Instant end;

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
                .roomId(getMergedFieldValue(meeting, IMeeting::getRoomId))
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
                    String.format(
                            "The meetings have conflicting fields with the values %s and %s",
                            fieldValue1,
                            fieldValue2));
        }
    }
}
