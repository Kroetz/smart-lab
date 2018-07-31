package de.qaware.smartlabsampledata.factory;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public abstract class AbstractSampleDataFactory implements ISampleDataFactory {

    Instant timeBase;

    public AbstractSampleDataFactory() {
        this.timeBase = Instant.now();
    }

    @Override
    public abstract Set<IWorkgroup> createWorkgroupSet();

    @Override
    public Map<WorkgroupId, IWorkgroup> createWorkgroupMap() {
        Set<IWorkgroup> workgroups = createWorkgroupSet();
        return workgroups.stream()
                .collect(toMap(IWorkgroup::getId, workgroup -> workgroup));
    }

    @Override
    public abstract Set<IPerson> createWorkgroupMemberSet();

    @Override
    public Map<PersonId, IPerson> createWorkgroupMemberMap() {
        Set<IPerson> workgroupMembers = createWorkgroupMemberSet();
        return workgroupMembers.stream()
                .collect(toMap(IPerson::getId, workgroupMember -> workgroupMember));
    }

    @Override
    public abstract Set<IMeeting> createMeetingSet();

    @Override
    public Map<MeetingId, IMeeting> createMeetingMap() {
        Set<IMeeting> meetings = createMeetingSet();
        return meetings.stream()
                .collect(toMap(IMeeting::getId, meeting -> meeting));
    }

    @Override
    public abstract Set<ILocation> createLocationSet();

    @Override
    public Map<LocationId, ILocation> createLocationMap() {
        Set<ILocation> locations = createLocationSet();
        return locations.stream()
                .collect(toMap(ILocation::getId, location -> location));
    }

    @Override
    public abstract Set<IDevice> createDeviceSet();

    @Override
    public Map<DeviceId, IDevice> createDeviceMap() {
        Set<IDevice> devices = createDeviceSet();
        return devices.stream()
                .collect(toMap(IDevice::getId, device -> device));
    }
}
