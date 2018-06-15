package de.qaware.smartlabcore.data.device.entity;

import de.qaware.smartlabcore.data.generic.AbstractIdentifier;

public class DeviceId extends AbstractIdentifier {

    private DeviceId(String idValue) {
        super(idValue);
    }

    public static DeviceId of(String idValue) {
        return new DeviceId(idValue);
    }
}
