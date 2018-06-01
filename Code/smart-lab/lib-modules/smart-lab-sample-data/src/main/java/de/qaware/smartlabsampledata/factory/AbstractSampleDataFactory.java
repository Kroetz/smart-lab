package de.qaware.smartlabsampledata.factory;

import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractSampleDataFactory implements ISampleDataFactory {

    Instant timeBase;

    public AbstractSampleDataFactory() {
        this.timeBase = Instant.now();
    }

    @Override
    public abstract List<IWorkgroup> createWorkgroupList();

    @Override
    public Map<String, IWorkgroup> createWorkgroupMap() {
        List<IWorkgroup> workgroups = createWorkgroupList();
        return workgroups.stream()
                .collect(Collectors.toMap(IWorkgroup::getId, workgroup -> workgroup));
    }

    @Override
    public abstract List<IPerson> createWorkgroupMemberList();

    @Override
    public Map<String, IPerson> createWorkgroupMemberMap() {
        List<IPerson> workgroupMembers = createWorkgroupMemberList();
        return workgroupMembers.stream()
                .collect(Collectors.toMap(IPerson::getId, workgroupMember -> workgroupMember));
    }

    @Override
    public abstract List<IMeeting> createMeetingList();

    @Override
    public Map<String, IMeeting> createMeetingMap() {
        List<IMeeting> meetings = createMeetingList();
        return meetings.stream()
                .collect(Collectors.toMap(IMeeting::getId, meeting -> meeting));
    }

    @Override
    public abstract List<IRoom> createRoomList();

    @Override
    public Map<String, IRoom> createRoomMap() {
        List<IRoom> rooms = createRoomList();
        return rooms.stream()
                .collect(Collectors.toMap(IRoom::getId, room -> room));
    }

    @Override
    public abstract List<IDevice> createDeviceList();

    @Override
    public Map<String, IDevice> createDeviceMap() {
        List<IDevice> devices = createDeviceList();
        return devices.stream()
                .collect(Collectors.toMap(IDevice::getId, device -> device));
    }
}
