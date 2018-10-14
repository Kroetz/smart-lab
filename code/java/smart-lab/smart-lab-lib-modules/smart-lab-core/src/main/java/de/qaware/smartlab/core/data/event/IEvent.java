package de.qaware.smartlab.core.data.event;

import de.qaware.smartlab.core.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface IEvent extends IEntity<EventId> {

    String getTitle();
    WorkgroupId getWorkgroupId();
    LocationId getLocationId();
    List<IAgendaItem> getAgenda();
    Set<IAssistanceConfiguration> getAssistanceConfigurations();
    Instant getStart();
    Instant getEnd();
    Duration getDuration();
    boolean isInProgress();
    boolean isColliding(IEvent event);
    IEvent withEnd(Instant newEnd);
    IEvent withStartAndEnd(Instant newStart, Instant newEnd);
    IEvent merge(IEvent event);
    String toConfigLangString();
}
