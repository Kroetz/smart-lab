package de.qaware.smartlabsampledata.factory;

import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;

import java.util.List;
import java.util.Map;

public interface ISampleDataFactory {

    List<IWorkgroup> createWorkgroupList();
    Map<String, IWorkgroup> createWorkgroupMap();
    List<IPerson> createWorkgroupMemberList();
    Map<String, IPerson> createWorkgroupMemberMap();
    List<IMeeting> createMeetingList();
    Map<String, IMeeting> createMeetingMap();
    List<IRoom> createRoomList();
    Map<String, IRoom> createRoomMap();
    List<IDevice> createDeviceList();
    Map<String, IDevice> createDeviceMap();
}
