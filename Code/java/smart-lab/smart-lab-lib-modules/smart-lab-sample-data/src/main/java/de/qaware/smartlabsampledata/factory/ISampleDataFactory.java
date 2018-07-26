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

import java.util.Map;
import java.util.Set;

public interface ISampleDataFactory {

    Set<IWorkgroup> createWorkgroupSet();
    Map<WorkgroupId, IWorkgroup> createWorkgroupMap();
    Set<IPerson> createWorkgroupMemberSet();
    Map<PersonId, IPerson> createWorkgroupMemberMap();
    Set<IMeeting> createMeetingSet();
    Map<MeetingId, IMeeting> createMeetingMap();
    Set<IRoom> createRoomSet();
    Map<RoomId, IRoom> createRoomMap();
    Set<IDevice> createDeviceSet();
    Map<DeviceId, IDevice> createDeviceMap();
}
