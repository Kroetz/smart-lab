package de.qaware.smartlabcommons.data.device;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractDevice implements IDevice {

    private long id;
    private String name;
    private DeviceRole role;

    public AbstractDevice(long id, String name, DeviceRole role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
