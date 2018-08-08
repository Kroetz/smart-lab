package de.qaware.smartlab.actuator.adapter.adapters.generic;

import de.qaware.smartlab.core.data.actuator.IDeviceAdapter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class AbstractDeviceAdapter implements IDeviceAdapter {

    protected final String deviceType;
    protected final boolean hasLocalApi;

    public AbstractDeviceAdapter(String deviceType, boolean hasLocalApi) {
        this.deviceType = deviceType;
        this.hasLocalApi = hasLocalApi;
    }

    public boolean hasLocalApi() {
        return this.hasLocalApi;
    }
}
