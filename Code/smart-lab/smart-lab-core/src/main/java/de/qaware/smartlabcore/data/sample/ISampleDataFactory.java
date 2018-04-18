package de.qaware.smartlabcore.data.sample;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;

import java.net.MalformedURLException;
import java.util.Map;

public interface ISampleDataFactory {

    Map<Long, IWorkgroup> createWorkgroups() throws MalformedURLException;
    Map<Long, IPerson> createWorkgroupMembers();
    Map<Long, IMeeting> createMeetings();
    Map<Long, IRoom> createRooms();
    Map<Long, IDevice> createDevices();
}
