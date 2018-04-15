package de.qaware.smartlabcommons.data.meeting;


import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlabcommons.data.meeting.assistance.IAssistanceDao;
import de.qaware.smartlabcommons.data.trigger.ITrigger;
import lombok.*;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Meeting implements IMeeting {

    // Setting this field manually is needed due to a Jackson bug with Java Optionals (see https://stackoverflow.com/questions/49071166/jackson-java-util-optional-serialization-does-not-include-type-id)
    @JsonProperty
    private String type = this.getClass().getName();

    private long id;
    private String title;
    private long workgroupId;
    private long roomId;
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
