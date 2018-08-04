package de.qaware.smartlabcore.data.device;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode
@Slf4j
public class Device implements IDevice {

    // TODO: Fachliche Datentypen statt String-IDs
    private final DeviceId id;
    private final String type;
    private final String name;
    private final String responsibleDelegate;

    public static Device of(
            DeviceId id,
            String type,
            String name,
            String responsibleDelegate) {
        return new Device(id, type, name, responsibleDelegate);
    }
}
