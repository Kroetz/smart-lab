package de.qaware.smartlab.data.set.provider;

import de.qaware.smartlab.core.data.IDataSetProvider;
import de.qaware.smartlab.core.data.actuator.ActuatorDto;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.generic.IResolver;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationDto;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.PersonDto;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupDto;
import de.qaware.smartlab.core.exception.data.DataSetException;
import de.qaware.smartlab.core.exception.resolver.ResolverException;
import de.qaware.smartlab.data.set.factory.FileSourcedDataSetFactory;
import de.qaware.smartlab.data.set.factory.IDataSetFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

public class DataSetProvider implements IDataSetProvider {

    private final Set<IDataSetFactory> eventFactories;
    private final Set<IDataSetFactory> locationFactories;
    private final Set<IDataSetFactory> actuatorFactories;
    private final Set<IDataSetFactory> workgroupFactories;
    private final Set<IDataSetFactory> personFactories;

    private DataSetProvider(
            Set<IDataSetFactory> eventFactories,
            Set<IDataSetFactory> locationFactories,
            Set<IDataSetFactory> actuatorFactories,
            Set<IDataSetFactory> workgroupFactories,
            Set<IDataSetFactory> personFactories) {
        this.eventFactories = eventFactories;
        this.locationFactories = locationFactories;
        this.actuatorFactories = actuatorFactories;
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
    public Set<IEvent> getEvents() throws DataSetException {
        return getEntities(this.eventFactories, IDataSetFactory::createEventSet);
    }

    @Override
    public Map<LocationId, Set<IEvent>> getEventsByLocation() throws DataSetException {
        Set<IEvent> events = getEntities(this.eventFactories, IDataSetFactory::createEventSet);
        return events.stream().collect(groupingBy(
                IEvent::getLocationId,
                toSet()));
    }

    @Override
    public Set<ILocation> getLocations() throws DataSetException {
        return getEntities(this.locationFactories, IDataSetFactory::createLocationSet);
    }

    @Override
    public Set<IActuator> getActuators() throws DataSetException {
        return getEntities(this.actuatorFactories, IDataSetFactory::createActuatorSet);
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
        private final IDtoConverter<IEvent, EventDto> eventConverter;
        private final IDtoConverter<ILocation, LocationDto> locationConverter;
        private final IDtoConverter<IActuator, ActuatorDto> actuatorConverter;
        private final IDtoConverter<IWorkgroup, WorkgroupDto> workgroupConverter;
        private final IDtoConverter<IPerson, PersonDto> personConverter;

        public Factory(
                IResolver<String, IDataSetFactory> dataSetFactoryResolver,
                IDtoConverter<IEvent, EventDto> eventConverter,
                IDtoConverter<ILocation, LocationDto> locationConverter,
                IDtoConverter<IActuator, ActuatorDto> actuatorConverter,
                IDtoConverter<IWorkgroup, WorkgroupDto> workgroupConverter,
                IDtoConverter<IPerson, PersonDto> personConverter) {
            this.dataSetFactoryResolver = dataSetFactoryResolver;
            this.eventConverter = eventConverter;
            this.locationConverter = locationConverter;
            this.actuatorConverter = actuatorConverter;
            this.workgroupConverter = workgroupConverter;
            this.personConverter = personConverter;
        }

        public DataSetProvider of(
                List<String> eventDataSourceTokens,
                List<String> locationDataSourceTokens,
                List<String> actuatorDataSourceTokens,
                List<String> workgroupDataSourceTokens,
                List<String> personDataSourceTokens) {
            return new DataSetProvider(
                    dataSetFactoriesFromTokens(
                            eventDataSourceTokens,
                            (builder, dataSourceToken) -> builder.eventDataSource(get(dataSourceToken)).eventConverter(this.eventConverter)),
                    dataSetFactoriesFromTokens(
                            locationDataSourceTokens,
                            (builder, dataSourceToken) -> builder.locationDataSource(get(dataSourceToken)).locationConverter(this.locationConverter)),
                    dataSetFactoriesFromTokens(
                            actuatorDataSourceTokens,
                            (builder, dataSourceToken) -> builder.actuatorDataSource(get(dataSourceToken)).actuatorConverter(this.actuatorConverter)),
                    dataSetFactoriesFromTokens(
                            workgroupDataSourceTokens,
                            (builder, dataSourceToken) -> builder.workgroupDataSource(get(dataSourceToken)).workgroupConverter(this.workgroupConverter)),
                    dataSetFactoriesFromTokens(
                            personDataSourceTokens,
                            (builder, dataSourceToken) -> builder.personDataSource(get(dataSourceToken)).personConverter(this.personConverter)));
        }

        public DataSetProvider of(
                Set<IDataSetFactory> eventFactories,
                Set<IDataSetFactory> locationFactories,
                Set<IDataSetFactory> actuatorFactories,
                Set<IDataSetFactory> workgroupFactories,
                Set<IDataSetFactory> personFactories) {
            return new DataSetProvider(
                    eventFactories,
                    locationFactories,
                    actuatorFactories,
                    workgroupFactories,
                    personFactories);
        }

        private Set<IDataSetFactory> dataSetFactoriesFromTokens(
                List<String> dataSourceTokens,
                BiConsumer<FileSourcedDataSetFactory.FileSourcedDataSetFactoryBuilder, String> dataSourceSetter) {
            Set<IDataSetFactory> factories = new HashSet<>();
            for(String dataSourceToken : dataSourceTokens) {
                try {
                    factories.add(this.dataSetFactoryResolver.resolve(dataSourceToken));
                }
                catch(ResolverException e) {
                    FileSourcedDataSetFactory.FileSourcedDataSetFactoryBuilder builder =
                            FileSourcedDataSetFactory.builder();
                    dataSourceSetter.accept(builder, dataSourceToken);
                    factories.add(builder.build());
                }
            }
            return factories;
        }
    }
}
