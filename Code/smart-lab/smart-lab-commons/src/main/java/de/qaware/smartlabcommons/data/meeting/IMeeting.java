package de.qaware.smartlabcommons.data.meeting;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcommons.Constants;
import de.qaware.smartlabcommons.data.IEntity;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IMeeting extends IEntity {

    String getTitle();
    String getWorkgroupId();
    String getRoomId();
    List<IAgendaItem> getAgenda();
    Set<String> getAssistanceIds();
    void setStart(Instant start);
    Instant getStart();
    void setEnd(Instant end);
    Instant getEnd();
    Duration getDuration();
    IMeeting copy();
}
