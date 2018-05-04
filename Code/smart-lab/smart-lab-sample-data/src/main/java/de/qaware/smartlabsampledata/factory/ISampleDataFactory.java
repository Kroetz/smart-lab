package de.qaware.smartlabsampledata.factory;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;

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
