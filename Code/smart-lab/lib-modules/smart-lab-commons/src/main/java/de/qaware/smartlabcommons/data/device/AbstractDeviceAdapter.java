package de.qaware.smartlabcommons.data.device;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class AbstractDeviceAdapter {

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
