package de.qaware.smartlabsampledata.provider;

import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;

import java.util.List;

public interface ISampleDataProvider {

    List<IWorkgroup> getWorkgroups();
    List<IPerson> getWorkgroupMembers();
    List<IMeeting> getMeetings();
    List<IRoom> getRooms();
    List<IDevice> getDevices();
}