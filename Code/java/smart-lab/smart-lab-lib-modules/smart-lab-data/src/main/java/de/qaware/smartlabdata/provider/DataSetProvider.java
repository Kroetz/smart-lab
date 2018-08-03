package de.qaware.smartlabdata.provider;

import de.qaware.smartlabcore.data.IDataSetProvider;
import de.qaware.smartlabcore.data.device.entity.Device;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.data.location.Location;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.Meeting;
import de.qaware.smartlabcore.data.person.Person;
import de.qaware.smartlabcore.data.workgroup.Workgroup;
import de.qaware.smartlabcore.exception.DataSetException;
import de.qaware.smartlabdata.factory.FileSourcedDataSetFactory;
import de.qaware.smartlabdata.factory.IDataSetFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

public class DataSetProvider implements IDataSetProvider {

    private final Set<IDataSetFactory> meetingFactories;
    private final Set<IDataSetFactory> locationFactories;
    private final Set<IDataSetFactory> deviceFactories;
    private final Set<IDataSetFactory> workgroupFactories;
    private final Set<IDataSetFactory> personFactories;

    private DataSetProvider(
            Set<IDataSetFactory> meetingFactories,
            Set<IDataSetFactory> locationFactories,
            Set<IDataSetFactory> deviceFactories,
            Set<IDataSetFactory> workgroupFactories,
            Set<IDataSetFactory> personFactories) {
        this.meetingFactories = meetingFactories;
        this.locationFactories = locationFactories;
        this.deviceFactories = deviceFactories;
        this.workgroupFactories = workgroupFactories;
        this.personFactories = personFactories;
    }

    private <EntityT> Set<EntityT> getEntities(
            Set<IDataSetFactory> entityFactories,
            Function<IDataSetFactory, Set<EntityT>> entityCreation) {
        Set<EntityT> entities = new HashSet<>();
        for(IDataSetFactory factory : entityFactories) {
            entities.addAll(entityCreation.apply(factory));
        }
        return entities;
    }

    @Override
    public Set<Meeting> getMeetings() throws DataSetException {
        return getEntities(this.meetingFactories, IDataSetFactory::createMeetingSet);
    }

    @Override
    public Map<LocationId, Set<Meeting>> getMeetingsByLocation() throws DataSetException {
        Set<Meeting> meetings = getEntities(this.meetingFactories, IDataSetFactory::createMeetingSet);
        return meetings.stream().collect(groupingBy(
                IMeeting::getLocationId,
                toSet()));
    }

    @Override
    public Set<Location> getLocations() throws DataSetException {
        return getEntities(this.locationFactories, IDataSetFactory::createLocationSet);
    }

    @Override
    public Set<Device> getDevices() throws DataSetException {
        return getEntities(this.deviceFactories, IDataSetFactory::createDeviceSet);
    }

    @Override
    public Set<Workgroup> getWorkgroups() throws DataSetException {
        return getEntities(this.workgroupFactories, IDataSetFactory::createWorkgroupSet);
    }

    @Override
    public Set<Person> getWorkgroupMembers() throws DataSetException {
        return getEntities(this.personFactories, IDataSetFactory::createWorkgroupMemberSet);
    }

    @Component
    @Slf4j
    public static class Factory {

        private final IResolver<String, IDataSetFactory> dataSetFactoryResolver;

        public Factory(IResolver<String, IDataSetFactory> dataSetFactoryResolver) {
            this.dataSetFactoryResolver = dataSetFactoryResolver;
        }

        public DataSetProvider of(
                List<String> meetingDataSourceTokens,
                List<String> locationDataSourceTokens,
                List<String> deviceDataSourceTokens,
                List<String> workgroupDataSourceTokens,
                List<String> personDataSourceTokens) {
            return new DataSetProvider(
                    dataSetFactoriesFromTokens(
                            meetingDataSourceTokens,
                            (builder, dataSourceToken) -> builder.meetingDataSource(get(dataSourceToken))),
                    dataSetFactoriesFromTokens(
                            locationDataSourceTokens,
                            (builder, dataSourceToken) -> builder.locationDataSource(get(dataSourceToken))),
                    dataSetFactoriesFromTokens(
                            deviceDataSourceTokens,
                            (builder, dataSourceToken) -> builder.deviceDataSource(get(dataSourceToken))),
                    dataSetFactoriesFromTokens(
                            workgroupDataSourceTokens,
                            (builder, dataSourceToken) -> builder.workgroupDataSource(get(dataSourceToken))),
                    dataSetFactoriesFromTokens(
                            personDataSourceTokens,
                            (builder, dataSourceToken) -> builder.personDataSource(get(dataSourceToken))));
        }

        public DataSetProvider of(
                Set<IDataSetFactory> meetingFactories,
                Set<IDataSetFactory> locationFactories,
                Set<IDataSetFactory> deviceFactories,
                Set<IDataSetFactory> workgroupFactories,
                Set<IDataSetFactory> personFactories) {
            return new DataSetProvider(
                    meetingFactories,
                    locationFactories,
                    deviceFactories,
                    workgroupFactories,
                    personFactories);
        }

        private Set<IDataSetFactory> dataSetFactoriesFromTokens(
                List<String> dataSourceTokens,
                BiConsumer<FileSourcedDataSetFactory.FileSourcedDataSetFactoryBuilder, String> dataSourceSetter) {
            Set<IDataSetFactory> factories = new HashSet<>();
            for(String dataSourceToken : dataSourceTokens) {
                Optional<IDataSetFactory> factory = this.dataSetFactoryResolver.resolve(dataSourceToken);
                if(factory.isPresent()) factories.add(factory.get());
                else {
                    FileSourcedDataSetFactory.FileSourcedDataSetFactoryBuilder builder = FileSourcedDataSetFactory.builder();
                    dataSourceSetter.accept(builder, dataSourceToken);
                    factories.add(builder.build());
                }
            }
            return factories;
        }
    }
}
