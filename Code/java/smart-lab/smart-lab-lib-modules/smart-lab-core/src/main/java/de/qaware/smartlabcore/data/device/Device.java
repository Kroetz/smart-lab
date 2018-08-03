package de.qaware.smartlabcore.data.device;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // TODO: Really needed for objects being able to serialize/deserialize?
@AllArgsConstructor
public class Device implements IDevice {

    // TODO: Fachliche Datentypen statt String-IDs
    private DeviceId id;
    private String type;
    private String name;
    private String responsibleDelegate;
}
