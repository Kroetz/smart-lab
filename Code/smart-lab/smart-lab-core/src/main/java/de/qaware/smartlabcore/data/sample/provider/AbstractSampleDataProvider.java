package de.qaware.smartlabcore.data.sample.provider;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.sample.factory.ISampleDataFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AbstractSampleDataProvider implements ISampleDataProvider {

    private ISampleDataFactory[] sampleDataFactories;

    public AbstractSampleDataProvider(ISampleDataFactory... sampleDataFactories) {
        this.sampleDataFactories = sampleDataFactories;
    }

    @Override
    public List<IWorkgroup> getWorkgroups() {
        List<IWorkgroup> workgroups = new ArrayList<>();
        for(ISampleDataFactory factory : sampleDataFactories) {
            workgroups.addAll(factory.createWorkgroupList());
        }
        return workgroups;
    }

    @Override
    public List<IPerson> getWorkgroupMembers() {
        List<IPerson> workgroupMembers = new ArrayList<>();
        for(ISampleDataFactory factory : sampleDataFactories) {
            workgroupMembers.addAll(factory.createWorkgroupMemberList());
        }
        return workgroupMembers;
    }

    @Override
    public List<IMeeting> getMeetings() {
        List<IMeeting> meetings = new ArrayList<>();
        for(ISampleDataFactory factory : sampleDataFactories) {
            meetings.addAll(factory.createMeetingList());
        }
        return meetings;
    }

    @Override
    public List<IRoom> getRooms() {
        List<IRoom> rooms = new ArrayList<>();
        for(ISampleDataFactory factory : sampleDataFactories) {
            rooms.addAll(factory.createRoomList());
        }
        return rooms;
    }

    @Override
    public List<IDevice> getDevices() {
        List<IDevice> devices = new ArrayList<>();
        for(ISampleDataFactory factory : sampleDataFactories) {
            devices.addAll(factory.createDeviceList());
        }
        return devices;
    }
}
