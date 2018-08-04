package de.qaware.smartlabdataset.provider;

import de.qaware.smartlabcore.data.IDataSetProvider;
import de.qaware.smartlabcore.data.device.DeviceDto;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.LocationDto;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingDto;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonDto;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupDto;
import de.qaware.smartlabcore.exception.DataSetException;
import de.qaware.smartlabdataset.factory.FileSourcedDataSetFactory;
import de.qaware.smartlabdataset.factory.IDataSetFactory;
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
    public Set<IMeeting> getMeetings() throws DataSetException {
        return getEntities(this.meetingFactories, IDataSetFactory::createMeetingSet);
    }

    @Override
    public Map<LocationId, Set<IMeeting>> getMeetingsByLocation() throws DataSetException {
        Set<IMeeting> meetings = getEntities(this.meetingFactories, IDataSetFactory::createMeetingSet);
        return meetings.stream().collect(groupingBy(
                IMeeting::getLocationId,
                toSet()));
    }

    @Override
    public Set<ILocation> getLocations() throws DataSetException {
        return getEntities(this.locationFactories, IDataSetFactory::createLocationSet);
    }

    @Override
    public Set<IDevice> getDevices() throws DataSetException {
        return getEntities(this.deviceFactories, IDataSetFactory::createDeviceSet);
    }

    @Override
    public Set<IWorkgroup> getWorkgroups() throws DataSetException {
        return getEntities(this.workgroupFactories, IDataSetFactory::createWorkgroupSet);
    }

    @Override
    public Set<IPerson> getWorkgroupMembers() throws DataSetException {
        return getEntities(this.personFactories, IDataSetFactory::createWorkgroupMemberSet);
    }

    @Component
    @Slf4j
    public static class Factory {

        private final IResolver<String, IDataSetFactory> dataSetFactoryResolver;
        private final IDtoConverter<IMeeting, MeetingDto> meetingConverter;
        private final IDtoConverter<ILocation, LocationDto> locationConverter;
        private final IDtoConverter<IDevice, DeviceDto> deviceConverter;
        private final IDtoConverter<IWorkgroup, WorkgroupDto> workgroupConverter;
        private final IDtoConverter<IPerson, PersonDto> personConverter;

        public Factory(
                IResolver<String, IDataSetFactory> dataSetFactoryResolver,
                IDtoConverter<IMeeting, MeetingDto> meetingConverter,
                IDtoConverter<ILocation, LocationDto> locationConverter,
                IDtoConverter<IDevice, DeviceDto> deviceConverter,
                IDtoConverter<IWorkgroup, WorkgroupDto> workgroupConverter,
                IDtoConverter<IPerson, PersonDto> personConverter) {
            this.dataSetFactoryResolver = dataSetFactoryResolver;
            this.meetingConverter = meetingConverter;
            this.locationConverter = locationConverter;
            this.deviceConverter = deviceConverter;
            this.workgroupConverter = workgroupConverter;
            this.personConverter = personConverter;
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
                            (builder, dataSourceToken) -> builder.meetingDataSource(get(dataSourceToken)).meetingConverter(this.meetingConverter)),
                    dataSetFactoriesFromTokens(
                            locationDataSourceTokens,
                            (builder, dataSourceToken) -> builder.locationDataSource(get(dataSourceToken)).locationConverter(this.locationConverter)),
                    dataSetFactoriesFromTokens(
                            deviceDataSourceTokens,
                            (builder, dataSourceToken) -> builder.deviceDataSource(get(dataSourceToken)).deviceConverter(this.deviceConverter)),
                    dataSetFactoriesFromTokens(
                            workgroupDataSourceTokens,
                            (builder, dataSourceToken) -> builder.workgroupDataSource(get(dataSourceToken)).workgroupConverter(this.workgroupConverter)),
                    dataSetFactoriesFromTokens(
                            personDataSourceTokens,
                            (builder, dataSourceToken) -> builder.personDataSource(get(dataSourceToken)).personConverter(this.personConverter)));
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
