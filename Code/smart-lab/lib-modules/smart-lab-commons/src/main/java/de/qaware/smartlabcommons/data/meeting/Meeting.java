package de.qaware.smartlabcommons.data.meeting;


import de.qaware.smartlabcommons.data.assistance.IAssistanceConfiguration;
import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

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
