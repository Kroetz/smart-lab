package de.qaware.smartlabcore.data.sample;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;

import java.net.MalformedURLException;
import java.util.List;

public abstract class AbstractSampleDataFactory implements ISampleDataFactory {

    @Override
    public abstract List<IWorkgroup> createWorkgroups() throws MalformedURLException;

    @Override
    public abstract List<IPerson> createWorkgroupMembers();

    @Override
    public abstract List<IMeeting> createMeetings();

    @Override
    public abstract List<IRoom> createRooms();

    @Override
    public abstract List<IDevice> createDevices();
}
