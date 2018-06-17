package de.qaware.smartlabsampledata.provider;

import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;

import java.util.Map;
import java.util.Set;

public interface ISampleDataProvider {

    Set<IWorkgroup> getWorkgroups();
    Set<IPerson> getWorkgroupMembers();
    Set<IMeeting> getMeetings();
    Map<RoomId, Set<IMeeting>> getMeetingsByRoom();
    Set<IRoom> getRooms();
    Set<IDevice> getDevices();
}
