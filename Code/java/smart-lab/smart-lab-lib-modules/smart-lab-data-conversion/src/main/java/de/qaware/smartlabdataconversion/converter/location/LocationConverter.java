package de.qaware.smartlabdataconversion.converter.location;

import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.Location;
import de.qaware.smartlabcore.data.location.LocationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LocationConverter implements IDtoConverter<ILocation, LocationDto> {

    /*
     * TODO: Map fields automatically without breaking the immutability of the entity classes
     * Maybe http://modelmapper.org/ is appropriate for this task
     */

    @Override
    public LocationDto toDto(ILocation location) {
        return LocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .deviceIds(location.getDeviceIds())
                .build();
    }

    @Override
    public ILocation toEntity(LocationDto location) {
        return Location.of(
                location.getId(),
                location.getName(),
                location.getDeviceIds());
    }
}
