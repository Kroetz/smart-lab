package de.qaware.smartlabsampledata.provider;

import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;

import java.util.List;
import java.util.Map;

public interface ISampleDataProvider {

    List<IWorkgroup> getWorkgroups();
    List<IPerson> getWorkgroupMembers();
    List<IMeeting> getMeetings();
    Map<RoomId, List<IMeeting>> getMeetingsByRoom();
    List<IRoom> getRooms();
    List<IDevice> getDevices();
}
