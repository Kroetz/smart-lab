package de.qaware.smartlabdata.factory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.qaware.smartlabcore.data.device.entity.Device;
import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.location.Location;
import de.qaware.smartlabcore.data.meeting.Meeting;
import de.qaware.smartlabcore.data.person.Person;
import de.qaware.smartlabcore.data.workgroup.Workgroup;
import de.qaware.smartlabcore.exception.DataSetException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Set;

import static java.lang.String.format;

@Slf4j
public class FileSourcedDataSetFactory extends AbstractDataSetFactory {

    public static final String ID = "fileSourced";

    private final Path meetingDataSource;
    private final Path locationDataSource;
    private final Path deviceDataSource;
    private final Path workgroupDataSource;
    private final Path personDataSource;
    private final ObjectMapper mapper;

    @Builder
    public FileSourcedDataSetFactory(
            Path meetingDataSource,
            Path locationDataSource,
            Path deviceDataSource,
            Path workgroupDataSource,
            Path personDataSource) {
        super(ID);
        this.meetingDataSource = meetingDataSource;
        this.locationDataSource = locationDataSource;
        this.deviceDataSource = deviceDataSource;
        this.workgroupDataSource = workgroupDataSource;
        this.personDataSource = personDataSource;
        this.mapper = new ObjectMapper();
    }

    @Override
    public Set<Meeting> createMeetingSet() throws DataSetException {
        return readMeetingsFromFile(this.meetingDataSource);
    }

    @Override
    public Set<Location> createLocationSet() throws DataSetException {
        return readLocationsFromFile(this.locationDataSource);
    }

    @Override
    public Set<Device> createDeviceSet() throws DataSetException {
        return readDevicesFromFile(this.deviceDataSource);
    }

    @Override
    public Set<Workgroup> createWorkgroupSet() throws DataSetException {
        return readWorkgroupsFromFile(this.workgroupDataSource);
    }

    @Override
    public Set<Person> createWorkgroupMemberSet() throws DataSetException {
        return readPersonsFromFile(this.personDataSource);
    }

    private Set<Meeting> readMeetingsFromFile(Path path) throws DataSetException {
        return readEntitiesFromFile(path, Meeting.class);
    }

    private Set<Location> readLocationsFromFile(Path path) throws DataSetException {
        return readEntitiesFromFile(path, Location.class);
    }

    private Set<Device> readDevicesFromFile(Path path) throws DataSetException {
        return readEntitiesFromFile(path, Device.class);
    }

    private Set<Workgroup> readWorkgroupsFromFile(Path path) throws DataSetException {
        return readEntitiesFromFile(path, Workgroup.class);
    }

    private Set<Person> readPersonsFromFile(Path path) throws DataSetException {
        return readEntitiesFromFile(path, Person.class);
    }

    private <EntityT extends IEntity> Set<EntityT> readEntitiesFromFile(Path path, Class<EntityT> entityClass) {
        try {
            return this.mapper.readValue(path.toFile(), new TypeReference<Set<EntityT>>(){});
        }
        catch (Exception e) {
            String errorMessage = format(
                    "Could not read entities of type %s from file %s",
                    entityClass.getName(),
                    path.toString());
            log.error(errorMessage, e);
            throw new DataSetException(errorMessage, e);
        }
    }
}
