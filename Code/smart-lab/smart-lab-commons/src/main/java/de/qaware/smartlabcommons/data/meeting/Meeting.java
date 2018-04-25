package de.qaware.smartlabcommons.data.meeting;


import de.qaware.smartlabcommons.data.meeting.assistance.IAssistanceDao;
import de.qaware.smartlabcommons.data.trigger.ITrigger;
import lombok.*;

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
    private Set<IAssistanceDao> assistances;
    private Instant start;
    private Instant end;

    @Override
    public void triggerAssistances(ITrigger trigger) {
        /*val room = roomService.findOne(getRoomId()).orElseThrow(IllegalStateException::new);
        val workgroup = workgroupService.findOne(getWorkgroupId()).orElseThrow(IllegalStateException::new);

        for (val assistance : getAssistances()) {

        }*/
    }

    @Override
    public Duration getDuration() {
        return Duration.between(getStart(), getEnd());
    }

    @Override
    public IMeeting copy() {
        return toBuilder().build();
    }
}
