package de.qaware.smartlab.core.data.actuator;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode
@Slf4j
public class Actuator implements IActuator {

    private final ActuatorId id;
    private final String type;
    private final String name;
    private final String responsibleDelegate;

    public static Actuator of(
            ActuatorId id,
            String type,
            String name,
            String responsibleDelegate) {
        return new Actuator(id, type, name, responsibleDelegate);
    }
}
