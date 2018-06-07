package de.qaware.smartlabsampledata.provider;

import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabsampledata.factory.ISampleDataFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
        return getEntities(ISampleDataFactory::createWorkgroupList);
    }

    @Override
    public List<IPerson> getWorkgroupMembers() {
        return getEntities(ISampleDataFactory::createWorkgroupMemberList);
    }

    @Override
    public List<IMeeting> getMeetings() {
        return getEntities(ISampleDataFactory::createMeetingList);
    }

    @Override
    public List<IRoom> getRooms() {
        return getEntities(ISampleDataFactory::createRoomList);
    }

    @Override
    public List<IDevice> getDevices() {
        return getEntities(ISampleDataFactory::createDeviceList);
    }
}
