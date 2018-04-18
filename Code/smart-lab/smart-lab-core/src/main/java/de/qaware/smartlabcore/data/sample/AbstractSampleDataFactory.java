package de.qaware.smartlabcore.data.sample;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;

import java.net.MalformedURLException;
import java.time.Instant;
import java.util.Map;

public abstract class AbstractSampleDataFactory implements ISampleDataFactory {

    protected Instant timeBase;

    public AbstractSampleDataFactory() {
        this.timeBase = Instant.now();
    }

    @Override
    public abstract Map<Long, IWorkgroup> createWorkgroups() throws MalformedURLException;

    @Override
    public abstract Map<Long, IPerson> createWorkgroupMembers();

    @Override
    public abstract Map<Long, IMeeting> createMeetings();

    @Override
    public abstract Map<Long, IRoom> createRooms();

    @Override
    public abstract Map<Long, IDevice> createDevices();
}
