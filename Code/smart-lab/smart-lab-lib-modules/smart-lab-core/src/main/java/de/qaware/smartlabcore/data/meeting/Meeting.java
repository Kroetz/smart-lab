package de.qaware.smartlabcore.data.meeting;


import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

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
    private Set<String> assistanceIds;
    private Map<String, IAssistanceConfiguration> assistanceConfigurationsById;
    private Instant start;
    private Instant end;

    @Override
    public Duration getDuration() {
        return Duration.between(getStart(), getEnd());
    }

    @Override
    public Optional<IAssistanceConfiguration> getAssistanceConfiguration(String assistanceId) {
        return Optional.ofNullable(assistanceConfigurationsById.get(assistanceId));
    }

    @Override
    public IMeeting copy() {
        return toBuilder().build();
    }
}
