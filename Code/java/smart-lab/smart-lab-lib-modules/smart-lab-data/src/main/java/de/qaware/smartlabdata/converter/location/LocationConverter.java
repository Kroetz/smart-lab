package de.qaware.smartlabdata.converter.location;

import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.Location;
import de.qaware.smartlabcore.data.location.LocationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LocationConverter implements IDtoConverter<ILocation, LocationDto> {

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
        return Location.builder()
                .id(location.getId())
                .name(location.getName())
                .deviceIds(location.getDeviceIds())
                .build();
    }
}
