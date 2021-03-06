package de.qaware.smartlab.data.set.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.qaware.smartlab.core.data.actuator.ActuatorDto;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationDto;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.PersonDto;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupDto;
import de.qaware.smartlab.core.exception.data.DataException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;

@Slf4j
public class FileSourcedDataSetFactory extends AbstractDataSetFactory {

    public static final String ID = "fileSourced";

    private final ObjectMapper mapper;
    private final Path eventDataSource;
    private final Path locationDataSource;
    private final Path actuatorDataSource;
    private final Path workgroupDataSource;
    private final Path personDataSource;
    private final IDtoConverter<IEvent, EventDto> eventConverter;
    private final IDtoConverter<ILocation, LocationDto> locationConverter;
    private final IDtoConverter<IActuator, ActuatorDto> actuatorConverter;
    private final IDtoConverter<IWorkgroup, WorkgroupDto> workgroupConverter;
    private final IDtoConverter<IPerson, PersonDto> personConverter;

    @Builder
    public FileSourcedDataSetFactory(
            Path eventDataSource,
            Path locationDataSource,
            Path actuatorDataSource,
            Path workgroupDataSource,
            Path personDataSource,
            IDtoConverter<IEvent, EventDto> eventConverter,
            IDtoConverter<ILocation, LocationDto> locationConverter,
            IDtoConverter<IActuator, ActuatorDto> actuatorConverter,
            IDtoConverter<IWorkgroup, WorkgroupDto> workgroupConverter,
            IDtoConverter<IPerson, PersonDto> personConverter) {
        super(ID);
        /*
         * Registering the time module before auto-registering other modules is needed to avoid conflicts
         * (See https://github.com/FasterXML/jackson-modules-java8)
         */
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();
        this.eventDataSource = eventDataSource;
        this.locationDataSource = locationDataSource;
        this.actuatorDataSource = actuatorDataSource;
        this.workgroupDataSource = workgroupDataSource;
        this.personDataSource = personDataSource;
        this.eventConverter = eventConverter;
        this.locationConverter = locationConverter;
        this.actuatorConverter = actuatorConverter;
        this.workgroupConverter = workgroupConverter;
        this.personConverter = personConverter;
    }

    @Override
    public Set<IEvent> createEventSet() throws DataException {
        if(isNull(this.eventDataSource)) return new HashSet<>();
        return readEventsFromFile(this.eventDataSource);
    }

    @Override
    public Set<ILocation> createLocationSet() throws DataException {
        if(isNull(this.locationDataSource)) return new HashSet<>();
        return readLocationsFromFile(this.locationDataSource);
    }

    @Override
    public Set<IActuator> createActuatorSet() throws DataException {
        if(isNull(this.actuatorDataSource)) return new HashSet<>();
        return readActuatorsFromFile(this.actuatorDataSource);
    }

    @Override
    public Set<IWorkgroup> createWorkgroupSet() throws DataException {
        if(isNull(this.workgroupDataSource)) return new HashSet<>();
        return readWorkgroupsFromFile(this.workgroupDataSource);
    }

    @Override
    public Set<IPerson> createWorkgroupMemberSet() throws DataException {
        if(isNull(this.personDataSource)) return new HashSet<>();
        return readPersonsFromFile(this.personDataSource);
    }

    private Set<IEvent> readEventsFromFile(Path path) throws DataException {
        return readEntitiesFromFile(path, EventDto.class, this.eventConverter);
    }

    private Set<ILocation> readLocationsFromFile(Path path) throws DataException {
        return readEntitiesFromFile(path, LocationDto.class, this.locationConverter);
    }

    private Set<IActuator> readActuatorsFromFile(Path path) throws DataException {
        return readEntitiesFromFile(path, ActuatorDto.class, this.actuatorConverter);
    }

    private Set<IWorkgroup> readWorkgroupsFromFile(Path path) throws DataException {
        return readEntitiesFromFile(path, WorkgroupDto.class, this.workgroupConverter);
    }

    private Set<IPerson> readPersonsFromFile(Path path) throws DataException {
        return readEntitiesFromFile(path, PersonDto.class, this.personConverter);
    }

    private <EntityT, DtoT> Set<EntityT> readEntitiesFromFile(
            Path path,
            Class<DtoT> dtoClass,
            IDtoConverter<EntityT, DtoT> converter) {
        try {
            Set<DtoT> dtos =  this.mapper.readValue(
                    path.toFile(),
                    TypeFactory.defaultInstance().constructCollectionType(HashSet.class, dtoClass));
            return dtos.stream()
                    .map(converter::toEntity)
                    .collect(toSet());
        }
        catch (Exception e) {
            String errorMessage = format(
                    "Could not read entities of type %s from file %s",
                    dtoClass.getName(),
                    path.toString());
            log.error(errorMessage, e);
            throw new DataException(errorMessage, e);
        }
    }
}
