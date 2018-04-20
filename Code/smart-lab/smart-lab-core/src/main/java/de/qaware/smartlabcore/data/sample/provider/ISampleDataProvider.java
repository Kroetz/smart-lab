package de.qaware.smartlabcore.data.sample.provider;

import de.qaware.smartlabcommons.data.device.IDevice;
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
