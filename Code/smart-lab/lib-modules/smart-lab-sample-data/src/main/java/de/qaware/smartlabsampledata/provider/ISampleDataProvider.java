package de.qaware.smartlabsampledata.provider;

import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;

import java.util.List;

public interface ISampleDataProvider {

    List<IWorkgroup> getWorkgroups();
    List<IPerson> getWorkgroupMembers();
    List<IMeeting> getMeetings();
    List<IRoom> getRooms();
    List<IDevice> getDevices();
}
