package de.qaware.smartlabcommons.data.device;

import de.qaware.smartlabcommons.data.device.DeviceRole;
import de.qaware.smartlabcommons.data.device.IDevice;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractDevice implements IDevice {

    private String id;
    private String name;
    private DeviceRole role;

    public AbstractDevice(String id, String name, DeviceRole role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
