package de.qaware.smartlabcommons.data.meeting;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Meeting implements IMeeting {

    private String id;
    private String title;
    private String workgroupId;
    private String roomId;
    private List<IAgendaItem> agenda;
    private Set<String> assistanceIds;
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
}
