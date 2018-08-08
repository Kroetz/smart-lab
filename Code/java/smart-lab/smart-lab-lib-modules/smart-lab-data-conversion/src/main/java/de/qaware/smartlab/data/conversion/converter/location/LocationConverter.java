package de.qaware.smartlab.data.conversion.converter.location;

import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.Location;
import de.qaware.smartlab.core.data.location.LocationDto;
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
                .actuatorIds(location.getActuatorIds())
                .build();
    }

    @Override
    public ILocation toEntity(LocationDto location) {
        return Location.of(
                location.getId(),
                location.getName(),
                location.getActuatorIds());
    }
}
