package de.qaware.smartlab.core.data.location;

import de.qaware.smartlab.core.data.device.DeviceId;
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
