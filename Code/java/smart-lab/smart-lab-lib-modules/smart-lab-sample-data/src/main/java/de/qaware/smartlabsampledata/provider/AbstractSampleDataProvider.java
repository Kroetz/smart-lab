package de.qaware.smartlabsampledata.provider;

import de.qaware.smartlabcore.data.ISampleDataProvider;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabsampledata.factory.ISampleDataFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

public abstract class AbstractSampleDataProvider implements ISampleDataProvider {

    private ISampleDataFactory[] sampleDataFactories;

    public AbstractSampleDataProvider(ISampleDataFactory... sampleDataFactories) {
        this.sampleDataFactories = sampleDataFactories;
    }

    private <T> Set<T> getEntities(Function<ISampleDataFactory, Set<T>> entityCreation) {
        Set<T> entities = new HashSet<>();
        for(ISampleDataFactory factory : sampleDataFactories) {
            entities.addAll(entityCreation.apply(factory));
        }
        return entities;
    }

    @Override
    public Set<IWorkgroup> getWorkgroups() {
        return getEntities(ISampleDataFactory::createWorkgroupSet);
    }

    @Override
    public Set<IPerson> getWorkgroupMembers() {
        return getEntities(ISampleDataFactory::createWorkgroupMemberSet);
    }

    @Override
    public Set<IMeeting> getMeetings() {
        return getEntities(ISampleDataFactory::createMeetingSet);
    }

    @Override
    public Map<LocationId, Set<IMeeting>> getMeetingsByLocation() {
        Set<IMeeting> meetings = getEntities(ISampleDataFactory::createMeetingSet);
        return meetings.stream().collect(groupingBy(
                IMeeting::getLocationId,
                toSet()));
    }

    @Override
    public Set<ILocation> getLocations() {
        return getEntities(ISampleDataFactory::createLocationSet);
    }

    @Override
    public Set<IDevice> getDevices() {
        return getEntities(ISampleDataFactory::createDeviceSet);
    }
}
