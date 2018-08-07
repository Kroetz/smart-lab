package de.qaware.smartlab.data.set.factory;

import de.qaware.smartlab.core.data.device.DeviceId;
import de.qaware.smartlab.core.data.device.IDevice;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.PersonId;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.DataSetException;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public abstract class AbstractDataSetFactory implements IDataSetFactory {

    private final String id;
    protected final Instant timeBase;

    public AbstractDataSetFactory(String id) {
        this.id = id;
        this.timeBase = Instant.now();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public abstract Set<IWorkgroup> createWorkgroupSet() throws DataSetException;

    @Override
    public Map<WorkgroupId, IWorkgroup> createWorkgroupMap() throws DataSetException {
        Set<IWorkgroup> workgroups = createWorkgroupSet();
        return workgroups.stream()
                .collect(toMap(IWorkgroup::getId, workgroup -> workgroup));
    }

    @Override
    public abstract Set<IPerson> createWorkgroupMemberSet() throws DataSetException;

    @Override
    public Map<PersonId, IPerson> createWorkgroupMemberMap() throws DataSetException {
        Set<IPerson> workgroupMembers = createWorkgroupMemberSet();
        return workgroupMembers.stream()
                .collect(toMap(IPerson::getId, workgroupMember -> workgroupMember));
    }

    @Override
    public abstract Set<IEvent> createEventSet() throws DataSetException;

    @Override
    public Map<EventId, IEvent> createEventMap() throws DataSetException {
        Set<IEvent> events = createEventSet();
        return events.stream()
                .collect(toMap(IEvent::getId, event -> event));
    }

    @Override
    public abstract Set<ILocation> createLocationSet() throws DataSetException;

    @Override
    public Map<LocationId, ILocation> createLocationMap() throws DataSetException {
        Set<ILocation> locations = createLocationSet();
        return locations.stream()
                .collect(toMap(ILocation::getId, location -> location));
    }

    @Override
    public abstract Set<IDevice> createDeviceSet() throws DataSetException;

    @Override
    public Map<DeviceId, IDevice> createDeviceMap() throws DataSetException {
        Set<IDevice> devices = createDeviceSet();
        return devices.stream()
                .collect(toMap(IDevice::getId, device -> device));
    }
}
