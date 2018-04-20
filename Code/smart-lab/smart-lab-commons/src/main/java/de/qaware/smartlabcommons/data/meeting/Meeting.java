package de.qaware.smartlabcommons.data.meeting;


import de.qaware.smartlabcommons.data.meeting.assistance.IAssistanceDao;
import de.qaware.smartlabcommons.data.trigger.ITrigger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

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
    private Collection<IAssistanceDao> assistances;
    private Instant start;
    private Instant end;

    @Override
    public void triggerAssistances(ITrigger trigger) {
        /*val room = roomService.getRoom(getRoomId()).orElseThrow(IllegalStateException::new);
        val workgroup = workgroupService.getWorkgroup(getWorkgroupId()).orElseThrow(IllegalStateException::new);

        for (val assistance : getAssistances()) {

        }*/
    }

    @Override
    public IMeeting copy() {
        return toBuilder().build();
    }
}
