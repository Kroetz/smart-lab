package de.qaware.smartlabcommons.data.meeting;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcommons.data.meeting.assistance.IAssistanceDao;
import de.qaware.smartlabcommons.data.trigger.ITrigger;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Meeting.class)
})
public interface IMeeting {

    String getId();
    String getTitle();
    String getWorkgroupId();
    String getRoomId();
    List<IAgendaItem> getAgenda();
    Collection<IAssistanceDao> getAssistances();
    void setStart(Instant start);
    Instant getStart();
    void setEnd(Instant end);
    Instant getEnd();
    Duration getDuration();
    void triggerAssistances(ITrigger trigger);
    IMeeting copy();
}
