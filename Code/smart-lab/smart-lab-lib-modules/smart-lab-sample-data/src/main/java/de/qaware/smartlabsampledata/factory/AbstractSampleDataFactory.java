package de.qaware.smartlabsampledata.factory;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
                .collect(Collectors.toMap(IWorkgroup::getId, workgroup -> workgroup));
    }

    @Override
    public abstract Set<IPerson> createWorkgroupMemberSet();

    @Override
    public Map<PersonId, IPerson> createWorkgroupMemberMap() {
        Set<IPerson> workgroupMembers = createWorkgroupMemberSet();
        return workgroupMembers.stream()
                .collect(Collectors.toMap(IPerson::getId, workgroupMember -> workgroupMember));
    }

    @Override
    public abstract Set<IMeeting> createMeetingSet();

    @Override
    public Map<MeetingId, IMeeting> createMeetingMap() {
        Set<IMeeting> meetings = createMeetingSet();
        return meetings.stream()
                .collect(Collectors.toMap(IMeeting::getId, meeting -> meeting));
    }

    @Override
    public abstract Set<IRoom> createRoomSet();

    @Override
    public Map<RoomId, IRoom> createRoomMap() {
        Set<IRoom> rooms = createRoomSet();
        return rooms.stream()
                .collect(Collectors.toMap(IRoom::getId, room -> room));
    }

    @Override
    public abstract Set<IDevice> createDeviceSet();

    @Override
    public Map<DeviceId, IDevice> createDeviceMap() {
        Set<IDevice> devices = createDeviceSet();
        return devices.stream()
                .collect(Collectors.toMap(IDevice::getId, device -> device));
    }
}
