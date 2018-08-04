package de.qaware.smartlabdataset.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.qaware.smartlabcore.data.device.DeviceDto;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.LocationDto;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingDto;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonDto;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupDto;
import de.qaware.smartlabcore.exception.DataSetException;
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
    private final Path meetingDataSource;
    private final Path locationDataSource;
    private final Path deviceDataSource;
    private final Path workgroupDataSource;
    private final Path personDataSource;
    private final IDtoConverter<IMeeting, MeetingDto> meetingConverter;
    private final IDtoConverter<ILocation, LocationDto> locationConverter;
    private final IDtoConverter<IDevice, DeviceDto> deviceConverter;
    private final IDtoConverter<IWorkgroup, WorkgroupDto> workgroupConverter;
    private final IDtoConverter<IPerson, PersonDto> personConverter;

    @Builder
    public FileSourcedDataSetFactory(
            Path meetingDataSource,
            Path locationDataSource,
            Path deviceDataSource,
            Path workgroupDataSource,
            Path personDataSource,
            IDtoConverter<IMeeting, MeetingDto> meetingConverter,
            IDtoConverter<ILocation, LocationDto> locationConverter,
            IDtoConverter<IDevice, DeviceDto> deviceConverter,
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
        this.meetingDataSource = meetingDataSource;
        this.locationDataSource = locationDataSource;
        this.deviceDataSource = deviceDataSource;
        this.workgroupDataSource = workgroupDataSource;
        this.personDataSource = personDataSource;
        this.meetingConverter = meetingConverter;
        this.locationConverter = locationConverter;
        this.deviceConverter = deviceConverter;
        this.workgroupConverter = workgroupConverter;
        this.personConverter = personConverter;
    }

    @Override
    public Set<IMeeting> createMeetingSet() throws DataSetException {
        if(isNull(this.meetingDataSource)) return new HashSet<>();
        return readMeetingsFromFile(this.meetingDataSource);
    }

    @Override
    public Set<ILocation> createLocationSet() throws DataSetException {
        if(isNull(this.locationDataSource)) return new HashSet<>();
        return readLocationsFromFile(this.locationDataSource);
    }

    @Override
    public Set<IDevice> createDeviceSet() throws DataSetException {
        if(isNull(this.deviceDataSource)) return new HashSet<>();
        return readDevicesFromFile(this.deviceDataSource);
    }

    @Override
    public Set<IWorkgroup> createWorkgroupSet() throws DataSetException {
        if(isNull(this.workgroupDataSource)) return new HashSet<>();
        return readWorkgroupsFromFile(this.workgroupDataSource);
    }

    @Override
    public Set<IPerson> createWorkgroupMemberSet() throws DataSetException {
        if(isNull(this.personDataSource)) return new HashSet<>();
        return readPersonsFromFile(this.personDataSource);
    }

    private Set<IMeeting> readMeetingsFromFile(Path path) throws DataSetException {
        return readEntitiesFromFile(path, MeetingDto.class, this.meetingConverter);
    }

    private Set<ILocation> readLocationsFromFile(Path path) throws DataSetException {
        return readEntitiesFromFile(path, LocationDto.class, this.locationConverter);
    }

    private Set<IDevice> readDevicesFromFile(Path path) throws DataSetException {
        return readEntitiesFromFile(path, DeviceDto.class, this.deviceConverter);
    }

    private Set<IWorkgroup> readWorkgroupsFromFile(Path path) throws DataSetException {
        return readEntitiesFromFile(path, WorkgroupDto.class, this.workgroupConverter);
    }

    private Set<IPerson> readPersonsFromFile(Path path) throws DataSetException {
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
            throw new DataSetException(errorMessage, e);
        }
    }
}
