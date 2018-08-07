package de.qaware.smartlab.core.data.meeting;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlab.core.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.miscellaneous.Constants;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IMeeting extends IEntity<MeetingId> {

    String getTitle();
    WorkgroupId getWorkgroupId();
    LocationId getLocationId();
    List<IAgendaItem> getAgenda();
    Set<IAssistanceConfiguration> getAssistanceConfigurations();
    Instant getStart();
    Instant getEnd();
    Duration getDuration();
    boolean isInProgress();
    boolean isColliding(IMeeting meeting);
    IMeeting withEnd(Instant newEnd);
    IMeeting withStartAndEnd(Instant newStart, Instant newEnd);
    IMeeting merge(IMeeting meeting);
    String toConfigLangString();
}
