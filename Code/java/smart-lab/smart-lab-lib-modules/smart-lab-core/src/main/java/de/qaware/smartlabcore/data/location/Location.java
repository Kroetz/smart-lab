package de.qaware.smartlabcore.data.location;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode
@Slf4j
public class Location implements ILocation {

    private final LocationId id;
    private final String name;
    private final Collection<DeviceId> deviceIds;

    public static Location of(
            LocationId id,
            String name,
            Collection<DeviceId> deviceIds) {
        return new Location(id, name, deviceIds);
    }
}
