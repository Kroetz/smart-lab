package de.qaware.smartlabcore.data.device.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlabcore.data.generic.AbstractIdentifier;

public class DeviceId extends AbstractIdentifier {

    private DeviceId(String idValue) {
        super(idValue);
    }

    @JsonCreator
    public static DeviceId of(@JsonProperty(ID_VALUE_FIELD_NAME) String idValue) {
        return new DeviceId(idValue);
    }
}
