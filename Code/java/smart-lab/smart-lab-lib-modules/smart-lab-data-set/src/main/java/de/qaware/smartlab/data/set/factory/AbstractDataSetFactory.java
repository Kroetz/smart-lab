package de.qaware.smartlab.data.set.factory;

import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.PersonId;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.data.DataException;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public abstract class AbstractDataSetFactory implements IDataSetFactory {

    private final String id;
    protected final Instant timeBase;

    protected AbstractDataSetFactory(String id) {
        this.id = id;
        this.timeBase = Instant.now();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public abstract Set<IWorkgroup> createWorkgroupSet() throws DataException;

    @Override
    public Map<WorkgroupId, IWorkgroup> createWorkgroupMap() throws DataException {
        Set<IWorkgroup> workgroups = createWorkgroupSet();
        return workgroups.stream()
                .collect(toMap(IWorkgroup::getId, workgroup -> workgroup));
    }

    @Override
    public abstract Set<IPerson> createWorkgroupMemberSet() throws DataException;

    @Override
    public Map<PersonId, IPerson> createWorkgroupMemberMap() throws DataException {
        Set<IPerson> workgroupMembers = createWorkgroupMemberSet();
        return workgroupMembers.stream()
                .collect(toMap(IPerson::getId, workgroupMember -> workgroupMember));
    }

    @Override
    public abstract Set<IEvent> createEventSet() throws DataException;

    @Override
    public Map<EventId, IEvent> createEventMap() throws DataException {
        Set<IEvent> events = createEventSet();
        return events.stream()
                .collect(toMap(IEvent::getId, event -> event));
    }

    @Override
    public abstract Set<ILocation> createLocationSet() throws DataException;

    @Override
    public Map<LocationId, ILocation> createLocationMap() throws DataException {
        Set<ILocation> locations = createLocationSet();
        return locations.stream()
                .collect(toMap(ILocation::getId, location -> location));
    }

    @Override
    public abstract Set<IActuator> createActuatorSet() throws DataException;

    @Override
    public Map<ActuatorId, IActuator> createActuatorMap() throws DataException {
        Set<IActuator> actuators = createActuatorSet();
        return actuators.stream()
                .collect(toMap(IActuator::getId, actuator -> actuator));
    }
}
