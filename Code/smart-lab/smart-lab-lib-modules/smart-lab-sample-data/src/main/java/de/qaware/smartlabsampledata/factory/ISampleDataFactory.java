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

import java.util.List;
import java.util.Map;

public interface ISampleDataFactory {

    List<IWorkgroup> createWorkgroupList();
    Map<WorkgroupId, IWorkgroup> createWorkgroupMap();
    List<IPerson> createWorkgroupMemberList();
    Map<PersonId, IPerson> createWorkgroupMemberMap();
    List<IMeeting> createMeetingList();
    Map<MeetingId, IMeeting> createMeetingMap();
    List<IRoom> createRoomList();
    Map<RoomId, IRoom> createRoomMap();
    List<IDevice> createDeviceList();
    Map<DeviceId, IDevice> createDeviceMap();
}
