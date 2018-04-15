package de.qaware.smartlabcore.entity.meeting;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcore.entity.meeting.assistance.IAssistanceDao;
import de.qaware.smartlabcore.entity.trigger.ITrigger;

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

    long getId();
    String getTitle();
    long getWorkgroupId();
    long getRoomId();
    List<IAgendaItem> getAgenda();
    Collection<IAssistanceDao> getAssistances();
    void setStart(Instant start);
    Instant getStart();
    void setEnd(Instant end);
    Instant getEnd();
    void triggerAssistances(ITrigger trigger);
    IMeeting copy();
}
