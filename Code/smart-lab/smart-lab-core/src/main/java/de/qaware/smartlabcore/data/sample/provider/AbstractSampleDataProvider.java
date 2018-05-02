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
import java.util.function.Function;

@Slf4j
public abstract class AbstractSampleDataProvider implements ISampleDataProvider {

    private ISampleDataFactory[] sampleDataFactories;

    public AbstractSampleDataProvider(ISampleDataFactory... sampleDataFactories) {
        this.sampleDataFactories = sampleDataFactories;
    }

    private <T> List<T> getEntities(Function<ISampleDataFactory, List<T>> createEntities) {
        List<T> entities = new ArrayList<>();
        for(ISampleDataFactory factory : sampleDataFactories) {
            entities.addAll(createEntities.apply(factory));
        }
        return entities;
    }

    @Override
    public List<IWorkgroup> getWorkgroups() {
        return getEntities(factory -> factory.createWorkgroupList());
    }

    @Override
    public List<IPerson> getWorkgroupMembers() {
        return getEntities(factory -> factory.createWorkgroupMemberList());
    }

    @Override
    public List<IMeeting> getMeetings() {
        return getEntities(factory -> factory.createMeetingList());
    }

    @Override
    public List<IRoom> getRooms() {
        return getEntities(factory -> factory.createRoomList());
    }

    @Override
    public List<IDevice> getDevices() {
        return getEntities(factory -> factory.createDeviceList());
    }
}
