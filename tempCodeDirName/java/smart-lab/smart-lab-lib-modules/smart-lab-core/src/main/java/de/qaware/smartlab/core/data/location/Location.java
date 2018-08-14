package de.qaware.smartlab.core.data.location;

import de.qaware.smartlab.core.data.actuator.ActuatorId;
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
    private final Collection<ActuatorId> actuatorIds;

    public static Location of(
            LocationId id,
            String name,
            Collection<ActuatorId> actuatorIds) {
        return new Location(id, name, actuatorIds);
    }
}
