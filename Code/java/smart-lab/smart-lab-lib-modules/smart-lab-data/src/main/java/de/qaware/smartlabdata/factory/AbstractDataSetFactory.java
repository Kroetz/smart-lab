package de.qaware.smartlabdata.factory;

import de.qaware.smartlabcore.data.device.entity.Device;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.Location;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.Meeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.Person;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.Workgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.DataSetException;

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
    public abstract Set<Workgroup> createWorkgroupSet() throws DataSetException;

    @Override
    public Map<WorkgroupId, Workgroup> createWorkgroupMap() throws DataSetException {
        Set<Workgroup> workgroups = createWorkgroupSet();
        return workgroups.stream()
                .collect(toMap(IWorkgroup::getId, workgroup -> workgroup));
    }

    @Override
    public abstract Set<Person> createWorkgroupMemberSet() throws DataSetException;

    @Override
    public Map<PersonId, Person> createWorkgroupMemberMap() throws DataSetException {
        Set<Person> workgroupMembers = createWorkgroupMemberSet();
        return workgroupMembers.stream()
                .collect(toMap(IPerson::getId, workgroupMember -> workgroupMember));
    }

    @Override
    public abstract Set<Meeting> createMeetingSet() throws DataSetException;

    @Override
    public Map<MeetingId, Meeting> createMeetingMap() throws DataSetException {
        Set<Meeting> meetings = createMeetingSet();
        return meetings.stream()
                .collect(toMap(IMeeting::getId, meeting -> meeting));
    }

    @Override
    public abstract Set<Location> createLocationSet() throws DataSetException;

    @Override
    public Map<LocationId, Location> createLocationMap() throws DataSetException {
        Set<Location> locations = createLocationSet();
        return locations.stream()
                .collect(toMap(ILocation::getId, location -> location));
    }

    @Override
    public abstract Set<Device> createDeviceSet() throws DataSetException;

    @Override
    public Map<DeviceId, Device> createDeviceMap() throws DataSetException {
        Set<Device> devices = createDeviceSet();
        return devices.stream()
                .collect(toMap(IDevice::getId, device -> device));
    }
}
