package de.qaware.smartlabcore.data.sample;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;

import java.net.MalformedURLException;
import java.util.List;

public interface ISampleDataFactory {

    List<IWorkgroup> createWorkgroups() throws MalformedURLException;
    List<IPerson> createWorkgroupMembers();
    List<IMeeting> createMeetings();
    List<IRoom> createRooms();
    List<IDevice> createDevices();
}
